package com.sammy.malum.common.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import net.minecraftforge.registries.ForgeRegistryEntry;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SpiritTransmutationRecipe extends ILodestoneRecipe {
    public static final String NAME = "soulwood_spirit_transmutation";

    public static class Type implements RecipeType<SpiritTransmutationRecipe> {
        @Override
        public String toString() {
            return MalumMod.MALUM + ":" + NAME;
        }

        public static final SpiritTransmutationRecipe.Type INSTANCE = new SpiritTransmutationRecipe.Type();
    }

    private final ResourceLocation id;

    public final List<Ingredient> inputs;
    public final List<Ingredient> outputs;

    public SpiritTransmutationRecipe(ResourceLocation id, List<Ingredient> inputs, List<Ingredient> outputs) {
        this.id = id;
        this.inputs = inputs;
        this.outputs = outputs;
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

    public Ingredient getInput(ItemStack input) {
        for (Ingredient ingredient : inputs) {
            if (ingredient.test(input)) {
                return ingredient;
            }
        }
        return null;
    }

    public ItemStack getOutput(ItemStack input) {
        for (int i = 0; i < inputs.size(); i++) {
            if (inputs.get(i).test(input)) {
                return outputs.get(i).getItems()[0];
            }
        }
        return null;
    }

    public static SpiritTransmutationRecipe getRecipe(Level level, Item item) {
        return getRecipe(level, item.getDefaultInstance());
    }

    public static SpiritTransmutationRecipe getRecipe(Level level, ItemStack item) {
        List<SpiritTransmutationRecipe> recipes = getRecipes(level);
        for (SpiritTransmutationRecipe recipe : recipes) {
            Ingredient input = recipe.getInput(item);
            if (input != null) {
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
            ArrayList<Ingredient> inputs = new ArrayList<>();
            ArrayList<Ingredient> outputs = new ArrayList<>();
            for (JsonElement element : json.getAsJsonArray("recipes").getAsJsonArray()) {
                JsonObject object = element.getAsJsonObject();
                inputs.add(Ingredient.fromJson(object.getAsJsonObject("input")));
                outputs.add(Ingredient.fromJson(object.getAsJsonObject("output")));
            }
            return new SpiritTransmutationRecipe(recipeId, inputs, outputs);
        }

        @Nullable
        @Override
        public SpiritTransmutationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int amount = buffer.readVarInt();
            ArrayList<Ingredient> inputs = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                inputs.add(Ingredient.fromNetwork(buffer));
            }
            ArrayList<Ingredient> outputs = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                outputs.add(Ingredient.fromNetwork(buffer));
            }
            return new SpiritTransmutationRecipe(recipeId, inputs, outputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritTransmutationRecipe recipe) {
            buffer.writeInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.toNetwork(buffer);
            }
            for (Ingredient ingredient : recipe.outputs) {
                ingredient.toNetwork(buffer);
            }
        }
    }
}