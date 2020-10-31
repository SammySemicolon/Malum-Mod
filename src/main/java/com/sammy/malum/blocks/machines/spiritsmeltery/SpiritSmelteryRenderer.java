package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.MalumMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(value = Dist.CLIENT)
public class SpiritSmelteryRenderer extends TileEntityRenderer<SpiritSmelteryTileEntity>
{
    public static final ResourceLocation spirit_fluid_texture = new ResourceLocation(MalumMod.MODID, "other/spirit_fluid");
    
    public SpiritSmelteryRenderer(TileEntityRendererDispatcher rendererDispatcherIn)
    {
        super(rendererDispatcherIn);
    }
    
    
    private void add(IVertexBuilder renderer, MatrixStack stack, float x, float y, float z, float u, float v)
    {
        renderer.pos(stack.getLast().getMatrix(), x, y, z).color(0.95f,0.5f,0.9f,1f).tex(u, v).lightmap(0, 240).normal(1, 0, 0).endVertex();
    }
    float delayedFill = 0f;
    @Override
    public void render(SpiritSmelteryTileEntity blockEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int light, int overlay)
    {
    
        TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(AtlasTexture.LOCATION_BLOCKS_TEXTURE).apply(spirit_fluid_texture);
        IVertexBuilder builder = iRenderTypeBuffer.getBuffer(RenderType.getCutout());
        
        matrixStack.push();
        float frameCount = 20;
        float time = (blockEntity.getWorld().getGameTime() + partialTicks) / 2f;
        int index = (int) Math.abs(time % (frameCount * 2) - frameCount);
        float min = sprite.getMinV();
        float max = sprite.getMaxV();
    
        float finalMinV = min + ((index+1)*((max-min)/frameCount));
        float finalMaxV = min + (index*((max-min)/frameCount));
    
        delayedFill = MathHelper.lerp(0.075f, delayedFill, blockEntity.filled());
        float height = 1.5f + (delayedFill * 1.5f);
        matrixStack.translate(-0.5, 0, -0.5);
        add(builder, matrixStack, 0, height, 0, sprite.getMinU(), finalMinV);
        add(builder, matrixStack, 2, height, 0, sprite.getMaxU(), finalMinV);
        add(builder, matrixStack, 2, height, 2, sprite.getMaxU(), finalMaxV);
        add(builder, matrixStack, 0, height, 2, sprite.getMinU(), finalMaxV);
    
        add(builder, matrixStack, 0, height, 2, sprite.getMinU(), finalMaxV);
        add(builder, matrixStack, 2, height, 2, sprite.getMaxU(), finalMaxV);
        add(builder, matrixStack, 2, height, 0, sprite.getMaxU(), finalMinV);
        add(builder, matrixStack, 0, height, 0, sprite.getMinU(), finalMinV);
        
        matrixStack.pop();
    
    }
}