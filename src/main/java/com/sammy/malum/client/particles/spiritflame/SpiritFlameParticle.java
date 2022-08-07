package com.sammy.malum.client.particles.spiritflame;

import team.lodestar.lodestone.systems.rendering.particle.world.FrameSetParticle;
import team.lodestar.lodestone.systems.rendering.particle.world.WorldParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;

public class SpiritFlameParticle extends FrameSetParticle {

    public SpiritFlameParticle(ClientLevel world, WorldParticleOptions data, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, data, spriteSet, x, y, z, xd, yd, zd);
        addFrames(0, 37);
        addLoop(38, 77, 2);
        setLifetime(frameSet.size());
    }

    @Override
    public void tick() {
        super.tick();
        xd *= 0.9f;
        if (age < 5) {
            yd += 0.005f;
        } else {
            yd *= 0.9f;
        }
        zd *= 0.9f;
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }
}