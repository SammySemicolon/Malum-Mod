package com.sammy.malum.data.recipe.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class VoidFavorRecipeBuilder {
    private final IngredientWithCount input;

    private final ItemStack output;

    public VoidFavorRecipeBuilder(Ingredient input, int inputCount, ItemStack output) {
        this.input = new IngredientWithCount(input, inputCount);
        this.output = output;
    }

    public VoidFavorRecipeBuilder(Ingredient input, int inputCount, ItemLike output, int outputCount) {
        this(input, inputCount, new ItemStack(output, outputCount));
    }

    public VoidFavorRecipeBuilder(ItemLike input, int inputCount, ItemStack output) {
        this(Ingredient.of(input), inputCount, output);
    }

    public VoidFavorRecipeBuilder(ItemLike input, int inputCount, ItemLike output, int outputCount) {
        this(Ingredient.of(input), inputCount, new ItemStack(output, outputCount));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("void_favor/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, ForgeRegistries.ITEMS.getKey(output.getItem()).getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new VoidFavorRecipeBuilder.Result(id));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public Result(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject inputObject = input.serialize();
            JsonElement outputObject = Ingredient.of(output).toJson();
            if (output.getCount() != 1) {
                outputObject.getAsJsonObject().addProperty("count", output.getCount());
            }
            json.add("input", inputObject);
            json.add("output", outputObject);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.VOID_FAVOR_RECIPE_SERIALIZER.get();
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
