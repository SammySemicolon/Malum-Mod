package com.sammy.malum.common.block.storage;

import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.core.helper.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.blockentity.*;

public abstract class MalumItemHolderBlockEntity extends ItemHolderBlockEntity implements IMalumSpecialItemAccessPoint {

    public MalumItemHolderBlockEntity(BlockEntityType<? extends MalumItemHolderBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = new LodestoneBlockEntityInventory(1, 64) {
            @Override
            public void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                BlockHelper.updateAndNotifyState(level, worldPosition);
            }
        };
    }

    @Override
    public LodestoneBlockEntityInventory getSuppliedInventory() {
        return inventory;
    }

    @Override
    public Vec3 getItemCenterPos() {
        final BlockPos blockPos = getBlockPos();
        final Vec3 offset = getItemOffset();
        return new Vec3(blockPos.getX()+offset.x, blockPos.getY()+offset.y, blockPos.getZ()+offset.z);
    }

    @Override
    public BlockPos getAccessPointBlockPos() {
        return worldPosition;
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem item) {
                MalumSpiritType type = item.type;
                SpiritLightSpecs.rotatingLightSpecs(level, getItemCenterPos(), type, 0.4f, 2);
            }
        }
    }
}