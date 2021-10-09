package com.sammy.malum.core.mod_systems.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumMod;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtilities {
    public static final RenderState.TransparencyState ADDITIVE_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderState.TransparencyState NORMAL_TRANSPARENCY = new RenderState.TransparencyState("lightning_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    }, () -> {
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static RenderType GLOWING_SPRITE = RenderType.makeType(
            MalumMod.MODID + ":glowing_sprite",
            DefaultVertexFormats.POSITION_COLOR_TEX,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(false))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false, false))
                    .build(false)
    ), GLOWING = RenderType.makeType(
            MalumMod.MODID + ":glowing",
            DefaultVertexFormats.POSITION_COLOR,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .build(false)
    ), DELAYED_PARTICLE = RenderType.makeType(
            MalumMod.MODID + ":delayed_particle",
            DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(NORMAL_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES_TEXTURE, false, false))
                    .build(false)
    ), GLOWING_PARTICLE = RenderType.makeType(
            MalumMod.MODID + ":glowing_particle",
            DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES_TEXTURE, false, false))
                    .build(false)
    ), GLOWING_BLOCK_PARTICLE = RenderType.makeType(
            MalumMod.MODID + ":glowing_particle",
            DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP,
            GL11.GL_QUADS, 256,
            RenderType.State.getBuilder()
                    .shadeModel(new RenderState.ShadeModelState(true))
                    .writeMask(new RenderState.WriteMaskState(true, false))
                    .lightmap(new RenderState.LightmapState(false))
                    .diffuseLighting(new RenderState.DiffuseLightingState(false))
                    .transparency(ADDITIVE_TRANSPARENCY)
                    .texture(new RenderState.TextureState(AtlasTexture.LOCATION_BLOCKS_TEXTURE, false, false))
                    .build(false)
    );
}