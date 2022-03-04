package com.sammy.malum.core.setup.client;

import com.google.common.collect.Maps;
import com.sammy.malum.client.particles.SimpleScreenParticleType;
import com.sammy.malum.core.handlers.ScreenParticleHandler;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;

import java.util.ArrayList;
import java.util.Comparator;

public class ScreenParticleRegistry {
    public static final ArrayList<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();

    public static final ScreenParticleType<ScreenParticleOptions> WISP = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> SMOKE = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> SPARKLE = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> TWINKLE = registerType(new SimpleScreenParticleType());
    public static final ScreenParticleType<ScreenParticleOptions> STAR = registerType(new SimpleScreenParticleType());

    static {
        ScreenParticleHandler.PARTICLES = Maps.newTreeMap(Comparator.comparingInt(PARTICLE_TYPES::indexOf));
    }

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        registerProvider(WISP, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("wisp"))));
        registerProvider(SMOKE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("smoke"))));
        registerProvider(SPARKLE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("sparkle"))));
        registerProvider(TWINKLE, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("twinkle"))));
        registerProvider(STAR, new SimpleScreenParticleType.Factory(getSpriteSet(DataHelper.prefix("star"))));
    }

    public static <T extends ScreenParticleOptions> ScreenParticleType<T> registerType(ScreenParticleType<T> type) {
        PARTICLE_TYPES.add(type);
        return type;
    }

    public static <T extends ScreenParticleOptions> void registerProvider(ScreenParticleType<T> type, ScreenParticleType.ParticleProvider<T> provider) {
        type.provider = provider;
    }

    public static SpriteSet getSpriteSet(ResourceLocation resourceLocation) {
        return Minecraft.getInstance().particleEngine.spriteSets.get(resourceLocation);
    }
}