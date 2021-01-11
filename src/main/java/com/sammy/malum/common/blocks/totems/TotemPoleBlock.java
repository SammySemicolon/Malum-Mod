package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.modcontent.MalumRunes.MalumRune;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;

import javax.annotation.Nullable;

import static com.sammy.malum.common.blocks.totems.TotemCoreBlock.POWERED;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TotemPoleBlock extends Block
{
    public MalumRune rune;
    public TotemPoleBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, false));
    }
    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(POWERED);
        blockStateBuilder.add(HORIZONTAL_FACING);
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return state(context.getNearestLookingDirection());
    }
    public BlockState state(Direction direction)
    {
        return getDefaultState().with(HORIZONTAL_FACING, direction).with(POWERED, false);
    
    }
}
