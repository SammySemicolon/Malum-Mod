package com.sammy.malum.common.block.storage;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

public interface IMalumSpecialItemAccessPoint {
    LodestoneBlockEntityInventory getSuppliedInventory();

    default Vec3 getItemPos() {
        return getItemPos(0);
    }

    Vec3 getItemPos(float partialTicks);

    Vec3 getItemOffset(float partialTicks);

    BlockPos getAccessPointBlockPos();
}
