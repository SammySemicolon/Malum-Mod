package com.sammy.malum.common.blocks.itempedestal;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.items.SpiritItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class  ItemPedestalRenderer extends TileEntityRenderer<ItemPedestalTileEntity>
{
    public ItemPedestalRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
    }

    @Override
    public void render(ItemPedestalTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        World world = Minecraft.getInstance().world;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty())
        {
            matrixStackIn.push();
            Vector3f offset = new Vector3f(ItemPedestalTileEntity.itemOffset());
            if (stack.getItem() instanceof SpiritItem)
            {
                double y = Math.sin((world.getGameTime() % 360) / 20f + partialTicks) * 0.1f;
                matrixStackIn.translate(0, y, 0);
            }
            matrixStackIn.translate(offset.getX(), offset.getY(), offset.getZ());
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(((world.getGameTime() % 360) + partialTicks) * 3));
            matrixStackIn.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }
}