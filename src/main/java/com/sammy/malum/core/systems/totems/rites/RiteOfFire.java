package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class RiteOfFire extends AffectEntitiesRite implements IPoppetBlessing
{
    public RiteOfFire(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.setFire(2);
    }
    
    @Override
    public void blessingEffect(PlayerEntity entity)
    {
        entity.setFire(2);
    }
}