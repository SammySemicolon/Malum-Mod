package com.sammy.malum.core.systems.rendering.particle.options;


import com.sammy.malum.core.systems.rendering.screenparticle.ScreenParticleType;

public class ScreenParticleOptions extends ParticleOptionsBase {

    public final ScreenParticleType<?> type;
    public ScreenParticleOptions(ScreenParticleType<?> type) {
        this.type = type;
    }
}