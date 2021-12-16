package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.tile.SpiritAltarTileEntity;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
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


public class SpiritAltarRenderer implements BlockEntityRenderer<SpiritAltarTileEntity> {
    public SpiritAltarRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SpiritAltarTileEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        SimpleBlockEntityInventory inventory = tileEntityIn.spiritInventory;
        for (int i = 0; i < inventory.slotCount; i++) {
            ItemStack item = inventory.getStackInSlot(i);
            if (!item.isEmpty()) {
                poseStack.pushPose();
                Vector3f offset = new Vector3f(SpiritAltarTileEntity.itemOffset(tileEntityIn, i));
                poseStack.translate(offset.x(), offset.y(), offset.z());
                poseStack.mulPose(Vector3f.YP.rotationDegrees((level.getGameTime() % 360) * 3 + partialTicks));
                poseStack.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderStatic(item, ItemTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, item.getCount());
                poseStack.popPose();
            }
        }
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty()) {
            poseStack.pushPose();
            Vec3 offset = tileEntityIn.itemOffset();
            poseStack.translate(offset.x, offset.y, offset.z);
            poseStack.mulPose(Vector3f.YP.rotationDegrees((level.getGameTime() % 360) * 3 + partialTicks));
            poseStack.scale(0.4f, 0.4f, 0.4f);
            itemRenderer.renderStatic(stack, ItemTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, stack.getCount());
            poseStack.popPose();
        }
    }
}