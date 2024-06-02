package com.sammy.malum.registry.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumMod;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;
import team.lodestar.lodestone.systems.rendering.shader.ExtendedShaderInstance;
import team.lodestar.lodestone.systems.rendering.shader.ShaderHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ShaderRegistry {
    public static List<Pair<ShaderInstance, Consumer<ShaderInstance>>> shaderList;

    public static ShaderHolder TOUCH_OF_DARKNESS = new ShaderHolder(MalumMod.malumPath("touch_of_darkness"), DefaultVertexFormat.POSITION_COLOR_TEX, "Speed", "Zoom", "Distortion", "Intensity", "Wibble");

    public static ShaderHolder DISTORTION = new ShaderHolder(MalumMod.malumPath("distortion"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, "Speed", "Distortion", "Width", "Height", "UVEncasement");

    public static void init(ResourceProvider manager) throws IOException {
        shaderList = new ArrayList<>();
        registerShader(TOUCH_OF_DARKNESS.createInstance(manager));
        registerShader(DISTORTION.createInstance(manager));
    }

    public static void registerShader(ExtendedShaderInstance extendedShaderInstance) {
        registerShader(extendedShaderInstance, (shader) -> ((ExtendedShaderInstance) shader).getShaderHolder());
    }

    public static void registerShader(ShaderInstance shader, Consumer<ShaderInstance> onLoaded) {
        shaderList.add(Pair.of(shader, onLoaded));
    }
}
