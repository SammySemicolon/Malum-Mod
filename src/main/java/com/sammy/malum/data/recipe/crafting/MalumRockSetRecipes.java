package com.sammy.malum.data.recipe.crafting;

import com.sammy.malum.registry.common.item.*;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;

import java.util.function.*;

import static com.sammy.malum.data.recipe.MalumVanillaRecipes.*;
import static net.minecraft.data.recipes.RecipeBuilder.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.*;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.*;
import static net.minecraft.data.recipes.SingleItemRecipeBuilder.*;

public class MalumRockSetRecipes {

    private static MalumRockSetRecipes.MalumDatagenRockSet cachedRockSet;

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        buildRecipes(consumer, new MalumRockSetRecipes.MalumDatagenRockSet(
                "tainted_rock",
                ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.SMOOTH_TAINTED_ROCK.get(), ItemRegistry.POLISHED_TAINTED_ROCK.get(),
                ItemRegistry.TAINTED_ROCK_SLAB.get(), ItemRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), ItemRegistry.POLISHED_TAINTED_ROCK_SLAB.get(),
                ItemRegistry.TAINTED_ROCK_STAIRS.get(), ItemRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), ItemRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(),

                ItemRegistry.TAINTED_ROCK_BRICKS.get(), ItemRegistry.TAINTED_ROCK_TILES.get(), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get(),
                ItemRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), ItemRegistry.TAINTED_ROCK_TILES_SLAB.get(), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(),
                ItemRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), ItemRegistry.TAINTED_ROCK_TILES_STAIRS.get(), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(),

                ItemRegistry.RUNIC_TAINTED_ROCK_BRICKS.get(), ItemRegistry.RUNIC_TAINTED_ROCK_TILES.get(), ItemRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS.get(),
                ItemRegistry.RUNIC_TAINTED_ROCK_BRICKS_SLAB.get(), ItemRegistry.RUNIC_TAINTED_ROCK_TILES_SLAB.get(), ItemRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(),
                ItemRegistry.RUNIC_TAINTED_ROCK_BRICKS_STAIRS.get(), ItemRegistry.RUNIC_TAINTED_ROCK_TILES_STAIRS.get(), ItemRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(),

                ItemRegistry.TAINTED_ROCK_COLUMN.get(), ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get(),

                ItemRegistry.CUT_TAINTED_ROCK.get(), ItemRegistry.CHECKERED_TAINTED_ROCK.get(),

                ItemRegistry.CHISELED_TAINTED_ROCK.get(),

                ItemRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), ItemRegistry.TAINTED_ROCK_BUTTON.get(),

                ItemRegistry.TAINTED_ROCK_WALL.get(), ItemRegistry.SMOOTH_TAINTED_ROCK_WALL.get(), ItemRegistry.POLISHED_TAINTED_ROCK_WALL.get(),
                ItemRegistry.TAINTED_ROCK_BRICKS_WALL.get(), ItemRegistry.TAINTED_ROCK_TILES_WALL.get(), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(),
                ItemRegistry.RUNIC_TAINTED_ROCK_BRICKS_WALL.get(), ItemRegistry.RUNIC_TAINTED_ROCK_TILES_WALL.get(), ItemRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL.get(),

                ItemRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get(), ItemRegistry.TAINTED_ROCK_ITEM_STAND.get(),

                ItemTagRegistry.TAINTED_BLOCKS, ItemTagRegistry.TAINTED_SLABS
        ));
        buildRecipes(consumer, new MalumRockSetRecipes.MalumDatagenRockSet(
                "twisted_rock",
                ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.SMOOTH_TWISTED_ROCK.get(), ItemRegistry.POLISHED_TWISTED_ROCK.get(),
                ItemRegistry.TWISTED_ROCK_SLAB.get(), ItemRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), ItemRegistry.POLISHED_TWISTED_ROCK_SLAB.get(),
                ItemRegistry.TWISTED_ROCK_STAIRS.get(), ItemRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), ItemRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(),

                ItemRegistry.TWISTED_ROCK_BRICKS.get(), ItemRegistry.TWISTED_ROCK_TILES.get(), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get(),
                ItemRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), ItemRegistry.TWISTED_ROCK_TILES_SLAB.get(), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(),
                ItemRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), ItemRegistry.TWISTED_ROCK_TILES_STAIRS.get(), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(),

                ItemRegistry.RUNIC_TWISTED_ROCK_BRICKS.get(), ItemRegistry.RUNIC_TWISTED_ROCK_TILES.get(), ItemRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS.get(),
                ItemRegistry.RUNIC_TWISTED_ROCK_BRICKS_SLAB.get(), ItemRegistry.RUNIC_TWISTED_ROCK_TILES_SLAB.get(), ItemRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(),
                ItemRegistry.RUNIC_TWISTED_ROCK_BRICKS_STAIRS.get(), ItemRegistry.RUNIC_TWISTED_ROCK_TILES_STAIRS.get(), ItemRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(),

                ItemRegistry.TWISTED_ROCK_COLUMN.get(), ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get(),

                ItemRegistry.CUT_TWISTED_ROCK.get(), ItemRegistry.CHECKERED_TWISTED_ROCK.get(),

                ItemRegistry.CHISELED_TWISTED_ROCK.get(),

                ItemRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), ItemRegistry.TWISTED_ROCK_BUTTON.get(),

                ItemRegistry.TWISTED_ROCK_WALL.get(), ItemRegistry.SMOOTH_TWISTED_ROCK_WALL.get(), ItemRegistry.POLISHED_TWISTED_ROCK_WALL.get(),
                ItemRegistry.TWISTED_ROCK_BRICKS_WALL.get(), ItemRegistry.TWISTED_ROCK_TILES_WALL.get(), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(),
                ItemRegistry.RUNIC_TWISTED_ROCK_BRICKS_WALL.get(), ItemRegistry.RUNIC_TWISTED_ROCK_TILES_WALL.get(), ItemRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL.get(),

                ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), ItemRegistry.TWISTED_ROCK_ITEM_STAND.get(),

                ItemTagRegistry.TWISTED_BLOCKS, ItemTagRegistry.TWISTED_SLABS
        ));
    }
    protected static void buildRecipes(Consumer<FinishedRecipe> consumer, MalumRockSetRecipes.MalumDatagenRockSet rockSet) {
        var condition = has(rockSet.rock);
        cachedRockSet = rockSet;
        shapedSlab(consumer, rockSet.rockSlab, rockSet.rock);
        shapedSlab(consumer, rockSet.smoothRockSlab, rockSet.smoothRock);
        shapedSlab(consumer, rockSet.polishedRockSlab, rockSet.polishedRock);
        shapedStairs(consumer, rockSet.rockStairs, rockSet.rock);
        shapedStairs(consumer, rockSet.smoothRockStairs, rockSet.smoothRock);
        shapedStairs(consumer, rockSet.polishedRockStairs, rockSet.polishedRock);
        shapedWall(consumer, rockSet.rockWall, rockSet.rock);
        shapedWall(consumer, rockSet.smoothRockWall, rockSet.smoothRock);
        shapedWall(consumer, rockSet.polishedRockWall, rockSet.polishedRock);

        shapedSlab(consumer, rockSet.bricksSlab, rockSet.bricks);
        shapedSlab(consumer, rockSet.tilesSlab, rockSet.tiles);
        shapedSlab(consumer, rockSet.smallBricksSlab, rockSet.smallBricks);
        shapedStairs(consumer, rockSet.bricksStairs, rockSet.bricks);
        shapedStairs(consumer, rockSet.tilesStairs, rockSet.tiles);
        shapedStairs(consumer, rockSet.smallBricksStairs, rockSet.smallBricks);
        shapedWall(consumer, rockSet.bricksWall, rockSet.bricks);
        shapedWall(consumer, rockSet.tilesWall, rockSet.tiles);
        shapedWall(consumer, rockSet.smallBricksWall, rockSet.smallBricks);

        shapedSlab(consumer, rockSet.runicBricksSlab, rockSet.runicBricks);
        shapedSlab(consumer, rockSet.runicTilesSlab, rockSet.runicTiles);
        shapedSlab(consumer, rockSet.runicSmallBricksSlab, rockSet.runicSmallBricks);
        shapedStairs(consumer, rockSet.runicBricksStairs, rockSet.runicBricks);
        shapedStairs(consumer, rockSet.runicTilesStairs, rockSet.runicTiles);
        shapedStairs(consumer, rockSet.runicSmallBricksStairs, rockSet.runicSmallBricks);
        shapedWall(consumer, rockSet.runicBricksWall, rockSet.bricks);
        shapedWall(consumer, rockSet.runicTilesWall, rockSet.tiles);
        shapedWall(consumer, rockSet.runicSmallBricksWall, rockSet.smallBricks);

        rockExchange(consumer, rockSet.bricks, rockSet.rock);

        rockExchange(consumer, rockSet.tiles, rockSet.bricks);
        rockExchange(consumer, rockSet.smallBricks, rockSet.tiles);
        rockExchange(consumer, rockSet.bricks, rockSet.smallBricks);

        runicExchange(consumer, rockSet.runicBricks, rockSet.bricks);
        runicExchange(consumer, rockSet.runicTiles, rockSet.tiles);
        runicExchange(consumer, rockSet.runicSmallBricks, rockSet.smallBricks);

        rockExchange(consumer, rockSet.runicTiles, rockSet.runicBricks);
        rockExchange(consumer, rockSet.runicSmallBricks, rockSet.runicTiles);
        rockExchange(consumer, rockSet.runicBricks, rockSet.runicSmallBricks);

        shapelessButton(consumer, rockSet.button, rockSet.rock);
        shapedPressurePlate(consumer, rockSet.pressurePlate, rockSet.rock);

        runicExchange(consumer, rockSet.smoothRock, rockSet.rock);
        rockExchange(consumer, rockSet.polishedRock, rockSet.smoothRock);

        shaped(RecipeCategory.MISC, rockSet.cutRock, 2)
                .define('X', rockSet.polishedRock)
                .define('Y', rockSet.rock)
                .pattern("X")
                .pattern("Y")
                .unlockedBy("has_input", condition)
                .save(consumer);
        stoneCutting(consumer, rockSet.rock, rockSet.cutRock);
        stoneCutting(consumer, rockSet.polishedRock, rockSet.cutRock);

        runicExchange(consumer, rockSet.checkeredRock, rockSet.cutRock);

        shaped(RecipeCategory.MISC, rockSet.column, 3)
                .define('#', rockSet.rock)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_input", condition)
                .save(consumer);
        stoneCutting(consumer, rockSet.rock, rockSet.column);

        shaped(RecipeCategory.MISC, rockSet.columnCap, 2)
                .define('X', rockSet.polishedRock)
                .define('Y', rockSet.column)
                .pattern("X")
                .pattern("Y")
                .unlockedBy("has_input", condition)
                .save(consumer);
        stoneCutting(consumer, rockSet.rock, rockSet.columnCap);
        stoneCutting(consumer, rockSet.polishedRock, rockSet.columnCap);

        shaped(RecipeCategory.MISC, rockSet.itemStand, 2)
                .define('X', rockSet.rock)
                .define('Y', rockSet.rockSlab)
                .pattern("YYY")
                .pattern("XXX")
                .unlockedBy("has_input", condition)
                .save(consumer);
        stoneCutting(consumer, rockSet.rock, rockSet.itemStand);

        shaped(RecipeCategory.MISC, rockSet.itemPedestal)
                .define('X', rockSet.rock)
                .define('Y', rockSet.rockSlab)
                .pattern("YYY")
                .pattern(" X ")
                .pattern("YYY")
                .unlockedBy("has_input", condition)
                .save(consumer);
        stoneCutting(consumer, rockSet.rock, rockSet.itemPedestal);
    }


    private static void rockExchange(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike input) {
        final ResourceLocation recipeID = getDefaultRecipeId(output).withSuffix("_from_" + getDefaultRecipeId(input).getPath());
        shaped(RecipeCategory.MISC, output, 4)
                .define('#', input)
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer, recipeID);
        stoneCutting(recipeConsumer, input, output);
    }

    private static void runicExchange(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike input) {
        smelting(Ingredient.of(input), RecipeCategory.MISC, output, 0.1f, 200)
                .unlockedBy("has_input", has(input)).save(recipeConsumer);
        stoneCutting(recipeConsumer, input, output);
    }

    private static void shapelessButton(Consumer<FinishedRecipe> recipeConsumer, ItemLike button, Item input) {
        shapeless(RecipeCategory.MISC, button)
                .requires(input)
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
        stoneCutting(recipeConsumer, input, button);
    }

    private static void shapedPressurePlate(Consumer<FinishedRecipe> recipeConsumer, ItemLike pressurePlate, Item input) {
        shaped(RecipeCategory.MISC, pressurePlate)
                .define('#', input)
                .pattern("##")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
        stoneCutting(recipeConsumer, input, pressurePlate);
    }

    private static void shapedSlab(Consumer<FinishedRecipe> recipeConsumer, ItemLike slab, Item input) {
        shaped(RecipeCategory.MISC, slab, 6)
                .define('#', input)
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
        stoneCutting(recipeConsumer, input, slab, 2);
        if (!input.equals(cachedRockSet.rock)) {
            stoneCutting(recipeConsumer, cachedRockSet.rock, slab, 2);
        }
    }

    private static void shapedStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, Item input) {
        shaped(RecipeCategory.MISC, stairs, 4)
                .define('#', input)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
        stoneCutting(recipeConsumer, input, stairs);
        if (!input.equals(cachedRockSet.rock)) {
            stoneCutting(recipeConsumer, cachedRockSet.rock, stairs);
        }
    }

    private static void shapedWall(Consumer<FinishedRecipe> recipeConsumer, ItemLike wall, Item input) {
        shaped(RecipeCategory.MISC, wall, 6)
                .define('#', input)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_input", has(input))
                .save(recipeConsumer);
        stoneCutting(recipeConsumer, input, wall, 1);
        if (!input.equals(cachedRockSet.rock)) {
            stoneCutting(recipeConsumer, cachedRockSet.rock, wall, 1);
        }
    }

    private static void stoneCutting(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike output) {
        stoneCutting(recipeConsumer, input, output, 1);
    }
    private static void stoneCutting(Consumer<FinishedRecipe> recipeConsumer, ItemLike input, ItemLike output, int outputCount) {
        final ResourceLocation recipeID = getDefaultRecipeId(output).withSuffix("_stonecutting_from_" + getDefaultRecipeId(input).getPath());
        stonecutting(Ingredient.of(input), RecipeCategory.MISC, output, outputCount).unlockedBy("has_input", has(input)).save(recipeConsumer, recipeID);
    }

    public record MalumDatagenRockSet(
            String prefix,

            Item rock, Item smoothRock, Item polishedRock,
            Item rockSlab, Item smoothRockSlab, Item polishedRockSlab,
            Item rockStairs, Item smoothRockStairs, Item polishedRockStairs,

            Item bricks, Item tiles, Item smallBricks,
            Item bricksSlab, Item tilesSlab, Item smallBricksSlab,
            Item bricksStairs, Item tilesStairs, Item smallBricksStairs,

            Item runicBricks, Item runicTiles, Item runicSmallBricks,
            Item runicBricksSlab, Item runicTilesSlab, Item runicSmallBricksSlab,
            Item runicBricksStairs, Item runicTilesStairs, Item runicSmallBricksStairs,

            Item column, Item columnCap,

            Item cutRock, Item checkeredRock,

            Item chiseledRock,

            Item pressurePlate, Item button,

            Item rockWall, Item smoothRockWall, Item polishedRockWall,
            Item bricksWall, Item tilesWall, Item smallBricksWall,
            Item runicBricksWall, Item runicTilesWall, Item runicSmallBricksWall,

            Item itemStand, Item itemPedestal,

            TagKey<Item> blockTag, TagKey<Item> slabTag
            ) { }
}
