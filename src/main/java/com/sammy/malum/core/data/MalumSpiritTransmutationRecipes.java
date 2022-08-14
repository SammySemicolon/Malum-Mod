package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SoulwoodSpiritTransmutationRecipeBuilder;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.core.setup.content.block.BlockRegistry.*;

public class MalumSpiritTransmutationRecipes extends RecipeProvider {
    public MalumSpiritTransmutationRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Malum Block Transmutation Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        RegistryObject<Block>[] runewoodBlocks = new RegistryObject[]{RUNEWOOD_SAPLING, RUNEWOOD_LEAVES, STRIPPED_RUNEWOOD_LOG, RUNEWOOD_LOG, STRIPPED_RUNEWOOD, RUNEWOOD, REVEALED_RUNEWOOD_LOG, EXPOSED_RUNEWOOD_LOG, RUNEWOOD_PLANKS, RUNEWOOD_PLANKS_SLAB, RUNEWOOD_PLANKS_STAIRS, VERTICAL_RUNEWOOD_PLANKS, VERTICAL_RUNEWOOD_PLANKS_SLAB, VERTICAL_RUNEWOOD_PLANKS_STAIRS, RUNEWOOD_PANEL, RUNEWOOD_PANEL_SLAB, RUNEWOOD_PANEL_STAIRS, RUNEWOOD_TILES, RUNEWOOD_TILES_SLAB, RUNEWOOD_TILES_STAIRS, CUT_RUNEWOOD_PLANKS, RUNEWOOD_BEAM, RUNEWOOD_DOOR, RUNEWOOD_TRAPDOOR, SOLID_RUNEWOOD_TRAPDOOR, RUNEWOOD_PLANKS_BUTTON, RUNEWOOD_PLANKS_PRESSURE_PLATE, RUNEWOOD_PLANKS_FENCE, RUNEWOOD_PLANKS_FENCE_GATE, RUNEWOOD_ITEM_STAND, RUNEWOOD_ITEM_PEDESTAL, RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN};
        RegistryObject<Block>[] soulwoodBlocks = new RegistryObject[]{SOULWOOD_GROWTH, SOULWOOD_LEAVES, STRIPPED_SOULWOOD_LOG, SOULWOOD_LOG, STRIPPED_SOULWOOD, SOULWOOD, REVEALED_SOULWOOD_LOG, EXPOSED_SOULWOOD_LOG, SOULWOOD_PLANKS, SOULWOOD_PLANKS_SLAB, SOULWOOD_PLANKS_STAIRS, VERTICAL_SOULWOOD_PLANKS, VERTICAL_SOULWOOD_PLANKS_SLAB, VERTICAL_SOULWOOD_PLANKS_STAIRS, SOULWOOD_PANEL, SOULWOOD_PANEL_SLAB, SOULWOOD_PANEL_STAIRS, SOULWOOD_TILES, SOULWOOD_TILES_SLAB, SOULWOOD_TILES_STAIRS, CUT_SOULWOOD_PLANKS, SOULWOOD_BEAM, SOULWOOD_DOOR, SOULWOOD_TRAPDOOR, SOLID_SOULWOOD_TRAPDOOR, SOULWOOD_PLANKS_BUTTON, SOULWOOD_PLANKS_PRESSURE_PLATE, SOULWOOD_PLANKS_FENCE, SOULWOOD_PLANKS_FENCE_GATE, SOULWOOD_ITEM_STAND, SOULWOOD_ITEM_PEDESTAL, SOULWOOD_SIGN, SOULWOOD_WALL_SIGN};

        new SoulwoodSpiritTransmutationRecipeBuilder(Arrays.stream(runewoodBlocks).map(b -> Ingredient.of(b.get().asItem())).collect(Collectors.toList()), Arrays.stream(soulwoodBlocks).map(b -> Ingredient.of(b.get().asItem())).collect(Collectors.toList()))
                .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.STONE, Blocks.COBBLESTONE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.COBBLESTONE, Blocks.GRAVEL)
                .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.GRAVEL, Blocks.SAND)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.SAND, BLIGHTED_SOIL.get())
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.ANDESITE, Blocks.TUFF)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.TUFF, Blocks.STONE)
            .build(consumer, malumPath("tuff_block_to_stone"));

        new SpiritTransmutationRecipeBuilder(Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.DRIPSTONE_BLOCK, Blocks.STONE)
            .build(consumer, malumPath("dripstone_block_to_stone"));

        new SpiritTransmutationRecipeBuilder(Blocks.DIORITE, Blocks.CALCITE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.CALCITE, Blocks.STONE)
            .build(consumer, malumPath("calcite_to_stone"));

        new SpiritTransmutationRecipeBuilder(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.COBBLED_DEEPSLATE, Blocks.BASALT)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.BASALT, Blocks.NETHERRACK)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.NETHERRACK, Blocks.SOUL_SAND)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.SOUL_SAND, Blocks.RED_SAND)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.RED_SAND, BLIGHTED_SOIL.get())
            .build(consumer, malumPath("red_sand_to_blighted_soil"));

        new SpiritTransmutationRecipeBuilder(Blocks.BASALT, Blocks.CLAY)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.CLAY, Blocks.PRISMARINE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.PRISMARINE, Blocks.SEA_LANTERN)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.SEA_LANTERN, Blocks.SNOW_BLOCK)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.SNOW_BLOCK, Blocks.ICE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.ICE, BLIGHTED_SOIL.get())
            .build(consumer, malumPath("ice_to_blighted_soil"));

        new SpiritTransmutationRecipeBuilder(ItemRegistry.HEX_ASH.get(), ItemRegistry.CURSED_GRIT.get())
                .build(consumer);
    }
}