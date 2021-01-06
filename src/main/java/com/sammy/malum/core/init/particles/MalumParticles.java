package com.sammy.malum.core.init.particles;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.particles.itemcircle.ItemCircleParticleType;
import com.sammy.malum.client.particles.skull.SkullParticleType;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleType;
import com.sammy.malum.client.particles.wisp.WispParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MalumParticles
{
    public static DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MalumMod.MODID);
    
    public static RegistryObject<WispParticleType>
            WISP_PARTICLE = PARTICLES.register("wisp_particle", WispParticleType::new);
    
    public static RegistryObject<ItemCircleParticleType>
            ITEM_CIRCLE = PARTICLES.register("item_circle", ItemCircleParticleType::new);
    
    public static RegistryObject<SkullParticleType>
            SKULL = PARTICLES.register("skull", SkullParticleType::new);
    
    public static RegistryObject<SpiritFlameParticleType>
            SPIRIT_FLAME = PARTICLES.register("spirit_flame", SpiritFlameParticleType::new);
    
    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particles.registerFactory(WISP_PARTICLE.get(), WispParticleType.Factory::new);
        Minecraft.getInstance().particles.registerFactory(ITEM_CIRCLE.get(), ItemCircleParticleType.Factory::new);
        Minecraft.getInstance().particles.registerFactory(SKULL.get(), SkullParticleType.Factory::new);
        Minecraft.getInstance().particles.registerFactory(SPIRIT_FLAME.get(), SpiritFlameParticleType.Factory::new);
    }
}