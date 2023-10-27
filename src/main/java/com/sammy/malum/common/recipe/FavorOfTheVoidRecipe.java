package com.sammy.malum.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FavorOfTheVoidRecipe extends ILodestoneRecipe {
    public static final String NAME = "favor_of_the_void";

    private final ResourceLocation id;

    public final Ingredient input;

    public final ItemStack output;

    public FavorOfTheVoidRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.VOID_FAVOR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.VOID_FAVOR.get();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public boolean doesInputMatch(ItemStack input) {
        return this.input.test(input);
    }

    public boolean doesOutputMatch(ItemStack output) {
        return output.getItem().equals(this.output.getItem());
    }

    public static FavorOfTheVoidRecipe getRecipe(Level level, ItemStack stack) {
        return getRecipe(level, c -> c.doesInputMatch(stack));
    }

    public static FavorOfTheVoidRecipe getRecipe(Level level, Predicate<FavorOfTheVoidRecipe> predicate) {
        List<FavorOfTheVoidRecipe> recipes = getRecipes(level);
        for (FavorOfTheVoidRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<FavorOfTheVoidRecipe> getRecipes(Level level) {
        return level().getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.VOID_FAVOR.get());
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<FavorOfTheVoidRecipe> {
        @Override
        public FavorOfTheVoidRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonObject inputObject = json.getAsJsonObject("input");
            Ingredient input = Ingredient.fromJson(inputObject);

            JsonObject outputObject = json.getAsJsonObject("output");
            ItemStack output = CraftingHelper.getItemStack(outputObject, true);
            return new FavorOfTheVoidRecipe(recipeId, input, output);
        }

        @Nullable
        @Override
        public FavorOfTheVoidRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            return new FavorOfTheVoidRecipe(recipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, FavorOfTheVoidRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeItem(recipe.output);
        }
    }
}
