package com.sammy.malum.core.data.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpiritTransmutationRecipeBuilder {
    public final List<Pair<Ingredient, ItemStack>> subRecipes = new ArrayList<>();

    public SpiritTransmutationRecipeBuilder addTransmutation(Ingredient input, ItemStack output) {
        subRecipes.add(new Pair<>(input, output));
        return this;
    }

    public SpiritTransmutationRecipeBuilder addTransmutation(RegistryObject<? extends ItemLike> input, RegistryObject<? extends ItemLike> output) {
        return addTransmutation(input.get(), output.get());
    }

    public SpiritTransmutationRecipeBuilder addTransmutation(Ingredient input, Item output) {
        return addTransmutation(input, new ItemStack(output));
    }

    public SpiritTransmutationRecipeBuilder addTransmutation(ItemLike input, ItemStack output) {
        return addTransmutation(Ingredient.of(input), output);
    }

    public SpiritTransmutationRecipeBuilder addTransmutation(ItemStack input, ItemStack output) {
        return addTransmutation(Ingredient.of(input), output);
    }

    public SpiritTransmutationRecipeBuilder addTransmutation(ItemLike input, ItemLike output) {
        return addTransmutation(Ingredient.of(input), new ItemStack(output));
    }

    public SpiritTransmutationRecipeBuilder addTransmutation(ItemStack input, ItemLike output) {
        return addTransmutation(Ingredient.of(input), new ItemStack(output));
    }


    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("spirit_transmutation/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, RecipeBuilder.getDefaultRecipeId(subRecipes.get(0).getSecond().getItem()).getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id));
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public Result(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray array = new JsonArray();
            for (var subRecipe : subRecipes) {
                JsonObject object = new JsonObject();
                object.add("input", subRecipe.getFirst().toJson());
                ItemStack output = subRecipe.getSecond();
                JsonElement outputObject = Ingredient.of(output).toJson();
                if (output.getCount() != 1) {
                    outputObject.getAsJsonObject().addProperty("count", output.getCount());
                }
                object.add("output", outputObject);
                array.add(object);
            }

            json.add("recipes", array);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER.get();
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
