package com.sammy.malum.datagen;

import com.sammy.malum.datagen.block.MalumBlockLootDatagen;
import com.sammy.malum.datagen.entity.cultist.MalumCultistLootDatagen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MalumLootTableDatagen extends LootTableProvider {

    public MalumLootTableDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                Set.of(),
                List.of(
                        new SubProviderEntry(MalumCultistLootDatagen::new, LootContextParamSets.ENTITY),
                        new SubProviderEntry(MalumBlockLootDatagen::new, LootContextParamSets.BLOCK)
                ),
                registries
        );
    }
}