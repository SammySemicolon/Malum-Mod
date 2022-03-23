package com.sammy.malum.core.setup.client;

import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.function.Supplier;

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

        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("additive_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> additiveTexture.setInstance(shaderInstance));
        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("additive_particle"), DefaultVertexFormat.PARTICLE), shaderInstance -> additiveParticle.setInstance(shaderInstance));

        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("noise/distorted_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> distortedTexture.setInstance(shaderInstance));
        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("noise/metallic"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> metallicNoise.setInstance(shaderInstance));
        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("noise/radial_noise"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> radialNoise.setInstance(shaderInstance));
        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("noise/radial_scatter_noise"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> radialScatterNoise.setInstance(shaderInstance));

        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("vfx/scrolling_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> scrollingTexture.setInstance(shaderInstance));
        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("vfx/triangle_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> triangleTexture.setInstance(shaderInstance));
        event.registerShader(new ExtendedShaderInstance(event.getResourceManager(), DataHelper.prefix("vfx/scrolling_triangle_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> scrollingTriangleTexture.setInstance(shaderInstance));
    }

    public static class ShaderHolder {
        public ExtendedShaderInstance instance;
        public ArrayList<UniformData> defaultUniformData = new ArrayList<>();
        public String[] uniforms;
        public final ShaderStateShard shard = new ShaderStateShard(getInstance());

        public ShaderHolder(String... uniforms) {
            this.uniforms = uniforms;
        }

        public void setInstance(ShaderInstance instance) {
            this.instance = (ExtendedShaderInstance) instance;
            this.instance.holder = this;
            //We cache the default values of select uniforms so that we can reset their values after rendering.
            for (String uniformName : uniforms) {
                if (instance.safeGetUniform(uniformName) instanceof Uniform uniform) {
                    if (uniform.getType() <= 3) {
                        IntBuffer intBuffer = uniform.getIntBuffer();
                        if (intBuffer.hasArray()) {
                            defaultUniformData.add(new UniformData.IntegerUniformData(uniformName, intBuffer.array()));
                        }
                    }
                    else {
                        FloatBuffer floatBuffer = uniform.getFloatBuffer();
                        if (floatBuffer.hasArray()) {
                            defaultUniformData.add(new UniformData.FloatUniformData(uniformName, floatBuffer.array()));
                        }
                    }
                }
            }
        }

        public Supplier<ShaderInstance> getInstance() {
            return () -> instance;
        }
    }

    public static class ExtendedShaderInstance extends ShaderInstance {
        public ShaderHolder holder;
        public ExtendedShaderInstance(ResourceProvider pResourceProvider, ResourceLocation location, VertexFormat pVertexFormat) throws IOException {
            super(pResourceProvider, location, pVertexFormat);
        }
    }

    public static class UniformData {
        public final String uniformName;

        public UniformData(String uniformName) {
            this.uniformName = uniformName;
        }

        public static class FloatUniformData extends UniformData {
            public final float[] array;

            public FloatUniformData(String uniformName, float[] array) {
                super(uniformName);
                this.array = array;
            }
        }

        public static class IntegerUniformData extends UniformData {
            public final int[] array;

            public IntegerUniformData(String uniformName, int[] array) {
                super(uniformName);
                this.array = array;
            }
        }
    }
}