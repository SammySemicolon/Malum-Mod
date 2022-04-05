package com.sammy.malum.core.data.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpiritRepairRecipeBuilder {

    public final String inputLookup;
    public final float durabilityPercentage;
    public final ArrayList<Item> inputs = new ArrayList<>();
    public final IngredientWithCount repairMaterial;
    public final ArrayList<ItemWithCount> spirits = new ArrayList<>();

    public SpiritRepairRecipeBuilder(String inputLookup, float durabilityPercentage, Ingredient repairMaterial, int repairMaterialCount) {
        this.inputLookup = inputLookup;
        this.durabilityPercentage = durabilityPercentage;
        this.repairMaterial = new IngredientWithCount(repairMaterial, repairMaterialCount);
    }

    public SpiritRepairRecipeBuilder(float durabilityPercentage, Ingredient repairMaterial, int repairMaterialCount) {
        this.inputLookup = "none";
        this.durabilityPercentage = durabilityPercentage;
        this.repairMaterial = new IngredientWithCount(repairMaterial, repairMaterialCount);
    }

    public SpiritRepairRecipeBuilder addItem(Item item) {
        inputs.add(item);
        return this;
    }

    public SpiritRepairRecipeBuilder addSpirit(MalumSpiritType type, int count) {
        spirits.add(new ItemWithCount(type.getSplinterItem(), count));
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, DataHelper.prefix("spirit_crucible/repair/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new SpiritRepairRecipeBuilder.Result(id, inputLookup, durabilityPercentage, inputs, repairMaterial, spirits));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public final String inputLookup;
        public final float durabilityPercentage;
        public final ArrayList<Item> input;
        public final IngredientWithCount repairMaterial;
        public final ArrayList<ItemWithCount> spirits;


        public Result(ResourceLocation id, String inputLookup, float durabilityPercentage, ArrayList<Item> input, IngredientWithCount repairMaterial, ArrayList<ItemWithCount> spirits) {
            this.id = id;
            this.inputLookup = inputLookup;
            this.durabilityPercentage = durabilityPercentage;
            this.input = input;
            this.repairMaterial = repairMaterial;
            this.spirits = spirits;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray inputs = new JsonArray();
            for (Item item : this.input) {
                inputs.add(item.toString());
            }
            JsonArray spirits = new JsonArray();
            for (ItemWithCount spirit : this.spirits) {
                spirits.add(spirit.serialize());
            }

            if (inputLookup != null) {
                json.addProperty("inputLookup", inputLookup);
            }
            json.addProperty("durabilityPercentage", durabilityPercentage);
            json.add("inputs", inputs);
            json.add("repairMaterial", repairMaterial.serialize());
            json.add("spirits", spirits);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return RecipeSerializerRegistry.REPAIR_RECIPE_SERIALIZER.get();
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