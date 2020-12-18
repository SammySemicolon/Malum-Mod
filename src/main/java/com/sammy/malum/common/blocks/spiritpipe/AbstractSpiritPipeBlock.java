package com.sammy.malum.common.blocks.spiritpipe;

import com.google.common.collect.Maps;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Map;
import java.util.function.Predicate;

public abstract class AbstractSpiritPipeBlock extends Block
{
    public AbstractSpiritPipeBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH_EXTENSION, false).with(SOUTH_EXTENSION, false).with(WEST_EXTENSION, false).with(EAST_EXTENSION, false).with(DOWN_EXTENSION, false).with(UP_EXTENSION, false));
        this.shapes = this.makeShapes();
    }
    //region properties
    public static final BooleanProperty NORTH_EXTENSION = BooleanProperty.create("north_extension");
    public static final BooleanProperty SOUTH_EXTENSION = BooleanProperty.create("south_extension");
    public static final BooleanProperty WEST_EXTENSION = BooleanProperty.create("west_extension");
    public static final BooleanProperty EAST_EXTENSION = BooleanProperty.create("east_extension");
    public static final BooleanProperty DOWN_EXTENSION = BooleanProperty.create("down_extension");
    public static final BooleanProperty UP_EXTENSION = BooleanProperty.create("up_extension");
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(NORTH_EXTENSION);
        builder.add(SOUTH_EXTENSION);
        builder.add(WEST_EXTENSION);
        builder.add(EAST_EXTENSION);
        builder.add(DOWN_EXTENSION);
        builder.add(UP_EXTENSION);
    }
    //endregion
    
    //region shapes
    public static final Direction[] FACING_VALUES = Direction.values();
    public static final VoxelShape core = Block.makeCuboidShape(6, 6, 6, 10, 10, 10);
    public static final VoxelShape down = Block.makeCuboidShape(6, 0, 6, 10, 6, 10);
    public static final VoxelShape up = Block.makeCuboidShape(6, 10, 6, 10, 16, 10);
    public static final VoxelShape north = Block.makeCuboidShape(6, 6, 0, 10, 10, 6);
    public static final VoxelShape south = Block.makeCuboidShape(6, 6, 10, 10, 10, 16);
    public static final VoxelShape west = Block.makeCuboidShape(0, 6, 6, 6, 10, 10);
    public static final VoxelShape east = Block.makeCuboidShape(10, 6, 6, 16, 10, 10);
    public static final Map<Direction, BooleanProperty> FACING_TO_CONNECTION_MAP = Util.make(Maps.newEnumMap(Direction.class), (directions) -> {
        directions.put(Direction.NORTH, NORTH_EXTENSION);
        directions.put(Direction.EAST, EAST_EXTENSION);
        directions.put(Direction.SOUTH, SOUTH_EXTENSION);
        directions.put(Direction.WEST, WEST_EXTENSION);
        directions.put(Direction.UP, UP_EXTENSION);
        directions.put(Direction.DOWN, DOWN_EXTENSION);
    });
    protected final VoxelShape[] shapes;
    protected VoxelShape[] makeShapes()
    {
        // 6 different state flags = 2^6 = 64 different state models (waterlogging
        // doesn't affect model)
        VoxelShape[] shapes = new VoxelShape[64];
        VoxelShape[] dunswe = {down, up, north, south, west, east};
        
        for (int i = 0; i < 64; i++)
        {
            shapes[i] = core;
            for (int j = 0; j < 6; j++)
            {
                if ((i & (1 << j)) != 0)
                {
                    shapes[i] = VoxelShapes.or(shapes[i], dunswe[j]);
                }
            }
        }
        
        return shapes;
    }
    public int getShapeIndex(BlockState state)
    {
        int index = 0;
        for (int j = 0; j < FACING_VALUES.length; ++j)
        {
            if (state.get(FACING_TO_CONNECTION_MAP.get(FACING_VALUES[j])))
            {
                index |= 1 << j;
            }
        }
        return index;
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return this.shapes[this.getShapeIndex(state)];
    }
    //endregion
    
    //region placement
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
        Predicate<TileEntity> tileEntityPredicate = SpiritHelper::isSpiritRelated;
        BlockState finalState = getDefaultState();
        TileEntity north = world.getTileEntity(pos.north());
        TileEntity south = world.getTileEntity(pos.south());
        TileEntity west = world.getTileEntity(pos.west());
        TileEntity east = world.getTileEntity(pos.east());
        TileEntity down = world.getTileEntity(pos.down());
        TileEntity up = world.getTileEntity(pos.up());
        if (tileEntityPredicate.test(north))
        {
            finalState = finalState.with(NORTH_EXTENSION, true);
        }
        if (tileEntityPredicate.test(south))
        {
            finalState = finalState.with(SOUTH_EXTENSION, true);
        }
        if (tileEntityPredicate.test(west))
        {
            finalState = finalState.with(WEST_EXTENSION, true);
        }
        if (tileEntityPredicate.test(east))
        {
            finalState = finalState.with(EAST_EXTENSION, true);
        }
        if (tileEntityPredicate.test(down))
        {
            finalState = finalState.with(DOWN_EXTENSION, true);
        }
        if (tileEntityPredicate.test(up))
        {
            finalState = finalState.with(UP_EXTENSION, true);
        }
        return finalState;
    }
    //endregion
}