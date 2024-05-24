package com.sammy.malum.data.recipe.builder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SpiritTransmutationRecipeBuilder {
    private final Ingredient ingredient;
    private final ItemStack output;

    @Nullable
    private String group = null;

    public SpiritTransmutationRecipeBuilder(Ingredient input, ItemStack output) {
        ingredient = input;
        this.output = output;
    }

    public SpiritTransmutationRecipeBuilder(RegistryObject<? extends ItemLike> input, RegistryObject<? extends ItemLike> output) {
        this(input.get(), output.get());
    }

    public SpiritTransmutationRecipeBuilder(Ingredient input, Item output) {
        this(input, new ItemStack(output));
    }

    public SpiritTransmutationRecipeBuilder(ItemLike input, ItemStack output) {
        this(Ingredient.of(input), output);
    }

    public SpiritTransmutationRecipeBuilder(ItemStack input, ItemStack output) {
        this(Ingredient.of(input), output);
    }

    public SpiritTransmutationRecipeBuilder(ItemLike input, ItemLike output) {
        this(Ingredient.of(input), new ItemStack(output));
    }

    public SpiritTransmutationRecipeBuilder(ItemStack input, ItemLike output) {
        this(Ingredient.of(input), new ItemStack(output));
    }

    public SpiritTransmutationRecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.malumPath("spirit_transmutation/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, RecipeBuilder.getDefaultRecipeId(output.getItem()).getPath());
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
            json.add("input", ingredient.toJson());
            JsonElement outputObject = Ingredient.of(output).toJson();
            if (output.getCount() != 1) {
                outputObject.getAsJsonObject().addProperty("count", output.getCount());
            }
            json.add("output", outputObject);
            if (group != null)
                json.addProperty("group", group);
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
