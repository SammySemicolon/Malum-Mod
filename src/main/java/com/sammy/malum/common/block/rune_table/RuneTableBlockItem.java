package com.sammy.malum.common.block.rune_table;

import com.sammy.malum.core.mod_systems.multiblock.MultiblockItem;
import net.minecraft.block.Block;

public class RuneTableBlockItem extends MultiblockItem
{
    public RuneTableBlockItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder, new RuneTableStructure());
    }
}
