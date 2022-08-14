package com.sammy.malum.core.data.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SoulwoodSpiritTransmutationRecipeBuilder {
    private final List<Ingredient> inputs;
    private final List<Ingredient> outputs;

    public SoulwoodSpiritTransmutationRecipeBuilder(List<Ingredient> inputs, List<Ingredient> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("spirit_transmutation/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, "soulwood_transmutation");
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new SoulwoodSpiritTransmutationRecipeBuilder.Result(id, inputs, outputs));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;

        private final List<Ingredient> inputs;
        private final List<Ingredient> outputs;

        public Result(ResourceLocation id, List<Ingredient> inputs, List<Ingredient> outputs) {
            this.id = id;
            this.inputs = inputs;
            this.outputs = outputs;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray array = new JsonArray();
            for (int i = 0; i < inputs.size(); i++) {
                JsonObject object = new JsonObject();
                object.add("input", inputs.get(i).toJson());
                object.add("output", outputs.get(i).toJson());
                array.add(object);
            }

            json.add("recipes", array);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.SOULWOOD_SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER.get();
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