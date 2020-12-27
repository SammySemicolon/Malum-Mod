package com.sammy.malum.core.systems.spirits.item;

import com.sammy.malum.core.systems.spirits.types.MalumSpiritType;
import net.minecraft.item.Item;

public class SpiritSplinterItem extends Item
{
    public MalumSpiritType type;
    public SpiritSplinterItem(Properties properties)
    {
        super(properties);
    }
}
