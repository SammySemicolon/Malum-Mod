package com.sammy.malum.common.block.storage;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.modules.toolkit.blockentity.*;
import team.lodestar.lodestone.modules.toolkit.inventory.ItemStackHandlerItemDisplayData;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackHandler;

import java.util.Optional;

public abstract class MalumItemHolderBlockEntity extends ItemHolderBlockEntity implements IMalumSpecialItemAccessPoint, Clearable {

    public MalumItemHolderBlockEntity(LodestoneBlockEntityType<? extends MalumItemHolderBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        inventory = MalumBlockItemStackHandler.create(this, 1).build();
    }

    @Override
    public LodestoneItemStackHandler getSuppliedInventory() {
        return inventory;
    }

    @Override
    public Vec3 getItemPos(float partialTicks) {
        var displayData = inventory.getDisplayData();
        return displayData.getEntry(0).map(displayData::getItemPosition).orElseGet(displayData::getDisplayCenter);
    }

    @Override
    public BlockPos getAccessPointBlockPos() {
        return worldPosition;
    }

    @Override
    public void clientTick(Level level) {
        if (inventory.getStackInSlot(0).getItem() instanceof SpiritShardItem item) {
            SpiritLightSpecs.rotatingLightSpecs(level, getItemPos(), item, 0.4f, 2);
        }
    }

    @Override
    public void clearContent() {
        inventory.clear();
    }
}