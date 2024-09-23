package com.sammy.malum.data.recipe.builder.vanilla;

import com.sammy.malum.MalumMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import team.lodestar.lodestone.recipe.builder.LodestoneCookingRecipeBuilder;

public class MetalNodeCookingRecipeBuilder extends LodestoneCookingRecipeBuilder {

    public MetalNodeCookingRecipeBuilder(SimpleCookingRecipeBuilder parent) {
        super(parent);
    }

    @Override
    public void save(RecipeOutput recipeOutput, String recipeName) {
        this.save(recipeOutput, MalumMod.malumPath("node_processing/" + recipeName));
    }

    @Override
    public void write(RecipeOutput consumer, ResourceLocation id, AbstractCookingRecipe recipe, Advancement.Builder advancement) {
        consumer.accept(id, recipe, advancement.build(id.withPrefix("recipes/metal_node_smelting/")));
    }
}