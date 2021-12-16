package com.sammy.malum.common.block.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;

import net.minecraft.block.AbstractBlock.Properties;

public class MalumDirectionalBlock extends DirectionalBlock
{
    public MalumDirectionalBlock(Properties builder)
    {
        super(builder);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        if (context.getPlayer().isShiftKeyDown())
        {
            return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection());
        }
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite());
    }
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.setValue(FACING, mirrorIn.mirror(state.getValue(FACING)));
    }
}