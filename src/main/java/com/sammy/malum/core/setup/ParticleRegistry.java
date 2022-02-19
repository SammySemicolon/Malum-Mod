package com.sammy.malum.core.setup;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.cut.ScytheAttackParticle;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleType;
import com.sammy.malum.client.particles.wisp.WispParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MalumMod.MODID);

    public static RegistryObject<WispParticleType> WISP_PARTICLE = PARTICLES.register("wisp_particle", WispParticleType::new);
    public static RegistryObject<WispParticleType> SMOKE_PARTICLE = PARTICLES.register("smoke_particle", WispParticleType::new);
    public static RegistryObject<WispParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle_particle", WispParticleType::new);
    public static RegistryObject<WispParticleType> TWINKLE_PARTICLE = PARTICLES.register("twinkle_particle", WispParticleType::new);
    public static RegistryObject<WispParticleType> STAR_PARTICLE = PARTICLES.register("star_particle", WispParticleType::new);

    public static RegistryObject<SpiritFlameParticleType> SPIRIT_FLAME_PARTICLE = PARTICLES.register("spirit_flame_particle", SpiritFlameParticleType::new);

    public static RegistryObject<SimpleParticleType> SCYTHE_CUT_ATTACK_PARTICLE = PARTICLES.register("scythe_cut_attack_particle", ()->new SimpleParticleType(true));
    public static RegistryObject<SimpleParticleType> SCYTHE_SWEEP_ATTACK_PARTICLE = PARTICLES.register("scythe_sweep_attack_particle", ()->new SimpleParticleType(true));

    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(WISP_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SMOKE_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SPARKLE_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(TWINKLE_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(STAR_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SPIRIT_FLAME_PARTICLE.get(), SpiritFlameParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(SCYTHE_CUT_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SCYTHE_SWEEP_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
    }
}