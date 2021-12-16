package com.sammy.malum.common.block.ether;

import net.minecraft.world.level.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.ILevelReader;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class EtherBrazierBlock extends EtherBlock implements IWaterLoggable
{
    public static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 8, 12);
    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;

    public EtherBrazierBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false).setValue(WATERLOGGED, false).setValue(ROTATED, false));
    
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
    
        BlockState blockstate = this.defaultBlockState();
        for (Direction direction : context.getNearestLookingDirections())
        {
            if (direction.getAxis() == Direction.Axis.Y)
            {
                blockstate = this.defaultBlockState().setValue(HANGING, direction == Direction.UP).setValue(ROTATED, context.getHorizontalDirection() == Direction.NORTH || context.getHorizontalDirection() == Direction.SOUTH);
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos()))
                {
                    return blockstate.setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
                }
            }
        }
        
        return blockstate;
    }
    
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HANGING, WATERLOGGED, ROTATED);
    }
    
    @Override
    public boolean canSurvive(BlockState state, ILevelReader level, BlockPos pos)
    {
        Direction direction = getBlockConnected(state).getOpposite();
        return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }
    
    protected static Direction getBlockConnected(BlockState state)
    {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }
    
    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

    
    @Override
    public boolean isPathfindable(BlockState state, IBlockReader level, BlockPos pos, PathType type)
    {
        return false;
    }
}