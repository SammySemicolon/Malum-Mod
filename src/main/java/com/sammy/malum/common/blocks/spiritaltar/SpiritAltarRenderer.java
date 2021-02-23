package com.sammy.malum.common.blocks.spiritaltar;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class SpiritAltarRenderer extends TileEntityRenderer<SpiritAltarTileEntity>
{
    public SpiritAltarRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
    }
    
    @Override
    public void render(SpiritAltarTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        SimpleInventory inventory = tileEntityIn.spiritInventory;
        for (int i = 0; i < inventory.slotCount; i++)
        {
            ItemStack item = inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                matrixStackIn.push();
                Vector3f offset = new Vector3f(SpiritAltarTileEntity.itemOffset(tileEntityIn, i));
                matrixStackIn.translate(offset.getX(), offset.getY(), offset.getZ());
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees((tileEntityIn.getWorld().getGameTime() + partialTicks) * 3));
                matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty())
        {
            matrixStackIn.push();
            matrixStackIn.translate(0.5f,1.15f, 0.5f);
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees((tileEntityIn.getWorld().getGameTime() + partialTicks) * 3));
            matrixStackIn.scale(0.4f, 0.4f, 0.4f);
            itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }
}