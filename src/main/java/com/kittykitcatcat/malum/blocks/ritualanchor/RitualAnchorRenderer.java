package com.kittykitcatcat.malum.blocks.ritualanchor;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

@OnlyIn(value = Dist.CLIENT)
public class RitualAnchorRenderer extends TileEntityRenderer<RitualAnchorTileEntity>
{

    public RitualAnchorRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    public static Vec3d getItemPositions(long gameTime, float itemCount, float currentItem)
    {
        double offset = (Math.PI / itemCount) * currentItem;
        double sin = (Math.sin((gameTime / 10f) + offset) / 10f);
        double rot = currentItem / itemCount * (Math.PI * 2) + (gameTime % 650f / 650f) * (Math.PI * 2);

        double dist = 0.4 + sin;

        double posX = 0.5 - (Math.cos(rot) * dist);
        double posY = 1.2 + Math.sin(gameTime / 10f) / 64f;
        double posZ = 0.5 - (Math.sin(rot) * dist);
        return new Vec3d(posX, posY, posZ);
    }
    @Override
    public void render(RitualAnchorTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
        if (this.renderDispatcher.renderInfo != null && blockEntity.getDistanceSq(this.renderDispatcher.renderInfo.getProjectedView().x, this.renderDispatcher.renderInfo.getProjectedView().y, this.renderDispatcher.renderInfo.getProjectedView().z) < 128d)
        {
            if (this.renderDispatcher.renderInfo != null && blockEntity.getDistanceSq(this.renderDispatcher.renderInfo.getProjectedView().x, this.renderDispatcher.renderInfo.getProjectedView().y, this.renderDispatcher.renderInfo.getProjectedView().z) < 128d)
            {
                ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

                for (int a = 1; a < blockEntity.inventory.getSlots() + 1; a += 1)
                {
                    ItemStack item = blockEntity.inventory.getStackInSlot(a - 1);
                    if (item.isEmpty())
                    {
                        break;
                    }
                    Vec3d pos = getItemPositions(blockEntity.getWorld().getGameTime(), blockEntity.inventory.getSlots(), a);
                    matrixStack.push();
                    matrixStack.translate(pos.x, pos.y, pos.z);
                    matrixStack.rotate(Vector3f.YP.rotationDegrees(a * 10f + blockEntity.getWorld().getGameTime() * 3));
                    matrixStack.scale(0.25f, 0.25f, 0.25f);
                    itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, light, NO_OVERLAY, matrixStack, iRenderTypeBuffer);
                    matrixStack.pop();
                }
            }
        }
    }
}