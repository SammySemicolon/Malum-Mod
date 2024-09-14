package com.sammy.malum.data.worldgen;

import com.sammy.malum.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;
import net.neoforged.neoforge.common.data.*;
import net.neoforged.neoforge.registries.*;

import java.util.*;
import java.util.concurrent.*;

public class WorldgenRegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, BiomeModifications::bootstrap);

    public WorldgenRegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", MalumMod.MALUM));
    }
}
