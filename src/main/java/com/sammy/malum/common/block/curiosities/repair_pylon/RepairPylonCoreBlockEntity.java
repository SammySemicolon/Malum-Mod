package com.sammy.malum.common.block.curiosities.repair_pylon;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.recipe.SpiritFocusingRecipe;
import com.sammy.malum.common.recipe.SpiritRepairRecipe;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.util.Mth;
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
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;
import team.lodestar.lodestone.systems.multiblock.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class RepairPylonCoreBlockEntity extends MultiBlockCoreEntity {

    public static final Supplier<MultiBlockStructure> STRUCTURE = () -> (MultiBlockStructure.of(
            new MultiBlockStructure.StructurePiece(0, 1, 0, BlockRegistry.REPAIR_PYLON_COMPONENT.get().defaultBlockState()),
            new MultiBlockStructure.StructurePiece(0, 2, 0, BlockRegistry.REPAIR_PYLON_COMPONENT.get().defaultBlockState().setValue(RepairPylonComponentBlock.TOP, true))));

    public enum RepairPylonState {
        IDLE,
        SEARCHING,
        ACTIVE,
        COOLDOWN
    }

    public LodestoneBlockEntityInventory inventory;
    public LodestoneBlockEntityInventory spiritInventory;
    public SpiritRepairRecipe recipe;

    public RepairPylonState state = RepairPylonState.IDLE;
    public float timer;
    public BlockPos targetedBlock;
    public ItemHolderBlockEntity targetedBlockEntity;

    public float spiritAmount;
    public float spiritSpin;

    public RepairPylonCoreBlockEntity(BlockEntityType<? extends RepairPylonCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
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
    }

    public RepairPylonCoreBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.REPAIR_PYLON.get(), STRUCTURE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
    }

    @Override
    public void tick() {
        super.tick();

    }
}
