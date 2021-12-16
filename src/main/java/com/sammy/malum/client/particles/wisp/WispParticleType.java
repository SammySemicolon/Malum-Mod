package com.sammy.malum.client.particles.wisp;

import com.mojang.serialization.Codec;
import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.Level.ClientLevel;
import net.minecraft.particles.ParticleType;

public class WispParticleType extends ParticleType<MalumParticleData> {
    public WispParticleType() {
        super(false, MalumParticleData.DESERIALIZER);
    }

    @Override
    public Codec<MalumParticleData> codec() {
        return MalumParticleData.codecFor(this);
    }

    public static class Factory implements IParticleFactory<MalumParticleData> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(MalumParticleData data, ClientLevel Level, double x, double y, double z, double mx, double my, double mz) {
            WispParticle ret = new WispParticle(Level, data, x, y, z, mx, my, mz);
            ret.pickSprite(sprite);
            return ret;
        }
    }
}
