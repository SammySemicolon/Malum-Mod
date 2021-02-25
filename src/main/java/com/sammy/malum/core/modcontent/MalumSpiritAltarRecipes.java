package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.recipes.MalumItemIngredient;
import com.sammy.malum.core.systems.recipes.MalumSpiritIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MalumSpiritAltarRecipes
{
    
    public static final ArrayList<MalumSpiritAltarRecipe> RECIPES = new ArrayList<>();
    
    public static void init()
    {
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.COBBLESTONE, 16), new MalumItemIngredient(MalumItems.TAINTED_ROCK.get(), 16),new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.COBBLESTONE, 16), new MalumItemIngredient(MalumItems.DARKENED_ROCK.get(), 16),new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
    }
    
    public static MalumSpiritAltarRecipe getRecipe(ItemStack stack)
    {
        for (MalumSpiritAltarRecipe recipe : RECIPES)
        {
            if (recipe.inputIngredient.matches(stack))
            {
                return recipe;
            }
        }
        return null;
    }
    
    
    public static class MalumSpiritAltarRecipe
    {
        public final MalumItemIngredient inputIngredient;
        public final MalumItemIngredient outputIngredient;
        public final ArrayList<MalumSpiritIngredient> spiritIngredients;
        public MalumSpiritAltarRecipe(MalumItemIngredient inputIngredient, MalumItemIngredient outputIngredient, MalumSpiritIngredient... malumSpiritIngredients)
        {
            this.inputIngredient = inputIngredient;
            this.outputIngredient = outputIngredient;
            this.spiritIngredients = MalumHelper.toArrayList(malumSpiritIngredients);
            RECIPES.add(this);
        }
        public ArrayList<ItemStack> sortedStacks(ArrayList<ItemStack> stacks)
        {
            ArrayList<ItemStack> sortedStacks = new ArrayList<>();
            for (MalumSpiritIngredient ingredient : spiritIngredients)
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
            if (spiritIngredients.size() == 0)
            {
                return true;
            }
            ArrayList<ItemStack> sortedStacks = sortedStacks(stacks);
            if (sortedStacks.size() < spiritIngredients.size())
            {
                return false;
            }
            for (int i = 0; i < spiritIngredients.size(); i++)
            {
                MalumSpiritIngredient ingredient = spiritIngredients.get(i);
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
