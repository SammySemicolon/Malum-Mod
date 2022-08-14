package com.sammy.malum.core.data.builder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpiritRepairRecipeBuilder {

    public final String inputLookup;
    public final float durabilityPercentage;
    public final List<Item> inputs = new ArrayList<>();
    public final IngredientWithCount repairMaterial;
    public final List<SpiritWithCount> spirits = new ArrayList<>();

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
        build(consumerIn, MalumMod.malumPath("spirit_crucible/repair/" + recipeName));
    }

    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new SpiritRepairRecipeBuilder.Result(id));
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        build(consumerIn, inputs.get(0).getRegistryName().getPath());
    }

    public class Result implements FinishedRecipe {
        private final ResourceLocation id;

        public Result(ResourceLocation id) {
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            JsonArray inputsJson = new JsonArray();
            for (Item item : inputs) {
                inputsJson.add(item.getRegistryName().toString());
            }
            JsonArray spiritsJson = new JsonArray();
            for (SpiritWithCount spirit : spirits) {
                spiritsJson.add(spirit.serialize());
            }

            if (inputLookup != null) {
                json.addProperty("inputLookup", inputLookup);
            }
            json.addProperty("durabilityPercentage", durabilityPercentage);
            json.add("inputs", inputsJson);
            json.add("repairMaterial", repairMaterial.serialize());
            json.add("spirits", spiritsJson);
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
