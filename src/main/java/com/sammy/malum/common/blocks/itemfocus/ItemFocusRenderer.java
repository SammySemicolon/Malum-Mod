package com.sammy.malum.common.blocks.itemfocus;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import static com.sammy.malum.common.blocks.itemfocus.ItemFocusTileEntity.PRESS_DURATION;
import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class ItemFocusRenderer extends TileEntityRenderer<ItemFocusTileEntity>
{
    private final ModelRenderer compressorModel;
    public static final ResourceLocation COMPRESSOR_TEXTURE = MalumHelper.prefix("textures/block/arcane_compressor.png");

    public ItemFocusRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
        compressorModel = new ModelRenderer(64, 64, 0, 0);
        compressorModel.setRotationPoint(0.0F, -8.0F, 0.0F);
        compressorModel.rotateAngleZ = -3.1416F;
        compressorModel.setTextureOffset(0, 0).addBox(-5.0F, -12.0F, -5.0F, 10.0F, 4.0F, 10.0F, 0.0F, false);
    }

    @Override
    public void render(ItemFocusTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        Minecraft mc = Minecraft.getInstance();

        float press = Math.min(PRESS_DURATION,
                tileEntityIn.progress > 0 ? tileEntityIn.progress+ (tileEntityIn.active ? partialTicks : -partialTicks) : 0);
        float pressPercentage = 1f - (press / PRESS_DURATION);
        press = tileEntityIn.pressDistance > 0 && tileEntityIn.pressDistance < 20 ? tileEntityIn.pressDistance+ (tileEntityIn.inventory.nonEmptyItems() == 0 ? -partialTicks : partialTicks) : tileEntityIn.pressDistance;
        float pressDistance = 0.25f + 0.5f * press/20f;
        for (int i = 0; i < 2; i++)
        {
            if (tileEntityIn.apparatusInventory.getStackInSlot(i).isEmpty())
            {
                break;
            }
            matrixStackIn.push();
            matrixStackIn.translate(0.5f, 1.5f, 0.5f);
            if (i == 1)
            {
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180));
            }
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90));
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(-(tileEntityIn.pressSpin + partialTicks)));
            matrixStackIn.translate(-pressDistance * pressPercentage,0,0);
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(90));

            mc.getTextureManager().bindTexture(COMPRESSOR_TEXTURE);
            compressorModel.render(matrixStackIn, bufferIn.getBuffer(RenderType.getEntitySolid(COMPRESSOR_TEXTURE)), combinedLightIn, combinedOverlayIn);
            matrixStackIn.pop();
        }
        if (tileEntityIn.progress > PRESS_DURATION)
        {
            return;
        }
        float minPercentage = Math.min(1, pressPercentage+0.5f);
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
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees((tileEntityIn.getWorld().getGameTime() + partialTicks) * 3));
                matrixStackIn.scale(0.5f*minPercentage, 0.5f*minPercentage, 0.5f*minPercentage);
                itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }
    }
}