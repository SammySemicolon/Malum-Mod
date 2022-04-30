package com.sammy.malum.common.blockentity.altar;

import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public interface IAltarProvider
{
    SimpleBlockEntityInventory getInventoryForAltar();
    Vec3 getItemPosForAltar();
    BlockPos getBlockPosForAltar();
}
