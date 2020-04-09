package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.DIRT, ModBlocks.blighted_dirt));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.GRASS_BLOCK, ModBlocks.blighted_grass));
        ModRecipes.addBlockCorruptionRecipe(new BlockCorruptionRecipe(Blocks.OAK_SAPLING, ModBlocks.spirit_sapling));
    }
}
