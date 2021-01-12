package com.sammy.malum.common.items.equipment.poppets;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

public class PoppetOfMisfortune extends PoppetItem
{
    public PoppetOfMisfortune(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target)
    {
        target.applyKnockback(0.6f, MathHelper.nextFloat(world.rand, -1,1), MathHelper.nextFloat(world.rand, -1,1));
        super.effect(event, world, playerEntity, target);
    }
}
