package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.handlers.RenderHandler;
import com.sammy.malum.core.setup.client.ShaderRegistry;
import net.mehvahdjukaar.selene.util.DispenserHelper;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class RenderTypes extends RenderStateShard{
    public RenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }
    public static final RenderType ADDITIVE_PARTICLE = createGenericRenderType("additive_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_PARTICLES);
    public static final RenderType ADDITIVE_BLOCK_PARTICLE = createGenericRenderType("additive_block_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_BLOCKS);

    public static final Function<ResourceLocation, RenderType> ADDITIVE_TEXTURE = Util.memoize((resourceLocation) -> createGenericRenderType("additive_texture", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation));
    public static final Function<ResourceLocation, RenderType> RADIAL_NOISE = Util.memoize((resourceLocation) -> createGenericRenderType("radial_noise_quad", POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, ShaderRegistry.radialNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation));
    public static final Function<ResourceLocation, RenderType> RADIAL_SCATTER_NOISE = Util.memoize((resourceLocation) -> createGenericRenderType("additive_texture", POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, ShaderRegistry.radialScatterNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation));
    public static final Function<ResourceLocation, RenderType> ADDITIVE_TEXTURE_TRAIL = Util.memoize((resourceLocation) -> createGenericRenderType("additive_texture", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.movingTrail.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation));
    public static final Function<ResourceLocation, RenderType> SCROLLING_ADDITIVE_TEXTURE_TRAIL = Util.memoize((resourceLocation) -> createGenericRenderType("additive_texture", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.movingBootlegTriangle.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation));

    public static RenderType createAdditiveQuadRenderType(ResourceLocation resourceLocation) {
        return createGenericRenderType("additive_quad", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation);
    }

    public static RenderType createRadialNoiseQuadRenderType(ResourceLocation resourceLocation) {
        return createGenericRenderType("radial_noise_quad", POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, ShaderRegistry.radialNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation);
    }

    public static RenderType createRadialScatterNoiseQuadRenderType(ResourceLocation resourceLocation) {
        return createGenericRenderType("radial_scatter_noise_quad", POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, ShaderRegistry.radialScatterNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, resourceLocation);
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

    public static RenderType createDistortedBlitRenderType(ResourceLocation resourceLocation) {
        return createGenericRenderType("distorted_blit", POSITION_TEX_COLOR, VertexFormat.Mode.QUADS, ShaderRegistry.distortedTexture.shard, StateShards.NORMAL_TRANSPARENCY, resourceLocation);
    }

    public static RenderType createGenericRenderType(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, TransparencyStateShard transparency, ResourceLocation texture) {
        RenderType type = RenderType.create(
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
        RenderHandler.BUFFERS.put(type, new BufferBuilder(type.bufferSize()));
        return type;
    }
    public static RenderType withShaderHandler(RenderType type, RenderTypeShaderHandler handler)
    {
        RenderHandler.HANDLERS.put(type, handler);
        return type;
    }
}
