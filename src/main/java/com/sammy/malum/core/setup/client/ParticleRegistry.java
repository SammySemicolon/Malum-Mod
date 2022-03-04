package com.sammy.malum.core.setup.client;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.SimpleMalumParticleType;
import com.sammy.malum.client.particles.cut.ScytheAttackParticle;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class ParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MalumMod.MODID);

    public static RegistryObject<SimpleMalumParticleType> WISP_PARTICLE = PARTICLES.register("wisp", SimpleMalumParticleType::new);
    public static RegistryObject<SimpleMalumParticleType> SMOKE_PARTICLE = PARTICLES.register("smoke", SimpleMalumParticleType::new);
    public static RegistryObject<SimpleMalumParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle", SimpleMalumParticleType::new);
    public static RegistryObject<SimpleMalumParticleType> TWINKLE_PARTICLE = PARTICLES.register("twinkle", SimpleMalumParticleType::new);
    public static RegistryObject<SimpleMalumParticleType> STAR_PARTICLE = PARTICLES.register("star", SimpleMalumParticleType::new);

    public static RegistryObject<SpiritFlameParticleType> SPIRIT_FLAME_PARTICLE = PARTICLES.register("spirit_flame", SpiritFlameParticleType::new);

    public static RegistryObject<SimpleParticleType> SCYTHE_CUT_ATTACK_PARTICLE = PARTICLES.register("scythe_cut_attack", ()->new SimpleParticleType(true));
    public static RegistryObject<SimpleParticleType> SCYTHE_SWEEP_ATTACK_PARTICLE = PARTICLES.register("scythe_sweep_attack", ()->new SimpleParticleType(true));

    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(WISP_PARTICLE.get(), SimpleMalumParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SMOKE_PARTICLE.get(), SimpleMalumParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SPARKLE_PARTICLE.get(), SimpleMalumParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(TWINKLE_PARTICLE.get(), SimpleMalumParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(STAR_PARTICLE.get(), SimpleMalumParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SPIRIT_FLAME_PARTICLE.get(), SpiritFlameParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(SCYTHE_CUT_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SCYTHE_SWEEP_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
    }
}