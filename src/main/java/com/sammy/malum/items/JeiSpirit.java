package com.sammy.malum.items;

import com.sammy.malum.SpiritStorage;
import net.minecraft.item.Item;

public class JeiSpirit extends Item implements SpiritStorage
{
    public JeiSpirit(Properties builder)
    {
        super(builder);
    }
    
    @Override
    public int capacity()
    {
        return -1;
    }
}