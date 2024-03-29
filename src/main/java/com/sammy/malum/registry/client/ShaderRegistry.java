package com.sammy.malum.registry.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.sammy.malum.MalumMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import team.lodestar.lodestone.registry.client.*;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {
    public static ShaderHolder TOUCH_OF_DARKNESS = new ShaderHolder(MalumMod.malumPath("touch_of_darkness"), DefaultVertexFormat.POSITION_COLOR_TEX, "Speed", "Zoom", "Distortion", "Intensity", "Wibble");
    public static ShaderHolder ADDITIVE_TEXT = new ShaderHolder(MalumMod.malumPath("additive_text"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        LodestoneShaderRegistry.registerShader(event, TOUCH_OF_DARKNESS.createInstance(event.getResourceProvider()));
        LodestoneShaderRegistry.registerShader(event, ADDITIVE_TEXT.createInstance(event.getResourceProvider()));
    }
}
