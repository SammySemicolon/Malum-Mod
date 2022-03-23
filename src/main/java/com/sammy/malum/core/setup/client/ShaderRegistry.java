package com.sammy.malum.core.setup.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.shaders.AbstractUniform;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ChainedJsonException;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        registerShader(event, createShaderInstance(additiveTexture, event.getResourceManager(), DataHelper.prefix("additive_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, createShaderInstance(additiveParticle, event.getResourceManager(), DataHelper.prefix("additive_particle"), DefaultVertexFormat.PARTICLE));

        registerShader(event, createShaderInstance(distortedTexture, event.getResourceManager(), DataHelper.prefix("noise/distorted_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, createShaderInstance(metallicNoise, event.getResourceManager(), DataHelper.prefix("noise/metallic"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, createShaderInstance(radialNoise, event.getResourceManager(), DataHelper.prefix("noise/radial_noise"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, createShaderInstance(radialScatterNoise, event.getResourceManager(), DataHelper.prefix("noise/radial_scatter_noise"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));

        registerShader(event, createShaderInstance(scrollingTexture, event.getResourceManager(), DataHelper.prefix("vfx/scrolling_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, createShaderInstance(triangleTexture, event.getResourceManager(), DataHelper.prefix("vfx/triangle_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
        registerShader(event, createShaderInstance(scrollingTriangleTexture, event.getResourceManager(), DataHelper.prefix("vfx/scrolling_triangle_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP));
    }

    public static void registerShader(RegisterShadersEvent event, ExtendedShaderInstance extendedShaderInstance) {
        event.registerShader(extendedShaderInstance, s -> ((ExtendedShaderInstance) s).getHolder().setInstance(s));
    }

    public static ExtendedShaderInstance createShaderInstance(ShaderHolder shaderHolder, ResourceProvider pResourceProvider, ResourceLocation location, VertexFormat pVertexFormat) throws IOException {
        return new ExtendedShaderInstance(pResourceProvider, location, pVertexFormat) {
            @Override
            public ShaderHolder getHolder() {
                return shaderHolder;
            }
        };
    }

    public static class ShaderHolder {
        public ShaderInstance instance;
        public ArrayList<String> uniforms;
        public ArrayList<UniformData> defaultUniformData = new ArrayList<>();
        public final ShaderStateShard shard = new ShaderStateShard(getInstance());

        public ShaderHolder(String... uniforms) {
            this.uniforms = new ArrayList<>(List.of(uniforms));
        }

        public void setInstance(ShaderInstance instance) {
            this.instance = instance;
        }

        public Supplier<ShaderInstance> getInstance() {
            return () -> instance;
        }
    }

    public static class ExtendedShaderInstance extends ShaderInstance {

        public ExtendedShaderInstance(ResourceProvider pResourceProvider, ResourceLocation location, VertexFormat pVertexFormat) throws IOException {
            super(pResourceProvider, location, pVertexFormat);
        }

        public void setUniformDefaults() {
            getHolder().defaultUniformData.forEach(u -> u.setUniformValue(safeGetUniform(u.uniformName)));
        }

        public ShaderHolder getHolder() {
            return null;
        }

        @Override
        public void parseUniformNode(JsonElement pJson) throws ChainedJsonException {
            if (getHolder().uniforms.isEmpty()) {
                super.parseUniformNode(pJson);
                return;
            }
            JsonObject jsonobject = GsonHelper.convertToJsonObject(pJson, "uniform");
            String name = GsonHelper.getAsString(jsonobject, "name");
            int i = Uniform.getTypeFromString(GsonHelper.getAsString(jsonobject, "type"));
            int j = GsonHelper.getAsInt(jsonobject, "count");
            float[] afloat = new float[Math.max(j, 16)];
            JsonArray jsonarray = GsonHelper.getAsJsonArray(jsonobject, "values");
            if (jsonarray.size() != j && jsonarray.size() > 1) {
                throw new ChainedJsonException("Invalid amount of values specified (expected " + j + ", found " + jsonarray.size() + ")");
            } else {
                int k = 0;

                for (JsonElement jsonelement : jsonarray) {
                    try {
                        afloat[k] = GsonHelper.convertToFloat(jsonelement, "value");
                    } catch (Exception exception) {
                        ChainedJsonException chainedjsonexception = ChainedJsonException.forException(exception);
                        chainedjsonexception.prependJsonKey("values[" + k + "]");
                        throw chainedjsonexception;
                    }

                    ++k;
                }

                if (j > 1 && jsonarray.size() == 1) {
                    while (k < j) {
                        afloat[k] = afloat[0];
                        ++k;
                    }
                }

                int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
                Uniform uniform = new Uniform(name, i + l, j, this);
                if (i <= 3) {
                    uniform.setSafe((int) afloat[0], (int) afloat[1], (int) afloat[2], (int) afloat[3]);
                    if (getHolder().uniforms.contains(name)) {
                        getHolder().defaultUniformData.add(new UniformData.IntegerUniformData(name, i, new int[]{(int) afloat[0], (int) afloat[1], (int) afloat[2], (int) afloat[3]}));
                    }
                } else if (i <= 7) {
                    uniform.setSafe(afloat[0], afloat[1], afloat[2], afloat[3]);
                } else {
                    uniform.set(Arrays.copyOfRange(afloat, 0, j));
                }
                if (i > 3) {
                    if (getHolder().uniforms.contains(name)) {
                        getHolder().defaultUniformData.add(new UniformData.FloatUniformData(name, i, afloat));
                    }
                }
                this.uniforms.add(uniform);
            }
        }
    }

    public static class UniformData {
        public final String uniformName;
        public final int uniformType;

        public UniformData(String uniformName, int uniformType) {
            this.uniformName = uniformName;
            this.uniformType = uniformType;
        }

        public void setUniformValue(AbstractUniform uniform) {

        }

        public static class FloatUniformData extends UniformData {
            public final float[] array;

            public FloatUniformData(String uniformName, int uniformType, float[] array) {
                super(uniformName, uniformType);
                this.array = array;
            }

            @Override
            public void setUniformValue(AbstractUniform uniform) {
                if (uniformType <= 7) {
                    uniform.setSafe(array[0], array[1], array[2], array[3]);
                } else {
                    uniform.set(array);
                }
            }
        }

        public static class IntegerUniformData extends UniformData {
            public final int[] array;

            public IntegerUniformData(String uniformName, int uniformType, int[] array) {
                super(uniformName, uniformType);
                this.array = array;
            }

            @Override
            public void setUniformValue(AbstractUniform uniform) {
                uniform.setSafe(array[0], array[1], array[2], array[3]);
            }
        }
    }
}