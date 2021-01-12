package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;

public class PoppetOfSapping extends PoppetItem
{
    public PoppetOfSapping(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(LivingHurtEvent event, World world, PlayerEntity playerEntity, ArrayList<LivingEntity> targets)
    {
        for (EffectInstance effectInstance : playerEntity.getActivePotionEffects())
        {
            if (!effectInstance.getPotion().isBeneficial())
            {
                targets.forEach(t -> MalumHelper.giveStackingEffect(MalumEffects.BLEEDING.get(), t, 80, effectInstance.amplifier));
            }
        }
    }
}
