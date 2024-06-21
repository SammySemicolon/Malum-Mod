package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.crafting.MalumRockSetRecipes;
import com.sammy.malum.data.recipe.crafting.MalumWoodSetRecipes;
import com.sammy.malum.data.recipe.infusion.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

public class MalumRecipes extends FabricRecipeProvider {

    MalumVanillaRecipeReplacements vanillaRecipeReplacements;

    public PackOutput pOutput;

    public MalumRecipes(FabricDataOutput pOutput) {
        super(pOutput);
        this.pOutput = pOutput;
        this.vanillaRecipeReplacements = new MalumVanillaRecipeReplacements(pOutput);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> consumer) {
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
