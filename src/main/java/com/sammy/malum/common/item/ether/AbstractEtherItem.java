package com.sammy.malum.common.item.ether;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public abstract class AbstractEtherItem extends BlockItem implements IDyeableArmorItem
{
    public static final String firstColor = "firstColor";
    public static final String secondColor = "secondColor";
    private final boolean iridescent;

    public AbstractEtherItem(Block blockIn, Properties builder, boolean iridescent)
    {
        super(blockIn, builder);
        this.iridescent = iridescent;
    }
    public String colorLookup()
    {
        return iridescent ? secondColor : firstColor;
    }



    public int getSecondColor(ItemStack stack)
    {
        if (!iridescent)
        {
            return getFirstColor(stack);
        }
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains(secondColor, 99) ? compoundnbt.getInt(secondColor) : 1459183;
    }
    public void setSecondColor(ItemStack stack, int color)
    {
        if (iridescent)
        {
            stack.getOrCreateChildTag("display").putInt(secondColor, color);
        }
    }

    public int getFirstColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains(firstColor, 99) ? compoundnbt.getInt(firstColor) : 15712278;
    }
    public void setFirstColor(ItemStack stack, int color)
    {
        stack.getOrCreateChildTag("display").putInt(firstColor, color);
    }


    @Override
    public int getColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains(colorLookup(), 99) ? compoundnbt.getInt(colorLookup()) : 15712278;
    }
    @Override
    public boolean hasColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        return compoundnbt != null && compoundnbt.contains(colorLookup(), 99);
    }
    @Override
    public void removeColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getChildTag("display");
        if (compoundnbt != null && compoundnbt.contains(colorLookup()))
        {
            compoundnbt.remove(colorLookup());
        }
    }

    @Override
    public void setColor(ItemStack stack, int color)
    {
        stack.getOrCreateChildTag("display").putInt(colorLookup(), color);
    }
}