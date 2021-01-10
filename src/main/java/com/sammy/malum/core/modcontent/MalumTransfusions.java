package com.sammy.malum.core.modcontent;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.recipes.MalumBlockIngredient;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;

import java.util.ArrayList;

public class MalumTransfusions
{
    public static final ArrayList<MalumTransfusionRecipe> TRANSFUSIONS = new ArrayList<>();
    
    public static void init()
    {
        new MalumTransfusionRecipe(Blocks.STONE, MalumBlocks.TAINTED_ROCK.get());
        new MalumTransfusionRecipe(Blocks.BLACKSTONE, MalumBlocks.DARKENED_ROCK.get());
        new MalumTransfusionRecipe(Blocks.GRASS_BLOCK, MalumBlocks.SUN_KISSED_GRASS_BLOCK.get());
        new MalumTransfusionRecipe(BlockTags.LOGS, MalumBlocks.SUN_KISSED_LOG.get());
        new MalumTransfusionRecipe(Blocks.COBBLESTONE, Blocks.NETHERRACK);
    }
    
    public static Block getTransfusedBlock(Block block)
    {
        for (MalumTransfusionRecipe recipe : TRANSFUSIONS)
        {
            Block outputBlockMaybe = recipe.matches(block);
            if (outputBlockMaybe != null)
            {
                return outputBlockMaybe;
            }
        }
        return null;
    }
    
    public static class MalumTransfusionRecipe
    {
        public MalumBlockIngredient input;
        public Block outputBlock;
    
        public MalumTransfusionRecipe(ITag<Block> tagInput, Block outputBlock)
        {
            this.input = new MalumBlockIngredient(tagInput);
            this.outputBlock = outputBlock;
            TRANSFUSIONS.add(this);
        }
        public MalumTransfusionRecipe(Block defaultInput, Block outputBlock)
        {
            this.input = new MalumBlockIngredient(defaultInput);
            this.outputBlock = outputBlock;
            TRANSFUSIONS.add(this);
        }
        public Block matches(Block block)
        {
            if (input.isSimple())
            {
                if (input.block.equals(block))
                {
                    return outputBlock;
                }
            }
            else
            {
                if (input.tag.contains(block) && !block.equals(outputBlock))
                {
                    return outputBlock;
                }
            }
            return null;
        }
    }
}
