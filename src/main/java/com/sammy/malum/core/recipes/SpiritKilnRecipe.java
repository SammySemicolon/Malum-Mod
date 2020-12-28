package com.sammy.malum.core.recipes;

import com.sammy.malum.core.init.MalumItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

import static com.sammy.malum.core.recipes.TaintTransfusion.transfusions;

public class SpiritKilnRecipe
{
    public static final ArrayList<SpiritKilnRecipe> recipes = new ArrayList<>();
    public static int advancedRecipeCount;
    public static int globalSpeedMultiplier = 40;
    public final Item inputItem;
    public final int inputItemCount;
    public final Item outputItem;
    public final int outputItemCount;
    public final int recipeTime;
    public ArrayList<Item> extraItems;
    public SpiritKilnRecipe(Item inputItem, int inputItemCount, Item outputItem, int outputItemCount, int recipeTime) {
        this.inputItem = inputItem;
        this.inputItemCount = inputItemCount;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        this.recipeTime = recipeTime * globalSpeedMultiplier;
        recipes.add(this);
    }
    public SpiritKilnRecipe(Item inputItem, int inputItemCount, Item outputItem, int outputItemCount, int recipeTime, Item... items) {
        this.inputItem = inputItem;
        this.inputItemCount = inputItemCount;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        this.recipeTime = recipeTime * globalSpeedMultiplier;
        this.extraItems = new ArrayList<>();
        for (Item item : items)
        {
            if (extraItems.contains(item))
            {
                throw new ArrayStoreException("Yo there shouldn't be any duplicate extra items in a tainted furnace recipe, this one is 100% on you. Duplicate item: " + item.getRegistryName().getPath());
            }
            extraItems.add(item);
        }
        recipes.add(this);
    }
    public boolean isAdvanced()
    {
        return extraItems != null;
    }
    public static void init()
    {
        new SpiritKilnRecipe(Items.DIAMOND, 1, MalumItems.EMPTY_SPLINTER.get(), 8, 2, Items.SOUL_SAND);
        new SpiritKilnRecipe(Items.GOLD_INGOT, 2, MalumItems.TRANSMISSIVE_METAL_INGOT.get(), 1, 4, MalumItems.REMEDIAL_SPIRIT_SPLINTER.get());
        new SpiritKilnRecipe(Items.GLOWSTONE_DUST, 4, MalumItems.DARK_FLARES.get(), 2, 4, MalumItems.ARCANE_SPIRIT_SPLINTER.get(), Items.COAL, Items.GUNPOWDER);
        new SpiritKilnRecipe(Items.IRON_INGOT, 2, MalumItems.RUIN_PLATING.get(), 4, 4, MalumItems.DARK_FLARES.get(), MalumItems.SINISTER_SPIRIT_SPLINTER.get(), Items.IRON_NUGGET);
//        new SpiritKilnRecipe(MalumItems.RUIN_PLATING.get(), 2, MalumItems.UMBRAL_METAL_INGOT.get(), 1,8, MalumItems.ECTOPLASM.get(), MalumItems.REMEDIAL_SPIRIT_SPLINTER.get(), MalumItems.SINISTER_SPIRIT_SPLINTER.get());
    
        new SpiritKilnRecipe(Items.COAL, 1, MalumItems.ARCANE_CHARCOAL.get(), 2,1, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(), MalumItems.ARCANE_SPIRIT_SPLINTER.get());
        new SpiritKilnRecipe(Items.ROTTEN_FLESH, 4, MalumItems.REANIMATED_MATTER.get(), 4, 2, MalumItems.UNDEAD_SPIRIT_SPLINTER.get(), MalumItems.WILD_SPIRIT_SPLINTER.get());
//        new SpiritKilnRecipe(MalumItems.REANIMATED_MATTER.get(), 1, MalumItems.ECTOPLASM.get(), 2 ,4, MalumItems.LUNAR_SAPBALL.get(), MalumItems.SOLAR_SAPBALL.get(), MalumItems.ARCANE_SPIRIT_SPLINTER.get());
//        new SpiritKilnRecipe(Items.NETHER_STAR, 1, MalumItems.SHARD_OF_WISDOM.get(), 2,8, MalumItems.TERMINUS_SPIRIT_SPLINTER.get(), MalumItems.SINISTER_SPIRIT_SPLINTER.get(), MalumItems.ELDRITCH_SPIRIT_SPLINTER.get());
    
        new SpiritKilnRecipe(Items.PRISMARINE, 2, MalumItems.ABSTRUSE_BLOCK.get(), 1,2, MalumItems.AQUATIC_SPIRIT_SPLINTER.get(), MalumItems.NIMBLE_SPIRIT_SPLINTER.get());
        new SpiritKilnRecipe(Items.SOUL_SAND, 2, MalumItems.WITHER_SAND.get(), 1,2, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(), MalumItems.REMEDIAL_SPIRIT_SPLINTER.get());
    
        new SpiritKilnRecipe(MalumItems.ADHESIVE.get(), 2, Items.ENDER_PEARL, 1 ,2, MalumItems.TERMINUS_SPIRIT_SPLINTER.get());
        new SpiritKilnRecipe(Items.GUNPOWDER, 2, Items.BLAZE_POWDER, 6,2, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(), MalumItems.SULPHURIC_SPIRIT_SPLINTER.get());
        new SpiritKilnRecipe(Items.PRISMARINE_CRYSTALS, 4, Items.GHAST_TEAR, 1,4, MalumItems.REMEDIAL_SPIRIT_SPLINTER.get(), MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get(), MalumItems.ARCANE_SPIRIT_SPLINTER.get());
        new SpiritKilnRecipe(Items.WHEAT_SEEDS, 2, Items.VINE, 8,3, MalumItems.WILD_SPIRIT_SPLINTER.get());
        new SpiritKilnRecipe(Items.QUARTZ, 1, Items.PRISMARINE_SHARD, 1,2, MalumItems.AQUATIC_SPIRIT_SPLINTER.get());
        new SpiritKilnRecipe(Items.CLAY_BALL, 2, Items.BRICK, 4,1, MalumItems.NETHERBORNE_SPIRIT_SPLINTER.get());
    
        advancedRecipeCount = recipes.size();
        for (TaintTransfusion transfusion : transfusions)
        {
            new SpiritKilnRecipe(transfusion.inputBlock.asItem(),1,transfusion.outputBlock.asItem(),1,1);
        }
    }
    public static SpiritKilnRecipe getRecipe(ItemStack stack)
    {
        for (SpiritKilnRecipe recipe : recipes)
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
}
