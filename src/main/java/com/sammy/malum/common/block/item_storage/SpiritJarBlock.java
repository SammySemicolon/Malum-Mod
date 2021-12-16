package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.common.tile.SpiritJarTileEntity;
import com.sammy.malum.core.registry.misc.ParticleRegistry;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.ILevel;
import net.minecraft.world.level.Level;
import net.minecraft.Level.server.ServerLevel;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;

public class SpiritJarBlock extends Block implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final VoxelShape SHAPE = Stream.of(Block.box(2.5, 0.5, 2.5, 13.5, 13.5, 13.5), Block.box(3.5, 14.5, 3.5, 12.5, 16.5, 12.5), Block.box(4.5, 13.5, 4.5, 11.5, 14.5, 11.5), Block.box(5.5, 0, 5.5, 10.5, 1, 10.5)).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
    public SpiritJarBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }
    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player)
    {
        if (level instanceof ServerLevel)
        {
            spawnAfterBreak(state, (ServerLevel) level, pos, player.getUseItem());
        }
        super.playerWillDestroy(level, pos, state, player);
    }
    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack)
    {
        if (level.getBlockEntity(pos) instanceof SpiritJarTileEntity)
        {
            SpiritJarTileEntity tileEntity = (SpiritJarTileEntity) level.getBlockEntity(pos);
            while (tileEntity.count > 0)
            {
                int stackCount = Math.min(tileEntity.count, 64);
                level.addFreshEntity(new ItemEntity(level,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,new ItemStack(tileEntity.type.splinterItem(), stackCount)));
                tileEntity.count -= stackCount;
            }
        }
        super.spawnAfterBreak(state, level, pos, stack);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        ItemStack heldItem = player.getItemInHand(handIn);
        if (level.getBlockEntity(pos) instanceof SpiritJarTileEntity)
        {
            SpiritJarTileEntity tileEntity = (SpiritJarTileEntity) level.getBlockEntity(pos);
            if (heldItem.getItem() instanceof MalumSpiritItem)
            {
                MalumSpiritItem spiritSplinterItem = (MalumSpiritItem) heldItem.getItem();
                if (tileEntity.type == null)
                {
                    tileEntity.type = spiritSplinterItem.type;
                    tileEntity.count = heldItem.getCount();
                    player.setItemInHand(handIn, ItemStack.EMPTY);
                    particles(level,hit, tileEntity.type);
                    return InteractionResult.SUCCESS;
                }
                else if (tileEntity.type.equals(spiritSplinterItem.type))
                {
                    tileEntity.count += heldItem.getCount();
                    player.setItemInHand(handIn, ItemStack.EMPTY);
                    particles(level,hit, tileEntity.type);
                    return InteractionResult.SUCCESS;
                }
            }
            else if (tileEntity.type != null)
            {
                int max = player.isShiftKeyDown() ? 64 : 1;
                int count = Math.min(tileEntity.count, max);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(tileEntity.type.splinterItem(), count));
                tileEntity.count -= count;
                particles(level,hit, tileEntity.type);
                if (tileEntity.count == 0)
                {
                    tileEntity.type = null;
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.use(state, level, pos, player, handIn, hit);
    }
    public void particles(Level level, BlockHitResult hit, MalumSpiritType type)
    {
        if (MalumHelper.areWeOnClient(Level))
        {
            Color color = type.color;
            RenderUtilities.create(ParticleRegistry.WISP_PARTICLE)
                    .setAlpha(0.4f, 0f)
                    .setLifetime(20)
                    .setScale(0.3f, 0)
                    .setSpin(0.2f)
                    .randomOffset(0.1f, 0.1f)
                    .setColor(color, color.darker())
                    .enableNoClip()
                    .repeat(Level, hit.getLocation().x(), hit.getLocation().y, hit.getLocation().z, 10);
        }
    }
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public BlockEntity createTileEntity(BlockState state, IBlockReader Level)
    {
        return new SpiritJarTileEntity();
    }
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, ILevel level, BlockPos currentPos, BlockPos facingPos)
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
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }
}
