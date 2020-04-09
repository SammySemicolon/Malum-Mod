package com.kittykitcatcat.malum.recipes;

import com.kittykitcatcat.malum.init.ModBlocks;
import com.kittykitcatcat.malum.init.ModRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.kittykitcatcat.malum.MalumHelper.setBlockStateWithExistingProperties;

public class BlockTransmutationRecipe
{

    private Block block;
    private Block replacementBlock;

    public BlockTransmutationRecipe(Block block, Block replacementBlock)
    {
        this.block = block;
        this.replacementBlock = replacementBlock;
    }


    public static void attemptBlockTransmutation(BlockState state, World world, BlockPos pos)
    {
        BlockTransmutationRecipe recipe = ModRecipes.getBlockTransmutationRecipe(state);
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
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE, ModBlocks.refined_pathway));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_STAIRS, ModBlocks.refined_pathway_stairs));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_SLAB, ModBlocks.refined_pathway_slab));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_BRICKS, ModBlocks.refined_bricks));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_BRICK_STAIRS, ModBlocks.refined_bricks_stairs));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_BRICK_SLAB, ModBlocks.refined_bricks_slab));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.GLOWSTONE, ModBlocks.refined_glowstone_block));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.REDSTONE_LAMP, ModBlocks.refined_glowstone_lamp));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SMOOTH_STONE, ModBlocks.refined_smooth_stone));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(ModBlocks.smooth_stone_stairs, ModBlocks.refined_smooth_stone_stairs));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SMOOTH_STONE_SLAB, ModBlocks.refined_smooth_stone_slab));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(ModBlocks.wooden_casing, ModBlocks.wooden_beam));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(ModBlocks.wooden_beam, ModBlocks.wooden_casing));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.CARVED_PUMPKIN, ModBlocks.evil_pumpkin));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JACK_O_LANTERN, ModBlocks.lit_evil_pumpkin));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.OAK_LOG, ModBlocks.wooden_beam));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.BIRCH_LOG, ModBlocks.wooden_beam));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SPRUCE_LOG, ModBlocks.wooden_beam));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JUNGLE_LOG, ModBlocks.wooden_beam));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.DARK_OAK_LOG, ModBlocks.wooden_beam));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.ACACIA_LOG, ModBlocks.wooden_beam));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.OAK_STAIRS, ModBlocks.wooden_planks_stairs));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.BIRCH_STAIRS, ModBlocks.wooden_planks_stairs));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SPRUCE_STAIRS, ModBlocks.wooden_planks_stairs));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JUNGLE_STAIRS, ModBlocks.wooden_planks_stairs));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.DARK_OAK_STAIRS, ModBlocks.wooden_planks_stairs));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.ACACIA_STAIRS, ModBlocks.wooden_planks_stairs));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.OAK_SLAB, ModBlocks.wooden_planks_slab));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.BIRCH_SLAB, ModBlocks.wooden_planks_slab));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SPRUCE_SLAB, ModBlocks.wooden_planks_slab));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JUNGLE_SLAB, ModBlocks.wooden_planks_slab));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.DARK_OAK_SLAB, ModBlocks.wooden_planks_slab));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.ACACIA_SLAB, ModBlocks.wooden_planks_slab));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.OAK_PLANKS, ModBlocks.wooden_planks));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.BIRCH_PLANKS, ModBlocks.wooden_planks));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SPRUCE_PLANKS, ModBlocks.wooden_planks));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JUNGLE_PLANKS, ModBlocks.wooden_planks));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.DARK_OAK_PLANKS, ModBlocks.wooden_planks));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.ACACIA_PLANKS, ModBlocks.wooden_planks));
    }
}
