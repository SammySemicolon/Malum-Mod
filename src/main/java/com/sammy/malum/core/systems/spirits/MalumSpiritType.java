package com.sammy.malum.core.systems.spirits;

import com.sammy.malum.common.items.SpiritItem;
import net.minecraft.entity.LivingEntity;

import java.awt.*;

public class MalumSpiritType
{
    public final Color color;
    public final String identifier;
    public final String translationKey;
    public final String description;
    public final CountPredicate countPredicate;
    public SpiritItem splinterItem;
    public MalumSpiritType(String identifier, CountPredicate countPredicate, Color color, SpiritItem splinterItem)
    {
        this.identifier = identifier;
        this.countPredicate = countPredicate;
        this.color = color;
        
        this.translationKey = "malum.tooltip.spirit." + identifier;
        this.description = translationKey + "_description";
        
        this.splinterItem = splinterItem;
        splinterItem.type = this;
    }
    public int spiritCount(LivingEntity entity)
    {
        return countPredicate.spiritCount(entity);
    }
}
