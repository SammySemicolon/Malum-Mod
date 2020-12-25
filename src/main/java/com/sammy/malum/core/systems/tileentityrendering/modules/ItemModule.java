package com.sammy.malum.core.systems.tileentityrendering.modules;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class ItemModule extends RendererModule
{
    @Override
    public void render(SimpleTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, TileEntityRendererDispatcher renderDispatcher, int combinedLightIn, int combinedOverlayIn)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        SimpleInventory inventory = simpleInventory(tileEntityIn);
        if (inventory != null)
        {
            for (int i = 0; i < inventory.slotCount; i++)
            {
                ItemStack item = inventory.getStackInSlot(i);
                if (!item.isEmpty())
                {
                    matrixStackIn.push();
                    Vector3f offset = itemOffset(tileEntityIn, partialTicks, 0.2f, i,inventory.nonEmptyItems());
                    matrixStackIn.translate(offset.getX(), offset.getY(), offset.getZ());
                    matrixStackIn.rotate(Vector3f.YP.rotationDegrees((tileEntityIn.getWorld().getGameTime() + partialTicks) * 3));
                    matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                    itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                    matrixStackIn.pop();
                }
            }
        }
        super.render(tileEntityIn, partialTicks, matrixStackIn, bufferIn, renderDispatcher, combinedLightIn, combinedOverlayIn);
    }
    public SimpleInventory simpleInventory(SimpleTileEntity tileEntity)
    {
        if (tileEntity instanceof SimpleInventoryTileEntity)
        {
            return ((SimpleInventoryTileEntity) tileEntity).inventory;
        }
        return null;
    }
    public Vector3f itemOffset(SimpleTileEntity tileEntity, float partialTicks, float distance, int currentPoint, int totalPoints)
    {
//        double theta = ((Math.PI * 2) / totalPoints);
//        double finalAngle = (world.getGameTime() + partialTicks) / 160 + (theta * currentPoint);
//
//        double x = (distance * Math.cos(finalAngle));
//        double y = (distance * Math.sin(finalAngle));
//        return new Vector2f((float)x,(float)y);
        return new Vector3f(0,0,0);
    }
}
