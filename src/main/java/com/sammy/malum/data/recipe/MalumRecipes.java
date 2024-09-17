package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.crafting.*;
import com.sammy.malum.data.recipe.infusion.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.*;

public class MalumRecipes extends VanillaRecipeProvider {

    MalumVanillaRecipeReplacements vanillaRecipeReplacements;

    public PackOutput pOutput;

    public MalumRecipes(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(pOutput, registries);
        this.pOutput = pOutput;
        this.vanillaRecipeReplacements = new MalumVanillaRecipeReplacements(pOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        vanillaRecipeReplacements.buildRecipes(consumer);
        MalumVanillaRecipes.buildRecipes(consumer);
        MalumWoodSetRecipes.buildRecipes(consumer);
        MalumRockSetRecipes.buildRecipes(consumer);

        ArtificeSpiritInfusionRecipes.buildRecipes(consumer);
        CurioSpiritInfusionRecipes.buildRecipes(consumer);
        GearSpiritInfusionRecipes.buildRecipes(consumer);
        MaterialSpiritInfusionRecipes.buildRecipes(consumer);
        TotemicSpiritInfusionRecipes.buildRecipes(consumer);
        MiscSpiritInfusionRecipes.buildRecipes(consumer);

        MalumRuneworkingRecipes.buildRecipes(consumer);
        MalumSpiritFocusingRecipes.buildRecipes(consumer);
        MalumSpiritRepairRecipes.buildRecipes(consumer);
        MalumSpiritTransmutationRecipes.buildRecipes(consumer);
        MalumVoidFavorRecipes.buildRecipes(consumer);
    }
}
