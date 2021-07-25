package com.sammy.malum.common.item;

import com.sammy.malum.core.systems.spirit.ISpiritEntityGlow;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.item.Item;

import java.awt.*;

public class SpiritItem extends Item implements ISpiritEntityGlow
{
    public MalumSpiritType type;
    public SpiritItem(Properties properties, MalumSpiritType type)
    {
        super(properties);
        this.type = type;
    }

    @Override
    public Color getColor()
    {
        return type.color;
    }
}
