package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.init.MalumDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;

public class PoppetOfDefiance extends PoppetItem
{
    public PoppetOfDefiance(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean onlyDirect()
    {
        return super.onlyDirect();
    }
    
    @Override
    public void effect(LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target)
    {
        if (!event.getSource().isMagicDamage())
        {
            return;
        }
        float damage = Math.min(event.getAmount(), target.getMaxHealth());
        target.attackEntityFrom(MalumDamageSources.VOODOO, damage);
        super.effect(event, world, playerEntity, target);
    }
}
