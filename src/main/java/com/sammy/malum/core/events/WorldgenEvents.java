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
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegistry.PlacedFeatures.RUNEWOOD_TREE);
            }
        }
        if (event.getCategory().equals(Biome.BiomeCategory.FOREST)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, FeatureRegistry.PlacedFeatures.RARE_RUNEWOOD_TREE);
            }
        }
        if (event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            if (CommonConfig.GENERATE_BLAZE_QUARTZ.get()) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.BLAZING_QUARTZ_FEATURE);
            }
        }
        if (CommonConfig.GENERATE_SOULSTONE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.SOULSTONE_FEATURE);
        }
        if (CommonConfig.GENERATE_SURFACE_SOULSTONE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.SURFACE_SOULSTONE_FEATURE);
        }
        if (CommonConfig.GENERATE_BRILLIANT_STONE.get()) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, FeatureRegistry.PlacedFeatures.BRILLIANCE_FEATURE);
        }
    }
}