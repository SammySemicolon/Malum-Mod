package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.common.tile.ItemStandTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.core.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.ILevel;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

import net.minecraft.block.AbstractBlock.Properties;

public class ItemStandBlock extends SimpleInventoryBlock implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public static final VoxelShape UP =Block.box(4, 0, 4, 12, 2, 12);
    public static final VoxelShape DOWN =Block.box(4, 14, 4, 12, 16, 12);
    public static final VoxelShape SOUTH =Block.box(4, 4, 0, 12, 12, 2);
    public static final VoxelShape NORTH =Block.box(4, 4, 14, 12, 12, 16);
    public static final VoxelShape WEST =Block.box(14, 4, 4, 16, 12, 12);
    public static final VoxelShape EAST =Block.box(0, 4, 4, 2, 12, 12);

    public ItemStandBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader LevelIn, BlockPos pos, ISelectionContext context)
    {
        switch (state.getValue(FACING))
        {
            case UP:
            {
                return UP;
            }
            case DOWN:
            {
                return DOWN;
            }
            case SOUTH:
            {
                return SOUTH;
            }
            case NORTH:
            {
                return NORTH;
            }
            case WEST:
            {
                return WEST;
            }
            case EAST:
            {
                return EAST;
            }
        }
        return super.getShape(state, LevelIn, pos, context);
    }
    
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader Level)
    {
        return new ItemStandTileEntity();
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
        builder.add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, ILevel LevelIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.getValue(WATERLOGGED))
        {
            LevelIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(LevelIn));
        }
        return super.updateShape(stateIn, facing, facingState, LevelIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(FACING, context.getClickedFace()).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
}