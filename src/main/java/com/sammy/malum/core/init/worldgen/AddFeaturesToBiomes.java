package com.sammy.malum.core.init.worldgen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AddFeaturesToBiomes
{
    @SubscribeEvent
    public static void addFeatures(BiomeLoadingEvent event)
    {
        if (event.getCategory().equals(Biome.Category.PLAINS) || event.getCategory().equals(Biome.Category.FOREST))
        {
            event.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION).add(() -> MalumFeatures.SUN_SPOT);
        }
    }
}
