package com.sammy.malum.client.events;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.renderer.*;
import com.sammy.malum.client.renderer.renderpass.ParallelWorldRenderer;
import com.sammy.malum.client.screen.overlay.ScryglassGuiOverlay;
import com.sammy.malum.client.screen.tooltip.ClientMalumPouchTooltip;
import com.sammy.malum.common.data.component.pouch.*;
import com.sammy.malum.core.handlers.client.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.MalumContainers;
import com.sammy.malum.registry.common.MalumParticles;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import team.lodestar.lodestone.systems.rendering.LodestoneRenderSystem;
import team.lodestar.lodestone.systems.rendering.renderpass.RenderPassHandler;

@EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MALUM)
public class ClientSetupHandler {


    @SubscribeEvent
    public static void registerExtras(FMLClientSetupEvent event) {
        MalumHiddenTags.registerHiddenTags();
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void addItemProperties(FMLClientSetupEvent event) {
        MalumItemColors.addItemProperties(event);
    }

    @SubscribeEvent
    public static void setItemColors(RegisterColorHandlersEvent.Item event) {
        MalumItemColors.setItemColors(event);
    }

    @SubscribeEvent
    public static void setBlockColors(RegisterColorHandlersEvent.Block event) {
        MalumBlockColors.setBlockColors(event);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        MalumModels.registerLayerDefinitions(event);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        MalumModels.addLayers(event);
    }

    @SubscribeEvent
    public static void bindEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        EntityRenderers.bindEntityRenderers(event);
    }

    @SubscribeEvent
    public static void bindContainerRenderers(RegisterMenuScreensEvent event) {
        MalumContainers.ClientOnly.bindContainerRenderers(event);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        MalumClientExtensions.registerClientExtensions(event);
    }

    @SubscribeEvent
    public static void registerTooltipComponentManagers(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(SoulwovenPouchContentsComponent.class, ClientMalumPouchTooltip::new);
        event.register(RavenousPouchContentsComponent.class, ClientMalumPouchTooltip::new);
    }

    @SubscribeEvent
    public static void registerOverlays(RegisterGuiLayersEvent event) {
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, MalumMod.malumPath("soul_ward"),
                SoulWardRenderHandler::renderSoulWard);
        event.registerAbove(VanillaGuiLayers.AIR_LEVEL, MalumMod.malumPath("malignant_aegis"),
                MalignantAegisRenderHandler::renderMalignantAegis);
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_LEVEL, MalumMod.malumPath("staff_charges"),
                StaffAbilityRenderHandler::renderStaffCharges);
        event.registerAbove(VanillaGuiLayers.EXPERIENCE_LEVEL, MalumMod.malumPath("hidden_blade_cooldown"),
                HiddenBladeRenderHandler::renderHiddenBladeCooldown);

        event.registerAbove(VanillaGuiLayers.CAMERA_OVERLAYS, MalumMod.malumPath("scryglass"), new ScryglassGuiOverlay());


        event.registerAboveAll(MalumMod.malumPath("touch_of_darkness"),
                TouchOfDarknessRenderHandler::renderDarknessVignette);

    }

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        MalumParticles.registerParticleFactory(event);
        MalumScreenParticles.registerParticleFactory(event);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LodestoneRenderSystem.wrap(() -> RenderPassHandler.registerRenderPass(new ParallelWorldRenderer()));
    }
}
