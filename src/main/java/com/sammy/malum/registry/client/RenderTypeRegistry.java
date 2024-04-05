package com.sammy.malum.registry.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.*;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ForgeRenderTypes;
import org.lwjgl.opengl.GL14;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.util.function.*;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.*;

public class RenderTypeRegistry extends RenderStateShard {

    //TODO: move this to lodestone
    public static final Function<RenderStateShard.EmptyTextureStateShard, RenderType> ADDITIVE_TEXT = Util.memoize((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType("malum:glowing_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
                    .setShaderState(ShaderRegistry.ADDITIVE_TEXT.getShard())
                    .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
                    .setTextureState(texture)));


    private static final RenderStateShard.TransparencyStateShard SUBTRACTIVE_TEXT_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("malum:subtractive_text_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GL14.glBlendEquation(GL14.GL_FUNC_SUBTRACT);
    }, () -> {
        GL14.glBlendEquation(GL14.GL_FUNC_ADD);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
    });

    public static final Function<ResourceLocation, RenderType> SUBTRACTIVE_TEXT = Util.memoize((texture) ->
        LodestoneRenderTypeRegistry.createGenericRenderType("malum:subtractive_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
            .setShaderState(RENDERTYPE_TEXT_SHADER)
            .setTransparencyState(SUBTRACTIVE_TEXT_TRANSPARENCY)
            .setTextureState(new TextureStateShard(texture, ForgeRenderTypes.enableTextTextureLinearFiltering, false))));

    public static final Function<ResourceLocation, RenderType> SUBTRACTIVE_INTENSE_TEXT = Util.memoize((texture) ->
        LodestoneRenderTypeRegistry.createGenericRenderType("malum:subtractive_intense_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
            .setShaderState(RENDERTYPE_TEXT_INTENSITY_SHADER)
            .setTransparencyState(SUBTRACTIVE_TEXT_TRANSPARENCY)
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
