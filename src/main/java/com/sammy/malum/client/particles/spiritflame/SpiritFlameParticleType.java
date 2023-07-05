package com.sammy.malum.client.particles.spiritflame;

import com.mojang.serialization.Codec;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleType;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleType;
import team.lodestar.lodestone.systems.particle.screen.base.ScreenParticle;
import team.lodestar.lodestone.systems.particle.world.WorldParticleOptions;

import javax.annotation.Nullable;

public class SpiritFlameParticleType extends ParticleType<WorldParticleOptions> {
    public SpiritFlameParticleType() {
        super(false, WorldParticleOptions.DESERIALIZER);
    }

    @Override
    public Codec<WorldParticleOptions> codec() {
        return WorldParticleOptions.codecFor(this);
    }
}