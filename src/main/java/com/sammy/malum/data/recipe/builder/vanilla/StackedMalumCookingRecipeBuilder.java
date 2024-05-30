package com.sammy.malum.data.recipe.builder.vanilla;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class StackedMalumCookingRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int resultCount;
    private final Ingredient ingredient;
    private final float experience;
    private final int cookingTime;
    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    @Nullable
    private String group;
    private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

    private StackedMalumCookingRecipeBuilder(RecipeCategory pCategory, ItemLike pResult, int resultCount, Ingredient pIngredient, float pExperience, int pCookingTime, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
        this.category = pCategory;
        this.result = pResult.asItem();
        this.resultCount = resultCount;
        this.ingredient = pIngredient;
        this.experience = pExperience;
        this.cookingTime = pCookingTime;
        this.serializer = pSerializer;
    }

    public static StackedMalumCookingRecipeBuilder cookingWithCount(Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, int resultCount, float pExperience, int pCookingTime, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
        return new StackedMalumCookingRecipeBuilder(pCategory, pResult, resultCount, pIngredient, pExperience, pCookingTime, pSerializer);
    }

    public static StackedMalumCookingRecipeBuilder campfireCookingWithCount(Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, int resultCount, float pExperience, int pCookingTime) {
        return cookingWithCount(pIngredient, pCategory, pResult, resultCount, pExperience, pCookingTime, RecipeSerializer.CAMPFIRE_COOKING_RECIPE);
    }

    public static StackedMalumCookingRecipeBuilder blastingWithCount(Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, int resultCount, float pExperience, int pCookingTime) {
        return cookingWithCount(pIngredient, pCategory, pResult, resultCount, pExperience, pCookingTime, RecipeSerializer.BLASTING_RECIPE);
    }

    public static StackedMalumCookingRecipeBuilder smeltingWithCount(Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, int resultCount, float pExperience, int pCookingTime) {
        return cookingWithCount(pIngredient, pCategory, pResult, resultCount, pExperience, pCookingTime, RecipeSerializer.SMELTING_RECIPE);
    }

    public static StackedMalumCookingRecipeBuilder smoking(Ingredient pIngredient, RecipeCategory pCategory, ItemLike pResult, int resultCount, float pExperience, int pCookingTime) {
        return cookingWithCount(pIngredient, pCategory, pResult, resultCount, pExperience, pCookingTime, RecipeSerializer.SMOKING_RECIPE);
    }

    public StackedMalumCookingRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
        this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
        return this;
    }

    public StackedMalumCookingRecipeBuilder group(@Nullable String pGroupName) {
        this.group = pGroupName;
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
        this.ensureValid(pRecipeId);
        this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
        pFinishedRecipeConsumer.accept(new StackedMalumCookingRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredient, this.result, this.resultCount, this.experience, this.cookingTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/" + this.category.getFolderName() + "/" + pRecipeId.getPath()), this.serializer));
    }

    private void ensureValid(ResourceLocation pId) {
        if (this.advancement.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + pId);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final Ingredient ingredient;
        private final Item result;
        private final int resultCount;
        private final float experience;
        private final int cookingTime;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;
        private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

        public Result(ResourceLocation pId, String pGroup, Ingredient pIngredient, Item pResult, int resultCount, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
            this.id = pId;
            this.group = pGroup;
            this.ingredient = pIngredient;
            this.result = pResult;
            this.resultCount = resultCount;
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
            JsonObject object = new JsonObject();
            object.addProperty("item", BuiltInRegistries.ITEM.getKey(this.result).toString());
            object.addProperty("count", this.resultCount);
            pJson.add("result", object);
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