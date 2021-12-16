package com.sammy.malum.common.block.ether;

import net.minecraft.world.level.block.*;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.ILevel;
import net.minecraft.Level.ILevelReader;

import net.minecraft.block.AbstractBlock.Properties;

public class EtherTorchBlock extends EtherBlock implements IWaterLoggable
{
    protected static final VoxelShape SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);

    public EtherTorchBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader LevelIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, ILevel LevelIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !this.canSurvive(stateIn, LevelIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, LevelIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, ILevelReader LevelIn, BlockPos pos) {
        return canSupportCenter(LevelIn, pos.below(), Direction.UP);
    }
}