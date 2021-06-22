package com.sammy.malum.common.blocks.runetable;

import com.sammy.malum.core.systems.multiblock.MultiblockItem;
import com.sammy.malum.core.systems.multiblock.MultiblockStructure;
import net.minecraft.block.Block;

public class RuneTableBlockItem extends MultiblockItem
{
    public RuneTableBlockItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder, new RuneTableStructure());
    }
}
