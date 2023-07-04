package com.sammy.malum.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sammy.malum.registry.common.recipe.RecipeSerializerRegistry;
import com.sammy.malum.registry.common.recipe.RecipeTypeRegistry;
import com.sammy.malum.core.systems.recipe.SpiritWithCount;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SpiritRepairRecipe extends ILodestoneRecipe {
    public static final String NAME = "spirit_repair";

    private final ResourceLocation id;

    public final float durabilityPercentage;
    public final List<Item> inputs;
    public final IngredientWithCount repairMaterial;
    public final List<SpiritWithCount> spirits;

    public SpiritRepairRecipe(ResourceLocation id, float durabilityPercentage, List<Item> inputs, IngredientWithCount repairMaterial, List<SpiritWithCount> spirits) {
        this.id = id;
        this.durabilityPercentage = durabilityPercentage;
        this.repairMaterial = repairMaterial;
        this.inputs = inputs;
        this.spirits = spirits;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.REPAIR_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeTypeRegistry.SPIRIT_REPAIR.get();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public List<ItemStack> getSortedSpirits(List<ItemStack> stacks) {
        List<ItemStack> sortedStacks = new ArrayList<>();
        for (SpiritWithCount item : spirits) {
            for (ItemStack stack : stacks) {
                if (item.matches(stack)) {
                    sortedStacks.add(stack);
                    break;
                }
            }
        }
        return sortedStacks;
    }

    public boolean doSpiritsMatch(List<ItemStack> spirits) {
        if (this.spirits.size() == 0) {
            return true;
        }
        if (this.spirits.size() != spirits.size()) {
            return false;
        }
        List<ItemStack> sortedStacks = getSortedSpirits(spirits);
        if (sortedStacks.size() < this.spirits.size()) {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++) {
            SpiritWithCount item = this.spirits.get(i);
            ItemStack stack = sortedStacks.get(i);
            if (!item.matches(stack)) {
                return false;
            }
        }
        return true;
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
        List<SpiritRepairRecipe> recipes = getRecipes(level);
        for (SpiritRepairRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<SpiritRepairRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(RecipeTypeRegistry.SPIRIT_REPAIR.get());
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
                REPAIRABLE = ForgeRegistries.ITEMS.getEntries().stream().map(Map.Entry::getValue).filter(Item::canBeDepleted).collect(Collectors.toList());
            }
            float durabilityPercentage = json.getAsJsonPrimitive("durabilityPercentage").getAsFloat();
            String itemIdRegex = json.get("itemIdRegex").getAsString();
            String modIdRegex = json.get("modIdRegex").getAsString();
            JsonArray inputsArray = json.getAsJsonArray("inputs");
            List<Item> inputs = new ArrayList<>();
            for (JsonElement jsonElement : inputsArray) {
                Item input = ForgeRegistries.ITEMS.getValue(new ResourceLocation(jsonElement.getAsString()));
                if (input == null) {
                    continue;
                }
                inputs.add(input);
            }
            for (Item item : REPAIRABLE) {
                if (item.getRegistryName().getPath().matches(itemIdRegex)) {
                    if (!modIdRegex.equals("") && !item.getRegistryName().getNamespace().matches(modIdRegex)) {
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
