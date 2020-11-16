package com.sammy.malum.common.blocks.arcanecraftingtable;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

@OnlyIn(value = Dist.CLIENT)
public class ArcaneCraftingTableRenderer extends TileEntityRenderer<ArcaneCraftingTableTileEntity>
{
    public ArcaneCraftingTableRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    @Override
    public void render(ArcaneCraftingTableTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        int itemCount = 0;
        for (int i = 0; i < blockEntity.inventory.slotCount; i++)
        {
            ItemStack item = blockEntity.inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                itemCount++;
            }
            else
            {
                break;
            }
        }
        for (int i = 0; i < blockEntity.inventory.slotCount; i++)
        {
            ItemStack item = blockEntity.inventory.getStackInSlot(i);
            if (!item.isEmpty())
            {
                matrixStack.push();
                if (itemCount != 1)
                {
                    Vector2f pos = itemCoords(itemCount / 16f, i, itemCount);
                    matrixStack.translate(pos.x, 0, pos.y);
                }
                matrixStack.translate(0.5f,1.25f,0.5f);
                matrixStack.rotate(Vector3f.YP.rotationDegrees(blockEntity.getWorld().getGameTime() * 3));
                matrixStack.scale(0.5f, 0.5f, 0.5f);
                itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, light, NO_OVERLAY, matrixStack, iRenderTypeBuffer);
                matrixStack.pop();
            }
        }
    }
    public Vector2f itemCoords(float distance, int currentPoint, int totalPoints)
    {
        double theta = ((Math.PI * 2) / totalPoints);
        double angle = (theta * currentPoint);
    
        double x = (distance * Math.cos(angle));
        double y = (distance * Math.sin(angle));
        return new Vector2f((float)x,(float)y);
    }
}