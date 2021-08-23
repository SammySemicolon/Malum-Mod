package com.sammy.malum.core.data.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.mod_systems.recipe.IngredientWithCount;
import com.sammy.malum.core.mod_systems.recipe.ItemWithCount;
import com.sammy.malum.common.recipe.SpiritInfusionRecipe;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class SpiritInfusionRecipeBuilder
{

    private final IngredientWithCount input;

    private final ItemWithCount output;

    private final List<ItemWithCount> spirits = Lists.newArrayList();
    private final List<IngredientWithCount> extraItems = Lists.newArrayList();

    public SpiritInfusionRecipeBuilder(Ingredient input, int inputCount, Item output, int outputCount)
    {
        this.input = new IngredientWithCount(input, inputCount);
        this.output = new ItemWithCount(output, outputCount);
    }
    public SpiritInfusionRecipeBuilder(Item input, int inputCount, Item output, int outputCount)
    {
        this.input = new IngredientWithCount(Ingredient.fromItems(input), inputCount);
        this.output = new ItemWithCount(output, outputCount);
    }
    public SpiritInfusionRecipeBuilder addExtraItem(Ingredient ingredient, int count)
    {
        extraItems.add(new IngredientWithCount(ingredient, count));
        return this;
    }
    public SpiritInfusionRecipeBuilder addExtraItem(Item input, int count)
    {
        extraItems.add(new IngredientWithCount(Ingredient.fromItems(input), count));
        return this;
    }
    public SpiritInfusionRecipeBuilder addSpirit(MalumSpiritType type, int count)
    {
        spirits.add(new ItemWithCount(type.splinterItem(), count));
        return this;
    }
    public void build(Consumer<IFinishedRecipe> consumerIn, String recipeName)
    {
        build(consumerIn, MalumHelper.prefix("spirit_infusion/" + recipeName));
    }
    public void build(Consumer<IFinishedRecipe> consumerIn)
    {
        build(consumerIn, output.item.getRegistryName().getPath());
    }
    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id)
    {
        consumerIn.accept(new SpiritInfusionRecipeBuilder.Result(id, input, output, spirits, extraItems));
    }

    public static class Result implements IFinishedRecipe
    {
        private final ResourceLocation id;

        private final IngredientWithCount input;

        private final ItemWithCount output;

        private final List<ItemWithCount> spirits;
        private final List<IngredientWithCount> extraItems;


        public Result(ResourceLocation id, IngredientWithCount input, ItemWithCount output, List<ItemWithCount> spirits, List<IngredientWithCount> extraItems)
        {
            this.id = id;
            this.input = input;
            this.output = output;
            this.spirits = spirits;
            this.extraItems = extraItems;
        }

        @Override
        public void serialize(JsonObject json)
        {
            JsonObject inputObject = input.serialize();

            JsonObject outputObject = output.serialize();
            JsonArray extraItems = new JsonArray();
            for (IngredientWithCount extraItem : this.extraItems)
            {
                extraItems.add(extraItem.serialize());
            }
            JsonArray spirits = new JsonArray();
            for (ItemWithCount spirit : this.spirits)
            {
                spirits.add(spirit.serialize());
            }
            json.add("input", inputObject);
            json.add("output", outputObject);
            json.add("extra_items", extraItems);
            json.add("spirits", spirits);
        }

        @Override
        public ResourceLocation getID()
        {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer()
        {
            return SpiritInfusionRecipe.SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson()
        {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID()
        {
            return null;
        }
    }
}