package com.sammy.malum.core.systems.recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;

public interface IRecipeComponent {
    ItemStack getStack();

    ArrayList<ItemStack> getStacks();

    Item getItem();

    int getCount();

    default boolean isValid() {
        return !getStack().is(Items.BARRIER);
    }

    boolean matches(ItemStack stack);
}