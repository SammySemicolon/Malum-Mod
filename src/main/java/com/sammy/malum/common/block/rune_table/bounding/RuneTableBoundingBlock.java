package com.sammy.malum.common.block.rune_table.bounding;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.tile.RuneTableBoundingBlockTileEntity;
import com.sammy.malum.core.mod_systems.multiblock.BoundingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class RuneTableBoundingBlock extends BoundingBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final VoxelShape SOUTH = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(3, 0, -1, 13, 9, 12), Block.makeCuboidShape(1, 9, -1, 15, 15, 14), IBooleanFunction.OR);
    public static final VoxelShape WEST = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4, 0, 3, 17, 9, 13), Block.makeCuboidShape(2, 9, 1, 17, 15, 15), IBooleanFunction.OR);
    public static final VoxelShape NORTH = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(3, 0, 4, 13, 9, 17), Block.makeCuboidShape(1, 9, 2, 15, 15, 17), IBooleanFunction.OR);
    public static final VoxelShape EAST = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(-1, 0, 3, 12, 9, 13), Block.makeCuboidShape(-1, 9, 1, 14, 15, 15), IBooleanFunction.OR);
    public RuneTableBoundingBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (MalumHelper.areWeOnClient(worldIn))
        {
            return ActionResultType.SUCCESS;
        }
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (worldIn.getTileEntity(pos) instanceof RuneTableBoundingBlockTileEntity)
            {
                ((RuneTableBoundingBlockTileEntity) worldIn.getTileEntity(pos)).inventory.playerHandleItem(worldIn, player, handIn);
                MalumHelper.updateState(worldIn, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if (worldIn instanceof ServerWorld)
        {
            spawnAdditionalDrops(state, (ServerWorld) worldIn, pos, player.getActiveItemStack());
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack)
    {
        if (worldIn.getTileEntity(pos) instanceof RuneTableBoundingBlockTileEntity)
        {
            RuneTableBoundingBlockTileEntity tileEntity = (RuneTableBoundingBlockTileEntity) worldIn.getTileEntity(pos);
            for (ItemStack itemStack : tileEntity.inventory.stacks())
            {
                worldIn.addEntity(new ItemEntity(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,itemStack));
            }
        }
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        switch (state.get(HORIZONTAL_FACING))
        {
            case NORTH:
            {
                return NORTH;
            }
            case EAST:
            {
                return EAST;
            }
            case SOUTH:
            {
                return SOUTH;
            }
            case WEST:
            {
                return WEST;
            }
        }
        return super.getShape(state, worldIn, pos, context);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new RuneTableBoundingBlockTileEntity();
    }
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
        builder.add(HORIZONTAL_FACING);
        super.fillStateContainer(builder);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.get(WATERLOGGED))
        {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        return getDefaultState().with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER).with(HORIZONTAL_FACING, Direction.NORTH);
    }
}
