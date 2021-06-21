package com.sammy.malum.common.blocks.itemfocus;

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
import net.minecraft.world.World;

import static com.sammy.malum.common.blocks.arcanecompressor.ArcaneCompressorTileEntity.PRESS_DURATION;
import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class ItemFocusRenderer extends TileEntityRenderer<ItemFocusTileEntity>
{
    public ItemFocusRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
    }

    @Override
    public void render(ItemFocusTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (tileEntityIn.progress > PRESS_DURATION)
        {
            return;
        }

        float press = Math.min(PRESS_DURATION, tileEntityIn.progress > 0 ? tileEntityIn.progress+ partialTicks : 0);
        float pressPercentage = 1f - (press / PRESS_DURATION);

        float minPercentage = Math.min(1, pressPercentage+0.5f);
        World world = Minecraft.getInstance().world;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        SimpleInventory inventory = tileEntityIn.inventory;
        for (int i = 0; i < inventory.slotCount; i++)
        {
            ItemStack item = inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                matrixStackIn.push();
                Vector3f offset = new Vector3f(ItemFocusTileEntity.itemOffset(tileEntityIn, i));
                matrixStackIn.translate(offset.getX(), offset.getY(), offset.getZ());
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(((world.getGameTime() % 360) + partialTicks) * 3));
                matrixStackIn.scale(0.5f*minPercentage, 0.5f*minPercentage, 0.5f*minPercentage);
                itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }
    }
}