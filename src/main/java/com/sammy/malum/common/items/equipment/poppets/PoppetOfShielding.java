package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class PoppetOfShielding extends PoppetItem
{
    public PoppetOfShielding(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory)
    {
        event.setAmount(event.getAmount() * 0.9f);
        super.effect(poppet, event, world, playerEntity, target, inventory);
    }
}
