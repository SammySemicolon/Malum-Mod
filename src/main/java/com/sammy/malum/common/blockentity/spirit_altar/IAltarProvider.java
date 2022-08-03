package com.sammy.malum.common.blockentity.spirit_altar;

import com.sammy.ortus.systems.blockentity.OrtusBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public interface IAltarProvider
{
    OrtusBlockEntityInventory getInventoryForAltar();
    Vec3 getItemPosForAltar();
    BlockPos getBlockPosForAltar();
}
