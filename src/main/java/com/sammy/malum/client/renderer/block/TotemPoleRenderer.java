package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.joml.Vector3f;
import team.lodestar.lodestone.handlers.RenderHandler;
import team.lodestar.lodestone.setup.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.systems.rendering.VFXBuilders;

import java.awt.*;


public class TotemPoleRenderer implements BlockEntityRenderer<TotemPoleBlockEntity> {

    public TotemPoleRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(TotemPoleBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = blockEntityIn.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        if (blockEntityIn.type == null) {
            return;
        }
        renderQuad(blockEntityIn.type.getTotemGlowTexture(), blockEntityIn.type.getPrimaryColor(), blockEntityIn.currentColor / 20f, direction, poseStack);
    }

    public void renderQuad(ResourceLocation resourceLocation, Color color, float alpha, Direction direction, PoseStack poseStack) {
        VertexConsumer consumer = RenderHandler.DELAYED_RENDER.getBuffer(LodestoneRenderTypeRegistry.ADDITIVE_TEXTURE.applyAndCache(resourceLocation));

        Vector3f[] positions = new Vector3f[]{new Vector3f(0, 0, 2.01f), new Vector3f(2, 0, 2.01f), new Vector3f(2, 2, 2.01f), new Vector3f(0, 2, 2.01f)};

        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YN.rotationDegrees(direction.toYRot()));
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        VFXBuilders.createWorld()
                .setPosColorTexLightmapDefaultFormat()
                .setColor(color, alpha)
                .renderQuad(consumer, poseStack, positions, 0.5f);
        poseStack.popPose();
    }
}
