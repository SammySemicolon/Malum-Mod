package com.sammy.malum.common.block.ether;

import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.tile.EtherTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.ILevel;
import net.minecraft.Level.Level;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class EtherBlock extends Block implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE =Block.box(6, 6, 6, 10, 10, 10);

    public EtherBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public void setPlacedBy(Level LevelIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (LevelIn.getBlockEntity(pos) instanceof EtherTileEntity)
        {
            EtherTileEntity tileEntity = (EtherTileEntity) LevelIn.getBlockEntity(pos);
            AbstractEtherItem item = (AbstractEtherItem) stack.getItem();
            tileEntity.firstColor = ClientHelper.getColor(item.getFirstColor(stack));
            tileEntity.secondColor = ClientHelper.getColor(item.getSecondColor(stack));
        }
        super.setPlacedBy(LevelIn, pos, state, placer, stack);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader Level, BlockPos pos, Player player)
    {
        ItemStack stack = asItem().getDefaultInstance();
        if (Level.getBlockEntity(pos) instanceof EtherTileEntity)
        {
            EtherTileEntity tileEntity = (EtherTileEntity) Level.getBlockEntity(pos);
            AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
            if (tileEntity.firstColor != null)
            {
                etherItem.setFirstColor(stack, tileEntity.firstColor.getRGB());
            }
            if (tileEntity.secondColor != null)
            {
                etherItem.setSecondColor(stack, tileEntity.secondColor.getRGB());
            }
        }
        return stack;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader LevelIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader Level)
    {
        return new EtherTileEntity();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
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
        return defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
}
