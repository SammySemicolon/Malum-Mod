package com.sammy.malum.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.*;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class SpiritFocusingRecipe extends ILodestoneRecipe {
    public static final String NAME = "spirit_focusing";

    public static class Type implements RecipeType<SpiritFocusingRecipe> {
        @Override
        public String toString() {
            return MalumMod.MALUM + ":" + NAME;
        }

        public static final SpiritFocusingRecipe.Type INSTANCE = new SpiritFocusingRecipe.Type();
    }

    private final ResourceLocation id;

    public final int time;
    public final int durabilityCost;

    public final Ingredient input;
    public final IngredientWithCount output;
    public final ArrayList<SpiritWithCount> spirits;

    public SpiritFocusingRecipe(ResourceLocation id, int time, int durabilityCost, Ingredient input, IngredientWithCount output, ArrayList<SpiritWithCount> spirits) {
        this.id = id;
        this.time = time;
        this.durabilityCost = durabilityCost;
        this.input = input;
        this.output = output;
        this.spirits = spirits;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.FOCUSING_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public ArrayList<ItemStack> getSortedSpirits(ArrayList<ItemStack> stacks) {
        ArrayList<ItemStack> sortedStacks = new ArrayList<>();
        for (SpiritWithCount item : spirits) {
            for (ItemStack stack : stacks) {
                if (item.matches(stack)) {
                    sortedStacks.add(stack);
                    break;
                }
            }
        }
        return sortedStacks;
    }

    public boolean doSpiritsMatch(ArrayList<ItemStack> spirits) {
        if (this.spirits.size() == 0) {
            return true;
        }
        if (this.spirits.size() != spirits.size()) {
            return false;
        }
        ArrayList<ItemStack> sortedStacks = getSortedSpirits(spirits);
        if (sortedStacks.size() < this.spirits.size()) {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++) {
            SpiritWithCount item = this.spirits.get(i);
            ItemStack stack = sortedStacks.get(i);
            if (!item.matches(stack)) {
                return false;
            }
        }
        return true;
    }

    public boolean doesInputMatch(ItemStack input) {
        return this.input.test(input);
    }

    public boolean doesOutputMatch(ItemStack output) {
        return this.output.ingredient.test(output);
    }

    public static SpiritFocusingRecipe getRecipe(Level level, ItemStack stack, ArrayList<ItemStack> spirits) {
        return getRecipe(level, c -> c.doesInputMatch(stack) && c.doSpiritsMatch(spirits));
    }

    public static SpiritFocusingRecipe getRecipe(Level level, Predicate<SpiritFocusingRecipe> predicate) {
        List<SpiritFocusingRecipe> recipes = getRecipes(level);
        for (SpiritFocusingRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<SpiritFocusingRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SpiritFocusingRecipe> {

        @Override
        public SpiritFocusingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int time = json.getAsJsonPrimitive("time").getAsInt();
            int durabilityCost = json.getAsJsonPrimitive("durabilityCost").getAsInt();

            JsonObject inputObject = json.getAsJsonObject("input");
            Ingredient input = Ingredient.fromJson(inputObject);

            JsonObject outputObject = json.getAsJsonObject("output");
            IngredientWithCount output = IngredientWithCount.deserialize(outputObject);

            JsonArray spiritsArray = json.getAsJsonArray("spirits");
            ArrayList<SpiritWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritsArray.size(); i++) {
                JsonObject spiritObject = spiritsArray.get(i).getAsJsonObject();
                spirits.add(SpiritWithCount.deserialize(spiritObject));
            }
            if (spirits.isEmpty()) {
                return null;
            }
            return new SpiritFocusingRecipe(recipeId, time, durabilityCost, input, output, spirits);
        }

        @Nullable
        @Override
        public SpiritFocusingRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            int time = buffer.readInt();
            int durabilityCost = buffer.readInt();
            Ingredient input = Ingredient.fromNetwork(buffer);
            IngredientWithCount output = IngredientWithCount.read(buffer);
            int spiritCount = buffer.readInt();
            ArrayList<SpiritWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritCount; i++) {
                spirits.add(new SpiritWithCount(buffer.readItem()));
            }
            return new SpiritFocusingRecipe(recipeId, time, durabilityCost, input, output, spirits);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritFocusingRecipe recipe) {
            buffer.writeInt(recipe.time);
            buffer.writeInt(recipe.durabilityCost);
            recipe.input.toNetwork(buffer);
            recipe.output.write(buffer);
            buffer.writeInt(recipe.spirits.size());
            for (SpiritWithCount item : recipe.spirits) {
                buffer.writeItem(item.getStack());
            }
        }
    }
}