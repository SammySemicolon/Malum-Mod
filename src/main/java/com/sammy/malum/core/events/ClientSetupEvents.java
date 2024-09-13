package com.sammy.malum.core.events;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.client.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {


    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.ARMOR_LEVEL.id(), "soul_ward", (gui, poseStack, partialTick, width, height) ->
                SoulWardHandler.ClientOnly.renderSoulWard(gui, poseStack, width, height));
        event.registerAbove(VanillaGuiOverlay.PLAYER_LIST.id(), "touch_of_darkness", (gui, poseStack, partialTick, width, height) ->
                TouchOfDarknessHandler.ClientOnly.renderDarknessVignette(poseStack));
    }

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        ParticleRegistry.registerParticleFactory(event);
        ScreenParticleRegistry.registerParticleFactory(event);
    }
}