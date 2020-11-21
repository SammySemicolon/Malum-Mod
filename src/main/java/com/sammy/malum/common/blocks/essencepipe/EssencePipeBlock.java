package com.sammy.malum.common.blocks.essencepipe;

import com.sammy.malum.core.systems.essences.IEssenceRequest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class EssencePipeBlock extends Block
{
    public static final BooleanProperty NORTH_CONNECTION = BooleanProperty.create("north_connection");
    public static final BooleanProperty SOUTH_CONNECTION = BooleanProperty.create("south_connection");
    public static final BooleanProperty WEST_CONNECTION = BooleanProperty.create("west_connection");
    public static final BooleanProperty EAST_CONNECTION = BooleanProperty.create("east_connection");
    public static final BooleanProperty DOWN_CONNECTION = BooleanProperty.create("down_connection");
    public static final BooleanProperty UP_CONNECTION = BooleanProperty.create("up_connection");
    public static final BooleanProperty NORTH_EXTENSION = BooleanProperty.create("north_extension");
    public static final BooleanProperty SOUTH_EXTENSION = BooleanProperty.create("south_extension");
    public static final BooleanProperty WEST_EXTENSION = BooleanProperty.create("west_extension");
    public static final BooleanProperty EAST_EXTENSION = BooleanProperty.create("east_extension");
    public static final BooleanProperty DOWN_EXTENSION = BooleanProperty.create("down_extension");
    public static final BooleanProperty UP_EXTENSION = BooleanProperty.create("up_extension");
    
    public EssencePipeBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH_CONNECTION, false).with(SOUTH_CONNECTION, false).with(WEST_CONNECTION, false).with(EAST_CONNECTION, false).with(DOWN_CONNECTION, false).with(UP_CONNECTION, false).with(NORTH_EXTENSION, false).with(SOUTH_EXTENSION, false).with(WEST_EXTENSION, false).with(EAST_EXTENSION, false).with(DOWN_EXTENSION, false).with(UP_EXTENSION, false));
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return super.getShape(state, worldIn, pos, context);
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(NORTH_CONNECTION);
        builder.add(SOUTH_CONNECTION);
        builder.add(WEST_CONNECTION);
        builder.add(EAST_CONNECTION);
        builder.add(DOWN_CONNECTION);
        builder.add(UP_CONNECTION);
        builder.add(NORTH_EXTENSION);
        builder.add(SOUTH_EXTENSION);
        builder.add(WEST_EXTENSION);
        builder.add(EAST_EXTENSION);
        builder.add(DOWN_EXTENSION);
        builder.add(UP_EXTENSION);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return figureOutState(context.getWorld(), context.getPos());
    }
    
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        worldIn.setBlockState(pos, figureOutState(worldIn, pos));
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }
    
    public BlockState figureOutState(World world, BlockPos pos)
    {
        BlockState finalState = getDefaultState();
        Block north = world.getBlockState(pos.north()).getBlock();
        Block south = world.getBlockState(pos.south()).getBlock();
        Block west = world.getBlockState(pos.west()).getBlock();
        Block east = world.getBlockState(pos.east()).getBlock();
        Block down = world.getBlockState(pos.down()).getBlock();
        Block up = world.getBlockState(pos.up()).getBlock();
        if (north instanceof EssencePipeBlock)
        {
            finalState = finalState.with(NORTH_EXTENSION, true);
            finalState = finalState.with(NORTH_CONNECTION, false);
        }
        if (south instanceof EssencePipeBlock)
        {
            finalState = finalState.with(SOUTH_EXTENSION, true);
            finalState = finalState.with(SOUTH_CONNECTION, false);
        }
        if (west instanceof EssencePipeBlock)
        {
            finalState = finalState.with(WEST_EXTENSION, true);
            finalState = finalState.with(WEST_CONNECTION, false);
        }
        if (east instanceof EssencePipeBlock)
        {
            finalState = finalState.with(EAST_EXTENSION, true);
            finalState = finalState.with(EAST_CONNECTION, false);
        }
        if (down instanceof EssencePipeBlock)
        {
            finalState = finalState.with(DOWN_EXTENSION, true);
            finalState = finalState.with(DOWN_CONNECTION, false);
        }
        if (up instanceof EssencePipeBlock)
        {
            finalState = finalState.with(UP_EXTENSION, true);
            finalState = finalState.with(UP_CONNECTION, false);
        }
    
        if (north instanceof IEssenceRequest)
        {
            finalState = finalState.with(NORTH_EXTENSION, false);
            finalState = finalState.with(NORTH_CONNECTION, true);
        }
        if (south instanceof IEssenceRequest)
        {
            finalState = finalState.with(SOUTH_EXTENSION, false);
            finalState = finalState.with(SOUTH_CONNECTION, true);
        }
        if (west instanceof IEssenceRequest)
        {
            finalState = finalState.with(WEST_EXTENSION, false);
            finalState = finalState.with(WEST_CONNECTION, true);
        }
        if (east instanceof IEssenceRequest)
        {
            finalState = finalState.with(EAST_EXTENSION, false);
            finalState = finalState.with(EAST_CONNECTION, true);
        }
        if (down instanceof IEssenceRequest)
        {
            finalState = finalState.with(DOWN_EXTENSION, false);
            finalState = finalState.with(DOWN_CONNECTION, true);
        }
        if (up instanceof IEssenceRequest)
        {
            finalState = finalState.with(UP_EXTENSION, false);
            finalState = finalState.with(UP_CONNECTION, true);
        }
        return finalState;
    }
}