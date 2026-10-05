package com.sammy.malum.registry.common.util.building;

import com.sammy.malum.common.block.building.ColumnBlock;
import com.sammy.malum.common.block.storage.pedestal.ItemPedestalBlock;
import com.sammy.malum.common.block.storage.stand.ItemStandBlock;
import com.sammy.malum.datagen.block.MalumBlockStateDatagen;
import com.sammy.malum.datagen.block.MalumBlockStateSmithTypes;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.util.MalumRegistrySet;
import com.sammy.malum.registry.common.util.data.BlockBundle;
import com.sammy.malum.registry.common.util.data.BlockBundleWithWall;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import team.lodestar.lodestone.modules.core.util.BlockItemTagKey;
import team.lodestar.lodestone.modules.datagen.BlockStateSmithTypes;
import team.lodestar.lodestone.modules.datagen.smith.blockstate.BlockStateSystemData;
import team.lodestar.lodestone.modules.toolkit.block.BlockBlockItemHolder;
import team.lodestar.lodestone.modules.toolkit.block.LodestoneBlockProperties;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CreativeTabCategoryBuilder;

import java.util.List;
import java.util.function.Supplier;

import static com.sammy.malum.registry.common.MalumTags.Blocks.*;
import static net.minecraft.tags.BlockTags.*;

public class RockBlockSet extends MalumRegistrySet {


    public static List<RockBlockSet> getMalumSets() {
        return List.of(MalumContent.BuildingBlocks.TAINTED_ROCK_SET, MalumContent.BuildingBlocks.TWISTED_ROCK_SET);
    }

    public final BlockItemTagKey blocksTag;
    public final BlockItemTagKey stairsTag;
    public final BlockItemTagKey slabsTag;
    public final BlockItemTagKey wallsTag;

    public final BlockBundleWithWall rock, polishedRock, bricks;
    public final BlockBundleWithWall tiles, grid, mosaic;

    public final BlockBlockItemHolder<Block, BlockItem> column, altar;
    public final BlockBlockItemHolder<Block, BlockItem> button, pressurePlate;
    public final BlockBlockItemHolder<Block, BlockItem> itemPedestal, itemStand;


    public RockBlockSet(String id,
                        Supplier<LodestoneBlockProperties> rockProperties,
                        Supplier<LodestoneBlockProperties> brickProperties,
                        Supplier<LodestoneBlockProperties> chiseledProperties) {
        super(id);

        blocksTag = createTag("blocks");
        stairsTag = createTag("stairs");
        slabsTag = createTag("slabs");
        wallsTag = createTag("walls");

        rock = new BlockBundleWithWall(name("%s"), rockProperties, blocksTag, slabsTag, stairsTag, wallsTag);
        polishedRock = new BlockBundleWithWall(name("polished_%s"), rockProperties, blocksTag, slabsTag, stairsTag, wallsTag);
        bricks = new BlockBundleWithWall(name("%s_bricks"), brickProperties, blocksTag, slabsTag, stairsTag, wallsTag);
        tiles = new BlockBundleWithWall(name("%s_tiles"), brickProperties, blocksTag, slabsTag, stairsTag, wallsTag);
        grid = new BlockBundleWithWall(name("%s_grid"), brickProperties, blocksTag, slabsTag, stairsTag, wallsTag);
        mosaic = new BlockBundleWithWall(name("%s_mosaic"), brickProperties, blocksTag, slabsTag, stairsTag, wallsTag);

        column = MalumContent.registerBlock(name("%s_column"), () -> new ColumnBlock(chiseledProperties.get()));
        altar = MalumContent.registerBlock(name("%s_altar"), () -> new Block(chiseledProperties.get().addTag(EIDOLON_ALTAR_BLOCK)));

        button = MalumContent.registerBlock(name("%s_button"), () -> new ButtonBlock(BlockSetType.STONE, 20, rockProperties.get().noCollission().addTag(BUTTONS)));

        pressurePlate = MalumContent.registerBlock(name("%s_pressure_plate"), () -> new PressurePlateBlock(BlockSetType.STONE, rockProperties.get().noCollission().addTag(PRESSURE_PLATES)));

        itemPedestal = MalumContent.registerBlock(name("%s_item_pedestal"), () -> new ItemPedestalBlock<>(rockProperties.get().noOcclusion()));

        itemStand = MalumContent.registerBlock(name("%s_item_stand"), () -> new ItemStandBlock<>(rockProperties.get().noOcclusion()));

    }

    protected BlockItemTagKey createTag(String tag) {
        return BlockBundle.createTag(id, tag);
    }

    public void addToCreativeTab(CreativeTabCategoryBuilder builder) {
        builder
                .addItems(
                        rock.block,
                        polishedRock.block,
                        bricks.block,
                        tiles.block,
                        mosaic.block,

                        column,
                        altar
                ).nextLine()
                .addItems(
                        rock.stairs,
                        polishedRock.stairs,
                        bricks.stairs,
                        tiles.stairs,
                        mosaic.stairs,

                        itemPedestal,
                        itemStand
                ).nextLine()
                .addItems(
                        rock.slab,
                        polishedRock.slab,
                        bricks.slab,
                        tiles.slab,
                        mosaic.slab,

                        pressurePlate,
                        button
                ).nextLine()
                .addItems(
                        rock.wall,
                        polishedRock.wall,
                        bricks.wall,
                        tiles.wall,
                        mosaic.wall
                );
    }

    public void addBlockStates(MalumBlockStateDatagen datagen, BlockStateSystemData<MalumBlockStateDatagen> data) {
        datagen.setTexturePath("building/stone/" + id);
        datagen.generateBlockBundle(data, rock);
        datagen.generateBlockBundle(data, polishedRock);
        datagen.generateBlockBundle(data, bricks);
        datagen.generateBlockBundle(data, tiles);

        MalumBlockStateSmithTypes.COLUMN.act(data, column);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, datagen::simpleBlock, datagen.models()::cubeBottomTop, altar);

        BlockStateSmithTypes.BUTTON_BLOCK.act(data, button);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, pressurePlate);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, datagen::simpleBlock, datagen::rockItemPedestalModel, itemPedestal);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, datagen::directionalBlock, datagen::itemStandModel, itemStand);
    }
}