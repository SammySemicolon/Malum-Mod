package com.sammy.malum.core.events;

import com.sammy.malum.common.components.MalumItemDataCapability;
import com.sammy.malum.common.components.MalumLivingEntityDataCapability;
import com.sammy.malum.common.components.MalumPlayerDataCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class SetupEvents {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        MalumPlayerDataCapability.registerCapabilities(event);
        MalumLivingEntityDataCapability.registerCapabilities(event);
        MalumItemDataCapability.registerCapabilities(event);
    }
}