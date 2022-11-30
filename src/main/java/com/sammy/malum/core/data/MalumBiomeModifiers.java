package com.sammy.malum.core.data;

import com.sammy.malum.core.setup.content.worldgen.FeatureRegistry;
import net.minecraft.core.HolderSet;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import team.lodestar.lodestone.data.provider.Providers;

import static com.sammy.malum.MalumMod.MALUM;

public class MalumBiomeModifiers {

	public static DataProvider getProvider(GatherDataEvent event, DataGenerator generator) {
		return Providers.biomeModifer(MALUM, generator, event.getExistingFileHelper())
				.add("runewood_trees", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.HAS_VILLAGE_PLAINS),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.RUNEWOOD_TREE), GenerationStep.Decoration.VEGETAL_DECORATION))
				.add("rare_runewood_trees", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_FOREST),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.RARE_RUNEWOOD_TREE), GenerationStep.Decoration.VEGETAL_DECORATION))
				.add("blazing_quartz", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_NETHER),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.BLAZING_QUARTZ_FEATURE), GenerationStep.Decoration.UNDERGROUND_ORES))
				.add("soulstone", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.SOULSTONE_FEATURE), GenerationStep.Decoration.UNDERGROUND_ORES))
				.add("surface_soulstone", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.SURFACE_SOULSTONE_FEATURE), GenerationStep.Decoration.UNDERGROUND_ORES))
				.add("brilliant_stone", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.BRILLIANCE_FEATURE), GenerationStep.Decoration.UNDERGROUND_ORES))
				.add("natural_quartz", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.NATURAL_QUARTZ_FEATURE), GenerationStep.Decoration.UNDERGROUND_ORES))
				.add("quartz_geodes", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.QUARTZ_GEODE_FEATURE), GenerationStep.Decoration.LOCAL_MODIFICATIONS))
				.add("deepslte_quartz_geodes", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.DEEPSLATE_QUARTZ_GEODE_FEATURE), GenerationStep.Decoration.LOCAL_MODIFICATIONS))
				.add("generate_rare_earth", (ctx) -> new ForgeBiomeModifiers.AddFeaturesBiomeModifier(ctx.getHolderSet(BiomeTags.IS_OVERWORLD),
						HolderSet.direct(FeatureRegistry.PlacedFeatures.RARE_EARTH_GEODE_FEATURE), GenerationStep.Decoration.LOCAL_MODIFICATIONS))
				.build();
	}
}
