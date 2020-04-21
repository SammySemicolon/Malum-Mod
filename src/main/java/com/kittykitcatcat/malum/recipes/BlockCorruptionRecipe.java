package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.kittykitcatcat.malum.MalumHelper.setBlockStateWithExistingProperties;

public class BlockCorruptionRecipe
{
    private Block block;
    private Block replacementBlock;

    public BlockCorruptionRecipe(Block block, Block replacementBlock)
    {
        this.block = block;
        this.replacementBlock = replacementBlock;
    }


    public static void attemptCorruption(BlockState state, World world, BlockPos pos)
    {
        BlockCorruptionRecipe recipe = ModRecipes.getBlockCorruptionRecipe(state);
        if (recipe != null)
        {
            Block replacementBlock = recipe.getReplacementBlock();
            BlockState replacementState = replacementBlock.getDefaultState();
            setBlockStateWithExistingProperties(world, pos, replacementState);
        }
    }


    public Block getBlock()
    {
        return block;
    }

    public Block getReplacementBlock()
    {
        return replacementBlock;
    }

    public static void initRecipes()
    {
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.OAK_SAPLING, ModBlocks.spirit_sapling));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.BIRCH_SAPLING, ModBlocks.spirit_sapling));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.SPRUCE_SAPLING, ModBlocks.spirit_sapling));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.JUNGLE_SAPLING, ModBlocks.spirit_sapling));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.DARK_OAK_SAPLING, ModBlocks.spirit_sapling));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.ACACIA_SAPLING, ModBlocks.spirit_sapling));

        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.OAK_LOG, ModBlocks.deadwood_log));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.BIRCH_LOG, ModBlocks.deadwood_log));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.SPRUCE_LOG, ModBlocks.deadwood_log));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.JUNGLE_LOG, ModBlocks.deadwood_log));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.DARK_OAK_LOG, ModBlocks.deadwood_log));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.ACACIA_LOG, ModBlocks.deadwood_log));

        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.OAK_PLANKS, ModBlocks.deadwood_planks));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.BIRCH_PLANKS, ModBlocks.deadwood_planks));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.SPRUCE_PLANKS, ModBlocks.deadwood_planks));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.JUNGLE_PLANKS, ModBlocks.deadwood_planks));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.DARK_OAK_PLANKS, ModBlocks.deadwood_planks));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.ACACIA_PLANKS, ModBlocks.deadwood_planks));

        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.OAK_LEAVES, ModBlocks.deadwood_leaves));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.BIRCH_LEAVES, ModBlocks.deadwood_leaves));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.SPRUCE_LEAVES, ModBlocks.deadwood_leaves));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.JUNGLE_LEAVES, ModBlocks.deadwood_leaves));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.DARK_OAK_LEAVES, ModBlocks.deadwood_leaves));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.ACACIA_LEAVES, ModBlocks.deadwood_leaves));

    }
}
