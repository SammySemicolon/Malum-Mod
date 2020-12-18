package com.sammy.malum.common.blocks.spiritjar;

import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.block.SpiritHolderBlock;
import com.sammy.malum.core.systems.spirits.block.ISpiritTransferTileEntity;
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

public abstract class AbstractSpiritJarBlock extends SpiritHolderBlock
{
    //region properties
    public static final IntegerProperty UP = IntegerProperty.create("up", 0, 2);
    public static final BooleanProperty NORTH_CONNECTION = BooleanProperty.create("north_connection");
    public static final BooleanProperty SOUTH_CONNECTION = BooleanProperty.create("south_connection");
    public static final BooleanProperty WEST_CONNECTION = BooleanProperty.create("west_connection");
    public static final BooleanProperty EAST_CONNECTION = BooleanProperty.create("east_connection");
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(UP);
        builder.add(NORTH_CONNECTION);
        builder.add(SOUTH_CONNECTION);
        builder.add(WEST_CONNECTION);
        builder.add(EAST_CONNECTION);
        super.fillStateContainer(builder);
    }
    //endregion
    
    //region shapes
    public static final VoxelShape base = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4, 0, 4, 12, 12, 12), Block.makeCuboidShape(5.5, 12, 5.5, 10.5, 14, 10.5), IBooleanFunction.OR);
    public static final VoxelShape up = Stream.of(Block.makeCuboidShape(12, 0, 12, 15, 4, 15), Block.makeCuboidShape(4, 0, 4, 12, 12, 12), Block.makeCuboidShape(5.5, 12, 5.5, 10.5, 14, 10.5), Block.makeCuboidShape(1, 10, 12, 4, 14, 15), Block.makeCuboidShape(0, 4, 12, 4, 10, 16), Block.makeCuboidShape(1, 0, 12, 4, 4, 15), Block.makeCuboidShape(1, 10, 1, 4, 14, 4), Block.makeCuboidShape(0, 4, 0, 4, 10, 4), Block.makeCuboidShape(1, 0, 1, 4, 4, 4), Block.makeCuboidShape(12, 10, 1, 15, 14, 4), Block.makeCuboidShape(12, 4, 0, 16, 10, 4), Block.makeCuboidShape(12, 0, 1, 15, 4, 4), Block.makeCuboidShape(0, 14, 0, 16, 16, 16), Block.makeCuboidShape(12, 10, 12, 15, 14, 15), Block.makeCuboidShape(12, 4, 12, 16, 10, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    public static final VoxelShape up_connection = Block.makeCuboidShape(4, 12, 4, 12, 16, 12);
    public static final VoxelShape north = Block.makeCuboidShape(5, 5, 0, 11, 11, 4);
    public static final VoxelShape east = Block.makeCuboidShape(12, 5, 5, 16, 11, 11);
    public static final VoxelShape south = Block.makeCuboidShape(5, 5, 12, 11, 11, 16);
    public static final VoxelShape west = Block.makeCuboidShape(0, 5, 5, 4, 11, 11);
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        VoxelShape shape = base;
        
        if (state.get(UP) == 1)
        {
            shape = VoxelShapes.or(shape, up);
        }
        if (state.get(UP) == 2)
        {
            shape = VoxelShapes.or(shape, up_connection);
        }
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
    
    public AbstractSpiritJarBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(UP, 0).with(NORTH_CONNECTION, false).with(SOUTH_CONNECTION, false).with(WEST_CONNECTION, false).with(EAST_CONNECTION, false));
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
        Predicate<TileEntity> tileEntityPredicate = SpiritHelper::connectsToHolders;
        BlockState finalState = getDefaultState();
        TileEntity north = world.getTileEntity(pos.north());
        TileEntity south = world.getTileEntity(pos.south());
        TileEntity west = world.getTileEntity(pos.west());
        TileEntity east = world.getTileEntity(pos.east());
        TileEntity up = world.getTileEntity(pos.up());
        if (tileEntityPredicate.test(north))
        {
            finalState = finalState.with(NORTH_CONNECTION, true);
        }
        if (tileEntityPredicate.test(south))
        {
            finalState = finalState.with(SOUTH_CONNECTION, true);
        }
        if (tileEntityPredicate.test(west))
        {
            finalState = finalState.with(WEST_CONNECTION, true);
        }
        if (tileEntityPredicate.test(east))
        {
            finalState = finalState.with(EAST_CONNECTION, true);
        }
        if (up instanceof ISpiritTransferTileEntity)
        {
            finalState = finalState.with(UP, 2);
        }
        else if (!world.getBlockState(pos.up()).isAir())
        {
            finalState = finalState.with(UP, 1);
        }
        else
        {
            finalState = finalState.with(UP, 0);
        }
        return finalState;
    }
}
