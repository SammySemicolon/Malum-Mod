package com.sammy.malum.core.events;

import com.sammy.malum.common.spiritaffinity.ArcaneAffinity;
import com.sammy.malum.core.handlers.SoulHarvestHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
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
    public static void registerOverlays(FMLClientSetupEvent event) {
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.ARMOR_LEVEL_ELEMENT, "Soul Ward", (gui, poseStack, partialTick, width, height) ->
            ArcaneAffinity.ClientOnly.renderSoulWard(gui, poseStack, width, height));
    }
}
