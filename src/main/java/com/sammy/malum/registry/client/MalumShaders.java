package com.sammy.malum.registry.client;

import com.mojang.blaze3d.vertex.*;
import com.sammy.malum.*;
import net.neoforged.api.distmarker.*;
import net.neoforged.bus.api.*;
import net.neoforged.fml.common.*;
import net.neoforged.neoforge.client.event.*;
import team.lodestar.lodestone.systems.rendering.shader.*;

@EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MALUM)
public class MalumShaders {

    public static ShaderRegister SHADERS = new ShaderRegister(MalumMod.MALUM);

    //BOOK
    public static ShaderHolder PROGRESSION_SCREEN = SHADERS.register("book/progression_screen", DefaultVertexFormat.POSITION_TEX);
    public static ShaderHolder OUTLINED_HUD_ELEMENT = SHADERS.register("book/object_outline", DefaultVertexFormat.POSITION_TEX);


    //HUD
    public static ShaderHolder DISSOLVING_HUD_ELEMENT = SHADERS.register("dissolving_hud_element", DefaultVertexFormat.POSITION_TEX_COLOR);

    //WEEPING WELL
    public static ShaderHolder TOUCH_OF_DARKNESS = SHADERS.register("weeping_well/touch_of_darkness", DefaultVertexFormat.POSITION_TEX_COLOR);
    public static ShaderHolder WEEPING_SKYBOX = SHADERS.register("weeping_well/weeping_skybox", DefaultVertexFormat.POSITION_TEX_COLOR);
    public static ShaderHolder WEEPING_SPYHOLE = SHADERS.register("weeping_well/weeping_spyhole", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) {
        SHADERS.init(event);
    }
}