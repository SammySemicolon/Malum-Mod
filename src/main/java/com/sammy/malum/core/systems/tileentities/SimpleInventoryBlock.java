package com.sammy.malum.core.systems.tileentities;

import com.sammy.malum.MalumHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

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
}
