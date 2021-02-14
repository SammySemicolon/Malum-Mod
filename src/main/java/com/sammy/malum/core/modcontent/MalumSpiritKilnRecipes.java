package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.recipes.MalumItemIngredient;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class MalumSpiritKilnRecipes
{
    public static final ArrayList<MalumSpiritKilnRecipe> RECIPES = new ArrayList<>();
    
    public static void init()
    {
        new MalumSpiritKilnRecipe(new MalumItemIngredient(MalumItems.UNHOLY_BLEND.get(),1), new MalumItemIngredient(MalumItems.ARCANE_GRIT.get(),1));
    }
    
    public static MalumSpiritKilnRecipe getRecipe(ItemStack stack)
    {
        for (MalumSpiritKilnRecipe recipe : RECIPES)
        {
            if (recipe.inputIngredient.item.equals(stack.getItem()))
            {
                if (stack.getCount() >= recipe.inputIngredient.count)
                {
                    return recipe;
                }
            }
        }
        return null;
    }
    
    
    public static class MalumSpiritKilnRecipe
    {
        public final MalumItemIngredient inputIngredient;
        public final MalumItemIngredient outputIngredient;
    
        public MalumSpiritKilnRecipe(MalumItemIngredient inputIngredient, MalumItemIngredient outputIngredient)
        {
            this.inputIngredient = inputIngredient;
            this.outputIngredient = outputIngredient;
            RECIPES.add(this);
        }
    }
}