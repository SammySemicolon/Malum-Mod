package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.worldgen.ConfiguredFeatureRegistry;
import com.sammy.malum.registry.common.worldgen.PlacedFeatureRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MalumWorldgenProvider extends DatapackBuiltinEntriesProvider {

    public MalumWorldgenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Set.of(MalumMod.MALUM));
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureRegistry::bootstrap)
            .add(Registries.PLACED_FEATURE, PlacedFeatureRegistry::bootstrap);
}
