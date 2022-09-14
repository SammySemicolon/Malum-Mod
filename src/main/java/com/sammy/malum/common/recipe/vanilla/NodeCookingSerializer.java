package com.sammy.malum.common.recipe.vanilla;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

public class NodeCookingSerializer<T extends AbstractCookingRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
   public final int defaultCookingTime;
   public final NodeBaker<T> factory;

   public NodeCookingSerializer(NodeBaker<T> pFactory, int pDefaultCookingTime) {
      this.defaultCookingTime = pDefaultCookingTime;
      this.factory = pFactory;
   }

   public T fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
      String s = GsonHelper.getAsString(pJson, "group", "");
      JsonElement jsonelement = (GsonHelper.isArrayNode(pJson, "ingredient") ? GsonHelper.getAsJsonArray(pJson, "ingredient") : GsonHelper.getAsJsonObject(pJson, "ingredient"));
      Ingredient ingredient = Ingredient.fromJson(jsonelement);
      IngredientWithCount result = IngredientWithCount.deserialize(pJson.getAsJsonObject("result"));

      float f = GsonHelper.getAsFloat(pJson, "experience", 0.0F);
      int i = GsonHelper.getAsInt(pJson, "cookingtime", this.defaultCookingTime);
      return this.factory.create(pRecipeId, s, ingredient, result, f, i);
   }

   public T fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
      String s = pBuffer.readUtf();
      Ingredient ingredient = Ingredient.fromNetwork(pBuffer);
      IngredientWithCount result = IngredientWithCount.read(pBuffer);
      float f = pBuffer.readFloat();
      int i = pBuffer.readVarInt();
      return this.factory.create(pRecipeId, s, ingredient, result, f, i);
   }

   public void toNetwork(FriendlyByteBuf pBuffer, T pRecipe) {
      pBuffer.writeUtf(pRecipe.getGroup());
      pRecipe.getIngredients().get(0).toNetwork(pBuffer);
      ((INodeSmeltingRecipe)pRecipe).getOutput().write(pBuffer);
      pBuffer.writeFloat(pRecipe.getExperience());
      pBuffer.writeVarInt(pRecipe.getCookingTime());
   }

   public interface NodeBaker<T extends AbstractCookingRecipe> {
      T create(ResourceLocation pId, String pGroup, Ingredient pIngredient, IngredientWithCount pResult, float pExperience, int pCookingTime);
   }
}