package com.sammy.malum.common.recipe;

import com.google.gson.*;
import com.sammy.malum.core.systems.recipe.*;
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

public class SpiritInfusionRecipe extends AbstractSpiritListMalumRecipe {
    public static final String NAME = "spirit_infusion";

    public final IngredientWithCount input;
    public final boolean useNbtFromInput;

    public final ItemStack output;

    public final List<IngredientWithCount> extraItems;

    public SpiritInfusionRecipe(ResourceLocation id, IngredientWithCount input, boolean useNbtFromInput, ItemStack output, List<SpiritWithCount> spirits, List<IngredientWithCount> extraItems) {
        super(id, RecipeSerializerRegistry.INFUSION_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_INFUSION.get(), spirits);
        this.input = input;
        this.useNbtFromInput = useNbtFromInput;
        this.output = output;
        this.extraItems = extraItems;
    }

    public boolean doesInputMatch(ItemStack input) {
        return this.input.matches(input);
    }

    public boolean doesOutputMatch(ItemStack output) {
        return output.getItem().equals(this.output.getItem());
    }

    public static SpiritInfusionRecipe getRecipe(Level level, ItemStack stack, List<ItemStack> spirits) {
        return getRecipe(level, c -> c.doesInputMatch(stack) && c.doSpiritsMatch(spirits));
    }

    public static SpiritInfusionRecipe getRecipe(Level level, Predicate<SpiritInfusionRecipe> predicate) {
        return getRecipe(level, RecipeTypeRegistry.SPIRIT_INFUSION.get(), predicate);
    }

    public static List<SpiritInfusionRecipe> getRecipes(Level level) {
        return getRecipes(level, RecipeTypeRegistry.SPIRIT_INFUSION.get());
    }

    public static class Serializer implements RecipeSerializer<SpiritInfusionRecipe> {
        @Override
        public SpiritInfusionRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonObject inputObject = json.getAsJsonObject("input");
            IngredientWithCount input = IngredientWithCount.deserialize(inputObject);
            boolean useNbtFromInput = !json.has("useNbtFromInput") || json.getAsJsonPrimitive("useNbtFromInput").getAsBoolean();
            JsonObject outputObject = json.getAsJsonObject("output");
            ItemStack output = CraftingHelper.getItemStack(outputObject, true);
            JsonArray extraItemsArray = json.getAsJsonArray("extra_items");
            List<IngredientWithCount> extraItems = new ArrayList<>();
            for (int i = 0; i < extraItemsArray.size(); i++) {
                JsonObject extraItemObject = extraItemsArray.get(i).getAsJsonObject();
                extraItems.add(IngredientWithCount.deserialize(extraItemObject));
            }

            JsonArray spiritsArray = json.getAsJsonArray("spirits");
            List<SpiritWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritsArray.size(); i++) {
                JsonObject spiritObject = spiritsArray.get(i).getAsJsonObject();
                spirits.add(SpiritWithCount.deserialize(spiritObject));
            }
            if (spirits.isEmpty()) {
                return null;
            }
            return new SpiritInfusionRecipe(recipeId, input, useNbtFromInput, output, spirits, extraItems);
        }

        @Nullable
        @Override
        public SpiritInfusionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            IngredientWithCount input = IngredientWithCount.read(buffer);
            boolean useNbtFromInput = buffer.readBoolean();
            ItemStack output = buffer.readItem();
            int extraItemCount = buffer.readInt();
            List<IngredientWithCount> extraItems = new ArrayList<>();
            for (int i = 0; i < extraItemCount; i++) {
                extraItems.add(IngredientWithCount.read(buffer));
            }
            int spiritCount = buffer.readInt();
            List<SpiritWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritCount; i++) {
                spirits.add(new SpiritWithCount(buffer.readItem()));
            }
            return new SpiritInfusionRecipe(recipeId, input, useNbtFromInput, output, spirits, extraItems);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritInfusionRecipe recipe) {
            recipe.input.write(buffer);
            buffer.writeBoolean(recipe.useNbtFromInput);
            buffer.writeItem(recipe.output);
            buffer.writeInt(recipe.extraItems.size());
            for (IngredientWithCount item : recipe.extraItems) {
                item.write(buffer);
            }
            buffer.writeInt(recipe.spirits.size());
            for (SpiritWithCount item : recipe.spirits) {
                buffer.writeItem(item.getStack());
            }
        }
    }
}
