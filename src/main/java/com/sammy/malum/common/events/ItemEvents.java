package com.sammy.malum.common.events;

import com.sammy.malum.core.systems.events.EventSubscriberItem;
import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemEvents
{
    @SubscribeEvent
    public static void onBlockRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        BlockState state = event.getWorld().getBlockState(event.getPos());
        if (state.getBlock() instanceof IAlwaysActivatedBlock)
        {
            state.getBlock().onBlockActivated(state, event.getWorld(), event.getPos(), event.getPlayer(), event.getHand(), null);
            event.setUseBlock(Event.Result.DENY);
            event.setUseItem(Event.Result.DENY);
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
                if (eventSubscriberItem.hasEntityKill())
                {
                    eventSubscriberItem.onEntityKill(stack,attacker,event.getEntityLiving());
                }
            }
        }
    }
}
