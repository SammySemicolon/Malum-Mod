package com.sammy.malum.registry.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeRenderTypes;
import org.lwjgl.opengl.GL14;
import team.lodestar.lodestone.registry.client.LodestoneRenderTypeRegistry;
import team.lodestar.lodestone.registry.client.LodestoneShaderRegistry;
import team.lodestar.lodestone.systems.rendering.StateShards;
import team.lodestar.lodestone.systems.rendering.rendeertype.RenderTypeProvider;

import java.util.function.Function;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS;

public class RenderTypeRegistry extends RenderStateShard {

    //TODO: move this to lodestone
    public static final Function<RenderStateShard.EmptyTextureStateShard, RenderType> ADDITIVE_TEXT = Util.memoize((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType("malum:glowing_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
                    .setShaderState(ShaderRegistry.ADDITIVE_TEXT.getShard())
                    .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
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

    public static final Function<ResourceLocation, RenderType> SUBTRACTIVE_TEXT = Util.memoize((texture) ->
        LodestoneRenderTypeRegistry.createGenericRenderType("malum:subtractive_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
            .setShaderState(RENDERTYPE_TEXT_SEE_THROUGH_SHADER)
            .setTransparencyState(SUBTRACTIVE_TEXT_TRANSPARENCY)
            .setLightmapState(LIGHTMAP)
            .setTextureState(new TextureStateShard(texture, ForgeRenderTypes.enableTextTextureLinearFiltering, false))));

    public static final Function<ResourceLocation, RenderType> SUBTRACTIVE_INTENSE_TEXT = Util.memoize((texture) ->
        LodestoneRenderTypeRegistry.createGenericRenderType("malum:subtractive_intense_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
            .setShaderState(RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH_SHADER)
            .setTransparencyState(SUBTRACTIVE_TEXT_TRANSPARENCY)
            .setLightmapState(LIGHTMAP)
            .setTextureState(new TextureStateShard(texture, ForgeRenderTypes.enableTextTextureLinearFiltering, false))));

    public static final RenderTypeProvider MALIGNANT_GLOW = new RenderTypeProvider((texture) -> LodestoneRenderTypeRegistry.createGenericRenderType("lodestone:malignant_glow", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
            .setShaderState(LodestoneShaderRegistry.LODESTONE_TEXTURE)
            .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
            .setTextureState(texture)
            .setCullState(RenderStateShard.NO_CULL)));

    public RenderTypeRegistry(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }
}
