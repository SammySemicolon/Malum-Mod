package com.sammy.malum.core.systems.heat;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHeatBlock
{
    public default void updateHeat(World world, BlockPos pos)
    {
        if (world.getTileEntity(pos) instanceof IHeatTileEntity)
        {
            IHeatTileEntity tileEntity = (IHeatTileEntity) world.getTileEntity(pos);
            tileEntity.getSystem().needsUpdate = true;
        }
    }
}
