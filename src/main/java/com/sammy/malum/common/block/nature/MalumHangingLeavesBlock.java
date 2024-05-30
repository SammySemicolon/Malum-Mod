package com.sammy.malum.common.block.nature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.awt.*;

public class MalumHangingLeavesBlock extends MalumLeavesBlock {
    protected static final VoxelShape SHAPE = Block.box(3.0D, 3.0D, 3.0D, 13.0D, 16.0D, 13.0D);

    public MalumHangingLeavesBlock(Properties properties, Color maxColor, Color minColor) {
        super(properties, maxColor, minColor);
    }

    @Override
    protected boolean decaying(BlockState pState) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing.equals(Direction.UP)) {
            if (pFacingState.hasProperty(DISTANCE) && pFacingState.hasProperty(COLOR)) {
                final int distance = Math.min(pFacingState.getValue(DISTANCE) + 1, 7);
                return super.updateShape(pState.setValue(DISTANCE, distance).setValue(COLOR, pFacingState.getValue(COLOR)), pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            }
        }
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        final Block block = pLevel.getBlockState(pPos.above()).getBlock();
        return block instanceof MalumLeavesBlock && !block.equals(pState.getBlock());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}