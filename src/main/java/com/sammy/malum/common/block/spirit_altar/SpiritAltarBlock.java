package com.sammy.malum.common.block.spirit_altar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.common.tile.SpiritAltarTileEntity;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.registry.item.ItemRegistry;
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
import net.minecraft.sounds.SoundSource;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.BlockHitResult;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.ILevel;
import net.minecraft.world.level.Level;
import net.minecraft.Level.server.ServerLevel;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class SpiritAltarBlock extends Block implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public SpiritAltarBlock(Properties properties)
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
        if (level.getBlockEntity(pos) instanceof SpiritAltarTileEntity)
        {
            SpiritAltarTileEntity tileEntity = (SpiritAltarTileEntity) level.getBlockEntity(pos);
            level.addFreshEntity(new ItemEntity(level,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,tileEntity.inventory.getStackInSlot(0)));
            for (ItemStack itemStack : tileEntity.spiritInventory.stacks())
            {
                level.addFreshEntity(new ItemEntity(level,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,itemStack));
            }
            for (ItemStack itemStack : tileEntity.extrasInventory.stacks())
            {
                level.addFreshEntity(new ItemEntity(level,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,itemStack));
            }
        }
        super.spawnAfterBreak(state, level, pos, stack);
    }
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit)
    {
        if (level.isClientSide)
        {
            return InteractionResult.SUCCESS;
        }
        if (handIn.equals(InteractionHand.MAIN_HAND))
        {
            if (level.getBlockEntity(pos) instanceof SpiritAltarTileEntity)
            {
                SpiritAltarTileEntity tileEntity = (SpiritAltarTileEntity) level.getBlockEntity(pos);
                ItemStack heldStack = player.getMainHandItem();
                if (heldStack.getItem().equals(ItemRegistry.HEX_ASH.get()) && !tileEntity.inventory.getStackInSlot(0).isEmpty())
                {
                    if (!tileEntity.spedUp)
                    {
                        heldStack.shrink(1);
                        tileEntity.progress = 0;
                        tileEntity.spedUp = true;
                        level.playSound(null, pos, SoundRegistry.ALTAR_SPEED_UP, SoundSource.BLOCKS,1,0.9f + level.random.nextFloat() * 0.2f);
                        MalumHelper.updateState(level, pos);
                        return InteractionResult.SUCCESS;
                    }
                    return InteractionResult.PASS;
                }
                if (!(heldStack.getItem() instanceof MalumSpiritItem))
                {
                    boolean success = tileEntity.inventory.playerHandleItem(level, player, handIn);
                    if (success)
                    {
                        return InteractionResult.SUCCESS;
                    }
                }
                if (heldStack.getItem() instanceof MalumSpiritItem || heldStack.isEmpty())
                {
                    boolean success = tileEntity.spiritInventory.playerHandleItem(level, player, handIn);
                    if (success)
                    {
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.use(state, level, pos, player, handIn, hit);
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public BlockEntity createTileEntity(BlockState state, IBlockReader Level)
    {
        return new SpiritAltarTileEntity();
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