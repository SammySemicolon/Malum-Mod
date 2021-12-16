package com.sammy.malum.core.systems.particle.rendertypes;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class AdditiveRenderType implements IParticleRenderType {
    public static final AdditiveRenderType INSTANCE = new AdditiveRenderType();

    private static void beginRenderCommon(BufferBuilder bufferBuilder, TextureManager textureManager) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.alphaFunc(GL11.GL_GEQUAL, 0.00390625f);

        textureManager.bind(AtlasTexture.LOCATION_PARTICLES);
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.PARTICLE);
    }

    private static void endRenderCommon() {
        Minecraft.getInstance().textureManager.getTexture(AtlasTexture.LOCATION_PARTICLES).restoreLastBlurMipmap();
        RenderSystem.enableAlphaTest();
        RenderSystem.defaultAlphaFunc();
        RenderSystem.depthMask(true);
    }

    @Override
    public void begin(BufferBuilder b, TextureManager tex) {
        beginRenderCommon(b, tex);
    }

    @Override
    public void end(Tessellator t) {
        t.end();
        RenderSystem.enableDepthTest();
        endRenderCommon();
    }
}
