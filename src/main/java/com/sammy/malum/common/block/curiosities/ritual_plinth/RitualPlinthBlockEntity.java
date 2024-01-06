package com.sammy.malum.common.block.curiosities.ritual_plinth;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.ritual.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.altar.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nullable;
import java.util.*;

public class RitualPlinthBlockEntity extends LodestoneBlockEntity {

    public MalumRitualType ritualType;
    public int spiritAmount;
    public int progress;
    public Map<Object, Integer> absorptionProgress = new HashMap<>();

    public MalumRitualRecipeData ritualRecipe;
    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory extrasInventory;

    public LazyOptional<IItemHandler> internalInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory, extrasInventory));
    public LazyOptional<IItemHandler> exposedInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory));

    public RitualPlinthBlockEntity(BlockEntityType<? extends RitualPlinthBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RitualPlinthBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RITUAL_PLINTH.get(), pos, state);

        inventory = new MalumBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        extrasInventory = new MalumBlockEntityInventory(8, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (spiritAmount != 0) {
            compound.putInt("spiritAmount", spiritAmount);
        }
        if (progress != 0) {
            compound.putInt("progress", progress);
        }
        if (ritualType != null) {
            compound.putString("ritualType", ritualType.identifier.toString());
        }
        inventory.save(compound);
        extrasInventory.save(compound, "extrasInventory");
    }

    @Override
    public void load(CompoundTag compound) {
        spiritAmount = compound.getInt("spiritAmount");
        progress = compound.getInt("progress");
        ritualType = RitualRegistry.RITUALS.get(new ResourceLocation(compound.getString("ritualType")));

        inventory.load(compound);
        extrasInventory.load(compound, "extrasInventory");
        super.load(compound);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        extrasInventory.dumpItems(level, worldPosition);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        inventory.interact(player.level(), player, hand);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void init() {
        ItemStack stack = inventory.getStackInSlot(0);
        ritualRecipe = RitualRegistry.RITUALS.values().stream().map(MalumRitualType::getRecipeData).filter(recipeData -> recipeData.input.matches(stack)).findAny().orElse(null);
    }

    @Override
    public void tick() {
        super.tick();
        if (ritualType != null) {
            if (progress > 0) {
                progress--;
                if (!level.isClientSide) {
                    eatSpirits();
                    if (progress == 0) {
                        completeCharging();
                    }
                }
            }
            else if (!level.isClientSide) {
                ritualType.triggerRitualEffect(this);
            }
        }
        else if (ritualRecipe != null) {
            progress++;
            if (!level.isClientSide) {
                if (progress > 60) {
                    boolean success = consume();
                    if (success) {
                        beginCharging();
                    }
                    else {
                        progress = 0;
                    }
                }
            }
        }
        else {
            progress = 0;
        }
        if (ritualType != null && level.isClientSide) {
            RitualPlinthParticleEffects.passiveRitualPlinthParticles(this);
        }
    }

    public boolean consume() {
        if (ritualRecipe.extraItems.isEmpty()) {
            return true;
        }
        extrasInventory.updateData();
        int extras = extrasInventory.nonEmptyItemAmount;
        if (extras < ritualRecipe.extraItems.size()) {
            Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, 4);
            for (IMalumSpecialItemAccessPoint provider : altarProviders) {
                LodestoneBlockEntityInventory inventoryForAltar = provider.getSuppliedInventory();
                ItemStack providedStack = inventoryForAltar.getStackInSlot(0);
                IngredientWithCount requestedItem = ritualRecipe.extraItems.get(extras);
                if (requestedItem.matches(providedStack)) {
                    level.playSound(null, provider.getAccessPointBlockPos(), SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    ParticleEffectTypeRegistry.SPIRIT_ALTAR_EATS_ITEM.createPositionedEffect(level, new PositionEffectData(worldPosition), new ColorEffectData(ritualRecipe.ritualType.spirit), SpiritAltarEatItemParticleEffect.createData(provider.getAccessPointBlockPos(), providedStack));
                    extrasInventory.insertItem(providedStack.split(requestedItem.count));
                    inventoryForAltar.updateData();
                    BlockHelper.updateAndNotifyState(level, provider.getAccessPointBlockPos());
                    break;
                }
            }
            return false;
        }
        return true;
    }

    public void beginCharging() {
        ritualType = ritualRecipe.ritualType;
        ParticleEffectTypeRegistry.SPIRIT_ALTAR_CRAFTS.createPositionedEffect(level, new PositionEffectData(worldPosition), new ColorEffectData(ritualType.spirit));
        level.playSound(null, worldPosition, SoundRegistry.ALTAR_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void eatSpirits() {
        extrasInventory.updateData();
        Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, 4);
        Collection<SpiritJarBlockEntity> jars = BlockHelper.getBlockEntities(SpiritJarBlockEntity.class, level, worldPosition, 4);
        Collection<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(worldPosition).inflate(4)).stream().filter(i-> i.getItem().getItem() instanceof SpiritShardItem).toList();
        int increase = 0;
        final MalumSpiritType spirit = ritualType.spirit;
        for (IMalumSpecialItemAccessPoint altarProvider : altarProviders) {
            ItemStack stack = altarProvider.getSuppliedInventory().getStackInSlot(0);
            if (stack.getItem() instanceof SpiritShardItem spiritShardItem && spiritShardItem.type.equals(spirit)) {
                absorptionProgress.compute(altarProvider, (p, i) -> i == null ? 1 : i + 1);
                if (absorptionProgress.get(altarProvider) >= 5) {
                    final BlockPos accessPointBlockPos = altarProvider.getAccessPointBlockPos();
                    increase += stack.getCount();
                    altarProvider.getSuppliedInventory().setStackInSlot(0, ItemStack.EMPTY);
                    level.playSound(null, accessPointBlockPos, SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    ParticleEffectTypeRegistry.SPIRIT_ALTAR_EATS_ITEM.createPositionedEffect(level, new PositionEffectData(worldPosition), new ColorEffectData(spirit), SpiritAltarEatItemParticleEffect.createData(accessPointBlockPos, stack));
                    absorptionProgress.remove(altarProvider);
                    BlockHelper.updateAndNotifyState(level, accessPointBlockPos);
                }
            }
        }
        for (SpiritJarBlockEntity jar : jars) {
            if (spirit.equals(jar.type) && jar.count > 0) {
                absorptionProgress.compute(jar, (p, i) -> i == null ? 1 : i + 1);
                if (absorptionProgress.get(jar) >= 5) {
                    final BlockPos jarPosition = jar.getBlockPos();
                    final int absorbedAmount = Math.min(jar.count, 64);
                    increase += absorbedAmount;
                    jar.count -= absorbedAmount;
                    if (jar.count == 0) {
                        jar.type = null;
                    }
                    level.playSound(null, jarPosition, SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    ParticleEffectTypeRegistry.SPIRIT_ALTAR_EATS_ITEM.createPositionedEffect(level, new PositionEffectData(worldPosition), new ColorEffectData(spirit), SpiritAltarEatItemParticleEffect.createData(jarPosition, spirit.spiritShard.get().getDefaultInstance()));
                    absorptionProgress.remove(jar);
                    BlockHelper.updateAndNotifyState(level, jarPosition);
                }
            }
        }
        for (ItemEntity itemEntity : itemEntities) {
            final ItemStack item = itemEntity.getItem();
            if (item.getItem() instanceof SpiritShardItem spiritShardItem && spiritShardItem.type.equals(spirit)) {
                absorptionProgress.compute(itemEntity, (p, i) -> i == null ? 1 : i + 1);
                if (absorptionProgress.get(itemEntity) >= 5) {
                    level.playSound(null, itemEntity.blockPosition(), SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    increase += item.getCount();
                    itemEntity.discard();
                    absorptionProgress.remove(itemEntity);
                }
            }
        }
        if (increase != 0) {
            spiritAmount += increase;
            progress = 60;
            BlockHelper.updateAndNotifyState(level, getBlockPos());
        }
    }

    public void completeCharging() {
        inventory.getStackInSlot(0).shrink(ritualRecipe.input.count);
        inventory.updateData();
        extrasInventory.clear();
        ParticleEffectTypeRegistry.SPIRIT_ALTAR_CRAFTS.createPositionedEffect(level, new PositionEffectData(worldPosition), new ColorEffectData(ritualType.spirit));
        level.playSound(null, worldPosition, SoundRegistry.ALTAR_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public Vec3 getParticlePositionPosition(Direction direction) {
        final BlockPos blockPos = getBlockPos();
        float x = blockPos.getX() + 0.5f + direction.getStepX()*0.51f;
        float y = blockPos.getY() + 0.875f;
        float z = blockPos.getZ() + 0.5f + direction.getStepZ()*0.51f;
        return new Vec3(x, y, z);
    }
}
