package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;

public class RiteOfDeath extends AffectEntitiesRite implements IPoppetBlessing
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
        entity.addPotionEffect(new EffectInstance(MalumEffects.DEATH.get(), 400, 1));
    }
    
    @Override
    public void blessingEffect(PlayerEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(MalumEffects.DEATH.get(), 400, 0));
    
    }
}
