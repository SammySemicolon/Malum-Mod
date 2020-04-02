package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticle;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticleData;
import com.kittykitcatcat.malum.particles.soulflameparticle.SoulFlameParticle;
import com.kittykitcatcat.malum.particles.soulflameparticle.SoulFlameParticleData;
import com.kittykitcatcat.malum.particles.spiritleaf.SpiritLeafParticle;
import com.kittykitcatcat.malum.particles.spiritleaf.SpiritLeafParticleData;
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
        Minecraft.getInstance().particles.registerFactory(spiritLeaf, SpiritLeafParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(soulFlame, SoulFlameParticle.Factory::new);
    }
    public static ParticleType<SpiritLeafParticleData> spiritLeaf;
    public static ParticleType<BloodParticleData> bloodParticle;
    public static ParticleType<SoulFlameParticleData> soulFlame;
    @SubscribeEvent
    public static void registerTypeRegistry(RegistryEvent.Register<ParticleType<?>> event)
    {
        bloodParticle = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "blood_particle", new ParticleType<>(false, BloodParticleData.DESERIALIZER));
        spiritLeaf = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "spirit_leaf", new ParticleType<>(false, SpiritLeafParticleData.DESERIALIZER));
        soulFlame = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "soul_flame", new ParticleType<>(false, SoulFlameParticleData.DESERIALIZER));
    }
}