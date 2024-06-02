package com.sammy.malum.common.recipe;

import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSpiritListMalumRecipe extends AbstractMalumRecipe {

    public final List<SpiritWithCount> spirits;

    protected AbstractSpiritListMalumRecipe(ResourceLocation id, RecipeSerializer<?> recipeSerializer, RecipeType<?> recipeType, List<SpiritWithCount> spirits) {
        super(id, recipeSerializer, recipeType);
        this.spirits = spirits;
    }


    public List<ItemStack> getSortedSpirits(List<ItemStack> stacks) {
        List<ItemStack> sortedStacks = new ArrayList<>();
        for (SpiritWithCount item : spirits) {
            for (ItemStack stack : stacks) {
                if (item.matches(stack)) {
                    sortedStacks.add(stack);
                    break;
                }
            }
        }
        return sortedStacks;
    }

    public boolean doSpiritsMatch(List<ItemStack> spirits) {
        if (this.spirits.size() == 0) {
            return true;
        }
        if (this.spirits.size() != spirits.size()) {
            return false;
        }
        List<ItemStack> sortedStacks = getSortedSpirits(spirits);
        if (sortedStacks.size() < this.spirits.size()) {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++) {
            SpiritWithCount item = this.spirits.get(i);
            ItemStack stack = sortedStacks.get(i);
            if (!item.matches(stack)) {
                return false;
            }
        }
        return true;
    }
}
