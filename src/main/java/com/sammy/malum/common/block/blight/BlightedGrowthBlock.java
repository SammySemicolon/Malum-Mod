package com.sammy.malum.common.block.blight;

import io.github.fabricators_of_create.porting_lib.extensions.extensions.IShearable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import static com.sammy.malum.registry.common.block.BlockTagRegistry.BLIGHTED_BLOCKS;

public class BlightedGrowthBlock extends BushBlock implements BonemealableBlock, IShearable {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 8.0D, 14.0D);

    public BlightedGrowthBlock(Properties p_57318_) {
        super(p_57318_);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.is(BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.mayPlaceOn(pState, pLevel, pPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        final BlockPos below = pPos.below();
        final BlockState state = pLevel.getBlockState(below);
        final Block block = state.getBlock();
        return block instanceof BlightedSoilBlock blightedSoilBlock && blightedSoilBlock.isBonemealSuccess(pLevel, pRandom, below, state);
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        final BlockPos below = pPos.below();
        final BlockState state = pLevel.getBlockState(below);
        if (state.getBlock() instanceof BlightedSoilBlock blightedSoilBlock) {
            blightedSoilBlock.performBonemeal(pLevel, pRandom, below, state);
        }
    }
}
