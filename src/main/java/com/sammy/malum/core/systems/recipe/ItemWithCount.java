package com.sammy.malum.core.systems.recipe;

import com.google.gson.JsonObject;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ItemWithCount implements IRecipeComponent {
    public final Item item;
    public final int count;

    public ItemWithCount(Item item, int count) {
        this.item = item;
        this.count = count;
    }

    public ItemWithCount(ItemStack stack) {
        this(stack.getItem(), stack.getCount());
    }

    public static ItemWithCount deserialize(JsonObject inputObject) {
        ItemStack input = ShapedRecipe.itemStackFromJson(inputObject);
        return new ItemWithCount(input);
    }

    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        object.addProperty("item", ForgeRegistries.ITEMS.getKey(item).toString());
        if (getCount() > 1) {
            object.addProperty("count", getCount());
        }
        return object;
    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(getItem(), getCount());
    }

    @Override
    public ArrayList<ItemStack> getStacks() {
        return new ArrayList<>(List.of(getStack()));
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean matches(ItemStack stack) {
        return stack.getItem().equals(getItem()) && stack.getCount() >= getCount();
    }
}