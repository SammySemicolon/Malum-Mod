package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class RiteOfLife extends AffectEntitiesRite implements IPoppetBlessing
{
    public RiteOfLife(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        if (entity instanceof PlayerEntity)
        {
            entity.addPotionEffect(new EffectInstance(MalumEffects.LIFE.get(), 400, 1));
        }
    }
    
    @Override
    public void blessingEffect(PlayerEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(MalumEffects.LIFE.get(), 400, 0));
    }
}
