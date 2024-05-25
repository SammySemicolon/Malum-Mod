package com.sammy.malum.registry.client;

import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import net.minecraft.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.util.function.*;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.*;

public class RenderTypeRegistry extends RenderStateShard {

    //TODO: move this to lodestone
    public static final Function<RenderStateShard.EmptyTextureStateShard, RenderType> ADDITIVE_TEXT = Util.memoize((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType("malum:additive_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
                    .setShaderState(ShaderRegistry.ADDITIVE_TEXT.getShard())
                    .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
                    .setTextureState(texture)));
    public static final Function<RenderStateShard.EmptyTextureStateShard, RenderType> TRANSLUCENT_TEXT = Util.memoize((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType("malum:translucent_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
                    .setShaderState(ShaderRegistry.ADDITIVE_TEXT.getShard())
                    .setTransparencyState(StateShards.TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(texture)));

    private static final RenderStateShard.TransparencyStateShard SUBTRACTIVE_TEXT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("malum:subtractive_text_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        RenderSystem.blendEquation(GL14.GL_FUNC_SUBTRACT);
    }, () -> {
        RenderSystem.blendEquation(GL14.GL_FUNC_ADD);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final RenderTypeProvider ADDITIVE_DISTORTED_TEXTURE = new RenderTypeProvider((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType(texture.getIdentifier() + ":additive_distorted_texture", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
                    .setShaderState(ShaderRegistry.DISTORTION)
                    .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setCullState(CULL)
                    .setTextureState(new ResourceLocation(texture.getIdentifier()))));

    public static final RenderTypeProvider TRANSPARENT_DISTORTED_TEXTURE = new RenderTypeProvider((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType(texture.getIdentifier() + ":transparent_distorted_texture", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
                    .setShaderState(ShaderRegistry.DISTORTION)
                    .setTransparencyState(StateShards.TRANSLUCENT_TRANSPARENCY)
                    .setLightmapState(LIGHTMAP)
                    .setCullState(CULL)
                    .setTextureState(new ResourceLocation(texture.getIdentifier()))));

    public static final RenderTypeProvider SUBTRACTIVE_TEXT = new RenderTypeProvider((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType("malum:subtractive_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
            .setShaderState(RENDERTYPE_TEXT_SEE_THROUGH_SHADER)
            .setTransparencyState(SUBTRACTIVE_TEXT_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .setLightmapState(LIGHTMAP)
            .setTextureState(new TextureStateShard(new ResourceLocation(texture.getIdentifier()), false, false))));

    public static final RenderTypeProvider SUBTRACTIVE_INTENSE_TEXT = new RenderTypeProvider((texture) ->
        LodestoneRenderTypeRegistry.createGenericRenderType("malum:subtractive_intense_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
            .setShaderState(RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH_SHADER)
            .setTransparencyState(SUBTRACTIVE_TEXT_TRANSPARENCY)
            .setWriteMaskState(COLOR_WRITE)
            .setLightmapState(LIGHTMAP)
            .setTextureState(new TextureStateShard(new ResourceLocation(texture.getIdentifier()), false, false))));

    public static final RenderTypeProvider MALIGNANT_GLOW = new RenderTypeProvider((texture) -> LodestoneRenderTypeRegistry.createGenericRenderType("lodestone:malignant_glow", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
            .setShaderState(LodestoneShaderRegistry.LODESTONE_TEXTURE)
            .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
            .setTextureState(new ResourceLocation(texture.getIdentifier()))
            .setCullState(RenderStateShard.NO_CULL)));

    public RenderTypeRegistry(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }
}
