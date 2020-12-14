package com.sammy.malum.core.systems.spirits.block;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpiritTransferTileEntity
{
    void setNeedsUpdate(boolean needsUpdate);
    boolean getNeedsUpdate();
    default void updateNetwork(World world, BlockPos requestPos, BlockPos currentPos)
    {
        if (world.getTileEntity(requestPos) instanceof ISpiritRequestTileEntity)
        {
            ISpiritRequestTileEntity requestTileEntity = (ISpiritRequestTileEntity) world.getTileEntity(requestPos);
            for (Direction direction : Direction.values())
            {
                BlockPos pos = currentPos.add(direction.getDirectionVec());
                if (world.getTileEntity(pos) instanceof ISpiritHolderTileEntity)
                {
                    requestTileEntity.addCachedHolder(pos);
                }
                if (world.getTileEntity(pos) instanceof ISpiritTransferTileEntity)
                {
                    ISpiritTransferTileEntity pipe = (ISpiritTransferTileEntity) world.getTileEntity(pos);
                    if (pipe.getNeedsUpdate())
                    {
                        pipe.setNeedsUpdate(false);
                        updateNetwork(world, requestPos, pos);
                    }
                }
            }
        }
    }
}
