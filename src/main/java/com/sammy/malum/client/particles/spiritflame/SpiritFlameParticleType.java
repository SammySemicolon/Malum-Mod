package com.sammy.malum.client.particles.spiritflame;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import team.lodestar.lodestone.systems.rendering.particle.world.WorldParticleOptions;

public class SpiritFlameParticleType extends ParticleType<WorldParticleOptions> {
    public SpiritFlameParticleType() {
        super(false, WorldParticleOptions.DESERIALIZER);
    }


    @Override
    public Codec<WorldParticleOptions> codec() {
        return WorldParticleOptions.codecFor(this);
    }
}