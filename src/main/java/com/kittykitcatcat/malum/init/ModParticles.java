package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticle;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticleData;
import com.kittykitcatcat.malum.particles.bonk.BonkParticle;
import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import com.kittykitcatcat.malum.particles.lensmagic.LensMagicParticle;
import com.kittykitcatcat.malum.particles.lensmagic.LensMagicParticleData;
import com.kittykitcatcat.malum.particles.skull.SkullParticle;
import com.kittykitcatcat.malum.particles.skull.SkullParticleData;
import com.kittykitcatcat.malum.particles.spiritflame.SpiritFlameParticle;
import com.kittykitcatcat.malum.particles.spiritflame.SpiritFlameParticleData;
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
        Minecraft.getInstance().particles.registerFactory(spiritFlame, SpiritFlameParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(lensMagic, LensMagicParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(bonk, BonkParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(skull, SkullParticle.Factory::new);
    }
    public static ParticleType<BloodParticleData> bloodParticle;
    public static ParticleType<SpiritFlameParticleData> spiritFlame;
    public static ParticleType<LensMagicParticleData> lensMagic;
    public static ParticleType<BonkParticleData> bonk;
    public static ParticleType<SkullParticleData> skull;
    @SubscribeEvent
    public static void registerTypeRegistry(RegistryEvent.Register<ParticleType<?>> event)
    {
        bloodParticle = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "blood_particle", new ParticleType<>(false, BloodParticleData.DESERIALIZER));
        spiritFlame = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "spirit_flame", new ParticleType<>(false, SpiritFlameParticleData.DESERIALIZER));
        lensMagic = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "lens_magic", new ParticleType<>(false, LensMagicParticleData.DESERIALIZER));
        bonk = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "bonk", new ParticleType<>(false, BonkParticleData.DESERIALIZER));
        skull = Registry.register(Registry.PARTICLE_TYPE, MalumMod.MODID + ":" + "skull", new ParticleType<>(false, SkullParticleData.DESERIALIZER));
    }
}