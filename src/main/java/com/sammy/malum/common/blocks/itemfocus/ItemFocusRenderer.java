package com.sammy.malum.common.blocks.itemfocus;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class ItemFocusRenderer extends TileEntityRenderer<ItemFocusTileEntity>
{
    private final ModelRenderer compressorModel;
    public static final ResourceLocation COMPRESSOR_TEXTURE = MalumHelper.prefix("textures/block/arcane_compressor.png");
    public static final float BIT = 0.0625f;

    public ItemFocusRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
        compressorModel = new ModelRenderer(64, 64, 0, 0);
        compressorModel.setRotationPoint(0.0F, -8.0F, 0.0F);
        compressorModel.rotateAngleZ = -3.1416F;
        compressorModel.setTextureOffset(0, 0).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
    }

    @Override
    public void render(ItemFocusTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Minecraft mc = Minecraft.getInstance();

        matrixStackIn.push();
        mc.getTextureManager().bindTexture(COMPRESSOR_TEXTURE);
        matrixStackIn.translate(0.5f,1, 0.5f);
        compressorModel.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(COMPRESSOR_TEXTURE)), combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty())
        {
            matrixStackIn.push();
            Vector3f offset = new Vector3f(ItemFocusTileEntity.itemOffset());
            if (stack.getItem() instanceof SpiritItem)
            {
                double y = Math.sin(tileEntityIn.getWorld().getGameTime() / 20f) * 0.1f;
                matrixStackIn.translate(0, y, 0);
            }
            matrixStackIn.translate(offset.getX(), offset.getY() + BIT*5, offset.getZ());
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((tileEntityIn.getWorld().getGameTime() + partialTicks) * 3));
            matrixStackIn.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }
}