package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.common.blockentity.ItemStandTileEntity;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
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

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING;

public class ItemStandBlock extends SimpleBlock<ItemStandTileEntity> implements SimpleWaterloggedBlock {
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
        setTile(BlockEntityRegistry.ITEM_STAND_BLOCK_ENTITY);
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
        return super.getShape(state, level, pos, context);
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_152803_) {
        FluidState fluidstate = p_152803_.getLevel().getFluidState(p_152803_.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(p_152803_).setValue(WATERLOGGED, flag).setValue(FACING, p_152803_.getClickedFace());
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