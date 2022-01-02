package com.sammy.malum.core.registry.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MalumMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ShaderRegistry {

    public static ExtendedShaderInstance additiveTexture = new ExtendedShaderInstance();
    public static ExtendedShaderInstance additiveParticle = new ExtendedShaderInstance();
    public static ExtendedShaderInstance distortedTexture = new ExtendedShaderInstance();
    public static ExtendedShaderInstance metallicNoise = new ExtendedShaderInstance();
    public static ExtendedShaderInstance movingTrail = new ExtendedShaderInstance();
    public static ExtendedShaderInstance bootlegTriangle = new ExtendedShaderInstance();
    public static ExtendedShaderInstance movingBootlegTriangle = new ExtendedShaderInstance();

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {

        event.registerShader(new ShaderInstance(event.getResourceManager(), DataHelper.prefix("additive_texture"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> additiveTexture.setInstance(shaderInstance));
        event.registerShader(new ShaderInstance(event.getResourceManager(), DataHelper.prefix("additive_particle"), DefaultVertexFormat.PARTICLE), shaderInstance -> additiveParticle.setInstance(shaderInstance));
        event.registerShader(new ShaderInstance(event.getResourceManager(), DataHelper.prefix("noise/distorted_texture"), DefaultVertexFormat.POSITION_COLOR_TEX), shaderInstance -> distortedTexture.setInstance(shaderInstance));
        event.registerShader(new ShaderInstance(event.getResourceManager(), DataHelper.prefix("noise/metallic"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> metallicNoise.setInstance(shaderInstance));
        event.registerShader(new ShaderInstance(event.getResourceManager(), DataHelper.prefix("vfx/moving_trail"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> movingTrail.setInstance(shaderInstance));
        event.registerShader(new ShaderInstance(event.getResourceManager(), DataHelper.prefix("vfx/bootleg_triangle"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> bootlegTriangle.setInstance(shaderInstance));
        event.registerShader(new ShaderInstance(event.getResourceManager(), DataHelper.prefix("vfx/moving_bootleg_triangle"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> movingBootlegTriangle.setInstance(shaderInstance));
    }

    public static class ExtendedShaderInstance {
        private ShaderInstance instance;
        public final ShaderStateShard shard;

        public ExtendedShaderInstance() {
            this.shard = new ShaderStateShard(getInstance());
        }

        public Supplier<ShaderInstance> getInstance() {
            return () -> instance;
        }
        public void setInstance(ShaderInstance instance)
        {
            this.instance = instance;
        }
    }
}