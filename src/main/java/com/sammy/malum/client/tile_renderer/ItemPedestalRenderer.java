package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.common.tile.ItemPedestalTileEntity;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.level.Level;

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
        Level level = Minecraft.getInstance().level;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack stack = tileEntityIn.inventory.getStackInSlot(0);
        if (!stack.isEmpty())
        {
            matrixStackIn.pushPose();
            Vector3f offset = new Vector3f(ItemPedestalTileEntity.itemOffset());
            if (stack.getItem() instanceof MalumSpiritItem)
            {
                double y = Math.sin(((Level.getGameTime() + partialTicks) % 360) / 20f) * 0.1f;
                matrixStackIn.translate(0, y, 0);
            }
            matrixStackIn.translate(offset.x(), offset.y(), offset.z());
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees((Level.getGameTime() % 360)* 3 + partialTicks));
            matrixStackIn.scale(0.6f, 0.6f, 0.6f);
            itemRenderer.renderStatic(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
            matrixStackIn.popPose();
        }
    }
}