package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticle;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticleData;
import com.kittykitcatcat.malum.particles.bonk.BonkParticle;
import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import com.kittykitcatcat.malum.particles.soulflameparticle.SoulFlameParticle;
import com.kittykitcatcat.malum.particles.soulflameparticle.SoulFlameParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles
{
    @SubscribeEvent
    public static void registerFactoryRegistry(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particles.registerFactory(bloodParticle, BloodParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(soulFlame, SoulFlameParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(bonk, BonkParticle.Factory::new);
    }
    public static ParticleType<BloodParticleData> bloodParticle;
    public static ParticleType<SoulFlameParticleData> soulFlame;
    public static ParticleType<BonkParticleData> bonk;
    @SubscribeEvent
    public static void registerTypeRegistry(RegistryEvent.Register<ParticleType<?>> event)
    {
        bloodParticle = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "blood_particle", new ParticleType<>(false, BloodParticleData.DESERIALIZER));
        soulFlame = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "soul_flame", new ParticleType<>(false, SoulFlameParticleData.DESERIALIZER));
        bonk = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "bonk", new ParticleType<>(false, BonkParticleData.DESERIALIZER));
    }
}