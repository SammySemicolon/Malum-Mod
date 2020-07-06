package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.SpiritStorage;
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