package com.sammy.malum.registry.client;

import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.systems.*;
import com.sammy.malum.client.renderer.renderpass.ParallelWorldRenderer;
import net.minecraft.client.renderer.*;
import org.lwjgl.opengl.*;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.*;
import team.lodestar.lodestone.systems.rendering.rendeertype.*;

import static com.mojang.blaze3d.vertex.DefaultVertexFormat.*;
import static com.mojang.blaze3d.vertex.VertexFormat.Mode.*;
import static team.lodestar.lodestone.registry.client.LodestoneRenderTypes.createGenericRenderType;

public class MalumRenderTypes extends RenderStateShard {


//    public static final RenderTypeProvider PALETTE_SWAP = new RenderTypeProvider((token) ->
//            createGenericRenderType(token, "palette_swap", POSITION_COLOR_TEX_LIGHTMAP, QUADS,
//                    b -> b.setStateShards(StateShards.NORMAL_TRANSPARENCY, MalumShaders.PALETTE_SWAP, NO_CULL)));



    public static final RenderTypeProvider WEEPING_SKYBOX = new RenderTypeProvider((token) ->
            createGenericRenderType(token, "weeping_skybox", POSITION_TEX_COLOR, QUADS,
                    b -> b.setStateShards(StateShards.NORMAL_TRANSPARENCY, MalumShaders.WEEPING_SKYBOX, ParallelWorldRenderer.getOutputState())));

    public static final RenderTypeProvider WEEPING_SPYHOLE = new RenderTypeProvider((token) ->
            createGenericRenderType(token, "weeping_spyhole", POSITION_COLOR_TEX_LIGHTMAP, QUADS,
                    b -> b.setStateShards(StateShards.NORMAL_TRANSPARENCY, MalumShaders.WEEPING_SPYHOLE, NO_CULL, COLOR_WRITE, LIGHTMAP)));


    public MalumRenderTypes(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }
}
