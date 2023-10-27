package com.sammy.malum.core.events;

import com.sammy.malum.common.capability.MalumItemDataCapability;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import com.sammy.malum.core.handlers.MissingMappingHandler;
import com.sammy.malum.registry.client.ParticleRegistry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
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

    /*TODO
    @SubscribeEvent
    public static void correctMissingItemMappings(RegistryEvent.MissingMappings<Item> event) {
        MissingMappingHandler.correctMissingItemMappings(event);
    }

    @SubscribeEvent
    public static void correctMissingBlockMappings(RegistryEvent.MissingMappings<Block> event) {
        MissingMappingHandler.correctMissingBlockMappings(event);
    }

     */



}