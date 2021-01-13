package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.modcontent.MalumRunes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

public class RiteOfWarding extends AffectEntitiesRite implements IPoppetBlessing
{
    public RiteOfWarding(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public int range()
    {
        return 20;
    }
    
    @Override
    public void effect(LivingEntity entity)
    {
        entity.addPotionEffect(new EffectInstance(MalumEffects.WARDING.get(),200,0));
    }
    
    @Override
    public void blessingEffect(PlayerEntity entity)
    {
        effect(entity);
    }
}
