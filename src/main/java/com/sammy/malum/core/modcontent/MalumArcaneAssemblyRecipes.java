package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MalumArcaneAssemblyRecipes
{
    public static final ArrayList<MalumArcaneAssemblerRecipe> RECIPES = new ArrayList<>();

    public static void init()
    {
        new MalumArcaneAssemblerRecipe(
                new SimpleItemIngredient(Tags.Items.GEMS_LAPIS),
                new ItemIngredient(MalumItems.CONFINED_BRILLIANCE.get(), 4),
                new SimpleItemIngredient(MalumItems.ARCANE_SPIRIT.get()));

        new MalumArcaneAssemblerRecipe(
                new SimpleItemIngredient(Items.GLASS_BOTTLE),
                new ItemIngredient(Items.EXPERIENCE_BOTTLE, 1),
                new SimpleItemIngredient(MalumItems.CONFINED_BRILLIANCE.get()));

    }

    public static MalumArcaneAssemblerRecipe getRecipe(ItemStack stack)
    {
        for (MalumArcaneAssemblerRecipe recipe : RECIPES)
        {
            if (recipe.primeIngredient.matches(stack))
            {
                return recipe;
            }
        }
        return null;
    }

    public static class MalumArcaneAssemblerRecipe
    {
        public SimpleItemIngredient primeIngredient;
        public ArrayList<SimpleItemIngredient> extraIngredients;
        public ItemIngredient outputIngredient;

        public MalumArcaneAssemblerRecipe(SimpleItemIngredient primeIngredient, ItemIngredient outputIngredient, SimpleItemIngredient... extraIngredients)
        {
            this.primeIngredient = primeIngredient;
            this.outputIngredient = outputIngredient;
            this.extraIngredients = MalumHelper.toArrayList(extraIngredients);
            RECIPES.add(this);
        }
    }
}