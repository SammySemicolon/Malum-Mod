package com.sammy.malum.core.mod_content;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.recipe.ItemIngredient;
import com.sammy.malum.core.mod_systems.recipe.SimpleItemIngredient;
import com.sammy.malum.core.mod_systems.recipe.SpiritIngredient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;

public class MalumSpiritAltarRecipes
{

    public static final ArrayList<MalumSpiritAltarRecipe> RECIPES = new ArrayList<>();

    public static void init()
    {
        //hex ash
        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.GUNPOWDER, 1), new ItemIngredient(MalumItems.HEX_ASH.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        //tainted rock
        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.COBBLESTONE, 16), new ItemIngredient(MalumItems.TAINTED_ROCK.get(), 16))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.SACRED_SPIRIT))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        //twisted rock
        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.COBBLESTONE, 16), new ItemIngredient(MalumItems.TWISTED_ROCK.get(), 16))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        //ether
        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.DUSTS_GLOWSTONE, 4), new ItemIngredient(MalumItems.ETHER.get(), 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.INFERNAL_SPIRIT, 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT))
                .addExtraItem(new SimpleItemIngredient(MalumItems.BLAZING_QUARTZ.get()))
                .addExtraItem(new SimpleItemIngredient(Items.BLAZE_POWDER));

        //sac dag
        new MalumSpiritAltarRecipe(new ItemIngredient(Items.IRON_SWORD, 1), new ItemIngredient(MalumItems.PITHING_NEEDLE.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.EARTHEN_SPIRIT, 8))
                .addExtraItem(new SimpleItemIngredient(MalumItems.TAINTED_ROCK.get()))
                .addExtraItem(new SimpleItemIngredient(MalumItems.RUNEWOOD_PLANKS.get()))
                .addExtraItem(new SimpleItemIngredient(MalumItems.SOULSTONE.get()));

        //arcane architecture
        new MalumSpiritAltarRecipe(new ItemIngredient(Items.GRANITE, 16), new ItemIngredient(MalumItems.CLEANSED_ROCK.get(), 16))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.EARTHEN_SPIRIT))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        new MalumSpiritAltarRecipe(new ItemIngredient(Items.DIORITE, 16), new ItemIngredient(MalumItems.PURIFIED_ROCK.get(), 16))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.EARTHEN_SPIRIT))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        new MalumSpiritAltarRecipe(new ItemIngredient(Items.ANDESITE, 16), new ItemIngredient(MalumItems.ERODED_ROCK.get(), 16))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.EARTHEN_SPIRIT))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.INGOTS_IRON, 1), new ItemIngredient(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT, 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT, 1))
                .addExtraItem(new SimpleItemIngredient(Items.SOUL_SAND))
                .addExtraItem(new SimpleItemIngredient(MalumItems.SOULSTONE.get()));

        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.INGOTS_GOLD, 1), new ItemIngredient(MalumItems.HALLOWED_GOLD_INGOT.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT, 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.SACRED_SPIRIT, 1))
                .addExtraItem(new SimpleItemIngredient(Items.QUARTZ))
                .addExtraItem(new SimpleItemIngredient(MalumItems.SOULSTONE.get()));
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
                if (recipe.spiritIngredients.size() != stacks.size())
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
        public ItemIngredient inputIngredient;
        public ArrayList<SimpleItemIngredient> extraItemIngredients;
        public ItemIngredient outputIngredient;
        public ArrayList<SpiritIngredient> spiritIngredients;

        public MalumSpiritAltarRecipe(ItemIngredient inputIngredient, ItemIngredient outputIngredient)
        {
            this.inputIngredient = inputIngredient;
            this.outputIngredient = outputIngredient;
            this.extraItemIngredients = new ArrayList<>();
            this.spiritIngredients = new ArrayList<>();
            RECIPES.add(this);
        }

        public MalumSpiritAltarRecipe addExtraItem(SimpleItemIngredient ingredient)
        {
            extraItemIngredients.add(ingredient);
            return this;
        }

        public MalumSpiritAltarRecipe addSpirit(SpiritIngredient ingredient)
        {
            spiritIngredients.add(ingredient);
            return this;
        }

        public ArrayList<ItemStack> sortedStacks(ArrayList<ItemStack> stacks)
        {
            ArrayList<ItemStack> sortedStacks = new ArrayList<>();
            for (SpiritIngredient ingredient : spiritIngredients)
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
                SpiritIngredient ingredient = spiritIngredients.get(i);
                ItemStack stack = sortedStacks.get(i);
                if (!ingredient.matches(stack))
                {
                    return false;
                }
            }
            return true;
        }
        public ArrayList<ItemStack> spiritStacks()
        {
            ArrayList<ItemStack> stacks = new ArrayList<>();
            for (SpiritIngredient spiritIngredient : spiritIngredients)
            {
                stacks.add(spiritIngredient.getItem());
            }
            return stacks;
        }
        public ArrayList<ItemStack> extraItemStacks()
        {
            ArrayList<ItemStack> stacks = new ArrayList<>();
            for (ItemIngredient ingredient : extraItemIngredients)
            {
                stacks.add(ingredient.getStaticItem());
            }
            return stacks;
        }
    }
 }