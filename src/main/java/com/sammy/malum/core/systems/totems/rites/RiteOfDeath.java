package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class RiteOfDeath extends AffectEntitiesRite
{
    public RiteOfDeath(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public int cooldown()
    {
        return 10;
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.attackEntityFrom(DamageSource.MAGIC, 1);
    }
}
