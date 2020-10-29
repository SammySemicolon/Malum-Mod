package com.sammy.malum.blocks.utility.multiblock;


import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;

public class MultiblockBlock extends Block
{
    public MultiblockStructure structure;
    public MultiblockBlock(Properties properties, MultiblockStructure structure)
    {
        super(properties);
        this.structure = structure;
    }
    
}