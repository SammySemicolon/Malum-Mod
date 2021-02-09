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
import net.minecraft.tags.ItemTags;

import java.util.ArrayList;

import static com.sammy.malum.core.systems.recipes.MalumSpiritIngredient.simpleIngredient;

public class MalumSpiritKilnRecipes
{
    public static final ArrayList<MalumSpiritKilnRecipe> INFUSING = new ArrayList<>();
    
    public static void init()
    {
        new MalumSpiritKilnRecipe(new MalumItemIngredient(MalumItems.UNHOLY_BLEND.get(),1), new MalumItemIngredient(MalumItems.ARCANE_GRIT.get(),1));
        new MalumSpiritKilnRecipe(new MalumItemIngredient(Items.STONE,1), new MalumItemIngredient(MalumItems.TAINTED_ROCK.get(),1));
        new MalumSpiritKilnRecipe(new MalumItemIngredient(Items.BLACKSTONE,1), new MalumItemIngredient(MalumItems.DARKENED_ROCK.get(),1));
        new MalumSpiritKilnRecipe(new MalumItemIngredient(ItemTags.LOGS,1), new MalumItemIngredient(MalumItems.RUNEWOOD_LOG.get(),1));
        new MalumSpiritKilnRecipe(new MalumItemIngredient(Items.GRASS_BLOCK,1), new MalumItemIngredient(MalumItems.SUN_KISSED_GRASS_BLOCK.get(),1));
        new MalumSpiritKilnRecipe(new MalumItemIngredient(Items.IRON_INGOT,1), new MalumItemIngredient(MalumItems.SPIRITED_METAL_INGOT.get(),1));
        new MalumSpiritKilnRecipe(new MalumItemIngredient(Items.GOLD_INGOT,1), new MalumItemIngredient(MalumItems.HALLOWED_GOLD_INGOT.get(),1));
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
    
        public MalumSpiritKilnRecipe(MalumItemIngredient inputIngredient, MalumItemIngredient outputIngredient)
        {
            this.inputIngredient = inputIngredient;
            this.outputIngredient = outputIngredient;
            INFUSING.add(this);
        }
    }
}