package com.sammy.malum.common.blocks.totem.pole;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.LightType;

import java.awt.*;
import java.util.HashMap;


public class TotemPoleRenderer extends TileEntityRenderer<TotemPoleTileEntity>
{
    public static boolean setup = false;
    public static HashMap<MalumSpiritType, ResourceLocation> overlayHashmap = new HashMap<>();
    public static HashMap<MalumSpiritType, ResourceLocation> cutoutHashmap = new HashMap<>();

    public TotemPoleRenderer(Object rendererDispatcherIn)
    {
        super((TileEntityRendererDispatcher) rendererDispatcherIn);
    }

    @Override
    public void render(TotemPoleTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
    {
        if (tileEntityIn.type == null)
        {
            renderQuad(MalumHelper.prefix("block/runewood_log"), new Color(255,255,255), combinedLightIn,tileEntityIn,matrixStackIn,bufferIn);
            return;
        }
        if (!setup)
        {
            MalumSpiritTypes.SPIRITS.forEach(s ->
                    {
                        overlayHashmap.put(s, MalumHelper.prefix("spirit/" + s.identifier + "_overlay"));
                        cutoutHashmap.put(s, MalumHelper.prefix("spirit/" + s.identifier + "_cutout"));
                    }
            );
            setup = true;
        }
        renderQuad(cutoutHashmap.get(tileEntityIn.type), new Color(255,255,255), combinedLightIn,tileEntityIn,matrixStackIn,bufferIn);
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

    public void renderQuad(ResourceLocation texture, Color color, int light, TotemPoleTileEntity totemPoleTileEntity, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn)
    {
        matrixStackIn.push();
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(texture);
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getCutout());
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