package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PoppetOfBleeding extends PoppetItem
{
    public PoppetOfBleeding(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target)
    {
        MalumHelper.giveStackingEffect(MalumEffects.BLEEDING.get(), target, 80,1);
        super.effect(event, world, playerEntity, target);
    }
    
    @Override
    public boolean onlyDirect()
    {
        return true;
    }
}
