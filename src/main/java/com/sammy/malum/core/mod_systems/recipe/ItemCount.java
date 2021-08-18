package com.sammy.malum.core.mod_systems.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCount
{
    public final Item item;
    public final int count;

    public ItemCount(Item item, int count)
    {
        this.item = item;
        this.count = count;
    }

    public ItemCount(ItemStack stack)
    {
        this.item = stack.getItem();
        this.count = stack.getCount();
    }

    public ItemStack stack()
    {
        return new ItemStack(item, count);
    }

    public boolean matches(ItemStack stack)
    {
        return stack.getItem().equals(item) && stack.getCount() >= count;
    }
}
