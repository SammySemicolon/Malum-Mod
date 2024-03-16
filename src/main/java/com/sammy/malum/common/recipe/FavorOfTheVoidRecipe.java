package com.sammy.malum.common.recipe;

import com.google.gson.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.crafting.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class FavorOfTheVoidRecipe extends AbstractMalumRecipe {
    public static final String NAME = "favor_of_the_void";

    public final Ingredient input;

    public final ItemStack output;

    public FavorOfTheVoidRecipe(ResourceLocation id, Ingredient input, ItemStack output) {
        super(id, RecipeSerializerRegistry.VOID_FAVOR_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.VOID_FAVOR.get());
        this.input = input;
        this.output = output;
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
        return getRecipe(level, RecipeTypeRegistry.VOID_FAVOR.get(), predicate);
    }

    public static List<FavorOfTheVoidRecipe> getRecipes(Level level) {
        return getRecipes(level, RecipeTypeRegistry.VOID_FAVOR.get());
    }

    public static class Serializer implements RecipeSerializer<FavorOfTheVoidRecipe> {
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
