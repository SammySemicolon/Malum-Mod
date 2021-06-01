package com.sammy.malum.common.items;

import com.sammy.malum.MalumColors;
import com.sammy.malum.core.systems.spirits.ISpiritEntityGlow;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
