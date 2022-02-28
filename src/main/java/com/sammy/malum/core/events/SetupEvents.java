package com.sammy.malum.core.events;

import com.sammy.malum.core.handlers.MissingMappingHandler;
import com.sammy.malum.core.setup.client.ParticleRegistry;
import com.sammy.malum.core.setup.client.ScreenParticleRegistry;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEvents {

    @SubscribeEvent
    public static void correctMissingMappings(RegistryEvent.MissingMappings<Item> event){
        MissingMappingHandler.correctMissingMappings(event);
    }

    @SubscribeEvent
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event) {
        ParticleRegistry.registerParticleFactory(event);
        ScreenParticleRegistry.registerParticleFactory(event);
    }
}
