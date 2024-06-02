package com.sammy.malum.data.recipe.builder.vanilla;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import java.util.function.Consumer;

public class MetalNodeCookingRecipeBuilder {
    private final IngredientWithCount result;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

    private MetalNodeCookingRecipeBuilder(IngredientWithCount result, Ingredient pIngredient, float pExperience, int pCookingTime, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
        this.result = result;
        this.ingredient = pIngredient;
        this.experience = pExperience;
        this.cookingTime = pCookingTime;
        this.serializer = pSerializer;
    }

    public static MetalNodeCookingRecipeBuilder cookingWithCount(IngredientWithCount result, Ingredient pIngredient, float pExperience, int pCookingTime, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
        return new MetalNodeCookingRecipeBuilder(result, pIngredient, pExperience, pCookingTime, pSerializer);
    }

    public static MetalNodeCookingRecipeBuilder blastingWithTag(IngredientWithCount result, Ingredient pIngredient, float pExperience, int pCookingTime) {
        return cookingWithCount(result, pIngredient, pExperience, pCookingTime, RecipeSerializerRegistry.METAL_NODE_BLASTING_SERIALIZER.get());
    }

    public static MetalNodeCookingRecipeBuilder smeltingWithTag(IngredientWithCount result, Ingredient pIngredient, float pExperience, int pCookingTime) {
        return cookingWithCount(result, pIngredient, pExperience, pCookingTime, RecipeSerializerRegistry.METAL_NODE_SMELTING_SERIALIZER.get());
    }

    public MetalNodeCookingRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }


    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("node_processing/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, BuiltInRegistries.ITEM.getKey(result.getStack().getItem()).getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new MetalNodeCookingRecipeBuilder.Result(id, this.group == null ? "" : this.group, this.ingredient, this.result, this.experience, this.cookingTime, this.advancement, new ResourceLocation(id.getNamespace(), "recipes/metal_node_smelting/" + id.getPath()), this.serializer));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final IngredientWithCount result;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

        public Result(ResourceLocation pId, String pGroup, Ingredient pIngredient, IngredientWithCount result, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
            this.id = pId;
            this.group = pGroup;
            this.ingredient = pIngredient;
            this.result = result;
            this.experience = pExperience;
            this.cookingTime = pCookingTime;
            this.advancement = pAdvancement;
            this.advancementId = pAdvancementId;
            this.serializer = pSerializer;
        }

        public void serializeRecipeData(JsonObject pJson) {
            if (!this.group.isEmpty()) {
                pJson.addProperty("group", this.group);
            }
            pJson.add("ingredient", this.ingredient.toJson());
            pJson.add("result", result.serialize());
            pJson.addProperty("experience", this.experience);
            pJson.addProperty("cookingtime", this.cookingTime);
        }

        public RecipeSerializer<?> getType() {
            return this.serializer;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}