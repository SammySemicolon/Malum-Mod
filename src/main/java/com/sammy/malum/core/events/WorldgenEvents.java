package com.sammy.malum.core.events;

import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.setup.content.worldgen.FeatureRegistry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WorldgenEvents {

    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.BiomeCategory.PLAINS) || event.getCategory().equals(Biome.BiomeCategory.EXTREME_HILLS)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.getConfigValue()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegistry.PlacedFeatures.RUNEWOOD_TREE);
            }
        }
        if (event.getCategory().equals(Biome.BiomeCategory.FOREST)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.getConfigValue()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegistry.PlacedFeatures.RARE_RUNEWOOD_TREE);
            }
        }
        if (event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            if (CommonConfig.GENERATE_BLAZE_QUARTZ.getConfigValue()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.BLAZING_QUARTZ_FEATURE);
            }
        }
        if (CommonConfig.GENERATE_SOULSTONE.getConfigValue()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.SOULSTONE_FEATURE);
        }
        if (CommonConfig.GENERATE_SURFACE_SOULSTONE.getConfigValue()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.SURFACE_SOULSTONE_FEATURE);
        }
        if (CommonConfig.GENERATE_BRILLIANT_STONE.getConfigValue()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.BRILLIANCE_FEATURE);
        }
        if (CommonConfig.GENERATE_NATURAL_QUARTZ.getConfigValue()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.NATURAL_QUARTZ_FEATURE);
        }

        if (CommonConfig.GENERATE_QUARTZ_GEODES.getConfigValue()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureRegistry.PlacedFeatures.QUARTZ_GEODE_FEATURE);
            event.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureRegistry.PlacedFeatures.DEEPSLATE_QUARTZ_GEODE_FEATURE);
        }
        if (CommonConfig.GENERATE_RARE_EARTH.getConfigValue()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, FeatureRegistry.PlacedFeatures.RARE_EARTH_GEODE_FEATURE);
        }
    }
}