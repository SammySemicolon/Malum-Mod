package com.sammy.malum.core.events;

import com.sammy.malum.core.handlers.SoulHarvestHandler;
import com.sammy.malum.core.handlers.SoulWardHandler;
import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import com.sammy.malum.registry.client.ParticleRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {
    @SubscribeEvent
    public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
        for (Map.Entry<EntityType<?>, EntityRenderer<?>> entry : Minecraft.getInstance().getEntityRenderDispatcher().renderers.entrySet()) {
            EntityRenderer<?> render = entry.getValue();
            SoulHarvestHandler.ClientOnly.addRenderLayer(render);
        }
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.ARMOR_LEVEL.id(), "soul_ward", (gui, poseStack, partialTick, width, height) ->
                SoulWardHandler.ClientOnly.renderSoulWard(gui, poseStack, width, height));
        event.registerAbove(VanillaGuiOverlay.SLEEP_FADE.id(), "touch_of_darkness", (gui, poseStack, partialTick, width, height) ->
                TouchOfDarknessHandler.ClientOnly.renderDarknessVignette(poseStack));
    }

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        ParticleRegistry.registerParticleFactory(event);
    }
}