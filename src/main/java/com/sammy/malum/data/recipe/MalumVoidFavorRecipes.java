package com.sammy.malum.data.recipe;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.data.recipe.builder.SpiritTransmutationRecipeBuilder;
import com.sammy.malum.data.recipe.builder.VoidFavorRecipeBuilder;
import com.sammy.malum.registry.common.item.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Consumer;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumVoidFavorRecipes extends RecipeProvider {
    public MalumVoidFavorRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Malum Block Transmutation Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.RAW_MATERIALS), 2, ItemRegistry.RAW_SOULSTONE.get(), 1)
            .build(consumer);
    }
}
