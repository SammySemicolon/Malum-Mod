package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.systems.recipes.ItemIngredient;
import com.sammy.malum.core.systems.recipes.SimpleItemIngredient;
import com.sammy.malum.core.systems.recipes.SpiritIngredient;
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
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.HOLY_SPIRIT))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        //twisted rock
        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.COBBLESTONE, 16), new ItemIngredient(MalumItems.TWISTED_ROCK.get(), 16))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        //ether
        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.DUSTS_GLOWSTONE, 4), new ItemIngredient(MalumItems.YELLOW_ETHER.get(), 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.INFERNAL_SPIRIT))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT))
                .addExtraItem(new SimpleItemIngredient(MalumItems.BLAZING_QUARTZ.get()));

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

        //soul stained steel
        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.INGOTS_IRON, 1), new ItemIngredient(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT, 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        //hallowed gold
        new MalumSpiritAltarRecipe(new ItemIngredient(Tags.Items.INGOTS_GOLD, 1), new ItemIngredient(MalumItems.HALLOWED_GOLD_INGOT.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.HOLY_SPIRIT, 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT));

        //radiant soulstone
        new MalumSpiritAltarRecipe(new ItemIngredient(MalumItems.SOULSTONE.get(), 2), new ItemIngredient(MalumItems.RADIANT_SOULSTONE.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.HOLY_SPIRIT, 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT, 2))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT))
                .addExtraItem(new SimpleItemIngredient(Items.PHANTOM_MEMBRANE))
                .addExtraItem(new SimpleItemIngredient(MalumItems.HEX_ASH.get()));

        //tyrving
        new MalumSpiritAltarRecipe(new ItemIngredient(MalumItems.SOUL_STAINED_STEEL_SWORD.get(), 1), new ItemIngredient(MalumItems.TYRVING.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ELDRITCH_SPIRIT, 4))
                .addExtraItem(new SimpleItemIngredient(MalumItems.RADIANT_SOULSTONE.get()))
                .addExtraItem(new SimpleItemIngredient(MalumItems.TWISTED_ROCK.get()))
                .addExtraItem(new SimpleItemIngredient(Items.OBSIDIAN))
                .addExtraItem(new SimpleItemIngredient(MalumItems.HEX_ASH.get()));

        //soul stained stronghold
        new MalumSpiritAltarRecipe(new ItemIngredient(MalumItems.SOUL_STAINED_STEEL_HELMET.get(), 1), new ItemIngredient(MalumItems.SOUL_STAINED_STRONGHOLD_HELMET.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.EARTHEN_SPIRIT, 6))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ELDRITCH_SPIRIT, 2))
                .addExtraItem(new SimpleItemIngredient(MalumItems.RADIANT_SOULSTONE.get()))
                .addExtraItem(new SimpleItemIngredient(Items.NETHERITE_SCRAP))
                .addExtraItem(new SimpleItemIngredient(MalumItems.SOUL_STAINED_STEEL_INGOT.get()))
                .addExtraItem(new SimpleItemIngredient(MalumItems.HEX_ASH.get()));

        new MalumSpiritAltarRecipe(new ItemIngredient(MalumItems.SOUL_STAINED_STEEL_CHESTPLATE.get(), 1), new ItemIngredient(MalumItems.SOUL_STAINED_STRONGHOLD_CHESTPLATE.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.INFERNAL_SPIRIT, 6))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ELDRITCH_SPIRIT, 2))
                .addExtraItem(new SimpleItemIngredient(MalumItems.RADIANT_SOULSTONE.get()))
                .addExtraItem(new SimpleItemIngredient(Items.NETHERITE_SCRAP))
                .addExtraItem(new SimpleItemIngredient(MalumItems.SOUL_STAINED_STEEL_INGOT.get()))
                .addExtraItem(new SimpleItemIngredient(MalumItems.HEX_ASH.get()));

        new MalumSpiritAltarRecipe(new ItemIngredient(MalumItems.SOUL_STAINED_STEEL_LEGGINGS.get(), 1), new ItemIngredient(MalumItems.SOUL_STAINED_STRONGHOLD_LEGGINGS.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.AERIAL_SPIRIT, 6))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ELDRITCH_SPIRIT, 2))
                .addExtraItem(new SimpleItemIngredient(MalumItems.RADIANT_SOULSTONE.get()))
                .addExtraItem(new SimpleItemIngredient(Items.NETHERITE_SCRAP))
                .addExtraItem(new SimpleItemIngredient(MalumItems.SOUL_STAINED_STEEL_INGOT.get()))
                .addExtraItem(new SimpleItemIngredient(MalumItems.HEX_ASH.get()));

        new MalumSpiritAltarRecipe(new ItemIngredient(MalumItems.SOUL_STAINED_STEEL_BOOTS.get(), 1), new ItemIngredient(MalumItems.SOUL_STAINED_STRONGHOLD_BOOTS.get(), 1))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.WICKED_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ARCANE_SPIRIT, 12))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.AQUATIC_SPIRIT, 6))
                .addSpirit(new SpiritIngredient(MalumSpiritTypes.ELDRITCH_SPIRIT, 2))
                .addExtraItem(new SimpleItemIngredient(MalumItems.RADIANT_SOULSTONE.get()))
                .addExtraItem(new SimpleItemIngredient(Items.NETHERITE_SCRAP))
                .addExtraItem(new SimpleItemIngredient(MalumItems.SOUL_STAINED_STEEL_INGOT.get()))
                .addExtraItem(new SimpleItemIngredient(MalumItems.HEX_ASH.get()));
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
    }
}