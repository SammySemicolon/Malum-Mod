package com.sammy.malum.registry.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.sammy.malum.MalumMod;
import team.lodestar.lodestone.systems.rendering.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.rendering.ShaderHolder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {

    public static ShaderHolder TOUCH_OF_DARKNESS = new ShaderHolder("Speed", "Zoom", "Distortion", "Intensity", "Wibble");
    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        registerShader(event, ExtendedShaderInstance.createShaderInstance(TOUCH_OF_DARKNESS, event.getResourceManager(), MalumMod.malumPath("touch_of_darkness"), DefaultVertexFormat.POSITION_COLOR_TEX));
    }

    public static void registerShader(RegisterShadersEvent event, ExtendedShaderInstance extendedShaderInstance) {
        event.registerShader(extendedShaderInstance, s -> ((ExtendedShaderInstance) s).getHolder().setInstance(((ExtendedShaderInstance) s)));
    }
}