package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;

public class CurioWickedRing extends MalumCurioItem implements IEventResponderItem
{
    public CurioWickedRing(Properties builder)
    {
        super(builder);
    }

    @Override
    public boolean isOrnate()
    {
        return true;
    }

    @Override
    public void pickupSpirit(LivingEntity attacker, ItemStack stack) {
        MalumHelper.giveStackingEffect(Effects.STRENGTH, attacker, 100, 0);
    }
}