package com.sammy.malum.core.data.builder.vanilla;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sammy.malum.core.data.builder.SpiritFocusingRecipeBuilder;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;

import javax.annotation.Nullable;
import java.util.function.Consumer;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.EARTHEN_SPIRIT;
import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.INFERNAL_SPIRIT;

public class MetalNodeCookingRecipeBuilder implements RecipeBuilder {
   private final Tag<Item> result;
   private final int resultCount;
   private final Ingredient ingredient;
   private final float experience;
   private final int cookingTime;
   private final Advancement.Builder advancement = Advancement.Builder.advancement();
   @Nullable
   private String group;
   private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

   private MetalNodeCookingRecipeBuilder(Tag<Item> result, int resultCount, Ingredient pIngredient, float pExperience, int pCookingTime, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
      this.result = result;
      this.resultCount = resultCount;
      this.ingredient = pIngredient;
      this.experience = pExperience;
      this.cookingTime = pCookingTime;
      this.serializer = pSerializer;
   }

   public static MetalNodeCookingRecipeBuilder cookingWithCount(Ingredient pIngredient, Tag<Item> pResult, int resultCount, float pExperience, int pCookingTime, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
      return new MetalNodeCookingRecipeBuilder(pResult, resultCount, pIngredient, pExperience, pCookingTime, pSerializer);
   }

   public static MetalNodeCookingRecipeBuilder blastingWithTag(Ingredient pIngredient, Tag<Item> pResult, int resultCount, float pExperience, int pCookingTime) {
      return cookingWithCount(pIngredient, pResult, resultCount, pExperience, pCookingTime, RecipeSerializerRegistry.METAL_NODE_BLASTING_SERIALIZER.get());
   }

   public static MetalNodeCookingRecipeBuilder smeltingWithTag(Ingredient pIngredient, Tag<Item> pResult, int resultCount, float pExperience, int pCookingTime) {
      return cookingWithCount(pIngredient, pResult, resultCount, pExperience, pCookingTime, RecipeSerializerRegistry.METAL_NODE_SMELTING_SERIALIZER.get());
   }

   public MetalNodeCookingRecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
      this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
      return this;
   }

   public MetalNodeCookingRecipeBuilder group(@Nullable String pGroupName) {
      this.group = pGroupName;
      return this;
   }

   public Item getResult() {
      return result.getValues().get(0);
   }


   public void save(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ResourceLocation pRecipeId) {
      this.ensureValid(pRecipeId);
      this.advancement.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pRecipeId)).rewards(AdvancementRewards.Builder.recipe(pRecipeId)).requirements(RequirementsStrategy.OR);
      pFinishedRecipeConsumer.accept(new MetalNodeCookingRecipeBuilder.Result(pRecipeId, this.group == null ? "" : this.group, this.ingredient, this.result, this.resultCount, this.experience, this.cookingTime, this.advancement, new ResourceLocation(pRecipeId.getNamespace(), "recipes/metal_node_smelting/" + pRecipeId.getPath()), this.serializer));
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
      private final Tag<Item> result;
      private final int resultCount;
      private final float experience;
      private final int cookingTime;
      private final Advancement.Builder advancement;
      private final ResourceLocation advancementId;
      private final RecipeSerializer<? extends AbstractCookingRecipe> serializer;

      public Result(ResourceLocation pId, String pGroup, Ingredient pIngredient, Tag<Item> pResult, int resultCount, float pExperience, int pCookingTime, Advancement.Builder pAdvancement, ResourceLocation pAdvancementId, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer) {
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
         ResourceLocation tag = SerializationTags.getInstance().getIdOrThrow(Registry.ITEM_REGISTRY, result, () -> new JsonSyntaxException("Unknown item tag '" + result + "' in recipe " + id));

         object.addProperty("tag", tag.toString());
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