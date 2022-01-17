package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.registry.worldgen.FeatureRegistry;
import com.sammy.malum.core.systems.spirit.SpiritDataReloadListener;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldgenEvents {
    @SubscribeEvent
    public static void registerListeners(AddReloadListenerEvent event) {
        event.addListener(new SpiritDataReloadListener());
    }

    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.BiomeCategory.PLAINS) || event.getCategory().equals(Biome.BiomeCategory.EXTREME_HILLS)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegistry.RUNEWOOD_TREE_FEATURE);
            }
        }
        if (event.getCategory().equals(Biome.BiomeCategory.FOREST)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegistry.RARE_RUNEWOOD_TREE_FEATURE);
            }
        }
        if (event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            if (CommonConfig.GENERATE_BLAZE_QUARTZ.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.BLAZING_QUARTZ_FEATURE);
            }
        }
        if (CommonConfig.GENERATE_SOULSTONE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.SOULSTONE_FEATURE);
        }
        if (CommonConfig.GENERATE_SURFACE_SOULSTONE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.SURFACE_SOULSTONE_FEATURE);
        }
        if (CommonConfig.GENERATE_BRILLIANT_STONE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.BRILLIANT_STONE_FEATURE);
        }
    }
}