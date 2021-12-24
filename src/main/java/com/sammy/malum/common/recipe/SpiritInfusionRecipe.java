package com.sammy.malum.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.core.registry.content.RecipeSerializerRegistry;
import com.sammy.malum.core.systems.recipe.IMalumRecipe;
import com.sammy.malum.core.systems.recipe.IngredientWithCount;
import com.sammy.malum.core.systems.recipe.ItemWithCount;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SpiritInfusionRecipe extends IMalumRecipe
{
    public static final String NAME = "spirit_infusion";
    public static class Type implements RecipeType<SpiritInfusionRecipe> {
        @Override
        public String toString () {
            return MalumMod.MODID + ":" + NAME;
        }

        public static final SpiritInfusionRecipe.Type INSTANCE = new SpiritInfusionRecipe.Type();
    }
    private final ResourceLocation id;

    public final boolean retainsPrimeItem;
    public final IngredientWithCount input;

    public final ItemWithCount output;

    public final List<ItemWithCount> spirits;
    public final List<IngredientWithCount> extraItems;

    public SpiritInfusionRecipe(ResourceLocation id, boolean retainsPrimeItem, IngredientWithCount input, ItemWithCount output, List<ItemWithCount> spirits, List<IngredientWithCount> extraItems)
    {
        this.id = id;
        this.retainsPrimeItem = retainsPrimeItem;
        this.input = input;
        this.output = output;
        this.spirits = spirits;
        this.extraItems = extraItems;
    }
    @Override
    public RecipeSerializer<?> getSerializer()
    {
        return RecipeSerializerRegistry.INFUSION_RECIPE_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return Type.INSTANCE;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    public ArrayList<ItemStack> getSortedStacks(ArrayList<ItemStack> stacks)
    {
        ArrayList<ItemStack> sortedStacks = new ArrayList<>();
        for (ItemWithCount item : spirits)
        {
            for (ItemStack stack : stacks)
            {
                if (item.matches(stack))
                {
                    sortedStacks.add(stack);
                    break;
                }
            }
        }
        return sortedStacks;
    }
    public ArrayList<MalumSpiritType> getSpirits()
    {
        ArrayList<MalumSpiritType> spirits = new ArrayList<>();
        for (ItemWithCount item : this.spirits)
        {
            MalumSpiritItem spiritItem = (MalumSpiritItem) item.item;
            spirits.add(spiritItem.type);
        }
        return spirits;
    }
    public boolean doSpiritsMatch(ArrayList<ItemStack> spirits)
    {
        if (this.spirits.size() == 0)
        {
            return true;
        }
        if (this.spirits.size() != spirits.size())
        {
            return false;
        }
        ArrayList<ItemStack> sortedStacks = getSortedStacks(spirits);
        if (sortedStacks.size() < this.spirits.size())
        {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++)
        {
            ItemWithCount item = this.spirits.get(i);
            ItemStack stack = sortedStacks.get(i);
            if (!item.matches(stack))
            {
                return false;
            }
        }
        return true;
    }
    public boolean doesInputMatch(ItemStack input)
    {
        return this.input.matches(input);
    }

    public boolean doesOutputMatch(ItemStack output)
    {
        return this.output.item.equals(output.getItem());
    }

    public static SpiritInfusionRecipe getRecipeForAltar(Level level, ItemStack stack, ArrayList<ItemStack> spirits)
    {
        List<SpiritInfusionRecipe> recipes = getRecipes(level);
        for (SpiritInfusionRecipe recipe : recipes)
        {
            if (recipe.doesInputMatch(stack) && recipe.doSpiritsMatch(spirits))
            {
                return recipe;
            }
        }
        return null;
    }
    public static SpiritInfusionRecipe getRecipeForArcana(Level level, ItemStack stack)
    {
        List<SpiritInfusionRecipe> recipes = getRecipes(level);
        for (SpiritInfusionRecipe recipe : recipes)
        {
            if (recipe.doesOutputMatch(stack))
            {
                return recipe;
            }
        }
        return null;
    }
    public static List<SpiritInfusionRecipe> getRecipes(Level level)
    {
        return level.getRecipeManager().getAllRecipesFor(Type.INSTANCE);
    }
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SpiritInfusionRecipe> {

        @Override
        public SpiritInfusionRecipe fromJson(ResourceLocation recipeId, JsonObject json)
        {
            boolean retainsPrimeItem = json.getAsJsonPrimitive("retain_prime_item").getAsBoolean();
            JsonObject inputObject = json.getAsJsonObject("input");
            IngredientWithCount input = IngredientWithCount.deserialize(inputObject);

            JsonObject outputObject = json.getAsJsonObject("output");
            ItemWithCount output = ItemWithCount.deserialize(outputObject);

            JsonArray extraItemsArray = json.getAsJsonArray("extra_items");
            ArrayList<IngredientWithCount> extraItems = new ArrayList<>();
            for (int i = 0; i < extraItemsArray.size(); i++)
            {
                JsonObject extraItemObject = extraItemsArray.get(i).getAsJsonObject();
                extraItems.add(IngredientWithCount.deserialize(extraItemObject));
            }

            JsonArray spiritsArray = json.getAsJsonArray("spirits");
            ArrayList<ItemWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritsArray.size(); i++)
            {
                JsonObject spiritObject = spiritsArray.get(i).getAsJsonObject();
                spirits.add(ItemWithCount.deserialize(spiritObject));
            }
            if (spirits.isEmpty())
            {
                throw new JsonSyntaxException("Spirit infusion recipes need at least 1 spirit ingredient, recipe with id: " + recipeId + " is incorrect");
            }
            return new SpiritInfusionRecipe(recipeId, retainsPrimeItem, input, output,spirits,extraItems);
        }

        @Nullable
        @Override
        public SpiritInfusionRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer)
        {
            IngredientWithCount input = IngredientWithCount.read(buffer);
            ItemStack output = buffer.readItem();
            int extraItemCount = buffer.readInt();
            ArrayList<IngredientWithCount> extraItems = new ArrayList<>();
            for (int i = 0; i < extraItemCount;i++)
            {
                extraItems.add(IngredientWithCount.read(buffer));
            }
            int spiritCount = buffer.readInt();
            ArrayList<ItemWithCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritCount;i++)
            {
                spirits.add(new ItemWithCount(buffer.readItem()));
            }
            boolean retainsPrimeItem = buffer.readBoolean();
            return new SpiritInfusionRecipe(recipeId, retainsPrimeItem, input, new ItemWithCount(output), spirits, extraItems);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SpiritInfusionRecipe recipe)
        {
            recipe.input.write(buffer);
            buffer.writeItem(recipe.output.stack());
            buffer.writeInt(recipe.extraItems.size());
            for (IngredientWithCount item : recipe.extraItems)
            {
                item.write(buffer);
            }
            buffer.writeInt(recipe.spirits.size());
            for (ItemWithCount item : recipe.spirits)
            {
                buffer.writeItem(item.stack());
            }
            buffer.writeBoolean(recipe.retainsPrimeItem);
        }
    }
}
