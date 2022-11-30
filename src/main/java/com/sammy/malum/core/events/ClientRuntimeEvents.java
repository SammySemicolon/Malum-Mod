package com.sammy.malum.core.events;

import com.sammy.malum.common.spiritaffinity.EarthenAffinity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientRuntimeEvents {

    @SubscribeEvent
    public static void renderOverlay(RenderGuiOverlayEvent.Post event) {
        EarthenAffinity.ClientOnly.renderHeartOfStone(event);
    }
}
