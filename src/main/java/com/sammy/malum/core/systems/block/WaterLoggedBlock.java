package com.sammy.malum.core.systems.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class WaterLoggedBlock<T extends BlockEntity> extends SimpleBlock<T> implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public WaterLoggedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_152803_) {
        FluidState fluidstate = p_152803_.getLevel().getFluidState(p_152803_.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(p_152803_).setValue(WATERLOGGED, flag);
    }

    @Override
    public BlockState updateShape(BlockState p_152833_, Direction p_152834_, BlockState p_152835_, LevelAccessor p_152836_, BlockPos p_152837_, BlockPos p_152838_) {
        if (p_152833_.getValue(WATERLOGGED)) {
            p_152836_.scheduleTick(p_152837_, Fluids.WATER, Fluids.WATER.getTickDelay(p_152836_));
        }
        return super.updateShape(p_152833_, p_152834_, p_152835_, p_152836_, p_152837_, p_152838_);
    }

    @Override
    public FluidState getFluidState(BlockState p_152844_) {
        return p_152844_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_152844_);
    }
}
