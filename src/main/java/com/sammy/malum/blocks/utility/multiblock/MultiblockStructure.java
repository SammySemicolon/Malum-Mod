package com.sammy.malum.blocks.utility.multiblock;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiblockStructure
{
    public List<BlockPos> occupiedPositions;
    
    public MultiblockStructure(BlockPos... occupiedPositions)
    {
        this.occupiedPositions = new ArrayList<>();
        Collections.addAll(this.occupiedPositions, occupiedPositions);
    }
}