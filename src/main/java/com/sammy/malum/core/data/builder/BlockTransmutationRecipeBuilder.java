package com.sammy.malum.core.data.builder;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.content.RecipeSerializerRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class BlockTransmutationRecipeBuilder
{
    private final Block input;
    private final Block output;

    public BlockTransmutationRecipeBuilder(Block input, Block output)
    {
        this.input = input;
        this.output = output;
    }
    public void build(Consumer<IFinishedRecipe> consumerIn, String recipeName)
    {
        build(consumerIn, MalumHelper.prefix("block_transmutation/" + recipeName));
    }
    public void build(Consumer<IFinishedRecipe> consumerIn)
    {
        build(consumerIn, output.getRegistryName().getPath());
    }
    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id)
    {
        consumerIn.accept(new BlockTransmutationRecipeBuilder.Result(id, input, output));
    }

    public static class Result implements IFinishedRecipe
    {
        private final ResourceLocation id;

        private final Block input;
        private final Block output;

        public Result(ResourceLocation id, Block input, Block output)
        {
            this.id = id;
            this.input = input;
            this.output = output;
        }

        @Override
        public void serialize(JsonObject json) {
            json.addProperty("input", input.getRegistryName().toString());
            json.addProperty("output", output.getRegistryName().toString());
        }

        @Override
        public ResourceLocation getID()
        {
            return id;
        }

        @Override
        public IRecipeSerializer<?> getSerializer()
        {
            return RecipeSerializerRegistry.BLOCK_TRANSMUTATION_RECIPE_SERIALIZER.get();
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