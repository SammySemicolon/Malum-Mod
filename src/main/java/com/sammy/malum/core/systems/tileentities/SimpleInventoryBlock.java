package com.sammy.malum.core.systems.tileentities;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.itemfocus.ItemFocusTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class SimpleInventoryBlock extends Block
{
    public SimpleInventoryBlock(Properties properties)
    {
        super(properties);
    }
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
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
            if (worldIn.getTileEntity(pos) instanceof SimpleInventoryTileEntity)
            {
                ((SimpleInventoryTileEntity) worldIn.getTileEntity(pos)).inventory.playerHandleItem(worldIn, player, handIn);
                MalumHelper.updateState(worldIn, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
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
        if (worldIn.getTileEntity(pos) instanceof SimpleInventoryTileEntity)
        {
            SimpleInventoryTileEntity tileEntity = (SimpleInventoryTileEntity) worldIn.getTileEntity(pos);
            for (ItemStack itemStack : tileEntity.inventory.stacks())
            {
                worldIn.addEntity(new ItemEntity(worldIn,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,itemStack));
            }
        }
        super.spawnAdditionalDrops(state, worldIn, pos, stack);
    }
}
