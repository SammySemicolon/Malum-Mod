package com.sammy.malum.common.block.curiosities.spirit_altar;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.storage.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.altar.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.*;
import org.jetbrains.annotations.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.Nullable;
import javax.annotation.*;
import java.util.*;

public class SpiritAltarBlockEntity extends LodestoneBlockEntity {

    private static final Vec3 ALTAR_ITEM_OFFSET = new Vec3(0.5f, 1.25f, 0.5f);
    private static final int HORIZONTAL_RANGE = 4;
    private static final int VERTICAL_RANGE = 3;

    public float speed = 1f;
    public int progress;
    public float spiritYLevel;

    public List<BlockPos> acceleratorPositions = new ArrayList<>();
    public List<IAltarAccelerator> accelerators = new ArrayList<>();
    public float spiritAmount;
    public float spiritSpin;
    public boolean isCrafting;

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory extrasInventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public List<SpiritInfusionRecipe> possibleRecipes = new ArrayList<>();
    public SpiritInfusionRecipe recipe;

    public LazyOptional<IItemHandler> internalInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory, extrasInventory, spiritInventory));
    public LazyOptional<IItemHandler> exposedInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory, spiritInventory));

    public SpiritAltarBlockEntity(BlockEntityType<? extends SpiritAltarBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SpiritAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SPIRIT_ALTAR.get(), pos, state);

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
        spiritInventory = new MalumBlockEntityInventory(SpiritTypeRegistry.SPIRITS.size(), 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                spiritAmount = Math.max(1, Mth.lerp(0.15f, spiritAmount, nonEmptyItemAmount + 1));
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                if (!(stack.getItem() instanceof SpiritShardItem spiritItem))
                    return false;

                for (int i = 0; i < getSlots(); i++) {
                    if (i != slot) {
                        ItemStack stackInSlot = getStackInSlot(i);
                        if (!stackInSlot.isEmpty() && stackInSlot.getItem() == spiritItem)
                            return false;
                    }
                }
                return true;
            }
        };
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (progress != 0) {
            compound.putInt("progress", progress);
        }
        if (spiritYLevel != 0) {
            compound.putFloat("spiritYLevel", spiritYLevel);
        }
        if (speed != 0) {
            compound.putFloat("speed", speed);
        }
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }
        if (!acceleratorPositions.isEmpty()) {
            compound.putInt("acceleratorAmount", acceleratorPositions.size());
            for (int i = 0; i < acceleratorPositions.size(); i++) {
                BlockHelper.saveBlockPos(compound, acceleratorPositions.get(i), "" + i);
            }
        }

        inventory.save(compound);
        spiritInventory.save(compound, "spiritInventory");
        extrasInventory.save(compound, "extrasInventory");
    }

    @Override
    public void load(CompoundTag compound) {
        progress = compound.getInt("progress");
        spiritYLevel = compound.getFloat("spiritYLevel");
        speed = compound.getFloat("speed");
        spiritAmount = compound.getFloat("spiritAmount");

        acceleratorPositions.clear();
        accelerators.clear();
        int amount = compound.getInt("acceleratorAmount");
        for (int i = 0; i < amount; i++) {
            BlockPos pos = BlockHelper.loadBlockPos(compound, "" + i);
            if (level != null && level.getBlockEntity(pos) instanceof IAltarAccelerator accelerator) {
                acceleratorPositions.add(pos);
                accelerators.add(accelerator);
            }
        }
        inventory.load(compound);
        spiritInventory.load(compound, "spiritInventory");
        extrasInventory.load(compound, "extrasInventory");
        super.load(compound);
    }


    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        extrasInventory.dumpItems(level, worldPosition);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack heldStack = player.getMainHandItem();
            recalibrateAccelerators();
            if (!(heldStack.getItem() instanceof SpiritShardItem)) {
                ItemStack stack = inventory.interact(level, player, hand);
                if (!stack.isEmpty()) {
                    return InteractionResult.SUCCESS;
                }
            }
            spiritInventory.interact(level, player, hand);
            if (heldStack.isEmpty()) {
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.PASS;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    public void init() {
        ItemStack stack = inventory.getStackInSlot(0);
        possibleRecipes = new ArrayList<>(DataHelper.getAll(SpiritInfusionRecipe.getRecipes(level), r -> r.doesInputMatch(stack) && r.doSpiritsMatch(spiritInventory.nonEmptyItemStacks)));
        recipe = SpiritInfusionRecipe.getRecipe(level, stack, spiritInventory.nonEmptyItemStacks);
        if (level.isClientSide && !possibleRecipes.isEmpty() && !isCrafting) {
            AltarSoundInstance.playSound(this);
        }
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (!possibleRecipes.isEmpty()) {
            if (spiritYLevel < 30) {
                spiritYLevel++;
            }
            isCrafting = true;
            progress++;
            if (!level.isClientSide) {
                if (level.getGameTime() % 20L == 0) {
                    boolean canAccelerate = accelerators.stream().allMatch(IAltarAccelerator::canAccelerate);
                    if (!canAccelerate) {
                        recalibrateAccelerators();
                    }
                }
                int progressCap = (int) (300 / speed);
                if (progress >= progressCap) {
                    boolean success = consume();
                    if (success) {
                        craft();
                    }
                }
            }
        } else {
            isCrafting = false;
            progress = 0;
            if (spiritYLevel > 0) {
                this.spiritYLevel = Math.max(spiritYLevel - 0.8f, 0);
            }
        }
        if (level.isClientSide) {
            spiritSpin += 1 + spiritYLevel * 0.05f + speed * 0.5f;
            SpiritAltarParticleEffects.passiveSpiritAltarParticles(this);
        }
    }

    public boolean consume() {
        if (recipe.extraItems.isEmpty()) {
            return true;
        }
        extrasInventory.updateData();
        int extras = extrasInventory.nonEmptyItemAmount;
        if (extras < recipe.extraItems.size()) {
            progress *= 0.8f;
            Collection<IMalumSpecialItemAccessPoint> altarProviders = BlockHelper.getBlockEntities(IMalumSpecialItemAccessPoint.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
            for (IMalumSpecialItemAccessPoint provider : altarProviders) {
                LodestoneBlockEntityInventory inventoryForAltar = provider.getSuppliedInventory();
                ItemStack providedStack = inventoryForAltar.getStackInSlot(0);
                IngredientWithCount requestedItem = recipe.extraItems.get(extras);
                boolean matches = requestedItem.matches(providedStack);
                if (!matches) {
                    for (SpiritInfusionRecipe recipe : possibleRecipes) {
                        if (extras < recipe.extraItems.size() && recipe.extraItems.get(extras).matches(providedStack)) {
                            this.recipe = recipe;
                            break;
                        }
                    }
                }
                requestedItem = recipe.extraItems.get(extras);
                matches = requestedItem.matches(providedStack);
                if (matches) {
                    level.playSound(null, provider.getAccessPointBlockPos(), SoundRegistry.ALTAR_CONSUME.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
                    ParticleEffectTypeRegistry.SPIRIT_ALTAR_EATS_ITEM.createPositionedEffect(level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits), SpiritAltarEatItemParticleEffect.createData(provider.getAccessPointBlockPos(), providedStack));
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

    public void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack outputStack = recipe.output.copy();
        Vec3 itemPos = getItemPos();
        if (recipe.useNbtFromInput && inventory.getStackInSlot(0).hasTag()) {
            outputStack.setTag(stack.getTag());
        }
        stack.shrink(recipe.input.count);
        inventory.updateData();
        for (SpiritWithCount spirit : recipe.spirits) {
            for (int i = 0; i < spiritInventory.slotCount; i++) {
                ItemStack spiritStack = spiritInventory.getStackInSlot(i);
                if (spirit.matches(spiritStack)) {
                    spiritStack.shrink(spirit.count);
                    break;
                }
            }
        }
        spiritInventory.updateData();
        ParticleEffectTypeRegistry.SPIRIT_ALTAR_CRAFTS.createPositionedEffect(level, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits));
        progress *= 0.75f;
        extrasInventory.clear();
        level.playSound(null, worldPosition, SoundRegistry.ALTAR_CRAFT.get(), SoundSource.BLOCKS, 1, 0.9f + level.random.nextFloat() * 0.2f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        init();
        recalibrateAccelerators();
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    public void recalibrateAccelerators() {
        speed = 1f;
        accelerators.clear();
        acceleratorPositions.clear();
        Collection<IAltarAccelerator> nearbyAccelerators = BlockHelper.getBlockEntities(IAltarAccelerator.class, level, worldPosition, HORIZONTAL_RANGE, VERTICAL_RANGE, HORIZONTAL_RANGE);
        Map<IAltarAccelerator.AltarAcceleratorType, Integer> entries = new HashMap<>();
        for (IAltarAccelerator accelerator : nearbyAccelerators) {
            if (accelerator.canAccelerate()) {
                int max = accelerator.getAcceleratorType().maximumEntries();
                int amount = entries.computeIfAbsent(accelerator.getAcceleratorType(), (a) -> 0);
                if (amount < max) {
                    accelerators.add(accelerator);
                    acceleratorPositions.add(((BlockEntity) accelerator).getBlockPos());
                    speed += accelerator.getAcceleration();
                    entries.replace(accelerator.getAcceleratorType(), amount + 1);
                }
            }
        }
    }

    public float getSpinUp(Easing easing) {
        if (spiritYLevel > 30) {
            return 1;
        }
        return easing.ease(spiritYLevel / 30f, 0, 1, 1);
    }

    public Vec3 getItemPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getCentralItemOffset();
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
    }

    public Vec3 getCentralItemOffset() {
        return ALTAR_ITEM_OFFSET;
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float distance = 1 - getSpinUp(Easing.SINE_OUT) * 0.25f + (float) Math.sin((spiritSpin % 6.2831f + partialTicks) / 20f) * 0.025f;
        float height = 0.75f + getSpinUp(Easing.QUARTIC_OUT) * getSpinUp(Easing.BACK_OUT) * 0.5f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (spiritSpin + partialTicks), 360);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null)
                return internalInventory.cast();
            return exposedInventory.cast();
        }
        return super.getCapability(cap, side);
    }
}
