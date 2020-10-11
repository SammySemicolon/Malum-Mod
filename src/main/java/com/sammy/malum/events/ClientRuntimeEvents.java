package com.sammy.malum.events;

import com.sammy.malum.ClientHandler;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ClientRuntimeEvents
{
    @SubscribeEvent
    public static void addSpiritTooltips(ItemTooltipEvent event)
    {
        if (event.getPlayer() != null)
        {
            ItemStack stack = event.getItemStack();
            ClientHandler.makeSpiritTooltip(event.getPlayer(), stack, event.getPlayer().world, event.getToolTip(), event.getFlags());
        }
    }
}