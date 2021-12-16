package com.sammy.malum.core.systems.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;

public class IngredientWithCount
{
    public final Ingredient ingredient;
    public final int count;

    public IngredientWithCount(Ingredient ingredient, int count)
    {
        this.ingredient = ingredient;
        this.count = count;
    }

    public ItemStack stack()
    {
        return MalumHelper.copyWithNewCount(ingredient.getItems()[0], count);
    }

    public ArrayList<ItemStack> asStackList()
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        Arrays.stream(ingredient.getItems()).forEach(stack ->  stacks.add(MalumHelper.copyWithNewCount(stack, count)));
        return stacks;
    }

    public boolean matches(ItemStack stack)
    {
        return ingredient.test(stack) && stack.getCount() >= count;
    }

    public static IngredientWithCount read(FriendlyByteBuf buffer)
    {
        Ingredient ingredient = Ingredient.fromNetwork(buffer);
        int count = buffer.readByte();
        return new IngredientWithCount(ingredient, count);
    }

    public void write(FriendlyByteBuf buffer)
    {
        ingredient.toNetwork(buffer);
        buffer.writeByte(count);
    }

    public static IngredientWithCount deserialize(JsonObject object)
    {
        Ingredient input = object.has("ingredient_list") ? Ingredient.fromJson(object.get("ingredient_list")) : Ingredient.fromJson(object);
        int count = GsonHelper.getAsInt(object, "count", 1);
        return new IngredientWithCount(input, count);
    }

    public JsonObject serialize()
    {
        JsonObject object = new JsonObject();
        JsonElement serialize = ingredient.toJson();
        if (serialize.isJsonObject())
        {
            object = serialize.getAsJsonObject();
        }
        else
        {
            object.add("ingredient_list", ingredient.toJson());
        }
        object.addProperty("count", count);
        return object;
    }
}
