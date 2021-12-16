package com.sammy.malum.common.item.misc;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;

public class MalumFuelItem extends Item
{
    public final int fuel;
    public MalumFuelItem(Properties properties, int fuel)
    {
        super(properties);
        this.fuel = fuel;
    }
    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
        return fuel;
    }
}
