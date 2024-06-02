package com.sammy.malum.data.recipe.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class SpiritFocusingRecipeBuilder {
    private final int time;
    private final int durabilityCost;
    private final Ingredient input;
    private final ItemStack output;
    private final List<SpiritWithCount> spirits = Lists.newArrayList();

    public SpiritFocusingRecipeBuilder(int time, int durabilityCost, Ingredient input, ItemStack output) {
        this.time = time;
        this.durabilityCost = durabilityCost;
        this.input = input;
        this.output = output;
    }

    public SpiritFocusingRecipeBuilder(int time, int durabilityCost, Ingredient input, ItemLike output, int outputCount) {
        this(time, durabilityCost, input, new ItemStack(output, outputCount));
    }

    public SpiritFocusingRecipeBuilder addSpirit(MalumSpiritType type, int count) {
        spirits.add(new SpiritWithCount(type, count));
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("spirit_crucible/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, BuiltInRegistries.ITEM.getKey(output.getItem()).getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new SpiritFocusingRecipeBuilder.Result(id));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public Result(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject inputObject = input.toJson().getAsJsonObject();

            JsonElement outputObject = Ingredient.of(output).toJson();
            if (output.getCount() != 1) {
                outputObject.getAsJsonObject().addProperty("count", output.getCount());
            }
            JsonArray spiritJson = new JsonArray();
            for (SpiritWithCount spirit : spirits) {
                spiritJson.add(spirit.serialize());
            }

            json.addProperty("time", time);
            json.addProperty("durabilityCost", durabilityCost);
            json.add("input", inputObject);
            json.add("output", outputObject);
            json.add("spirits", spiritJson);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.FOCUSING_RECIPE_SERIALIZER.get();
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
