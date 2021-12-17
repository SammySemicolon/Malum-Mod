package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.registry.client.ShaderRegistry;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.PARTICLE;
import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;

public class RenderTypes extends RenderStateShard{
    public RenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }
    public static final RenderType ADDITIVE_PARTICLE = createGenericRenderType("additive_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_PARTICLES);
    public static final RenderType ADDITIVE_BLOCK_PARTICLE = createGenericRenderType("additive_block_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_BLOCKS);

    public static RenderType createAdditiveQuadRenderType(ResourceLocation resourceLocation) {
        return createGenericRenderType("additive_quad", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation);
    }

    public static RenderType createMovingTrailRenderType(TransparencyStateShard transparencyStateShard, ResourceLocation resourceLocation) {
        return createGenericRenderType("moving_trail", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.movingTrail.shard, transparencyStateShard, resourceLocation);
    }

    public static RenderType createBootlegTriangleRenderType(TransparencyStateShard transparencyStateShard, ResourceLocation resourceLocation) {
        return createGenericRenderType("bootleg_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.bootlegTriangle.shard, transparencyStateShard, resourceLocation);
    }

    public static RenderType createMovingBootlegTriangleRenderType(TransparencyStateShard transparencyStateShard, ResourceLocation resourceLocation) {
        return createGenericRenderType("moving_bootleg_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.movingBootlegTriangle.shard, transparencyStateShard, resourceLocation);
    }

    public static RenderType createGenericRenderType(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, TransparencyStateShard transparency, ResourceLocation texture) {
        return RenderType.create(
                MalumMod.MODID + ":" + name, format, mode, 256, false, false,
                RenderType.CompositeState.builder()
                        .setShaderState(shader)
                        .setWriteMaskState(new WriteMaskStateShard(true, true))
                        .setLightmapState(new LightmapStateShard(false))
                        .setTransparencyState(transparency)
                        .setTextureState(new TextureStateShard(texture, false, false))
                        .setCullState(new CullStateShard(true))
                        .createCompositeState(true)
        );
    }
}
