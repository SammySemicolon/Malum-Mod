package com.sammy.malum.common.block.curiosities.spirit_crucible;

import com.sammy.malum.common.block.MalumBlockEntityInventory;
import com.sammy.malum.common.item.augment.AbstractAugmentItem;
import com.sammy.malum.common.item.augment.core.AbstractCoreAugmentItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.item.ItemRegistry;
import com.sammy.malum.visual_effects.SpiritCrucibleParticleEffects;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import java.util.List;
import java.util.function.Supplier;

public class SpiritCrucibleCoreBlockEntity extends MultiBlockCoreEntity implements ICatalyzerAccelerationTarget {

    private static final Vec3 CRUCIBLE_ITEM_OFFSET = new Vec3(0.5f, 1.6f, 0.5f);
    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CRUCIBLE_COMPONENT.get().defaultBlockState())));

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public LodestoneBlockEntityInventory augmentInventory;
    public LodestoneBlockEntityInventory coreAugmentInventory;
    public SpiritFocusingRecipe recipe;

    public float spiritAmount;
    public float spiritSpin;

    public float progress;

    public int queuedCracks;
    public int crackTimer;

    public CrucibleAccelerationData acceleratorData = CrucibleAccelerationData.DEFAULT;
    public CrucibleTuning.CrucibleAttributeType tuningType = CrucibleTuning.CrucibleAttributeType.NONE;

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
            public boolean isItemValid(int slot, ItemVariant resource, int count) {
                if (!(resource.getItem() instanceof SpiritShardItem spiritItem))
                    return false;

                for (int i = 0; i < getSlots().size(); i++) {
                    if (i != slot) {
                        ItemStack stackInSlot = getStackInSlot(i);
                        if (!stackInSlot.isEmpty() && stackInSlot.getItem() == spiritItem)
                            return false;
                    }
                }
                return true;
            }
        };
        augmentInventory = new AugmentBlockEntityInventory(4, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        coreAugmentInventory = new AugmentBlockEntityInventory(1, 1, t -> t.getItem() instanceof AbstractCoreAugmentItem) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
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

        compound.putInt("tuningType", tuningType.ordinal());
        acceleratorData.save(compound);
        compound.put("Inventory", inventory.serializeNBT());
        compound.put("spiritInventory", spiritInventory.serializeNBT());
        compound.put("augmentInventory", augmentInventory.serializeNBT());
        compound.put("coreAugmentInventory", coreAugmentInventory.serializeNBT());
    }

    @Override
    public void load(CompoundTag compound) {
        spiritAmount = compound.getFloat("spiritAmount");
        progress = compound.getFloat("progress");
        queuedCracks = compound.getInt("queuedCracks");

        tuningType = CrucibleTuning.CrucibleAttributeType.values()[Mth.clamp(compound.getInt("tuningType"), 0, CrucibleTuning.CrucibleAttributeType.values().length - 1)];
        acceleratorData = CrucibleAccelerationData.load(level, this, compound);

        inventory.deserializeNBT(compound.getCompound("Inventory"));
        spiritInventory.deserializeNBT(compound.getCompound("spiritInventory"));
        augmentInventory.deserializeNBT(compound.getCompound("augmentInventory"));
        coreAugmentInventory.deserializeNBT(compound.getCompound("coreAugmentInventory"));

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
                level.playSound(null, worldPosition, SoundRegistry.TUNING_FORK_TINKERS.get(), SoundSource.BLOCKS, 1.25f + level.random.nextFloat() * 0.5f, 0.75f + level.random.nextFloat() * 0.5f);
                BlockHelper.updateAndNotifyState(level, worldPosition);
                return InteractionResult.SUCCESS;
            }
            final boolean augmentOnly = heldStack.getItem() instanceof AbstractAugmentItem;
            final boolean isEmpty = heldStack.isEmpty();
            if (augmentOnly || (isEmpty && inventory.isEmpty() && spiritInventory.isEmpty())) {
                final boolean isCoreAugment = heldStack.getItem() instanceof AbstractCoreAugmentItem;
                if ((augmentOnly && !isCoreAugment) || isEmpty) {
                    try (Transaction t = TransferUtil.getTransaction()) {
                        long inserted = augmentInventory.insert(ItemVariant.of(heldStack), 64, t);
                        heldStack.shrink((int) inserted);
                        setChanged();
                        t.commit();

                        if (inserted > 0) {
                            recalibrateAccelerators(level, worldPosition);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
                if ((augmentOnly && isCoreAugment) || isEmpty) {
                    try (Transaction t = TransferUtil.getTransaction()) {
                        long inserted = coreAugmentInventory.insert(ItemVariant.of(heldStack), 64, t);
                        heldStack.shrink((int) inserted);
                        setChanged();
                        t.commit();

                        if (inserted > 0) {
                            recalibrateAccelerators(level, worldPosition);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
            recalibrateAccelerators(level, worldPosition);
            if (!augmentOnly) {
                try (Transaction t = TransferUtil.getTransaction()) {
                    long inserted = spiritInventory.insert(ItemVariant.of(heldStack), 64, t);
                    heldStack.shrink((int) inserted);
                    setChanged();
                    t.commit();

                    if (inserted > 0) {
                        return InteractionResult.SUCCESS;
                    }
                }
                if (!(heldStack.getItem() instanceof SpiritShardItem)) {
                    try (Transaction t = TransferUtil.getTransaction()) {
                        long inserted = inventory.insert(ItemVariant.of(heldStack), 64, t);
                        heldStack.shrink((int) inserted);
                        setChanged();
                        t.commit();

                        if (inserted > 0) {
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
            if (isEmpty) {
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
        if (level != null && level.isClientSide && recipe == null) {
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
        float speed = acceleratorData.focusingSpeed.getValue(acceleratorData);
        if (level != null && !level.isClientSide) {
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
            }
        } else {
            spiritSpin += 1 + speed * 0.1f;
            SpiritCrucibleParticleEffects.passiveCrucibleParticles(this);
        }
    }

    public void craft() {
        ItemStack stack = inventory.getStackInSlot(0);
        ItemStack outputStack = recipe.output.copy();
        Vec3 itemPos = getItemPos();
        final RandomSource random = level.random;
        float processingSpeed = acceleratorData.focusingSpeed.getValue(acceleratorData);
        float damageChance = acceleratorData.damageChance.getValue(acceleratorData);
        float bonusYieldChance = acceleratorData.bonusYieldChance.getValue(acceleratorData);
        float instantCompletionChance = acceleratorData.chainFocusingChance.getValue(acceleratorData);
        float completeDamageNegationChance = acceleratorData.damageAbsorptionChance.getValue(acceleratorData);
        float restorationChance = acceleratorData.restorationChance.getValue(acceleratorData);
        if (random.nextFloat() < restorationChance) {
            stack.setDamageValue(Math.max(stack.getDamageValue() - recipe.durabilityCost * 4, 0));
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
        } else {
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

        spiritInventory.setChanged();
        final boolean instantCompletion = instantCompletionChance > 0 && random.nextFloat() < instantCompletionChance;
        progress = instantCompletion ? recipe.time - 10 * processingSpeed : 0;
        if (instantCompletion) {
            level.playSound(null, worldPosition, SoundRegistry.WARPING_ENGINE_REVERBERATES.get(), SoundSource.BLOCKS, 1.5f, 1f + random.nextFloat() * 0.25f);
        }
        acceleratorData.globalAttributeModifier = instantCompletion ? acceleratorData.globalAttributeModifier + 0.2f : 0;
        if (level instanceof ServerLevel serverLevel) {
            ParticleEffectTypeRegistry.SPIRIT_CRUCIBLE_CRAFTS.createPositionedEffect(serverLevel, new PositionEffectData(worldPosition), ColorEffectData.fromRecipe(recipe.spirits));
        }
        level.playSound(null, worldPosition, SoundRegistry.CRUCIBLE_CRAFT.get(), SoundSource.BLOCKS, 1, 0.75f + random.nextFloat() * 0.5f);
        level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack));
        while (bonusYieldChance > 0) {
            if (bonusYieldChance >= 1 || random.nextFloat() < bonusYieldChance) {
                level.addFreshEntity(new ItemEntity(level, itemPos.x, itemPos.y, itemPos.z, outputStack.copy()));
            }
            bonusYieldChance -= 1;
        }
        recipe = SpiritFocusingRecipe.getRecipe(level, stack, spiritInventory.nonEmptyItemStacks);
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
    public ItemStack getCoreAugment() {
        return coreAugmentInventory.getStackInSlot(0);
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
    public CrucibleTuning.CrucibleAttributeType getTuningType() {
        return tuningType;
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
        return new Vec3(blockPos.getX() + offset.x, blockPos.getY() + offset.y, blockPos.getZ() + offset.z);
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
}
