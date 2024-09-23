package com.sammy.malum.data.recipe.builder.vanilla;

import net.minecraft.advancements.*;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.ShapedRecipe;
import team.lodestar.lodestone.recipe.builder.LodestoneShapedRecipeBuilder;

public class TheDeviceRecipeBuilder extends LodestoneShapedRecipeBuilder {

    public TheDeviceRecipeBuilder(ShapedRecipeBuilder parent) {
        super(parent);
    }

    @Override
    public void write(RecipeOutput consumer, ResourceLocation id, ShapedRecipe recipe, Advancement.Builder builder) {
        consumer.accept(id, recipe, builder.build(id.withPrefix("recipes/the_device/")));
    }
}
