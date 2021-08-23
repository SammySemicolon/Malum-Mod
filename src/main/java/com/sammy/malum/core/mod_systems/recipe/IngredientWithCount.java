package com.sammy.malum.core.mod_systems.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.LingeringPotionItem;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return MalumHelper.copyWithNewCount(ingredient.getMatchingStacks()[0], count);
    }

    public ArrayList<ItemStack> asStackList()
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        Arrays.stream(ingredient.getMatchingStacks()).forEach(stack ->  stacks.add(MalumHelper.copyWithNewCount(stack, count)));
        return stacks;
    }

    public boolean matches(ItemStack stack)
    {
        return ingredient.test(stack) && stack.getCount() >= count;
    }

    public static IngredientWithCount read(PacketBuffer buffer)
    {
        Ingredient ingredient = Ingredient.read(buffer);
        int count = buffer.readInt();
        return new IngredientWithCount(ingredient, count);
    }

    public void write(PacketBuffer buffer)
    {
        ingredient.write(buffer);
        buffer.writeInt(count);
    }

    public static IngredientWithCount deserialize(JsonObject object)
    {
        Ingredient input = object.has("ingredient_list") ? Ingredient.deserialize(object.get("ingredient_list")) : Ingredient.deserialize(object);
        int count = JSONUtils.getInt(object, "count", 1);
        return new IngredientWithCount(input, count);
    }

    public JsonObject serialize()
    {
        JsonObject object = new JsonObject();
        JsonElement serialize = ingredient.serialize();
        if (serialize.isJsonObject())
        {
            object = serialize.getAsJsonObject();
        }
        else
        {
            object.add("ingredient_list", ingredient.serialize());
        }
        object.addProperty("count", count);
        return object;
    }
}
