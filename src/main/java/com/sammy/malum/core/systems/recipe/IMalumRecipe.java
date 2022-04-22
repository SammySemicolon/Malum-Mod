package com.sammy.malum.core.systems.recipe;


import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

public abstract class IMalumRecipe implements Recipe<Container> {
    @Deprecated
    @Override
    public boolean matches(Container inv, Level level) {
        return false;
    }

    @Deprecated
    @Override
    public ItemStack assemble(Container inv) {
        return ItemStack.EMPTY;
    }

    @Deprecated
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Deprecated
    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }


    @Override
    public boolean isSpecial() {
        return true;
    }

}