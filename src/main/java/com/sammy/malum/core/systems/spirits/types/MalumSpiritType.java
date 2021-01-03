package com.sammy.malum.core.systems.spirits.types;

import com.sammy.malum.core.systems.spirits.CountPredicate;
import com.sammy.malum.core.systems.spirits.item.SpiritSplinterItem;
import net.minecraft.entity.LivingEntity;

import java.awt.*;
import java.util.function.Predicate;

public class MalumSpiritType
{
    public final Color color;
    public final String splinter;
    public final String identifier;
    public final String translationKey;
    public final String description;
    public final Predicate<LivingEntity> predicate;
    public final CountPredicate countPredicate;
    
    
    public SpiritSplinterItem splinterItem;
    public MalumSpiritType(String identifier, Predicate<LivingEntity> predicate, CountPredicate countPredicate, Color color, String splinter)
    {
        this.identifier = identifier;
        this.predicate = predicate;
        this.countPredicate = countPredicate;
        this.color = color;
        this.splinter = splinter;
        
        this.translationKey = "malum.tooltip.spirit." + identifier;
        this.description = translationKey + "_description";
    }
    public int spiritCount(LivingEntity entity)
    {
        return countPredicate.spiritCount(entity);
    }
}
