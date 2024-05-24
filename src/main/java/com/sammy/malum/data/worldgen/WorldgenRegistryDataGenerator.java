package com.sammy.malum.data.worldgen;

import com.sammy.malum.*;
import io.github.fabricators_of_create.porting_lib.data.DatapackBuiltinEntriesProvider;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.data.*;


import java.util.*;
import java.util.concurrent.*;

public class WorldgenRegistryDataGenerator extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap);
            //TODO.add(Registries., BiomeModifications::bootstrap);

    public WorldgenRegistryDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("minecraft", MalumMod.MALUM));
    }
}
