package com.sammy.malum.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.ortus.systems.recipe.IOrtusRecipe;
import com.sammy.ortus.systems.recipe.IngredientWithCount;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class AugmentingRecipe extends IOrtusRecipe {
    public static final String NAME = "augmenting";

    public static class Type implements RecipeType<AugmentingRecipe> {
        @Override
        public String toString() {
            return MalumMod.MALUM + ":" + NAME;
        }

        public static final AugmentingRecipe.Type INSTANCE = new AugmentingRecipe.Type();
    }

    private final ResourceLocation id;

    public final Ingredient targetItem;
    public final Ingredient input;
    public final CompoundTag tagAugment;

    public AugmentingRecipe(ResourceLocation id, Ingredient targetItem, Ingredient input, CompoundTag tagAugment) {
        this.id = id;
        this.targetItem = targetItem;
        this.input = input;
        this.tagAugment = tagAugment;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.AUGMENTING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public boolean doesInputMatch(ItemStack input) {
        return this.targetItem.test(input);
    }

    public static AugmentingRecipe getRecipe(Level level, ItemStack stack) {
        return getRecipe(level, c -> c.doesInputMatch(stack));
    }

    public static AugmentingRecipe getRecipe(Level level, Predicate<AugmentingRecipe> predicate) {
        List<AugmentingRecipe> recipes = getRecipes(level);
        for (AugmentingRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<AugmentingRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AugmentingRecipe> {

        @Override
        public AugmentingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient targetItem = Ingredient.fromJson(json.get("targetItem"));
            Ingredient input = Ingredient.fromJson(json.get("input"));

            CompoundTag tagAugment = CraftingHelper.getNBT(json.get("tagAugment"));
            return new AugmentingRecipe(recipeId, targetItem, input, tagAugment);
        }

        @Nullable
        @Override
        public AugmentingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient targetItem = Ingredient.fromNetwork(buffer);
            Ingredient input = Ingredient.fromNetwork(buffer);
            CompoundTag tagAugment = buffer.readNbt();
            return new AugmentingRecipe(recipeId, targetItem, input, tagAugment);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AugmentingRecipe recipe) {
            recipe.targetItem.toNetwork(buffer);
            recipe.input.toNetwork(buffer);
            buffer.writeNbt(recipe.tagAugment);
        }
    }
}