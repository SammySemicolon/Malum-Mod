package com.sammy.malum.core.data.builder;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import com.sammy.ortus.systems.recipe.IngredientWithCount;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class SpiritTransmutationRecipeBuilder {
    private final IngredientWithCount input;
    private final IngredientWithCount output;

    public SpiritTransmutationRecipeBuilder(IngredientWithCount input, IngredientWithCount output) {
        this.input = input;
        this.output = output;
    }
    public SpiritTransmutationRecipeBuilder(Item input, Item output) {
        this(new IngredientWithCount(Ingredient.of(input), 1), new IngredientWithCount(Ingredient.of(output), 1));
    }
    public SpiritTransmutationRecipeBuilder(Block input, Block output) {
        this(new IngredientWithCount(Ingredient.of(input), 1), new IngredientWithCount(Ingredient.of(output), 1));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("spirit_transmutation/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, output.getStack().getItem().getRegistryName().getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new SpiritTransmutationRecipeBuilder.Result(id, input, output));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;

        private final IngredientWithCount input;
        private final IngredientWithCount output;

        public Result(ResourceLocation id, IngredientWithCount input, IngredientWithCount output) {
            this.id = id;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            json.add("input", input.serialize());
            json.add("output", output.serialize());
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER.get();
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