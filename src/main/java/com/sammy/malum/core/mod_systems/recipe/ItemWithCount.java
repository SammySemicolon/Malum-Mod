package com.sammy.malum.core.mod_systems.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemWithCount
{
    public final Item item;
    public final int count;

    public ItemWithCount(Item item, int count)
    {
        this.item = item;
        this.count = count;
    }

    public ItemWithCount(ItemStack stack)
    {
        this(stack.getItem(), stack.getCount());
    }

    public ItemStack stack()
    {
        return new ItemStack(item, count);
    }

    public boolean matches(ItemStack stack)
    {
        return stack.getItem().equals(item) && stack.getCount() >= count;
    }

    public static ItemWithCount deserialize(JsonObject inputObject)
    {
        ItemStack input = ShapedRecipe.deserializeItem(inputObject);
        return new ItemWithCount(input);
    }
    public JsonObject serialize()
    {
        JsonObject object = new JsonObject();
        object.addProperty("item", ForgeRegistries.ITEMS.getKey(item).toString());
        if (count > 1)
        {
            object.addProperty("count", count);
        }
        return object;
    }
    public static ItemWithCount fromIngredient(IngredientWithCount ingredient)
    {
        return new ItemWithCount(ingredient.stack());
    }
}
