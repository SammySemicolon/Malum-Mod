package com.sammy.malum.common.recipe.furnace;

import com.sammy.malum.common.recipe.furnace.crystallarium.MalumSizedChanceResult;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.Optional;

public abstract class MalumAbstractFurnaceRecipe<I extends RecipeInput> implements Recipe<I> {
    private final int processingTime;
    private final NonNullList<MalumSizedChanceResult> results;
    private final Optional<ItemStack> resultFallback;

    public MalumAbstractFurnaceRecipe(int processingTime, NonNullList<MalumSizedChanceResult> results, Optional<ItemStack> resultFallback) {
        this.processingTime = processingTime;
        this.results = results;
        this.resultFallback = resultFallback;
    }

    public NonNullList<MalumSizedChanceResult> getFurnaceResults() {
        return results;
    };

    public int getProcessingTime() {
        return processingTime;
    }

    public Optional<ItemStack> getResultFallback() {
        return resultFallback;
    }
}
