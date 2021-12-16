package com.sammy.malum.client.particles.wisp;

import com.mojang.serialization.Codec;
import com.sammy.malum.core.systems.rendering.particle.options.ParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;

import javax.annotation.Nullable;

public class WispParticleType extends ParticleType<ParticleOptions> {
    public WispParticleType() {
        super(false, ParticleOptions.DESERIALIZER);
    }


    @Override
    public Codec<ParticleOptions> codec() {
        return ParticleOptions.codecFor(this);
    }

    public static class Factory implements ParticleProvider<ParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }


        @Nullable
        @Override
        public Particle createParticle(ParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            WispParticle ret = new WispParticle(world, data, x, y, z, mx, my, mz);
            ret.pickSprite(sprite);
            return ret;
        }
    }
}
