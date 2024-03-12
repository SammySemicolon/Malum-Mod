package com.sammy.malum.common.recipe;

import com.google.gson.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraftforge.common.crafting.*;
import team.lodestar.lodestone.systems.recipe.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

public class RunicWorkbenchRecipe extends AbstractMalumRecipe {
    public static final String NAME = "runeworking";

    public final IngredientWithCount primaryInput;
    public final IngredientWithCount secondaryInput;
    public final ItemStack output;

    public RunicWorkbenchRecipe(ResourceLocation id, IngredientWithCount primaryInput, IngredientWithCount secondaryInput, ItemStack output) {
        super(id, RecipeSerializerRegistry.RUNEWORKING_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.RUNEWORKING.get());
        this.primaryInput = primaryInput;
        this.secondaryInput = secondaryInput;
        this.output = output;
    }

    public boolean doesPrimaryInputMatch(ItemStack input) {
        return this.primaryInput.matches(input);
    }

    public boolean doesSecondaryInputMatch(ItemStack input) {
        return this.secondaryInput.matches(input);
    }

    public boolean doesOutputMatch(ItemStack output) {
        return output.getItem().equals(this.output.getItem());
    }

    public static RunicWorkbenchRecipe getRecipe(Level level, ItemStack primaryStack, ItemStack secondaryStack) {
        return getRecipe(level, c -> c.doesPrimaryInputMatch(primaryStack) && c.doesSecondaryInputMatch(secondaryStack));
    }

    public static RunicWorkbenchRecipe getRecipe(Level level, Predicate<RunicWorkbenchRecipe> predicate) {
        return getRecipe(level, RecipeTypeRegistry.RUNEWORKING.get(), predicate);
    }

    public static List<RunicWorkbenchRecipe> getRecipes(Level level) {
        return getRecipes(level, RecipeTypeRegistry.RUNEWORKING.get());
    }

    public static class Serializer implements RecipeSerializer<RunicWorkbenchRecipe> {

        @Override
        public RunicWorkbenchRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            IngredientWithCount primaryInput = IngredientWithCount.deserialize(json.getAsJsonObject("primaryInput"));
            IngredientWithCount secondaryInput = IngredientWithCount.deserialize(json.getAsJsonObject("secondaryInput"));

            JsonObject outputObject = json.getAsJsonObject("output");
            ItemStack output = CraftingHelper.getItemStack(outputObject, true);

            return new RunicWorkbenchRecipe(recipeId, primaryInput, secondaryInput, output);
        }

        @Nullable
        @Override
        public RunicWorkbenchRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            IngredientWithCount primaryInput = IngredientWithCount.read(buffer);
            IngredientWithCount secondaryInput = IngredientWithCount.read(buffer);
            ItemStack output = buffer.readItem();
            return new RunicWorkbenchRecipe(recipeId, primaryInput, secondaryInput, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RunicWorkbenchRecipe recipe) {
            recipe.primaryInput.write(buffer);
            recipe.secondaryInput.write(buffer);
            buffer.writeItem(recipe.output);
        }
    }
}
