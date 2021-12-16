package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;

import net.minecraft.item.Item.Properties;

public class CurioCurativeRing extends MalumCurioItem implements IEventResponderItem
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
    public void pickupSpirit(LivingEntity attacker, ItemStack stack) {
        attacker.heal(attacker.getMaxHealth()*0.1f);
        MalumHelper.giveStackingEffect(Effects.REGENERATION, attacker, 100, 0);
    }
}