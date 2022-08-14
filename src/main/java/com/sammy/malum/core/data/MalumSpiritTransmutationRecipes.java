package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SpiritTransmutationRecipeBuilder;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

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
        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(RUNEWOOD_SAPLING, SOULWOOD_GROWTH)
            .addTransmutation(RUNEWOOD_LEAVES, SOULWOOD_LEAVES)
            .addTransmutation(STRIPPED_RUNEWOOD_LOG, STRIPPED_SOULWOOD_LOG)
            .addTransmutation(RUNEWOOD_LOG, SOULWOOD_LOG)
            .addTransmutation(STRIPPED_RUNEWOOD, STRIPPED_SOULWOOD)
            .addTransmutation(RUNEWOOD, SOULWOOD)
            .addTransmutation(REVEALED_RUNEWOOD_LOG, REVEALED_SOULWOOD_LOG)
            .addTransmutation(EXPOSED_RUNEWOOD_LOG, EXPOSED_SOULWOOD_LOG)
            .addTransmutation(RUNEWOOD_PLANKS, SOULWOOD_PLANKS)
            .addTransmutation(RUNEWOOD_PLANKS_SLAB, SOULWOOD_PLANKS_SLAB)
            .addTransmutation(RUNEWOOD_PLANKS_STAIRS, SOULWOOD_PLANKS_STAIRS)
            .addTransmutation(VERTICAL_RUNEWOOD_PLANKS, VERTICAL_SOULWOOD_PLANKS)
            .addTransmutation(VERTICAL_RUNEWOOD_PLANKS_SLAB, VERTICAL_SOULWOOD_PLANKS_SLAB)
            .addTransmutation(VERTICAL_RUNEWOOD_PLANKS_STAIRS, VERTICAL_SOULWOOD_PLANKS_STAIRS)
            .addTransmutation(RUNEWOOD_PANEL, SOULWOOD_PANEL)
            .addTransmutation(RUNEWOOD_PANEL_SLAB, SOULWOOD_PANEL_SLAB)
            .addTransmutation(RUNEWOOD_PANEL_STAIRS, SOULWOOD_PANEL_STAIRS)
            .addTransmutation(RUNEWOOD_TILES, SOULWOOD_TILES)
            .addTransmutation(RUNEWOOD_TILES_SLAB, SOULWOOD_TILES_SLAB)
            .addTransmutation(RUNEWOOD_TILES_STAIRS, SOULWOOD_TILES_STAIRS)
            .addTransmutation(CUT_RUNEWOOD_PLANKS, CUT_SOULWOOD_PLANKS)
            .addTransmutation(RUNEWOOD_BEAM, SOULWOOD_BEAM)
            .addTransmutation(RUNEWOOD_DOOR, SOULWOOD_DOOR)
            .addTransmutation(RUNEWOOD_TRAPDOOR, SOULWOOD_TRAPDOOR)
            .addTransmutation(SOLID_RUNEWOOD_TRAPDOOR, SOLID_SOULWOOD_TRAPDOOR)
            .addTransmutation(RUNEWOOD_PLANKS_BUTTON, SOULWOOD_PLANKS_BUTTON)
            .addTransmutation(RUNEWOOD_PLANKS_PRESSURE_PLATE, SOULWOOD_PLANKS_PRESSURE_PLATE)
            .addTransmutation(RUNEWOOD_PLANKS_FENCE, SOULWOOD_PLANKS_FENCE)
            .addTransmutation(RUNEWOOD_PLANKS_FENCE_GATE, SOULWOOD_PLANKS_FENCE_GATE)
            .addTransmutation(RUNEWOOD_ITEM_STAND, SOULWOOD_ITEM_STAND)
            .addTransmutation(RUNEWOOD_ITEM_PEDESTAL, SOULWOOD_ITEM_PEDESTAL)
            .addTransmutation(RUNEWOOD_SIGN, SOULWOOD_SIGN)
            .addTransmutation(RUNEWOOD_WALL_SIGN, SOULWOOD_WALL_SIGN)
            .build(consumer, "soulwood_transmutation");

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.STONE, Blocks.COBBLESTONE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.COBBLESTONE, Blocks.GRAVEL)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.GRAVEL, Blocks.SAND)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.SAND, BLIGHTED_SOIL.get())
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.ANDESITE, Blocks.TUFF)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.TUFF, Blocks.STONE)
            .build(consumer, "tuff_block_to_stone");

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.DRIPSTONE_BLOCK, Blocks.STONE)
            .build(consumer, "dripstone_block_to_stone");

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.DIORITE, Blocks.CALCITE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.CALCITE, Blocks.STONE)
            .build(consumer, "calcite_to_stone");

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.COBBLED_DEEPSLATE, Blocks.BASALT)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.BASALT, Blocks.NETHERRACK)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.NETHERRACK, Blocks.SOUL_SAND)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.SOUL_SAND, Blocks.RED_SAND)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.RED_SAND, BLIGHTED_SOIL.get())
            .build(consumer, "red_sand_to_blighted_soil");

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.BASALT, Blocks.CLAY)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.CLAY, Blocks.PRISMARINE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.PRISMARINE, Blocks.SEA_LANTERN)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.SEA_LANTERN, Blocks.SNOW_BLOCK)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.SNOW_BLOCK, Blocks.ICE)
            .build(consumer);

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(Blocks.ICE, BLIGHTED_SOIL.get())
            .build(consumer, "ice_to_blighted_soil");

        new SpiritTransmutationRecipeBuilder()
            .addTransmutation(ItemRegistry.HEX_ASH.get(), ItemRegistry.CURSED_GRIT.get())
            .build(consumer);
    }
}
