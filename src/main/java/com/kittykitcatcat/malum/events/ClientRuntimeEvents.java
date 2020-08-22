package com.kittykitcatcat.malum.events;

import com.kittykitcatcat.malum.ClientHandler;
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
            ClientHandler.makeSpiritTooltip(event.getItemStack(), event.getPlayer().world, event.getToolTip(), event.getFlags());
        }
    }
}