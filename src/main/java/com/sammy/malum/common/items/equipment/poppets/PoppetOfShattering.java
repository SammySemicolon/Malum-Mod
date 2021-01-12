package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.init.MalumDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.ArrayList;

public class PoppetOfShattering extends PoppetItem
{
    public PoppetOfShattering(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(LivingDamageEvent event, World world, PlayerEntity playerEntity, ArrayList<LivingEntity> targets)
    {
        if (!event.getSource().equals(DamageSource.FALL))
        {
            return;
        }
        super.effect(event, world, playerEntity, targets);
    }
    
    @Override
    public void effect(LivingDamageEvent event, World world, PlayerEntity playerEntity, LivingEntity target)
    {
        float damage = Math.min(event.getAmount(), target.getMaxHealth()) * 0.5f;
        event.setAmount(event.getAmount() - damage);
        target.attackEntityFrom(MalumDamageSources.VOODOO, damage);
        super.effect(event, world, playerEntity, target);
    }
}
