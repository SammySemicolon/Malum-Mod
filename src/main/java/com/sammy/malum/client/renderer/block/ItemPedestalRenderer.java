package com.sammy.malum.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.*;
import com.sammy.malum.common.block.storage.ItemPedestalBlockEntity;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.joml.*;
import org.joml.Math;

import java.lang.*;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class  ItemPedestalRenderer implements BlockEntityRenderer<ItemPedestalBlockEntity> {
    public ItemPedestalRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ItemPedestalBlockEntity blockEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = blockEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = blockEntityIn.itemOffset();
            if (stack.getItem() instanceof SpiritShardItem) {
                double y = Math.sin(((level.getGameTime() % 360) + partialTicks) / 20f) * 0.1f;
                poseStack.translate(0, y, 0);
            }
            poseStack.translate(offset.x(), offset.y(), offset.z());
            poseStack.mulPose(Axis.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, level, 0);
            poseStack.popPose();
        }
    }
}