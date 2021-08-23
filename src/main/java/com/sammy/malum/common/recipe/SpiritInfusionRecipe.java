package com.sammy.malum.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.SpiritItem;
import com.sammy.malum.core.mod_systems.recipe.IMalumRecipe;
import com.sammy.malum.core.mod_systems.recipe.IngredientWithCount;
import com.sammy.malum.core.mod_systems.recipe.ItemWithCount;
import com.sammy.malum.core.mod_systems.recipe.RecipeType;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class SpiritInfusionRecipe extends IMalumRecipe
{
    public static final ResourceLocation NAME = MalumHelper.prefix("spirit_infusion");
    public static final Serializer SERIALIZER = new Serializer();
    public static final IRecipeType<SpiritInfusionRecipe> RECIPE_TYPE = new RecipeType<>();
    private final ResourceLocation id;

    public final IngredientWithCount input;

    public final ItemWithCount output;

    public final List<ItemWithCount> spirits;
    public final List<IngredientWithCount> extraItems;

    public SpiritInfusionRecipe(ResourceLocation id, IngredientWithCount input, ItemWithCount output, List<ItemWithCount> spirits, List<IngredientWithCount> extraItems)
    {
        this.id = id;
        this.input = input;
        this.output = output;
        this.spirits = spirits;
        this.extraItems = extraItems;
    }
    @Override
    public IRecipeSerializer<?> getSerializer()
    {
        return null;
    }

    @Override
    public IRecipeType<?> getType()
    {
        return Registry.RECIPE_TYPE.getOptional(NAME).get();
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }
    public ArrayList<ItemStack> sortedStacks(ArrayList<ItemStack> stacks)
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
    public ArrayList<MalumSpiritType> spirits()
    {
        ArrayList<MalumSpiritType> spirits = new ArrayList<>();
        for (ItemWithCount item : this.spirits)
        {
            SpiritItem spiritItem = (SpiritItem) item.item;
            spirits.add(spiritItem.type);
        }
        return spirits;
    }
    public static SpiritInfusionRecipe getRecipeForAltar(World world, ItemStack stack, ArrayList<ItemStack> spirits)
    {
        List<SpiritInfusionRecipe> recipes = getRecipes(world);
        for (SpiritInfusionRecipe recipe : recipes)
        {
            if (recipe.doesInputMatch(stack) && recipe.doSpiritsMatch(spirits))
            {
                return recipe;
            }
        }
        return null;
    }
    public static SpiritInfusionRecipe getRecipeForArcana(World world, ItemStack stack)
    {
        List<SpiritInfusionRecipe> recipes = getRecipes(world);
        for (SpiritInfusionRecipe recipe : recipes)
        {
            if (recipe.doesOutputMatch(stack))
            {
                return recipe;
            }
        }
        return null;
    }
    public static List<SpiritInfusionRecipe> getRecipes(World world)
    {
        return world.getRecipeManager().getRecipesForType(RECIPE_TYPE);
    }
    public boolean doSpiritsMatch(ArrayList<ItemStack> spirits)
    {
        if (this.spirits.size() == 0)
        {
            return true;
        }
        ArrayList<ItemStack> sortedStacks = sortedStacks(spirits);
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
    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SpiritInfusionRecipe> {

        @Override
        public SpiritInfusionRecipe read(ResourceLocation recipeId, JsonObject json)
        {
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
            return new SpiritInfusionRecipe(recipeId, input, output,spirits,extraItems);
        }

        @Nullable
        @Override
        public SpiritInfusionRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {
            IngredientWithCount input = IngredientWithCount.read(buffer);
            ItemStack output = buffer.readItemStack();
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
                spirits.add(new ItemWithCount(buffer.readItemStack()));
            }
            return new SpiritInfusionRecipe(recipeId, input, new ItemWithCount(output), spirits, extraItems);
        }

        @Override
        public void write(PacketBuffer buffer, SpiritInfusionRecipe recipe)
        {
            recipe.input.write(buffer);
            buffer.writeItemStack(recipe.output.stack());
            buffer.writeInt(recipe.extraItems.size());
            for (IngredientWithCount item : recipe.extraItems)
            {
                item.write(buffer);
            }
            buffer.writeInt(recipe.spirits.size());
            for (ItemWithCount item : recipe.spirits)
            {
                buffer.writeItemStack(item.stack());
            }
        }
    }
}
