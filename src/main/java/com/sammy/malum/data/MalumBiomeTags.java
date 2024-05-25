package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.worldgen.*;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.tags.*;
import net.minecraft.world.level.biome.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

public class MalumBiomeTags extends BiomeTagsProvider {

    public MalumBiomeTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //super.addTags(pProvider);
        tag(BiomeTagRegistry.HAS_SOULSTONE).addTag(BiomeTags.IS_OVERWORLD);
        tag(BiomeTagRegistry.HAS_BRILLIANT).addTag(BiomeTags.IS_OVERWORLD);
        tag(BiomeTagRegistry.HAS_BLAZING_QUARTZ).addTag(BiomeTags.IS_NETHER);
        tag(BiomeTagRegistry.HAS_QUARTZ).addTag(BiomeTags.IS_OVERWORLD);
        tag(BiomeTagRegistry.HAS_CTHONIC).addTag(BiomeTags.IS_OVERWORLD);

        tag(BiomeTagRegistry.HAS_RUNEWOOD).addTag(Tags.Biomes.IS_PLAINS).addTag(Tags.Biomes.IS_MOUNTAIN).addTag(BiomeTags.IS_HILL);//TODO.remove(Tags.Biomes.IS_SNOWY);
        tag(BiomeTagRegistry.HAS_RARE_RUNEWOOD).addTag(BiomeTags.IS_FOREST);//TODO.remove(Tags.Biomes.IS_SNOWY);

        tag(BiomeTagRegistry.HAS_AZURE_RUNEWOOD).add(Biomes.SNOWY_PLAINS).add(Biomes.SNOWY_TAIGA).add(Biomes.FROZEN_RIVER).add(Biomes.SNOWY_BEACH);
        tag(BiomeTagRegistry.HAS_RARE_AZURE_RUNEWOOD).add(Biomes.FROZEN_PEAKS).add(Biomes.JAGGED_PEAKS).add(Biomes.SNOWY_SLOPES).add(Biomes.GROVE);
    }
}
