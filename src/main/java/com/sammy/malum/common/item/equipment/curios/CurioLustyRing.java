package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.MalumHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effects;

public class CurioLustyRing extends MalumCurioItem
{
    public CurioLustyRing(Properties builder)
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
        MalumHelper.giveStackingEffect(Effects.STRENGTH, livingEntity, 100, 0);
    }
}