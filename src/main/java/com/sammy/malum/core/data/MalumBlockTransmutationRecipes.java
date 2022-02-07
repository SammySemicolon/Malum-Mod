package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.BlockTransmutationRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static com.sammy.malum.core.setup.block.BlockRegistry.*;

public class MalumBlockTransmutationRecipes extends RecipeProvider
{
    public MalumBlockTransmutationRecipes(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    public String getName()
    {
        return "Malum Block Transmutation Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {
        RegistryObject<Block>[] runewoodBlocks = new RegistryObject[]{RUNEWOOD_SAPLING, RUNEWOOD_LEAVES, STRIPPED_RUNEWOOD_LOG, RUNEWOOD_LOG, STRIPPED_RUNEWOOD, RUNEWOOD, REVEALED_RUNEWOOD_LOG, EXPOSED_RUNEWOOD_LOG, RUNEWOOD_PLANKS, RUNEWOOD_PLANKS_SLAB, RUNEWOOD_PLANKS_STAIRS, VERTICAL_RUNEWOOD_PLANKS, VERTICAL_RUNEWOOD_PLANKS_SLAB, VERTICAL_RUNEWOOD_PLANKS_STAIRS, RUNEWOOD_PANEL, RUNEWOOD_PANEL_SLAB, RUNEWOOD_PANEL_STAIRS, RUNEWOOD_TILES, RUNEWOOD_TILES_SLAB, RUNEWOOD_TILES_STAIRS, CUT_RUNEWOOD_PLANKS, RUNEWOOD_BEAM, RUNEWOOD_DOOR, RUNEWOOD_TRAPDOOR, SOLID_RUNEWOOD_TRAPDOOR, RUNEWOOD_PLANKS_BUTTON, RUNEWOOD_PLANKS_PRESSURE_PLATE, RUNEWOOD_PLANKS_FENCE, RUNEWOOD_PLANKS_FENCE_GATE, RUNEWOOD_ITEM_STAND, RUNEWOOD_ITEM_PEDESTAL, RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN};
        RegistryObject<Block>[] soulwoodBlocks = new RegistryObject[]{SOULWOOD_SAPLING, SOULWOOD_LEAVES, STRIPPED_SOULWOOD_LOG, SOULWOOD_LOG, STRIPPED_SOULWOOD, SOULWOOD, REVEALED_SOULWOOD_LOG, EXPOSED_SOULWOOD_LOG, SOULWOOD_PLANKS, SOULWOOD_PLANKS_SLAB, SOULWOOD_PLANKS_STAIRS, VERTICAL_SOULWOOD_PLANKS, VERTICAL_SOULWOOD_PLANKS_SLAB, VERTICAL_SOULWOOD_PLANKS_STAIRS, SOULWOOD_PANEL, SOULWOOD_PANEL_SLAB, SOULWOOD_PANEL_STAIRS, SOULWOOD_TILES, SOULWOOD_TILES_SLAB, SOULWOOD_TILES_STAIRS, CUT_SOULWOOD_PLANKS, SOULWOOD_BEAM, SOULWOOD_DOOR, SOULWOOD_TRAPDOOR, SOLID_SOULWOOD_TRAPDOOR, SOULWOOD_PLANKS_BUTTON, SOULWOOD_PLANKS_PRESSURE_PLATE, SOULWOOD_PLANKS_FENCE, SOULWOOD_PLANKS_FENCE_GATE, SOULWOOD_ITEM_STAND, SOULWOOD_ITEM_PEDESTAL, SOULWOOD_SIGN, SOULWOOD_WALL_SIGN};

        for (int i = 0; i < runewoodBlocks.length; i++)
        {
            Block runewoodBlock = runewoodBlocks[i].get();
            Block soulwoodBlock = soulwoodBlocks[i].get();
            new BlockTransmutationRecipeBuilder(runewoodBlock, soulwoodBlock)
                    .build(consumer);
        }
        new BlockTransmutationRecipeBuilder(Blocks.COBBLESTONE, Blocks.GRAVEL)
                .build(consumer);

        new BlockTransmutationRecipeBuilder(Blocks.STONE, Blocks.SAND)
                .build(consumer);

        new BlockTransmutationRecipeBuilder(Blocks.NETHERRACK, Blocks.SOUL_SAND)
                .build(consumer);

        new BlockTransmutationRecipeBuilder(Blocks.SOUL_SAND, Blocks.MAGMA_BLOCK)
                .build(consumer);
    }
}