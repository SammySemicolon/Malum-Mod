package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

public class MalumChiseling
{
    public static final ArrayList<MalumChiselRecipe> recipes = new ArrayList<>();
    
    public static void init()
    {
        new MalumChiselRecipe(Items.BONE, MalumRunes.RUNE_OF_DEATH);
        new MalumChiselRecipe(Items.EGG, MalumRunes.RUNE_OF_LIFE);
    }
    public static Block getChiseledBlock(Block block, ItemStack stack)
    {
        for (MalumChiselRecipe recipe : recipes)
        {
            if (recipe.inputItem.equals(stack.getItem()))
            {
                if (block.equals(MalumBlocks.SUN_KISSED_LOG.get()))
                {
                    return recipe.rune.carvedForm;
                }
            }
        }
        return null;
    }
    public static class MalumChiselRecipe
    {
        public final Item inputItem;
        public final MalumRunes.MalumRune rune;
    
        public MalumChiselRecipe(Item inputItem, MalumRunes.MalumRune rune) {
            this.inputItem = inputItem;
            this.rune = rune;
            recipes.add(this);
        }
    }
}
