package com.sammy.malum.data;

import com.sammy.malum.data.block.MalumBlockLootTables;
import com.sammy.malum.data.block.MalumBlockStates;
import com.sammy.malum.data.block.MalumBlockTags;
import com.sammy.malum.data.item.MalumItemModels;
import com.sammy.malum.data.item.MalumItemTags;
import com.sammy.malum.data.recipe.MalumRecipes;
import com.sammy.malum.data.worldgen.BiomeModifications;
import com.sammy.malum.data.worldgen.ConfiguredFeatures;
import com.sammy.malum.data.worldgen.PlacedFeatures;
import com.sammy.malum.data.worldgen.WorldgenRegistryDataGenerator;
import com.sammy.malum.registry.common.DamageTypeRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class DataGenerators implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();
        ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();

        var items = pack.addProvider(((output, registriesFuture) -> new MalumItemModels(output, helper)));
        var blocks = pack.addProvider(((output, registriesFuture) -> new MalumBlockTags(output, registriesFuture)));

        pack.addProvider((output, registriesFuture) -> new MalumBlockStates(output, helper, items));
        pack.addProvider((output, registriesFuture) -> new MalumBlockLootTables(output));
        pack.addProvider((output, registriesFuture) -> new MalumItemTags(output, registriesFuture, blocks));
        pack.addProvider((output, registriesFuture) -> new MalumRecipes(output));
        pack.addProvider((output, registriesFuture) -> new MalumBiomeTags(output, registriesFuture, helper));
        pack.addProvider((output, registriesFuture) -> new MalumDamageTypeTags(output, registriesFuture, helper));
        pack.addProvider((output, registriesFuture) -> new WorldgenRegistryDataGenerator(output, registriesFuture));
        pack.addProvider((output, registriesFuture) -> new MalumLang(output));
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatures::bootstrap);
        registryBuilder.add(Registries.PLACED_FEATURE, PlacedFeatures::bootstrap);
        registryBuilder.add(Registries.BIOME, BiomeModifications::bootstrap);
        registryBuilder.add(Registries.DAMAGE_TYPE, DamageTypeRegistry::bootstrap);
    }

}
