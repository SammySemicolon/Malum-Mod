package com.sammy.malum.core.eventhandlers;

import com.sammy.malum.config.CommonConfig;
import com.sammy.malum.core.registry.worldgen.FeatureRegistry;
import com.sammy.malum.core.systems.spirit.SpiritReloadListener;
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
        event.addListener(new SpiritReloadListener());
    }

    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event) {
        if (event.getCategory().equals(Biome.BiomeCategory.PLAINS) || event.getCategory().equals(Biome.BiomeCategory.EXTREME_HILLS)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.get()) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> FeatureRegistry.COMMON_RUNEWOOD_TREE);
            }
        }
        if (event.getCategory().equals(Biome.BiomeCategory.FOREST)) {
            if (CommonConfig.GENERATE_RUNEWOOD_TREES.get()) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).add(() -> FeatureRegistry.RARE_CONFIGURED_RUNEWOOD_TREE);
            }
        }
        if (event.getCategory().equals(Biome.BiomeCategory.NETHER)) {
            if (CommonConfig.GENERATE_BLAZE_QUARTZ.get()) {
                event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(() -> FeatureRegistry.BLAZE_QUARTZ_ORE);
            }
        }
        if (CommonConfig.GENERATE_SOULSTONE.get()) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(() -> FeatureRegistry.SOULSTONE_ORE);
        }
        if (CommonConfig.GENERATE_BRILLIANT_STONE.get()) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(() -> FeatureRegistry.BRILLIANT_STONE);
        }
        if (CommonConfig.GENERATE_SURFACE_SOULSTONE.get()) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(() -> FeatureRegistry.SOULSTONE_ORE_SURFACE);
        }
    }
}