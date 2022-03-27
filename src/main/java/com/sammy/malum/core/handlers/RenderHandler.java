package com.sammy.malum.core.handlers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Matrix4f;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.core.helper.RenderHelper;
import com.sammy.malum.core.systems.rendering.ExtendedShaderInstance;
import com.sammy.malum.core.systems.rendering.ShaderUniformHandler;
import com.sammy.malum.core.systems.rendering.RenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.HashMap;

public class RenderHandler {
    public static HashMap<RenderType, BufferBuilder> BUFFERS = new HashMap<>();
    public static HashMap<RenderType, ShaderUniformHandler> HANDLERS = new HashMap<>();
    public static MultiBufferSource.BufferSource DELAYED_RENDER;
    public static Matrix4f PARTICLE_MATRIX = null;

    public static void onClientSetup(FMLClientSetupEvent event) {
        DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(BUFFERS, new BufferBuilder(256));
    }

    public static void renderLast(RenderLevelLastEvent event) {
        event.getPoseStack().pushPose();
        if (ClientConfig.DELAYED_PARTICLE_RENDERING.get()) {
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
            ShaderInstance instance = RenderHelper.getShader(type);
            if (HANDLERS.containsKey(type)) {
                ShaderUniformHandler handler = HANDLERS.get(type);
                handler.updateShaderData(instance);
            }
            DELAYED_RENDER.endBatch(type);

            if (instance instanceof ExtendedShaderInstance extendedShaderInstance) {
                extendedShaderInstance.setUniformDefaults();
            }
        }
        DELAYED_RENDER.endBatch();
        event.getPoseStack().popPose();
    }
}