package com.sammy.malum.client.particles.skull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.client.particles.SpriteParticleRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class SkullParticleRenderType extends SpriteParticleRenderType
{
    public static final SkullParticleRenderType INSTANCE = new SkullParticleRenderType();
    
    private static void beginRenderCommon(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        RenderSystem.alphaFunc(GL11.GL_GEQUAL, 0.00390625f);
        
        textureManager.bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
    }
    
    private static void endRenderCommon() {
        Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.depthMask(true);
    }
    
    @Override
    public void beginRender(BufferBuilder b, TextureManager tex) {
        beginRenderCommon(b, tex);
    }
    
    @Override
    public void finishRender(Tessellator t) {
        t.draw();
        RenderSystem.enableDepthTest();
        endRenderCommon();
    }
}