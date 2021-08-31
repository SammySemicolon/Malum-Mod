package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.MalumHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;

public class CurioCurativeRing extends MalumCurioItem
{
    public CurioCurativeRing(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean isGilded()
    {
        return true;
    }

    @Override
    public void consumeSpiritEffect(LivingEntity livingEntity) {
        livingEntity.heal(livingEntity.getMaxHealth()*0.2f);
        MalumHelper.giveStackingEffect(Effects.REGENERATION, livingEntity, 100, 0);

    }
}