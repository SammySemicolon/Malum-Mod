package com.sammy.malum.common.events;

import com.sammy.malum.core.systems.events.EventSubscriberItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemEvents
{
    @SubscribeEvent
    public static void onEntityKill(LivingDeathEvent event)
    {
        if (event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity attacker = (PlayerEntity) event.getSource().getTrueSource();
            ItemStack stack = ItemStack.EMPTY;
            if (attacker.swingingHand != null)
            {
                stack = attacker.getHeldItem(attacker.swingingHand);
            }
            if (attacker.isHandActive() && stack.isEmpty())
            {
                stack = attacker.getActiveItemStack();
            }
            if (stack.getItem() instanceof EventSubscriberItem)
            {
                if (((EventSubscriberItem) stack.getItem()).onEntityKill(stack,attacker,event.getEntityLiving(),false))
                {
                    ((EventSubscriberItem) stack.getItem()).onEntityKill(stack,attacker,event.getEntityLiving(), true);
                }
            }
        }
    }
}
