package com.sammy.malum.common.item.ether;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.BlockItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.awt.*;

import net.minecraft.item.Item.Properties;

public abstract class AbstractEtherItem extends BlockItem implements IDyeableArmorItem
{
    public static final String firstColor = "firstColor";
    public static final String secondColor = "secondColor";
    public static final int defaultFirstColor = 15712278;
    public static final int defaultSecondColor = 4607909;

    public final boolean iridescent;

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
        CompoundNBT compoundnbt = stack.getTagElement("display");

        return compoundnbt != null && compoundnbt.contains(secondColor, 99) ? compoundnbt.getInt(secondColor) : defaultSecondColor;
    }
    public void setSecondColor(ItemStack stack, int color)
    {
        if (iridescent)
        {
            stack.getOrCreateTagElement("display").putInt(secondColor, color);
        }
    }

    public int getFirstColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains(firstColor, 99) ? compoundnbt.getInt(firstColor) : defaultFirstColor;
    }
    public void setFirstColor(ItemStack stack, int color)
    {
        stack.getOrCreateTagElement("display").putInt(firstColor, color);
    }


    @Override
    public int getColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains(colorLookup(), 99) ? compoundnbt.getInt(colorLookup()) : defaultFirstColor;
    }
    @Override
    public boolean hasCustomColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getTagElement("display");
        return compoundnbt != null && compoundnbt.contains(colorLookup(), 99);
    }
    @Override
    public void clearColor(ItemStack stack)
    {
        CompoundNBT compoundnbt = stack.getTagElement("display");
        if (compoundnbt != null && compoundnbt.contains(colorLookup()))
        {
            compoundnbt.remove(colorLookup());
        }
    }

    @Override
    public void setColor(ItemStack stack, int color)
    {
        stack.getOrCreateTagElement("display").putInt(colorLookup(), color);
    }
}