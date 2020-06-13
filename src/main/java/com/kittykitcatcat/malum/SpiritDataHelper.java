package com.kittykitcatcat.malum;

import com.kittykitcatcat.malum.blocks.utility.soulstorage.SoulStoringTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class SpiritDataHelper
{
    public static String countNBT = "malum:spiritCount";
    public static String typeNBT = "malum:spiritType";

    public static boolean doesStorageHaveSpirit(World world, Block block, BlockPos pos)
    {
        if (block instanceof SoulStorage)
        {
            if (world.getTileEntity(pos) instanceof SoulStoringTileEntity)
            {
                SoulStoringTileEntity tileEntity = (SoulStoringTileEntity) world.getTileEntity(pos);
                if (tileEntity.type != null)
                {
                    if (tileEntity.count > 0)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static void harvestSpirit(PlayerEntity player, String spirit, int amount)
    {
        int i = 0;
        if (player.inventory.getCurrentItem().getItem() instanceof SoulStorage)
        {
            if (((SoulStorage) player.inventory.getCurrentItem().getItem()).capacity() != i)
            {
                increaseSpiritOfItem(player.inventory.getCurrentItem(), 1, ((SoulStorage) player.inventory.getCurrentItem().getItem()).capacity(), spirit);
                i++;
                if (i >= amount)
                {
                    return;
                }
            }
        }
        for (ItemStack stack : player.inventory.mainInventory)
        {
            if (stack.getItem() instanceof SoulStorage)
            {
                increaseSpiritOfItem(stack, 1, ((SoulStorage) stack.getItem()).capacity(),spirit);
                i++;
                if (i >= amount)
                {
                    return;
                }
            }
        }
    }
    public static void increaseSpiritOfItem(ItemStack stack, int amount, int cap, String spirit)
    {
        if (stack.getTag() != null)
        {
            CompoundNBT nbt = stack.getTag();
            if (doesItemHaveSpirit(stack))
            {
                if (doesItemHaveSpirit(stack, spirit))
                {
                    nbt.putInt(countNBT, Math.min(nbt.getInt(countNBT) + amount, cap));
                }
            }
            else
            {
                nbt.putString(typeNBT, spirit);
                nbt.putInt(countNBT, Math.min(nbt.getInt(countNBT) + amount, cap));
            }
        }
    }
    public static boolean doesItemHaveSpirit(ItemStack stack, String spirit)
    {
        if (stack.getTag() != null)
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains(typeNBT))
            {
                if (nbt.getString(typeNBT).equals(spirit))
                {
                    return nbt.getInt(countNBT) > 0;
                }
            }
        }
        return false;
    }
    public static boolean doesItemHaveSpirit(ItemStack stack)
    {
        if (stack.getTag() != null)
        {
            CompoundNBT nbt = stack.getTag();
            if (nbt.contains(typeNBT))
            {
                return nbt.getInt(countNBT) > 0;
            }
        }
        return false;
    }
}