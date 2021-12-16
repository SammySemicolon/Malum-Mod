package com.sammy.malum.core.data.builder;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.content.RecipeSerializerRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

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
    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName)
    {
        build(consumerIn, MalumHelper.prefix("block_transmutation/" + recipeName));
    }
    public void build(Consumer<FinishedRecipe> consumerIn)
    {
        build(consumerIn, output.getRegistryName().getPath());
    }
    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id)
    {
        consumerIn.accept(new BlockTransmutationRecipeBuilder.Result(id, input, output));
    }

    public static class Result implements FinishedRecipe
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
        public void serializeRecipeData(JsonObject json) {
            json.addProperty("input", input.getRegistryName().toString());
            json.addProperty("output", output.getRegistryName().toString());
        }

        @Override
        public ResourceLocation getId()
        {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType()
        {
            return RecipeSerializerRegistry.BLOCK_TRANSMUTATION_RECIPE_SERIALIZER.get();
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