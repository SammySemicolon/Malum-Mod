package com.sammy.malum.common.item;

import com.sammy.malum.core.mod_systems.spirit.ISpiritEntityGlow;
import net.minecraft.item.Item;

import java.awt.*;

public class ConfinedBrillianceItem extends Item implements ISpiritEntityGlow
{
    public ConfinedBrillianceItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Color getColor()
    {
        return new Color(31, 175, 18);
    }
}
