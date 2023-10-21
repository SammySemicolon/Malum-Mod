package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.malum.client.renderer.entity.*;
import com.sammy.malum.common.block.storage.jar.SpiritJarBlockEntity;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.level.Level;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class SpiritJarRenderer implements BlockEntityRenderer<SpiritJarBlockEntity> {
    public SpiritJarRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpiritJarBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if (blockEntityIn.type != null) {
            final MalumSpiritType type = blockEntityIn.type;
            double time = ((level.getGameTime() % 360) + partialTicks) * (2 * Math.PI) / 120f;
            double y = 0.5f + (float)Math.sin(time) * 0.2f;
            poseStack.pushPose();
            poseStack.translate(0.5f, y, 0.5f);
            FloatingItemEntityRenderer.renderSpiritGlimmer(poseStack, type, partialTicks);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderStatic(type.spiritShard.get().getDefaultInstance(), ItemTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, 0);
            poseStack.popPose();
        }
    }
}