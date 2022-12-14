package com.sammy.malum.core.events;

import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRuntimeEvents {

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event) {
    }
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void lateRenderTick(TickEvent.RenderTickEvent event) {
    }
}
