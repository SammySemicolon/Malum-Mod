package com.sammy.malum.client.tile_renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.common.tile.TotemPoleTileEntity;
import com.sammy.malum.core.init.MalumSpiritTypes;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;
import java.util.HashMap;


public class TotemPoleRenderer extends TileEntityRenderer<TotemPoleTileEntity>
{
    public static boolean setup = false;
    public static HashMap<MalumSpiritType, RenderMaterial> overlayHashmap = new HashMap<>();
    public static HashMap<MalumSpiritType, RenderMaterial> cutoutHashmap = new HashMap<>();
    public static HashMap<MalumSpiritType, RenderMaterial> corruptedCutoutHashmap = new HashMap<>();
    public static final RenderMaterial RUNEWOOD_LOG = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, MalumHelper.prefix("block/runewood_log"));

    public TotemPoleRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
    }

    @Override
    public void render(TotemPoleTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (tileEntityIn.type == null)
        {
            renderQuad(RUNEWOOD_LOG, new Color(255,255,255), combinedLightIn,tileEntityIn,matrixStackIn,bufferIn);
            return;
        }
        if (!setup)
        {
            MalumSpiritTypes.SPIRITS.forEach(s ->
                    {
                        overlayHashmap.put(s, new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, MalumHelper.prefix("spirit/" + "overlay_" + s.identifier)));
                        cutoutHashmap.put(s, new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, MalumHelper.prefix("spirit/" + "cutout_" + s.identifier)));
                        corruptedCutoutHashmap.put(s, new RenderMaterial(AtlasTexture.LOCATION_BLOCKS_TEXTURE, MalumHelper.prefix("spirit/" + "corrupted_cutout_" + s.identifier)));
                    }
            );
            setup = true;
        }
        if (((TotemPoleBlock)tileEntityIn.getBlockState().getBlock()).corrupted) {
            renderQuad(corruptedCutoutHashmap.get(tileEntityIn.type), new Color(255, 255, 255), combinedLightIn, tileEntityIn, matrixStackIn, bufferIn);
        }
        else
        {
            renderQuad(cutoutHashmap.get(tileEntityIn.type), new Color(255, 255, 255), combinedLightIn, tileEntityIn, matrixStackIn, bufferIn);
        }
        renderQuad(overlayHashmap.get(tileEntityIn.type), color(tileEntityIn), combinedLightIn,tileEntityIn,matrixStackIn,bufferIn);
    }

    public Color color(TotemPoleTileEntity totemPoleTileEntity)
    {
        Color color1 = new Color(12, 8, 7);
        Color color2 = totemPoleTileEntity.type.color;
        int red = (int) MathHelper.lerp(totemPoleTileEntity.currentColor / 20f, color1.getRed(), color2.getRed());
        int green = (int) MathHelper.lerp(totemPoleTileEntity.currentColor / 20f, color1.getGreen(), color2.getGreen());
        int blue = (int) MathHelper.lerp(totemPoleTileEntity.currentColor / 20f, color1.getBlue(), color2.getBlue());
        return new Color(red,green,blue);
    }

    public void renderQuad(RenderMaterial material, Color color, int light, TotemPoleTileEntity totemPoleTileEntity, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn)
    {
        matrixStackIn.push();
        TextureAtlasSprite sprite = material.getSprite();
        IVertexBuilder builder = material.getBuffer(bufferIn, r -> RenderType.getCutout());
        matrixStackIn.translate(0.5, 0, 0.5);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(rotation(totemPoleTileEntity)));
        matrixStackIn.translate(-0.5, 0, -0.5);
        add(builder, matrixStackIn, color, light, 1f, 1, 1, sprite.getMinU(), sprite.getMinV());
        add(builder, matrixStackIn, color, light, 1f, 0, 1, sprite.getMinU(), sprite.getMaxV());
        add(builder, matrixStackIn, color, light, 1f, 0, 0, sprite.getMaxU(), sprite.getMaxV());
        add(builder, matrixStackIn, color, light, 1f, 1, 0, sprite.getMaxU(), sprite.getMinV());

        matrixStackIn.pop();
    }
    public float rotation(TotemPoleTileEntity totemPoleTileEntity)
    {
        Direction direction = totemPoleTileEntity.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING);
        if (direction == Direction.NORTH || direction == Direction.SOUTH)
        {
            direction = direction.getOpposite();
        }
        return 90+direction.getHorizontalAngle();
    }
    private void add(IVertexBuilder renderer, MatrixStack stack, Color color, int light, float x, float y, float z, float u, float v)
    {
        renderer.pos(stack.getLast().getMatrix(), x, y, z).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1).tex(u, v).lightmap(light & '\uffff', light >> 16 & '\uffff').normal(1, 0, 0).endVertex();
    }
}