package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class RiteOfLevitation extends AffectEntitiesRite implements IPoppetBlessing
{
    public RiteOfLevitation(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public int cooldown()
    {
        return 10;
    }
    
    @Override
    public int range()
    {
        return 1;
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(Effects.LEVITATION,20,8));
    }
    
    @Override
    public void blessingEffect(PlayerEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(Effects.LEVITATION, 20, 0));
    
    }
}
