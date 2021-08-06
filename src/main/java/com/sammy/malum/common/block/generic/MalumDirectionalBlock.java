package com.sammy.malum.common.block.generic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

public class MalumDirectionalBlock extends DirectionalBlock
{
    public MalumDirectionalBlock(Properties builder)
    {
        super(builder);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }
    
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        if (context.getPlayer().isSneaking())
        {
            return this.getDefaultState().with(FACING, context.getNearestLookingDirection());
        }
        return this.getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}