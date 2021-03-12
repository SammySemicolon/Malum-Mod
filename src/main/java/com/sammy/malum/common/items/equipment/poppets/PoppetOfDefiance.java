package com.sammy.malum.common.items.equipment.poppets;

import com.sammy.malum.core.init.MalumDamageSources;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.ArrayList;

public class PoppetOfDefiance extends PoppetItem
{
    public PoppetOfDefiance(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void effect(ItemStack poppet, LivingHurtEvent event, World world, PlayerEntity playerEntity, LivingEntity target, SimpleInventory inventory)
    {
        if (!event.getSource().isMagicDamage())
        {
            return;
        }
        SpiritHelper.causeVoodooDamage(playerEntity, target, event.getAmount() / 2f);
        event.setAmount(event.getAmount() / 2f);
        super.effect(poppet, event, world, playerEntity, target, inventory);
    }
}
