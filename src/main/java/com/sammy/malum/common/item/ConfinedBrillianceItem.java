package com.sammy.malum.common.item;

import com.sammy.malum.MalumColors;
import com.sammy.malum.core.systems.spirit.ISpiritEntityGlow;
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
        return MalumColors.GREEN.brighter();
    }
}
