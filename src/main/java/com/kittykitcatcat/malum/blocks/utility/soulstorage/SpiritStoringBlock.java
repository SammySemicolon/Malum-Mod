package com.kittykitcatcat.malum.blocks.utility.soulstorage;

import com.kittykitcatcat.malum.SpiritStorage;
import com.kittykitcatcat.malum.SpiritDataHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpiritStoringBlock extends Block implements SpiritStorage
{
    public SpiritStoringBlock(Properties properties)
    {
        super(properties);
    }
    @Override
    public int capacity()
    {
        return 0;
    }
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!isMoving)
        {
            if (worldIn.getTileEntity(pos) instanceof SpiritStoringTileEntity)
            {
                SpiritStoringTileEntity tileEntity = (SpiritStoringTileEntity) worldIn.getTileEntity(pos);
                ItemStack stack = new ItemStack(state.getBlock().asItem());
                stack.setTag(new CompoundNBT());
                for (int i = 0; i < tileEntity.count; i++)
                {
                    SpiritDataHelper.increaseSpiritOfItem(stack, tileEntity.type);
                }
                Entity entity = new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() + 0.9f, pos.getZ() + 0.5f, stack);
                worldIn.addEntity(entity);
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
}