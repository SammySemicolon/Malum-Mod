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
import net.minecraft.util.math.vector.Vector3f;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class ItemFocusRenderer extends TileEntityRenderer<ItemFocusTileEntity>
{
    private final ModelRenderer bottom;
    private final ModelRenderer top;
    public static final ResourceLocation COMPRESSOR_TEXTURE = MalumHelper.prefix("textures/block/arcane_compressor.png");
    public static final float BIT = 0.0625f;

    public ItemFocusRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
        bottom = new ModelRenderer(64, 64, 0, 0);
        bottom.setRotationPoint(0.0F, 16.0F, 16.0F);
        bottom.setTextureOffset(0, 18).addBox(3.0F, 0.0F, -13.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);

        top = new ModelRenderer(64, 64, 0, 0);
        top.setRotationPoint(0.0F, 16.0F, 16.0F);
        top.setTextureOffset(0, 0).addBox(2.0F, 0.0F, -14.0F, 12.0F, 6.0F, 12.0F, 0.0F, false);
    }

    @Override
    public void render(ItemFocusTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Minecraft mc = Minecraft.getInstance();
        matrixStackIn.push();

        mc.getTextureManager().bindTexture(COMPRESSOR_TEXTURE);
        matrixStackIn.translate(0,BIT*2, 0);
        bottom.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(COMPRESSOR_TEXTURE)), combinedLightIn, combinedOverlayIn);
        matrixStackIn.pop();

        matrixStackIn.push();
        matrixStackIn.translate(0,BIT*16, 0);
        mc.getTextureManager().bindTexture(COMPRESSOR_TEXTURE);
        top.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(COMPRESSOR_TEXTURE)), combinedLightIn, combinedOverlayIn);
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