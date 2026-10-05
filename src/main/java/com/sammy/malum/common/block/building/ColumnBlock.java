package com.sammy.malum.common.block.building;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public class ColumnBlock extends RotatedPillarBlock {

    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");

    public ColumnBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(TOP, BOTTOM);
    }

    protected boolean canConnectTo(LevelAccessor level, BlockPos pos, Direction direction) {
        var offset = pos.relative(direction);
        var state = level.getBlockState(offset);
        if (state.getBlock() instanceof ColumnBlock) {
            return state.getValue(AXIS).equals(direction.getAxis());
        }
        return false;
    }

    protected BlockState getState(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        var positive = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        var negative = Direction.get(Direction.AxisDirection.NEGATIVE, axis);

        return defaultBlockState()
                .setValue(TOP, canConnectTo(level, pos, positive))
                .setValue(BOTTOM, canConnectTo(level, pos, negative))
                .setValue(AXIS, axis);
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getState(context.getLevel(), context.getClickedPos(), context.getClickedFace().getAxis());
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos facingPos) {
        Direction.Axis axis = state.getValue(AXIS);
        if (facing.getAxis().equals(axis)) {
            return getState(level, pos, axis);
        }
        return state;
    }
}