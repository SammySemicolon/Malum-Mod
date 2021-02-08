package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;

public class PoppetOfShattering extends PoppetItem
{
    public PoppetOfShattering(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory)
    {
        float damage = Math.min(event.getAmount(), target.getMaxHealth());
        if (damage == 0)
        {
            return;
        }
        event.setAmount(event.getAmount() - damage);
        target.attackEntityFrom(MalumDamageSources.VOODOO, damage);
        super.effect(poppet, event, world, playerEntity, target, inventory);
    }
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, ArrayList<LivingEntity> targets, SimpleInventory inventory)
    {
        if (!event.getSource().equals(DamageSource.FALL))
        {
            return;
        }
        super.effect(poppet, event, world, playerEntity, targets, inventory);
    }
}
