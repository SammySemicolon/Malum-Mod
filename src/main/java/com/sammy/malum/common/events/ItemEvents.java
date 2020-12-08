package com.sammy.malum.common.events;

import com.sammy.malum.core.systems.events.EventSubscriberItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemEvents
{
    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        Item item = event.getItemStack().getItem();
        if (item instanceof EventSubscriberItem)
        {
            BlockState state = event.getWorld().getBlockState(event.getPos());
            EventSubscriberItem eventSubscriberItem = ((EventSubscriberItem) item);
            if (eventSubscriberItem.onBlockRightClick(event.getItemStack(), event.getPlayer(), state, event.getPos(), false))
            {
                eventSubscriberItem.onBlockRightClick(event.getItemStack(), event.getPlayer(), state, event.getPos(), true);
            }
        }
    }
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
            Item item = stack.getItem();
            if (item instanceof EventSubscriberItem)
            {
                EventSubscriberItem eventSubscriberItem = ((EventSubscriberItem) item);
                if (eventSubscriberItem.onEntityKill(stack,attacker,event.getEntityLiving(),false))
                {
                    eventSubscriberItem.onEntityKill(stack,attacker,event.getEntityLiving(), true);
                }
            }
        }
    }
}
