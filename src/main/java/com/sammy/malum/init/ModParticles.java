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
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModParticles
{
    public static ParticleType<SpiritFlameParticleData> spiritFlame;
    public static ParticleType<LensMagicParticleData> lensMagic;
    public static ParticleType<BonkParticleData> bonk;
    public static ParticleType<SkullParticleData> skull;
    @SubscribeEvent
    public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event)
    {
        spiritFlame = registerParticle(event.getRegistry(), new SpiritFlameParticleData.Type(false),"spirit_flame");
        lensMagic = registerParticle(event.getRegistry(), new LensMagicParticleData.Type(false),"lensMagic");
        bonk = registerParticle(event.getRegistry(), new BonkParticleData.Type(false),"bonk");
        skull = registerParticle(event.getRegistry(), new SkullParticleData.Type(false),"skull");
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
    }
}