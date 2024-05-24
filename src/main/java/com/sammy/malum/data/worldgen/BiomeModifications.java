package com.sammy.malum.data.worldgen;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.worldgen.BiomeTagRegistry;
import com.sammy.malum.registry.common.worldgen.PlacedFeatureRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class BiomeModifications {
    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        register(context, "soulstone_ore", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.ORE_SOULSTONE),
                        BiomeTagRegistry.HAS_SOULSTONE, GenerationStep.Decoration.UNDERGROUND_ORES));

        register(context, "brilliant_ore", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.ORE_BRILLIANT),
                        BiomeTagRegistry.HAS_BRILLIANT, GenerationStep.Decoration.UNDERGROUND_ORES));

        register(context, "natural_quartz_ore", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.ORE_NATURAL_QUARTZ),
                        BiomeTagRegistry.HAS_QUARTZ, GenerationStep.Decoration.UNDERGROUND_ORES));

        register(context, "cthonic_gold_ore", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.ORE_CTHONIC_GOLD),
                        BiomeTagRegistry.HAS_CTHONIC, GenerationStep.Decoration.UNDERGROUND_ORES));

        register(context, "blazing_quartz_ore", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.ORE_BLAZING_QUARTZ),
                        BiomeTagRegistry.HAS_BLAZING_QUARTZ, GenerationStep.Decoration.UNDERGROUND_ORES));

        register(context, "runewood_tree", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.RUNEWOOD_TREE),
                        BiomeTagRegistry.HAS_RUNEWOOD, GenerationStep.Decoration.VEGETAL_DECORATION));

        register(context, "rare_runewood_tree", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.RARE_RUNEWOOD_TREE),
                        BiomeTagRegistry.HAS_RARE_RUNEWOOD, GenerationStep.Decoration.VEGETAL_DECORATION));

        register(context, "azure_runewood_tree", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.AZURE_RUNEWOOD_TREE),
                        BiomeTagRegistry.HAS_AZURE_RUNEWOOD, GenerationStep.Decoration.VEGETAL_DECORATION));

        register(context, "rare_azure_runewood_tree", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.RARE_AZURE_RUNEWOOD_TREE),
                        BiomeTagRegistry.HAS_RARE_AZURE_RUNEWOOD, GenerationStep.Decoration.VEGETAL_DECORATION));

        register(context, "quartz_geode", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.QUARTZ_GEODE_FEATURE),
                        BiomeTagRegistry.HAS_QUARTZ, GenerationStep.Decoration.UNDERGROUND_DECORATION));

        register(context, "deepslate_quartz_geode", () ->
                addFeatureModifier(context,
                        getPlacedHolderSet(context, PlacedFeatureRegistry.DEEPSLATE_QUARTZ_GEODE_FEATURE),
                        BiomeTagRegistry.HAS_QUARTZ, GenerationStep.Decoration.UNDERGROUND_DECORATION));


    }

    public static HolderSet<PlacedFeature> getPlacedHolderSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... placedFeatures) {
        List<Holder<PlacedFeature>> holders = new ArrayList<>();
        for (ResourceKey<PlacedFeature> feature : placedFeatures) {
            holders.add(context.lookup(Registries.PLACED_FEATURE).getOrThrow(feature));
        }
        return HolderSet.direct(holders);
    }

    private static ForgeBiomeModifiers.AddFeaturesBiomeModifier addFeatureModifier(BootstapContext<BiomeModifier> context, HolderSet<PlacedFeature> placedSet, TagKey<Biome> biomeTag, GenerationStep.Decoration decoration) {
        return new ForgeBiomeModifiers.AddFeaturesBiomeModifier(context.lookup(Registries.BIOME).getOrThrow(biomeTag), placedSet, decoration);
    }

    private static void register(BootstapContext<BiomeModifier> context, String name, Supplier<? extends BiomeModifier> modifier) {
        context.register(ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, MalumMod.malumPath(name)), modifier.get());
    }
}
