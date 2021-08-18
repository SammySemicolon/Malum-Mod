package com.sammy.malum.core.mod_systems.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.item.SpiritItem;
import com.sammy.malum.core.init.MalumSpiritTypes;
import com.sammy.malum.core.mod_systems.spirit.MalumSpiritType;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
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

    public final Item input;
    public final int inputCount;

    public final Item output;
    public final int outputCount;

    public final List<ItemCount> spirits;
    public final List<ItemCount> extraItems;


    public SpiritInfusionRecipe(ResourceLocation id, Item input, int inputCount, Item output, int outputCount, List<ItemCount> spirits, List<ItemCount> extraItems)
    {
        this.id = id;
        this.input = input;
        this.inputCount = inputCount;
        this.output = output;
        this.outputCount = outputCount;
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
    public static SpiritInfusionRecipe getRecipe(World world, ItemStack stack, ArrayList<ItemStack> spirits)
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
    public static SpiritInfusionRecipe getRecipe(World world, ItemStack stack)
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
    public ArrayList<ItemStack> sortedStacks(ArrayList<ItemStack> stacks)
    {
        ArrayList<ItemStack> sortedStacks = new ArrayList<>();
        for (ItemCount item : spirits)
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
        for (ItemCount item : this.spirits)
        {
            SpiritItem spiritItem = (SpiritItem) item.item;
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
        ArrayList<ItemStack> sortedStacks = sortedStacks(spirits);
        if (sortedStacks.size() < this.spirits.size())
        {
            return false;
        }
        for (int i = 0; i < this.spirits.size(); i++)
        {
            ItemCount item = this.spirits.get(i);
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
        return input.getCount() >= inputCount && input.getItem().equals(this.input);
    }
    public boolean doesOutputMatch(ItemStack output)
    {
        return output.getItem().equals(this.output);
    }
    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SpiritInfusionRecipe> {

        @Override
        public SpiritInfusionRecipe read(ResourceLocation recipeId, JsonObject json)
        {
            JsonObject inputObject = json.getAsJsonObject("input");
            ItemStack input = ShapedRecipe.deserializeItem(inputObject);

            JsonObject outputObject = json.getAsJsonObject("output");
            ItemStack output = ShapedRecipe.deserializeItem(outputObject);

            JsonArray extraItemsArray = json.getAsJsonArray("extra_items");
            ArrayList<ItemCount> extraItems = new ArrayList<>();
            for (int i = 0; i < extraItemsArray.size(); i++)
            {
                JsonObject extraItemObject = extraItemsArray.get(i).getAsJsonObject();
                extraItems.add(new ItemCount(ShapedRecipe.deserializeItem(extraItemObject)));
            }

            JsonArray spiritsArray = json.getAsJsonArray("spirits");
            ArrayList<ItemCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritsArray.size(); i++)
            {
                JsonObject spiritObject = spiritsArray.get(i).getAsJsonObject();
                spirits.add(new ItemCount(ShapedRecipe.deserializeItem(spiritObject)));
            }

            return new SpiritInfusionRecipe(recipeId, input.getItem(), input.getCount(), output.getItem(), output.getCount(),spirits,extraItems);
        }

        @Nullable
        @Override
        public SpiritInfusionRecipe read(ResourceLocation recipeId, PacketBuffer buffer)
        {
            ItemStack input = buffer.readItemStack();
            ItemStack output = buffer.readItemStack();
            int extraItemCount = buffer.readInt();
            ArrayList<ItemCount> extraItems = new ArrayList<>();
            for (int i = 0; i < extraItemCount;i++)
            {
                extraItems.add(new ItemCount(buffer.readItemStack()));
            }
            int spiritCount = buffer.readInt();
            ArrayList<ItemCount> spirits = new ArrayList<>();
            for (int i = 0; i < spiritCount;i++)
            {
                spirits.add(new ItemCount(buffer.readItemStack()));
            }
            return new SpiritInfusionRecipe(recipeId, input.getItem(), input.getCount(), output.getItem(), output.getCount(), spirits, extraItems);
        }

        @Override
        public void write(PacketBuffer buffer, SpiritInfusionRecipe recipe)
        {
            buffer.writeItemStack(new ItemStack(recipe.input, recipe.inputCount));
            buffer.writeItemStack(new ItemStack(recipe.output, recipe.outputCount));
            buffer.writeInt(recipe.extraItems.size());
            for (ItemCount item : recipe.extraItems)
            {
                buffer.writeItemStack(item.stack());
            }
            buffer.writeInt(recipe.spirits.size());
            for (ItemCount item : recipe.spirits)
            {
                buffer.writeItemStack(item.stack());
            }
        }
    }
}
