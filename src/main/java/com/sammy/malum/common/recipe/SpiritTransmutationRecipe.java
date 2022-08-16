package com.sammy.malum.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SpiritTransmutationRecipe extends ILodestoneRecipe {
    public static final String NAME = "spirit_transmutation";

    public static class Type implements RecipeType<SpiritTransmutationRecipe> {
        @Override
        public String toString() {
            return MalumMod.MALUM + ":" + NAME;
        }

        public static final SpiritTransmutationRecipe.Type INSTANCE = new SpiritTransmutationRecipe.Type();
    }

    private final ResourceLocation id;

    public final List<Pair<Ingredient, ItemStack>> subRecipes;

    public SpiritTransmutationRecipe(ResourceLocation id, List<Pair<Ingredient, ItemStack>> subRecipes) {
        this.id = id;
        this.subRecipes = subRecipes;
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

    public Pair<Ingredient, ItemStack> getSubRecipe(ItemStack input) {
        for (var recipe : subRecipes) {
            if (recipe.getFirst().test(input)) {
                return recipe;
            }
        }
        return null;
    }

    public static Pair<Ingredient, ItemStack> getRecipe(Level level, Item item) {
        return getRecipe(level, item.getDefaultInstance());
    }

    public static Pair<Ingredient, ItemStack> getRecipe(Level level, ItemStack item) {
        List<SpiritTransmutationRecipe> recipes = getRecipes(level);
        for (SpiritTransmutationRecipe recipe : recipes) {
            var subRecipe = recipe.getSubRecipe(item);
            if (subRecipe != null) {
                return subRecipe;
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
            List<Pair<Ingredient, ItemStack>> subRecipes = new ArrayList<>();
            for (JsonElement element : json.getAsJsonArray("recipes").getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();
                Ingredient input = Ingredient.fromJson(object.getAsJsonObject("input"));
                ItemStack output = CraftingHelper.getItemStack(object.getAsJsonObject("output"), true);
                subRecipes.add(new Pair<>(input, output));
            }
            return new SpiritTransmutationRecipe(recipeId, subRecipes);
        }

        @Nullable
        @Override
        public SpiritTransmutationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int amount = buffer.readVarInt();
            List<Pair<Ingredient, ItemStack>> subRecipes = new ArrayList<>();

            for (int i = 0; i < amount; i++) {
                Ingredient input = Ingredient.fromNetwork(buffer);
                ItemStack output = buffer.readItem();
                subRecipes.add(new Pair<>(input, output));
            }
            return new SpiritTransmutationRecipe(recipeId, subRecipes);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritTransmutationRecipe recipe) {
            buffer.writeInt(recipe.subRecipes.size());
            for (var subRecipe : recipe.subRecipes) {
                subRecipe.getFirst().toNetwork(buffer);
                buffer.writeItem(subRecipe.getSecond());
            }
        }
    }
}
