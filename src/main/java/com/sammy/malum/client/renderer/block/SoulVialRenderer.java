package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.sammy.malum.client.renderer.entity.SoulEntityRenderer;
import com.sammy.malum.common.blockentity.storage.SoulVialBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;


public class SoulVialRenderer implements BlockEntityRenderer<SoulVialBlockEntity> {
    public SoulVialRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SoulVialBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        if (blockEntityIn.data != null) {
            poseStack.pushPose();
            double y = 0.5f + Math.sin(blockEntityIn.getLevel().getGameTime() / 20f) * 0.08f;
            poseStack.translate(0.5f, y, 0.5f);
            poseStack.scale(0.75f, 0.75f, 0.75f);
            SoulEntityRenderer.renderSoul(poseStack, blockEntityIn.data.primaryType.color.darker());
            poseStack.popPose();
        }
    }
}