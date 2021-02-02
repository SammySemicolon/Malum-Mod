package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritSplinterItem;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.recipes.MalumItemIngredient;
import com.sammy.malum.core.systems.recipes.MalumSpiritIngredient;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
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
        new MalumSpiritKilnRecipe(Items.DIAMOND, 1, MalumItems.SOUL_GEM.get(), 1, 2, new MalumItemIngredient(MalumItems.MAGIC_SPIRIT_SPLINTER.get(),2), new MalumItemIngredient(MalumItems.EARTH_SPIRIT_SPLINTER.get(), 1));
        new MalumSpiritKilnRecipe(MalumItems.UNHOLY_BLEND.get(), 1, MalumItems.ARCANE_GRIT.get(), 2, 1, new MalumItemIngredient(MalumItems.MAGIC_SPIRIT_SPLINTER.get(), 1), new MalumItemIngredient(MalumItems.LIFE_SPIRIT_SPLINTER.get(), 1));
        new MalumSpiritKilnRecipe(Items.GOLD_INGOT, 1, MalumItems.HALLOWED_GOLD_INGOT.get(), 1, 4, new MalumItemIngredient(MalumItems.LIFE_SPIRIT_SPLINTER.get(),2), new MalumItemIngredient(MalumItems.MAGIC_SPIRIT_SPLINTER.get(), 1), new MalumItemIngredient(MalumItems.DEATH_SPIRIT_SPLINTER.get(), 1));
        new MalumSpiritKilnRecipe(Items.IRON_INGOT, 1, MalumItems.SPIRITED_METAL_INGOT.get(), 1, 4, new MalumItemIngredient(MalumItems.EARTH_SPIRIT_SPLINTER.get(), 2), new MalumItemIngredient(MalumItems.MAGIC_SPIRIT_SPLINTER.get(), 1));
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
    
    
    public static MalumSpiritKilnRecipe getPreciseRecipe(ItemStack stack, ArrayList<ItemStack> extraItems)
    {
        for (MalumSpiritKilnRecipe recipe : INFUSING)
        {
            if (recipe.inputIngredient.item.equals(stack.getItem()))
            {
                if (stack.getCount() >= recipe.inputIngredient.count)
                {
                    if (recipe.items.size() != extraItems.size())
                    {
                        continue;
                    }
                    boolean continuePlease = false;
                    for (int i = 0; i < recipe.items.size(); i++)
                    {
                        ItemStack currentStack = extraItems.get(i);
                        MalumItemIngredient ingredient = recipe.items.get(i);
                        if (!ingredient.matches(currentStack))
                        {
                            continuePlease = true;
                            break;
                        }
                    }
                    if (continuePlease)
                    {
                        continue;
                    }
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
        public final ArrayList<MalumItemIngredient> items;
    
        public MalumSpiritKilnRecipe(Item inputItem, int inputCount, Item outputItem, int outputCount, int recipeTime, MalumItemIngredient... items) {
            this.inputIngredient = new MalumItemIngredient(inputItem, inputCount);
            this.outputIngredient = new MalumItemIngredient(outputItem, outputCount);
            this.recipeTime = recipeTime * MalumConstants.globalSpeedMultiplier;
            this.items = MalumHelper.toArrayList(items);
            if (this.items.size() > 3)
            {
                throw new ArrayStoreException("bro u deadass tweakin'");
            }
            INFUSING.add(this);
        }
    }
}