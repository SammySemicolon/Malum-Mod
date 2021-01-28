package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.recipes.MalumItemIngredient;
import com.sammy.malum.core.systems.recipes.MalumSpiritIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

import static com.sammy.malum.core.systems.recipes.MalumSpiritIngredient.simpleIngredient;

public class MalumSpiritKilnRecipes
{
    public static final ArrayList<MalumSpiritKilnRecipe> INFUSING = new ArrayList<>();
    
    public static void init()
    {
        new MalumSpiritKilnRecipe(Items.DIAMOND, 2, MalumItems.SOUL_GEM.get(), 1, 2, simpleIngredient(MalumSpiritTypes.MAGIC_SPIRIT,2), simpleIngredient(MalumSpiritTypes.EARTH_SPIRIT));
        new MalumSpiritKilnRecipe(Items.GOLD_INGOT, 1, MalumItems.HALLOWED_GOLD_INGOT.get(), 1, 4, simpleIngredient(MalumSpiritTypes.LIFE_SPIRIT,2), simpleIngredient(MalumSpiritTypes.MAGIC_SPIRIT), simpleIngredient(MalumSpiritTypes.DEATH_SPIRIT));
        new MalumSpiritKilnRecipe(Items.IRON_INGOT, 1, MalumItems.SPIRITED_METAL_INGOT.get(), 1, 4, simpleIngredient(MalumSpiritTypes.EARTH_SPIRIT, 2), simpleIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
    }
    
    public static MalumSpiritKilnRecipe getRecipe(ItemStack stack)
    {
        for (MalumSpiritKilnRecipe recipe : INFUSING)
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
        public final int recipeTime;
        public boolean hasAlternatives;
        public final ArrayList<MalumSpiritIngredient> spirits;
    
        public MalumSpiritKilnRecipe(Item inputItem, int inputCount, Item outputItem, int outputCount, int recipeTime, MalumSpiritIngredient... spirits) {
            this.inputIngredient = new MalumItemIngredient(inputItem, inputCount);
            this.outputIngredient = new MalumItemIngredient(outputItem, outputCount);
            this.recipeTime = recipeTime * MalumConstants.globalSpeedMultiplier;
            this.spirits = MalumHelper.toArrayList(spirits);
            for (MalumSpiritKilnRecipe recipe : INFUSING)
            {
                if (recipe.inputIngredient.item.equals(this.inputIngredient.item))
                {
                    recipe.hasAlternatives = true;
                    this.hasAlternatives = true;
                }
            }
            INFUSING.add(this);
        }
    }
}