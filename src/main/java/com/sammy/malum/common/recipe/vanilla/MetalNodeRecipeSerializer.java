package com.sammy.malum.common.recipe.vanilla;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
public class MetalNodeRecipeSerializer<T extends AbstractCookingRecipe> extends SimpleCookingSerializer<T> {
    public MetalNodeRecipeSerializer(SimpleCookingSerializer.CookieBaker<T> pFactory, int pDefaultCookingTime) {
        super(pFactory, pDefaultCookingTime);
    }

    @Override
    public T fromJson(ResourceLocation pRecipeId, JsonObject pJson) {
        String s = GsonHelper.getAsString(pJson, "group", "");
        JsonElement jsonelement = GsonHelper.isArrayNode(pJson, "ingredient") ? GsonHelper.getAsJsonArray(pJson, "ingredient") : GsonHelper.getAsJsonObject(pJson, "ingredient");
        Ingredient ingredient = Ingredient.fromJson(jsonelement);
        JsonObject result = pJson.getAsJsonObject("result");
        ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.getAsString(result, "tag"));
        Tag<Item> tag = SerializationTags.getInstance().getTagOrThrow(Registry.ITEM_REGISTRY, resourcelocation, (p_151262_) -> new JsonSyntaxException("Unknown item tag '" + p_151262_ + "'"));
        if (tag.getValues().isEmpty()) {
            return null;
        }
        ItemStack itemstack = new ItemStack(tag.getValues().get(0), GsonHelper.getAsInt(result, "count", 1));
        float f = GsonHelper.getAsFloat(pJson, "experience", 0.0F);
        int i = GsonHelper.getAsInt(pJson, "cookingtime", this.defaultCookingTime);
        return this.factory.create(pRecipeId, s, ingredient, itemstack, f, i);
    }
}