package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderManager {
    public static HashMap<RenderType, BufferBuilder> BUFFERS = new HashMap<>();
    public static MultiBufferSource.BufferSource DELAYED_RENDER;

    public static void onClientSetup(FMLClientSetupEvent event) {
        DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(BUFFERS, new BufferBuilder(256));
    }

    @SubscribeEvent
    public static void onRenderLast(RenderLevelLastEvent event) {
        event.getPoseStack().pushPose();
        for (RenderType type : BUFFERS.keySet()) {
            DELAYED_RENDER.endBatch(type);
        }
        DELAYED_RENDER.endBatch();
        event.getPoseStack().popPose();
    }
}