package com.sammy.malum.common.recipe;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import com.sammy.ortus.systems.recipe.IOrtusRecipe;
import com.sammy.ortus.systems.recipe.IngredientWithCount;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.List;

public class SpiritTransmutationRecipe extends IOrtusRecipe {
    public static final String NAME = "spirit_transmutation";

    public static class Type implements RecipeType<SpiritTransmutationRecipe> {
        @Override
        public String toString() {
            return MalumMod.MALUM + ":" + NAME;
        }

        public static final SpiritTransmutationRecipe.Type INSTANCE = new SpiritTransmutationRecipe.Type();
    }

    private final ResourceLocation id;

    public final IngredientWithCount input;
    public final IngredientWithCount output;

    public SpiritTransmutationRecipe(ResourceLocation id, IngredientWithCount input, IngredientWithCount output) {
        this.id = id;
        this.input = input;
        this.output = output;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER.get();
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
        return this.input.matches(input);
    }

    public boolean doesOutputMatch(ItemStack output) {
        return this.output.matches(output);
    }

    public static SpiritTransmutationRecipe getRecipe(Level level, Item item) {
        return getRecipe(level, item.getDefaultInstance());
    }
    public static SpiritTransmutationRecipe getRecipe(Level level, ItemStack item) {
        List<SpiritTransmutationRecipe> recipes = getRecipes(level);
        for (SpiritTransmutationRecipe recipe : recipes) {
            if (recipe.doesInputMatch(item)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<SpiritTransmutationRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SpiritTransmutationRecipe> {

        @Override
        public SpiritTransmutationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            IngredientWithCount input = IngredientWithCount.deserialize(json.getAsJsonObject("input"));
            IngredientWithCount output = IngredientWithCount.deserialize(json.getAsJsonObject("output"));
            return new SpiritTransmutationRecipe(recipeId, input, output);
        }

        @Nullable
        @Override
        public SpiritTransmutationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            IngredientWithCount input = IngredientWithCount.read(buffer);
            IngredientWithCount output = IngredientWithCount.read(buffer);
            return new SpiritTransmutationRecipe(recipeId, input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritTransmutationRecipe recipe) {
            recipe.input.write(buffer);
            recipe.output.write(buffer);
        }
    }
}