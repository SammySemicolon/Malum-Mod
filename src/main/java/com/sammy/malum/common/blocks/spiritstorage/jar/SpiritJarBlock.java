package com.sammy.malum.common.blocks.spiritstorage.jar;

import com.sammy.malum.common.blocks.spiritstorage.pipe.IPipeConnected;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class SpiritJarBlock extends Block implements IPipeConnected
{
    //region properties
    public static final BooleanProperty NORTH_CONNECTION = BooleanProperty.create("north_connection");
    public static final BooleanProperty SOUTH_CONNECTION = BooleanProperty.create("south_connection");
    public static final BooleanProperty WEST_CONNECTION = BooleanProperty.create("west_connection");
    public static final BooleanProperty EAST_CONNECTION = BooleanProperty.create("east_connection");
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(NORTH_CONNECTION);
        builder.add(SOUTH_CONNECTION);
        builder.add(WEST_CONNECTION);
        builder.add(EAST_CONNECTION);
        super.fillStateContainer(builder);
    }
    //endregion
    
    //region shapes
    public static final VoxelShape base = Stream.of(Block.makeCuboidShape(2.5, 0.5, 2.5, 13.5, 13.5, 13.5), Block.makeCuboidShape(3.5, 14.5, 3.5, 12.5, 16.5, 12.5), Block.makeCuboidShape(4.5, 13.5, 4.5, 11.5, 14.5, 11.5), Block.makeCuboidShape(5.5, 0, 5.5, 10.5, 1, 10.5)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    public static final VoxelShape north = Block.makeCuboidShape(5, 5, 0, 11, 11, 4);
    public static final VoxelShape east = Block.makeCuboidShape(12, 5, 5, 16, 11, 11);
    public static final VoxelShape south = Block.makeCuboidShape(5, 5, 12, 11, 11, 16);
    public static final VoxelShape west = Block.makeCuboidShape(0, 5, 5, 4, 11, 11);
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        VoxelShape shape = base;
        if (state.get(NORTH_CONNECTION))
        {
            shape = VoxelShapes.or(shape, north);
        }
        if (state.get(SOUTH_CONNECTION))
        {
            shape = VoxelShapes.or(shape, south);
        }
        if (state.get(WEST_CONNECTION))
        {
            shape = VoxelShapes.or(shape, west);
        }
        if (state.get(EAST_CONNECTION))
        {
            shape = VoxelShapes.or(shape, east);
        }
        return shape;
    }
    //endregion
    
    public SpiritJarBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH_CONNECTION, false).with(SOUTH_CONNECTION, false).with(WEST_CONNECTION, false).with(EAST_CONNECTION, false));
    }
    
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SpiritJarTileEntity();
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
        Predicate<Block> predicate = b -> b instanceof IPipeConnected;
        BlockState finalState = getDefaultState();
        Block north = world.getBlockState(pos.north()).getBlock();
        Block south = world.getBlockState(pos.south()).getBlock();
        Block west = world.getBlockState(pos.west()).getBlock();
        Block east = world.getBlockState(pos.east()).getBlock();
        if (predicate.test(north))
        {
            finalState = finalState.with(NORTH_CONNECTION, true);
        }
        if (predicate.test(south))
        {
            finalState = finalState.with(SOUTH_CONNECTION, true);
        }
        if (predicate.test(west))
        {
            finalState = finalState.with(WEST_CONNECTION, true);
        }
        if (predicate.test(east))
        {
            finalState = finalState.with(EAST_CONNECTION, true);
        }
        return finalState;
    }
}
