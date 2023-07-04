package com.sammy.malum.client.particles.spiritflame;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import team.lodestar.lodestone.systems.particle.world.WorldParticleOptions;

public class SpiritFlameParticleType extends ParticleType<WorldParticleOptions> {
    public SpiritFlameParticleType() {
        super(false, WorldParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<WorldParticleOptions> codec() {
        return WorldParticleOptions.codecFor(this);
    }

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