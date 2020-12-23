package com.sammy.malum.core.systems.multiblock;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiblockStructure
{
    public List<BlockPos> occupiedPositions;
    public BoundingBlock boundingBlock;
    public MultiblockStructure(Block boundingBlock, BlockPos... offsets)
    {
        this.boundingBlock = (BoundingBlock) boundingBlock;
        this.occupiedPositions = new ArrayList<>();
        Collections.addAll(this.occupiedPositions, offsets);
    }
    public static BlockPos off(int x, int y, int z)
    {
        return new BlockPos(x,y,z);
    }
    public static MultiblockStructure doubleTallBlock(Block boundingBlock)
    {
        return new MultiblockStructure(boundingBlock, off(0,1,0));
    }
}