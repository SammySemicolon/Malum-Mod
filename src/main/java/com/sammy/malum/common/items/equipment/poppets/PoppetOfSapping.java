package com.sammy.malum.common.items.equipment.poppets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class PoppetOfSapping extends PoppetItem
{
    public PoppetOfSapping(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(LivingDamageEvent event, World world, PlayerEntity playerEntity, LivingEntity target)
    {
        for (EffectInstance effectInstance : playerEntity.getActivePotionEffects())
        {
            if (!effectInstance.getPotion().isBeneficial())
            {
                effectInstance.duration -= 30;
            }
        }
        super.effect(event, world, playerEntity, target);
    }
}
