package com.sammy.malum.common.block.spirit_altar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.common.tile.SpiritAltarTileEntity;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.Level.IBlockReader;
import net.minecraft.Level.ILevel;
import net.minecraft.Level.Level;
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
    public void playerWillDestroy(Level LevelIn, BlockPos pos, BlockState state, Player player)
    {
        if (LevelIn instanceof ServerLevel)
        {
            spawnAfterBreak(state, (ServerLevel) LevelIn, pos, player.getUseItem());
        }
        super.playerWillDestroy(LevelIn, pos, state, player);
    }
    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel LevelIn, BlockPos pos, ItemStack stack)
    {
        if (LevelIn.getBlockEntity(pos) instanceof SpiritAltarTileEntity)
        {
            SpiritAltarTileEntity tileEntity = (SpiritAltarTileEntity) LevelIn.getBlockEntity(pos);
            LevelIn.addFreshEntity(new ItemEntity(LevelIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,tileEntity.inventory.getStackInSlot(0)));
            for (ItemStack itemStack : tileEntity.spiritInventory.stacks())
            {
                LevelIn.addFreshEntity(new ItemEntity(LevelIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,itemStack));
            }
            for (ItemStack itemStack : tileEntity.extrasInventory.stacks())
            {
                LevelIn.addFreshEntity(new ItemEntity(LevelIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,itemStack));
            }
        }
        super.spawnAfterBreak(state, LevelIn, pos, stack);
    }
    @Override
    public ActionResultType use(BlockState state, Level LevelIn, BlockPos pos, Player player, Hand handIn, BlockRayTraceResult hit)
    {
        if (MalumHelper.areWeOnClient(LevelIn))
        {
            return ActionResultType.SUCCESS;
        }
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (LevelIn.getBlockEntity(pos) instanceof SpiritAltarTileEntity)
            {
                SpiritAltarTileEntity tileEntity = (SpiritAltarTileEntity) LevelIn.getBlockEntity(pos);
                ItemStack heldStack = player.getMainHandItem();
                if (heldStack.getItem().equals(ItemRegistry.HEX_ASH.get()) && !tileEntity.inventory.getStackInSlot(0).isEmpty())
                {
                    if (!tileEntity.spedUp)
                    {
                        heldStack.shrink(1);
                        tileEntity.progress = 0;
                        tileEntity.spedUp = true;
                        LevelIn.playSound(null, pos, SoundRegistry.ALTAR_SPEED_UP, SoundCategory.BLOCKS,1,0.9f + LevelIn.random.nextFloat() * 0.2f);
                        MalumHelper.updateState(LevelIn, pos);
                        return ActionResultType.SUCCESS;
                    }
                    return ActionResultType.PASS;
                }
                if (!(heldStack.getItem() instanceof MalumSpiritItem))
                {
                    boolean success = tileEntity.inventory.playerHandleItem(LevelIn, player, handIn);
                    if (success)
                    {
                        return ActionResultType.SUCCESS;
                    }
                }
                if (heldStack.getItem() instanceof MalumSpiritItem || heldStack.isEmpty())
                {
                    boolean success = tileEntity.spiritInventory.playerHandleItem(LevelIn, player, handIn);
                    if (success)
                    {
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return super.use(state, LevelIn, pos, player, handIn, hit);
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader Level)
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