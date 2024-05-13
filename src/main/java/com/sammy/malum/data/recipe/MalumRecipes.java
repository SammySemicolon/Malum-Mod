package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.crafting.*;
import com.sammy.malum.data.recipe.infusion.*;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.data.recipes.packs.*;

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
        MalumWoodenRecipes.buildRecipes(consumer);

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
