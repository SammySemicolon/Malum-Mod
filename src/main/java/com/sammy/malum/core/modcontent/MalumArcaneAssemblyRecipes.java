package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;

public class MalumArcaneAssemblyRecipes
{
    public static final ArrayList<MalumArcaneAssemblerRecipe> RECIPES = new ArrayList<>();

    public static void init()
    {
        new MalumArcaneAssemblerRecipe(
                new SimpleItemIngredient(Tags.Items.GEMS_LAPIS),
                new ItemIngredient(MalumItems.CONFINED_BRILLIANCE.get(), 2));

        new MalumArcaneAssemblerRecipe(
                new SimpleItemIngredient(Tags.Items.CROPS_WHEAT),
                new ItemIngredient(MalumItems.CURSED_STRAND.get(), 1));

        new MalumArcaneAssemblerRecipe(
                new SimpleItemIngredient(Items.PHANTOM_MEMBRANE),
                new ItemIngredient(MalumItems.ECTOPLASM.get(), 2));
    }

    public static MalumArcaneAssemblerRecipe getRecipe(ItemStack stack)
    {
        for (MalumArcaneAssemblerRecipe recipe : RECIPES)
        {
            if (recipe.inputIngredient.matches(stack))
            {
                return recipe;
            }
        }
        return null;
    }
    public static class MalumArcaneAssemblerRecipe
    {
        public ItemIngredient inputIngredient;
        public ItemIngredient outputIngredient;

        public MalumArcaneAssemblerRecipe(ItemIngredient inputIngredient, ItemIngredient outputIngredient)
        {
            this.inputIngredient = inputIngredient;
            this.outputIngredient = outputIngredient;
            RECIPES.add(this);
        }
    }
}