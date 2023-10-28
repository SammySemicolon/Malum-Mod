package com.sammy.malum.data.recipe.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class SpiritInfusionRecipeBuilder {
    private final IngredientWithCount input;

    private final ItemStack output;

    private final List<SpiritWithCount> spirits = Lists.newArrayList();
    private final List<IngredientWithCount> extraItems = Lists.newArrayList();

    public SpiritInfusionRecipeBuilder(Ingredient input, int inputCount, ItemStack output) {
        this.input = new IngredientWithCount(input, inputCount);
        this.output = output;
    }

    public SpiritInfusionRecipeBuilder(Ingredient input, int inputCount, ItemLike output, int outputCount) {
        this(input, inputCount, new ItemStack(output, outputCount));
    }

    public SpiritInfusionRecipeBuilder(ItemLike input, int inputCount, ItemStack output) {
        this(Ingredient.of(input), inputCount, output);
    }

    public SpiritInfusionRecipeBuilder(ItemLike input, int inputCount, ItemLike output, int outputCount) {
        this(Ingredient.of(input), inputCount, new ItemStack(output, outputCount));
    }

    public SpiritInfusionRecipeBuilder addExtraItem(Ingredient ingredient, int count) {
        extraItems.add(new IngredientWithCount(ingredient, count));
        return this;
    }

    public SpiritInfusionRecipeBuilder addExtraItem(Item input, int count) {
        extraItems.add(new IngredientWithCount(Ingredient.of(input), count));
        return this;
    }

    public SpiritInfusionRecipeBuilder addSpirit(MalumSpiritType type, int count) {
        spirits.add(new SpiritWithCount(type, count));
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("spirit_infusion/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, ForgeRegistries.ITEMS.getKey(output.getItem()).getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new SpiritInfusionRecipeBuilder.Result(id));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public Result(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject inputObject = input.serialize();
            JsonElement outputObject = Ingredient.of(output).toJson();
            if (output.getCount() != 1) {
                outputObject.getAsJsonObject().addProperty("count", output.getCount());
            }
            JsonArray extraItemsJson = new JsonArray();
            for (IngredientWithCount extraItem : extraItems) {
                extraItemsJson.add(extraItem.serialize());
            }
            JsonArray spiritsJson = new JsonArray();
            for (SpiritWithCount spirit : spirits) {
                spiritsJson.add(spirit.serialize());
            }
            json.add("input", inputObject);
            json.add("output", outputObject);
            json.add("extra_items", extraItemsJson);
            json.add("spirits", spiritsJson);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.INFUSION_RECIPE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
