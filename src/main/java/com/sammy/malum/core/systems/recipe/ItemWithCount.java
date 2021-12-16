package com.sammy.malum.core.systems.recipe;

import com.google.gson.JsonObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
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
        ItemStack input = ShapedRecipe.itemStackFromJson(inputObject);
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
