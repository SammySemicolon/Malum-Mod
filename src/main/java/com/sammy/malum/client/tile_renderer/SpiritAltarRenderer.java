package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.tile.SpiritAltarTileEntity;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.Level.Level;

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
        Level Level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        SimpleBlockEntityInventory inventory = tileEntityIn.spiritInventory;
        for (int i = 0; i < inventory.slotCount; i++)
        {
            ItemStack item = inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                matrixStackIn.pushPose();
                Vector3f offset = new Vector3f(SpiritAltarTileEntity.itemOffset(tileEntityIn, i));
                matrixStackIn.translate(offset.x(), offset.y(), offset.z());
                matrixStackIn.mulPose(Vector3f.YP.rotationDegrees((Level.getGameTime() % 360)* 3 + partialTicks));
                matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderStatic(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                matrixStackIn.popPose();
            }
        }
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty())
        {
            matrixStackIn.pushPose();
            Vector3d offset = tileEntityIn.itemOffset();
            matrixStackIn.translate(offset.x, offset.y,offset.z);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees((Level.getGameTime() % 360)* 3 + partialTicks));
            matrixStackIn.scale(0.4f, 0.4f, 0.4f);
            itemRenderer.renderStatic(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.popPose();
        }
    }
}