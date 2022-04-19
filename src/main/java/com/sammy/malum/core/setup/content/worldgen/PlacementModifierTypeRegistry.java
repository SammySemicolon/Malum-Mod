package com.sammy.malum.core.setup.content.worldgen;

import com.mojang.serialization.Codec;
import com.sammy.malum.core.systems.worldgen.ChancePlacementFilter;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlacementModifierTypeRegistry {

    public static PlacementModifierType<ChancePlacementFilter> CHANCE;
    @SubscribeEvent
    public static void registerTypes(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CHANCE = register("malum:chance", ChancePlacementFilter.CODEC);
        });
    }

    private static <P extends PlacementModifier> PlacementModifierType<P> register(String name, Codec<P> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> codec);
    }
}
