package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.recipes.MalumItemIngredient;
import com.sammy.malum.core.systems.recipes.MalumSpiritIngredient;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MalumSpiritAltarRecipes
{
    
    public static final ArrayList<MalumSpiritAltarRecipe> RECIPES = new ArrayList<>();
    
    public static void init()
    {
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.COBBLESTONE, 16), new MalumItemIngredient(MalumItems.TAINTED_ROCK.get(), 16),new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.COBBLESTONE, 16), new MalumItemIngredient(MalumItems.DARKENED_ROCK.get(), 16),new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
    
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.DIRT, 32), new MalumItemIngredient(Items.GRASS_BLOCK, 32),new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT));
    
        new MalumSpiritAltarRecipe(new MalumItemIngredient(ItemTags.LOGS, 16), new MalumItemIngredient(MalumItems.RUNEWOOD_LOG.get(), 16),new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(ItemTags.PLANKS, 64), new MalumItemIngredient(MalumItems.RUNEWOOD_PLANKS.get(), 64),new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(ItemTags.SAPLINGS, 4), new MalumItemIngredient(MalumItems.RUNEWOOD_SAPLING.get(), 4),new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.DIRT, 32), new MalumItemIngredient(MalumItems.SUN_KISSED_GRASS_BLOCK.get(), 32),new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
    
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.SOLAR_SAP_BOTTLE.get(), 1), new MalumItemIngredient(MalumItems.ELIXIR_OF_LIFE.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT, 2));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.SWEET_BERRIES, 8), new MalumItemIngredient(MalumItems.VOID_BERRIES.get(), 8),new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT, 2));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.GLOWSTONE_DUST, 4), new MalumItemIngredient(MalumItems.ORANGE_ETHER.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.FIRE_SPIRIT, 2));
    
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.GOLD_INGOT, 2), new MalumItemIngredient(MalumItems.HALLOWED_GOLD_INGOT.get(), 2),new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.FIRE_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.AIR_SPIRIT),new MalumSpiritIngredient(MalumSpiritTypes.WATER_SPIRIT));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.IRON_INGOT, 2), new MalumItemIngredient(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 2),new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT, 2),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 2));
    
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.HAY_BLOCK, 1), new MalumItemIngredient(MalumItems.POPPET.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT, 3), new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT, 3),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 3));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.GILDED_BELT.get(), 1), new MalumItemIngredient(MalumItems.POPPET_BELT.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT, 3), new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT, 3),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 3));
    
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.POPPET.get(), 1), new MalumItemIngredient(MalumItems.POPPET_OF_VENGEANCE.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT, 8), new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 2),new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT, 2));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.POPPET.get(), 1), new MalumItemIngredient(MalumItems.POPPET_OF_BLEEDING.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT, 6), new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT, 6));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.POPPET.get(), 1), new MalumItemIngredient(MalumItems.POPPET_OF_DEFIANCE.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT, 8), new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 4));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.POPPET.get(), 1), new MalumItemIngredient(MalumItems.POPPET_OF_MISFORTUNE.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 6), new MalumSpiritIngredient(MalumSpiritTypes.WATER_SPIRIT, 6));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.POPPET.get(), 1), new MalumItemIngredient(MalumItems.POPPET_OF_SAPPING.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT, 4), new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT, 4), new MalumSpiritIngredient(MalumSpiritTypes.WATER_SPIRIT, 4));
    
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.POPPET.get(), 1), new MalumItemIngredient(MalumItems.POPPET_OF_SHIELDING.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT, 8), new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 2),new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT, 2));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.POPPET.get(), 1), new MalumItemIngredient(MalumItems.POPPET_OF_BLEEDING.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT, 6), new MalumSpiritIngredient(MalumSpiritTypes.FIRE_SPIRIT, 6));
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.POPPET.get(), 1), new MalumItemIngredient(MalumItems.POPPET_OF_DEFIANCE.get(), 1),new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT, 4), new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT, 8));
    }
    
    public static MalumSpiritAltarRecipe getRecipe(ItemStack stack)
    {
        for (MalumSpiritAltarRecipe recipe : RECIPES)
        {
            if (recipe.inputIngredient.matches(stack))
            {
                if (stack.getItem().equals(recipe.outputIngredient.getItem().getItem()))
                {
                    continue;
                }
                return recipe;
            }
        }
        return null;
    }
    public static MalumSpiritAltarRecipe getRecipe(ItemStack stack, ArrayList<ItemStack> stacks)
    {
        for (MalumSpiritAltarRecipe recipe : RECIPES)
        {
            if (recipe.inputIngredient.matches(stack))
            {
                if (stack.getItem().equals(recipe.outputIngredient.getItem().getItem()))
                {
                    continue;
                }
                if (recipe.matches(stacks))
                {
                    return recipe;
                }
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
