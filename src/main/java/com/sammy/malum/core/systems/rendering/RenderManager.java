package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Matrix4f;
import com.sammy.malum.config.ClientConfig;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RenderManager {
    public static HashMap<RenderType, BufferBuilder> BUFFERS = new HashMap<>();
    public static HashMap<RenderType, RenderTypeShaderHandler> HANDLERS = new HashMap<>();
    public static MultiBufferSource.BufferSource DELAYED_RENDER;
    public static Matrix4f PARTICLE_MATRIX = null;

    public static void onClientSetup(FMLClientSetupEvent event) {
        DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(BUFFERS, new BufferBuilder(256));
    }


    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderLast(RenderLevelLastEvent event) {
        event.getPoseStack().pushPose();
        if (ClientConfig.BETTER_LAYERING.get()) {
            RenderSystem.getModelViewStack().pushPose();
            RenderSystem.getModelViewStack().setIdentity();
            if (PARTICLE_MATRIX != null) RenderSystem.getModelViewStack().mulPoseMatrix(PARTICLE_MATRIX);
            RenderSystem.applyModelViewMatrix();
            DELAYED_RENDER.endBatch(RenderTypes.ADDITIVE_PARTICLE);
            DELAYED_RENDER.endBatch(RenderTypes.ADDITIVE_BLOCK_PARTICLE);
            RenderSystem.getModelViewStack().popPose();
            RenderSystem.applyModelViewMatrix();
        }
        for (RenderType type : BUFFERS.keySet()) {
            if (HANDLERS.containsKey(type))
            {
                RenderTypeShaderHandler handler = HANDLERS.get(type);
                handler.updateShaderData();
            }
            DELAYED_RENDER.endBatch(type);
        }
        DELAYED_RENDER.endBatch();
        event.getPoseStack().popPose();
    }
}