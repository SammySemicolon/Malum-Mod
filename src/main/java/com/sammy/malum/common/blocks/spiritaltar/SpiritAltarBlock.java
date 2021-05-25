package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.itemstand.ItemStandBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class SpiritAltarBlock extends Block implements IWaterLoggable
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public SpiritAltarBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
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
        if (worldIn.getTileEntity(pos) instanceof SpiritAltarTileEntity)
        {
            SpiritAltarTileEntity tileEntity = (SpiritAltarTileEntity) worldIn.getTileEntity(pos);
            worldIn.addEntity(new ItemEntity(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,tileEntity.inventory.getStackInSlot(0)));
            for (ItemStack itemStack : tileEntity.spiritInventory.stacks())
            {
                worldIn.addEntity(new ItemEntity(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,itemStack));
            }
            for (ItemStack itemStack : tileEntity.extrasInventory.stacks())
            {
                worldIn.addEntity(new ItemEntity(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,itemStack));
            }
        }
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
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
            if (worldIn.getTileEntity(pos) instanceof SpiritAltarTileEntity)
            {
                SpiritAltarTileEntity tileEntity = (SpiritAltarTileEntity) worldIn.getTileEntity(pos);
                ItemStack heldStack = player.getHeldItemMainhand();
                if (heldStack.getItem().equals(MalumItems.HEX_ASH.get()) && !tileEntity.inventory.getStackInSlot(0).isEmpty())
                {
                    if (!tileEntity.spedUp)
                    {
                        heldStack.shrink(1);
                        tileEntity.progress = 0;
                        tileEntity.spedUp = true;
                        worldIn.playSound(null, pos, MalumSounds.ALTAR_SPEED_UP, SoundCategory.BLOCKS,1,0.9f + worldIn.rand.nextFloat() * 0.2f);
                        MalumHelper.updateState(worldIn, pos);
                        return ActionResultType.SUCCESS;
                    }
                    return ActionResultType.PASS;
                }
                if (!(heldStack.getItem() instanceof SpiritItem))
                {
                    boolean success = tileEntity.inventory.playerHandleItem(worldIn, player, handIn);
                    if (success)
                    {
                        return ActionResultType.SUCCESS;
                    }
                }
                if (heldStack.getItem() instanceof SpiritItem || heldStack.isEmpty())
                {
                    boolean success = tileEntity.spiritInventory.playerHandleItem(worldIn, player, handIn);
                    if (success)
                    {
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SpiritAltarTileEntity();
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
        return getDefaultState().with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }
}