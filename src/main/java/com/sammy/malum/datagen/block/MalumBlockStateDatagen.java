
package com.sammy.malum.datagen.block;
import com.sammy.malum.datagen.set.MalumCrystalSetDatagen;
import com.sammy.malum.datagen.set.MalumMetallicsDatagen;
import com.sammy.malum.datagen.item.*;
import com.sammy.malum.datagen.set.MalumPoppetrySetDatagen;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.MalumContent.*;
import com.sammy.malum.registry.common.util.building.CommonStoneBuildingSet;
import com.sammy.malum.registry.common.util.building.RockBlockSet;
import com.sammy.malum.registry.common.util.building.WoolRegistrySet;
import com.sammy.malum.registry.common.util.data.*;
import net.minecraft.data.*;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.*;
import team.lodestar.lodestone.modules.datagen.BlockStateSmithTypes;
import team.lodestar.lodestone.modules.datagen.providers.block.LodestoneBlockStateSystem;
import team.lodestar.lodestone.modules.datagen.providers.item.LodestoneItemModelSystem;
import team.lodestar.lodestone.modules.datagen.smith.blockstate.BlockStateSystemData;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.MalumContent.Artifice.*;
import static com.sammy.malum.registry.common.MalumContent.CompactBlocks.*;
import static com.sammy.malum.registry.common.MalumContent.Materials.*;
import static com.sammy.malum.registry.common.MalumContent.Sorcery.*;
import static team.lodestar.lodestone.modules.datagen.ItemModelSmithTypes.*;

public class MalumBlockStateDatagen extends LodestoneBlockStateSystem {

    public MalumBlockStateDatagen(PackOutput output, ExistingFileHelper exFileHelper, LodestoneItemModelSystem itemModelProvider) {
        super(output, MALUM, exFileHelper, itemModelProvider);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Malum BlockStates";
    }

    @Override
    protected void registerStatesAndModels() {
        Set<Supplier<? extends Block>> blocks = new HashSet<>(MalumContent.BLOCKS.getEntries());

        BlockStateSystemData<MalumBlockStateDatagen> data = new BlockStateSystemData<>(this, blocks::remove);


        setTexturePath("banners");
        MalumBlockStateSmithTypes.BANNER.act(data, BuildingBlocks.SOULWOVEN_BANNER);
        setTexturePath("building/spirited_glass");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                BuildingBlocks.SACRED_SPIRITED_GLASS, BuildingBlocks.WICKED_SPIRITED_GLASS, BuildingBlocks.ARCANE_SPIRITED_GLASS, BuildingBlocks.ELDRITCH_SPIRITED_GLASS,
                BuildingBlocks.AERIAL_SPIRITED_GLASS, BuildingBlocks.AQUEOUS_SPIRITED_GLASS, BuildingBlocks.EARTHEN_SPIRITED_GLASS, BuildingBlocks.INFERNAL_SPIRITED_GLASS,
                BuildingBlocks.NULL_SPIRITED_GLASS);

        setTexturePath("building/terracotta");
        BlockStateSmithTypes.GLAZED_TERRACOTTA_BLOCK.act(data,
                BuildingBlocks.SACRED_VARNISHED_TERRACOTTA, BuildingBlocks.WICKED_VARNISHED_TERRACOTTA, BuildingBlocks.ARCANE_VARNISHED_TERRACOTTA, BuildingBlocks.ELDRITCH_VARNISHED_TERRACOTTA,
                BuildingBlocks.AERIAL_VARNISHED_TERRACOTTA, BuildingBlocks.AQUEOUS_VARNISHED_TERRACOTTA, BuildingBlocks.EARTHEN_VARNISHED_TERRACOTTA, BuildingBlocks.INFERNAL_VARNISHED_TERRACOTTA,
                BuildingBlocks.NULL_VARNISHED_TERRACOTTA);

        setTexturePath("building/stone");
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::simpleBlock, this::stoneBookshelfModel, BuildingBlocks.STONE_BOOKSHELF);

        for (CommonStoneBuildingSet malumSet : CommonStoneBuildingSet.getMalumSets()) {
            malumSet.addBlockStates(this, data);
        }

        for (RockBlockSet malumSet : RockBlockSet.getMalumSets()) {
            malumSet.addBlockStates(this, data);
        }

        for (WoolRegistrySet malumSet : WoolRegistrySet.getMalumSets()) {
            malumSet.addBlockStates(this, data);
        }

        setTexturePath("building/runewood");
        BuildingBlocks.RUNEWOOD_SET.generateWoodSet(data);
        BlockStateSmithTypes.POTTED_PLANT.act(data, BuildingBlocks.POTTED_RUNEWOOD_SAPLING, BuildingBlocks.POTTED_AZURE_RUNEWOOD_SAPLING);
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, BuildingBlocks.RUNEWOOD_SAPLING, BuildingBlocks.AZURE_RUNEWOOD_SAPLING);
        MalumBlockStateSmithTypes.TOTEM_POLE.act(data, Totemancy.RUNEWOOD_TOTEM_POLE);

        setTexturePath("building/runewood/leaves");
        MalumBlockStateSmithTypes.STAGED_LEAVES.act(data, BuildingBlocks.RUNEWOOD_LEAVES, BuildingBlocks.AZURE_RUNEWOOD_LEAVES);
        MalumBlockStateSmithTypes.STAGED_HANGING_LEAVES.act(data, BuildingBlocks.HANGING_RUNEWOOD_LEAVES, BuildingBlocks.HANGING_AZURE_RUNEWOOD_LEAVES);

        setTexturePath("building/soulwood");
        BuildingBlocks.SOULWOOD_SET.generateWoodSet(data);
        BlockStateSmithTypes.POTTED_PLANT.act(data, BuildingBlocks.POTTED_SOULWOOD_SAPLING);
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, BuildingBlocks.SOULWOOD_SAPLING);
        MalumBlockStateSmithTypes.TOTEM_POLE.act(data, Totemancy.SOULWOOD_TOTEM_POLE);

        setTexturePath("building/soulwood/leaves");
        MalumBlockStateSmithTypes.STAGED_LEAVES.act(data, BuildingBlocks.SOULWOOD_LEAVES);
        MalumBlockStateSmithTypes.STAGED_HANGING_LEAVES.act(data, BuildingBlocks.HANGING_SOULWOOD_LEAVES);

        setTexturePath("ores");
        BlockStateSmithTypes.FULL_BLOCK.act(data, BRILLIANT_STONE, BRILLIANT_DEEPSLATE, CTHONIC_GOLD_ORE, BLAZING_QUARTZ_ORE);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, GENERATED_ITEM, this::directionalBlock, fromFunction(models()::cross), CTHONIC_GOLD_FRAGMENT);

        setTexturePath("ores/soulstone");
        BlockStateSmithTypes.FULL_BLOCK.act(data, SOULSTONE_ORE, DEEPSLATE_SOULSTONE_ORE);

        MalumBlockStateSmithTypes.SOULSTONE_BUD.act(data, NO_DATAGEN, ARCHAIC_SOULSTONE_BUD);
        MalumBlockStateSmithTypes.SOULSTONE_BUD.act(data, SOULSTONE_BUD);

        MalumPoppetrySetDatagen.MALUM.addBlockStates(data);
        MalumCrystalSetDatagen.MALUM.addBlockStates(data);

        setTexturePath("storage_blocks");
        BlockStateSmithTypes.FULL_BLOCK.act(data,
                BLOCK_OF_SOUL_STAINED_STEEL, BLOCK_OF_HALLOWED_GOLD, BLOCK_OF_MALIGNANT_PEWTER,
                BLOCK_OF_NULL_SLATE, BLOCK_OF_VOID_SALTS, BLOCK_OF_MNEMONIC_FRAGMENT, BLOCK_OF_MALIGNANT_LEAD, BLOCK_OF_AURIC_EMBERS);

        MalumBlockStateSmithTypes.STORAGE_BLOCK.act(data, BLOCK_OF_REFINED_SOULSTONE, BLOCK_OF_RAW_SOULSTONE, BLOCK_OF_BRILLIANCE, BLOCK_OF_RAW_BRILLIANCE);
        MalumBlockStateSmithTypes.STORAGE_BLOCK.act(data, BLOCK_OF_BLAZING_QUARTZ, BLOCK_OF_CTHONIC_GOLD);

        MalumBlockStateSmithTypes.STORAGE_BLOCK.act(data, BLOCK_OF_ROTTING_ESSENCE, BLOCK_OF_GRIM_TALC, BLOCK_OF_EERIE_WEAVE, BLOCK_OF_WARP_FLUX);
        MalumBlockStateSmithTypes.STORAGE_BLOCK.act(data, BLOCK_OF_WIND_NUCLEI, BLOCK_OF_PYRE_NUCLEI);

        MalumBlockStateSmithTypes.STORAGE_BLOCK.act(data, BLOCK_OF_HEX_ASH, BLOCK_OF_LIVING_FLESH, BLOCK_OF_ALCHEMICAL_CALX, BLOCK_OF_ARCANE_CHARCOAL);

        MalumBlockStateSmithTypes.STORAGE_BLOCK.act(data, BLOCK_OF_EBONY);

        setTexturePath("storage_blocks/metallics");
        MalumMetallicsDatagen.MALUM.addBlockStates(data);

        setTexturePath("flora");
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, NO_DATAGEN, EBONY_SAPLING);
        MalumBlockStateSmithTypes.EBONY.act(data, EBONY_STALK);


        setTexturePath("blight");
        MalumBlockStateSmithTypes.COLUMN.act(data, Blight.COLUMNAR_BLIGHT);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::simpleBlock, models()::cubeBottomTop, Blight.BLIGHTED_EARTH);
        MalumBlockStateSmithTypes.COVERING_BLOCK.act(data, Blight.BLIGHT);
        MalumBlockStateSmithTypes.BLIGHTED_GROWTH.act(data, Blight.BLIGHTED_GUNK);
        MalumBlockStateSmithTypes.CREEPING_BLIGHT.act(data, Blight.CLINGING_BLIGHT);
        BlockStateSmithTypes.POTTED_PLANT.act(data, BuildingBlocks.POTTED_BLIGHTPEARL, BuildingBlocks.POTTED_BLIGHTROOT);
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, Blight.BLIGHTPEARL, Blight.BLIGHTROOT);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::simpleBlock, this::blightedSoulwoodModel, BuildingBlocks.BLIGHTED_SOULWOOD);

        setTexturePath("blight/scarstone");
        MalumBlockStateSmithTypes.LARGE_STRANGE_CRYSTAL.act(data, Blight.LARGE_STRANGE_CRYSTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, GENERATED_ITEM, this::simpleBlock, models()::crossModel, Blight.STRANGE_CRYSTAL);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::simpleBlock, models()::cubeBottomTop, Blight.SCARSTONE);
        BlockStateSmithTypes.CROSS_MODEL_BLOCK.act(data, Blight.STRANGEROOT);
        BlockStateSmithTypes.POTTED_PLANT.act(data, BuildingBlocks.POTTED_STRANGEROOT);

        setTexturePath("artifice");
        MalumBlockStateSmithTypes.SPIRIT_DIODE.act(data, WAVECHARGER, WAVEBANKER, WAVEMAKER, WAVEBREAKER);

        setTexturePath("artifice/soul_link");
        MalumBlockStateSmithTypes.SOUL_LINK.act(data, SOUL_LINK);

        setTexturePath("artifice/wind_tunnel");
        MalumBlockStateSmithTypes.ELEMENTAL_ARTIFICE_BLOCK.act(data, WIND_TUNNEL);
        setTexturePath("artifice/gust_igniter");
        MalumBlockStateSmithTypes.ELEMENTAL_ARTIFICE_BLOCK.act(data, GUST_IGNITER);

        setTexturePath("artifice/crystallarium");
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::horizontalBlock, models()::predefinedModel, CONJUNCTURE_CRYSTALLARIUM);

        setTexturePath("ether");
        itemModelProvider.setTexturePath("ether");
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, GENERATED_ITEM,
                this::simpleBlock, this::etherModel, BuildingBlocks.ETHER);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, MalumItemModelSmithTypes.GENERATED_OVERLAY_ITEM,
                this::simpleBlock, this::etherModel, BuildingBlocks.IRIDESCENT_ETHER);

        MalumBlockStateSmithTypes.ETHER_CANDLE_BLOCK.act(data,
                BuildingBlocks.ETHER_CANDLE, BuildingBlocks.IRIDESCENT_ETHER_CANDLE);
        MalumBlockStateSmithTypes.ETHER_TORCH_BLOCK.act(data,
                BuildingBlocks.ETHER_TORCH, BuildingBlocks.IRIDESCENT_ETHER_TORCH);
        MalumBlockStateSmithTypes.ETHER_WALL_TORCH_BLOCK.act(data,
                BuildingBlocks.WALL_ETHER_TORCH, BuildingBlocks.IRIDESCENT_WALL_ETHER_TORCH);
        MalumBlockStateSmithTypes.ETHER_BRAZIER_BLOCK.act(data,
                BuildingBlocks.ETHER_BRAZIER, BuildingBlocks.IRIDESCENT_ETHER_BRAZIER);
        MalumBlockStateSmithTypes.ETHER_CRESSET_BLOCK.act(data,
                BuildingBlocks.ETHER_CRESSET, BuildingBlocks.IRIDESCENT_ETHER_CRESSET);
        itemModelProvider.setTexturePath("");

        setTexturePath("enscription/flesh");
        MalumBlockStateSmithTypes.COLUMN.act(data, Enscription.COLUMNAR_FLESH);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, this::directionalBlock, models()::cubeBottomTop, Enscription.FLESHBULB);
        MalumBlockStateSmithTypes.WRITHING_FLESH.act(data, Enscription.WRITHING_FLESH);

        setTexturePath("enscription/dungeon");
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::horizontalBlock, models()::predefinedModel, Enscription.MYRIAD_GATEWAY, Enscription.MEDITATING_EFFIGY);

        setTexturePath("");
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::simpleBlock, models()::cubeBottomTop, Totemancy.RITE_ANCHOR, Totemancy.RITE_UNWEAVER);
        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::directionalBlock, models()::cubeBottomTop, Totemancy.RITE_SPREADER);
        MalumBlockStateSmithTypes.RITE_CHANNEL.act(data, Totemancy.RITE_CHANNEL);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::simpleBlock, models()::predefinedModel, SPIRIT_ALTAR, WAND_TINKERER, SOUL_BRAZIER);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BUILTIN_ENTITY_ITEM, this::simpleBlock, models()::predefinedModel, SPIRIT_JAR);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::horizontalBlock, models()::predefinedModel, WEAVERS_WORKBENCH, RUNIC_WORKBENCH);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, NO_DATAGEN, this::simpleBlock, models()::predefinedModel,
                RUNEWOOD_OBELISK, RUNEWOOD_OBELISK_COMPONENT,
                BRILLIANT_OBELISK, BRILLIANT_OBELISK_COMPONENT,
                ARCANA_PYLON, ARCANA_PYLON_COMPONENT,
                Focusing.SPIRIT_CRUCIBLE, Focusing.SPIRIT_CRUCIBLE_COMPONENT, Focusing.REPAIR_PYLON);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::simpleBlock, this::totemBaseModel,
                Totemancy.RUNEWOOD_TOTEM_BASE, Totemancy.SOULWOOD_TOTEM_BASE, Totemancy.WAVEFORM_RUNEWOOD_TOTEM_BASE, Totemancy.WAVEFORM_SOULWOOD_TOTEM_BASE);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, NO_DATAGEN, this::horizontalBlock, models()::predefinedModel,
                Focusing.SPIRIT_CATALYZER, Focusing.SPIRIT_CATALYZER_COMPONENT);

        MalumBlockStateSmithTypes.REPAIR_PYLON_COMPONENT.act(data, Focusing.REPAIR_PYLON_COMPONENT);

        BlockStateSmithTypes.CUSTOM_MODEL.act(data, BLOCK_MODEL_ITEM, this::simpleBlock, models()::predefinedModel, Enscription.WEEPING_WELL_CENTERPIECE);

        MalumBlockStateSmithTypes.PRIMORDIAL_SOUP.act(data, Enscription.WEEPING_WELL);

        BlockStateSmithTypes.FULL_BLOCK.act(data, BuildingBlocks.THE_DEVICE, BuildingBlocks.THE_VESSEL);
    }

    public void generateVariedBlockBundle(BlockStateSystemData<MalumBlockStateDatagen> data, BlockBundle bundle) {
        VariedBlockStateSmithTypes.VARIED_FULL_BLOCK.act(data, bundle.block);
        VariedBlockStateSmithTypes.VARIED_STAIRS_BLOCK.act(data, bundle.stairs);
        VariedBlockStateSmithTypes.VARIED_SLAB_BLOCK.act(data, bundle.slab);
        if (bundle instanceof BlockBundleWithWall wall) {
            VariedBlockStateSmithTypes.VARIED_WALL_BLOCK.act(data, wall.wall);
        }
    }

    public void generateBlockBundle(BlockStateSystemData<MalumBlockStateDatagen> data, BlockBundle bundle) {
        BlockStateSmithTypes.FULL_BLOCK.act(data, bundle.block);
        BlockStateSmithTypes.STAIRS_BLOCK.act(data, bundle.stairs);
        BlockStateSmithTypes.SLAB_BLOCK.act(data, bundle.slab);
        if (bundle instanceof BlockBundleWithWall wall) {
            BlockStateSmithTypes.WALL_BLOCK.act(data, wall.wall);
        }
    }

    public ModelFile stoneBookshelfModel(Block block) {
        var texture = getBlockTexture(block);
        var stoneBricks = ResourceLocation.parse("minecraft:block/stone_bricks");
        return models().cubeBottomTop(getBlockName(block), texture, stoneBricks, stoneBricks);
    }

    public ModelFile rockItemPedestalModel(Block block) {
        return itemPedestalModel(block, "template_item_pedestal_rock");
    }

    public ModelFile woodenItemPedestalModel(Block block) {
        return itemPedestalModel(block, "template_item_pedestal_wooden");
    }

    public ModelFile decoratedItemPedestalModel(Block block) {
        return itemPedestalModel(block, "template_item_pedestal_wooden_decorated", s -> s.substring(s.indexOf("_") + 1) + "_" + s.split("_")[0]);
    }

    public ModelFile itemPedestalModel(Block block, String template) {
        return itemPedestalModel(block, template, s -> s);
    }

    public ModelFile itemPedestalModel(Block block, String template, Function<String, String> pathFunction) {
        String name = getBlockName(block);
        var parent = malumPath("block/templates/" + template);
        var pedestal = getBlockTexture(pathFunction.apply(name));
        return models().withExistingParent(name, parent).texture("pedestal", pedestal);
    }

    public ModelFile itemStandModel(Block block) {
        return itemStandModel(block, "template_item_stand", s -> s);
    }

    public ModelFile decoratedItemStandModel(Block block) {
        return itemStandModel(block, "template_item_stand_decorated", s -> s.substring(s.indexOf("_") + 1) + "_" + s.split("_")[0]);
    }

    public ModelFile itemStandModel(Block block, String template, Function<String, String> pathFunction) {
        String name = getBlockName(block);
        ResourceLocation parent = malumPath("block/templates/" + template);
        ResourceLocation stand = getBlockTexture(pathFunction.apply(name));
        return models().withExistingParent(name, parent).texture("stand", stand);
    }

    public ModelFile etherModel(Block block) {
        String name = getBlockName(block);
        return models().withExistingParent(name, ResourceLocation.withDefaultNamespace("block/air")).texture("particle", itemModelProvider.getItemTexture("ether"));
    }

    public ModelFile poppetPillowModel(Block block) {
        String name = getBlockName(block);
        String[] split = name.split("_");
        var raw = String.join("_", Arrays.copyOfRange(split, split.length - 3, split.length));
        var colored = getBlockTexture(name);
        var bottom = getBlockTexture(raw + "_bottom");
        return models().withExistingParent(name, malumPath("block/poppet_pillow")).texture("pillow", colored).texture("bottom", bottom);
    }

    public ModelFile totemBaseModel(Block block) {
        return models().withExistingParent(block, malumPath("block/templates/template_totem_base"), "totem_base");
    }

    public ModelFile blightedSoulwoodModel(Block block) {
        String name = getBlockName(block);
        ResourceLocation side = getBlockTexture(name);
        ResourceLocation bottom = getBlockTexture("blighted_earth_bottom");
        ResourceLocation top = getAbsoluteBlockTexture("building/soulwood/soulwood_log_top");
        return models().cubeBottomTop(name, side, bottom, top);
    }
}