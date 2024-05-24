package com.sammy.malum.common.recipe;

import com.google.gson.*;
import com.sammy.malum.core.systems.recipe.*;
import com.sammy.malum.registry.common.recipe.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.*;
import net.minecraft.resources.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class SpiritRepairRecipe extends AbstractSpiritListMalumRecipe {
    public static final String NAME = "spirit_repair";

    public final float durabilityPercentage;
    public final List<Item> inputs;
    public final IngredientWithCount repairMaterial;

    public SpiritRepairRecipe(ResourceLocation id, float durabilityPercentage, List<Item> inputs, IngredientWithCount repairMaterial, List<SpiritWithCount> spirits) {
        super(id, RecipeSerializerRegistry.REPAIR_RECIPE_SERIALIZER.get(), RecipeTypeRegistry.SPIRIT_REPAIR.get(), spirits);
        this.durabilityPercentage = durabilityPercentage;
        this.repairMaterial = repairMaterial;
        this.inputs = inputs;
    }


    public boolean doesInputMatch(ItemStack input) {
        return this.inputs.stream().anyMatch(i -> i.equals(input.getItem()));
    }

    public boolean doesRepairMatch(ItemStack input) {
        return this.repairMaterial.matches(input);
    }

    public static SpiritRepairRecipe getRecipe(Level level, ItemStack stack, ItemStack repairStack, List<ItemStack> spirits) {
        if (stack.isRepairable() && !stack.isDamaged()) {
            return null;
        }
        return getRecipe(level, c -> c.doesInputMatch(stack) && c.doesRepairMatch(repairStack) && c.doSpiritsMatch(spirits));
    }

    public static SpiritRepairRecipe getRecipe(Level level, Predicate<SpiritRepairRecipe> predicate) {
        return getRecipe(level, RecipeTypeRegistry.SPIRIT_REPAIR.get(), predicate);
    }

    public static List<SpiritRepairRecipe> getRecipes(Level level) {
        return getRecipes(level, RecipeTypeRegistry.SPIRIT_REPAIR.get());
    }

    public static ItemStack getRepairRecipeOutput(ItemStack input) {
        return input.getItem() instanceof IRepairOutputOverride ? new ItemStack(((IRepairOutputOverride) input.getItem()).overrideRepairResult(), input.getCount(), input.getTag()) : input;
    }

    public interface IRepairOutputOverride {
        default Item overrideRepairResult() {
            return Items.AIR;
        }

        default boolean ignoreDuringLookup() {
            return false;
        }
    }

    public static class Serializer implements RecipeSerializer<SpiritRepairRecipe> {

        public static List<Item> REPAIRABLE;

        @Override
        public SpiritRepairRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            if (REPAIRABLE == null) {
                REPAIRABLE = BuiltInRegistries.ITEM.getEntries().stream().map(Map.Entry::getValue).filter(Item::canBeDepleted).collect(Collectors.toList());
            }
            float durabilityPercentage = json.getAsJsonPrimitive("durabilityPercentage").getAsFloat();
            String itemIdRegex = json.get("itemIdRegex").getAsString();
            String modIdRegex = json.get("modIdRegex").getAsString();
            JsonArray inputsArray = json.getAsJsonArray("inputs");
            List<Item> inputs = new ArrayList<>();
            for (JsonElement jsonElement : inputsArray) {
                Item input = BuiltInRegistries.ITEM.getValue(new ResourceLocation(jsonElement.getAsString()));
                if (input == null) {
                    continue;
                }
                inputs.add(input);
            }
            for (Item item : REPAIRABLE) {
                if (BuiltInRegistries.ITEM.getKey(item).getPath().matches(itemIdRegex)) {
                    if (!modIdRegex.equals("") && !BuiltInRegistries.ITEM.getKey(item).getNamespace().matches(modIdRegex)) {
                        continue;
                    }
                    if (item instanceof IRepairOutputOverride repairOutputOverride && repairOutputOverride.ignoreDuringLookup()) {
                        continue;
                    }
                    if (!inputs.contains(item)) {
                        inputs.add(item);
                    }
                }
            }
            if (inputs.isEmpty()) {
                return null;
            }
            JsonObject repairObject = json.getAsJsonObject("repairMaterial");
            IngredientWithCount repair = IngredientWithCount.deserialize(repairObject);

            JsonArray spiritsArray = json.getAsJsonArray("spirits");
            List<SpiritWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritsArray.size(); i++) {
                JsonObject spiritObject = spiritsArray.get(i).getAsJsonObject();
                spirits.add(SpiritWithCount.deserialize(spiritObject));
            }
            return new SpiritRepairRecipe(recipeId, durabilityPercentage, inputs, repair, spirits);
        }

        @Nullable
        @Override
        public SpiritRepairRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            float durabilityPercentage = buffer.readFloat();
            int inputCount = buffer.readInt();
            List<Item> inputs = new ArrayList<>();
            for (int i = 0; i < inputCount; i++) {
                inputs.add(buffer.readItem().getItem());
            }
            IngredientWithCount repair = IngredientWithCount.read(buffer);
            int spiritCount = buffer.readInt();
            List<SpiritWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritCount; i++) {
                spirits.add(new SpiritWithCount(buffer.readItem()));
            }
            return new SpiritRepairRecipe(recipeId, durabilityPercentage, inputs, repair, spirits);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritRepairRecipe recipe) {
            buffer.writeFloat(recipe.durabilityPercentage);
            buffer.writeInt(recipe.inputs.size());
            for (Item item : recipe.inputs) {
                buffer.writeItem(item.getDefaultInstance());
            }
            recipe.repairMaterial.write(buffer);
            buffer.writeInt(recipe.spirits.size());
            for (SpiritWithCount item : recipe.spirits) {
                buffer.writeItem(item.getStack());
            }
        }
    }
}
