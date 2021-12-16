package com.sammy.malum.core.systems.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sammy.malum.MalumMod;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
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

    public static RenderType GLOWING_SPRITE = RenderType.create(
            MalumMod.MODID + ":glowing_sprite",
            DefaultVertexFormats.POSITION_COLOR_TEX,
            GL11.GL_QUADS, 256,
            RenderType.State.builder()
                    .setShadeModelState(new RenderState.ShadeModelState(false))
                    .setWriteMaskState(new RenderState.WriteMaskState(true, false))
                    .setLightmapState(new RenderState.LightmapState(false))
                    .setDiffuseLightingState(new RenderState.DiffuseLightingState(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderState.TextureState(AtlasTexture.LOCATION_BLOCKS, false, false))
                    .createCompositeState(false)
    ), GLOWING = RenderType.create(
            MalumMod.MODID + ":glowing",
            DefaultVertexFormats.POSITION_COLOR,
            GL11.GL_QUADS, 256,
            RenderType.State.builder()
                    .setShadeModelState(new RenderState.ShadeModelState(true))
                    .setWriteMaskState(new RenderState.WriteMaskState(true, false))
                    .setLightmapState(new RenderState.LightmapState(false))
                    .setDiffuseLightingState(new RenderState.DiffuseLightingState(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .createCompositeState(false)
    ), DELAYED_PARTICLE = RenderType.create(
            MalumMod.MODID + ":delayed_particle",
            DefaultVertexFormats.PARTICLE,
            GL11.GL_QUADS, 256,
            RenderType.State.builder()
                    .setShadeModelState(new RenderState.ShadeModelState(true))
                    .setWriteMaskState(new RenderState.WriteMaskState(true, false))
                    .setLightmapState(new RenderState.LightmapState(false))
                    .setDiffuseLightingState(new RenderState.DiffuseLightingState(false))
                    .setTransparencyState(NORMAL_TRANSPARENCY)
                    .setTextureState(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES, false, false))
                    .createCompositeState(false)
    ), GLOWING_PARTICLE = RenderType.create(
            MalumMod.MODID + ":glowing_particle",
            DefaultVertexFormats.PARTICLE,
            GL11.GL_QUADS, 256,
            RenderType.State.builder()
                    .setShadeModelState(new RenderState.ShadeModelState(true))
                    .setWriteMaskState(new RenderState.WriteMaskState(true, false))
                    .setLightmapState(new RenderState.LightmapState(false))
                    .setDiffuseLightingState(new RenderState.DiffuseLightingState(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderState.TextureState(AtlasTexture.LOCATION_PARTICLES, false, false))
                    .createCompositeState(false)
    ), GLOWING_BLOCK_PARTICLE = RenderType.create(
            MalumMod.MODID + ":glowing_particle",
            DefaultVertexFormats.PARTICLE,
            GL11.GL_QUADS, 256,
            RenderType.State.builder()
                    .setShadeModelState(new RenderState.ShadeModelState(true))
                    .setWriteMaskState(new RenderState.WriteMaskState(true, false))
                    .setLightmapState(new RenderState.LightmapState(false))
                    .setDiffuseLightingState(new RenderState.DiffuseLightingState(false))
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setTextureState(new RenderState.TextureState(AtlasTexture.LOCATION_BLOCKS, false, false))
                    .createCompositeState(false)
    );
}