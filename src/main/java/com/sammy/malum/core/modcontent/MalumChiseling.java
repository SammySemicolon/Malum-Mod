package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumRunes.MalumRune;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;

public class MalumChiseling
{
    public static final ArrayList<MalumChiselRecipe> CHISELING = new ArrayList<>();
    
    public static void init()
    {
        
        new MalumChiselRecipe(Items.ROTTEN_FLESH, MalumRunes.RUNE_OF_DEATH);
        new MalumChiselRecipe(Items.WHEAT, MalumRunes.RUNE_OF_LIFE);
        new MalumChiselRecipe(Items.INK_SAC, MalumRunes.RUNE_OF_WATER);
        new MalumChiselRecipe(Items.IRON_INGOT, MalumRunes.RUNE_OF_EARTH);
        new MalumChiselRecipe(Items.FEATHER, MalumRunes.RUNE_OF_AIR);
        new MalumChiselRecipe(MalumItems.ETHER.get(), MalumRunes.RUNE_OF_SOUL);
        new MalumChiselRecipe(Blocks.BONE_BLOCK, MalumBlocks.POLISHED_BONE_BLOCK.get());
    }
    public static Block getChiseledBlock(Block block, ItemStack stack)
    {
        for (MalumChiselRecipe recipe : CHISELING)
        {
            if (recipe.isRunic())
            {
                if (recipe.inputItem.equals(stack.getItem()))
                {
                    if (block.equals(MalumBlocks.SUN_KISSED_LOG.get()))
                    {
                        return recipe.rune.carvedForm;
                    }
                    if (block.equals(MalumBlocks.SUN_KISSED_LOG.get()))
                    {
                        return recipe.rune.necroticForm;
                    }
                }
            }
            else
            {
                if (recipe.inputBlock.equals(block))
                {
                    return recipe.outputBlock;
                }
            }
        }
        return null;
    }
    public static class MalumChiselRecipe
    {
        public boolean isRunic()
        {
            return rune != null;
        }
        public Block inputBlock;
        public Block outputBlock;
        
        public Item inputItem;
        public MalumRune rune;
    
        public Item outputItem;
    
        public MalumChiselRecipe(Item inputItem, MalumRune rune) {
            this.inputItem = inputItem;
            this.outputItem = rune.item;
            this.rune = rune;
            CHISELING.add(this);
        }
        public MalumChiselRecipe(Block inputBlock, Block outputBlock)
        {
            this.inputItem = inputBlock.asItem();
            this.outputItem = outputBlock.asItem();
            this.inputBlock = inputBlock;
            this.outputBlock = outputBlock;
            CHISELING.add(this);
        }
    }
}
