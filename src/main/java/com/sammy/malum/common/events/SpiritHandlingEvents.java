package com.sammy.malum.common.events;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entities.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("all")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpiritHandlingEvents
{
    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event)
    {
        if (event.getSource().getTrueSource() instanceof LivingEntity)
        {
            ItemStack stack = ItemStack.EMPTY;
            LivingEntity attacker = (LivingEntity) event.getSource().getTrueSource();
            if (event.getSource().getImmediateSource() instanceof ScytheBoomerangEntity)
            {
                ScytheBoomerangEntity entity = (ScytheBoomerangEntity) event.getSource().getImmediateSource();
                stack = entity.scythe;
            }
            else
            {
                stack = MalumHelper.heldItem(attacker, s -> s.getItem() instanceof ScytheItem);
            }
            if (stack != null)
            {
                if (stack.getItem() instanceof ScytheItem && event.getSource().isProjectile())
                {
                    return;
                }
                SpiritHelper.summonSpirits(event.getEntityLiving(), attacker, stack);
            }
        }
    }
}
