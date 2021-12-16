package com.sammy.malum.client.particles.spiritflame;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.core.systems.rendering.particle.GenericAnimatedParticle;
import com.sammy.malum.core.systems.rendering.particle.options.ParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.rendertypes.AdditiveParticleRenderType;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;

public class SpiritFlameParticle extends GenericAnimatedParticle {
    protected SpiritFlameParticle(ClientLevel level, ParticleOptions data, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, SpriteSet spriteSet) {
        super(level, data, xSpeed, ySpeed, zSpeed, x, y, z, spriteSet);
        addFrames(0, 37);
        addLoop(38, 77, 2);
        setLifetime(118);
        hasPhysics = false;
    }

    @Override
    public void tick() {
        super.tick();
        xd *= 0.9f;
        if (data.gravity && age < 5) {
            yd += 0.005f;
        } else {
            yd *= 0.9f;
        }
        zd *= 0.9f;
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(consumer, camera, partialTicks);
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return AdditiveParticleRenderType.INSTANCE;
    }
}