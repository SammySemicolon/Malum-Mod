package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

import java.util.ArrayList;

public class PoppetOfMisfortune extends PoppetItem
{
    public PoppetOfMisfortune(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory, int slot)
    {
        target.applyKnockback(0.6f, MathHelper.nextFloat(world.rand, -1,1), MathHelper.nextFloat(world.rand, -1,1));
        super.effect(poppet, event, world, playerEntity, target, inventory, slot);
    }
}
