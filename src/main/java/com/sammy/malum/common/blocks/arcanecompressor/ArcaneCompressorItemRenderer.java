package com.sammy.malum.common.blocks.arcanecompressor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

import static com.sammy.malum.common.blocks.arcanecompressor.ArcaneCompressorRenderer.COMPRESSOR_TEXTURE;

public class ArcaneCompressorItemRenderer extends ItemStackTileEntityRenderer
{
    private final ModelRenderer compressorModel;
    public ArcaneCompressorItemRenderer()
    {
        compressorModel = new ModelRenderer(64, 64, 0, 0);
        compressorModel.setRotationPoint(0.0F, -8.0F, 0.0F);
        compressorModel.rotateAngleZ = -3.1416F;
        compressorModel.setTextureOffset(0, 0).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
    }
    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay)
    {
        Minecraft mc = Minecraft.getInstance();
        for (int i = 0; i < 2; i++)
        {
            matrixStackIn.push();
            mc.getBlockRendererDispatcher().renderBlock(MalumBlocks.ARCANE_COMPRESSOR.get().getDefaultState(), matrixStackIn, buffer,combinedLight, combinedOverlay);
            matrixStackIn.translate(0.5f, 0.25f, 0.5f);
            if (i == 1)
            {
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180));
            }
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90));
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(-(mc.world.getGameTime() + mc.getRenderPartialTicks())));
            matrixStackIn.translate(-0.25f,0,0);
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90));
            mc.getTextureManager().bindTexture(COMPRESSOR_TEXTURE);
            compressorModel.render(matrixStackIn, buffer.getBuffer(RenderType.getEntitySolid(COMPRESSOR_TEXTURE)), combinedLight, combinedOverlay);
            matrixStackIn.pop();
        }
        super.func_239207_a_(stack, transformType, matrixStackIn, buffer, combinedLight, combinedOverlay);
    }
}