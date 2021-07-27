package com.sammy.malum.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class EtherItem extends BlockItem implements IDyeableArmorItem
{
    public EtherItem(Block blockIn, Properties builder)
    {
        super(blockIn, builder);
    }

    @Override
    public boolean hasColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99);
    }

    @Override
    public int getColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains("color", 99) ? compoundnbt.getInt("color") : 15712278;
    }

    @Override
    public void removeColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        if (compoundnbt != null && compoundnbt.contains("color")) {
            compoundnbt.remove("color");
        }
    }

    @Override
    public void setColor(ItemStack stack, int color)
    {
        stack.getOrCreateChildTag("display").putInt("color", color);
    }
}