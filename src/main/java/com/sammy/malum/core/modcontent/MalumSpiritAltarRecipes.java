package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.recipes.MalumItemIngredient;
import com.sammy.malum.core.systems.recipes.MalumSpiritIngredient;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MalumSpiritAltarRecipes
{

    public static final ArrayList<MalumSpiritAltarRecipe> RECIPES = new ArrayList<>();

    public static void init()
    {
        //tainted rock
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.COBBLESTONE, 16), new MalumItemIngredient(MalumItems.TAINTED_ROCK.get(), 16))
                .addSpirit(new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT))
                .addSpirit(new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));

        //twisted rock
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.COBBLESTONE, 16), new MalumItemIngredient(MalumItems.TWISTED_ROCK.get(), 16))
                .addSpirit(new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT))
                .addSpirit(new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));

        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.DUSTS_GLOWSTONE, 2), new MalumItemIngredient(MalumItems.YELLOW_ETHER.get(), 4))
                .addSpirit(new MalumSpiritIngredient(MalumSpiritTypes.FIRE_SPIRIT))
                .addSpirit(new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 2))
                .addExtraItem(new MalumItemIngredient(MalumItems.BLAZING_QUARTZ.get(), 1));

/*
        //tainted rock
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.COBBLESTONE, 16),
                                   new MalumItemIngredient(MalumItems.TAINTED_ROCK.get(), 16),
                                   new MalumSpiritIngredient(MalumSpiritTypes.LIFE_SPIRIT),
                                   new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
        //twisted rock
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Tags.Items.COBBLESTONE, 16),
                                   new MalumItemIngredient(MalumItems.TWISTED_ROCK.get(), 16),
                                   new MalumSpiritIngredient(MalumSpiritTypes.DEATH_SPIRIT),
                                   new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT));
        //ether
        new MalumSpiritAltarRecipe(new MalumItemIngredient(MalumItems.BLAZING_QUARTZ.get(), 2),
                new MalumItemIngredient(MalumItems.PINK_ETHER.get(), 1),
                new MalumSpiritIngredient(MalumSpiritTypes.FIRE_SPIRIT, 1),
                new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 2));

        //hex ash
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.REDSTONE, 4),
                new MalumItemIngredient(MalumItems.HEX_ASH.get(), 4),
                new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 1),
                new MalumSpiritIngredient(MalumSpiritTypes.EARTH_SPIRIT, 1),
                new MalumSpiritIngredient(MalumSpiritTypes.FIRE_SPIRIT, 1),
                new MalumSpiritIngredient(MalumSpiritTypes.AIR_SPIRIT, 1),
                new MalumSpiritIngredient(MalumSpiritTypes.WATER_SPIRIT, 1));

        //arcane architecture
        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.GRANITE, 16),
                new MalumItemIngredient(MalumItems.CLEANSED_ROCK.get(), 16),
                new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 1));

        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.DIORITE, 16),
                new MalumItemIngredient(MalumItems.PURIFIED_ROCK.get(), 16),
                new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 1));

        new MalumSpiritAltarRecipe(new MalumItemIngredient(Items.ANDESITE, 16),
                new MalumItemIngredient(MalumItems.ERODED_ROCK.get(), 16),
                new MalumSpiritIngredient(MalumSpiritTypes.MAGIC_SPIRIT, 1));

*/
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
        public MalumItemIngredient inputIngredient;
        public ArrayList<MalumItemIngredient> extraItemIngredients;
        public MalumItemIngredient outputIngredient;
        public ArrayList<MalumSpiritIngredient> spiritIngredients;

        public MalumSpiritAltarRecipe(MalumItemIngredient inputIngredient, MalumItemIngredient outputIngredient)
        {
            this.inputIngredient = inputIngredient;
            this.outputIngredient = outputIngredient;
            this.extraItemIngredients = new ArrayList<>();
            this.spiritIngredients = new ArrayList<>();
            RECIPES.add(this);
        }

        public MalumSpiritAltarRecipe addExtraItem(MalumItemIngredient ingredient)
        {
            extraItemIngredients.add(ingredient);
            return this;
        }

        public MalumSpiritAltarRecipe addSpirit(MalumSpiritIngredient ingredient)
        {
            spiritIngredients.add(ingredient);
            return this;
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