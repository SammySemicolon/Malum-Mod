package com.sammy.malum.common.block.curiosities.ritual_plinth;

import com.sammy.malum.common.block.MalumBlockEntityInventory;
import com.sammy.malum.common.block.storage.IMalumSpecialItemAccessPoint;
import com.sammy.malum.common.block.storage.jar.SpiritJarBlockEntity;
import com.sammy.malum.common.item.spirit.RitualShardItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.systems.ritual.MalumRitualRecipeData;
import com.sammy.malum.core.systems.ritual.MalumRitualTier;
import com.sammy.malum.core.systems.ritual.MalumRitualType;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.RitualRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.visual_effects.RitualPlinthParticleEffects;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import com.sammy.malum.visual_effects.networked.ritual.RitualPlinthAbsorbItemParticleEffect;
import io.github.fabricators_of_create.porting_lib.block.CustomRenderBoundingBoxBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RitualPlinthBlockEntity extends LodestoneBlockEntity implements CustomRenderBoundingBoxBlockEntity {

    private static final Vec3 PLINTH_ITEM_OFFSET = new Vec3(0.5f, 1.375f, 0.5f);

    public MalumRitualType ritualType;
    public MalumRitualTier ritualTier;
    public int spiritAmount;
    public int progress;
    public float activeDuration;
    public boolean setupComplete;
    public Map<Object, Integer> absorptionProgress = new HashMap<>();

    public MalumRitualRecipeData ritualRecipe;
    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory extrasInventory;

    public RitualPlinthBlockEntity(BlockEntityType<? extends RitualPlinthBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RitualPlinthBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RITUAL_PLINTH.get(), pos, state);

        inventory = new MalumBlockEntityInventory(1, 64, t -> (ritualType != null && ritualType.isItemStackValid(this, t)) || (ritualType == null && !(t.getItem() instanceof SpiritShardItem))) {
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
        if (setupComplete) {
            compound.putBoolean("setupComplete", true);
        }
        if (activeDuration != 0) {
            compound.putFloat("activeDuration", activeDuration);
        }
        if (ritualType != null) {
            compound.putString("ritualType", ritualType.identifier.toString());
        }
        compound.put("Inventory", inventory.serializeNBT());
        compound.put("extrasInventory", extrasInventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag compound) {
        spiritAmount = compound.getInt("spiritAmount");
        progress = compound.getInt("progress");
        setupComplete = compound.getBoolean("setupComplete");
        activeDuration = compound.getFloat("activeDuration");
        ritualType = RitualRegistry.get(new ResourceLocation(compound.getString("ritualType")));
        ritualTier = MalumRitualTier.figureOutTier(spiritAmount);

        inventory.deserializeNBT(compound.getCompound("Inventory"));
        extrasInventory.deserializeNBT(compound.getCompound("extrasInventory"));
        super.load(compound);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        extrasInventory.dumpItems(level, worldPosition);
        if (ritualType != null && ritualTier != null) {
            ItemStack shard = new ItemStack(ItemRegistry.RITUAL_SHARD.get());
            shard.setTag(ritualType.createShardNBT(ritualTier));
            level.addFreshEntity(new ItemEntity(level, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), shard));
        }
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (ritualType != null) {
            InteractionResult interactionResult = ritualType.onUsePlinth(this, player, hand);
            if (!interactionResult.equals(InteractionResult.PASS)) {
                return interactionResult;
            }
        } else if (inventory.getStackInSlot(0).isEmpty() && extrasInventory.isEmpty()) {
            ItemStack stack = player.getItemInHand(hand);
            if (stack.getItem() instanceof RitualShardItem) {
                if (!level.isClientSide) {
                    ritualType = RitualShardItem.getRitualType(stack);
                    ritualTier = RitualShardItem.getRitualTier(stack);
                    spiritAmount = ritualTier.spiritThreshold;
                    setupComplete = true;
                    if (level instanceof ServerLevel serverLevel) {
                        ParticleEffectTypeRegistry.RITUAL_PLINTH_BEGINS_CHARGING.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(ritualType.spirit));
                    }
                    level.playSound(null, getBlockPos(), SoundRegistry.RITUAL_BEGINS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    level.playSound(null, getBlockPos(), SoundRegistry.RITUAL_COMPLETED.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    player.setItemInHand(hand, ItemStack.EMPTY);
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
                return InteractionResult.SUCCESS;
            }
        }
        inventory.interact(this, level, player, hand, stack -> true);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void init() {
        ItemStack stack = inventory.getStackInSlot(0);
        boolean wasLackingRecipe = ritualRecipe == null;
        ritualRecipe = RitualRegistry.RITUALS.stream().map(MalumRitualType::getRecipeData).filter(recipeData -> recipeData != null && recipeData.input.matches(stack)).findAny().orElse(null);
        if (ritualType == null && wasLackingRecipe && ritualRecipe != null) {
            level.playSound(null, getBlockPos(), SoundRegistry.RITUAL_BEGINS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        }
        if (level.isClientSide) {
            Supplier<SoundEvent> soundEvent = null;
            Predicate<RitualPlinthBlockEntity> stopCondition = null;
            if (setupComplete) {
                soundEvent = SoundRegistry.COMPLETED_RITUAL_AMBIENCE;
                stopCondition = p -> false;
            } else if (ritualType != null) {
                soundEvent = SoundRegistry.RITUAL_EVOLUTION_AMBIENCE;
                stopCondition = p -> p.setupComplete || p.ritualType == null;
            } else if (wasLackingRecipe && ritualRecipe != null) {
                soundEvent = SoundRegistry.RITUAL_BEGINNING_AMBIENCE;
                stopCondition = p -> p.ritualRecipe == null || p.ritualType != null;
            }
            if (soundEvent != null) {
                RitualSoundInstance.playSound(this, soundEvent, stopCondition);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (ritualType != null) {
            activeDuration++;
            if (progress > 0) {
                progress--;
                if (!level.isClientSide) {
                    eatSpirits();
                    if (progress == 0) {
                        completeCharging();
                    }
                }
            } else if (!level.isClientSide) {
                ritualType.triggerRitualEffect(this);
            }
        } else if (ritualRecipe != null) {
            progress++;
            if (!level.isClientSide) {
                if (progress > 60) {
                    boolean success = consume();
                    if (success) {
                        beginCharging();
                    } else {
                        progress = 0;
                    }
                }
            }
        } else {
            progress = 0;
        }
        if (level.isClientSide) {
            final ItemStack stack = inventory.getStackInSlot(0);
            if (!stack.isEmpty()) {
                boolean isItemValid = ritualType != null ? ritualType.isItemStackValid(this, stack) : ritualRecipe != null && ritualRecipe.input.matches(stack);
                if (isItemValid) {
                    RitualPlinthParticleEffects.holdingPrimeItemPlinthParticles(this);
                }
            }
            if (ritualType != null) {
                RitualPlinthParticleEffects.riteActivePlinthParticles(this);
            }
        }
    }

    public boolean consume() {
        if (ritualRecipe.extraItems.isEmpty()) {
            return true;
        }
        extrasInventory.setChanged();
        int extras = extrasInventory.nonEmptyItemAmount;
        if (extras < ritualRecipe.extraItems.size()) {
            Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, 4);
            for (IMalumSpecialItemAccessPoint provider : altarProviders) {
                LodestoneBlockEntityInventory inventoryForAltar = provider.getSuppliedInventory();
                ItemStack providedStack = inventoryForAltar.getStackInSlot(0);
                IngredientWithCount requestedItem = ritualRecipe.extraItems.get(extras);
                if (requestedItem.matches(providedStack)) {
                    level.playSound(null, provider.getAccessPointBlockPos(), SoundRegistry.RITUAL_ABSORBS_ITEM.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    if (level instanceof ServerLevel serverLevel) {
                        ParticleEffectTypeRegistry.RITUAL_PLINTH_EATS_ITEM.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(ritualRecipe.ritualType.spirit), RitualPlinthAbsorbItemParticleEffect.createData(provider.getItemPos(), providedStack));
                    }
                    try (Transaction t = TransferUtil.getTransaction()) {
                        long res = extrasInventory.insert(ItemVariant.of(providedStack.split(requestedItem.count)), 64, t);
                        t.commit();
                    }
                    extrasInventory.setChanged();
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
        inventory.getStackInSlot(0).shrink(ritualRecipe.input.count);
        inventory.setChanged();
        extrasInventory.clear();
        if (level instanceof ServerLevel serverLevel) {
            ParticleEffectTypeRegistry.RITUAL_PLINTH_BEGINS_CHARGING.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(ritualType.spirit));
        }

        level.playSound(null, worldPosition, SoundRegistry.RITUAL_FORMS.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void eatSpirits() {
        extrasInventory.setChanged();
        Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, 4);
        Collection<SpiritJarBlockEntity> jars = BlockHelper.getBlockEntities(SpiritJarBlockEntity.class, level, worldPosition, 4);
        Collection<ItemEntity> itemEntities = level.getEntitiesOfClass(ItemEntity.class, new AABB(worldPosition).inflate(4)).stream().filter(i -> i.getItem().getItem() instanceof SpiritShardItem).toList();
        int increase = 0;
        final MalumSpiritType spirit = ritualType.spirit;
        for (IMalumSpecialItemAccessPoint altarProvider : altarProviders) {
            LodestoneBlockEntityInventory providerInventory = altarProvider.getSuppliedInventory();
            ItemStack stack = providerInventory.getStackInSlot(0);
            if (stack.getItem() instanceof SpiritShardItem spiritShardItem && spiritShardItem.type.equals(spirit)) {
                absorptionProgress.compute(altarProvider, (p, i) -> i == null ? 1 : i + 1);
                if (absorptionProgress.get(altarProvider) >= 5) {
                    final BlockPos accessPointBlockPos = altarProvider.getAccessPointBlockPos();
                    final int absorbedAmount = Math.min(stack.getCount(), ritualTier == null ? 4 : ritualTier.spiritThreshold / 16);
                    increase += absorbedAmount;
                    stack.shrink(absorbedAmount);
                    providerInventory.setChanged();
                    level.playSound(null, accessPointBlockPos, SoundRegistry.RITUAL_ABSORBS_SPIRIT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    if (level instanceof ServerLevel serverLevel) {
                        ParticleEffectTypeRegistry.RITUAL_PLINTH_EATS_SPIRIT.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(spirit), RitualPlinthAbsorbItemParticleEffect.createData(altarProvider.getItemPos(), stack));
                    }
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
                    final int absorbedAmount = Math.min(jar.count, ritualTier == null ? 4 : ritualTier.spiritThreshold / 16);
                    increase += absorbedAmount;
                    jar.count -= absorbedAmount;
                    if (jar.count == 0) {
                        jar.type = null;
                    }
                    level.playSound(null, jarPosition, SoundRegistry.RITUAL_ABSORBS_SPIRIT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    if (level instanceof ServerLevel serverLevel) {
                        ParticleEffectTypeRegistry.RITUAL_PLINTH_EATS_SPIRIT.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(spirit), RitualPlinthAbsorbItemParticleEffect.createData(jar.getItemPos(), spirit.spiritShard.get().getDefaultInstance()));
                    }
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
                    level.playSound(null, itemEntity.blockPosition(), SoundRegistry.RITUAL_ABSORBS_SPIRIT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    if (level instanceof ServerLevel serverLevel) {
                        ParticleEffectTypeRegistry.RITUAL_PLINTH_EATS_SPIRIT.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(spirit), RitualPlinthAbsorbItemParticleEffect.createData(itemEntity.position().add(0, itemEntity.getBbHeight() / 2f, 0), spirit.spiritShard.get().getDefaultInstance()));
                    }
                    increase += item.getCount();
                    itemEntity.discard();
                    absorptionProgress.remove(itemEntity);
                }
            }
        }
        if (increase != 0) {
            spiritAmount += increase;
            progress = 60;
            final MalumRitualTier oldTier = ritualTier;
            final MalumRitualTier newTier = MalumRitualTier.figureOutTier(spiritAmount);
            if (newTier != null && !newTier.equals(oldTier)) {
                ritualTier = newTier;
                level.playSound(null, worldPosition, SoundRegistry.RITUAL_EVOLVES.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                if (level instanceof ServerLevel serverLevel) {
                    ParticleEffectTypeRegistry.RITUAL_PLINTH_CHANGES_TIER.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(spirit));
                }
            }
            BlockHelper.updateAndNotifyState(level, getBlockPos());
        }
    }

    public void completeCharging() {
        ritualTier = MalumRitualTier.figureOutTier(this);
        setupComplete = ritualTier != null;
        if (!setupComplete) {
            final MalumSpiritType spirit = ritualType.spirit;
            ritualType = null;
            spiritAmount = 0;
            if (level instanceof ServerLevel serverLevel) {
                ParticleEffectTypeRegistry.RITUAL_PLINTH_FAILURE.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), new ColorEffectData(spirit));
            }
        }
        level.playSound(null, getBlockPos(), SoundRegistry.RITUAL_COMPLETED.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public Vec3 getItemPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getCentralItemOffset();
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
    }

    public Vec3 getCentralItemOffset() {
        return PLINTH_ITEM_OFFSET;
    }

    public Vec3 getRitualIconPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getRitualIconOffset(0f);
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
    }

    public Vec3 getRitualIconOffset(float partialTicks) {
        return new Vec3(0.5f, 1.5f + Easing.CUBIC_OUT.ease(Math.min(activeDuration + partialTicks, 30) / 30f, 0, 2, 1), 0.5f);
    }

    public Vec3 getParticlePositionPosition(Direction direction) {
        final BlockPos blockPos = getBlockPos();
        float x = blockPos.getX() + 0.5f + direction.getStepX() * 0.51f;
        float y = blockPos.getY() + 0.875f;
        float z = blockPos.getZ() + 0.5f + direction.getStepZ() * 0.51f;
        return new Vec3(x, y, z);
    }

    @Override
    public AABB getRenderBoundingBox() {
        var pos = worldPosition;
        return new AABB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 6, pos.getZ() + 1);
    }
}
