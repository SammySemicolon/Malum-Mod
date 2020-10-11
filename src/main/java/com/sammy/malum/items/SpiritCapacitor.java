package com.sammy.malum.items;

import com.sammy.malum.SpiritStorage;
import net.minecraft.item.Item;

public class SpiritCapacitor extends Item implements SpiritStorage
{
    public SpiritCapacitor(Properties builder)
    {
        super(builder);
    }

    @Override
    public int capacity()
    {
        return 5;
    }
}