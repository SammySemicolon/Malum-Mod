package com.sammy.malum.data.recipe;

import com.sammy.malum.data.recipe.builder.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.*;
import net.minecraftforge.common.*;

import java.util.function.*;

public class MalumVoidFavorRecipes extends RecipeProvider {
    public MalumVoidFavorRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Malum Void Favor Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        new VoidFavorRecipeBuilder(Ingredient.of(Tags.Items.RAW_MATERIALS), 1, ItemRegistry.RAW_SOULSTONE.get(), 1)
                .build(consumer);

        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.HEX_ASH.get()), 4, ItemRegistry.VOID_SALTS.get(), 1)
                .build(consumer);
        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.HEX_ASH.get()), 4, ItemRegistry.VOID_SALTS.get(), 1)
                .build(consumer);


        new VoidFavorRecipeBuilder(Ingredient.of(ItemRegistry.THE_DEVICE.get()), 1, ItemRegistry.THE_VESSEL.get(), 1)
                .build(consumer);
    }
}
