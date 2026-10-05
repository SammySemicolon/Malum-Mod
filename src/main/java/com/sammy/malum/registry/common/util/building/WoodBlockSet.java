package com.sammy.malum.registry.common.util.building;

import com.sammy.malum.common.block.building.ColumnBlock;
import com.sammy.malum.common.block.flora.wood.*;
import com.sammy.malum.common.block.storage.pedestal.DecoratedItemPedestalBlock;
import com.sammy.malum.common.block.storage.pedestal.WoodItemPedestalBlock;
import com.sammy.malum.common.block.storage.stand.ItemStandBlock;
import com.sammy.malum.datagen.block.MalumBlockStateDatagen;
import com.sammy.malum.registry.common.block.MalumWoodTypes;
import com.sammy.malum.registry.common.util.MalumRegistrySet;
import com.sammy.malum.registry.common.util.data.BlockBundle;
import com.sammy.malum.registry.common.util.data.BlockBundleWithWall;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import team.lodestar.lodestone.modules.core.util.BlockItemTagKey;
import team.lodestar.lodestone.modules.datagen.BlockStateSmithTypes;
import team.lodestar.lodestone.modules.datagen.smith.blockstate.BlockStateSystemData;
import team.lodestar.lodestone.modules.toolkit.block.BlockBlockItemHolder;
import team.lodestar.lodestone.modules.toolkit.block.LodestoneBlockProperties;
import team.lodestar.lodestone.modules.toolkit.creative_tab.CreativeTabCategoryBuilder;

import java.util.function.Supplier;

import static com.sammy.malum.registry.common.MalumContent.registerBlock;
import static com.sammy.malum.registry.common.MalumContent.registerBlockNoItem;
import static team.lodestar.lodestone.modules.toolkit.block.LodestoneBlockProperties.copy;

public class WoodBlockSet extends MalumRegistrySet {

    public final BlockItemTagKey allLogsTag, logsTag, strippedLogsTag;

    public final BlockItemTagKey boardsTag, boardStairsTag, boardSlabsTag, boardWallsTag;
    public final BlockItemTagKey planksTag, plankStairsTag, plankSlabsTag;

    public final BlockBlockItemHolder<Block, BlockItem> strippedLog, strippedWood, strippedSappyLog;

    public final BlockBlockItemHolder<Block, BlockItem> log, wood, sappyLog;

    public final BlockBundleWithWall boards, verticalBoards, blocks;
    public final BlockBundle planks, verticalPlanks, tiles;

    public final BlockBlockItemHolder<Block, BlockItem> steps, beam;

    public final BlockBlockItemHolder<Block, BlockItem> door, heavyDoor;
    public final BlockBlockItemHolder<Block, BlockItem> trapdoor, heavyTrapdoor;

    public final BlockBlockItemHolder<Block, BlockItem> button;
    public final BlockBlockItemHolder<Block, BlockItem> pressurePlate;

    public final BlockBlockItemHolder<Block, BlockItem> fence;
    public final BlockBlockItemHolder<Block, BlockItem> fenceGate;

    public final BlockBlockItemHolder<Block, BlockItem> itemPedestal;
    public final BlockBlockItemHolder<Block, BlockItem> itemStand;
    public final BlockBlockItemHolder<Block, BlockItem> decoratedItemPedestal;
    public final BlockBlockItemHolder<Block, BlockItem> decoratedItemStand;

    public final BlockBlockItemHolder<Block, BlockItem> sign;
    public final DeferredBlock<Block> wallSign;

    public WoodBlockSet(String id, String decoratedPrefix, Supplier<BlockSetType> blockSetType,
                        Supplier<LodestoneBlockProperties> properties) {
        super(id);

        allLogsTag = createTag("all_logs");
        logsTag = createTag("logs");
        strippedLogsTag = createTag("stripped_logs");

        boardsTag = createTag("boards");
        boardStairsTag = createTag("board_stairs");
        boardSlabsTag = createTag("board_slabs");
        boardWallsTag = createTag("board_walls");

        planksTag = createTag("planks");
        plankStairsTag = createTag("plank_stairs");
        plankSlabsTag = createTag("plank_slabs");

        var doorProperties = properties.get().setCutout();
        var itemHolderProperties = properties.get().noOcclusion();
        var decoratedItemHolderProperties = properties.get().setCutout().noOcclusion();

        strippedLog = registerBlock(name("stripped_%s_log"), () -> new RotatedPillarBlock(properties.get().addTags(strippedLogsTag)));
        strippedWood = registerBlock(name("stripped_%s"), () -> new RotatedPillarBlock(properties.get().addTags(strippedLogsTag)));
        strippedSappyLog = registerBlock(name("stripped_sappy_%s_log"), () -> new RotatedPillarBlock(properties.get().addTags(strippedLogsTag)));

        log = registerBlock(name("%s_log"), () -> new MalumLogBlock(properties.get().addTags(logsTag)));
        wood = registerBlock(id, () -> new RotatedPillarBlock(properties.get().addTags(logsTag)));
        sappyLog = registerBlock(name("sappy_%s_log"), () -> new RotatedPillarBlock(properties.get().addTags(logsTag)));

        boards = new BlockBundleWithWall(name("%s_boards"), properties, boardsTag, boardSlabsTag, boardStairsTag, boardWallsTag);
        verticalBoards = new BlockBundleWithWall(name("vertical_%s_boards"), properties, boardsTag, boardSlabsTag, boardStairsTag, boardWallsTag);
        blocks = new BlockBundleWithWall(name("%s_blocks"), properties, boardsTag, boardSlabsTag, boardStairsTag, boardWallsTag);

        planks = new BlockBundle(name("%s_planks"), properties, planksTag, plankSlabsTag, plankStairsTag);
        verticalPlanks = new BlockBundle(name("vertical_%s_planks"), properties, planksTag, plankSlabsTag, plankStairsTag);
        tiles = new BlockBundle(name("%s_tiles"), properties, planksTag, plankSlabsTag, plankStairsTag);
        
        steps = registerBlock(name("%s_steps"), () -> new Block(properties.get()));
        beam = registerBlock(name("%s_beam"), () -> new ColumnBlock(properties.get()));

        door = registerBlock(name("%s_door"), () -> new DoorBlock(blockSetType.get(), doorProperties));
        heavyDoor = registerBlock(name("heavy_%s_door"), () -> new DoorBlock(blockSetType.get(), doorProperties));
        trapdoor = registerBlock(name("%s_trapdoor"), () -> new TrapDoorBlock(blockSetType.get(), doorProperties));
        heavyTrapdoor = registerBlock(name("heavy_%s_trapdoor"), () -> new TrapDoorBlock(blockSetType.get(), doorProperties));

        button = registerBlock(name("%s_planks_button"), () -> new ButtonBlock(blockSetType.get(), 20, properties.get().noCollission()));
        pressurePlate = registerBlock(name("%s_planks_pressure_plate"), () -> new PressurePlateBlock(blockSetType.get(), properties.get().noCollission()));

        fence = registerBlock(name("%s_planks_fence"), () -> new FenceBlock(properties.get()));
        fenceGate = registerBlock(name("%s_planks_fence_gate"), () -> new FenceGateBlock(MalumWoodTypes.RUNEWOOD, properties.get()));

        itemStand = registerBlock(name("%s_item_stand"), () -> new ItemStandBlock<>(itemHolderProperties));
        decoratedItemPedestal = registerBlock(name(decoratedPrefix + "_%s_item_pedestal"), () -> new DecoratedItemPedestalBlock<>(decoratedItemHolderProperties));
        itemPedestal = registerBlock(name("%s_item_pedestal"), () -> new WoodItemPedestalBlock<>(itemHolderProperties));
        decoratedItemStand = registerBlock(name(decoratedPrefix + "_%s_item_stand"), () -> new ItemStandBlock<>(decoratedItemHolderProperties));

        wallSign = registerBlockNoItem(name("%s_wall_sign"), () -> new WallSignBlock(MalumWoodTypes.RUNEWOOD, itemHolderProperties.noCollission()));
        sign = registerBlock(name("%s_sign"), () -> new StandingSignBlock(MalumWoodTypes.RUNEWOOD, itemHolderProperties.noCollission()), (b, p) -> new SignItem(p, b, wallSign.get()));
    }

    protected BlockItemTagKey createTag(String tag) {
        return BlockBundle.createTag(id, tag);
    }

    public void addToCreativeTab(CreativeTabCategoryBuilder builder) {
        builder.addItems(
                        log,
                        wood,
                        sappyLog,
                        strippedLog,
                        strippedWood,
                        strippedSappyLog,

                        steps, beam
                ).nextLine()
                .addItems(
                        boards.block,
                        verticalBoards.block,
                        blocks.block,
                        planks.block,
                        verticalPlanks.block,
                        tiles.block,

                        itemPedestal, itemStand
                ).nextLine()
                .addItems(
                        boards.stairs,
                        verticalBoards.stairs,
                        blocks.stairs,
                        planks.stairs,
                        verticalPlanks.stairs,
                        tiles.stairs,

                        decoratedItemPedestal, decoratedItemStand
                ).nextLine()
                .addItems(
                        boards.slab,
                        verticalBoards.slab,
                        blocks.slab,
                        planks.slab,
                        verticalPlanks.slab,
                        tiles.slab
                ).nextLine()
                .addItems(
                        boards.wall,
                        verticalBoards.wall,
                        blocks.wall,
                        fence,
                        fenceGate,
                        sign
                ).nextLine()
                .addItems(
                        door,
                        trapdoor,
                        heavyDoor,
                        heavyTrapdoor,
                        pressurePlate,
                        button
                )
                .bake();
    }

    public void generateWoodSet(BlockStateSystemData<MalumBlockStateDatagen> data) {
        MalumBlockStateDatagen provider = data.provider();
        for (BlockBundle bundle : new BlockBundle[]{boards, verticalBoards, blocks, planks, verticalPlanks}) {
            provider.generateVariedBlockBundle(data, bundle);
        }
        for (BlockBundle bundle : new BlockBundle[]{tiles}) {
            provider.generateBlockBundle(data, bundle);
        }

        BlockStateSmithTypes.LOG_BLOCK.act(data, log, strippedLog, sappyLog, strippedSappyLog);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, wood, strippedWood);

        BlockStateSmithTypes.FENCE_BLOCK.act(data, fence);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, fenceGate);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, sign);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, button);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, pressurePlate);

//        MalumBlockStateSmithTypes.COLUMN.act(data, beam);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, provider::simpleBlock, provider.models()::cubeBottomTop, steps);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, provider::simpleBlock, provider::woodenItemPedestalModel, itemPedestal);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, provider::simpleBlock, provider::decoratedItemPedestalModel, decoratedItemPedestal);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, provider::directionalBlock, provider::itemStandModel, itemStand);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, provider::directionalBlock, provider::decoratedItemStandModel, decoratedItemStand);
    }

    public void bindSigns(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN, sign.get(), wallSign.get());
    }
}