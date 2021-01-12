package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.init.MalumDamageSources;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class PoppetOfVengeance extends PoppetItem
{
    public PoppetOfVengeance(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(LivingDamageEvent event, World world, PlayerEntity playerEntity, LivingEntity target)
    {
        float amount = event.getAmount() * 0.05f;
        event.setAmount(event.getAmount() - amount);
        target.attackEntityFrom(MalumDamageSources.VOODOO, amount * 3);
        super.effect(event, world, playerEntity, target);
    }
}
