package com.sammy.malum.common.block.ether;

import com.sammy.malum.common.tile.EtherTileEntity;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class EtherBrazierBlock extends EtherBlock implements IWaterLoggable
{
    public static final VoxelShape SHAPE = Block.makeCuboidShape(4, 0, 4, 12, 8, 12);
    public static final BooleanProperty ROTATED = BooleanProperty.create("rotated");
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;

    public EtherBrazierBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HANGING, false).with(WATERLOGGED, false).with(ROTATED, false));
    
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
    
        BlockState blockstate = this.getDefaultState();
        for (Direction direction : context.getNearestLookingDirections())
        {
            if (direction.getAxis() == Direction.Axis.Y)
            {
                blockstate = this.getDefaultState().with(HANGING, direction == Direction.UP).with(ROTATED, context.getPlacementHorizontalFacing() == Direction.NORTH || context.getPlacementHorizontalFacing() == Direction.SOUTH);
                if (blockstate.isValidPosition(context.getWorld(), context.getPos()))
                {
                    return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
                }
            }
        }
        
        return blockstate;
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(HANGING, WATERLOGGED, ROTATED);
    }
    
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos)
    {
        Direction direction = getBlockConnected(state).getOpposite();
        return Block.hasEnoughSolidSide(worldIn, pos.offset(direction), direction.getOpposite());
    }
    
    protected static Direction getBlockConnected(BlockState state)
    {
        return state.get(HANGING) ? Direction.DOWN : Direction.UP;
    }
    
    @Override
    public PushReaction getPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

    
    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type)
    {
        return false;
    }
}