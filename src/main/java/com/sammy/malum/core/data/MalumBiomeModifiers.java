package com.sammy.malum.core.data;

import com.google.gson.JsonElement;
import com.sammy.malum.registry.common.worldgen.FeatureRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.JsonCodecProvider;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.holdersets.AnyHolderSet;

import java.util.HashMap;
import java.util.Map;

import static com.sammy.malum.MalumMod.MALUM;

public class MalumBiomeModifiers {

	public static JsonCodecProvider<BiomeModifier> dataGenBiomeModifiers(DataGenerator generator, ExistingFileHelper helper, RegistryOps<JsonElement> regOps) {

		return JsonCodecProvider.forDatapackRegistry(generator, helper, MALUM, regOps, ForgeRegistries.Keys.BIOME_MODIFIERS, generateBiomeModifiers(regOps.registryAccess));
	}

	private static Map<ResourceLocation, BiomeModifier> generateBiomeModifiers(RegistryAccess registryAccess) {
		// Set up the requirements for the data generation
		Map<ResourceLocation, BiomeModifier> biomeMods = new HashMap<>();
		Registry<Biome> biomes = registryAccess.registryOrThrow(Registry.BIOME_REGISTRY);

		// Misc generation
		addToBiomeGen(biomeMods, "runewood_trees", new HolderSet.Named<>(biomes, BiomeTags.HAS_VILLAGE_PLAINS), HolderSet.direct(FeatureRegistry.PlacedFeatures.RUNEWOOD_TREE.getHolder().get()), GenerationStep.Decoration.VEGETAL_DECORATION);
		addToBiomeGen(biomeMods, "rare_runewood_trees", new HolderSet.Named<>(biomes, BiomeTags.IS_FOREST), HolderSet.direct(FeatureRegistry.PlacedFeatures.RARE_RUNEWOOD_TREE.getHolder().get()), GenerationStep.Decoration.VEGETAL_DECORATION);
		addToBiomeGen(biomeMods, "blazing_quartz", new HolderSet.Named<>(biomes, BiomeTags.IS_NETHER), HolderSet.direct(FeatureRegistry.PlacedFeatures.BLAZING_QUARTZ_FEATURE.getHolder().get()), GenerationStep.Decoration.UNDERGROUND_ORES);
		// Overworld Ores
		HolderSet<Biome> overworld = new HolderSet.Named<>(biomes, BiomeTags.IS_OVERWORLD);
		addToBiomeGen(biomeMods, "soulstone", overworld, HolderSet.direct(FeatureRegistry.PlacedFeatures.SOULSTONE_FEATURE.getHolder().get()), GenerationStep.Decoration.UNDERGROUND_ORES);
		addToBiomeGen(biomeMods, "surface_soulstone", overworld, HolderSet.direct(FeatureRegistry.PlacedFeatures.SURFACE_SOULSTONE_FEATURE.getHolder().get()), GenerationStep.Decoration.UNDERGROUND_ORES);
		addToBiomeGen(biomeMods, "brilliant_stone", overworld, HolderSet.direct(FeatureRegistry.PlacedFeatures.BRILLIANCE_FEATURE.getHolder().get()), GenerationStep.Decoration.UNDERGROUND_ORES);
		addToBiomeGen(biomeMods, "natural_quartz", overworld, HolderSet.direct(FeatureRegistry.PlacedFeatures.NATURAL_QUARTZ_FEATURE.getHolder().get()), GenerationStep.Decoration.UNDERGROUND_ORES);
		// Local modifications
		addToBiomeGen(biomeMods, "quartz_geodes", overworld, HolderSet.direct(FeatureRegistry.PlacedFeatures.QUARTZ_GEODE_FEATURE.getHolder().get()), GenerationStep.Decoration.LOCAL_MODIFICATIONS);
		addToBiomeGen(biomeMods, "deepslate_quartz_geodes", overworld, HolderSet.direct(FeatureRegistry.PlacedFeatures.DEEPSLATE_QUARTZ_GEODE_FEATURE.getHolder().get()), GenerationStep.Decoration.LOCAL_MODIFICATIONS);
		addToBiomeGen(biomeMods, "rare_earth", overworld, HolderSet.direct(FeatureRegistry.PlacedFeatures.RARE_EARTH_GEODE_FEATURE.getHolder().get()), GenerationStep.Decoration.LOCAL_MODIFICATIONS);
		return biomeMods;
	}

	private static void addToBiomeGen(Map<ResourceLocation, BiomeModifier> map, String name, HolderSet<Biome> biomes, HolderSet<PlacedFeature> feature, GenerationStep.Decoration deco) {
		map.put(new ResourceLocation(MALUM, name + "_biome_spawns"), addFeatureToBiomes(name, biomes, feature, deco));
	}

	private static ForgeBiomeModifiers.AddFeaturesBiomeModifier addFeatureToBiomes(String name, HolderSet<Biome> biomes, HolderSet<PlacedFeature> feature, GenerationStep.Decoration decStep) {
		return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(biomes, feature, decStep);
	}
}
