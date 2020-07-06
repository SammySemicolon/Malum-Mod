package com.kittykitcatcat.malum.items;

import com.kittykitcatcat.malum.SpiritStorage;
import net.minecraft.item.Item;

public class SpiritSock extends Item implements SpiritStorage
{
    public SpiritSock(Properties builder)
    {
        super(builder);
    }

    @Override
    public int capacity()
    {
        return 1;
    }
}