package com.sammy.malum.common.recipe;

import com.google.gson.JsonObject;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class SpiritTransmutationRecipe extends ILodestoneRecipe {
    public static final String NAME = "spirit_transmutation";

    private final ResourceLocation id;

    public final Ingredient ingredient;
    public final ItemStack output;

    @Nullable
    public final String group;

    public SpiritTransmutationRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output, @Nullable String group) {
        this.id = id;
        this.ingredient = ingredient;
        this.output = output;
        this.group = group;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public static SpiritTransmutationRecipe getRecipe(Level level, Item item) {
        return getRecipe(level, item.getDefaultInstance());
    }

    public static SpiritTransmutationRecipe getRecipe(Level level, ItemStack item) {
        return getRecipe(level, r -> r.ingredient.test(item));
    }

    public static SpiritTransmutationRecipe getRecipe(Level level, Predicate<SpiritTransmutationRecipe> predicate) {
        List<SpiritTransmutationRecipe> recipes = getRecipes(level);
        for (SpiritTransmutationRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<SpiritTransmutationRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.SPIRIT_TRANSMUTATION.get());
    }

    public static class Serializer implements RecipeSerializer<SpiritTransmutationRecipe> {

        @Override
        public SpiritTransmutationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(json.getAsJsonObject("input"));
            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
            String group = json.has("group") ? json.get("group").getAsString() : null;
            return new SpiritTransmutationRecipe(recipeId, input, output, group);
        }

        @Nullable
        @Override
        public SpiritTransmutationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient ingredient = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            String group = buffer.readBoolean() ? buffer.readUtf() : null;
            return new SpiritTransmutationRecipe(recipeId, ingredient, output, group);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritTransmutationRecipe recipe) {
            recipe.ingredient.toNetwork(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeBoolean(recipe.group != null);
            if (recipe.group != null)
                buffer.writeUtf(recipe.group);
        }
    }
}
