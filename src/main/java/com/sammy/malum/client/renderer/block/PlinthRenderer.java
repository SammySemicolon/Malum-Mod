package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.blockentity.item_storage.PlinthCoreBlockEntity;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class PlinthRenderer implements BlockEntityRenderer<PlinthCoreBlockEntity> {
    public PlinthRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(PlinthCoreBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = blockEntityIn.itemOffset();
            poseStack.translate(offset.x, offset.y, offset.z);
            if (stack.getItem() instanceof MalumSpiritItem)
            {
                double y = Math.sin(((level.getGameTime() + partialTicks) % 360) / 20f) * 0.05f;
                poseStack.translate(0, y, 0);
            }
            poseStack.mulPose(Vector3f.YP.rotationDegrees((level.getGameTime() % 360) * 3 + partialTicks));
            poseStack.scale(0.45f, 0.45f, 0.45f);
            itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, 0);
            poseStack.popPose();
        }
    }
}