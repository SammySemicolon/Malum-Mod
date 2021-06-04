package com.sammy.malum.common.blocks.spiritpipe;

import com.google.common.collect.Maps;
import com.sammy.malum.MalumHelper;
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

public class SpiritPipeBlock extends Block
{
    public SpiritPipeBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(NORTH, false).with(SOUTH, false).with(WEST, false).with(EAST, false).with(DOWN, false).with(UP, false));
        this.shapes = this.makeShapes();
    }

    //region tile entity stuff
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SpiritPipeTileEntity();
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        if (worldIn.getTileEntity(fromPos) instanceof SpiritPipeTileEntity)
        {
            SpiritPipeTileEntity tileEntity = (SpiritPipeTileEntity) worldIn.getTileEntity(fromPos);
            if (!tileEntity.needsUpdate)
            {
                tileEntity.needsUpdate = true;
                MalumHelper.updateAndNotifyState(worldIn, pos);
            }
        }
        worldIn.setBlockState(pos, figureOutState(worldIn, pos));
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    //endregion

    //region properties
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    public static final BooleanProperty UP = BooleanProperty.create("up");

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        super.fillStateContainer(builder);
        builder.add(NORTH);
        builder.add(SOUTH);
        builder.add(WEST);
        builder.add(EAST);
        builder.add(DOWN);
        builder.add(UP);
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
    public static final Map<Direction, BooleanProperty> FACING_TO_CONNECTION_MAP = Util.make(Maps.newEnumMap(Direction.class), (directions) ->
    {
        directions.put(Direction.NORTH, NORTH);
        directions.put(Direction.EAST, EAST);
        directions.put(Direction.SOUTH, SOUTH);
        directions.put(Direction.WEST, WEST);
        directions.put(Direction.UP, UP);
        directions.put(Direction.DOWN, DOWN);
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

    public BlockState figureOutState(World world, BlockPos pos)
    {
        Predicate<TileEntity> tileEntityPredicate = t -> t instanceof IPipeAcceptor;
        BlockState finalState = getDefaultState();
        TileEntity north = world.getTileEntity(pos.north());
        TileEntity south = world.getTileEntity(pos.south());
        TileEntity west = world.getTileEntity(pos.west());
        TileEntity east = world.getTileEntity(pos.east());
        TileEntity down = world.getTileEntity(pos.down());
        TileEntity up = world.getTileEntity(pos.up());
        if (tileEntityPredicate.test(north))
        {
            finalState = finalState.with(NORTH, true);
        }
        if (tileEntityPredicate.test(south))
        {
            finalState = finalState.with(SOUTH, true);
        }
        if (tileEntityPredicate.test(west))
        {
            finalState = finalState.with(WEST, true);
        }
        if (tileEntityPredicate.test(east))
        {
            finalState = finalState.with(EAST, true);
        }
        if (tileEntityPredicate.test(down))
        {
            finalState = finalState.with(DOWN, true);
        }
        if (tileEntityPredicate.test(up))
        {
            finalState = finalState.with(UP, true);
        }
        return finalState;
    }
    //endregion
}