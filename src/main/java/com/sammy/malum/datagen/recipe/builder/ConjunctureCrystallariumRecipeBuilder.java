package com.sammy.malum.datagen.recipe.builder;

import com.sammy.malum.common.data.component.soulstone.StoredInSoulstoneMetal;
import com.sammy.malum.common.recipe.furnace.crystallarium.ConjunctureCrystallariumRecipe;
import com.sammy.malum.common.recipe.CrystalPropertyModifier;
import com.sammy.malum.common.recipe.furnace.crystallarium.MalumSizedChanceResult;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import team.lodestar.lodestone.modules.toolkit.recipe.LodestoneRecipeBuilder;

import java.util.Optional;

public class ConjunctureCrystallariumRecipeBuilder implements LodestoneRecipeBuilder<ConjunctureCrystallariumRecipe> {
    private final Ingredient input;
    private Optional<CrystalPropertyModifier> crystalToGrow;
    private final NonNullList<MalumSizedChanceResult> additionalResults = NonNullList.create();
    private Optional<StoredInSoulstoneMetal> metalData;
    private final int processingTime;
    private Optional<ItemStack> resultFallback = Optional.empty();

    public ConjunctureCrystallariumRecipeBuilder(Ingredient input, MalumSizedChanceResult result, int processingTime) {
        this.input = input;
        this.additionalResults.add(result);
        this.processingTime = processingTime;
    }

    public ConjunctureCrystallariumRecipeBuilder addAdditionalResult(Item item, int count, float chance) {
        ItemStack stack = new ItemStack(item, count);
        additionalResults.add(new MalumSizedChanceResult(stack, chance));
        return this;
    }

    public ConjunctureCrystallariumRecipeBuilder addAdditionalResult(Item item, float chance) {
        return addAdditionalResult(item, 1, chance);
    }

    public ConjunctureCrystallariumRecipeBuilder addAdditionalResult(Item item) {
        return addAdditionalResult(item, 1, 1.0F);
    }

    public ConjunctureCrystallariumRecipeBuilder addAdditionalResult(Item item, int count) {
        return addAdditionalResult(item, count, 1.0F);
    }

    public ConjunctureCrystallariumRecipeBuilder addResultFallback(Item item, int count) {
        ItemStack stack = new ItemStack(item, count);
        this.resultFallback = Optional.of(stack);
        return this;
    }

    public ConjunctureCrystallariumRecipeBuilder addMetalData(StoredInSoulstoneMetal metalData) {
        this.metalData = Optional.of(metalData);
        return this;
    }

    public ConjunctureCrystallariumRecipeBuilder addCrystalToGrow(CrystalPropertyModifier crystalToGrow) {
        this.crystalToGrow = Optional.of(crystalToGrow);
        return this;
    }

    public ConjunctureCrystallariumRecipeBuilder growDefaultCrystal() {
        return addCrystalToGrow(CrystalPropertyModifier.DEFAULT);
    }

    public ConjunctureCrystallariumRecipeBuilder addResultFallback(Item item) {
        return addResultFallback(item, 1);
    }

    public void save(RecipeOutput recipeOutput) {
        this.save(recipeOutput, this.additionalResults.getFirst().result().getItem());
    }

    @Override
    public ConjunctureCrystallariumRecipe buildRecipe(ResourceLocation id) {
        return new ConjunctureCrystallariumRecipe(input, crystalToGrow, additionalResults, metalData, processingTime, resultFallback);
    }

    @Override
    public String getRecipeSubfolder() {
        return "conjuncture_crystallarium";
    }
}
