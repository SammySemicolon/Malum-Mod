package com.sammy.malum.core.systems.rendering.particle.rendertypes;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.sammy.malum.core.systems.rendering.RenderManager;
import com.sammy.malum.core.systems.rendering.Shaders;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.opengl.GL11;

public class AdditiveParticleRenderType implements ParticleRenderType {
    public static final AdditiveParticleRenderType INSTANCE = new AdditiveParticleRenderType();

    @Override
    public void begin(BufferBuilder builder, TextureManager manager) {
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        RenderSystem.setShader(Shaders.additiveParticle.getInstance());
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
        RenderManager.PARTICLE_MATRIX = RenderSystem.getModelViewMatrix();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    @Override
    public void end(Tesselator tesselator) {
        tesselator.end();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
    }

    public String toString() {
        return "PARTICLE_SHEET_ADDITIVE";
    }
}