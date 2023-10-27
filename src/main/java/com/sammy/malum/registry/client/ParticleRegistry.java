package com.sammy.malum.registry.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.cut.ScytheAttackParticle;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.particle.type.*;

@SuppressWarnings("unused")
public class ParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MalumMod.MALUM);

    public static RegistryObject<SpiritFlameParticleType> SPIRIT_FLAME_PARTICLE = PARTICLES.register("spirit_flame", SpiritFlameParticleType::new);

    public static RegistryObject<LodestoneParticleType> WEIRD_SQUIGGLE = PARTICLES.register("weird_squiggle", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> LIGHT_SPEC_SMALL = PARTICLES.register("light_spec_small", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> LIGHT_SPEC_LARGE = PARTICLES.register("light_spec_large", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> STAR = PARTICLES.register("star", LodestoneParticleType::new);

    public static RegistryObject<LodestoneSparkParticleType> ROUND_SPARK = PARTICLES.register("round_spark", LodestoneSparkParticleType::new);

    public static RegistryObject<SimpleParticleType> SCYTHE_CUT_ATTACK_PARTICLE = PARTICLES.register("scythe_cut_attack", () -> new SimpleParticleType(true));
    public static RegistryObject<SimpleParticleType> SCYTHE_SWEEP_ATTACK_PARTICLE = PARTICLES.register("scythe_sweep_attack", () -> new SimpleParticleType(true));

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(SPIRIT_FLAME_PARTICLE.get(), SpiritFlameParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(WEIRD_SQUIGGLE.get(), LodestoneParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(LIGHT_SPEC_SMALL.get(), LodestoneParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(LIGHT_SPEC_LARGE.get(), LodestoneParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(STAR.get(), LodestoneParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(ROUND_SPARK.get(), LodestoneSparkParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(SCYTHE_CUT_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SCYTHE_SWEEP_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
    }
}