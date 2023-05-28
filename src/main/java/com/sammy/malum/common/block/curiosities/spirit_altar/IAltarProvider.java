package com.sammy.malum.common.block.curiosities.spirit_altar;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntityInventory;

public interface IAltarProvider
{
    LodestoneBlockEntityInventory getInventoryForAltar();
    Vec3 getItemPosForAltar();
    BlockPos getBlockPosForAltar();
}
