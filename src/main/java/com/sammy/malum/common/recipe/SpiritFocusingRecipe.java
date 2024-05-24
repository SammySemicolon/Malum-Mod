package com.sammy.malum.common.recipe;

import com.google.gson.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import io.github.fabricators_of_create.porting_lib.util.CraftingHelper;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.*;

public class SpiritFocusingRecipe extends AbstractSpiritListMalumRecipe {
    public static final String NAME = "spirit_focusing";

    public final int time;
    public final int durabilityCost;

    public final Ingredient input;
    public final ItemStack output;

    public SpiritFocusingRecipe(ResourceLocation id, int time, int durabilityCost, Ingredient input, ItemStack output, List<SpiritWithCount> spirits) {
        super(id, RecipeSerializerRegistry.FOCUSING_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_FOCUSING.get(), spirits);
        this.time = time;
        this.durabilityCost = durabilityCost;
        this.input = input;
        this.output = output;
    }

    public boolean doesInputMatch(ItemStack input) {
        return this.input.test(input);
    }

    public boolean doesOutputMatch(ItemStack output) {
        return output.getItem().equals(this.output.getItem());
    }

    public static SpiritFocusingRecipe getRecipe(Level level, ItemStack stack, List<ItemStack> spirits) {
        return getRecipe(level, c -> c.doesInputMatch(stack) && c.doSpiritsMatch(spirits));
    }

    public static SpiritFocusingRecipe getRecipe(Level level, Predicate<SpiritFocusingRecipe> predicate) {
        return getRecipe(level, RecipeTypeRegistry.SPIRIT_FOCUSING.get(), predicate);
    }

    public static List<SpiritFocusingRecipe> getRecipes(Level level) {
        return getRecipes(level, RecipeTypeRegistry.SPIRIT_FOCUSING.get());
    }

    public static class Serializer implements RecipeSerializer<SpiritFocusingRecipe> {

        @Override
        public SpiritFocusingRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            int time = json.getAsJsonPrimitive("time").getAsInt();
            int durabilityCost = json.getAsJsonPrimitive("durabilityCost").getAsInt();

            JsonObject inputObject = json.getAsJsonObject("input");
            Ingredient input = Ingredient.fromJson(inputObject);

            JsonObject outputObject = json.getAsJsonObject("output");
            ItemStack output = CraftingHelper.getItemStack(outputObject, true);

            JsonArray spiritsArray = json.getAsJsonArray("spirits");
            List<SpiritWithCount> spirits = new ArrayList<>();
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
            ItemStack output = buffer.readItem();
            int spiritCount = buffer.readInt();
            List<SpiritWithCount> spirits = new ArrayList<>();
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
            buffer.writeItem(recipe.output);
            buffer.writeInt(recipe.spirits.size());
            for (SpiritWithCount item : recipe.spirits) {
                buffer.writeItem(item.getStack());
            }
        }
    }
}
