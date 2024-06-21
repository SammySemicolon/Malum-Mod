package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

import com.sammy.malum.common.block.MalumBlockEntityInventory;
import com.sammy.malum.common.block.curiosities.spirit_crucible.AugmentBlockEntityInventory;
import com.sammy.malum.common.block.curiosities.spirit_crucible.CrucibleAccelerationData;
import com.sammy.malum.common.block.curiosities.spirit_crucible.ICatalyzerAccelerationTarget;
import com.sammy.malum.common.block.curiosities.spirit_crucible.ICrucibleAccelerator;
import com.sammy.malum.common.item.augment.AbstractAugmentItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.visual_effects.SpiritCrucibleParticleEffects;
import io.github.fabricators_of_create.porting_lib.block.CustomRenderBoundingBoxBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.BlockHelper;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import team.lodestar.lodestone.systems.multiblock.HorizontalDirectionStructure;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

import java.util.HashMap;
import java.util.function.Supplier;

public class SpiritCatalyzerCoreBlockEntity extends MultiBlockCoreEntity implements ICrucibleAccelerator, CustomRenderBoundingBoxBlockEntity {

    private static final Vec3 CATALYZER_ITEM_OFFSET = new Vec3(0.5f, 2f, 0.5f);
    private static final Vec3 CATALYZER_AUGMENT_OFFSET = new Vec3(0.5f, 2.75f, 0.5f);

    public static final Supplier<HorizontalDirectionStructure> STRUCTURE = () -> (HorizontalDirectionStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CATALYZER_COMPONENT.get().defaultBlockState())));

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory augmentInventory;

    public float burnTicks;
    public HashMap<MalumSpiritType, Integer> intensity;
    protected ICatalyzerAccelerationTarget target;

    public SpiritCatalyzerCoreBlockEntity(BlockEntityType<? extends SpiritCatalyzerCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
        inventory = new MalumBlockEntityInventory(1, 64, t -> !(t.getItem() instanceof SpiritShardItem)) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
        augmentInventory = new AugmentBlockEntityInventory(1, 1) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                needsSync = true;
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    public SpiritCatalyzerCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CATALYZER.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (burnTicks != 0) {
            compound.putFloat("burnTicks", burnTicks);
        }

        compound.put("Inventory", inventory.serializeNBT());
        compound.put("augmentInventory", augmentInventory.serializeNBT());
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        burnTicks = compound.getFloat("burnTicks");

        inventory.deserializeNBT(compound.getCompound("Inventory"));
        augmentInventory.deserializeNBT(compound.getCompound("augmentInventory"));
        super.load(compound);
    }


    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (level.isClientSide) {
            return InteractionResult.CONSUME;
        }
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            final ItemStack heldStack = player.getItemInHand(hand);
            final boolean augmentOnly = heldStack.getItem() instanceof AbstractAugmentItem;
            if (augmentOnly || (heldStack.isEmpty() && inventory.isEmpty())) {
                try (Transaction t = TransferUtil.getTransaction()) {
                    long inserted = augmentInventory.insert(ItemVariant.of(heldStack), 64, t);
                    heldStack.shrink((int) inserted);
                    setChanged();
                    t.commit();

                    if (inserted > 0) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
            if (!augmentOnly) {
                inventory.interact(this, player.level(), player, hand, s -> true);
            }
            if (heldStack.isEmpty()) {
                return InteractionResult.SUCCESS;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        if (!level.isClientSide) {
            inventory.dumpItems(level, worldPosition);
            augmentInventory.dumpItems(level, worldPosition);
        }
        super.onBreak(player);
    }

    @Override
    public void tick() {
        if (target != null && target.canBeAccelerated()) {
            if (burnTicks > 0) {
                CrucibleAccelerationData data = target.getAccelerationData();
                float ratio = data.fuelUsageRate.getValue(data);
                if (ratio > 0) {
                    burnTicks -= ratio;
                }
            }
        }
        if (level.isClientSide) {
            if (intensity == null) {
                intensity = new HashMap<>();
            }
            if (target != null) {
                boolean canBeAccelerated = target.canBeAccelerated();
                MalumSpiritType activeSpiritType = target.getActiveSpiritType();
                if (activeSpiritType != null) {
                    intensity.putIfAbsent(activeSpiritType, 0);
                    if (canBeAccelerated) {
                        intensity.put(activeSpiritType, Math.min(60, intensity.get(activeSpiritType) + 1));
                    }
                }
                for (MalumSpiritType spiritType : intensity.keySet()) {
                    if (canBeAccelerated && spiritType.equals(activeSpiritType)) {
                        continue;
                    }
                    intensity.put(spiritType, Math.max(0, intensity.get(spiritType) - 1));
                }
            }
            SpiritCrucibleParticleEffects.passiveSpiritCatalyzerParticles(this);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void addParticles(ICatalyzerAccelerationTarget target, MalumSpiritType spiritType) {
        if (burnTicks > 0 && level != null && level.isClientSide) {
            SpiritCrucibleParticleEffects.activeSpiritCatalyzerParticles(this, target, spiritType);
        }
    }

    @Override
    public boolean canStartAccelerating() {
        if (burnTicks > 0) {
            return true;
        }
        return FuelRegistry.INSTANCE.get(inventory.getStackInSlot(0).getItem()) > 0;
    }

    @Override
    public boolean canContinueAccelerating() {
        if (isRemoved()) {
            return false;
        }
        if (burnTicks <= 0) {
            ItemStack stack = inventory.getStackInSlot(0);
            if (!stack.isEmpty()) {
                burnTicks = FuelRegistry.INSTANCE.get(inventory.getStackInSlot(0).getItem()) / 2f;
                stack.shrink(1);
                inventory.setChanged();
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        }
        return burnTicks != 0;
    }

    @Override
    public CrucibleAcceleratorType getAcceleratorType() {
        return CatalyzerAcceleratorType.CATALYZER;
    }

    @Override
    public ItemStack getAugment() {
        return augmentInventory.getStackInSlot(0);
    }

    @Override
    public ICatalyzerAccelerationTarget getTarget() {
        return target;
    }

    @Override
    public void setTarget(ICatalyzerAccelerationTarget target) {
        this.target = target;
    }

    public Vec3 getItemOffset() {
        return CATALYZER_ITEM_OFFSET;
    }

    public Vec3 getAugmentOffset() {
        return CATALYZER_AUGMENT_OFFSET;
    }

    @Override
    public AABB getRenderBoundingBox() {
        var pos = worldPosition;
        return new AABB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 1, pos.getY() + 4, pos.getZ() + 1);
    }
}
