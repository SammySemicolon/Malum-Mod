package com.sammy.malum.data.recipe.builder;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import java.util.function.Consumer;

public class RunicWorkbenchRecipeBuilder {
    private IngredientWithCount primaryInput;
    private IngredientWithCount secondaryInput;

    private final ItemStack output;

    public RunicWorkbenchRecipeBuilder(ItemStack output) {
        this.output = output;
    }

    public RunicWorkbenchRecipeBuilder(ItemLike output, int outputCount) {
        this.output = new ItemStack(output.asItem(), outputCount);
    }

    public RunicWorkbenchRecipeBuilder setPrimaryInput(IngredientWithCount primaryInput) {
        this.primaryInput = primaryInput;
        return this;
    }

    public RunicWorkbenchRecipeBuilder setPrimaryInput(ItemLike primaryInput, int primaryInputCount) {
        return setPrimaryInput(Ingredient.of(primaryInput), primaryInputCount);
    }

    public RunicWorkbenchRecipeBuilder setPrimaryInput(Ingredient primaryInput, int primaryInputCount) {
        return setPrimaryInput(new IngredientWithCount(primaryInput, primaryInputCount));
    }

    public RunicWorkbenchRecipeBuilder setSecondaryInput(IngredientWithCount secondaryInput) {
        this.secondaryInput = secondaryInput;
        return this;
    }

    public RunicWorkbenchRecipeBuilder setSecondaryInput(ItemLike secondaryInput, int secondaryInputCount) {
        return setSecondaryInput(Ingredient.of(secondaryInput), secondaryInputCount);
    }

    public RunicWorkbenchRecipeBuilder setSecondaryInput(Ingredient secondaryInput, int secondaryInputCount) {
        return setSecondaryInput(new IngredientWithCount(secondaryInput, secondaryInputCount));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("runeworking/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, BuiltInRegistries.ITEM.getKey(output.getItem()).getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new RunicWorkbenchRecipeBuilder.Result(id));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public Result(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject primaryInputObject = primaryInput.serialize();
            JsonObject secondaryInputObject = secondaryInput.serialize();
            JsonObject outputObject = new JsonObject();
            outputObject.addProperty("item", BuiltInRegistries.ITEM.getKey(output.getItem()).toString());
            if (output.getCount() != 1) {
                outputObject.getAsJsonObject().addProperty("count", output.getCount());
            }
            if (output.hasTag()) {
                outputObject.getAsJsonObject().addProperty("nbt", output.getTag().toString());
            }
            json.add("primaryInput", primaryInputObject);
            json.add("secondaryInput", secondaryInputObject);
            json.add("output", outputObject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.RUNEWORKING_RECIPE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
