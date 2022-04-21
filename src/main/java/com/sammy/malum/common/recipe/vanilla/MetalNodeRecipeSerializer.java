package com.sammy.malum.common.recipe.vanilla;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

import java.util.Optional;

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
        TagKey<Item> tagKey = TagKey.create(Registry.ITEM_REGISTRY, resourcelocation);
        Optional<ItemStack> stack = Registry.ITEM.getTag(tagKey).map(e -> new ItemStack(e.get(0).value(), GsonHelper.getAsInt(result, "count", 1)));
        if (stack.isEmpty()) {
            return null;
        }
        float f = GsonHelper.getAsFloat(pJson, "experience", 0.0F);
        int i = GsonHelper.getAsInt(pJson, "cookingtime", this.defaultCookingTime);
        return this.factory.create(pRecipeId, s, ingredient, stack.get(), f, i);
    }
}