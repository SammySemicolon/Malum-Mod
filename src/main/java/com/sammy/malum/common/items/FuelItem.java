package com.sammy.malum.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FuelItem extends Item
{
    public final int fuel;
    public FuelItem(Properties properties, int fuel)
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
