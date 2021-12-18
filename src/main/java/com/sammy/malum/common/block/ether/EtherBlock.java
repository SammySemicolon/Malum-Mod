package com.sammy.malum.common.block.ether;

import com.sammy.malum.core.helper.ClientHelper;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.blockentity.EtherTileEntity;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
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

public class EtherBlock extends SimpleBlock<EtherTileEntity> implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public EtherBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
        setTile(BlockEntityRegistry.ETHER_BLOCK_BLOCK_ENTITY);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (level.getBlockEntity(pos) instanceof EtherTileEntity) {
            EtherTileEntity blockEntity = (EtherTileEntity) level.getBlockEntity(pos);
            AbstractEtherItem item = (AbstractEtherItem) stack.getItem();
            blockEntity.firstColor = ClientHelper.getColor(item.getFirstColor(stack));
            blockEntity.secondColor = ClientHelper.getColor(item.getSecondColor(stack));
            blockEntity.setChanged();
        }
        super.setPlacedBy(level, pos, state, placer, stack);
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = asItem().getDefaultInstance();
        if (level.getBlockEntity(pos) instanceof EtherTileEntity) {
            EtherTileEntity blockEntity = (EtherTileEntity) level.getBlockEntity(pos);
            AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
            if (blockEntity.firstColor != null) {
                etherItem.setFirstColor(stack, blockEntity.firstColor.getRGB());
            }
            if (blockEntity.secondColor != null) {
                etherItem.setSecondColor(stack, blockEntity.secondColor.getRGB());
            }
            blockEntity.setChanged();
        }
        return stack;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_152803_) {
        FluidState fluidstate = p_152803_.getLevel().getFluidState(p_152803_.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(p_152803_).setValue(WATERLOGGED, flag);
    }

    @Override
    public BlockState updateShape(BlockState p_152833_, Direction p_152834_, BlockState p_152835_, LevelAccessor p_152836_, BlockPos p_152837_, BlockPos p_152838_) {
        if (p_152833_.getValue(WATERLOGGED)) {
            p_152836_.scheduleTick(p_152837_, Fluids.WATER, Fluids.WATER.getTickDelay(p_152836_));
        }
        return super.updateShape(p_152833_, p_152834_, p_152835_, p_152836_, p_152837_, p_152838_);
    }

    @Override
    public FluidState getFluidState(BlockState p_152844_) {
        return p_152844_.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(p_152844_);
    }
}