package com.sammy.malum.common.blocks.wellofsuffering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarTileEntity;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleTileEntity;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.biome.BiomeColors;

import java.awt.*;

import static net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY;


public class WellOfSufferingRenderer extends TileEntityRenderer<WellOfSufferingTileEntity>
{
    public static final RenderMaterial WATER = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, new ResourceLocation("block/water_still"));

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
                matrixStackIn.scale(0.4f, 0.4f, 0.4f);
                itemRenderer.renderItem(item, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, NO_OVERLAY, matrixStackIn, bufferIn);
                matrixStackIn.pop();
            }
        }
        int color = BiomeColors.getWaterColor(tileEntityIn.getWorld(), tileEntityIn.getPos());
        int red = ColorHelper.PackedColor.getRed(color);
        int green = ColorHelper.PackedColor.getGreen(color);
        int blue = ColorHelper.PackedColor.getBlue(color);
        matrixStackIn.translate(0,0.8f,0);
        renderQuad(WATER, new Color(red,green,blue), combinedLightIn, matrixStackIn, bufferIn);
    }
    public void renderQuad(RenderMaterial material, Color color, int light, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn)
    {
        matrixStackIn.push();
        TextureAtlasSprite sprite = material.getSprite();
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getTranslucentNoCrumbling());
        add(builder, matrixStackIn, color, light, 0, 0, 1, sprite.getMinU(), sprite.getMinV());
        add(builder, matrixStackIn, color, light, 1, 0, 1, sprite.getMinU(), sprite.getMaxV());
        add(builder, matrixStackIn, color, light, 1, 0, 0, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStackIn, color, light, 0, 0, 0, sprite.getMaxU(), sprite.getMinV());
        matrixStackIn.pop();
    }
    private void add(IVertexBuilder renderer, MatrixStack stack, Color color, int light, float x, float y, float z, float u, float v)
    {
        renderer.pos(stack.getLast().getMatrix(), x, y, z).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.8f).tex(u, v).lightmap(light & '\uffff', light >> 16 & '\uffff').normal(1, 0, 0).endVertex();
    }
}