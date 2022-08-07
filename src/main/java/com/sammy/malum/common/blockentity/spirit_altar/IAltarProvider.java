package com.sammy.malum.common.blockentity.spirit_altar;

import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public interface IAltarProvider
{
    LodestoneBlockEntityInventory getInventoryForAltar();
    Vec3 getItemPosForAltar();
    BlockPos getBlockPosForAltar();
}
