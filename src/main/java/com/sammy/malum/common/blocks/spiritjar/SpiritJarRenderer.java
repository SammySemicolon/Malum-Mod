package com.sammy.malum.common.blocks.spiritjar;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

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
        World world = Minecraft.getInstance().world;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        if (tileEntityIn.type != null)
        {
            matrixStackIn.push();
            double y =  0.5f + Math.sin(tileEntityIn.getWorld().getGameTime() / 20f) * 0.2f;
            matrixStackIn.translate(0.5f,y,0.5f);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(((world.getGameTime() % 360) + partialTicks) * 3));
            matrixStackIn.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderItem(tileEntityIn.type.splinterItem().getDefaultInstance(), ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }
}