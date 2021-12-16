package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.sammy.malum.common.tile.SpiritJarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.level.Level;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class SpiritJarRenderer implements BlockEntityRenderer<SpiritJarTileEntity>
{
    public SpiritJarRenderer(BlockEntityRendererProvider.Context context)
    {
    }
    
    @Override
    public void render(SpiritJarTileEntity tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if (tileEntityIn.type != null)
        {
            poseStack.pushPose();
            double y =  0.5f + Math.sin(tileEntityIn.getLevel().getGameTime() / 20f) * 0.2f;
            poseStack.translate(0.5f,y,0.5f);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(((level.getGameTime() % 360) + partialTicks) * 3));
            poseStack.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderStatic(tileEntityIn.type.splinterItem().getDefaultInstance(), ItemTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, poseStack, bufferIn, tileEntityIn.count);
            poseStack.popPose();
        }
    }
}