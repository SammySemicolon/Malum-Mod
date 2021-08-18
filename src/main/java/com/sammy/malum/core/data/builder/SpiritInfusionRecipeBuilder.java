package com.sammy.malum.core.data.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumHelper.ItemCount;
import com.sammy.malum.core.mod_content.SpiritInfusionRecipe;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class SpiritInfusionRecipeBuilder
{
    private final Item input;
    private final int inputCount;

    private final Item output;
    private final int outputCount;

    private final List<ItemCount> spirits = Lists.newArrayList();
    private final List<ItemCount> extraItems = Lists.newArrayList();

    public SpiritInfusionRecipeBuilder(Item input, int inputCount, Item output, int outputCount)
    {
        this.input = input;
        this.inputCount = inputCount;
        this.output = output;
        this.outputCount = outputCount;
    }
    public SpiritInfusionRecipeBuilder addExtraItem(Item item, int count)
    {
        extraItems.add(new ItemCount(item, count));
        return this;
    }
    public SpiritInfusionRecipeBuilder addSpirit(MalumSpiritType type, int count)
    {
        spirits.add(new ItemCount(type.splinterItem(), count));
        return this;
    }
    public void build(Consumer<IFinishedRecipe> consumerIn, String recipeName)
    {
        build(consumerIn, MalumHelper.prefix("spirit_infusion/" + recipeName));
    }
    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id)
    {
        consumerIn.accept(new SpiritInfusionRecipeBuilder.Result(id, input, inputCount, output, outputCount, spirits, extraItems));
    }

    public static class Result implements IFinishedRecipe
    {
        private final ResourceLocation id;

        private final Item input;
        private final int inputCount;

        private final Item output;
        private final int outputCount;

        private final List<ItemCount> spirits;
        private final List<ItemCount> extraItems;


        public Result(ResourceLocation id, Item input, int inputCount, Item output, int outputCount, List<ItemCount> spirits, List<ItemCount> extraItems)
        {
            this.id = id;
            this.input = input;
            this.inputCount = inputCount;
            this.output = output;
            this.outputCount = outputCount;
            this.spirits = spirits;
            this.extraItems = extraItems;
        }

        @Override
        public void serialize(JsonObject json)
        {
            JsonObject inputObject = new JsonObject();
            inputObject.addProperty("item", ForgeRegistries.ITEMS.getKey(input).toString());
            if (outputCount > 1)
            {
                inputObject.addProperty("count", inputCount);
            }

            JsonObject outputObject = new JsonObject();
            outputObject.addProperty("item", ForgeRegistries.ITEMS.getKey(output).toString());
            if (outputCount > 1)
            {
                outputObject.addProperty("count", outputCount);
            }
            JsonArray extraItems = new JsonArray();
            for (ItemCount extraItem : this.extraItems)
            {
                JsonObject extraItemObject = new JsonObject();
                extraItemObject.addProperty("item", ForgeRegistries.ITEMS.getKey(extraItem.item).toString());
                if (extraItem.count > 1)
                {
                    extraItemObject.addProperty("count", extraItem.count);
                }
                extraItems.add(extraItemObject);
            }
            JsonArray spirits = new JsonArray();
            for (ItemCount extraItem : this.spirits)
            {
                JsonObject extraItemObject = new JsonObject();
                extraItemObject.addProperty("item", ForgeRegistries.ITEMS.getKey(extraItem.item).toString());
                if (extraItem.count > 1)
                {
                    extraItemObject.addProperty("count", extraItem.count);
                }
                spirits.add(extraItemObject);
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