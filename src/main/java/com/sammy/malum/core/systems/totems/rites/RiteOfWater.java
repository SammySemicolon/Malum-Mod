package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RiteOfWater extends AffectEntitiesRite
{
    public RiteOfWater(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        if (entity instanceof PlayerEntity)
        {
            entity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 400, 0));
        }
    }
}
