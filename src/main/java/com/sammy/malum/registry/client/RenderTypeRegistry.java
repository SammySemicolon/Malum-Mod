package com.sammy.malum.registry.client;

import net.minecraft.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import java.util.function.*;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS;
import static net.minecraft.client.renderer.RenderStateShard.*;

public class RenderTypeRegistry {

    //TODO: move this to lodestone
    public static final Function<RenderStateShard.EmptyTextureStateShard, RenderType> ADDITIVE_TEXT = Util.memoize((texture) ->
            LodestoneRenderTypeRegistry.createGenericRenderType("malum:glowing_text", POSITION_COLOR_TEX_LIGHTMAP, QUADS, LodestoneRenderTypeRegistry.builder()
                    .setShaderState(ShaderRegistry.ADDITIVE_TEXT.getShard())
                    .setTransparencyState(StateShards.ADDITIVE_TRANSPARENCY)
                    .setTextureState(texture)));
}