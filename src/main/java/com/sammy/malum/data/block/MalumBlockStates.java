package com.sammy.malum.data.block;

import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.datagen.*;
import team.lodestar.lodestone.systems.datagen.providers.*;
import team.lodestar.lodestone.systems.datagen.statesmith.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumBlockStates extends LodestoneBlockStateProvider {

    public MalumBlockStates(DataGenerator gen, ExistingFileHelper exFileHelper, LodestoneItemModelProvider itemModelProvider) {
        super(gen, MALUM, exFileHelper, itemModelProvider);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Malum BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Supplier<Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        AbstractBlockStateSmith.StateSmithData data = new AbstractBlockStateSmith.StateSmithData(this, blocks::remove);

        setTexturePath("arcane_rock/tainted/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                TAINTED_ROCK, POLISHED_TAINTED_ROCK, SMOOTH_TAINTED_ROCK,
                TAINTED_ROCK_BRICKS, TAINTED_ROCK_TILES, SMALL_TAINTED_ROCK_BRICKS,
                CRACKED_TAINTED_ROCK_BRICKS, CRACKED_TAINTED_ROCK_TILES, CRACKED_SMALL_TAINTED_ROCK_BRICKS,
                CHISELED_TAINTED_ROCK);

        BlockStateSmithTypes.SLAB_BLOCK.act(data, TAINTED_ROCK_SLAB, POLISHED_TAINTED_ROCK_SLAB, SMOOTH_TAINTED_ROCK_SLAB,
                TAINTED_ROCK_BRICKS_SLAB, TAINTED_ROCK_TILES_SLAB, SMALL_TAINTED_ROCK_BRICKS_SLAB,
                CRACKED_TAINTED_ROCK_BRICKS_SLAB, CRACKED_TAINTED_ROCK_TILES_SLAB, CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB);

        BlockStateSmithTypes.STAIRS_BLOCK.act(data, TAINTED_ROCK_STAIRS, POLISHED_TAINTED_ROCK_STAIRS, SMOOTH_TAINTED_ROCK_STAIRS,
                TAINTED_ROCK_BRICKS_STAIRS, TAINTED_ROCK_TILES_STAIRS, SMALL_TAINTED_ROCK_BRICKS_STAIRS,
                CRACKED_TAINTED_ROCK_BRICKS_STAIRS, CRACKED_TAINTED_ROCK_TILES_STAIRS, CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS);

        BlockStateSmithTypes.WALL_BLOCK.act(data, TAINTED_ROCK_WALL,
                TAINTED_ROCK_BRICKS_WALL, TAINTED_ROCK_TILES_WALL, SMALL_TAINTED_ROCK_BRICKS_WALL,
                CRACKED_TAINTED_ROCK_BRICKS_WALL, CRACKED_TAINTED_ROCK_TILES_WALL, CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutRockBlockModel, CUT_TAINTED_ROCK);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::columnCapModel, TAINTED_ROCK_COLUMN_CAP);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::rockItemPedestalModel, TAINTED_ROCK_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::rockItemStandModel, TAINTED_ROCK_ITEM_STAND);

        BlockStateSmithTypes.LOG_BLOCK.act(data, TAINTED_ROCK_COLUMN);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, TAINTED_ROCK_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, TAINTED_ROCK_PRESSURE_PLATE);

        setTexturePath("arcane_rock/twisted/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                TWISTED_ROCK, POLISHED_TWISTED_ROCK, SMOOTH_TWISTED_ROCK,
                TWISTED_ROCK_BRICKS, TWISTED_ROCK_TILES, SMALL_TWISTED_ROCK_BRICKS,
                CRACKED_TWISTED_ROCK_BRICKS, CRACKED_TWISTED_ROCK_TILES, CRACKED_SMALL_TWISTED_ROCK_BRICKS,
                CHISELED_TWISTED_ROCK);

        BlockStateSmithTypes.SLAB_BLOCK.act(data, TWISTED_ROCK_SLAB, POLISHED_TWISTED_ROCK_SLAB, SMOOTH_TWISTED_ROCK_SLAB,
                TWISTED_ROCK_BRICKS_SLAB, TWISTED_ROCK_TILES_SLAB, SMALL_TWISTED_ROCK_BRICKS_SLAB,
                CRACKED_TWISTED_ROCK_BRICKS_SLAB, CRACKED_TWISTED_ROCK_TILES_SLAB, CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB);

        BlockStateSmithTypes.STAIRS_BLOCK.act(data, TWISTED_ROCK_STAIRS, POLISHED_TWISTED_ROCK_STAIRS, SMOOTH_TWISTED_ROCK_STAIRS,
                TWISTED_ROCK_BRICKS_STAIRS, TWISTED_ROCK_TILES_STAIRS, SMALL_TWISTED_ROCK_BRICKS_STAIRS,
                CRACKED_TWISTED_ROCK_BRICKS_STAIRS, CRACKED_TWISTED_ROCK_TILES_STAIRS, CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS);

        BlockStateSmithTypes.WALL_BLOCK.act(data, TWISTED_ROCK_WALL,
                TWISTED_ROCK_BRICKS_WALL, TWISTED_ROCK_TILES_WALL, SMALL_TWISTED_ROCK_BRICKS_WALL,
                CRACKED_TWISTED_ROCK_BRICKS_WALL, CRACKED_TWISTED_ROCK_TILES_WALL, CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutRockBlockModel, CUT_TWISTED_ROCK);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::columnCapModel, TWISTED_ROCK_COLUMN_CAP);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::rockItemPedestalModel, TWISTED_ROCK_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::rockItemStandModel, TWISTED_ROCK_ITEM_STAND);

        BlockStateSmithTypes.LOG_BLOCK.act(data, TWISTED_ROCK_COLUMN);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, TWISTED_ROCK_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, TWISTED_ROCK_PRESSURE_PLATE);

        setTexturePath("runewood/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, RUNEWOOD_PLANKS, VERTICAL_RUNEWOOD_PLANKS, RUNEWOOD_PANEL, RUNEWOOD_TILES);
        BlockStateSmithTypes.SLAB_BLOCK.act(data, RUNEWOOD_PLANKS_SLAB, VERTICAL_RUNEWOOD_PLANKS_SLAB, RUNEWOOD_PANEL_SLAB, RUNEWOOD_TILES_SLAB);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data, RUNEWOOD_PLANKS_STAIRS, VERTICAL_RUNEWOOD_PLANKS_STAIRS, RUNEWOOD_PANEL_STAIRS, RUNEWOOD_TILES_STAIRS);

        BlockStateSmithTypes.LOG_BLOCK.act(data, RUNEWOOD_BEAM, RUNEWOOD_LOG, STRIPPED_RUNEWOOD_LOG, EXPOSED_RUNEWOOD_LOG, REVEALED_RUNEWOOD_LOG);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, RUNEWOOD, STRIPPED_RUNEWOOD);
        BlockStateSmithTypes.LEAVES_BLOCK.act(data, RUNEWOOD_LEAVES);

        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, RUNEWOOD_SAPLING);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, RUNEWOOD_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, RUNEWOOD_PRESSURE_PLATE);
        BlockStateSmithTypes.DOOR_BLOCK.act(data, RUNEWOOD_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, RUNEWOOD_TRAPDOOR, SOLID_RUNEWOOD_TRAPDOOR);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN);
        BlockStateSmithTypes.FENCE_BLOCK.act(data, RUNEWOOD_FENCE);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, RUNEWOOD_FENCE_GATE);

        MalumBlockStateSmithTypes.TOTEM_POLE.act(data, RUNEWOOD_TOTEM_POLE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::totemBaseBlock, RUNEWOOD_TOTEM_BASE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutWoodBlockModel, CUT_RUNEWOOD_PLANKS);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::woodenItemPedestalModel, RUNEWOOD_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::woodenItemStandModel, RUNEWOOD_ITEM_STAND);

        setTexturePath("soulwood/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, SOULWOOD_PLANKS, VERTICAL_SOULWOOD_PLANKS, SOULWOOD_PANEL, SOULWOOD_TILES);
        BlockStateSmithTypes.SLAB_BLOCK.act(data, SOULWOOD_PLANKS_SLAB, VERTICAL_SOULWOOD_PLANKS_SLAB, SOULWOOD_PANEL_SLAB, SOULWOOD_TILES_SLAB);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data, SOULWOOD_PLANKS_STAIRS, VERTICAL_SOULWOOD_PLANKS_STAIRS, SOULWOOD_PANEL_STAIRS, SOULWOOD_TILES_STAIRS);

        BlockStateSmithTypes.LOG_BLOCK.act(data, SOULWOOD_BEAM, SOULWOOD_LOG, STRIPPED_SOULWOOD_LOG, EXPOSED_SOULWOOD_LOG, REVEALED_SOULWOOD_LOG);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, SOULWOOD, STRIPPED_SOULWOOD);
        BlockStateSmithTypes.LEAVES_BLOCK.act(data, SOULWOOD_LEAVES);

        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, SOULWOOD_GROWTH);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, SOULWOOD_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, SOULWOOD_PRESSURE_PLATE);
        BlockStateSmithTypes.DOOR_BLOCK.act(data, SOULWOOD_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, SOULWOOD_TRAPDOOR, SOLID_SOULWOOD_TRAPDOOR);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, SOULWOOD_SIGN, SOULWOOD_WALL_SIGN);
        BlockStateSmithTypes.FENCE_BLOCK.act(data, SOULWOOD_FENCE);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, SOULWOOD_FENCE_GATE);

        MalumBlockStateSmithTypes.TOTEM_POLE.act(data, SOULWOOD_TOTEM_POLE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::totemBaseBlock, SOULWOOD_TOTEM_BASE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutWoodBlockModel, CUT_SOULWOOD_PLANKS);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::woodenItemPedestalModel, SOULWOOD_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::woodenItemStandModel, SOULWOOD_ITEM_STAND);

        setTexturePath("ores/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, BLOCK_OF_CTHONIC_GOLD, NATURAL_QUARTZ_ORE, DEEPSLATE_QUARTZ_ORE, SOULSTONE_ORE, DEEPSLATE_SOULSTONE_ORE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::layeredBlockModel, BLAZING_QUARTZ_ORE, BRILLIANT_STONE, BRILLIANT_DEEPSLATE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.GENERATED_ITEM, this::directionalBlock, fromFunction(models()::cross), NATURAL_QUARTZ_CLUSTER);

        setTexturePath("storage_blocks/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                BLOCK_OF_ARCANE_CHARCOAL, BLOCK_OF_BLAZING_QUARTZ,
                BLOCK_OF_BRILLIANCE, BLOCK_OF_RAW_SOULSTONE, BLOCK_OF_SOULSTONE,
                BLOCK_OF_HALLOWED_GOLD, BLOCK_OF_SOUL_STAINED_STEEL, BLOCK_OF_ROTTING_ESSENCE,
                BLOCK_OF_GRIM_TALC, BLOCK_OF_ALCHEMICAL_CALX, BLOCK_OF_ASTRAL_WEAVE,
                BLOCK_OF_HEX_ASH, BLOCK_OF_CURSED_GRIT, BLOCK_OF_VOID_SALTS);

        setTexturePath("");
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::predefinedModel,
                SPIRIT_ALTAR, SPIRIT_JAR, SOUL_VIAL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::horizontalBlock, this::predefinedModel,
                WEAVERS_WORKBENCH);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::directionalBlock, this::predefinedModel,
                TWISTED_TABLET);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.UNIQUE_ITEM_MODEL, this::simpleBlock, this::predefinedModel,
                RUNEWOOD_OBELISK, RUNEWOOD_OBELISK_COMPONENT, BRILLIANT_OBELISK, BRILLIANT_OBELISK_COMPONENT, SPIRIT_CRUCIBLE, SPIRIT_CRUCIBLE_COMPONENT);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.UNIQUE_ITEM_MODEL, this::horizontalBlock, this::predefinedModel,
                SPIRIT_CATALYZER, SPIRIT_CATALYZER_COMPONENT);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.GENERATED_ITEM, this::simpleBlock, this::etherModel,
                ETHER, IRIDESCENT_ETHER);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_MODEL, this::simpleBlock, this::predefinedModel,
                VOID_CONDUIT);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_MODEL, this::horizontalBlock, this::predefinedModel,
                WEEPING_WELL_CORE, WEEPING_WELL_CORNER, WEEPING_WELL_SIDE);
        MalumBlockStateSmithTypes.PRIMORDIAL_SOUP.act(data, BlockRegistry.PRIMORDIAL_SOUP);

//        quartzClusterBlock(take(blocks, NATURAL_QUARTZ_CLUSTER));
//        blightedSoilBlock(take(blocks, BLIGHTED_SOIL));
//        blightedEarthBlock(take(blocks, BLIGHTED_EARTH));
//        blightedTumorBlock(take(blocks, BLIGHTED_TUMOR));
//        blightedSoulwoodBlock(take(blocks, BLIGHTED_SOULWOOD));
//        primordialSoupBlock(take(blocks, PRIMORDIAL_SOUP));
//        voidConduitBlock(take(blocks, VOID_CONDUIT));
//        weaversWorkbenchBlock(take(blocks, WEAVERS_WORKBENCH));
//
//        weepingWellBlock(take(blocks, WEEPING_WELL_CORE), "weeping_well_core");
//        weepingWellBlock(take(blocks, WEEPING_WELL_CORNER), "weeping_well_corner");
//        weepingWellBlock(take(blocks, WEEPING_WELL_SIDE), "weeping_well_side");
//
//        List<RegistryObject<Block>> customStatesAndModels = new ArrayList<>(List.of(BLAZING_SCONCE, WALL_BLAZING_SCONCE, TWISTED_TABLET, SPIRIT_CATALYZER, SPIRIT_CATALYZER_COMPONENT));
//
//        List<RegistryObject<Block>> customModelsSimpleStates = new ArrayList<>(List.of(
//                SPIRIT_ALTAR, SOUL_VIAL, SPIRIT_JAR, BRILLIANT_OBELISK, BRILLIANT_OBELISK_COMPONENT, RUNEWOOD_OBELISK,
//                RUNEWOOD_OBELISK_COMPONENT, SPIRIT_CRUCIBLE, SPIRIT_CRUCIBLE_COMPONENT));
//
//        List<RegistryObject<Block>> layeredModels = new ArrayList<>(List.of(BRILLIANT_STONE, BRILLIANT_DEEPSLATE, BLAZING_QUARTZ_ORE));
//
//        takeAll(blocks, customStatesAndModels::contains);
//        takeAll(blocks, customModelsSimpleStates::contains).forEach(this::customBlock);
//        takeAll(blocks, layeredModels::contains).forEach(this::layeredBlock);
//
//        DataHelper.takeAll(blocks, b -> b.get().getRegistryName().getPath().startsWith("cut_") && b.get().getRegistryName().getPath().endsWith("_planks")).forEach(this::cutPlanksBlock);
//        DataHelper.takeAll(blocks, b -> b.get().getRegistryName().getPath().startsWith("cut_")).forEach(this::cutBlock);
//        DataHelper.takeAll(blocks, b -> b.get().getDescriptionId().endsWith("_cap")).forEach(this::pillarCapBlock);
//
//        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherBrazierBlock).forEach(this::brazierBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherWallSconceBlock).forEach(this::wallEtherSconceBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherSconceBlock).forEach(this::etherSconceBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherWallTorchBlock).forEach(this::wallEtherTorchBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherTorchBlock).forEach(this::etherTorchBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof EtherBlock).forEach(this::etherBlock);
//
//        DataHelper.takeAll(blocks, b -> b.get() instanceof TotemBaseBlock).forEach(this::totemBaseBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof TotemPoleBlock).forEach(this::totemPoleBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof ItemPedestalBlock).forEach(this::itemPedestalBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof ItemStandBlock).forEach(this::itemStandBlock);
//        DataHelper.takeAll(blocks, b -> b.get() instanceof MalumLeavesBlock).forEach(this::malumLeavesBlock);
//
//        Collection<RegistryObject<Block>> slabs = DataHelper.takeAll(blocks, b -> b.get() instanceof SlabBlock);
//
//        BlockStateSmithTypes.PREDEFINED_MODEL.act(this, this::simpleBlock,
//                DataHelper.takeAll(blocks, SPIRIT_ALTAR, SPIRIT_JAR, SOUL_VIAL, BRILLIANT_OBELISK, BRILLIANT_OBELISK_COMPONENT, RUNEWOOD_OBELISK, RUNEWOOD_OBELISK_COMPONENT, SPIRIT_CRUCIBLE, SPIRIT_CRUCIBLE_COMPONENT));
//
//        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(this, DataHelper.takeAll(blocks, RUNEWOOD_PRESSURE_PLATE, SOULWOOD_PRESSURE_PLATE, TAINTED_ROCK_PRESSURE_PLATE, TWISTED_ROCK_PRESSURE_PLATE));
//        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(this, DataHelper.takeAll(blocks, RUNEWOOD_TRAPDOOR, SOLID_RUNEWOOD_TRAPDOOR, SOULWOOD_TRAPDOOR, SOLID_SOULWOOD_TRAPDOOR));
//        BlockStateSmithTypes.BUTTON_BLOCK.act(this, DataHelper.takeAll(blocks, RUNEWOOD_BUTTON, SOULWOOD_BUTTON, TAINTED_ROCK_BUTTON, TWISTED_ROCK_BUTTON));
//        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(this, DataHelper.takeAll(blocks, RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN, SOULWOOD_SIGN, SOULWOOD_WALL_SIGN));
//        BlockStateSmithTypes.WOOD_BLOCK.act(this, DataHelper.takeAll(blocks, RUNEWOOD, SOULWOOD, STRIPPED_RUNEWOOD, STRIPPED_SOULWOOD));
//        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(this, DataHelper.takeAll(blocks, RUNEWOOD_FENCE_GATE, SOULWOOD_FENCE_GATE));
//        BlockStateSmithTypes.FENCE_BLOCK.act(this, DataHelper.takeAll(blocks, RUNEWOOD_FENCE, SOULWOOD_FENCE));
//        BlockStateSmithTypes.DOOR_BLOCK.act(this, DataHelper.takeAll(blocks, RUNEWOOD_DOOR, SOULWOOD_DOOR));
//        BlockStateSmithTypes.WALL_TORCH_BLOCK.act(this, DataHelper.takeAll(blocks, WALL_BLAZING_TORCH));
//        BlockStateSmithTypes.TORCH_BLOCK.act(this, DataHelper.takeAll(blocks, BLAZING_TORCH));
//
//        BlockStateSmithTypes.WALL_BLOCK.act(this, DataHelper.takeAll(blocks, b -> b.get() instanceof WallBlock));
//        BlockStateSmithTypes.STAIRS_BLOCK.act(this, DataHelper.takeAll(blocks, b -> b.get() instanceof StairBlock));
//        BlockStateSmithTypes.LOG_BLOCK.act(this, DataHelper.takeAll(blocks, b -> b.get() instanceof RotatedPillarBlock));
//        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(this, DataHelper.takeAll(blocks, b -> b.get() instanceof BushBlock));
//
//        BlockStateSmithTypes.FULL_BLOCK.act(this, blocks);
//        BlockStateSmithTypes.SLAB_BLOCK.act(this, slabs);

    }

    public ModelFile columnCapModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation top = getBlockTexture(name + "_top");
        ResourceLocation bottom = getBlockTexture(name.replace("_cap", "") + "_top");
        ResourceLocation side = getBlockTexture(name);
        return models().cubeBottomTop(name, side, bottom, top);
    }

    public ModelFile cutRockBlockModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation top = getBlockTexture(name.replace("cut_", "polished_"));
        ResourceLocation bottom = getBlockTexture(name.replace("cut_", "smooth_"));
        ResourceLocation side = getBlockTexture(name);
        return models().cubeBottomTop(name, side, bottom, top);
    }

    public ModelFile cutWoodBlockModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation top = getBlockTexture(name.replace("cut_", ""));
        ResourceLocation side = getBlockTexture(name);
        return models().cubeBottomTop(name, side, top, top);
    }

    public ModelFile rockItemPedestalModel(Block block) {
        return itemPedestalModel(block, "template_rock_item_pedestal", "");
    }

    public ModelFile woodenItemPedestalModel(Block block) {
        return itemPedestalModel(block, "template_wooden_item_pedestal", "_planks");
    }

    public ModelFile itemPedestalModel(Block block, String template, String affix) {
        String name = getBlockName(block);
        ResourceLocation parent = malumPath("block/templates/" + template);
        ResourceLocation pedestal = getBlockTexture(name);
        ResourceLocation particle = getBlockTexture(name.replace("_item_pedestal", "") + affix);
        return models().withExistingParent(name, parent).texture("pedestal", pedestal).texture("particle", particle);
    }

    public ModelFile rockItemStandModel(Block block) {
        return itemStandModel(block, "");
    }

    public ModelFile woodenItemStandModel(Block block) {
        return itemStandModel(block, "_planks");
    }

    public ModelFile itemStandModel(Block block, String affix) {
        String name = getBlockName(block);
        ResourceLocation parent = malumPath("block/templates/template_item_stand");
        ResourceLocation stand = getBlockTexture(name);
        ResourceLocation particle = getBlockTexture(name.replace("_item_stand", "") + affix);
        return models().withExistingParent(name, parent).texture("stand", stand).texture("particle", particle);
    }

    public ModelFile layeredBlockModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation parent = malumPath("block/templates/template_glowing_block");
        ResourceLocation texture = getBlockTexture(name);
        ResourceLocation glowingTexture = getBlockTexture(name+"_glow");
        return models().withExistingParent(name, parent).texture("all", texture).texture("glow", glowingTexture).texture("particle", texture);
    }

    public ModelFile etherModel(Block block) {
        String name = getBlockName(block);
        return models().withExistingParent(name, new ResourceLocation("block/air")).texture("particle", malumPath("item/ether"));
    }

    public ModelFile totemBaseBlock(Block block) {
        String name = getBlockName(block);
        String woodName = name.substring(0, 8);
        ResourceLocation side = getBlockTexture(woodName + "_log");
        ResourceLocation top = getBlockTexture(woodName + "_log_top");
        ResourceLocation planks = getBlockTexture(woodName + "_planks");
        ResourceLocation panel = getBlockTexture(woodName + "_panel");
        return models().withExistingParent(name, malumPath("block/templates/template_totem_base")).texture("side", side).texture("top", top).texture("planks", planks).texture("panel", panel);
    }

    public void blightedSoilBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile soil0 = models().cubeAll(name, malumPath("block/" + name + "_0"));
        ModelFile soil1 = models().cubeAll(name + "_1", malumPath("block/" + name + "_1"));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(soil0)
                .nextModel().modelFile(soil0).rotationY(90)
                .nextModel().modelFile(soil0).rotationY(180)
                .nextModel().modelFile(soil0).rotationY(270)

                .nextModel().modelFile(soil1)
                .nextModel().modelFile(soil1).rotationY(90)
                .nextModel().modelFile(soil1).rotationY(180)
                .nextModel().modelFile(soil1).rotationY(270)
                .addModel();
    }

    public void blightedTumorBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        Function<Integer, ModelFile> tumorFunction = (i) -> models().withExistingParent(name + "_" + i, new ResourceLocation("block/cross")).texture("cross", malumPath("block/" + name + "_" + i));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(tumorFunction.apply(0))
                .nextModel().modelFile(tumorFunction.apply(1))
                .nextModel().modelFile(tumorFunction.apply(2))
                .nextModel().modelFile(tumorFunction.apply(3))
                .addModel();
    }

    public void blightedSoulwoodBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop("blighted_soulwood", malumPath("block/blighted_soulwood"), malumPath("block/blighted_soil_0"), malumPath("block/soulwood_log_top")));
    }

    public void blightedEarthBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop("blighted_earth", malumPath("block/blighted_earth"), new ResourceLocation("block/dirt"), malumPath("block/blighted_soil_0")));
    }

    public void etherBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile empty = models().withExistingParent(name, new ResourceLocation("block/air")).texture("particle", malumPath("item/ether"));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(empty).build());
    }

    public void etherSconceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile sconce = models().withExistingParent(name, malumPath("block/templates/template_ether_sconce")).texture("sconce", malumPath("block/ether_sconce")).texture("overlay", malumPath("block/ether_sconce_overlay")).texture("fire", malumPath("block/ether_sconce_fire")).texture("particle", malumPath("block/ether_sconce"));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(sconce).build());
    }

    public void wallEtherSconceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile sconce = models().withExistingParent(name, malumPath("block/templates/template_ether_sconce_wall")).texture("sconce", malumPath("block/ether_sconce")).texture("overlay", malumPath("block/ether_sconce_overlay")).texture("fire", malumPath("block/ether_sconce_fire")).texture("particle", malumPath("block/ether_sconce"));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(sconce).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(sconce).rotationY(270).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(sconce).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(sconce).rotationY(90).addModel();
    }

    public void etherTorchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().withExistingParent(name, malumPath("block/templates/template_ether_torch")).texture("torch", malumPath("block/ether_torch")).texture("colored", malumPath("block/ether_torch_overlay")).texture("particle", malumPath("block/runewood_planks"));

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(torch).build());
    }

    public void wallEtherTorchBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile torch = models().withExistingParent(name, malumPath("block/templates/template_ether_torch_wall")).texture("torch", malumPath("block/ether_torch")).texture("colored", malumPath("block/ether_torch_overlay")).texture("particle", malumPath("block/runewood_planks"));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(WallTorchBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(torch).rotationY(270).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.WEST)
                .modelForState().modelFile(torch).rotationY(180).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(torch).rotationY(90).addModel()
                .partialState().with(WallTorchBlock.FACING, Direction.EAST)
                .modelForState().modelFile(torch).addModel();
    }

    public void itemStandBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String particleName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_item_stand", "");
        if (!particleName.endsWith("_rock")) {
            particleName += "_planks";
        }
        ModelFile stand = models().withExistingParent(name, malumPath("block/templates/template_item_stand")).texture("stand", malumPath("block/" + name)).texture("particle", malumPath("block/" + particleName));

        getVariantBuilder(blockRegistryObject.get()).partialState()
                .partialState().with(BlockStateProperties.FACING, Direction.NORTH)
                .modelForState().modelFile(stand).rotationX(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.WEST)
                .modelForState().modelFile(stand).rotationX(90).rotationY(270).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.SOUTH)
                .modelForState().modelFile(stand).rotationX(90).rotationY(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.EAST)
                .modelForState().modelFile(stand).rotationX(90).rotationY(90).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.DOWN)
                .modelForState().modelFile(stand).rotationX(180).addModel()
                .partialState().with(BlockStateProperties.FACING, Direction.UP)
                .modelForState().modelFile(stand).addModel();
    }

    public void itemPedestalBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String particleName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_item_pedestal", "");
        String modelLocation = "block/templates/template_rock_item_pedestal";
        if (!particleName.endsWith("_rock")) {
            particleName += "_planks";
            modelLocation = "block/templates/template_wood_item_pedestal";
        }
        ModelFile pedestal = models().withExistingParent(name, malumPath(modelLocation)).texture("pedestal", malumPath("block/" + name)).texture("particle", malumPath("block/" + particleName));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(pedestal).build());
    }

    public void cutBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = "polished_" + name.substring(4);
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop(name, malumPath("block/" + name), malumPath("block/" + baseName), malumPath("block/" + baseName)));
    }

    public void cutPlanksBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - "_planks".length()).substring(4) + "_panel";
        simpleBlock(blockRegistryObject.get(), models().cubeBottomTop(name, malumPath("block/" + name), malumPath("block/" + baseName), malumPath("block/" + baseName)));
    }

    public void totemBaseBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String woodName = name.substring(0, 8);
        ResourceLocation side = malumPath("block/" + woodName + "_log");
        ResourceLocation top = malumPath("block/" + woodName + "_log_top");
        ResourceLocation planks = malumPath("block/" + woodName + "_planks");
        ResourceLocation panel = malumPath("block/" + woodName + "_panel");
        ModelFile base = models().withExistingParent(name, malumPath("block/templates/template_totem_base")).texture("side", side).texture("top", top).texture("planks", planks).texture("panel", panel);

        simpleBlock(blockRegistryObject.get(), base);
    }

    public void totemPoleBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String woodName = name.substring(0, 8);
        ResourceLocation side = malumPath("block/" + woodName + "_log");
        ResourceLocation top = malumPath("block/" + woodName + "_log_top");

        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> {
            String type = s.getValue(SpiritTypeRegistry.SPIRIT_TYPE_PROPERTY);
            MalumSpiritType spiritType = SpiritTypeRegistry.SPIRITS.get(type);
            ModelFile pole = models().withExistingParent(name + "_" + spiritType.identifier, malumPath("block/templates/template_totem_pole")).texture("side", side).texture("top", top).texture("front", malumPath("spirit/" + spiritType.identifier + "_" + woodName + "_cutout"));
            return ConfiguredModel.builder().modelFile(pole).rotationY(((int) s.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360).build();
        });
    }

    public void malumLeavesBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile leaves = models().withExistingParent(name, new ResourceLocation("block/leaves")).texture("all", malumPath("block/" + name));
        simpleBlock(blockRegistryObject.get(), leaves);
    }

    public void brazierBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String textureName = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath().replaceFirst("_iridescent", "");
        String particleName = textureName.replaceFirst("_ether_brazier", "") + "_rock";
        ModelFile brazier = models().withExistingParent(name, malumPath("block/templates/template_ether_brazier")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));
        ModelFile brazier_hanging = models().withExistingParent(name + "_hanging", malumPath("block/templates/template_ether_brazier_hanging")).texture("brazier", malumPath("block/" + textureName)).texture("particle", malumPath("block/" + particleName));

        getVariantBuilder(blockRegistryObject.get())
                .partialState().with(EtherBrazierBlock.HANGING, false).modelForState().modelFile(brazier).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, false).modelForState().modelFile(brazier_hanging).addModel()
                .partialState().with(EtherBrazierBlock.HANGING, true).with(EtherBrazierBlock.ROTATED, true).modelForState().modelFile(brazier_hanging).rotationY(90).addModel();
    }
}