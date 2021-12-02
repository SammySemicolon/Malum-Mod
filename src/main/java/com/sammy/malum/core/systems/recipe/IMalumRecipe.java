package com.sammy.malum.core.systems.recipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public abstract class IMalumRecipe implements IRecipe<IInventory>
{
    @Deprecated
    @Override
    public boolean matches(IInventory inv, World worldIn)
    {
        return false;
    }

    @Deprecated
    @Override
    public ItemStack getCraftingResult(IInventory inv)
    {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    public boolean canFit(int width, int height)
    {
        return false;
    }

    @Deprecated
    @Override
    public ItemStack getRecipeOutput()
    {
        return ItemStack.EMPTY;
    }
}
