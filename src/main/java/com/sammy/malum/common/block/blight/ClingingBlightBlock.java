package com.sammy.malum.common.block.blight;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Locale;

import static com.sammy.malum.registry.common.block.BlockTagRegistry.BLIGHTED_BLOCKS;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ClingingBlightBlock extends Block {
    protected static final VoxelShape SHAPE_GROUNDED_ROOTS_X = Block.box(0.0D, 0.0D, 2.0D, 16.0D, 6.0D, 14.0D);
    protected static final VoxelShape SHAPE_GROUNDED_ROOTS_Z = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 6.0D, 16.0D);

    protected static final VoxelShape SHAPE_ROOTED_EAST = Block.box(4.0D, 0.0D, 2.0D, 16.0D, 12.0D, 14.0D);
    protected static final VoxelShape SHAPE_ROOTED_WEST = Block.box(0.0D, 0.0D, 2.0D, 12.0D, 12.0D, 14.0D);
    protected static final VoxelShape SHAPE_ROOTED_SOUTH = Block.box(2.0D, 0.0D, 4.0D, 14.0D, 12.0D, 16.0D);
    protected static final VoxelShape SHAPE_ROOTED_NORTH = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 12.0D, 12.0D);

    protected static final VoxelShape SHAPE_BRACED_EAST = Block.box(4.0D, 0.0D, 4.0D, 16.0D, 16.0D, 12.0D);
    protected static final VoxelShape SHAPE_BRACED_WEST = Block.box(0.0D, 0.0D, 4.0D, 8.0D, 16.0D, 12.0D);
    protected static final VoxelShape SHAPE_BRACED_SOUTH = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_BRACED_NORTH = Block.box(4.0D, 0.0D, 0.0D, 12.0D, 16.0D, 8.0D);

    protected static final VoxelShape SHAPE_HANGING_EAST = Block.box(4.0D, 0.0D, 2.0D, 16.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE_HANGING_WEST = Block.box(0.0D, 0.0D, 2.0D, 12.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE_HANGING_SOUTH = Block.box(2.0D, 0.0D, 4.0D, 14.0D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_HANGING_NORTH = Block.box(2.0D, 0.0D, 0.0D, 14.0D, 16.0D, 12.0D);

    protected static final VoxelShape SHAPE_HANGING_ROOTS_X = Block.box(0.0D, 12.0D, 2.0D, 16.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE_HANGING_ROOTS_Z = Block.box(2.0D, 12.0D, 0.0D, 14.0D, 16.0D, 16.0D);


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
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
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
                if (aboveState.hasProperty(BLIGHT_TYPE)) {
                    if (aboveState.getValue(BLIGHT_TYPE).equals(BlightType.HANGING_BLIGHT)) {
                        return state.setValue(BLIGHT_TYPE, BlightType.HANGING_BLIGHT_CONNECTION);
                    }
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
            final BlockPos below = pPos.below();
            if (!pLevel.getBlockState(below).is(BLIGHTED_BLOCKS)) {
                return false;
            }
            if (!Block.canSupportCenter(pLevel, below, Direction.UP)) {
                return false;
            }
            if (value.equals(BlightType.ROOTED_BLIGHT)) {
                if (!Block.canSupportCenter(pLevel, pPos.relative(direction), direction.getOpposite())) {
                    return false;
                }
            }
        }

        if (value.equals(BlightType.HANGING_BLIGHT_CONNECTION)) {
            final BlockState state = pLevel.getBlockState(pPos.above());
            if (!state.hasProperty(BLIGHT_TYPE) || !state.getValue(BLIGHT_TYPE).equals(BlightType.HANGING_BLIGHT)) {
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
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape[] shapeArray;
        switch (pState.getValue(BLIGHT_TYPE)) {
            case GROUNDED_ROOTS ->
                    shapeArray = new VoxelShape[]{SHAPE_GROUNDED_ROOTS_X, SHAPE_GROUNDED_ROOTS_X, SHAPE_GROUNDED_ROOTS_Z, SHAPE_GROUNDED_ROOTS_Z};
            case ROOTED_BLIGHT ->
                    shapeArray = new VoxelShape[]{SHAPE_ROOTED_EAST, SHAPE_ROOTED_WEST, SHAPE_ROOTED_SOUTH, SHAPE_ROOTED_NORTH};
            case SOULWOOD_SPIKE, HANGING_BLIGHT_CONNECTION ->
                    shapeArray = new VoxelShape[]{SHAPE_BRACED_EAST, SHAPE_BRACED_WEST, SHAPE_BRACED_SOUTH, SHAPE_BRACED_NORTH};
            case HANGING_BLIGHT ->
                    shapeArray = new VoxelShape[]{SHAPE_HANGING_EAST, SHAPE_HANGING_WEST, SHAPE_HANGING_SOUTH, SHAPE_HANGING_NORTH};
            case HANGING_ROOTS ->
                    shapeArray = new VoxelShape[]{SHAPE_HANGING_ROOTS_X, SHAPE_HANGING_ROOTS_X, SHAPE_HANGING_ROOTS_Z, SHAPE_HANGING_ROOTS_Z};
            default -> throw new IllegalStateException("Unexpected value: " + pState.getValue(BLIGHT_TYPE));
        }
        switch (pState.getValue(HORIZONTAL_FACING)) {
            case EAST -> {
                return shapeArray[0];
            }
            case WEST -> {
                return shapeArray[1];
            }
            case SOUTH -> {
                return shapeArray[2];
            }
            case NORTH -> {
                return shapeArray[3];
            }
        }
        return super.getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    public VoxelShape getBlockSupportShape(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return Shapes.empty();
    }

    public enum BlightType implements StringRepresentable {
        GROUNDED_ROOTS,
        ROOTED_BLIGHT,
        SOULWOOD_SPIKE,
        HANGING_BLIGHT_CONNECTION,
        HANGING_BLIGHT,
        HANGING_ROOTS;

        final String name = name().toLowerCase(Locale.ROOT);

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
