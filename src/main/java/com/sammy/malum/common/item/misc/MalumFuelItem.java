package com.sammy.malum.common.item.misc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MalumFuelItem extends Item
{
    public final int fuel;
    public MalumFuelItem(Properties properties, int fuel)
    {
        super(properties);
        this.fuel = fuel;
    }
    
    @Override
    public int getBurnTime(ItemStack itemStack)
    {
        return fuel;
    }
}
