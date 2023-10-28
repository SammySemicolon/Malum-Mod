package com.sammy.malum.data.recipe;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.data.recipe.builder.SpiritTransmutationRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Consumer;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumSpiritTransmutationRecipes extends RecipeProvider {
    public MalumSpiritTransmutationRecipes(PackOutput generatorIn) {
        super(generatorIn);
    }

    private static final List<Pair<RegistryObject<Block>, RegistryObject<Block>>> SOULWOOD_TRANSMUTATIONS = List.of(
            new Pair<>(RUNEWOOD_SAPLING, SOULWOOD_GROWTH),
            new Pair<>(RUNEWOOD_LEAVES, SOULWOOD_LEAVES),
            new Pair<>(STRIPPED_RUNEWOOD_LOG, STRIPPED_SOULWOOD_LOG),
            new Pair<>(RUNEWOOD_LOG, SOULWOOD_LOG),
            new Pair<>(STRIPPED_RUNEWOOD, STRIPPED_SOULWOOD),
            new Pair<>(RUNEWOOD, SOULWOOD),
            new Pair<>(REVEALED_RUNEWOOD_LOG, REVEALED_SOULWOOD_LOG),
            new Pair<>(EXPOSED_RUNEWOOD_LOG, EXPOSED_SOULWOOD_LOG),
            new Pair<>(RUNEWOOD_PLANKS, SOULWOOD_PLANKS),
            new Pair<>(RUNEWOOD_PLANKS_SLAB, SOULWOOD_PLANKS_SLAB),
            new Pair<>(RUNEWOOD_PLANKS_STAIRS, SOULWOOD_PLANKS_STAIRS),
            new Pair<>(VERTICAL_RUNEWOOD_PLANKS, VERTICAL_SOULWOOD_PLANKS),
            new Pair<>(VERTICAL_RUNEWOOD_PLANKS_SLAB, VERTICAL_SOULWOOD_PLANKS_SLAB),
            new Pair<>(VERTICAL_RUNEWOOD_PLANKS_STAIRS, VERTICAL_SOULWOOD_PLANKS_STAIRS),
            new Pair<>(RUNEWOOD_PANEL, SOULWOOD_PANEL),
            new Pair<>(RUNEWOOD_PANEL_SLAB, SOULWOOD_PANEL_SLAB),
            new Pair<>(RUNEWOOD_PANEL_STAIRS, SOULWOOD_PANEL_STAIRS),
            new Pair<>(RUNEWOOD_TILES, SOULWOOD_TILES),
            new Pair<>(RUNEWOOD_TILES_SLAB, SOULWOOD_TILES_SLAB),
            new Pair<>(RUNEWOOD_TILES_STAIRS, SOULWOOD_TILES_STAIRS),
            new Pair<>(CUT_RUNEWOOD_PLANKS, CUT_SOULWOOD_PLANKS),
            new Pair<>(RUNEWOOD_BEAM, SOULWOOD_BEAM),
            new Pair<>(RUNEWOOD_DOOR, SOULWOOD_DOOR),
            new Pair<>(RUNEWOOD_TRAPDOOR, SOULWOOD_TRAPDOOR),
            new Pair<>(SOLID_RUNEWOOD_TRAPDOOR, SOLID_SOULWOOD_TRAPDOOR),
            new Pair<>(RUNEWOOD_BUTTON, SOULWOOD_BUTTON),
            new Pair<>(RUNEWOOD_PRESSURE_PLATE, SOULWOOD_PRESSURE_PLATE),
            new Pair<>(RUNEWOOD_FENCE, SOULWOOD_FENCE),
            new Pair<>(RUNEWOOD_FENCE_GATE, SOULWOOD_FENCE_GATE),
            new Pair<>(RUNEWOOD_ITEM_STAND, SOULWOOD_ITEM_STAND),
            new Pair<>(RUNEWOOD_ITEM_PEDESTAL, SOULWOOD_ITEM_PEDESTAL),
            new Pair<>(RUNEWOOD_SIGN, SOULWOOD_SIGN) // Wall sign already handled by this. Is it??? Wire? Huh ? How
    );

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        for (var transmutation : SOULWOOD_TRANSMUTATIONS) {
            new SpiritTransmutationRecipeBuilder(transmutation.getFirst(), transmutation.getSecond())
                    .group("soulwood")
                    .build(consumer, "soulwood/" + transmutation.getSecond().getId().getPath().replace("soulwood_", "").replace("_soulwood", ""));
        }

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
                .build(consumer, "tuff_block_to_stone");

        new SpiritTransmutationRecipeBuilder(Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK)
                .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.DRIPSTONE_BLOCK, Blocks.STONE)
                .build(consumer, "dripstone_block_to_stone");

        new SpiritTransmutationRecipeBuilder(Blocks.DIORITE, Blocks.CALCITE)
                .build(consumer);

        new SpiritTransmutationRecipeBuilder(Blocks.CALCITE, Blocks.STONE)
                .build(consumer, "calcite_to_stone");

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
                .build(consumer, "red_sand_to_blighted_soil");

        new SpiritTransmutationRecipeBuilder(Blocks.SMOOTH_BASALT, Blocks.CLAY)
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
                .build(consumer, "ice_to_blighted_soil");
    }
}
