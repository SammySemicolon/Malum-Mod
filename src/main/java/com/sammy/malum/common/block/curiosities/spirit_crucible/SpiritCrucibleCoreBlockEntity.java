package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.item.catalyzer_augment.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
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
import team.lodestar.lodestone.systems.multiblock.*;

import javax.annotation.Nullable;
import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class SpiritCrucibleCoreBlockEntity extends MultiBlockCoreEntity implements ICatalyzerAccelerationTarget {

    private static final Vec3 CRUCIBLE_ITEM_OFFSET = new Vec3(0.5f, 1.6f, 0.5f);
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CRUCIBLE_COMPONENT.get().defaultBlockState())));

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public LodestoneBlockEntityInventory augmentInventory;
    public SpiritFocusingRecipe recipe;

    public float spiritAmount;
    public float spiritSpin;

    public float progress;

    public int wexingEngineInfluence;
    public int mendingDiffuserCounter;

    public int queuedCracks;
    public int crackTimer;

    public CrucibleAccelerationData acceleratorData = CrucibleAccelerationData.DEFAULT;
    public CrucibleTuning.CrucibleTuningType tuningType = CrucibleTuning.CrucibleTuningType.NONE;

    private final LazyOptional<IItemHandler> combinedInventory = LazyOptional.of(() -> new CombinedInvWrapper(inventory, spiritInventory));

    public SpiritCrucibleCoreBlockEntity(BlockEntityType<? extends SpiritCrucibleCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new MalumBlockEntityInventory(1, 1, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        spiritInventory = new MalumBlockEntityInventory(4, 64) {
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
        augmentInventory = new MalumBlockEntityInventory(4, 1, t -> t.getItem() instanceof AbstractAugmentItem) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }

            @Override
            public SoundEvent getInsertSound(ItemStack stack) {
                return SoundRegistry.APPLY_AUGMENT.get();
            }

            @Override
            public SoundEvent getExtractSound(ItemStack stack) {
                return SoundRegistry.REMOVE_AUGMENT.get();
            }
        };
    }

    public SpiritCrucibleCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CRUCIBLE.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (spiritAmount != 0) {
            compound.putFloat("spiritAmount", spiritAmount);
        }
        if (progress != 0) {
            compound.putFloat("progress", progress);
        }
        if (queuedCracks != 0) {
            compound.putInt("queuedCracks", queuedCracks);
        }
        if (wexingEngineInfluence != 0) {
            compound.putInt("wexingEngineInfluence", wexingEngineInfluence);
        }
        if (mendingDiffuserCounter != 0) {
            compound.putInt("mendingDiffuserCounter", mendingDiffuserCounter);
        }

        compound.putInt("tuningType", tuningType.ordinal());
        acceleratorData.save(compound);


        inventory.save(compound);
        spiritInventory.save(compound, "spiritInventory");
        augmentInventory.save(compound, "augmentInventory");
    }

    @Override
    public void load(CompoundTag compound) {
        spiritAmount = compound.getFloat("spiritAmount");
        progress = compound.getFloat("progress");
        queuedCracks = compound.getInt("queuedCracks");
        wexingEngineInfluence = compound.getInt("wexingEngineInfluence");
        mendingDiffuserCounter = compound.getInt("mendingDiffuserCounter");

        tuningType = CrucibleTuning.CrucibleTuningType.values()[Mth.clamp(compound.getInt("tuningType"), 0, CrucibleTuning.CrucibleTuningType.values().length-1)];
        acceleratorData = CrucibleAccelerationData.load(level, this, compound);

        inventory.load(compound);
        spiritInventory.load(compound, "spiritInventory");
        augmentInventory.load(compound, "augmentInventory");

        super.load(compound);
    }

    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            ItemStack heldStack = player.getMainHandItem();
            if (heldStack.getItem().equals(ItemRegistry.TUNING_FORK.get())) {
                tuningType = tuningType.next(tuningType, this);
                recalibrateAccelerators(level, worldPosition);
                level.playSound(null, worldPosition, SoundRegistry.TUNING_FORK_TINKERS.get(), SoundSource.BLOCKS, 1.25f+level.random.nextFloat()*0.5f, 0.75f+level.random.nextFloat()*0.5f);
                BlockHelper.updateAndNotifyState(level, worldPosition);
                float f=  0;
                return InteractionResult.SUCCESS;
            }
            final boolean augmentOnly = heldStack.getItem() instanceof AbstractAugmentItem;
            if (augmentOnly || (heldStack.isEmpty() && inventory.isEmpty() && spiritInventory.isEmpty())) {
                ItemStack stack = augmentInventory.interact(player.level(), player, hand);
                if (!stack.isEmpty()) {
                    recalibrateAccelerators(level, worldPosition);
                    return InteractionResult.SUCCESS;
                }
            }
            recalibrateAccelerators(level, worldPosition);
            if (!augmentOnly) {
                ItemStack spiritStack = spiritInventory.interact(level, player, hand);
                if (!spiritStack.isEmpty()) {
                    return InteractionResult.SUCCESS;
                }
                if (!(heldStack.getItem() instanceof SpiritShardItem)) {
                    ItemStack stack = inventory.interact(level, player, hand);
                    if (!stack.isEmpty()) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (heldStack.isEmpty()) {
                return InteractionResult.SUCCESS;
            } else {
                return InteractionResult.FAIL;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, worldPosition);
        spiritInventory.dumpItems(level, worldPosition);
        augmentInventory.dumpItems(level, worldPosition);
        super.onBreak(player);
    }

    @Override
    public void init() {
        if (level.isClientSide && recipe == null) {
            CrucibleSoundInstance.playSound(this);
        }
        recipe = SpiritFocusingRecipe.getRecipe(level, inventory.getStackInSlot(0), spiritInventory.nonEmptyItemStacks);
    }

    @Override
    public void tick() {
        super.tick();
        spiritAmount = Math.max(1, Mth.lerp(0.1f, spiritAmount, spiritInventory.nonEmptyItemAmount));
        if (queuedCracks > 0) {
            crackTimer++;
            if (crackTimer % 5 == 0) {
                if (crackTimer >= 15) {
                    crackTimer = 0;
                }
                float pitch = 0.95f + (crackTimer - 8) * 0.015f + level.random.nextFloat() * 0.05f;
                level.playSound(null, worldPosition, SoundRegistry.IMPETUS_CRACK.get(), SoundSource.BLOCKS, 0.7f, pitch);
                queuedCracks--;
                if (queuedCracks == 0) {
                    crackTimer = 0;
                }
            }
        }
        float speed = acceleratorData.processingSpeed.getValue(acceleratorData);
        if (level instanceof ServerLevel) {
            if (recipe != null) {
                boolean needsRecalibration = !acceleratorData.accelerators.stream().allMatch(ICrucibleAccelerator::canContinueAccelerating);
                if (needsRecalibration) {
                    recalibrateAccelerators(level, worldPosition);
                    BlockHelper.updateAndNotifyState(level, worldPosition);
                }
                progress += speed;
                if (progress >= recipe.time) {
                    craft();
                }
            } else {
                progress = 0;
                wexingEngineInfluence = 0;
            }
        }
        else {
            spiritSpin += 1 + speed * 0.1f;
            SpiritCrucibleParticleEffects.passiveCrucibleParticles(this);
        }
    }

    public void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack outputStack = recipe.output.copy();
        Vec3 itemPos = getItemPos();
        final RandomSource random = level.random;
        float processingSpeed = acceleratorData.processingSpeed.getValue(acceleratorData);
        float damageChance = acceleratorData.damageChance.getValue(acceleratorData);
        float bonusYieldChance = acceleratorData.bonusYieldChance.getValue(acceleratorData);
        float instantCompletionChance = acceleratorData.chainFocusingChance.getValue(acceleratorData);
        float completeDamageNegationChance = acceleratorData.damageAbsorptionChance.getValue(acceleratorData);
        float restorationChance = acceleratorData.restorationChance.getValue(acceleratorData);
        if (random.nextFloat() < restorationChance) {
            stack.setDamageValue(Math.max(stack.getDamageValue()-recipe.durabilityCost*2, 0));
        }
        if (completeDamageNegationChance == 0 || random.nextFloat() < completeDamageNegationChance) {
            if (recipe.durabilityCost != 0 && stack.isDamageableItem()) {
                int durabilityCost = recipe.durabilityCost;
                if (damageChance > 0 && random.nextFloat() < damageChance) {
                    durabilityCost *= 2;
                }

                queuedCracks += durabilityCost;
                boolean success = stack.hurt(durabilityCost, random, null);
                if (success && stack.getItem() instanceof ImpetusItem impetusItem) {
                    inventory.setStackInSlot(0, impetusItem.getCrackedVariant().getDefaultInstance());
                }
            }
        }
        else {
            level.playSound(null, worldPosition, SoundRegistry.SHIELDING_APPARATUS_SHIELDS.get(), SoundSource.BLOCKS, 0.5f, 0.25f + random.nextFloat() * 0.25f);
        }
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
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        if (random.nextFloat() < bonusYieldChance) {
            level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack.copy()));
        }
        final boolean instantCompletion = instantCompletionChance > 0 && random.nextFloat() < instantCompletionChance;
        progress = instantCompletion ? recipe.time - 10 * processingSpeed : 0;
        if (instantCompletion) {
            level.playSound(null, worldPosition, SoundRegistry.WARPING_ENGINE_REVERBERATES.get(), SoundSource.BLOCKS, 1.5f, 1f + random.nextFloat() * 0.25f);
        }
        acceleratorData.globalAttributeModifier = instantCompletion ? acceleratorData.globalAttributeModifier + 0.2f : 0;
        recipe = SpiritFocusingRecipe.getRecipe(level, stack, spiritInventory.nonEmptyItemStacks);
        level.playSound(null, worldPosition, SoundRegistry.CRUCIBLE_CRAFT.get(), SoundSource.BLOCKS, 1, 0.75f + random.nextFloat() * 0.5f);
        BlockHelper.updateAndNotifyState(level, worldPosition);
    }

    @Override
    public Vec3 getAccelerationPoint() {
        return getItemPos();
    }

    @Override
    public List<ItemStack> getAugments() {
        return augmentInventory.nonEmptyItemStacks;
    }

    @Override
    public boolean canBeAccelerated() {
        return !isRemoved() && recipe != null;
    }

    @Override
    public CrucibleAccelerationData getAccelerationData() {
        return acceleratorData;
    }

    @Override
    public void setAccelerationData(CrucibleAccelerationData data) {
        this.acceleratorData = data;
    }

    @Override
    public CrucibleTuning.CrucibleTuningType getTuningType() {
        return tuningType;
    }

    @Override
    public float getWexingEngineInfluence() {
        return wexingEngineInfluence;
    }

    @Override
    public MalumSpiritType getActiveSpiritType() {
        int spiritCount = spiritInventory.nonEmptyItemAmount;
        Item currentItem = spiritInventory.getStackInSlot(0).getItem();
        if (spiritCount > 1) {
            float duration = 60f * spiritCount;
            float gameTime = (getLevel().getGameTime() % duration) / 60f;
            currentItem = spiritInventory.getStackInSlot(Mth.floor(gameTime)).getItem();
        }
        if (!(currentItem instanceof SpiritShardItem spiritItem)) {
            return null;
        }
        return spiritItem.type;
    }

    public Vec3 getItemPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getCentralItemOffset();
        return new Vec3(blockPos.getX()+offset.x, blockPos.getY()+offset.y, blockPos.getZ()+offset.z);
    }

    public Vec3 getCentralItemOffset() {
        return CRUCIBLE_ITEM_OFFSET;
    }

    public Vec3 getSpiritItemOffset(int slot, float partialTicks) {
        float distance = 0.75f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 1.8f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, spiritAmount, (long) (spiritSpin + partialTicks), 360);
    }

    public Vec3 getAugmentItemOffset(int slot, float partialTicks) {
        float distance = 0.6f + (float) Math.sin(((spiritSpin + partialTicks) % 6.28f) / 20f) * 0.025f;
        float height = 1.6f;
        return DataHelper.rotatingRadialOffset(new Vec3(0.5f, height, 0.5f), distance, slot, augmentInventory.slotCount, (long) (spiritSpin + partialTicks), 240);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return combinedInventory.cast();
        }
        return super.getCapability(cap, side);
    }
}
