package com.sammy.malum.data.recipe;

import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.*;

import java.util.*;
import java.util.function.*;

public class MalumRecipes extends VanillaRecipeProvider {

    MalumVanillaRecipeReplacements vanillaRecipeReplacements;

    public PackOutput pOutput;

    public MalumRecipes(PackOutput pOutput) {
        super(pOutput);
        this.pOutput = pOutput;
        this.vanillaRecipeReplacements = new MalumVanillaRecipeReplacements(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        vanillaRecipeReplacements.buildRecipes(consumer);
        MalumVanillaRecipes.buildRecipes(consumer);
        MalumVoidFavorRecipes.buildRecipes(consumer);
        MalumSpiritInfusionRecipes.buildRecipes(consumer);
        MalumSpiritTransmutationRecipes.buildRecipes(consumer);
        MalumSpiritFocusingRecipes.buildRecipes(consumer);
    }
}
