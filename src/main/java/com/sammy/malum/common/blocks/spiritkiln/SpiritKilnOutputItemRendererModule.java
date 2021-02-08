package com.sammy.malum.common.blocks.spiritkiln;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.RendererModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;

public class SpiritKilnOutputItemRendererModule extends RendererModule
{
    @Override
    public void render(SimpleTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, TileEntityRendererDispatcher renderDispatcher, int combinedLightIn, int combinedOverlayIn)
    {
        if (tileEntityIn instanceof SpiritKilnCoreTileEntity)
        {
            SpiritKilnCoreTileEntity tileEntity = (SpiritKilnCoreTileEntity) tileEntityIn;
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            SimpleInventory inventory = tileEntity.outputInventory;
            if (inventory != null)
            {
                ItemStack item = inventory.getStackInSlot(0);
                if (!item.isEmpty())
                {
                    Direction direction = tileEntity.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
                    matrixStackIn.push();
                    matrixStackIn.translate(0.5, 0.5, 0.5);
                    matrixStackIn.translate(direction.getDirectionVec().getX() * 0.5f, 1, direction.getDirectionVec().getZ() * 0.5f);
                    matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-direction.getHorizontalAngle()));
                    matrixStackIn.scale(0.5f, 0.5f, 0.5f);
                    itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                    matrixStackIn.pop();
                }
            }
        }
        super.render(tileEntityIn, partialTicks, matrixStackIn, bufferIn, renderDispatcher, combinedLightIn, combinedOverlayIn);
    }
}