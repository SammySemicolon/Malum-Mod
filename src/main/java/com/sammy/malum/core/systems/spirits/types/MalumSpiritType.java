package com.sammy.malum.core.systems.spirits.types;

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
    
    public SpiritSplinterItem splinterItem;
    public MalumSpiritType(String identifier, Predicate<LivingEntity> predicate, Color color, String splinter)
    {
        this.splinter = splinter;
        this.color = color;
        this.identifier = identifier;
        this.translationKey = "malum.tooltip.spirit." + identifier;
        this.description = translationKey + "_description";
        this.predicate = predicate;
    }
}
