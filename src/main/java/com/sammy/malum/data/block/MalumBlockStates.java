package com.sammy.malum.data.block;

import com.sammy.malum.MalumMod;
import com.sammy.malum.data.item.MalumItemModelSmithTypes;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import team.lodestar.lodestone.systems.datagen.BlockStateSmithTypes;
import team.lodestar.lodestone.systems.datagen.ItemModelSmithTypes;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneBlockStateProvider;
import team.lodestar.lodestone.systems.datagen.providers.LodestoneItemModelProvider;
import team.lodestar.lodestone.systems.datagen.statesmith.AbstractBlockStateSmith;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static com.sammy.malum.MalumMod.MALUM;
import static com.sammy.malum.MalumMod.malumPath;
import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumBlockStates extends LodestoneBlockStateProvider {

    public MalumBlockStates(PackOutput output, ExistingFileHelper exFileHelper, LodestoneItemModelProvider itemModelProvider) {
        super(output, MALUM, exFileHelper, itemModelProvider);
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
                RUNIC_TAINTED_ROCK_BRICKS, RUNIC_TAINTED_ROCK_TILES, RUNIC_SMALL_TAINTED_ROCK_BRICKS,
                CHISELED_TAINTED_ROCK);

        BlockStateSmithTypes.SLAB_BLOCK.act(data, TAINTED_ROCK_SLAB, POLISHED_TAINTED_ROCK_SLAB, SMOOTH_TAINTED_ROCK_SLAB,
                TAINTED_ROCK_BRICKS_SLAB, TAINTED_ROCK_TILES_SLAB, SMALL_TAINTED_ROCK_BRICKS_SLAB,
                RUNIC_TAINTED_ROCK_BRICKS_SLAB, RUNIC_TAINTED_ROCK_TILES_SLAB, RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB);

        BlockStateSmithTypes.STAIRS_BLOCK.act(data, TAINTED_ROCK_STAIRS, POLISHED_TAINTED_ROCK_STAIRS, SMOOTH_TAINTED_ROCK_STAIRS,
                TAINTED_ROCK_BRICKS_STAIRS, TAINTED_ROCK_TILES_STAIRS, SMALL_TAINTED_ROCK_BRICKS_STAIRS,
                RUNIC_TAINTED_ROCK_BRICKS_STAIRS, RUNIC_TAINTED_ROCK_TILES_STAIRS, RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS);

        BlockStateSmithTypes.WALL_BLOCK.act(data, TAINTED_ROCK_WALL,
                TAINTED_ROCK_BRICKS_WALL, TAINTED_ROCK_TILES_WALL, SMALL_TAINTED_ROCK_BRICKS_WALL,
                RUNIC_TAINTED_ROCK_BRICKS_WALL, RUNIC_TAINTED_ROCK_TILES_WALL, RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutRockBlockModel, CUT_TAINTED_ROCK);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::columnCapModel, TAINTED_ROCK_COLUMN_CAP);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::rockItemPedestalModel, TAINTED_ROCK_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::rockItemStandModel, TAINTED_ROCK_ITEM_STAND);

        BlockStateSmithTypes.LOG_BLOCK.act(data, TAINTED_ROCK_COLUMN);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, TAINTED_ROCK_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, TAINTED_ROCK_PRESSURE_PLATE);

        MalumBlockStateSmithTypes.BRAZIER_BLOCK.act(data, TAINTED_ETHER_BRAZIER);
        MalumBlockStateSmithTypes.IRIDESCENT_BRAZIER_BLOCK.act(data, TAINTED_IRIDESCENT_ETHER_BRAZIER);

        setTexturePath("arcane_rock/twisted/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                TWISTED_ROCK, POLISHED_TWISTED_ROCK, SMOOTH_TWISTED_ROCK,
                TWISTED_ROCK_BRICKS, TWISTED_ROCK_TILES, SMALL_TWISTED_ROCK_BRICKS,
                RUNIC_TWISTED_ROCK_BRICKS, RUNIC_TWISTED_ROCK_TILES, RUNIC_SMALL_TWISTED_ROCK_BRICKS,
                CHISELED_TWISTED_ROCK);

        BlockStateSmithTypes.SLAB_BLOCK.act(data, TWISTED_ROCK_SLAB, POLISHED_TWISTED_ROCK_SLAB, SMOOTH_TWISTED_ROCK_SLAB,
                TWISTED_ROCK_BRICKS_SLAB, TWISTED_ROCK_TILES_SLAB, SMALL_TWISTED_ROCK_BRICKS_SLAB,
                RUNIC_TWISTED_ROCK_BRICKS_SLAB, RUNIC_TWISTED_ROCK_TILES_SLAB, RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB);

        BlockStateSmithTypes.STAIRS_BLOCK.act(data, TWISTED_ROCK_STAIRS, POLISHED_TWISTED_ROCK_STAIRS, SMOOTH_TWISTED_ROCK_STAIRS,
                TWISTED_ROCK_BRICKS_STAIRS, TWISTED_ROCK_TILES_STAIRS, SMALL_TWISTED_ROCK_BRICKS_STAIRS,
                RUNIC_TWISTED_ROCK_BRICKS_STAIRS, RUNIC_TWISTED_ROCK_TILES_STAIRS, RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS);

        BlockStateSmithTypes.WALL_BLOCK.act(data, TWISTED_ROCK_WALL,
                TWISTED_ROCK_BRICKS_WALL, TWISTED_ROCK_TILES_WALL, SMALL_TWISTED_ROCK_BRICKS_WALL,
                RUNIC_TWISTED_ROCK_BRICKS_WALL, RUNIC_TWISTED_ROCK_TILES_WALL, RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutRockBlockModel, CUT_TWISTED_ROCK);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::columnCapModel, TWISTED_ROCK_COLUMN_CAP);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::rockItemPedestalModel, TWISTED_ROCK_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::rockItemStandModel, TWISTED_ROCK_ITEM_STAND);

        BlockStateSmithTypes.LOG_BLOCK.act(data, TWISTED_ROCK_COLUMN);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, TWISTED_ROCK_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, TWISTED_ROCK_PRESSURE_PLATE);

        MalumBlockStateSmithTypes.BRAZIER_BLOCK.act(data, TWISTED_ETHER_BRAZIER);
        MalumBlockStateSmithTypes.IRIDESCENT_BRAZIER_BLOCK.act(data, TWISTED_IRIDESCENT_ETHER_BRAZIER);

        setTexturePath("runewood/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, RUNEWOOD_PLANKS, VERTICAL_RUNEWOOD_PLANKS, RUNEWOOD_PANEL, RUNEWOOD_TILES);
        BlockStateSmithTypes.SLAB_BLOCK.act(data, RUNEWOOD_PLANKS_SLAB, VERTICAL_RUNEWOOD_PLANKS_SLAB, RUNEWOOD_PANEL_SLAB, RUNEWOOD_TILES_SLAB);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data, RUNEWOOD_PLANKS_STAIRS, VERTICAL_RUNEWOOD_PLANKS_STAIRS, RUNEWOOD_PANEL_STAIRS, RUNEWOOD_TILES_STAIRS);

        BlockStateSmithTypes.LOG_BLOCK.act(data, RUNEWOOD_BEAM, RUNEWOOD_LOG, STRIPPED_RUNEWOOD_LOG, EXPOSED_RUNEWOOD_LOG, REVEALED_RUNEWOOD_LOG);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, RUNEWOOD, STRIPPED_RUNEWOOD);
        BlockStateSmithTypes.LEAVES_BLOCK.act(data, RUNEWOOD_LEAVES);
        MalumBlockStateSmithTypes.HANGING_RUNEWOOD_LEAVES.act(data, HANGING_RUNEWOOD_LEAVES);

        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, RUNEWOOD_SAPLING);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, RUNEWOOD_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, RUNEWOOD_PRESSURE_PLATE);
        BlockStateSmithTypes.DOOR_BLOCK.act(data, RUNEWOOD_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, RUNEWOOD_TRAPDOOR, SOLID_RUNEWOOD_TRAPDOOR);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, RUNEWOOD_SIGN, RUNEWOOD_WALL_SIGN);
        BlockStateSmithTypes.FENCE_BLOCK.act(data, RUNEWOOD_FENCE);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, RUNEWOOD_FENCE_GATE);

        MalumBlockStateSmithTypes.TOTEM_POLE.act(data, RUNEWOOD_TOTEM_POLE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::totemBaseModel, RUNEWOOD_TOTEM_BASE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutWoodBlockModel, CUT_RUNEWOOD_PLANKS);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::woodenItemPedestalModel, RUNEWOOD_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::woodenItemStandModel, RUNEWOOD_ITEM_STAND);

        setTexturePath("soulwood/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, SOULWOOD_PLANKS, VERTICAL_SOULWOOD_PLANKS, SOULWOOD_PANEL, SOULWOOD_TILES);
        BlockStateSmithTypes.SLAB_BLOCK.act(data, SOULWOOD_PLANKS_SLAB, VERTICAL_SOULWOOD_PLANKS_SLAB, SOULWOOD_PANEL_SLAB, SOULWOOD_TILES_SLAB);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data, SOULWOOD_PLANKS_STAIRS, VERTICAL_SOULWOOD_PLANKS_STAIRS, SOULWOOD_PANEL_STAIRS, SOULWOOD_TILES_STAIRS);

        BlockStateSmithTypes.LOG_BLOCK.act(data, SOULWOOD_BEAM, SOULWOOD_LOG, STRIPPED_SOULWOOD_LOG, EXPOSED_SOULWOOD_LOG, REVEALED_SOULWOOD_LOG);
        BlockStateSmithTypes.WOOD_BLOCK.act(data, SOULWOOD, STRIPPED_SOULWOOD);
        BlockStateSmithTypes.LEAVES_BLOCK.act(data, SOULWOOD_LEAVES, BUDDING_SOULWOOD_LEAVES);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::hangingLeavesModel, HANGING_SOULWOOD_LEAVES);

        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, SOULWOOD_GROWTH);
        BlockStateSmithTypes.BUTTON_BLOCK.act(data, SOULWOOD_BUTTON);
        BlockStateSmithTypes.PRESSURE_PLATE_BLOCK.act(data, SOULWOOD_PRESSURE_PLATE);
        BlockStateSmithTypes.DOOR_BLOCK.act(data, SOULWOOD_DOOR);
        BlockStateSmithTypes.TRAPDOOR_BLOCK.act(data, SOULWOOD_TRAPDOOR, SOLID_SOULWOOD_TRAPDOOR);
        BlockStateSmithTypes.WOODEN_SIGN_BLOCK.act(data, SOULWOOD_SIGN, SOULWOOD_WALL_SIGN);
        BlockStateSmithTypes.FENCE_BLOCK.act(data, SOULWOOD_FENCE);
        BlockStateSmithTypes.FENCE_GATE_BLOCK.act(data, SOULWOOD_FENCE_GATE);

        MalumBlockStateSmithTypes.TOTEM_POLE.act(data, SOULWOOD_TOTEM_POLE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::totemBaseModel, SOULWOOD_TOTEM_BASE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::cutWoodBlockModel, CUT_SOULWOOD_PLANKS);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::woodenItemPedestalModel, SOULWOOD_ITEM_PEDESTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, this::woodenItemStandModel, SOULWOOD_ITEM_STAND);

        setTexturePath("ores/");
        BlockStateSmithTypes.FULL_BLOCK.act(data, CTHONIC_GOLD_ORE, NATURAL_QUARTZ_ORE, DEEPSLATE_QUARTZ_ORE, SOULSTONE_ORE, DEEPSLATE_SOULSTONE_ORE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, this::layeredBlockModel, BLAZING_QUARTZ_ORE, BRILLIANT_STONE, BRILLIANT_DEEPSLATE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.GENERATED_ITEM, this::directionalBlock, fromFunction(models()::cross), NATURAL_QUARTZ_CLUSTER, CTHONIC_GOLD_CLUSTER);

        setTexturePath("storage_blocks/");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                BLOCK_OF_SOUL_STAINED_STEEL, BLOCK_OF_ROTTING_ESSENCE, BLOCK_OF_GRIM_TALC, BLOCK_OF_ALCHEMICAL_CALX,
                BLOCK_OF_RAW_SOULSTONE, BLOCK_OF_SOULSTONE, BLOCK_OF_CTHONIC_GOLD, BLOCK_OF_ARCANE_CHARCOAL,
                BLOCK_OF_HALLOWED_GOLD, BLOCK_OF_ASTRAL_WEAVE, BLOCK_OF_HEX_ASH, MASS_OF_BLIGHTED_GUNK,
                BLOCK_OF_CURSED_GRIT, RUNIC_SAP_BLOCK, CURSED_SAP_BLOCK, BLOCK_OF_BLAZING_QUARTZ,
                BLOCK_OF_BRILLIANCE, BLOCK_OF_NULL_SLATE, BLOCK_OF_VOID_SALTS, BLOCK_OF_MNEMONIC_FRAGMENT,
                BLOCK_OF_MALIGNANT_ALLOY);

        setTexturePath("blight/");
        MalumBlockStateSmithTypes.BLIGHTED_BLOCK.act(data, BLIGHTED_SOIL);
        MalumBlockStateSmithTypes.BLIGHTED_GROWTH.act(data, BLIGHTED_GROWTH);
        MalumBlockStateSmithTypes.CLINGING_BLIGHT.act(data, CLINGING_BLIGHT);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::blightedEarthModel, BLIGHTED_EARTH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::blightedSoulwoodModel, BLIGHTED_SOULWOOD);

        setTexturePath("blight/calcified/");
        MalumBlockStateSmithTypes.CALCIFIED_BLIGHT.act(data, CALCIFIED_BLIGHT);
        MalumBlockStateSmithTypes.TALL_CALCIFIED_BLIGHT.act(data, TALL_CALCIFIED_BLIGHT);

        setTexturePath("");
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.GENERATED_ITEM, this::simpleBlock, this::etherModel, ETHER);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, MalumItemModelSmithTypes.GENERATED_OVERLAY_ITEM, (b, m) -> getVariantBuilder(b).forAllStates(s -> ConfiguredModel.builder().modelFile(m).build()), this::etherTorchModel, ETHER_TORCH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_MODEL, (b, m) -> horizontalBlock(b, m, 90), this::wallEtherTorchModel, WALL_ETHER_TORCH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, MalumItemModelSmithTypes.IRIDESCENT_ETHER_TORCH_ITEM, (b, m) -> getVariantBuilder(b).forAllStates(s -> ConfiguredModel.builder().modelFile(m).build()), this::etherTorchModel, IRIDESCENT_ETHER_TORCH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_MODEL, (b, m) -> horizontalBlock(b, m, 90), this::wallEtherTorchModel, IRIDESCENT_WALL_ETHER_TORCH);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, MalumItemModelSmithTypes.GENERATED_OVERLAY_ITEM, this::simpleBlock, this::etherModel, IRIDESCENT_ETHER);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::simpleBlock, this::predefinedModel,
                SPIRIT_ALTAR, SPIRIT_JAR, RITUAL_PLINTH);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.BLOCK_MODEL_ITEM, this::horizontalBlock, this::predefinedModel,
                WEAVERS_WORKBENCH);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.UNIQUE_ITEM_MODEL, this::simpleBlock, this::predefinedModel,
                RUNEWOOD_OBELISK, RUNEWOOD_OBELISK_COMPONENT, BRILLIANT_OBELISK, BRILLIANT_OBELISK_COMPONENT, SPIRIT_CRUCIBLE, SPIRIT_CRUCIBLE_COMPONENT, REPAIR_PYLON);

        MalumBlockStateSmithTypes.REPAIR_PYLON_COMPONENT.act(data, REPAIR_PYLON_COMPONENT);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.UNIQUE_ITEM_MODEL, this::horizontalBlock, this::predefinedModel,
                SPIRIT_CATALYZER, SPIRIT_CATALYZER_COMPONENT);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_MODEL, this::simpleBlock, this::predefinedModel,
                VOID_CONDUIT);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_MODEL, this::horizontalBlock, this::predefinedModel,
                WEEPING_WELL_CORE, WEEPING_WELL_CORNER, WEEPING_WELL_SIDE);
        MalumBlockStateSmithTypes.PRIMORDIAL_SOUP.act(data, PRIMORDIAL_SOUP);

        BlockStateSmithTypes.FULL_BLOCK.act(data, THE_DEVICE, THE_VESSEL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, ItemModelSmithTypes.NO_MODEL, this::simpleBlock, this::cubeModelAirTexture,
                MOTE_OF_SACRED_ARCANA, MOTE_OF_WICKED_ARCANA, MOTE_OF_RAW_ARCANA, MOTE_OF_ELDRITCH_ARCANA,
                MOTE_OF_AERIAL_ARCANA, MOTE_OF_AQUEOUS_ARCANA, MOTE_OF_INFERNAL_ARCANA, MOTE_OF_EARTHEN_ARCANA);
    }

    public ModelFile cubeModelAirTexture(Block block) {
        String name = getBlockName(block);
        return models().cubeAll(name, MalumMod.malumPath("block/air"));
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
        ResourceLocation glowingTexture = getBlockTexture(name + "_glow");
        return models().withExistingParent(name, parent).texture("all", texture).texture("glow", glowingTexture).texture("particle", texture);
    }

    public ModelFile etherModel(Block block) {
        String name = getBlockName(block);
        return models().withExistingParent(name, new ResourceLocation("block/air")).texture("particle", malumPath("item/ether"));
    }

    public ModelFile etherTorchModel(Block block) {
        return models().getExistingFile(malumPath("block/ether_torch"));
    }

    public ModelFile wallEtherTorchModel(Block block) {
        return models().getExistingFile(malumPath("block/ether_torch_wall"));
    }

    public ModelFile totemBaseModel(Block block) {
        String name = getBlockName(block);
        String woodName = name.substring(0, 8);
        ResourceLocation side = getBlockTexture(woodName + "_log");
        ResourceLocation top = getBlockTexture(woodName + "_log_top");
        ResourceLocation planks = getBlockTexture(woodName + "_planks");
        ResourceLocation panel = getBlockTexture(woodName + "_panel");
        return models().withExistingParent(name, malumPath("block/templates/template_totem_base")).texture("side", side).texture("top", top).texture("planks", planks).texture("panel", panel);
    }

    public ModelFile hangingLeavesModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation texture = getBlockTexture(name);
        return models().withExistingParent(name, malumPath("block/templates/template_hanging_leaves")).texture("hanging_leaves", texture).texture("particle", texture);
    }

    public ModelFile blightedSoulwoodModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation side = getBlockTexture(name);
        ResourceLocation bottom = getBlockTexture("blighted_soil_0");
        ResourceLocation top = getStaticBlockTexture("soulwood/soulwood_log_top");
        return models().cubeBottomTop(name, side, bottom, top);
    }

    public ModelFile blightedEarthModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation side = getBlockTexture(name);
        ResourceLocation bottom = new ResourceLocation("block/dirt");
        ResourceLocation top = getBlockTexture("blighted_soil_0");
        return models().cubeBottomTop(name, side, bottom, top);
    }
}