package com.sammy.malum.client.particles.spiritflame;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.rendering.particle.world.FrameSetParticle;
import team.lodestar.lodestone.systems.rendering.particle.world.WorldParticleOptions;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class SpiritFlameParticle extends FrameSetParticle {

    public SpiritFlameParticle(ClientLevel world, WorldParticleOptions options, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, options, spriteSet, x, y, z, xd, yd, zd);
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

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<WorldParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }


        @Nullable
        @Override
        public Particle createParticle(WorldParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            return new SpiritFlameParticle(world, data, (ParticleEngine.MutableSpriteSet) sprite, x, y, z, mx, my, mz);
        }
    }
}