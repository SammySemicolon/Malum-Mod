package com.sammy.malum.core.setup.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.cut.ScytheAttackParticle;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticle;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MalumMod.MALUM);

    public static RegistryObject<SpiritFlameParticleType> SPIRIT_FLAME_PARTICLE = PARTICLES.register("spirit_flame", SpiritFlameParticleType::new);
    public static RegistryObject<SimpleParticleType> SCYTHE_CUT_ATTACK_PARTICLE = PARTICLES.register("scythe_cut_attack", () -> new SimpleParticleType(true));
    public static RegistryObject<SimpleParticleType> SCYTHE_SWEEP_ATTACK_PARTICLE = PARTICLES.register("scythe_sweep_attack", () -> new SimpleParticleType(true));

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        event.register(SPIRIT_FLAME_PARTICLE.get(), SpiritFlameParticle.Factory::new);
        event.register(SCYTHE_CUT_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        event.register(SCYTHE_SWEEP_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
    }
}