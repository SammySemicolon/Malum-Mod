package com.sammy.malum.data.recipe.crafting;

import com.sammy.malum.registry.common.item.*;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;

import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.data.recipe.MalumVanillaRecipes.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.*;

public class MalumWoodenRecipes implements IConditionBuilder {

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        buildRecipes(consumer, new MalumDatagenWoodSet(
                "runewood",
                ItemRegistry.RUNEWOOD_LOG.get(), ItemRegistry.RUNEWOOD.get(),
                ItemRegistry.STRIPPED_RUNEWOOD_LOG.get(), ItemRegistry.STRIPPED_RUNEWOOD.get(),
                ItemRegistry.REVEALED_RUNEWOOD_LOG.get(), ItemRegistry.EXPOSED_RUNEWOOD_LOG.get(),
                ItemRegistry.RUNEWOOD_BOARDS.get(), ItemRegistry.VERTICAL_RUNEWOOD_BOARDS.get(),
                ItemRegistry.RUNEWOOD_BOARDS_SLAB.get(), ItemRegistry.VERTICAL_RUNEWOOD_BOARDS_SLAB.get(),
                ItemRegistry.RUNEWOOD_BOARDS_STAIRS.get(), ItemRegistry.VERTICAL_RUNEWOOD_BOARDS_STAIRS.get(),
                ItemRegistry.RUNEWOOD_PLANKS.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get(), ItemRegistry.RUNEWOOD_TILES.get(),
                ItemRegistry.RUSTIC_RUNEWOOD_PLANKS.get(), ItemRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS.get(), ItemRegistry.RUSTIC_RUNEWOOD_TILES.get(),
                ItemRegistry.RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.RUNEWOOD_TILES_SLAB.get(),
                ItemRegistry.RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.RUSTIC_RUNEWOOD_TILES_SLAB.get(),
                ItemRegistry.RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.RUNEWOOD_TILES_STAIRS.get(),
                ItemRegistry.RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.RUSTIC_RUNEWOOD_TILES_STAIRS.get(),
                ItemRegistry.RUNEWOOD_PANEL.get(), ItemRegistry.CUT_RUNEWOOD_PLANKS.get(), ItemRegistry.RUNEWOOD_BEAM.get(),
                ItemRegistry.RUNEWOOD_BUTTON.get(), ItemRegistry.RUNEWOOD_PRESSURE_PLATE.get(),
                ItemRegistry.SOLID_RUNEWOOD_TRAPDOOR.get(), ItemRegistry.RUNEWOOD_TRAPDOOR.get(),
                ItemRegistry.RUNEWOOD_FENCE.get(), ItemRegistry.RUNEWOOD_FENCE_GATE.get(),
                ItemRegistry.RUNEWOOD_DOOR.get(),
                ItemRegistry.RUNEWOOD_SIGN.get(), ItemRegistry.RUNEWOOD_SIGN.get(),
                ItemRegistry.RUNEWOOD_ITEM_STAND.get(), ItemRegistry.RUNEWOOD_ITEM_PEDESTAL.get(),
                ItemTagRegistry.RUNEWOOD_LOGS, ItemTagRegistry.RUNEWOOD_BOARD_INGREDIENT, ItemTagRegistry.RUNEWOOD_PLANKS, ItemTagRegistry.RUNEWOOD_SLABS,
                ItemRegistry.RUNEWOOD_BOAT.get()
        ));

        buildRecipes(consumer, new MalumDatagenWoodSet(
                "soulwood",
                ItemRegistry.SOULWOOD_LOG.get(), ItemRegistry.SOULWOOD.get(),
                ItemRegistry.STRIPPED_SOULWOOD_LOG.get(), ItemRegistry.STRIPPED_SOULWOOD.get(),
                ItemRegistry.REVEALED_SOULWOOD_LOG.get(), ItemRegistry.EXPOSED_SOULWOOD_LOG.get(),
                ItemRegistry.SOULWOOD_BOARDS.get(), ItemRegistry.VERTICAL_SOULWOOD_BOARDS.get(),
                ItemRegistry.SOULWOOD_BOARDS_SLAB.get(), ItemRegistry.VERTICAL_SOULWOOD_BOARDS_SLAB.get(),
                ItemRegistry.SOULWOOD_BOARDS_STAIRS.get(), ItemRegistry.VERTICAL_SOULWOOD_BOARDS_STAIRS.get(),
                ItemRegistry.SOULWOOD_PLANKS.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get(), ItemRegistry.SOULWOOD_TILES.get(),
                ItemRegistry.RUSTIC_SOULWOOD_PLANKS.get(), ItemRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS.get(), ItemRegistry.RUSTIC_SOULWOOD_TILES.get(),
                ItemRegistry.SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.SOULWOOD_TILES_SLAB.get(),
                ItemRegistry.RUSTIC_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.RUSTIC_SOULWOOD_TILES_SLAB.get(),
                ItemRegistry.SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.SOULWOOD_TILES_STAIRS.get(),
                ItemRegistry.RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.RUSTIC_SOULWOOD_TILES_STAIRS.get(),
                ItemRegistry.SOULWOOD_PANEL.get(), ItemRegistry.CUT_SOULWOOD_PLANKS.get(), ItemRegistry.SOULWOOD_BEAM.get(),
                ItemRegistry.SOULWOOD_BUTTON.get(), ItemRegistry.SOULWOOD_PRESSURE_PLATE.get(),
                ItemRegistry.SOLID_SOULWOOD_TRAPDOOR.get(), ItemRegistry.SOULWOOD_TRAPDOOR.get(),
                ItemRegistry.SOULWOOD_FENCE.get(), ItemRegistry.SOULWOOD_FENCE_GATE.get(),
                ItemRegistry.SOULWOOD_DOOR.get(),
                ItemRegistry.SOULWOOD_SIGN.get(), ItemRegistry.SOULWOOD_SIGN.get(),
                ItemRegistry.SOULWOOD_ITEM_STAND.get(), ItemRegistry.SOULWOOD_ITEM_PEDESTAL.get(),
                ItemTagRegistry.SOULWOOD_LOGS, ItemTagRegistry.SOULWOOD_BOARD_INGREDIENT, ItemTagRegistry.SOULWOOD_PLANKS, ItemTagRegistry.SOULWOOD_SLABS,
                ItemRegistry.SOULWOOD_BOAT.get()
        ));
    }
    protected static void buildRecipes(Consumer<FinishedRecipe> consumer, MalumDatagenWoodSet woodSet) {
        shapelessPlanks(consumer, woodSet.planks, woodSet.logTag);

        shapedBoards(consumer, woodSet.boards, woodSet.logWithBarkTag);

        shapedSlab(consumer, woodSet.boardsSlab, woodSet.boards);
        shapedStairs(consumer, woodSet.boardsStairs, woodSet.boards);
        shapedSlab(consumer, woodSet.verticalBoardsSlab, woodSet.verticalBoards);
        shapedStairs(consumer, woodSet.verticalBoardsStairs, woodSet.verticalBoards);

        planksExchange(consumer, woodSet.boards, woodSet.verticalBoards, woodSet.prefix + "_vertical_boards_exchange");
        planksExchange(consumer, woodSet.verticalBoards, woodSet.boards, woodSet.prefix + "_boards_exchange");

        shapedSlab(consumer, woodSet.planksSlab, woodSet.planks);
        shapedStairs(consumer, woodSet.planksStairs, woodSet.planks);
        shapedSlab(consumer, woodSet.verticalPlanksSlab, woodSet.verticalPlanks);
        shapedStairs(consumer, woodSet.verticalPlanksStairs, woodSet.verticalPlanks);
        shapedSlab(consumer, woodSet.tilesSlab, woodSet.tiles);
        shapedStairs(consumer, woodSet.tilesStairs, woodSet.tiles);

        shapedSlab(consumer, woodSet.rusticPlanksSlab, woodSet.rusticPlanks);
        shapedStairs(consumer, woodSet.rusticPlanksStairs, woodSet.rusticPlanks);
        shapedSlab(consumer, woodSet.verticalRusticPlanksSlab, woodSet.verticalRusticPlanks);
        shapedStairs(consumer, woodSet.verticalRusticPlanksStairs, woodSet.verticalRusticPlanks);
        shapedSlab(consumer, woodSet.rusticTilesSlab, woodSet.rusticTiles);
        shapedStairs(consumer, woodSet.rusticTilesStairs, woodSet.rusticTiles);

        shapelessWood(consumer, woodSet.wood, woodSet.log);
        shapelessWood(consumer, woodSet.strippedWood, woodSet.strippedLog);
        shapelessButton(consumer, woodSet.button, woodSet.planksTag);
        shapedDoor(consumer, woodSet.door, woodSet.planksTag);
        shapedFence(consumer, woodSet.fence, woodSet.planksTag);
        shapedFenceGate(consumer, woodSet.fenceGate, woodSet.planksTag);
        shapedPressurePlate(consumer, woodSet.pressurePlate, woodSet.planksTag);

        shapedTrapdoor(consumer, woodSet.solidTrapdoor, woodSet.planksTag);

        shapedSign(consumer, woodSet.sign, woodSet.planksTag);

        trapdoorExchange(consumer, woodSet.solidTrapdoor, woodSet.openTrapdoor, woodSet.prefix + "_open_trapdoor_exchange");
        trapdoorExchange(consumer, woodSet.openTrapdoor, woodSet.solidTrapdoor, woodSet.prefix + "_solid_trapdoor_exchange");

        planksExchange(consumer, woodSet.planks, woodSet.verticalPlanks, woodSet.prefix + "_vertical_planks_exchange");
        planksExchange(consumer, woodSet.verticalPlanks, woodSet.tiles, woodSet.prefix + "_tiles_exchange");
        planksExchange(consumer, woodSet.tiles, woodSet.planks, woodSet.prefix + "_planks_exchange");

        planksExchange(consumer, woodSet.rusticPlanks, woodSet.verticalRusticPlanks, woodSet.prefix + "_vertical_rustic_planks_exchange");
        planksExchange(consumer, woodSet.verticalRusticPlanks, woodSet.rusticTiles, woodSet.prefix + "_rustic_tiles_exchange");
        planksExchange(consumer, woodSet.rusticTiles, woodSet.rusticPlanks, woodSet.prefix + "_rustic_planks_exchange");

        shapedBoat(consumer, woodSet.boat, woodSet.planksTag);

        shapedPanel(consumer, woodSet.panel, woodSet.planksTag);

        shaped(RecipeCategory.MISC, woodSet.cutPlanks, 2)
                .define('X', woodSet.panel)
                .define('Y', woodSet.planksTag)
                .pattern("X").pattern("Y")
                .unlockedBy("has_input", has(woodSet.planksTag))
                .save(consumer);
        shaped(RecipeCategory.MISC, woodSet.beam, 3)
                .define('#', woodSet.planksTag)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_input", has(woodSet.planksTag))
                .save(consumer);

        shaped(RecipeCategory.MISC, woodSet.itemStand, 2)
                .define('X', woodSet.planksTag)
                .define('Y', woodSet.slabTag)
                .pattern("YYY")
                .pattern("XXX")
                .unlockedBy("has_input", has(woodSet.planksTag))
                .save(consumer);
        shaped(RecipeCategory.MISC, woodSet.itemPedestal)
                .define('X', woodSet.planksTag)
                .define('Y', woodSet.slabTag)
                .pattern("YYY")
                .pattern(" X ")
                .pattern("YYY")
                .unlockedBy("has_input", has(woodSet.planksTag))
                .save(consumer);

    }

    private static void trapdoorExchange(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike output, String path) {
        shapeless(RecipeCategory.MISC, output)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer, malumPath(path));
    }

    private static void planksExchange(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike planks, String path) {
        shaped(RecipeCategory.MISC, planks, 4)
                .define('#', input)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer, malumPath(path));
    }

    private static void shapelessPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planks, TagKey<Item> input) {
        shapeless(RecipeCategory.MISC, planks, 4)
                .requires(input)
                .group("planks")
                .unlockedBy("has_logs", has(input))
                .save(recipeConsumer);
    }

    private static void shapedBoards(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, output, 16)
                .define('#', input)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedPanel(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, output, 5)
                .define('#', input)
                .pattern(" # ")
                .pattern("###")
                .pattern(" # ")
                .unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapelessWood(Consumer<FinishedRecipe> recipeConsumer, ItemLike stripped, ItemLike input) {
        shaped(RecipeCategory.MISC, stripped, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .group("bark")
                .unlockedBy("has_log", has(input))
                .save(recipeConsumer);
    }

    private static void shapelessButton(Consumer<FinishedRecipe> recipeConsumer, ItemLike button, TagKey<Item> input) {
        shapeless(RecipeCategory.MISC, button)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedDoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike door, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, door, 3)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedFence(Consumer<FinishedRecipe> recipeConsumer, ItemLike fence, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, fence, 3)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', input)
                .pattern("W#W")
                .pattern("W#W")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedFenceGate(Consumer<FinishedRecipe> recipeConsumer, ItemLike fenceGate, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, fenceGate)
                .define('#', Tags.Items.RODS_WOODEN)
                .define('W', input)
                .pattern("#W#")
                .pattern("#W#")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedPressurePlate(Consumer<FinishedRecipe> recipeConsumer, ItemLike pressurePlate, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, pressurePlate)
                .define('#', input)
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedSlab(Consumer<FinishedRecipe> recipeConsumer, ItemLike slab, ItemLike input) {
        shaped(RecipeCategory.MISC, slab, 6)
                .define('#', input)
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, ItemLike input) {
        shaped(RecipeCategory.MISC, stairs, 4)
                .define('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike trapdoor, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, trapdoor, 2)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedSign(Consumer<FinishedRecipe> recipeConsumer, ItemLike sign, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, sign, 3)
                .group("sign")
                .define('#', input)
                .define('X', Tags.Items.RODS_WOODEN)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
    }

    private static void shapedBoat(Consumer<FinishedRecipe> recipeConsumer, ItemLike boat, TagKey<Item> input) {
        shaped(RecipeCategory.MISC, boat)
                .define('#', input)
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    public record MalumDatagenWoodSet(
            String prefix,

            Item log, Item wood,
            Item strippedLog, Item strippedWood,

            Item sapFilledLog, Item strippedSapFilledLog,

            Item boards, Item verticalBoards,
            Item boardsSlab, Item verticalBoardsSlab,
            Item boardsStairs, Item verticalBoardsStairs,

            Item planks, Item verticalPlanks, Item tiles,
            Item rusticPlanks, Item verticalRusticPlanks, Item rusticTiles,
            Item planksSlab, Item verticalPlanksSlab, Item tilesSlab,
            Item rusticPlanksSlab, Item verticalRusticPlanksSlab, Item rusticTilesSlab,
            Item planksStairs, Item verticalPlanksStairs, Item tilesStairs,
            Item rusticPlanksStairs, Item verticalRusticPlanksStairs, Item rusticTilesStairs,

            Item panel, Item cutPlanks, Item beam,

            Item button, Item pressurePlate,

            Item solidTrapdoor, Item openTrapdoor,

            Item fence, Item fenceGate,

            Item door,

            Item sign, Item hangingSign,

            Item itemStand, Item itemPedestal,

            TagKey<Item> logTag, TagKey<Item> logWithBarkTag, TagKey<Item> planksTag, TagKey<Item> slabTag,

            Item boat
    ) {
    }
}