package com.sammy.malum.common.blocks.arcanecompressor;

import com.sammy.malum.common.blocks.itemfocus.ItemFocusTileEntity;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;

public class ArcaneCompressorBlockItem extends BlockItem
{
    public ArcaneCompressorBlockItem(Block block, Properties builder)
    {
        super(block, builder);
    }

    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state)
    {
        if (context.getWorld().getTileEntity(context.getPos().down()) instanceof ItemFocusTileEntity)
        {
            return context.getWorld().setBlockState(context.getPos().up(), state, 11);
        }
        return super.placeBlock(context, state);
    }
}
