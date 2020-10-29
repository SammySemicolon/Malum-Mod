package com.sammy.malum.items;

import com.sammy.malum.SpiritStorage;
import net.minecraft.item.Item;

public class SpiritVault extends Item implements SpiritStorage
{
    public SpiritVault(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public int capacity()
    {
        return 20;
    }
}