package com.sammy.malum.common.block.blight;

import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;

import java.util.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class ClingingBlightBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(2.0D, 2.0D, 2.0D, 14.0D, 14.0D, 14.0D);

    public static final EnumProperty<BlightType> BLIGHT_TYPE = EnumProperty.create("blight_type", BlightType.class);

    public ClingingBlightBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(BLIGHT_TYPE, BlightType.ROOTED_BLIGHT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        builder.add(BLIGHT_TYPE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        final Direction direction = context.getClickedFace();
        BlockState state = this.defaultBlockState().setValue(HORIZONTAL_FACING, direction.getAxis().isHorizontal() ? direction.getOpposite() : context.getHorizontalDirection());
        final Level level = context.getLevel();
        final BlockPos clickedPos = context.getClickedPos();
        if (!direction.getAxis().isVertical()) {
            state = state.setValue(BLIGHT_TYPE, BlightType.SOULWOOD_SPIKE);
        } else {
            if (direction.equals(Direction.DOWN)) {
                final BlockState aboveState = level.getBlockState(clickedPos.above());
                if (aboveState.hasProperty(BLIGHT_TYPE) && aboveState.getValue(BLIGHT_TYPE).equals(BlightType.HANGING_BLIGHT)) {
                    return state.setValue(BLIGHT_TYPE, BlightType.HANGING_BLIGHT_CONNECTION);
                }
                state = state.setValue(BLIGHT_TYPE, BlightType.HANGING_BLIGHT);

                if (!state.canSurvive(level, clickedPos)) {
                    return state.setValue(BLIGHT_TYPE, BlightType.HANGING_ROOTS);
                }
            }
            if (direction.getAxis().isHorizontal() || (direction.equals(Direction.UP))) {
                state = state.setValue(BLIGHT_TYPE, BlightType.ROOTED_BLIGHT);
                if (!state.canSurvive(level, clickedPos)) {
                    return state.setValue(BLIGHT_TYPE, BlightType.GROUNDED_ROOTS);
                }
            }
        }
        return state.canSurvive(level, clickedPos) ? state : null;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        final BlightType value = pState.getValue(BLIGHT_TYPE);
        Direction direction = pState.getValue(HORIZONTAL_FACING);
        if (value.equals(BlightType.ROOTED_BLIGHT) || value.equals(BlightType.GROUNDED_ROOTS)) {
            if (!Block.canSupportCenter(pLevel, pPos.below(), Direction.UP)) {
                return false;
            }
        }

        if (value.equals(BlightType.HANGING_BLIGHT_CONNECTION)) {
            final BlockState state = pLevel.getBlockState(pPos.above());
            if (!state.hasProperty(BLIGHT_TYPE) && !state.getValue(BLIGHT_TYPE).equals(BlightType.HANGING_BLIGHT)) {
                return false;
            }
        }
        if (value.equals(BlightType.HANGING_BLIGHT) || value.equals(BlightType.HANGING_ROOTS)) {
            if (!Block.canSupportCenter(pLevel, pPos.above(), Direction.DOWN)) {
                return false;
            }
        }
        if (value.equals(BlightType.GROUNDED_ROOTS) || value.equals(BlightType.HANGING_ROOTS)) {
            return true;
        }
        final BlockPos pos = pPos.relative(direction);
        return Block.canSupportCenter(pLevel, pos, direction);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.empty();
    }

    public enum BlightType implements StringRepresentable {
        HANGING_BLIGHT,
        HANGING_BLIGHT_CONNECTION,
        ROOTED_BLIGHT,
        GROUNDED_ROOTS,
        HANGING_ROOTS,
        SOULWOOD_SPIKE;

        final String name = name().toLowerCase(Locale.ROOT);

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
