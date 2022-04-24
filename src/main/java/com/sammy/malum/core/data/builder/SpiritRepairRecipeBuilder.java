package com.sammy.malum.core.data.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.ortus.systems.recipe.IngredientWithCount;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SpiritRepairRecipeBuilder {

    public final String inputLookup;
    public final float durabilityPercentage;
    public final ArrayList<Item> inputs = new ArrayList<>();
    public final IngredientWithCount repairMaterial;
    public final ArrayList<SpiritWithCount> spirits = new ArrayList<>();

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
        spirits.add(new SpiritWithCount(type, count));
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String recipeName) {
        build(consumerIn, MalumMod.prefix("spirit_crucible/repair/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new SpiritRepairRecipeBuilder.Result(id, inputLookup, durabilityPercentage, inputs, repairMaterial, spirits));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, inputs.get(0).getRegistryName().getPath());
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public final String inputLookup;
        public final float durabilityPercentage;
        public final ArrayList<Item> input;
        public final IngredientWithCount repairMaterial;
        public final ArrayList<SpiritWithCount> spirits;


        public Result(ResourceLocation id, String inputLookup, float durabilityPercentage, ArrayList<Item> input, IngredientWithCount repairMaterial, ArrayList<SpiritWithCount> spirits) {
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
                inputs.add(item.getRegistryName().toString());
            }
            JsonArray spirits = new JsonArray();
            for (SpiritWithCount spirit : this.spirits) {
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