package com.sammy.malum.core.events;

import com.sammy.malum.common.item.curiosities.curios.sets.weeping.*;
import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.client.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {


    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(), "soul_ward", (gui, poseStack, partialTick, width, height) ->
                SoulWardHandler.ClientOnly.renderSoulWard(gui, poseStack, width, height));
        event.registerAboveAll("hidden_blade_cooldown", (gui, poseStack, partialTick, width, height) ->
                CurioHiddenBladeNecklace.ClientOnly.renderHiddenBladeCooldown(gui, poseStack, width, height));
        event.registerAbove(VanillaGuiOverlay.PLAYER_LIST.id(), "touch_of_darkness", (gui, poseStack, partialTick, width, height) ->
                TouchOfDarknessHandler.ClientOnly.renderDarknessVignette(poseStack));
    }

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        ParticleRegistry.registerParticleFactory(event);
        ScreenParticleRegistry.registerParticleFactory(event);
    }
}