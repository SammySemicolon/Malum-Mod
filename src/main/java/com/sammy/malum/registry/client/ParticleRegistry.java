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
import team.lodestar.lodestone.systems.particle.type.LodestoneDirectionalParticleType;
import team.lodestar.lodestone.systems.particle.type.LodestoneParticleType;
import team.lodestar.lodestone.systems.particle.type.LodestoneSparkParticleType;

@SuppressWarnings("unused")
public class ParticleRegistry {
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MalumMod.MALUM);

    public static RegistryObject<SpiritFlameParticleType> SPIRIT_FLAME_PARTICLE = PARTICLES.register("spirit_flame", SpiritFlameParticleType::new);

    public static RegistryObject<LodestoneParticleType> WEIRD_SQUIGGLE = PARTICLES.register("weird_squiggle", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> LIGHT_SPEC_SMALL = PARTICLES.register("light_spec_small", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> LIGHT_SPEC_LARGE = PARTICLES.register("light_spec_large", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> STAR = PARTICLES.register("star", LodestoneParticleType::new);
    public static RegistryObject<LodestoneParticleType> STRANGE_SMOKE = PARTICLES.register("strange_smoke", LodestoneParticleType::new);

    public static RegistryObject<LodestoneSparkParticleType> ROUND_SPARK = PARTICLES.register("round_spark", LodestoneSparkParticleType::new);
    public static RegistryObject<LodestoneSparkParticleType> BOLT = PARTICLES.register("bolt", LodestoneSparkParticleType::new);

    public static RegistryObject<LodestoneDirectionalParticleType> RITUAL_CIRCLE = PARTICLES.register("ritual_circle", LodestoneDirectionalParticleType::new);
    public static RegistryObject<LodestoneDirectionalParticleType> RITUAL_CIRCLE_WISP = PARTICLES.register("ritual_circle_wisp", LodestoneDirectionalParticleType::new);
    public static RegistryObject<LodestoneDirectionalParticleType> SHARP_SPARK = PARTICLES.register("sharp_spark", LodestoneDirectionalParticleType::new);
    public static RegistryObject<LodestoneDirectionalParticleType> HEXAGON = PARTICLES.register("hexagon", LodestoneDirectionalParticleType::new);
    public static RegistryObject<LodestoneDirectionalParticleType> CIRCLE = PARTICLES.register("circle", LodestoneDirectionalParticleType::new);
    public static RegistryObject<LodestoneDirectionalParticleType> SQUARE = PARTICLES.register("square", LodestoneDirectionalParticleType::new);
    public static RegistryObject<LodestoneDirectionalParticleType> SAW = PARTICLES.register("saw", LodestoneDirectionalParticleType::new);

    public static RegistryObject<SimpleParticleType> SCYTHE_CUT_PARTICLE = PARTICLES.register("scythe_cut", () -> new SimpleParticleType(true));
    public static RegistryObject<SimpleParticleType> SCYTHE_SWEEP_PARTICLE = PARTICLES.register("scythe_sweep", () -> new SimpleParticleType(true));
    public static RegistryObject<SimpleParticleType> STAFF_SLAM_PARTICLE = PARTICLES.register("staff_slam", () -> new SimpleParticleType(true));

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(SPIRIT_FLAME_PARTICLE.get(), SpiritFlameParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(WEIRD_SQUIGGLE.get(), LodestoneParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(LIGHT_SPEC_SMALL.get(), LodestoneParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(LIGHT_SPEC_LARGE.get(), LodestoneParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(STRANGE_SMOKE.get(), LodestoneParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(STAR.get(), LodestoneParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(ROUND_SPARK.get(), LodestoneSparkParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(BOLT.get(), LodestoneSparkParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(RITUAL_CIRCLE.get(), LodestoneDirectionalParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(RITUAL_CIRCLE_WISP.get(), LodestoneDirectionalParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SHARP_SPARK.get(), LodestoneDirectionalParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(HEXAGON.get(), LodestoneDirectionalParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(CIRCLE.get(), LodestoneDirectionalParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SQUARE.get(), LodestoneDirectionalParticleType.Factory::new);
        Minecraft.getInstance().particleEngine.register(SAW.get(), LodestoneDirectionalParticleType.Factory::new);

        Minecraft.getInstance().particleEngine.register(SCYTHE_CUT_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(SCYTHE_SWEEP_PARTICLE.get(), ScytheAttackParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(STAFF_SLAM_PARTICLE.get(), ScytheAttackParticle.Factory::new);
    }
}