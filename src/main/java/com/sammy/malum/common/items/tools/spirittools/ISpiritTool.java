package com.sammy.malum.common.items.tools.spirittools;

import net.minecraft.entity.LivingEntity;

public interface ISpiritTool
{
    public default int spiritBonus(LivingEntity target)
    {
        return 0;
    }
}
