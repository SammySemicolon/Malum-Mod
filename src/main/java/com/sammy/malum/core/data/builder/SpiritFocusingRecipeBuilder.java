package com.sammy.malum.core.data.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class SpiritFocusingRecipeBuilder
{
    private final int time;
    private final int durabilityCost;
    private final Ingredient input;
    private final IngredientWithCount output;
    private final List<ItemWithCount> spirits = Lists.newArrayList();

    public SpiritFocusingRecipeBuilder(int time, int durabilityCost, Ingredient input, Ingredient output, int outputCount)
    {
        this.time = time;
        this.durabilityCost = durabilityCost;
        this.input = input;
        this.output = new IngredientWithCount(output, outputCount);
    }
    public SpiritFocusingRecipeBuilder addSpirit(MalumSpiritType type, int count)
    {
        spirits.add(new ItemWithCount(type.getSplinterItem(), count));
        return this;
    }
    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName)
    {
        build(consumerIn, DataHelper.prefix("spirit_crucible/" + recipeName));
    }
    public void build(Consumer<FinishedRecipe> consumerIn)
    {
        build(consumerIn, output.stack().getItem().getRegistryName().getPath());
    }
    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id)
    {
        consumerIn.accept(new SpiritFocusingRecipeBuilder.Result(id, time, durabilityCost, input, output, spirits));
    }

    public static class Result implements FinishedRecipe
    {
        private final ResourceLocation id;

        private final int time;
        private final int durabilityCost;

        private final Ingredient input;
        private final IngredientWithCount output;
        private final List<ItemWithCount> spirits;


        public Result(ResourceLocation id, int time, int durabilityCost, Ingredient input, IngredientWithCount output, List<ItemWithCount> spirits)
        {
            this.id = id;
            this.time = time;
            this.durabilityCost = durabilityCost;
            this.input = input;
            this.output = output;
            this.spirits = spirits;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonObject inputObject = input.toJson().getAsJsonObject();

            JsonObject outputObject = output.serialize();
            JsonArray spirits = new JsonArray();
            for (ItemWithCount spirit : this.spirits) {
                spirits.add(spirit.serialize());
            }

            json.addProperty("time", time);
            json.addProperty("durabilityCost", durabilityCost);
            json.add("input", inputObject);
            json.add("output", outputObject);
            json.add("spirits", spirits);
        }

        @Override
        public ResourceLocation getId()
        {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return RecipeSerializerRegistry.FOCUSING_RECIPE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement()
        {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId()
        {
            return null;
        }
    }
}