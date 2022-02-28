package com.sammy.malum.core.setup.client;

import com.google.common.collect.Maps;
import com.sammy.malum.client.particles.wisp.WispScreenParticleType;
import com.sammy.malum.core.handlers.ScreenParticleHandler;
import com.sammy.malum.core.systems.rendering.particle.options.ScreenParticleOptions;
import com.sammy.malum.core.systems.rendering.screenparticle.ScreenParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class ScreenParticleRegistry {
    public static final ArrayList<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();

    public static final ScreenParticleType<ScreenParticleOptions> WISP = registerType(new WispScreenParticleType());

    static {
        ScreenParticleHandler.PARTICLES = Maps.newTreeMap(Comparator.comparingInt(PARTICLE_TYPES::indexOf));
    }

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        registerProvider(WISP, new WispScreenParticleType.Factory(Minecraft.getInstance().particleEngine.spriteSets.get(Registry.PARTICLE_TYPE.getKey(ParticleRegistry.WISP_PARTICLE.get()))));
    }

    public static <T extends ScreenParticleOptions> ScreenParticleType<T> registerType(ScreenParticleType<T> type) {
        PARTICLE_TYPES.add(type);
        return type;
    }

    public static <T extends ScreenParticleOptions> void registerProvider(ScreenParticleType<T> type, ScreenParticleType.ParticleProvider<T> provider) {
        type.provider = provider;
    }
}