package com.sammy.malum.core.systems.spirits.types;

import net.minecraft.entity.LivingEntity;

import java.util.function.Predicate;

public class MalumSpiritType
{
    public final String identifier;
    public final String translationKey;
    public final String description;
    public final Predicate<LivingEntity> predicate;
    public MalumSpiritType(String identifier, Predicate<LivingEntity> predicate)
    {
        this.identifier = identifier;
        this.translationKey = "malum.tooltip.spirit." + identifier;
        this.description = "malum.tooltip.spirit." + identifier + "_description";
        this.predicate = predicate;
    }
}
