package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.systems.item.IMalumEventResponderItem;
import com.sammy.ortus.helpers.EntityHelper;
import com.sammy.ortus.helpers.ItemHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;

public class CurioWickedRing extends MalumCurioItem implements IMalumEventResponderItem
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
    public void pickupSpirit(LivingEntity attacker, ItemStack stack, boolean isNatural) {
        EntityHelper.giveStackingEffect(MobEffects.DAMAGE_BOOST, attacker, 100, 0);
    }
}