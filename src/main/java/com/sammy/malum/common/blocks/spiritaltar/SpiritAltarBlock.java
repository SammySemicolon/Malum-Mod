package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SpiritAltarBlock extends Block
{
    public SpiritAltarBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
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
                        tileEntity.spedUp = true;
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
    
}