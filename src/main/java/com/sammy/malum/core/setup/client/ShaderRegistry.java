package com.sammy.malum.core.setup.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.systems.rendering.ExtendedShaderInstance;
import com.sammy.malum.core.systems.rendering.ShaderHolder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {

    public static ShaderHolder additiveTexture = new ShaderHolder();
    public static ShaderHolder additiveParticle = new ShaderHolder();

    public static ShaderHolder distortedTexture = new ShaderHolder("Speed", "TimeOffset", "Intensity", "XFrequency", "YFrequency", "UVCoordinates");
    public static ShaderHolder metallicNoise = new ShaderHolder("Intensity", "Size", "Speed", "Brightness");
    public static ShaderHolder radialNoise = new ShaderHolder("Speed", "XFrequency", "YFrequency", "Intensity", "ScatterPower", "ScatterFrequency", "DistanceFalloff");
    public static ShaderHolder radialScatterNoise = new ShaderHolder("Speed", "XFrequency", "YFrequency", "Intensity", "ScatterPower", "ScatterFrequency", "DistanceFalloff");

    public static ShaderHolder scrollingTexture = new ShaderHolder("Speed");
    public static ShaderHolder triangleTexture = new ShaderHolder();
    public static ShaderHolder scrollingTriangleTexture = new ShaderHolder("Speed");

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        registerShader(event, ExtendedShaderInstance.createShaderInstance(additiveTexture, event.getResourceManager(), DataHelper.prefix("additive_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, ExtendedShaderInstance.createShaderInstance(additiveParticle, event.getResourceManager(), DataHelper.prefix("additive_particle"), DefaultVertexFormat.PARTICLE));

        registerShader(event, ExtendedShaderInstance.createShaderInstance(distortedTexture, event.getResourceManager(), DataHelper.prefix("noise/distorted_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, ExtendedShaderInstance.createShaderInstance(metallicNoise, event.getResourceManager(), DataHelper.prefix("noise/metallic"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, ExtendedShaderInstance.createShaderInstance(radialNoise, event.getResourceManager(), DataHelper.prefix("noise/radial_noise"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, ExtendedShaderInstance.createShaderInstance(radialScatterNoise, event.getResourceManager(), DataHelper.prefix("noise/radial_scatter_noise"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));

        registerShader(event, ExtendedShaderInstance.createShaderInstance(scrollingTexture, event.getResourceManager(), DataHelper.prefix("vfx/scrolling_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, ExtendedShaderInstance.createShaderInstance(triangleTexture, event.getResourceManager(), DataHelper.prefix("vfx/triangle_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, ExtendedShaderInstance.createShaderInstance(scrollingTriangleTexture, event.getResourceManager(), DataHelper.prefix("vfx/scrolling_triangle_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
    }

    public static void registerShader(RegisterShadersEvent event, ExtendedShaderInstance extendedShaderInstance) {
        event.registerShader(extendedShaderInstance, s -> ((ExtendedShaderInstance) s).getHolder().setInstance(s));
    }

}