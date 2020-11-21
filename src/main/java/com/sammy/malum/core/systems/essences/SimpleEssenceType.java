package com.sammy.malum.core.systems.essences;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class SimpleEssenceType
{
    public enum essenceTypeEnum
    {
        essence, distillate
    }
    
    public final String identifier;
    public final String description;
    public final Predicate<LivingEntity> entityPredicate;
    public final essenceTypeEnum type;
    public SimpleEssenceType(String identifier, String description, Predicate<LivingEntity> entityPredicate, essenceTypeEnum type)
    {
        this.identifier = identifier;
        this.description = description;
        this.entityPredicate = entityPredicate;
        this.type = type;
    }
    public boolean doesEntityHaveEssence(LivingEntity entity)
    {
        return entityPredicate.test(entity);
    }
    
    public int howMuchEssenceDoesAnEntityHave(@Nullable PlayerEntity player, LivingEntity entity)
    {
        int amount = 0;
        int treshhold = 2;
        while (treshhold < entity.getMaxHealth())
        {
            amount++;
            treshhold*=2;
        }
        return amount;
    }
}