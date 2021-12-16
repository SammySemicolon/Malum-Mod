package com.sammy.malum.client.particles.wisp;


import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.core.systems.rendering.particle.GenericParticle;
import com.sammy.malum.core.systems.rendering.particle.options.ParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.rendertypes.AdditiveParticleRenderType;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;


public class WispParticle extends GenericParticle {
    public WispParticle(ClientLevel world, ParticleOptions data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getLightColor(float p_107249_) {
        return 0xF000F0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return AdditiveParticleRenderType.INSTANCE;
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(consumer, camera, partialTicks);
    }
}