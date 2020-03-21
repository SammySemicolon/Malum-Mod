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
    private boolean simpleRecipe;
    public BlockTransmutationRecipe(Block block, Block replacementBlock, boolean simpleRecipe)
    {
        this.block = block;
        this.replacementBlock = replacementBlock;
        this.simpleRecipe = simpleRecipe;
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
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE, ModBlocks.refined_pathway,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_STAIRS, ModBlocks.refined_pathway_stairs,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_SLAB, ModBlocks.refined_pathway_slab,false));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_BRICKS, ModBlocks.refined_bricks,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_BRICK_STAIRS, ModBlocks.refined_bricks_stairs,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.STONE_BRICK_SLAB, ModBlocks.refined_bricks_slab,false));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.GLOWSTONE, ModBlocks.refined_glowstone_block,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.REDSTONE_LAMP, ModBlocks.refined_glowstone_lamp,false));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SMOOTH_STONE, ModBlocks.refined_smooth_stone,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(ModBlocks.smooth_stone_stairs, ModBlocks.refined_smooth_stone_stairs,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SMOOTH_STONE_SLAB, ModBlocks.refined_smooth_stone_slab,false));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(ModBlocks.wooden_casing, ModBlocks.wooden_beam,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(ModBlocks.wooden_beam, ModBlocks.wooden_casing,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.CARVED_PUMPKIN, ModBlocks.evil_pumpkin,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JACK_O_LANTERN, ModBlocks.lit_evil_pumpkin,false));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.OAK_LOG, ModBlocks.wooden_beam,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.BIRCH_LOG, ModBlocks.wooden_beam,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SPRUCE_LOG, ModBlocks.wooden_beam,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JUNGLE_LOG, ModBlocks.wooden_beam,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.DARK_OAK_LOG, ModBlocks.wooden_beam,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.ACACIA_LOG, ModBlocks.wooden_beam,false));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.OAK_STAIRS, ModBlocks.wooden_planks_stairs,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.BIRCH_STAIRS, ModBlocks.wooden_planks_stairs,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SPRUCE_STAIRS, ModBlocks.wooden_planks_stairs,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JUNGLE_STAIRS, ModBlocks.wooden_planks_stairs,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.DARK_OAK_STAIRS, ModBlocks.wooden_planks_stairs,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.ACACIA_STAIRS, ModBlocks.wooden_planks_stairs,false));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.OAK_SLAB, ModBlocks.wooden_planks_slab,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.BIRCH_SLAB, ModBlocks.wooden_planks_slab,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SPRUCE_SLAB, ModBlocks.wooden_planks_slab,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JUNGLE_SLAB, ModBlocks.wooden_planks_slab,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.DARK_OAK_SLAB, ModBlocks.wooden_planks_slab,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.ACACIA_SLAB, ModBlocks.wooden_planks_slab,false));

        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.OAK_PLANKS, ModBlocks.wooden_planks,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.BIRCH_PLANKS, ModBlocks.wooden_planks,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.SPRUCE_PLANKS, ModBlocks.wooden_planks,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.JUNGLE_PLANKS, ModBlocks.wooden_planks,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.DARK_OAK_PLANKS, ModBlocks.wooden_planks,false));
        ModRecipes.addBlockTransmutationRecipe(new BlockTransmutationRecipe(Blocks.ACACIA_PLANKS, ModBlocks.wooden_planks,false));
    }
}
