package com.sammy.malum.core.events;

import com.sammy.malum.core.handlers.*;
import com.sammy.malum.registry.client.*;
public class ClientSetupEvents {


    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.ARMOR_LEVEL.id(), "soul_ward", (gui, poseStack, partialTick, width, height) ->
                SoulWardHandler.ClientOnly.renderSoulWard(gui, poseStack, width, height));
        event.registerAbove(VanillaGuiOverlay.PLAYER_LIST.id(), "touch_of_darkness", (gui, poseStack, partialTick, width, height) ->
                TouchOfDarknessHandler.ClientOnly.renderDarknessVignette(poseStack));
    }
}