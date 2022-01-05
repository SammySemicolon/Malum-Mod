package com.sammy.malum.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.spirit.MalumSpiritItem;
import com.sammy.malum.core.registry.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.IMalumRecipe;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SpiritFocusingRecipe extends IMalumRecipe {
    public static final String NAME = "spirit_focusing";

    public static class Type implements RecipeType<SpiritFocusingRecipe> {
        @Override
        public String toString() {
            return MalumMod.MODID + ":" + NAME;
        }

        public static final SpiritFocusingRecipe.Type INSTANCE = new SpiritFocusingRecipe.Type();
    }

    private final ResourceLocation id;

    public final int time;
    public final int durabilityCost;

    public final Ingredient input;
    public final IngredientWithCount output;
    public final List<ItemWithCount> spirits;

    public SpiritFocusingRecipe(ResourceLocation id, int time, int durabilityCost, Ingredient input, IngredientWithCount output, List<ItemWithCount> spirits) {
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

    public ArrayList<ItemStack> getSortedStacks(ArrayList<ItemStack> stacks) {
        ArrayList<ItemStack> sortedStacks = new ArrayList<>();
        for (ItemWithCount item : spirits) {
            for (ItemStack stack : stacks) {
                if (item.matches(stack)) {
                    sortedStacks.add(stack);
                    break;
                }
            }
        }
        return sortedStacks;
    }

    public ArrayList<MalumSpiritType> getSpirits() {
        ArrayList<MalumSpiritType> spirits = new ArrayList<>();
        for (ItemWithCount item : this.spirits) {
            MalumSpiritItem spiritItem = (MalumSpiritItem) item.item;
            spirits.add(spiritItem.type);
        }
        return spirits;
    }

    public boolean doSpiritsMatch(ArrayList<ItemStack> spirits) {
        if (this.spirits.size() == 0) {
            return true;
        }
        if (this.spirits.size() != spirits.size()) {
            return false;
        }
        ArrayList<ItemStack> sortedStacks = getSortedStacks(spirits);
        if (sortedStacks.size() < this.spirits.size()) {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++) {
            ItemWithCount item = this.spirits.get(i);
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
        public boolean isValid(Ingredient ingredient) {
            return Arrays.stream(ingredient.getItems()).map(ItemStack::getItem).noneMatch(s -> s.equals(Items.BARRIER));
        }
        public boolean isValid(Stream<Item> item) {
            return item.noneMatch(i -> i.equals(Items.BARRIER));
        }
        public boolean isValid(Item item) {
            return !item.equals(Items.BARRIER);
        }
        @Override
        public SpiritFocusingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int time = json.getAsJsonPrimitive("time").getAsInt();
            int durabilityCost = json.getAsJsonPrimitive("durabilityCost").getAsInt();

            JsonObject inputObject = json.getAsJsonObject("input");
            Ingredient input = Ingredient.fromJson(inputObject);
            if (!isValid(input))
            {
                return null;
            }

            JsonObject outputObject = json.getAsJsonObject("output");
            IngredientWithCount output = IngredientWithCount.deserialize(outputObject);
            if (!isValid(output.ingredient))
            {
                return null;
            }

            JsonArray spiritsArray = json.getAsJsonArray("spirits");
            ArrayList<ItemWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritsArray.size(); i++) {
                JsonObject spiritObject = spiritsArray.get(i).getAsJsonObject();
                spirits.add(ItemWithCount.deserialize(spiritObject));
            }
            if (!isValid(spirits.stream().map(ItemWithCount::getItem)))
            {
                return null;
            }
            if (spirits.isEmpty())
            {
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
            ArrayList<ItemWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritCount; i++) {
                spirits.add(new ItemWithCount(buffer.readItem()));
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
            for (ItemWithCount item : recipe.spirits) {
                buffer.writeItem(item.getStack());
            }
        }
    }
}