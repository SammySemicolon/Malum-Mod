package com.sammy.malum.common.recipe;

import com.google.gson.JsonObject;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.RecipeSerializerRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import team.lodestar.lodestone.systems.recipe.ILodestoneRecipe;
import team.lodestar.lodestone.systems.recipe.IngredientWithCount;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class AlterationRecipe extends ILodestoneRecipe {
    public static final String NAME = "spirit_transmutation";

    public static class Type implements RecipeType<AlterationRecipe> {
        @Override
        public String toString() {
            return MalumMod.MALUM + ":" + NAME;
        }

        public static final AlterationRecipe.Type INSTANCE = new AlterationRecipe.Type();
    }

    private final ResourceLocation id;

    public final IngredientWithCount input;
    public final IngredientWithCount modifier;
    public final ItemStack output;

    @Nullable
    public final String group;

    public AlterationRecipe(ResourceLocation id, IngredientWithCount input, IngredientWithCount modifier, ItemStack output, @Nullable String group) {
        this.id = id;
        this.input = input;
        this.modifier = modifier;
        this.output = output;
        this.group = group;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializerRegistry.SPIRIT_TRANSMUTATION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public static AlterationRecipe getRecipe(Level level, Item input, Item modifier) {
        return getRecipe(level, input.getDefaultInstance(), modifier.getDefaultInstance());
    }

    public static AlterationRecipe getRecipe(Level level, ItemStack input, ItemStack modifier) {
        return getRecipe(level, r -> r.input.matches(input) && r.modifier.matches(modifier));
    }

    public static AlterationRecipe getRecipe(Level level, Predicate<AlterationRecipe> predicate) {
        List<AlterationRecipe> recipes = getRecipes(level);
        for (AlterationRecipe recipe : recipes) {
            if (predicate.test(recipe)) {
                return recipe;
            }
        }
        return null;
    }

    public static List<AlterationRecipe> getRecipes(Level level) {
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<AlterationRecipe> {

        @Override
        public AlterationRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            IngredientWithCount input = IngredientWithCount.deserialize(json.getAsJsonObject("input"));
            IngredientWithCount modifier = IngredientWithCount.deserialize(json.getAsJsonObject("modifier"));
            ItemStack output = CraftingHelper.getItemStack(json.getAsJsonObject("output"), true);
            String group = json.has("group") ? json.get("group").getAsString() : null;
            return new AlterationRecipe(recipeId, input, modifier, output, group);
        }

        @Nullable
        @Override
        public AlterationRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            IngredientWithCount input = IngredientWithCount.read(buffer);
            IngredientWithCount modifier = IngredientWithCount.read(buffer);
            ItemStack output = buffer.readItem();
            String group = buffer.readBoolean() ? buffer.readUtf() : null;
            return new AlterationRecipe(recipeId, input, modifier, output, group);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, AlterationRecipe recipe) {
            recipe.input.write(buffer);
            recipe.modifier.write(buffer);
            buffer.writeItem(recipe.output);
            buffer.writeBoolean(recipe.group != null);
            if (recipe.group != null)
                buffer.writeUtf(recipe.group);
        }
    }
}
