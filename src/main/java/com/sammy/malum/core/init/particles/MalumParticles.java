package com.sammy.malum.core.init.particles;

import com.sammy.malum.client.particles.skull.SkullParticle;
import com.sammy.malum.core.systems.particles.data.EidolonParticleData;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MalumParticles
{
    public static ParticleType<EidolonParticleData> skull;
    @SubscribeEvent
    public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event)
    {
        skull = registerParticle(event.getRegistry(), new EidolonParticleData.Type(false),"skull");
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
        Minecraft.getInstance().particles.registerFactory(skull, SkullParticle.Factory::new);
    }
}