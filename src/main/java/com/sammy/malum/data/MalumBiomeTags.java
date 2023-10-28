package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.worldgen.BiomeTagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class MalumBiomeTags extends BiomeTagsProvider {

    public MalumBiomeTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        //super.addTags(pProvider);
        tag(BiomeTagRegistry.HAS_SOULSTONE).addTag(BiomeTags.IS_OVERWORLD);
        tag(BiomeTagRegistry.HAS_BRILLIANT).addTag(BiomeTags.IS_OVERWORLD);
        tag(BiomeTagRegistry.HAS_BLAZING_QUARTZ).addTag(BiomeTags.IS_NETHER);
        tag(BiomeTagRegistry.HAS_QUARTZ).addTag(BiomeTags.IS_OVERWORLD);
        tag(BiomeTagRegistry.HAS_CTHONIC).addTag(BiomeTags.IS_OVERWORLD);
        tag(BiomeTagRegistry.HAS_RUNEWOOD).addTag(Tags.Biomes.IS_PLAINS).addTag(Tags.Biomes.IS_MOUNTAIN).addTag(BiomeTags.IS_HILL);//Todo change to Forest and add rare for plains
        tag(BiomeTagRegistry.HAS_RARE_RUNEWOOD).addTag(BiomeTags.IS_FOREST);
    }
}
