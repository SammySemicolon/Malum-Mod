package com.sammy.malum.registry.client;

import com.sammy.malum.*;
import team.lodestar.lodestone.systems.particle.options.*;
import team.lodestar.lodestone.systems.particle.screen.*;
import team.lodestar.lodestone.systems.particle.type.*;

import static team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleRegistry.*;

@SuppressWarnings("unused")
public class ScreenParticleRegistry {
    public static final ScreenParticleType<ScreenParticleOptions> SAW = registerType(new LodestoneScreenParticleType());

    public static void registerParticleFactory() {
        registerProvider(SAW, new LodestoneScreenParticleType.Factory(getSpriteSet(MalumMod.malumPath("saw"))));
    }
}