package com.sammy.malum.common.block.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

public interface IMalumSpecialItemAccessPoint
{
    LodestoneBlockEntityInventory getSuppliedInventory();
    Vec3 getItemCenterPos();
    Vec3 getItemOffset();
    BlockPos getAccessPointBlockPos();
}
