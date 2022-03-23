package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.handlers.RenderHandler;
import com.sammy.malum.core.setup.client.ShaderRegistry;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class RenderTypes extends RenderStateShard {
    public RenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }

    public static final RenderType ADDITIVE_PARTICLE = createGenericRenderType("additive_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_PARTICLES);
    public static final RenderType ADDITIVE_BLOCK_PARTICLE = createGenericRenderType("additive_block_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_BLOCKS);

    public static final Function<ResourceLocation, RenderType> ADDITIVE_TEXTURE = (texture) -> createGenericRenderType("additive_texture", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> RADIAL_NOISE = (texture) -> createGenericRenderType("radial_noise", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.radialNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> RADIAL_SCATTER_NOISE = (texture) -> createGenericRenderType("radial_scatter_noise", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.radialScatterNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> TEXTURE_TRIANGLE = (texture) -> createGenericRenderType("texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.triangleTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> SCROLLING_TEXTURE_TRIANGLE = (texture) -> createGenericRenderType("scrolling_texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.scrollingTriangleTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);

    public static final Function<RenderTypeData, RenderType> GENERIC = (data) -> createGenericRenderType(data.name, data.format, data.mode, data.shader, data.transparency, data.texture);

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

    public static RenderType bufferUniformChanges(RenderType type, RenderTypeShaderHandler handler) {
        RenderHandler.HANDLERS.put(type, handler);
        return type;
    }

    public static class RenderTypeData {
        public final String name;
        public final VertexFormat format;
        public final VertexFormat.Mode mode;
        public final ShaderStateShard shader;
        public TransparencyStateShard transparency = StateShards.ADDITIVE_TRANSPARENCY;
        public final ResourceLocation texture;

        public RenderTypeData(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, ResourceLocation texture) {
            this.name = name;
            this.format = format;
            this.mode = mode;
            this.shader = shader;
            this.texture = texture;
        }

        public RenderTypeData(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, TransparencyStateShard transparency, ResourceLocation texture) {
            this(name, format, mode, shader, texture);
            this.transparency = transparency;
        }
    }
}