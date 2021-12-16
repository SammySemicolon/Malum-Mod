package com.sammy.malum.common.block.ether;

import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.tile.EtherTileEntity;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.systems.block.SimpleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class EtherBlock extends SimpleBlock implements SimpleWaterloggedBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE =Block.box(6, 6, 6, 10, 10, 10);

    public EtherBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
        setTile(TileEntityRegistry.ETHER_BLOCK_TILE_ENTITY.get());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if (level.getBlockEntity(pos) instanceof EtherTileEntity)
        {
            EtherTileEntity tileEntity = (EtherTileEntity) level.getBlockEntity(pos);
            AbstractEtherItem item = (AbstractEtherItem) stack.getItem();
            tileEntity.firstColor = ClientHelper.getColor(item.getFirstColor(stack));
            tileEntity.secondColor = ClientHelper.getColor(item.getSecondColor(stack));
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = asItem().getDefaultInstance();
        if (level.getBlockEntity(pos) instanceof EtherTileEntity)
        {
            EtherTileEntity tileEntity = (EtherTileEntity) level.getBlockEntity(pos);
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
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos)
    {
        if (stateIn.getValue(WATERLOGGED))
        {
            level.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
}
