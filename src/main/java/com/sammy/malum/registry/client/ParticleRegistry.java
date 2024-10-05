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
import team.lodestar.lodestone.systems.particle.world.type.*;

@SuppressWarnings("unused")
public class ParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MalumMod.MALUM);

    public static RegistryObject<SpiritFlameParticleType> SPIRIT_FLAME_PARTICLE = PARTICLES.register("spirit_flame", SpiritFlameParticleType::new);

    public static RegistryObject<LodestoneWorldParticleType> WEIRD_SQUIGGLE = PARTICLES.register("weird_squiggle", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> LIGHT_SPEC_SMALL = PARTICLES.register("light_spec_small", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> LIGHT_SPEC_LARGE = PARTICLES.register("light_spec_large", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> STAR = PARTICLES.register("star", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> STRANGE_SMOKE = PARTICLES.register("strange_smoke", LodestoneWorldParticleType::new);

    public static RegistryObject<LodestoneWorldParticleType> ROUND_SPARK = PARTICLES.register("round_spark", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> BOLT = PARTICLES.register("bolt", LodestoneWorldParticleType::new);


    public static RegistryObject<LodestoneWorldParticleType> RITUAL_CIRCLE = PARTICLES.register("ritual_circle", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> RITUAL_CIRCLE_WISP = PARTICLES.register("ritual_circle_wisp", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SHARP_SPARK = PARTICLES.register("sharp_spark", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> HEXAGON = PARTICLES.register("hexagon", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> CIRCLE = PARTICLES.register("circle", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SQUARE = PARTICLES.register("square", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> SAW = PARTICLES.register("saw", LodestoneWorldParticleType::new);

    public static RegistryObject<LodestoneWorldParticleType> SLASH = PARTICLES.register("slash", LodestoneWorldParticleType::new);
    public static RegistryObject<LodestoneWorldParticleType> HEAVY_SLASH = PARTICLES.register("heavy_slash", LodestoneWorldParticleType::new);


    public static RegistryObject<SimpleParticleType> SCYTHE_CUT_PARTICLE = PARTICLES.register("scythe_cut", () -> new SimpleParticleType(true));
    public static RegistryObject<SimpleParticleType> SCYTHE_SWEEP_PARTICLE = PARTICLES.register("scythe_sweep", () -> new SimpleParticleType(true));
    public static RegistryObject<SimpleParticleType> STAFF_SLAM_PARTICLE = PARTICLES.register("staff_slam", () -> new SimpleParticleType(true));

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(SPIRIT_FLAME_PARTICLE.get(), SpiritFlameParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(WEIRD_SQUIGGLE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(LIGHT_SPEC_SMALL.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(LIGHT_SPEC_LARGE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(STRANGE_SMOKE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(STAR.get(), LodestoneWorldParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(ROUND_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(BOLT.get(), LodestoneWorldParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(RITUAL_CIRCLE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(RITUAL_CIRCLE_WISP.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SHARP_SPARK.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(HEXAGON.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(CIRCLE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SQUARE.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SAW.get(), LodestoneWorldParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(SLASH.get(), LodestoneWorldParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(HEAVY_SLASH.get(), LodestoneWorldParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(SCYTHE_CUT_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SCYTHE_SWEEP_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(STAFF_SLAM_PARTICLE.get(), ScytheAttackParticle.Factory::new);
    }
}