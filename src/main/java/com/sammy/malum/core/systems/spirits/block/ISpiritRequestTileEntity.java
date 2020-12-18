package com.sammy.malum.core.systems.spirits.block;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public interface ISpiritRequestTileEntity
{
    void addCachedHolder(BlockPos pos);
    void resetCachedHolders();
    ArrayList<BlockPos> getCachedHolders();
    default boolean needsUpdate()
    {
        return getCachedHolders().isEmpty();
    }
    default ArrayList<BlockPos> issueRequest(World world, BlockPos requestPos)
    {
        if (needsUpdate())
        {
            for (Direction direction : Direction.values())
            {
                BlockPos pos = requestPos.add(direction.getDirectionVec());
    
                if (world.getTileEntity(pos) instanceof ISpiritHolderTileEntity)
                {
                    addCachedHolder(pos);
                }
                if (world.getTileEntity(pos) instanceof ISpiritTransferTileEntity)
                {
                    ISpiritTransferTileEntity pipe = (ISpiritTransferTileEntity) world.getTileEntity(pos);
                    if (pipe.getNeedsUpdate())
                    {
                        pipe.setNeedsUpdate(false);
                        pipe.updateNetwork(world, requestPos, pos);
                    }
                }
            }
        }
        return getCachedHolders();
    }
}
