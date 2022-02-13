package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.core.systems.rendering.RenderManager;
import com.sammy.malum.core.systems.spirit.SoulHarvestHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        RenderManager.onClientSetup(event);
    }

    @SuppressWarnings("all")
    @SubscribeEvent
    public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
        for (Map.Entry<EntityType<?>, EntityRenderer<?>> entry : Minecraft.getInstance().getEntityRenderDispatcher().renderers.entrySet()) {
            EntityRenderer render = entry.getValue();
            if (render instanceof LivingEntityRenderer livingRenderer) {
                livingRenderer.addLayer(new SoulHarvestHandler.ClientOnly.HarvestRenderLayer<>(livingRenderer));
            }
        }
    }
}
