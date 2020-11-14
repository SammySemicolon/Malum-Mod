package com.sammy.malum.init;

import com.sammy.malum.particles.particletypes.bonk.BonkParticle;
import com.sammy.malum.particles.particletypes.bonk.BonkParticleData;
import com.sammy.malum.particles.particletypes.charm.HeartParticle;
import com.sammy.malum.particles.particletypes.charm.HeartParticleData;
import com.sammy.malum.particles.particletypes.lensmagic.LensMagicParticle;
import com.sammy.malum.particles.particletypes.lensmagic.LensMagicParticleData;
import com.sammy.malum.particles.particletypes.skull.SkullParticle;
import com.sammy.malum.particles.particletypes.skull.SkullParticleData;
import com.sammy.malum.particles.particletypes.spiritflame.SpiritFlameParticle;
import com.sammy.malum.particles.particletypes.spiritflame.SpiritFlameParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles
{
    public static ParticleType<SpiritFlameParticleData> spiritFlame;
    public static ParticleType<LensMagicParticleData> lensMagic;
    public static ParticleType<BonkParticleData> bonk;
    public static ParticleType<SkullParticleData> skull;
    public static ParticleType<HeartParticleData> heart;
    @SubscribeEvent
    public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event)
    {
        spiritFlame = registerParticle(event.getRegistry(), new SpiritFlameParticleData.Type(false),"spirit_flame");
        lensMagic = registerParticle(event.getRegistry(), new LensMagicParticleData.Type(false),"lens_magic");
        bonk = registerParticle(event.getRegistry(), new BonkParticleData.Type(false),"bonk");
        skull = registerParticle(event.getRegistry(), new SkullParticleData.Type(false),"skull");
        heart = registerParticle(event.getRegistry(), new HeartParticleData.Type(false),"heart");
    }
    private static <T extends ParticleType<?>> T registerParticle(IForgeRegistry<ParticleType<?>> registry, T type, String name)
    {
        type.setRegistryName(name);
        registry.register(type);
        return type;
    }
    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particles.registerFactory(spiritFlame, SpiritFlameParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(lensMagic, LensMagicParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(bonk, BonkParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(skull, SkullParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(heart, HeartParticle.Factory::new);
    }
}