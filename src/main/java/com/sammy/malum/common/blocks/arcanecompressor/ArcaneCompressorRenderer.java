package com.sammy.malum.common.blocks.arcanecompressor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;


public class ArcaneCompressorRenderer extends TileEntityRenderer<ArcaneCompressorTileEntity>
{
    private final ModelRenderer compressorModel;
    public static final ResourceLocation COMPRESSOR_TEXTURE = MalumHelper.prefix("textures/block/arcane_compressor_plate.png");

    public ArcaneCompressorRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
        compressorModel = new ModelRenderer(64, 64, 0, 0);
        compressorModel.setRotationPoint(0.0F, -8.0F, 0.0F);
        compressorModel.rotateAngleZ = -3.1416F;
        compressorModel.setTextureOffset(0, 0).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
    }

    @Override
    public boolean isGlobalRenderer(ArcaneCompressorTileEntity te)
    {
        return true;
    }

    @Override
    public void render(ArcaneCompressorTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Minecraft mc = Minecraft.getInstance();

//        float press = Math.min(PRESS_DURATION,
//                tileEntityIn.progress > 0 ? tileEntityIn.progress+ (tileEntityIn.active ? partialTicks : -partialTicks) : 0);
//        float pressPercentage = 1f - (press / PRESS_DURATION);
//        press = tileEntityIn.pressDistance > 0 && tileEntityIn.pressDistance < 20 ? tileEntityIn.pressDistance+ (tileEntityIn.inventory.nonEmptyItems() == 0 ? -partialTicks : partialTicks) : tileEntityIn.pressDistance;
//        float pressDistance = 0.25f + 0.5f * press/20f;

        float animationProgress = tileEntityIn.animationProgress;
        if (animationProgress > 0 && animationProgress < 20)
        {
            animationProgress += tileEntityIn.hasFocus ? partialTicks : -partialTicks;
        }
        float animationPercentage = animationProgress / 20f;
        for (int i = 0; i < 2; i++)
        {
            matrixStackIn.push();
            matrixStackIn.translate(0.5f, 0.25f - 0.75f * animationPercentage, 0.5f);
            if (i == 1)
            {
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180));
            }
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90));
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(-(tileEntityIn.spin + partialTicks)));
            matrixStackIn.translate(-0.25f - 0.35f * animationPercentage, 0, 0);
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90));

            mc.getTextureManager().bindTexture(COMPRESSOR_TEXTURE);
            compressorModel.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(COMPRESSOR_TEXTURE)), combinedLightIn, combinedOverlayIn);
            matrixStackIn.pop();
        }
    }
}