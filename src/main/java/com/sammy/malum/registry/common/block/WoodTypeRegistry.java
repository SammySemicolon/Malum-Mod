package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.client.renderer.*;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class WoodTypeRegistry {

    public static final WoodType RUNEWOOD = WoodType.register(new WoodType("malum:runewood", MalumBlockSetTypes.RUNEWOOD,
            SoundRegistry.RUNEWOOD, SoundRegistry.RUNEWOOD_HANGING_SIGN,
            SoundRegistry.RUNEWOOD_FENCE_GATE_CLOSE.get(), SoundRegistry.RUNEWOOD_FENCE_GATE_OPEN.get()));
    public static final WoodType SOULWOOD = WoodType.register(new WoodType("malum:soulwood", MalumBlockSetTypes.SOULWOOD,
            SoundRegistry.SOULWOOD, SoundRegistry.SOULWOOD_HANGING_SIGN,
            SoundRegistry.SOULWOOD_FENCE_GATE_CLOSE.get(), SoundRegistry.SOULWOOD_FENCE_GATE_OPEN.get()));

    @EventBusSubscriber(modid= MalumMod.MALUM, bus= EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientOnly {
        @SubscribeEvent
        public static void addWoodTypes(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                Sheets.addWoodType(RUNEWOOD);
                Sheets.addWoodType(SOULWOOD);
            });
        }
    }
}