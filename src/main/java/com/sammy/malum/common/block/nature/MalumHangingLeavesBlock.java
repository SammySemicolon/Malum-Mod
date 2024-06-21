package com.sammy.malum.common.block.nature;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.awt.*;

public class MalumHangingLeavesBlock extends MalumLeavesBlock {
    protected static final VoxelShape SHAPE = Block.box(3.0D, 3.0D, 3.0D, 13.0D, 16.0D, 13.0D);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty COLOR = MalumLeavesBlock.COLOR;

    public final Color maxColor;
    public final Color minColor;

    public MalumHangingLeavesBlock(Properties properties, Color maxColor, Color minColor) {
        super(properties);
        this.maxColor = maxColor;
        this.minColor = minColor;
        registerDefaultState(defaultBlockState().setValue(COLOR, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COLOR, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(COLOR, 0);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing.equals(Direction.UP)) {
            if (pFacingState.hasProperty(DISTANCE) && pFacingState.hasProperty(COLOR)) {
                final int distance = Math.min(pFacingState.getValue(DISTANCE) + 1, 7);
                return super.updateShape(pState.setValue(DISTANCE, distance).setValue(COLOR, pFacingState.getValue(COLOR)), pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            if (pFacingState.hasProperty(COLOR)) {
                return super.updateShape(pState.setValue(COLOR, pFacingState.getValue(COLOR)), pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            }
        }
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (player.getItemInHand(handIn).getItem().equals(ItemRegistry.INFERNAL_SPIRIT.get())) {
            level.setBlockAndUpdate(pos, state.setValue(COLOR, (state.getValue(COLOR) + 1) % 5));
            player.swing(handIn);
            player.playSound(SoundEvents.BLAZE_SHOOT, 1F, 1.5f + RANDOM.nextFloat() * 0.5f);
            return InteractionResult.SUCCESS;
        }
        return super.use(state, level, pos, player, handIn, hit);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        var block = pLevel.getBlockState(pPos.above()).getBlock();
        return block instanceof LeavesBlock;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public Color getMaxColor() {
        return maxColor;
    }

    @Override
    public Color getMinColor() {
        return minColor;
    }
}