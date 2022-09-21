package com.sammy.malum.common.recipe;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.recipe.RecipeSerializerRegistry;
import net.minecraft.core.Registry;
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
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class AugmentingRecipe extends ILodestoneRecipe {
    public static final String NAME = "augmenting";

    public static class Type implements RecipeType<AugmentingRecipe> {
        @Override
        public String toString() {
            return MalumMod.MALUM + ":" + NAME;
        }

        public static final AugmentingRecipe.Type INSTANCE = Registry.register(Registry.RECIPE_TYPE, MalumMod.malumPath(NAME), new AugmentingRecipe.Type());
    }

    private final ResourceLocation id;

    public final Ingredient targetItem;
    public final Ingredient augment;
    public final CompoundTag tagAugment;

    public AugmentingRecipe(ResourceLocation id, Ingredient targetItem, Ingredient augment, CompoundTag tagAugment) {
        this.id = id;
        this.targetItem = targetItem;
        this.augment = augment;
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

    public boolean doesAugmentMatch(ItemStack input) {
        return this.augment.test(input);
    }

    public static AugmentingRecipe getRecipe(Level level, ItemStack stack, ItemStack augment) {
        return getRecipe(level, c -> c.doesInputMatch(stack) && c.doesAugmentMatch(augment));
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
            Ingredient input = Ingredient.fromJson(json.get("augment"));

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
            recipe.augment.toNetwork(buffer);
            buffer.writeNbt(recipe.tagAugment);
        }
    }
}