package com.sammy.malum.core.systems.rendering;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.handlers.RenderHandler;
import com.sammy.malum.core.setup.client.ShaderRegistry;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderLevelLastEvent;

import java.util.HashMap;
import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;

public class RenderTypes extends RenderStateShard {
    public RenderTypes(String p_110161_, Runnable p_110162_, Runnable p_110163_) {
        super(p_110161_, p_110162_, p_110163_);
    }

    /**
     * Stores many copies of render types, a copy is a new instance of a render type with the same properties.
     * It's useful when we want to apply different uniform changes with each separate use of our render type.
     * Use the {@link #copy(int, RenderType)} method to create copies.
     */
    public static final HashMap<Pair<Integer, RenderType>, RenderType> COPIES = new HashMap<>();

    public static final RenderType ADDITIVE_PARTICLE = createGenericRenderType("additive_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_PARTICLES);
    public static final RenderType ADDITIVE_BLOCK_PARTICLE = createGenericRenderType("additive_block_particle", PARTICLE, VertexFormat.Mode.QUADS, ShaderRegistry.additiveParticle.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_BLOCKS);
    public static final RenderType ADDITIVE_BLOCK = createGenericRenderType("block", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, TextureAtlas.LOCATION_BLOCKS);

    /**
     * Render Functions. You can create Render Types by statically applying these to your texture. Alternatively, use {@link #GENERIC} if none of the presets suit your needs.
     */
    public static final Function<ResourceLocation, RenderType> SOLID_TEXTURE = (texture) -> createGenericRenderType("solid_texture", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, RenderStateShard.POSITION_COLOR_TEX_LIGHTMAP_SHADER, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> ADDITIVE_TEXTURE = (texture) -> createGenericRenderType("additive_texture", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.additiveTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> RADIAL_NOISE = (texture) -> createGenericRenderType("radial_noise", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.radialNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> RADIAL_SCATTER_NOISE = (texture) -> createGenericRenderType("radial_scatter_noise", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.radialScatterNoise.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> TEXTURE_TRIANGLE = (texture) -> createGenericRenderType("texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.triangleTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);
    public static final Function<ResourceLocation, RenderType> SCROLLING_TEXTURE_TRIANGLE = (texture) -> createGenericRenderType("scrolling_texture_triangle", POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, ShaderRegistry.scrollingTriangleTexture.shard, StateShards.ADDITIVE_TRANSPARENCY, texture);

    public static final Function<RenderTypeData, RenderType> GENERIC = (data) -> createGenericRenderType(data.name, data.format, data.mode, data.shader, data.transparency, data.texture);

    /**
     * Creates a custom render type and creates a buffer builder for it.
     */
    public static RenderType createGenericRenderType(String name, VertexFormat format, VertexFormat.Mode mode, ShaderStateShard shader, TransparencyStateShard transparency, ResourceLocation texture) {
        RenderType type = RenderType.create(
                MalumMod.MODID + ":" + name, format, mode, 256, false, false, RenderType.CompositeState.builder()
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

    /**
     * Queues shader uniform changes for a render type. When we end batches in {@link RenderHandler#renderLast(RenderLevelLastEvent)}, we do so one render type at a time.
     * Prior to ending a batch, we run {@link ShaderUniformHandler#updateShaderData(ShaderInstance)} if one is present for a given render type.
     */
    public static RenderType queueUniformChanges(RenderType type, ShaderUniformHandler handler) {
        RenderHandler.HANDLERS.put(type, handler);
        return type;
    }

    /**
     * Creates a copy of a render type. These are stored in the {@link #COPIES} hashmap, with the key being a pair of original render type and copy index.
     */
    public static RenderType copy(int index, RenderType type) {
        return COPIES.computeIfAbsent(Pair.of(index, type), (p) -> GENERIC.apply(new RenderTypeData((RenderType.CompositeRenderType) type)));
    }

    /**
     * Stores all relevant data from a RenderType.
     */
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

        public RenderTypeData(RenderType.CompositeRenderType type) {
            this(type.toString(), type.format(), type.mode(), type.state.shaderState, type.state.transparencyState, type.state.textureState.cutoutTexture().get());
        }
    }
}