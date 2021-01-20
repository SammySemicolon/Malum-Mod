package com.sammy.malum.common.blocks.totems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemPoleBlock extends Block
{
    public TotemPoleBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }
    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return state(context.getNearestLookingDirection());
    }
    public BlockState state(Direction direction)
    {
        return getDefaultState().with(HORIZONTAL_FACING, direction);
    }
}
