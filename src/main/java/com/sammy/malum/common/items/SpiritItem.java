package com.sammy.malum.common.items;

import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.item.Item;

public class SpiritItem extends Item
{
    public MalumSpiritType type;
    public SpiritItem(Properties properties)
    {
        super(properties);
    }
}
