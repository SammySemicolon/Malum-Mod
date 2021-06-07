package com.sammy.malum.common.blocks.wellofsuffering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarTileEntity;
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


public class WellOfSufferingRenderer extends TileEntityRenderer<WellOfSufferingTileEntity>
{
    public WellOfSufferingRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
    }

    @Override
    public void render(WellOfSufferingTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        for (int i = 0; i < tileEntityIn.inventory.slotCount; i++)
        {
            ItemStack item = tileEntityIn.inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                matrixStackIn.push();
                Vector3f offset = new Vector3f(WellOfSufferingTileEntity.itemOffset(tileEntityIn, i));
                matrixStackIn.translate(offset.getX(), offset.getY(), offset.getZ());
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees((tileEntityIn.getWorld().getGameTime() + partialTicks) * 3));
                matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }
    }
}