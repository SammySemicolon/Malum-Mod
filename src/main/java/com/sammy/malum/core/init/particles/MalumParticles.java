package com.sammy.malum.core.init.particles;

import com.sammy.malum.client.particles.itemcircle.ItemCircleParticle;
import com.sammy.malum.client.particles.skull.SkullParticle;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticle;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
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
    public static ParticleType<MalumParticleData> skull;
    public static ParticleType<MalumParticleData> spirit_flame;
    public static ParticleType<MalumParticleData> item_circle;
    @SubscribeEvent
    public static void registerParticleTypes(RegistryEvent.Register<ParticleType<?>> event)
    {
        skull = registerParticle(event.getRegistry(), new MalumParticleData.Type(false),"skull");
        spirit_flame = registerParticle(event.getRegistry(), new MalumParticleData.Type(false),"spirit_flame");
        item_circle = registerParticle(event.getRegistry(), new MalumParticleData.Type(false),"item_circle");
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
        Minecraft.getInstance().particles.registerFactory(spirit_flame, SpiritFlameParticle.Factory::new);
        Minecraft.getInstance().particles.registerFactory(item_circle, ItemCircleParticle.Factory::new);
    }
}