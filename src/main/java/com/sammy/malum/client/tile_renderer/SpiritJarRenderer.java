package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.tile.SpiritJarTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class SpiritJarRenderer extends TileEntityRenderer<SpiritJarTileEntity>
{
    public SpiritJarRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
    }
    
    @Override
    public void render(SpiritJarTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if (tileEntityIn.type != null)
        {
            matrixStackIn.pushPose();
            double y =  0.5f + Math.sin(tileEntityIn.getLevel().getGameTime() / 20f) * 0.2f;
            matrixStackIn.translate(0.5f,y,0.5f);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(((Level.getGameTime() % 360) + partialTicks) * 3));
            matrixStackIn.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderStatic(tileEntityIn.type.splinterItem().getDefaultInstance(), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.popPose();
        }
    }
}