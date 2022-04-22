package com.sammy.malum.core.systems.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sammy.malum.core.helper.ItemHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;

public class IngredientWithCount implements IRecipeComponent {
    public final Ingredient ingredient;
    public final int count;

    public IngredientWithCount(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(getItem(), getCount());
    }

    @Override
    public ArrayList<ItemStack> getStacks() {
        return ItemHelper.copyWithNewCount(Arrays.asList(ingredient.getItems()), getCount());
    }

    @Override
    public Item getItem() {
        return ingredient.getItems()[0].getItem();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return ingredient.test(stack) && stack.getCount() >= getCount();
    }

    public static IngredientWithCount read(FriendlyByteBuf buffer) {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        int count = buffer.readByte();
        return new IngredientWithCount(ingredient, count);
    }

    public void write(FriendlyByteBuf buffer) {
        ingredient.toNetwork(buffer);
        buffer.writeByte(count);
    }

    public static IngredientWithCount deserialize(JsonObject object) {
        Ingredient input = object.has("ingredient_list") ? Ingredient.fromJson(object.get("ingredient_list")) : Ingredient.fromJson(object);
        int count = GsonHelper.getAsInt(object, "count", 1);
        return new IngredientWithCount(input, count);
    }

    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        JsonElement serialize = ingredient.toJson();
        if (serialize.isJsonObject()) {
            object = serialize.getAsJsonObject();
        } else {
            object.add("ingredient_list", ingredient.toJson());
        }
        object.addProperty("count", count);
        return object;
    }
}