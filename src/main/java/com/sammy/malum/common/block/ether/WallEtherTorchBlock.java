package com.sammy.malum.common.block.ether;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.world.level.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.ILevel;
import net.minecraft.Level.ILevelReader;

import javax.annotation.Nullable;
import java.util.Map;

import net.minecraft.block.AbstractBlock.Properties;

public class WallEtherTorchBlock extends EtherBlock implements IWaterLoggable
{
    public static final DirectionProperty HORIZONTAL_FACING = HorizontalBlock.FACING;
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.box(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.box(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, Block.box(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, Block.box(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));


    public WallEtherTorchBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public String getDescriptionId()
    {
        return this.asItem().getDescriptionId();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader LevelIn, BlockPos pos, ISelectionContext context)
    {
        return getShapeForState(state);
    }

    public static VoxelShape getShapeForState(BlockState state)
    {
        return SHAPES.get(state.getValue(HORIZONTAL_FACING));
    }

    @Override
    public boolean canSurvive(BlockState state, ILevelReader LevelIn, BlockPos pos)
    {
        Direction direction = state.getValue(HORIZONTAL_FACING);
        BlockPos blockpos = pos.relative(direction.getOpposite());
        BlockState blockstate = LevelIn.getBlockState(blockpos);
        return blockstate.isFaceSturdy(LevelIn, blockpos, direction);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        BlockState blockstate = this.defaultBlockState();
        ILevelReader iLevelreader = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        Direction[] adirection = context.getNearestLookingDirections();

        for (Direction direction : adirection)
        {
            if (direction.getAxis().isHorizontal())
            {
                Direction direction1 = direction.getOpposite();
                blockstate = blockstate.setValue(HORIZONTAL_FACING, direction1);
                if (blockstate.canSurvive(iLevelreader, blockpos))
                {
                    return blockstate;
                }
            }
        }

        return null;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, ILevel LevelIn, BlockPos currentPos, BlockPos facingPos)
    {
        return facing.getOpposite() == stateIn.getValue(HORIZONTAL_FACING) && !stateIn.canSurvive(LevelIn, currentPos) ? Blocks.AIR.defaultBlockState() : stateIn;
    }


    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return state.setValue(HORIZONTAL_FACING, rot.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn)
    {
        return state.rotate(mirrorIn.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED, HORIZONTAL_FACING);
    }
}