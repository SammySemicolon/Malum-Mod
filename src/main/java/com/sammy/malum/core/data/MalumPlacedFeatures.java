package com.sammy.malum.core.data;

import com.sammy.malum.core.setup.content.worldgen.FeatureRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import team.lodestar.lodestone.data.provider.Providers;

import static com.sammy.malum.MalumMod.MALUM;

public class MalumPlacedFeatures {

	public static DataProvider getProvider(GatherDataEvent event, DataGenerator generator) {
		return Providers.placedFeature(MALUM, generator, event.getExistingFileHelper())
				.add("runewood_tree", (ctx) -> FeatureRegistry.PlacedFeatures.RUNEWOOD_TREE.get())
				.add("rare_runewood_tree", (ctx) -> FeatureRegistry.PlacedFeatures.RARE_RUNEWOOD_TREE.get())
				.add("blazing_quartz_feature", (ctx) -> FeatureRegistry.PlacedFeatures.BLAZING_QUARTZ_FEATURE.get())
				.add("soulstone_feature", (ctx) -> FeatureRegistry.PlacedFeatures.SOULSTONE_FEATURE.get())
				.add("surface_soulstone_feature", (ctx) -> FeatureRegistry.PlacedFeatures.SURFACE_SOULSTONE_FEATURE.get())
				.add("brilliance_feature", (ctx) -> FeatureRegistry.PlacedFeatures.BRILLIANCE_FEATURE.get())
				.add("natural_quartz_feature", (ctx) -> FeatureRegistry.PlacedFeatures.NATURAL_QUARTZ_FEATURE.get())
				.add("quartz_geode_feature", (ctx) -> FeatureRegistry.PlacedFeatures.QUARTZ_GEODE_FEATURE.get())
				.add("deepslate_quartz_geode_feature", (ctx) -> FeatureRegistry.PlacedFeatures.DEEPSLATE_QUARTZ_GEODE_FEATURE.get())
				.add("rare_earth_geode_feature", (ctx) -> FeatureRegistry.PlacedFeatures.RARE_EARTH_GEODE_FEATURE.get())
				.build();
	}
}
