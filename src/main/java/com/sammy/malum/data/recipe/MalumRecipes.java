package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.infusion.*;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;

import java.util.function.*;

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
        //todo vanillaRecipeReplacements.buildRecipes(consumer);
        //MalumVanillaRecipes.buildRecipes(consumer);
        //MalumWoodenRecipes.buildRecipes(consumer);

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
