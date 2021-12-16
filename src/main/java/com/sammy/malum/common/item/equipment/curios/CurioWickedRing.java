package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;

import net.minecraft.world.item.Item.Properties;

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
        MalumHelper.giveStackingEffect(MobEffects.DAMAGE_BOOST, attacker, 100, 0);
    }
}