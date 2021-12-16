package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.systems.block.SimpleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class ItemStandBlock extends SimpleBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape UP = Block.box(4, 0, 4, 12, 2, 12);
    public static final VoxelShape DOWN = Block.box(4, 14, 4, 12, 16, 12);
    public static final VoxelShape SOUTH = Block.box(4, 4, 0, 12, 12, 2);
    public static final VoxelShape NORTH = Block.box(4, 4, 14, 12, 12, 16);
    public static final VoxelShape WEST = Block.box(14, 4, 4, 16, 12, 12);
    public static final VoxelShape EAST = Block.box(0, 4, 4, 2, 12, 12);

    public ItemStandBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
        setTile(TileEntityRegistry.ITEM_STAND_TILE_ENTITY.get());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case UP -> {
                return UP;
            }
            case DOWN -> {
                return DOWN;
            }
            case SOUTH -> {
                return SOUTH;
            }
            case NORTH -> {
                return NORTH;
            }
            case WEST -> {
                return WEST;
            }
            case EAST -> {
                return EAST;
            }
        }
        return super.getShape(state, Level, pos, context);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
}