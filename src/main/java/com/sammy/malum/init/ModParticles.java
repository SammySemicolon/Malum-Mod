package com.sammy.malum.init;

import com.sammy.malum.particles.bonk.BonkParticle;
import com.sammy.malum.particles.bonk.BonkParticleData;
import com.sammy.malum.particles.lensmagic.LensMagicParticle;
import com.sammy.malum.particles.lensmagic.LensMagicParticleData;
import com.sammy.malum.particles.skull.SkullParticle;
import com.sammy.malum.particles.skull.SkullParticleData;
import com.sammy.malum.particles.spiritflame.SpiritFlameParticle;
import com.sammy.malum.particles.spiritflame.SpiritFlameParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles
{
    public static ParticleType<SpiritFlameParticleData> spiritFlame;
    public static ParticleType<LensMagicParticleData> lensMagic;
    public static ParticleType<BonkParticleData> bonk;
    public static ParticleType<SkullParticleData> skull;
    @SubscribeEvent
    public void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event)
    {
        event.getRegistry().registerAll(
                new SpiritFlameParticleData.Type(false).setRegistryName("spirit_flame"),
                new LensMagicParticleData.Type(false).setRegistryName("lens_magic"),
                new BonkParticleData.Type(false).setRegistryName("bonk"),
                new SkullParticleData.Type(false).setRegistryName("skull")
        );
    }
    @SubscribeEvent
    public void registerParticleFactory(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particles.registerFactory(spiritFlame, SpiritFlameParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(lensMagic, LensMagicParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(bonk, BonkParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(skull, SkullParticle.Factory::new);
    }
}