package com.sammy.malum.common.block.ether;

import com.sammy.malum.common.item.EtherTorchItem;
import com.sammy.malum.common.tile.EtherTileEntity;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class WallEtherTorchBlock extends WallTorchBlock implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WallEtherTorchBlock(Properties properties)
    {
        super(properties, null);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (worldIn.getTileEntity(pos) instanceof EtherTileEntity)
        {
            EtherTileEntity tileEntity = (EtherTileEntity) worldIn.getTileEntity(pos);
            EtherTorchItem item = (EtherTorchItem) stack.getItem();
            tileEntity.color = item.getColor(stack);
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        ItemStack stack = MalumItems.ETHER_TORCH.get().getDefaultInstance();
        if (world.getTileEntity(pos) instanceof EtherTileEntity)
        {
            EtherTileEntity tileEntity = (EtherTileEntity) world.getTileEntity(pos);
            EtherTorchItem etherItem = (EtherTorchItem) stack.getItem();
            etherItem.setColor(stack, tileEntity.color);
        }
        return stack;
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new EtherTileEntity();
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {

    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
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
        BlockState state = super.getStateForPlacement(context);
        if (state != null)
        {
            return state.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
        }
        else
        {
            return null;
        }
    }
}