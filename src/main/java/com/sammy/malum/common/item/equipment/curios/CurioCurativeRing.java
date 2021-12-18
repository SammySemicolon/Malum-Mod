package com.sammy.malum.common.item.equipment.curios;

import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.systems.item.IEventResponderItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffects;

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
        ItemHelper.giveStackingEffect(MobEffects.REGENERATION, attacker, 100, 0);
    }
}