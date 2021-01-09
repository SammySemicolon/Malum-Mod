package com.sammy.malum.core.modcontent;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class MalumSpiritKilnRecipes
{
    public static final ArrayList<MalumSpiritKilnRecipe> INFUSING = new ArrayList<>();
    
    public static void init()
    {
        new MalumSpiritKilnRecipe(Items.SOUL_SAND, 1, MalumItems.SOUL_SHARD.get(), 1, 2, MalumItems.BLAZE_QUARTZ.get(), Items.QUARTZ);
        new MalumSpiritKilnRecipe(Items.GOLD_INGOT, 1, MalumItems.TRANSMISSIVE_METAL_INGOT.get(), 1, 4, MalumItems.AVARICIOUS_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.GLOWSTONE_DUST, 4, MalumItems.ETHER.get(), 1, 4, MalumItems.ARCANE_SPIRIT_SPLINTER.get(), MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(), MalumItems.SULPHURIC_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.IRON_INGOT, 1, MalumItems.SPIRITED_STEEL_INGOT.get(), 1, 4, MalumItems.SINISTER_SPIRIT_SPLINTER.get(), MalumItems.FUSIBLE_SPIRIT_SPLINTER.get());
        //        new SpiritKilnRecipe(MalumItems.RUIN_PLATING.get(), 2, MalumItems.UMBRAL_METAL_INGOT.get(), 1,8, MalumItems.ECTOPLASM.get(), MalumItems.REMEDIAL_SPIRIT_SPLINTER.get(), MalumItems.SINISTER_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(MalumItems.CRUDE_SCYTHE.get(), 1, MalumItems.SPIRITED_STEEL_SCYTHE.get(), 1, 8, MalumItems.SOUL_SHARD.get(), MalumItems.FUSIBLE_SPIRIT_SPLINTER.get(), MalumItems.SPIRITED_STEEL_INGOT.get());
    
        new MalumSpiritKilnRecipe(Items.ROTTEN_FLESH, 1, MalumItems.REANIMATED_MATTER.get(), 1, 2, MalumItems.UNDEAD_SPIRIT_SPLINTER.get(), MalumItems.WILD_SPIRIT_SPLINTER.get());
        //        new SpiritKilnRecipe(MalumItems.REANIMATED_MATTER.get(), 1, MalumItems.ECTOPLASM.get(), 2 ,4, MalumItems.LUNAR_SAPBALL.get(), MalumItems.SOLAR_SAPBALL.get(), MalumItems.ARCANE_SPIRIT_SPLINTER.get());
        //        new SpiritKilnRecipe(Items.NETHER_STAR, 1, MalumItems.SHARD_OF_WISDOM.get(), 2,8, MalumItems.TERMINUS_SPIRIT_SPLINTER.get(), MalumItems.SINISTER_SPIRIT_SPLINTER.get(), MalumItems.ELDRITCH_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(Items.CLAY_BALL, 1, MalumItems.PENUMBRAL_MOLD.get(), 1, 1, MalumItems.FUSIBLE_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.ENDER_PEARL, 1, 2, MalumItems.TERMINUS_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.MAGMA_CREAM, 1, 2, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.NETHER_WART, 4, 2, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(),Items.WHEAT_SEEDS);
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.BLAZE_POWDER, 1, 2, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(),MalumItems.SULPHURIC_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.GHAST_TEAR, 1, 8, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(),MalumItems.AQUATIC_SPIRIT_SPLINTER.get(), MalumItems.AVARICIOUS_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.NETHERITE_SCRAP, 1, 16, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(),MalumItems.ELDRITCH_SPIRIT_SPLINTER.get(), MalumItems.SULPHURIC_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.PRISMARINE_SHARD, 1, 2, MalumItems.AQUATIC_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.PRISMARINE_CRYSTALS, 2, 2, MalumItems.AQUATIC_SPIRIT_SPLINTER.get(),MalumItems.ARCANE_SPIRIT_SPLINTER.get());
        
        new MalumSpiritKilnRecipe(MalumItems.PENUMBRAL_MOLD.get(), 1, Items.VINE, 1, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        
        new MalumSpiritKilnRecipe(Items.ORANGE_TULIP, 1, Items.ORANGE_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        
        new MalumSpiritKilnRecipe(Items.ALLIUM, 1, Items.MAGENTA_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.LILAC, 1, Items.MAGENTA_DYE, 32, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(Items.BLUE_ORCHID, 1, Items.LIGHT_BLUE_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(Items.DANDELION, 1, Items.YELLOW_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.SUNFLOWER, 1, Items.YELLOW_DYE, 32, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(MalumItems.MARIGOLD.get(), 1, Items.YELLOW_DYE, 32, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(Items.PINK_TULIP, 1, Items.PINK_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.PEONY, 1, Items.PINK_DYE, 32, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(Items.OXEYE_DAISY, 1, Items.LIGHT_GRAY_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.AZURE_BLUET, 1, Items.LIGHT_GRAY_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.WHITE_TULIP, 1, Items.LIGHT_GRAY_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(MalumItems.LAVENDER.get(), 1, Items.PURPLE_DYE, 32, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(Items.CORNFLOWER, 1, Items.BLUE_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
    
        new MalumSpiritKilnRecipe(Items.RED_TULIP, 1, Items.RED_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.POPPY, 1, Items.RED_DYE, 16, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new MalumSpiritKilnRecipe(Items.ROSE_BUSH, 1, Items.RED_DYE, 32, 1, MalumItems.WILD_SPIRIT_SPLINTER.get());
    }
    
    public static MalumSpiritKilnRecipe getRecipe(ItemStack stack)
    {
        for (MalumSpiritKilnRecipe recipe : INFUSING)
        {
            if (recipe.inputItem.equals(stack.getItem()))
            {
                if (stack.getCount() >= recipe.inputItemCount)
                {
                    return recipe;
                }
            }
        }
        return null;
    }
    
    public static MalumSpiritKilnRecipe getPreciseRecipe(ItemStack stack, ArrayList<Item> extraItems)
    {
        for (MalumSpiritKilnRecipe recipe : INFUSING)
        {
            if (recipe.hasAlternatives)
            {
                if (recipe.inputItem.equals(stack.getItem()))
                {
                    if (stack.getCount() >= recipe.inputItemCount)
                    {
                        if (recipe.extraItems.containsAll(extraItems))
                        {
                            return recipe;
                        }
                    }
                }
            }
        }
        return null;
    }
    public static class MalumSpiritKilnRecipe
    {
    
        public final Item inputItem;
        public final int inputItemCount;
        public final Item outputItem;
        public final int outputItemCount;
        public final int recipeTime;
        public boolean hasAlternatives;
        public ArrayList<Item> extraItems;
    
        public boolean isAdvanced()
        {
            return extraItems != null;
        }
        
        public MalumSpiritKilnRecipe(Item inputItem, int inputItemCount, Item outputItem, int outputItemCount, int recipeTime)
        {
            this.inputItem = inputItem;
            this.inputItemCount = inputItemCount;
            this.outputItem = outputItem;
            this.outputItemCount = outputItemCount;
            this.recipeTime = recipeTime * MalumMod.globalSpeedMultiplier;
            this.hasAlternatives = false;
            INFUSING.add(this);
        }
    
        public MalumSpiritKilnRecipe(Item inputItem, int inputItemCount, Item outputItem, int outputItemCount, int recipeTime, Item... items)
        {
            this.inputItem = inputItem;
            this.inputItemCount = inputItemCount;
            this.outputItem = outputItem;
            this.outputItemCount = outputItemCount;
            this.recipeTime = recipeTime * MalumMod.globalSpeedMultiplier;
            this.extraItems = new ArrayList<>();
            for (Item item : items)
            {
                if (extraItems.contains(item))
                {
                    throw new ArrayStoreException("Yo there shouldn't be any duplicate extra items in a tainted furnace recipe, this one is 100% on you. Duplicate item: " + item.getRegistryName().getPath());
                }
                extraItems.add(item);
            }
            for (MalumSpiritKilnRecipe recipe : INFUSING)
            {
                if (recipe.inputItem.equals(this.inputItem))
                {
                    recipe.hasAlternatives = true;
                    this.hasAlternatives = true;
                }
            }
            INFUSING.add(this);
        }
    }
}