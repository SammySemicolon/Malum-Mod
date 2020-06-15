package com.kittykitcatcat.malum;

import com.kittykitcatcat.malum.blocks.utility.soulstorage.SpiritStoringTileEntity;
import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
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

    public static boolean increaseSpiritOfStorage(SpiritStoringTileEntity tileEntity, int cap,String spirit)
    {
        if (doesStorageHaveSpirit(tileEntity))
        {
            if (doesStorageHaveSpirit(tileEntity, spirit))
            {
                if (tileEntity.count < cap)
                {
                    tileEntity.count = Math.min(tileEntity.count + 1, cap);
                    return true;
                }
            }
        }
        else
        {
            tileEntity.type = spirit;
            tileEntity.count = 1;
            return true;
        }
        return false;
    }
    public static boolean doesStorageHaveSpirit(SpiritStoringTileEntity tileEntity)
    {
        if (tileEntity.type != null)
        {
            return tileEntity.count > 0;
        }
        return false;
    }
    public static boolean doesStorageHaveSpirit(SpiritStoringTileEntity tileEntity, String spirit)
    {
        if (tileEntity.type != null)
        {
            if (tileEntity.type.equals(spirit))
            {
                return tileEntity.count > 0;
            }
        }
        return false;
    }
    public static void harvestSpirit(PlayerEntity player, String spirit, int amount)
    {
        amount+= CapabilityValueGetter.getExtraSpirits(player);
        int i = 0;
        if (player.inventory.getCurrentItem().getItem() instanceof SoulStorage)
        {
            ItemStack stack = player.inventory.getCurrentItem();
            if (stack.getOrCreateTag().getString(typeNBT).equals(spirit))
            {
                if (stack.getOrCreateTag().getInt(countNBT) < ((SoulStorage) stack.getItem()).capacity())
                {
                    for (int k = 0; k < amount; k++)
                    {
                        boolean increase = increaseSpiritOfItem(stack, spirit);
                        if (increase)
                        {
                            i++;
                            if (i >= amount)
                            {
                                return;
                            }
                        }
                    }
                }
            }
        }
        for (ItemStack stack : player.inventory.mainInventory)
        {
            if (stack.getItem() instanceof SoulStorage)
            {
                if (stack.getOrCreateTag().getInt(countNBT) < ((SoulStorage) stack.getItem()).capacity())
                {
                    for (int k = 0; k < amount; k++)
                    {
                        boolean increase = increaseSpiritOfItem(stack, spirit);
                        if (increase)
                        {
                            i++;
                            if (i >= amount)
                            {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
    public static boolean increaseSpiritOfItem(ItemStack stack,String spirit)
    {
        if (stack.getItem() instanceof SoulStorage)
        {
            if (stack.getTag() != null)
            {
                CompoundNBT nbt = stack.getTag();
                if (doesItemHaveSpirit(stack))
                {
                    if (doesItemHaveSpirit(stack, spirit))
                    {
                        if (nbt.getInt(countNBT) < ((SoulStorage) stack.getItem()).capacity())
                        {
                            nbt.putInt(countNBT, Math.min(nbt.getInt(countNBT) + 1, ((SoulStorage) stack.getItem()).capacity()));
                            return true;
                        }
                    }
                }
                else
                {
                    nbt.putString(typeNBT, spirit);
                    nbt.putInt(countNBT, 1);
                    return true;
                }
            }
        }
        return false;
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