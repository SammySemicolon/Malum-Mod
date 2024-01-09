package com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.item.catalyzer_augment.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.multiblock.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class SpiritCatalyzerCoreBlockEntity extends MultiBlockCoreEntity implements ICrucibleAccelerator {

    private static final Vec3 CATALYZER_ITEM_OFFSET = new Vec3(0.5f, 2f, 0.5f);

    public static final Supplier<HorizontalDirectionStructure> STRUCTURE = () -> (HorizontalDirectionStructure.of(new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.SPIRIT_CATALYZER_COMPONENT.get().defaultBlockState())));

    public static final CrucibleAcceleratorType CATALYZER = new ArrayCrucibleAcceleratorType("catalyzer",
            new float[]{0.2f, 0.25f, 0.3f, 0.4f, 0.45f, 0.5f, 0.6f, 0.8f},
            new int[]{1, 1, 1, 2, 2, 3, 3, 5},
            new float[]{0.25f, 0.5f, 0.75f, 1f, 1.5f, 2.25f, 3.5f, 8f});

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory augmentInventory;

    public int burnTicks;
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
        augmentInventory = new MalumBlockEntityInventory(1, 1, t -> t.getItem() instanceof AbstractAugmentItem abstractAugmentItem && !abstractAugmentItem.isForCrucible()) {
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

    public SpiritCatalyzerCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.SPIRIT_CATALYZER.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        if (burnTicks != 0) {
            compound.putInt("burnTicks", burnTicks);
        }

        inventory.save(compound);
        augmentInventory.save(compound, "augmentInventory");
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        burnTicks = compound.getInt("burnTicks");

        inventory.load(compound);
        augmentInventory.load(compound, "augmentInventory");
        super.load(compound);
    }


    @Override
    public InteractionResult onUse(Player player, InteractionHand hand) {
        if (hand.equals(InteractionHand.MAIN_HAND)) {
            final ItemStack heldStack = player.getItemInHand(hand);
            final boolean augmentOnly = heldStack.getItem() instanceof AbstractAugmentItem augment && !augment.isForCrucible();
            if (augmentOnly || (heldStack.isEmpty() && inventory.isEmpty())) {
                ItemStack stack = augmentInventory.interact(player.level(), player, hand);
                if (!stack.isEmpty()) {
                    return InteractionResult.SUCCESS;
                }
            }
            if (!augmentOnly) {
                inventory.interact(player.level(), player, hand);
            }
            if (heldStack.isEmpty()) {
                return InteractionResult.SUCCESS;
            }
        }
        return super.onUse(player, hand);
    }

    @Override
    public void onBreak(@Nullable Player player) {
        inventory.dumpItems(level, BlockHelper.fromBlockPos(worldPosition).add(0.5f, 0.5f, 0.5f));
        super.onBreak(player);
    }

    @Override
    public void tick() {
        if (target != null && target.canBeAccelerated()) {
            if (burnTicks > 0) {
                burnTicks--;
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
                    intensity.put(spiritType, Math.max(0,intensity.get(spiritType) - 1));
                }
            }
        }
    }


    @Override
    public boolean canStartAccelerating() {
        if (burnTicks > 0) {
            return true;
        }
        return ForgeHooks.getBurnTime(inventory.getStackInSlot(0), RecipeType.SMELTING) > 0;
    }

    @Override
    public boolean canContinueAccelerating() {
        if (burnTicks == 0) {
            ItemStack stack = inventory.getStackInSlot(0);
            if (!stack.isEmpty()) {
                burnTicks = ForgeHooks.getBurnTime(inventory.getStackInSlot(0), RecipeType.SMELTING) / 2;
                stack.shrink(1);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        }
        return burnTicks != 0;
    }

    @Override
    public CrucibleAcceleratorType getAcceleratorType() {
        return CATALYZER;
    }

    @Override
    public ICatalyzerAccelerationTarget getTarget() {
        return target;
    }

    @Override
    public void setTarget(ICatalyzerAccelerationTarget target) {
        this.target = target;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addParticles(ICatalyzerAccelerationTarget target, MalumSpiritType spiritType) {
        if (burnTicks > 0) {
            SpiritCrucibleParticleEffects.spiritCatalyzerParticles(this, target, spiritType);
        }
    }

    public Vec3 getItemOffset() {
        return CATALYZER_ITEM_OFFSET;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return inventory.inventoryOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
