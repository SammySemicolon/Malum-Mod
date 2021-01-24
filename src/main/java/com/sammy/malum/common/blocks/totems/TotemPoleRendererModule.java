package com.sammy.malum.common.blocks.totems;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import com.sammy.malum.core.systems.tileentityrendering.modules.RendererModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;
import java.util.HashMap;

public class TotemPoleRendererModule extends RendererModule
{
    public static HashMap<MalumSpiritType, ResourceLocation> locationHashMap = new HashMap<>();
    
    @Override
    public void render(SimpleTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, TileEntityRendererDispatcher renderDispatcher, int combinedLightIn, int combinedOverlayIn)
    {
        if (tileEntityIn instanceof TotemPoleTileEntity)
        {
            TotemPoleTileEntity totemPoleTileEntity = (TotemPoleTileEntity) tileEntityIn;
            if (totemPoleTileEntity.type == null)
            {
                return;
            }
            if (locationHashMap.isEmpty())
            {
                MalumSpiritTypes.SPIRITS.forEach(
                        s -> locationHashMap.put(s, MalumHelper.prefix("spirit/" + s.identifier + "_overlay"))
                );
            }
            renderTheThing(totemPoleTileEntity,matrixStackIn,bufferIn);
        }
    }
    public Color color(TotemPoleTileEntity totemPoleTileEntity)
    {
        Color color1 = new Color(10, 8, 8);
        Color color2 = totemPoleTileEntity.type.color;
        int red = (int) MathHelper.lerp(totemPoleTileEntity.activeTime /20f,color1.getRed(),color2.getRed());
        int green = (int) MathHelper.lerp(totemPoleTileEntity.activeTime /20f,color1.getGreen(),color2.getGreen());
        int blue = (int) MathHelper.lerp(totemPoleTileEntity.activeTime /20f,color1.getBlue(),color2.getBlue());
        return new Color(red,green,blue);
    }
    public void renderTheThing(TotemPoleTileEntity totemPoleTileEntity, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn)
    {
        ResourceLocation texture = locationHashMap.get(totemPoleTileEntity.type);
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(texture);
        IVertexBuilder builder = bufferIn.getBuffer(RenderType.getCutout());
        
        Color color = color(totemPoleTileEntity);
        matrixStackIn.translate(0.5,0,0.5);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-totemPoleTileEntity.direction.getHorizontalAngle()-90));
        matrixStackIn.translate(-0.5,0,-0.5);
        add(builder, matrixStackIn, color,1.01f, 0, 0, sprite.getMinU(), sprite.getMaxV());
        add(builder, matrixStackIn, color,1.01f, 1, 0, sprite.getMinU(), sprite.getMinV());
        add(builder, matrixStackIn, color,1.01f, 1, 1, sprite.getMaxU(), sprite.getMinV());
        add(builder, matrixStackIn, color,1.01f, 0, 1, sprite.getMaxU(), sprite.getMaxV());
    }
    private void add(IVertexBuilder renderer, MatrixStack stack, Color color, float x, float y, float z, float u, float v)
    {
        renderer.pos(stack.getLast().getMatrix(), x, y, z).color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f,1).tex(u, v).lightmap(240, 240).normal(1, 0, 0).endVertex();
    }
}
