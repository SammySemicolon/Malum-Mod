package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

import static com.sammy.malum.core.modcontent.MalumSpiritTypes.AQUATIC_SPIRIT;
import static com.sammy.malum.core.modcontent.MalumSpiritTypes.INFERNAL_SPIRIT;

public class MalumTotemRecipes
{

    public static final ArrayList<MalumTotemRecipe> RECIPES = new ArrayList<>();

    public static void init()
    {
        new MalumTotemRecipe(new ItemIngredient(MalumItems.BRILLIANCE_SPIRIT.get(), 1), AQUATIC_SPIRIT)
                .addExtraItem(new ItemIngredient(MalumItems.ARCANE_SPIRIT.get(), 2));

        new MalumTotemRecipe(new ItemIngredient(MalumItems.TAINTED_ROCK_RUNE.get(), 1), AQUATIC_SPIRIT)
                .addExtraItem(new ItemIngredient(MalumItems.TAINTED_ROCK.get(), 8))
                .addExtraItem(new ItemIngredient(MalumItems.AQUATIC_SPIRIT.get(), 2));

        new MalumTotemRecipe(new ItemIngredient(MalumItems.TWISTED_ROCK_RUNE.get(), 1), AQUATIC_SPIRIT)
                .addExtraItem(new ItemIngredient(MalumItems.TWISTED_ROCK.get(), 8))
                .addExtraItem(new ItemIngredient(MalumItems.AQUATIC_SPIRIT.get(), 2));

        new MalumTotemRecipe(new ItemIngredient(Items.BLAZE_POWDER, 1), INFERNAL_SPIRIT)
                .addExtraItem(new ItemIngredient(Tags.Items.GUNPOWDER, 1))
                .addExtraItem(new ItemIngredient(MalumItems.BLAZING_QUARTZ.get(), 1));
    }

    public static MalumTotemRecipe getRecipe(ArrayList<ItemStack> stacks)
    {
        for (MalumTotemRecipe recipe : RECIPES)
        {
            if (recipe.matches(stacks))
            {
                return recipe;
            }
        }
        return null;
    }


    public static class MalumTotemRecipe
    {
        public ArrayList<ItemIngredient> itemIngredients;
        public ItemIngredient outputIngredient;
        public MalumSpiritType assemblyType;

        public MalumTotemRecipe(ItemIngredient outputIngredient, MalumSpiritType assemblyType)
        {
            this.outputIngredient = outputIngredient;
            this.assemblyType = assemblyType;
            this.itemIngredients = new ArrayList<>();
            RECIPES.add(this);
        }

        public MalumTotemRecipe addExtraItem(ItemIngredient ingredient)
        {
            itemIngredients.add(ingredient);
            return this;
        }

        public ArrayList<ItemStack> sortedStacks(ArrayList<ItemStack> stacks)
        {
            ArrayList<ItemStack> sortedStacks = new ArrayList<>();
            for (ItemIngredient ingredient : itemIngredients)
            {
                for (ItemStack stack : stacks)
                {
                    if (ingredient.matches(stack))
                    {
                        sortedStacks.add(stack);
                        break;
                    }
                }
            }
            return sortedStacks;
        }

        public boolean matches(ArrayList<ItemStack> stacks)
        {
            ArrayList<ItemStack> sortedStacks = sortedStacks(stacks);
            if (sortedStacks.size() < itemIngredients.size())
            {
                return false;
            }
            for (int i = 0; i < itemIngredients.size(); i++)
            {
                ItemIngredient ingredient = itemIngredients.get(i);
                ItemStack stack = sortedStacks.get(i);
                if (!ingredient.matches(stack))
                {
                    return false;
                }
            }
            return true;
        }
    }
}