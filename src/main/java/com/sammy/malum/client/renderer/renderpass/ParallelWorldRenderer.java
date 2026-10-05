package com.sammy.malum.client.renderer.renderpass;

import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.*;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.MalumAttachmentTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import org.joml.*;
import team.lodestar.lodestone.helpers.StateShardHelper;
import team.lodestar.lodestone.helpers.TextureHelper;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.builder.VFXBuilders;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;
import team.lodestar.lodestone.systems.rendering.builder.data.CubeVertexData;

import team.lodestar.lodestone.systems.rendering.renderpass.BeforeLevelRenderPass;
import team.lodestar.lodestone.systems.rendering.uniform.UniformData;
import team.lodestar.lodestone.systems.rendering.uniform.UniformDataBuilder;

import java.awt.*;
import java.lang.Math;

public class ParallelWorldRenderer extends BeforeLevelRenderPass {
    public static ParallelWorldRenderer INSTANCE;
    private final RenderTarget target = new TextureTarget(Minecraft.getInstance().getWindow().getWidth()/2, Minecraft.getInstance().getWindow().getHeight()/2, true, Minecraft.ON_OSX);
    private static final RenderStateShard.OutputStateShard outputState = StateShardHelper.createOutputState("parallelWorld", () -> INSTANCE.target.bindWrite(false));

    public ParallelWorldRenderer() {
        INSTANCE = this;
    }

    @Override
    public void render(DeltaTracker deltaTracker, Camera camera, GameRenderer gameRenderer, Matrix4f viewMat, Matrix4f projMat) {
        Minecraft mc = Minecraft.getInstance();
        long gameTime = mc.level.getGameTime();
        var partialTicks = deltaTracker.getGameTimeDeltaTicks();
        float uOffset = ((gameTime + partialTicks) % 8000) / 4000f;
        float vOffset = ((gameTime + 500f + partialTicks) % 8000) / 8000f;

        target.setClearColor(0, 0, 0, 0);

        target.clear(Minecraft.ON_OSX);
        MultiBufferSource.BufferSource bufferSource = mc.renderBuffers().bufferSource();
        Matrix4fStack matrix4fstack = RenderSystem.getModelViewStack();
        matrix4fstack.pushMatrix();
        matrix4fstack.mul(viewMat);
        RenderSystem.applyModelViewMatrix();


        PoseStack poseStack = new PoseStack();
        poseStack.pushPose();
        int cubeScale = 200;
        poseStack.scale(cubeScale, cubeScale, cubeScale);
//        Vector3f cameraPosition = camera.getPosition().toVector3f();
//        poseStack.translate(-cameraPosition.x(), -cameraPosition.y(), -cameraPosition.z());
        poseStack.mulPose(Axis.XP.rotationDegrees(((gameTime + partialTicks) * 0.2f) % 360));
        poseStack.mulPose(Axis.YP.rotationDegrees(((gameTime + partialTicks) * 0.1f) % 360));
        poseStack.mulPose(Axis.ZP.rotationDegrees(((gameTime + partialTicks) * 0.05f) % 360));


        for (int i = 0; i < 4; i++) {
            float speed = 200f + 100f * i;
            float distortion = 4f + 2 * i;
            float scale = (2 - 0.05f * i);
            float red = 0.8f;
            float green = 0.6f;
            float blue = 0.7f;
            float alpha = 0.8f - i * 0.05f;

            var uniforms = UniformData.create()
                    .setUniform("Speed", speed)
                    .setUniform("Distortion", distortion)
                    .setUniform("Width", 512f)
                    .setUniform("Height", 512f)
                    .setUniform("UVCoordinates", -20f, 40f, -20f, 40f)
                    .build();

            var builder = MalumRenderTypes.WEEPING_SKYBOX.apply(MalumRenderTypeTokens.VOID_NOISE)
                    .addUniformData(uniforms);

            var renderType = builder.getRenderType();
            VertexConsumer consumer = bufferSource.getBuffer(renderType);
            var vfxBuilder = VFXBuilders.createWorld()
                    .setVertexConsumer(consumer)
                    .setRenderType(renderType)
                    .setColor(red, green, blue)
                    .setAlpha(alpha);
            var cubeData = CubeVertexData.makeCubePositions(-scale).applyWobble(0, 0.5f, 0.015f);

            vfxBuilder.setUV(-uOffset, vOffset, 1 - uOffset, 1 + vOffset).renderCube(poseStack, cubeData);
            vfxBuilder.setUV(uOffset * 2, -vOffset * 2, (1 + uOffset) * 2, (1 - vOffset) * 2).renderCube(poseStack, cubeData);

            uOffset = -uOffset * 1.25f - 0.2f;
            vOffset = -vOffset * 1.25f + 0.4f;
            bufferSource.endBatch(renderType);
        }
        poseStack.popPose();

        matrix4fstack.popMatrix();
        RenderSystem.applyModelViewMatrix();
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

    }

    @Override
    public boolean shouldRender(DeltaTracker deltaTracker, Camera camera, GameRenderer gameRenderer, Matrix4f matrix4f, Matrix4f matrix4f1) {
        if (!ClientConfig.PARALLEL_WORLD.getConfigValue()) {
            return false;
        }
        var player = Minecraft.getInstance().player;
        if (player == null) {
            return false;
        }
        if (player.hasData(MalumAttachmentTypes.WEEPING_WELL_INFO)) {
            return player.getData(MalumAttachmentTypes.WEEPING_WELL_INFO).isNearWeepingWell;
        }
        return false;
    }

    @Override
    public void resize(int width, int height) {
        target.resize(width, height, Minecraft.ON_OSX);
    }

    public RenderTarget getTarget() {
        return target;
    }

    public static RenderStateShard.OutputStateShard getOutputState() {
        return outputState;
    }
}
