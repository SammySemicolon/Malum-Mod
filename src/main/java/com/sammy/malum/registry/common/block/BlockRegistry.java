package com.sammy.malum.registry.common.block;

import com.sammy.malum.*;
import com.sammy.malum.common.block.blight.*;
import com.sammy.malum.common.block.curiosities.obelisk.*;
import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.common.block.curiosities.tablet.*;
import com.sammy.malum.common.block.curiosities.totem.*;
import com.sammy.malum.common.block.curiosities.weavers_workbench.*;
import com.sammy.malum.common.block.curiosities.weeping_well.*;
import com.sammy.malum.common.block.ether.*;
import com.sammy.malum.common.block.mana_mote.*;
import com.sammy.malum.common.block.nature.*;
import com.sammy.malum.common.block.nature.soulwood.*;
import com.sammy.malum.common.block.storage.jar.*;
import com.sammy.malum.common.block.storage.pedestal.*;
import com.sammy.malum.common.block.storage.stand.*;
import com.sammy.malum.common.block.the_device.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.registry.common.worldgen.*;
import net.minecraft.client.color.block.*;
import net.minecraft.util.*;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.block.*;
import team.lodestar.lodestone.systems.block.sign.*;
import team.lodestar.lodestone.systems.easing.*;

import java.awt.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.block.BlockTagRegistry.*;
import static net.minecraft.tags.BlockTags.FENCES;
import static net.minecraft.tags.BlockTags.FENCE_GATES;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.world.level.block.PressurePlateBlock.Sensitivity.*;
import static net.minecraftforge.common.Tags.Blocks.*;


public class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MALUM);

    //region useful blocks
    public static final RegistryObject<Block> SPIRIT_ALTAR = BLOCKS.register("spirit_altar", () -> new SpiritAltarBlock<>(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SPIRIT_ALTAR));
    public static final RegistryObject<Block> SPIRIT_JAR = BLOCKS.register("spirit_jar", () -> new SpiritJarBlock<>(MalumBlockProperties.SPIRIT_JAR().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SPIRIT_JAR));
    public static final RegistryObject<Block> RITUAL_PLINTH = BLOCKS.register("ritual_plinth", () -> new RitualPlinthBlock<>(MalumBlockProperties.SOULWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.RITUAL_PLINTH));

    public static final RegistryObject<Block> WEAVERS_WORKBENCH = BLOCKS.register("weavers_workbench", () -> new WeaversWorkbenchBlock<>(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.WEAVERS_WORKBENCH));

    public static final RegistryObject<Block> TWISTED_TABLET = BLOCKS.register("twisted_tablet", () -> new TwistedTabletBlock<>(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.TWISTED_TABLET));

    public static final RegistryObject<Block> RUNEWOOD_OBELISK = BLOCKS.register("runewood_obelisk", () -> new RunewoodObeliskCoreBlock(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()));
    public static final RegistryObject<Block> RUNEWOOD_OBELISK_COMPONENT = BLOCKS.register("runewood_obelisk_component", () -> new ObeliskComponentBlock(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().lootFrom(RUNEWOOD_OBELISK).noOcclusion(), ItemRegistry.RUNEWOOD_OBELISK));

    public static final RegistryObject<Block> BRILLIANT_OBELISK = BLOCKS.register("brilliant_obelisk", () -> new BrillianceObeliskCoreBlock(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().noOcclusion()));
    public static final RegistryObject<Block> BRILLIANT_OBELISK_COMPONENT = BLOCKS.register("brilliant_obelisk_component", () -> new ObeliskComponentBlock(MalumBlockProperties.RUNEWOOD().setCutoutRenderType().lootFrom(BRILLIANT_OBELISK).noOcclusion(), ItemRegistry.BRILLIANT_OBELISK));

    public static final RegistryObject<Block> SPIRIT_CRUCIBLE = BLOCKS.register("spirit_crucible", () -> new SpiritCrucibleCoreBlock<>(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SPIRIT_CRUCIBLE));
    public static final RegistryObject<Block> SPIRIT_CRUCIBLE_COMPONENT = BLOCKS.register("spirit_crucible_component", () -> new SpiritCrucibleComponentBlock(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().lootFrom(SPIRIT_CRUCIBLE).noOcclusion()));

    public static final RegistryObject<Block> SPIRIT_CATALYZER = BLOCKS.register("spirit_catalyzer", () -> new SpiritCatalyzerCoreBlock<>(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SPIRIT_CATALYZER));
    public static final RegistryObject<Block> SPIRIT_CATALYZER_COMPONENT = BLOCKS.register("spirit_catalyzer_component", () -> new SpiritCatalyzerComponentBlock(MalumBlockProperties.TAINTED_ROCK().setCutoutRenderType().lootFrom(SPIRIT_CATALYZER).noOcclusion(), ItemRegistry.SPIRIT_CATALYZER));

    public static final RegistryObject<Block> RUNEWOOD_TOTEM_BASE = BLOCKS.register("runewood_totem_base", () -> new TotemBaseBlock<>(MalumBlockProperties.RUNEWOOD().addTag(RITE_IMMUNE).noOcclusion(), false).setBlockEntity(BlockEntityRegistry.TOTEM_BASE));
    public static final RegistryObject<Block> RUNEWOOD_TOTEM_POLE = BLOCKS.register("runewood_totem_pole", () -> new TotemPoleBlock<>(MalumBlockProperties.RUNEWOOD().addTag(RITE_IMMUNE).noOcclusion(), BlockRegistry.RUNEWOOD_LOG, false).setBlockEntity(BlockEntityRegistry.TOTEM_POLE));

    public static final RegistryObject<Block> SOULWOOD_TOTEM_BASE = BLOCKS.register("soulwood_totem_base", () -> new TotemBaseBlock<>(MalumBlockProperties.SOULWOOD().addTag(RITE_IMMUNE).noOcclusion(), true).setBlockEntity(BlockEntityRegistry.TOTEM_BASE));
    public static final RegistryObject<Block> SOULWOOD_TOTEM_POLE = BLOCKS.register("soulwood_totem_pole", () -> new TotemPoleBlock<>(MalumBlockProperties.SOULWOOD().addTag(RITE_IMMUNE).noOcclusion(), BlockRegistry.SOULWOOD_LOG, true).setBlockEntity(BlockEntityRegistry.TOTEM_POLE));

    public static final RegistryObject<Block> VOID_CONDUIT = BLOCKS.register("void_conduit", () -> new VoidConduitBlock<>(MalumBlockProperties.PRIMORDIAL_SOUP().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.VOID_CONDUIT));
    public static final RegistryObject<Block> PRIMORDIAL_SOUP = BLOCKS.register("primordial_soup", () -> new PrimordialSoupBlock(MalumBlockProperties.PRIMORDIAL_SOUP().setCutoutRenderType().noOcclusion()));
    public static final RegistryObject<Block> WEEPING_WELL_CORNER = BLOCKS.register("weeping_well_corner", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL().setCutoutRenderType()));
    public static final RegistryObject<Block> WEEPING_WELL_SIDE = BLOCKS.register("weeping_well_side", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL().setCutoutRenderType()));
    public static final RegistryObject<Block> WEEPING_WELL_CORE = BLOCKS.register("weeping_well_core", () -> new WeepingWellBlock(MalumBlockProperties.WEEPING_WELL().setCutoutRenderType()));
    //endregion

    //region tainted rock
    public static final RegistryObject<Block> TAINTED_ROCK = BLOCKS.register("tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK = BLOCKS.register("smooth_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK = BLOCKS.register("polished_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS = BLOCKS.register("tainted_rock_bricks", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES = BLOCKS.register("tainted_rock_tiles", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS = BLOCKS.register("small_tainted_rock_bricks", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> RUNIC_TAINTED_ROCK_BRICKS = BLOCKS.register("runic_tainted_rock_bricks", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> RUNIC_TAINTED_ROCK_TILES = BLOCKS.register("runic_tainted_rock_tiles", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> RUNIC_SMALL_TAINTED_ROCK_BRICKS = BLOCKS.register("runic_small_tainted_rock_bricks", () -> new Block(MalumBlockProperties.TAINTED_ROCK_BRICKS()));

    public static final RegistryObject<Block> TAINTED_ROCK_COLUMN = BLOCKS.register("tainted_rock_column", () -> new RotatedPillarBlock(MalumBlockProperties.TAINTED_ROCK()));
    public static final RegistryObject<Block> TAINTED_ROCK_COLUMN_CAP = BLOCKS.register("tainted_rock_column_cap", () -> new LodestoneDirectionalBlock(MalumBlockProperties.TAINTED_ROCK()));

    public static final RegistryObject<Block> CUT_TAINTED_ROCK = BLOCKS.register("cut_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK()));
    public static final RegistryObject<Block> CHISELED_TAINTED_ROCK = BLOCKS.register("chiseled_tainted_rock", () -> new Block(MalumBlockProperties.TAINTED_ROCK()));

    public static final RegistryObject<Block> TAINTED_ROCK_SLAB = BLOCKS.register("tainted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK().addTags(SLABS)));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_SLAB = BLOCKS.register("smooth_tainted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK().addTags(SLABS)));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_SLAB = BLOCKS.register("polished_tainted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK().addTags(SLABS)));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("tainted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_SLAB = BLOCKS.register("tainted_rock_tiles_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("small_tainted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> RUNIC_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("runic_tainted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> RUNIC_TAINTED_ROCK_TILES_SLAB = BLOCKS.register("runic_tainted_rock_tiles_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("runic_small_tainted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(SLABS)));

    public static final RegistryObject<Block> TAINTED_ROCK_STAIRS = BLOCKS.register("tainted_rock_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK().addTags(STAIRS)));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_STAIRS = BLOCKS.register("smooth_tainted_rock_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK().addTags(STAIRS)));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_STAIRS = BLOCKS.register("polished_tainted_rock_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK().addTags(STAIRS)));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("tainted_rock_bricks_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("tainted_rock_tiles_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_tainted_rock_bricks_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> RUNIC_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("runic_tainted_rock_bricks_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> RUNIC_TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("runic_tainted_rock_tiles_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("runic_small_tainted_rock_bricks_stairs", () -> new StairBlock(() -> TAINTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TAINTED_ROCK_BRICKS().addTags(STAIRS)));

    public static final RegistryObject<Block> TAINTED_ROCK_BUTTON = BLOCKS.register("tainted_rock_button", () -> new ButtonBlock(MalumBlockProperties.TAINTED_ROCK().addTag(BUTTONS), BlockSetType.STONE, 20, false));
    public static final RegistryObject<Block> TAINTED_ROCK_PRESSURE_PLATE = BLOCKS.register("tainted_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, MalumBlockProperties.TAINTED_ROCK().addTag(PRESSURE_PLATES), BlockSetType.STONE));

    public static final RegistryObject<Block> TAINTED_ROCK_WALL = BLOCKS.register("tainted_rock_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK().addTag(WALLS)));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("tainted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_WALL = BLOCKS.register("tainted_rock_tiles_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("small_tainted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> RUNIC_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("runic_tainted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> RUNIC_TAINTED_ROCK_TILES_WALL = BLOCKS.register("runic_tainted_rock_tiles_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("runic_small_tainted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TAINTED_ROCK_BRICKS().addTag(WALLS)));

    public static final RegistryObject<Block> TAINTED_ROCK_ITEM_STAND = BLOCKS.register("tainted_rock_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.TAINTED_ROCK().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final RegistryObject<Block> TAINTED_ROCK_ITEM_PEDESTAL = BLOCKS.register("tainted_rock_item_pedestal", () -> new ItemPedestalBlock<>(MalumBlockProperties.TAINTED_ROCK().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));
    //endregion

    //region twisted rock
    public static final RegistryObject<Block> TWISTED_ROCK = BLOCKS.register("twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK()));
    public static final RegistryObject<Block> SMOOTH_TWISTED_ROCK = BLOCKS.register("smooth_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK()));
    public static final RegistryObject<Block> POLISHED_TWISTED_ROCK = BLOCKS.register("polished_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK()));
    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS = BLOCKS.register("twisted_rock_bricks", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> TWISTED_ROCK_TILES = BLOCKS.register("twisted_rock_tiles", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS = BLOCKS.register("small_twisted_rock_bricks", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> RUNIC_TWISTED_ROCK_BRICKS = BLOCKS.register("runic_twisted_rock_bricks", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> RUNIC_TWISTED_ROCK_TILES = BLOCKS.register("runic_twisted_rock_tiles", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS()));
    public static final RegistryObject<Block> RUNIC_SMALL_TWISTED_ROCK_BRICKS = BLOCKS.register("runic_small_twisted_rock_bricks", () -> new Block(MalumBlockProperties.TWISTED_ROCK_BRICKS()));

    public static final RegistryObject<Block> TWISTED_ROCK_COLUMN = BLOCKS.register("twisted_rock_column", () -> new RotatedPillarBlock(MalumBlockProperties.TWISTED_ROCK()));
    public static final RegistryObject<Block> TWISTED_ROCK_COLUMN_CAP = BLOCKS.register("twisted_rock_column_cap", () -> new LodestoneDirectionalBlock(MalumBlockProperties.TWISTED_ROCK()));

    public static final RegistryObject<Block> CUT_TWISTED_ROCK = BLOCKS.register("cut_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK()));
    public static final RegistryObject<Block> CHISELED_TWISTED_ROCK = BLOCKS.register("chiseled_twisted_rock", () -> new Block(MalumBlockProperties.TWISTED_ROCK()));

    public static final RegistryObject<Block> TWISTED_ROCK_SLAB = BLOCKS.register("twisted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK().addTags(SLABS)));
    public static final RegistryObject<Block> SMOOTH_TWISTED_ROCK_SLAB = BLOCKS.register("smooth_twisted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK().addTags(SLABS)));
    public static final RegistryObject<Block> POLISHED_TWISTED_ROCK_SLAB = BLOCKS.register("polished_twisted_rock_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK().addTags(SLABS)));
    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("twisted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> TWISTED_ROCK_TILES_SLAB = BLOCKS.register("twisted_rock_tiles_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("small_twisted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> RUNIC_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("runic_twisted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> RUNIC_TWISTED_ROCK_TILES_SLAB = BLOCKS.register("runic_twisted_rock_tiles_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS)));
    public static final RegistryObject<Block> RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("runic_small_twisted_rock_bricks_slab", () -> new SlabBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(SLABS)));

    public static final RegistryObject<Block> TWISTED_ROCK_STAIRS = BLOCKS.register("twisted_rock_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK().addTags(STAIRS)));
    public static final RegistryObject<Block> SMOOTH_TWISTED_ROCK_STAIRS = BLOCKS.register("smooth_twisted_rock_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK().addTags(STAIRS)));
    public static final RegistryObject<Block> POLISHED_TWISTED_ROCK_STAIRS = BLOCKS.register("polished_twisted_rock_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK().addTags(STAIRS)));
    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("twisted_rock_bricks_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> TWISTED_ROCK_TILES_STAIRS = BLOCKS.register("twisted_rock_tiles_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_twisted_rock_bricks_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> RUNIC_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("runic_twisted_rock_bricks_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> RUNIC_TWISTED_ROCK_TILES_STAIRS = BLOCKS.register("runic_twisted_rock_tiles_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS)));
    public static final RegistryObject<Block> RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("runic_small_twisted_rock_bricks_stairs", () -> new StairBlock(() -> TWISTED_ROCK.get().defaultBlockState(), MalumBlockProperties.TWISTED_ROCK_BRICKS().addTags(STAIRS)));

    public static final RegistryObject<Block> TWISTED_ROCK_BUTTON = BLOCKS.register("twisted_rock_button", () -> new ButtonBlock(MalumBlockProperties.TWISTED_ROCK().addTag(BUTTONS), BlockSetType.STONE, 20, false));
    public static final RegistryObject<Block> TWISTED_ROCK_PRESSURE_PLATE = BLOCKS.register("twisted_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, MalumBlockProperties.TWISTED_ROCK().addTag(PRESSURE_PLATES), BlockSetType.STONE));

    public static final RegistryObject<Block> TWISTED_ROCK_WALL = BLOCKS.register("twisted_rock_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK().addTag(WALLS)));
    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("twisted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> TWISTED_ROCK_TILES_WALL = BLOCKS.register("twisted_rock_tiles_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("small_twisted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> RUNIC_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("runic_twisted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> RUNIC_TWISTED_ROCK_TILES_WALL = BLOCKS.register("runic_twisted_rock_tiles_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(WALLS)));
    public static final RegistryObject<Block> RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("runic_small_twisted_rock_bricks_wall", () -> new WallBlock(MalumBlockProperties.TWISTED_ROCK_BRICKS().addTag(WALLS)));

    public static final RegistryObject<Block> TWISTED_ROCK_ITEM_STAND = BLOCKS.register("twisted_rock_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.TWISTED_ROCK().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final RegistryObject<Block> TWISTED_ROCK_ITEM_PEDESTAL = BLOCKS.register("twisted_rock_item_pedestal", () -> new ItemPedestalBlock<>(MalumBlockProperties.TWISTED_ROCK().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));
    //endregion

    //region runewood
    public static final RegistryObject<Block> RUNEWOOD_SAPLING = BLOCKS.register("runewood_sapling", () -> new MalumSaplingBlock(MalumBlockProperties.RUNEWOOD_SAPLING().setCutoutRenderType().randomTicks(), FeatureRegistry.RUNEWOOD_TREE));
    public static final RegistryObject<Block> RUNEWOOD_LEAVES = BLOCKS.register("runewood_leaves", () -> new MalumLeavesBlock(MalumBlockProperties.RUNEWOOD_LEAVES().setCutoutRenderType(), new Color(217, 110, 23), new Color(251, 193, 76)));
    public static final RegistryObject<Block> HANGING_RUNEWOOD_LEAVES = BLOCKS.register("hanging_runewood_leaves", () -> new MalumHangingLeavesBlock(MalumBlockProperties.RUNEWOOD_LEAVES().setCutoutRenderType().noOcclusion().noCollission(), new Color(217, 110, 23), new Color(251, 193, 76)));

    public static final RegistryObject<Block> STRIPPED_RUNEWOOD_LOG = BLOCKS.register("stripped_runewood_log", () -> new RotatedPillarBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, STRIPPED_LOGS, RUNEWOOD_LOGS)));
    public static final RegistryObject<Block> RUNEWOOD_LOG = BLOCKS.register("runewood_log", () -> new MalumLogBLock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, RUNEWOOD_LOGS), STRIPPED_RUNEWOOD_LOG, false));
    public static final RegistryObject<Block> STRIPPED_RUNEWOOD = BLOCKS.register("stripped_runewood", () -> new RotatedPillarBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, STRIPPED_LOGS, RUNEWOOD_LOGS)));
    public static final RegistryObject<Block> RUNEWOOD = BLOCKS.register("runewood", () -> new LodestoneLogBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, RUNEWOOD_LOGS), STRIPPED_RUNEWOOD));

    public static final RegistryObject<Block> REVEALED_RUNEWOOD_LOG = BLOCKS.register("revealed_runewood_log", () -> new SapFilledLogBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, RUNEWOOD_LOGS), STRIPPED_RUNEWOOD_LOG, ItemRegistry.RUNIC_SAP, SpiritTypeRegistry.INFERNAL_SPIRIT.getPrimaryColor()));
    public static final RegistryObject<Block> EXPOSED_RUNEWOOD_LOG = BLOCKS.register("exposed_runewood_log", () -> new LodestoneLogBlock(MalumBlockProperties.RUNEWOOD().addTags(LOGS, STRIPPED_LOGS, RUNEWOOD_LOGS), REVEALED_RUNEWOOD_LOG));

    public static final RegistryObject<Block> RUNEWOOD_PLANKS = BLOCKS.register("runewood_planks", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_SLAB = BLOCKS.register("runewood_planks_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("runewood_planks_stairs", () -> new StairBlock(() -> RUNEWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS = BLOCKS.register("vertical_runewood_planks", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS_SLAB = BLOCKS.register("vertical_runewood_planks_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_runewood_planks_stairs", () -> new StairBlock(() -> VERTICAL_RUNEWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final RegistryObject<Block> RUNEWOOD_PANEL = BLOCKS.register("runewood_panel", () -> new Block(MalumBlockProperties.RUNEWOOD()));
    public static final RegistryObject<Block> RUNEWOOD_PANEL_SLAB = BLOCKS.register("runewood_panel_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final RegistryObject<Block> RUNEWOOD_PANEL_STAIRS = BLOCKS.register("runewood_panel_stairs", () -> new StairBlock(() -> RUNEWOOD_PANEL.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final RegistryObject<Block> RUNEWOOD_TILES = BLOCKS.register("runewood_tiles", () -> new Block(MalumBlockProperties.RUNEWOOD()));
    public static final RegistryObject<Block> RUNEWOOD_TILES_SLAB = BLOCKS.register("runewood_tiles_slab", () -> new SlabBlock(MalumBlockProperties.RUNEWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final RegistryObject<Block> RUNEWOOD_TILES_STAIRS = BLOCKS.register("runewood_tiles_stairs", () -> new StairBlock(() -> RUNEWOOD_TILES.get().defaultBlockState(), MalumBlockProperties.RUNEWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final RegistryObject<Block> CUT_RUNEWOOD_PLANKS = BLOCKS.register("cut_runewood_planks", () -> new Block(MalumBlockProperties.RUNEWOOD().addTags(PLANKS)));
    public static final RegistryObject<Block> RUNEWOOD_BEAM = BLOCKS.register("runewood_beam", () -> new RotatedPillarBlock(MalumBlockProperties.RUNEWOOD()));

    public static final RegistryObject<Block> RUNEWOOD_DOOR = BLOCKS.register("runewood_door", () -> new DoorBlock(MalumBlockProperties.RUNEWOOD().addTags(DOORS, WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MalumBlockSetTypes.RUNEWOOD));
    public static final RegistryObject<Block> RUNEWOOD_TRAPDOOR = BLOCKS.register("runewood_trapdoor", () -> new TrapDoorBlock(MalumBlockProperties.RUNEWOOD().addTags(TRAPDOORS, WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MalumBlockSetTypes.RUNEWOOD));
    public static final RegistryObject<Block> SOLID_RUNEWOOD_TRAPDOOR = BLOCKS.register("solid_runewood_trapdoor", () -> new TrapDoorBlock(MalumBlockProperties.RUNEWOOD().addTags(TRAPDOORS, WOODEN_TRAPDOORS).noOcclusion(), MalumBlockSetTypes.RUNEWOOD));

    public static final RegistryObject<Block> RUNEWOOD_BUTTON = BLOCKS.register("runewood_planks_button", () -> new ButtonBlock(MalumBlockProperties.RUNEWOOD().addTags(BUTTONS, WOODEN_BUTTONS).addTags(BUTTONS, WOODEN_BUTTONS), MalumBlockSetTypes.RUNEWOOD, 20, true));
    public static final RegistryObject<Block> RUNEWOOD_PRESSURE_PLATE = BLOCKS.register("runewood_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, MalumBlockProperties.RUNEWOOD().addTags(PRESSURE_PLATES, WOODEN_PRESSURE_PLATES), MalumBlockSetTypes.RUNEWOOD));

    public static final RegistryObject<Block> RUNEWOOD_FENCE = BLOCKS.register("runewood_planks_fence", () -> new FenceBlock(MalumBlockProperties.RUNEWOOD().addTags(FENCES, WOODEN_FENCES)));
    public static final RegistryObject<Block> RUNEWOOD_FENCE_GATE = BLOCKS.register("runewood_planks_fence_gate", () -> new FenceGateBlock(MalumBlockProperties.RUNEWOOD().addTags(FENCE_GATES, FENCE_GATES_WOODEN), WoodTypeRegistry.RUNEWOOD));

    public static final RegistryObject<Block> RUNEWOOD_ITEM_STAND = BLOCKS.register("runewood_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.RUNEWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final RegistryObject<Block> RUNEWOOD_ITEM_PEDESTAL = BLOCKS.register("runewood_item_pedestal", () -> new WoodItemPedestalBlock<>(MalumBlockProperties.RUNEWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));

    public static final RegistryObject<Block> RUNEWOOD_SIGN = BLOCKS.register("runewood_sign", () -> new LodestoneStandingSignBlock(MalumBlockProperties.RUNEWOOD().addTags(SIGNS, STANDING_SIGNS).noOcclusion().noCollission(), WoodTypeRegistry.RUNEWOOD));
    public static final RegistryObject<Block> RUNEWOOD_WALL_SIGN = BLOCKS.register("runewood_wall_sign", () -> new LodestoneWallSignBlock(MalumBlockProperties.RUNEWOOD().addTags(SIGNS, WALL_SIGNS).noOcclusion().noCollission(), WoodTypeRegistry.RUNEWOOD));
    //endregion

    //region soulwood
    public static final RegistryObject<Block> SOULWOOD_GROWTH = BLOCKS.register("soulwood_growth", () -> new SoulwoodGrowthBlock(MalumBlockProperties.BLIGHTED_PLANTS().setCutoutRenderType().randomTicks(), FeatureRegistry.SOULWOOD_TREE));
    public static final RegistryObject<Block> SOULWOOD_LEAVES = BLOCKS.register("soulwood_leaves", () -> new MalumLeavesBlock(MalumBlockProperties.SOULWOOD_LEAVES().setCutoutRenderType(), new Color(213, 8, 63), new Color(255, 61, 243)));
    public static final RegistryObject<Block> MYSTIC_SOULWOOD_LEAVES = BLOCKS.register("mystic_soulwood_leaves", () -> new MalumLeavesBlock(MalumBlockProperties.SOULWOOD_LEAVES().setCutoutRenderType(), new Color(213, 8, 63), new Color(255, 61, 243)));
    public static final RegistryObject<Block> HANGING_MYSTIC_SOULWOOD_LEAVES = BLOCKS.register("hanging_mystic_soulwood_leaves", () -> new MalumHangingLeavesBlock(MalumBlockProperties.SOULWOOD_LEAVES().setCutoutRenderType().noOcclusion().noCollission(), new Color(213, 8, 63), new Color(255, 61, 243)));

    public static final RegistryObject<Block> STRIPPED_SOULWOOD_LOG = BLOCKS.register("stripped_soulwood_log", () -> new RotatedPillarBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, STRIPPED_LOGS, SOULWOOD_LOGS)));
    public static final RegistryObject<Block> SOULWOOD_LOG = BLOCKS.register("soulwood_log", () -> new SoulwoodLogBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, SOULWOOD_LOGS), STRIPPED_SOULWOOD_LOG, true));
    public static final RegistryObject<Block> STRIPPED_SOULWOOD = BLOCKS.register("stripped_soulwood", () -> new RotatedPillarBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, STRIPPED_LOGS, SOULWOOD_LOGS)));
    public static final RegistryObject<Block> SOULWOOD = BLOCKS.register("soulwood", () -> new SoulwoodBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, SOULWOOD_LOGS), STRIPPED_SOULWOOD));

    public static final RegistryObject<Block> REVEALED_SOULWOOD_LOG = BLOCKS.register("revealed_soulwood_log", () -> new SapFilledSoulwoodLogBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, STRIPPED_LOGS, SOULWOOD_LOGS), STRIPPED_SOULWOOD_LOG, ItemRegistry.CURSED_SAP, new Color(214, 46, 83)));
    public static final RegistryObject<Block> EXPOSED_SOULWOOD_LOG = BLOCKS.register("exposed_soulwood_log", () -> new SoulwoodBlock(MalumBlockProperties.SOULWOOD().addTags(LOGS, SOULWOOD_LOGS), REVEALED_SOULWOOD_LOG));

    public static final RegistryObject<Block> SOULWOOD_PLANKS = BLOCKS.register("soulwood_planks", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final RegistryObject<Block> SOULWOOD_PLANKS_SLAB = BLOCKS.register("soulwood_planks_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final RegistryObject<Block> SOULWOOD_PLANKS_STAIRS = BLOCKS.register("soulwood_planks_stairs", () -> new StairBlock(() -> SOULWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final RegistryObject<Block> VERTICAL_SOULWOOD_PLANKS = BLOCKS.register("vertical_soulwood_planks", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final RegistryObject<Block> VERTICAL_SOULWOOD_PLANKS_SLAB = BLOCKS.register("vertical_soulwood_planks_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final RegistryObject<Block> VERTICAL_SOULWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_soulwood_planks_stairs", () -> new StairBlock(() -> VERTICAL_SOULWOOD_PLANKS.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final RegistryObject<Block> SOULWOOD_PANEL = BLOCKS.register("soulwood_panel", () -> new Block(MalumBlockProperties.SOULWOOD()));
    public static final RegistryObject<Block> SOULWOOD_PANEL_SLAB = BLOCKS.register("soulwood_panel_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final RegistryObject<Block> SOULWOOD_PANEL_STAIRS = BLOCKS.register("soulwood_panel_stairs", () -> new StairBlock(() -> SOULWOOD_PANEL.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final RegistryObject<Block> SOULWOOD_TILES = BLOCKS.register("soulwood_tiles", () -> new Block(MalumBlockProperties.SOULWOOD()));
    public static final RegistryObject<Block> SOULWOOD_TILES_SLAB = BLOCKS.register("soulwood_tiles_slab", () -> new SlabBlock(MalumBlockProperties.SOULWOOD().addTags(SLABS, WOODEN_SLABS)));
    public static final RegistryObject<Block> SOULWOOD_TILES_STAIRS = BLOCKS.register("soulwood_tiles_stairs", () -> new StairBlock(() -> SOULWOOD_TILES.get().defaultBlockState(), MalumBlockProperties.SOULWOOD().addTags(STAIRS, WOODEN_STAIRS)));

    public static final RegistryObject<Block> CUT_SOULWOOD_PLANKS = BLOCKS.register("cut_soulwood_planks", () -> new Block(MalumBlockProperties.SOULWOOD().addTags(PLANKS)));
    public static final RegistryObject<Block> SOULWOOD_BEAM = BLOCKS.register("soulwood_beam", () -> new RotatedPillarBlock(MalumBlockProperties.SOULWOOD()));

    public static final RegistryObject<Block> SOULWOOD_DOOR = BLOCKS.register("soulwood_door", () -> new DoorBlock(MalumBlockProperties.SOULWOOD().addTags(DOORS, WOODEN_DOORS).setCutoutRenderType().noOcclusion(), MalumBlockSetTypes.SOULWOOD));
    public static final RegistryObject<Block> SOULWOOD_TRAPDOOR = BLOCKS.register("soulwood_trapdoor", () -> new TrapDoorBlock(MalumBlockProperties.SOULWOOD().addTags(TRAPDOORS, WOODEN_TRAPDOORS).setCutoutRenderType().noOcclusion(), MalumBlockSetTypes.SOULWOOD));
    public static final RegistryObject<Block> SOLID_SOULWOOD_TRAPDOOR = BLOCKS.register("solid_soulwood_trapdoor", () -> new TrapDoorBlock(MalumBlockProperties.SOULWOOD().addTags(TRAPDOORS, WOODEN_TRAPDOORS).noOcclusion(), MalumBlockSetTypes.SOULWOOD));

    public static final RegistryObject<Block> SOULWOOD_BUTTON = BLOCKS.register("soulwood_planks_button", () -> new ButtonBlock(MalumBlockProperties.SOULWOOD().addTags(BUTTONS, WOODEN_BUTTONS), MalumBlockSetTypes.SOULWOOD, 20, true));
    public static final RegistryObject<Block> SOULWOOD_PRESSURE_PLATE = BLOCKS.register("soulwood_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, MalumBlockProperties.SOULWOOD().addTags(PRESSURE_PLATES, WOODEN_PRESSURE_PLATES), MalumBlockSetTypes.SOULWOOD));

    public static final RegistryObject<Block> SOULWOOD_FENCE = BLOCKS.register("soulwood_planks_fence", () -> new FenceBlock(MalumBlockProperties.SOULWOOD().addTags(FENCES, WOODEN_FENCES)));
    public static final RegistryObject<Block> SOULWOOD_FENCE_GATE = BLOCKS.register("soulwood_planks_fence_gate", () -> new FenceGateBlock(MalumBlockProperties.SOULWOOD().addTags(FENCE_GATES, FENCE_GATES_WOODEN), WoodTypeRegistry.SOULWOOD));

    public static final RegistryObject<Block> SOULWOOD_ITEM_STAND = BLOCKS.register("soulwood_item_stand", () -> new ItemStandBlock<>(MalumBlockProperties.SOULWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_STAND));
    public static final RegistryObject<Block> SOULWOOD_ITEM_PEDESTAL = BLOCKS.register("soulwood_item_pedestal", () -> new WoodItemPedestalBlock<>(MalumBlockProperties.SOULWOOD().noOcclusion()).setBlockEntity(BlockEntityRegistry.ITEM_PEDESTAL));

    public static final RegistryObject<Block> SOULWOOD_SIGN = BLOCKS.register("soulwood_sign", () -> new LodestoneStandingSignBlock(MalumBlockProperties.SOULWOOD().addTags(SIGNS, STANDING_SIGNS).noOcclusion().noCollission(), WoodTypeRegistry.SOULWOOD));
    public static final RegistryObject<Block> SOULWOOD_WALL_SIGN = BLOCKS.register("soulwood_wall_sign", () -> new LodestoneWallSignBlock(MalumBlockProperties.SOULWOOD().addTags(SIGNS, WALL_SIGNS).noOcclusion().noCollission(), WoodTypeRegistry.SOULWOOD));
    //endregion

    //region blight
    public static final RegistryObject<Block> BLIGHTED_EARTH = BLOCKS.register("blighted_earth", () -> new BlightedSoilBlock(MalumBlockProperties.BLIGHT()));
    public static final RegistryObject<Block> BLIGHTED_SOIL = BLOCKS.register("blighted_soil", () -> new BlightedSoilBlock(MalumBlockProperties.BLIGHT()));
    public static final RegistryObject<Block> BLIGHTED_GROWTH = BLOCKS.register("blighted_growth", () -> new BlightedGrowthBlock(MalumBlockProperties.BLIGHTED_PLANTS().setCutoutRenderType()));
    public static final RegistryObject<Block> CLINGING_BLIGHT = BLOCKS.register("clinging_blight", () -> new ClingingBlightBlock(MalumBlockProperties.BLIGHTED_PLANTS().setCutoutRenderType()));
    public static final RegistryObject<Block> BLIGHTED_SOULWOOD = BLOCKS.register("blighted_soulwood", () -> new BlightedSoulwoodBlock(MalumBlockProperties.SOULWOOD()));
    //endregion

    //region empowered blocks
    public static final RegistryObject<SpiritMoteBlock> MOTE_OF_SACRED_ARCANA = BLOCKS.register("mote_of_sacred_arcana", () -> new SpiritMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK(), SpiritTypeRegistry.SACRED_SPIRIT).setBlockEntity(BlockEntityRegistry.MOTE_OF_MANA));
    public static final RegistryObject<SpiritMoteBlock> MOTE_OF_WICKED_ARCANA = BLOCKS.register("mote_of_wicked_arcana", () -> new SpiritMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK(), SpiritTypeRegistry.WICKED_SPIRIT).setBlockEntity(BlockEntityRegistry.MOTE_OF_MANA));
    public static final RegistryObject<SpiritMoteBlock> MOTE_OF_RAW_ARCANA = BLOCKS.register("mote_of_raw_arcana", () -> new SpiritMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK(), SpiritTypeRegistry.ARCANE_SPIRIT).setBlockEntity(BlockEntityRegistry.MOTE_OF_MANA));
    public static final RegistryObject<SpiritMoteBlock> MOTE_OF_ELDRITCH_ARCANA = BLOCKS.register("mote_of_eldritch_arcana", () -> new SpiritMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK(), SpiritTypeRegistry.ELDRITCH_SPIRIT).setBlockEntity(BlockEntityRegistry.MOTE_OF_MANA));
    public static final RegistryObject<SpiritMoteBlock> MOTE_OF_AERIAL_ARCANA = BLOCKS.register("mote_of_aerial_arcana", () -> new SpiritMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK(), SpiritTypeRegistry.AERIAL_SPIRIT).setBlockEntity(BlockEntityRegistry.MOTE_OF_MANA));
    public static final RegistryObject<SpiritMoteBlock> MOTE_OF_AQUEOUS_ARCANA = BLOCKS.register("mote_of_aqueous_arcana", () -> new SpiritMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK(), SpiritTypeRegistry.AQUEOUS_SPIRIT).setBlockEntity(BlockEntityRegistry.MOTE_OF_MANA));
    public static final RegistryObject<SpiritMoteBlock> MOTE_OF_INFERNAL_ARCANA = BLOCKS.register("mote_of_infernal_arcana", () -> new SpiritMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK(), SpiritTypeRegistry.INFERNAL_SPIRIT).setBlockEntity(BlockEntityRegistry.MOTE_OF_MANA));
    public static final RegistryObject<SpiritMoteBlock> MOTE_OF_EARTHEN_ARCANA = BLOCKS.register("mote_of_earthen_arcana", () -> new SpiritMoteBlock(MalumBlockProperties.MANA_MOTE_BLOCK(), SpiritTypeRegistry.EARTHEN_SPIRIT).setBlockEntity(BlockEntityRegistry.MOTE_OF_MANA));
    //endregion

    //region ether
    public static final RegistryObject<Block> ETHER = BLOCKS.register("ether", () -> new EtherBlock<>(MalumBlockProperties.ETHER().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType()).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final RegistryObject<Block> ETHER_TORCH = BLOCKS.register("ether_torch", () -> new EtherTorchBlock<>(MalumBlockProperties.RUNEWOOD().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType().noCollission().instabreak().lightLevel((b) -> 14)).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final RegistryObject<Block> WALL_ETHER_TORCH = BLOCKS.register("wall_ether_torch", () -> new EtherWallTorchBlock<>(MalumBlockProperties.RUNEWOOD().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType().noCollission().instabreak().lightLevel((b) -> 14).lootFrom(ETHER_TORCH)).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final RegistryObject<Block> TAINTED_ETHER_BRAZIER = BLOCKS.register("tainted_ether_brazier", () -> new EtherBrazierBlock<>(MalumBlockProperties.TAINTED_ROCK().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType().lightLevel((b) -> 14).noOcclusion()).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final RegistryObject<Block> TWISTED_ETHER_BRAZIER = BLOCKS.register("twisted_ether_brazier", () -> new EtherBrazierBlock<>(MalumBlockProperties.TWISTED_ROCK().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType().lightLevel((b) -> 14).noOcclusion()).setBlockEntity(BlockEntityRegistry.ETHER));

    public static final RegistryObject<Block> IRIDESCENT_ETHER = BLOCKS.register("iridescent_ether", () -> new EtherBlock<>(MalumBlockProperties.ETHER().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType()).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final RegistryObject<Block> IRIDESCENT_ETHER_TORCH = BLOCKS.register("iridescent_ether_torch", () -> new EtherTorchBlock<>(MalumBlockProperties.RUNEWOOD().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType().noCollission().instabreak().lightLevel((b) -> 14)).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final RegistryObject<Block> IRIDESCENT_WALL_ETHER_TORCH = BLOCKS.register("iridescent_wall_ether_torch", () -> new EtherWallTorchBlock<>(MalumBlockProperties.RUNEWOOD().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType().noCollission().instabreak().lightLevel((b) -> 14).lootFrom(IRIDESCENT_ETHER_TORCH)).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final RegistryObject<Block> TAINTED_IRIDESCENT_ETHER_BRAZIER = BLOCKS.register("tainted_iridescent_ether_brazier", () -> new EtherBrazierBlock<>(MalumBlockProperties.TAINTED_ROCK().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType().lightLevel((b) -> 14).noOcclusion()).setBlockEntity(BlockEntityRegistry.ETHER));
    public static final RegistryObject<Block> TWISTED_IRIDESCENT_ETHER_BRAZIER = BLOCKS.register("twisted_iridescent_ether_brazier", () -> new EtherBrazierBlock<>(MalumBlockProperties.TWISTED_ROCK().addTag(TRAY_HEAT_SOURCES).setCutoutRenderType().lightLevel((b) -> 14).noOcclusion()).setBlockEntity(BlockEntityRegistry.ETHER));
    //endregion

    public static final RegistryObject<Block> BLOCK_OF_ARCANE_CHARCOAL = BLOCKS.register("block_of_arcane_charcoal", () -> new Block(MalumBlockProperties.ARCANE_CHARCOAL_BLOCK()));

    public static final RegistryObject<Block> BLAZING_QUARTZ_ORE = BLOCKS.register("blazing_quartz_ore", () -> new DropExperienceBlock(MalumBlockProperties.BLAZING_QUARTZ_ORE().setCutoutRenderType().lightLevel((b) -> 6), UniformInt.of(4, 7)));
    public static final RegistryObject<Block> BLOCK_OF_BLAZING_QUARTZ = BLOCKS.register("block_of_blazing_quartz", () -> new Block(MalumBlockProperties.BLAZING_QUARTZ_BLOCK().lightLevel((b) -> 14)));

    public static final RegistryObject<Block> NATURAL_QUARTZ_ORE = BLOCKS.register("natural_quartz_ore", () -> new DropExperienceBlock(MalumBlockProperties.NATURAL_QUARTZ_ORE(false).setCutoutRenderType(), UniformInt.of(1, 4)));
    public static final RegistryObject<Block> DEEPSLATE_QUARTZ_ORE = BLOCKS.register("deepslate_quartz_ore", () -> new DropExperienceBlock(MalumBlockProperties.NATURAL_QUARTZ_ORE(true).setCutoutRenderType(), UniformInt.of(2, 5)));
    public static final RegistryObject<Block> NATURAL_QUARTZ_CLUSTER = BLOCKS.register("natural_quartz_cluster", () -> new AmethystClusterBlock(6, 3, MalumBlockProperties.NATURAL_QUARTZ_CLUSTER().setCutoutRenderType()));

    public static final RegistryObject<Block> CTHONIC_GOLD_ORE = BLOCKS.register("cthonic_gold_ore", () -> new DropExperienceBlock(MalumBlockProperties.CTHONIC_GOLD_ORE(), UniformInt.of(10, 100)));
    public static final RegistryObject<Block> CTHONIC_GOLD_CLUSTER = BLOCKS.register("cthonic_gold_cluster", () -> new AmethystClusterBlock(4, 3, MalumBlockProperties.CTHONIC_GOLD_CLUSTER().setCutoutRenderType()));
    public static final RegistryObject<Block> BLOCK_OF_CTHONIC_GOLD = BLOCKS.register("block_of_cthonic_gold", () -> new Block(MalumBlockProperties.CTHONIC_GOLD_BLOCK()));

    public static final RegistryObject<Block> BRILLIANT_STONE = BLOCKS.register("brilliant_stone", () -> new DropExperienceBlock(MalumBlockProperties.BRILLIANCE_ORE(false).setCutoutRenderType(), UniformInt.of(14, 18)));
    public static final RegistryObject<Block> BRILLIANT_DEEPSLATE = BLOCKS.register("brilliant_deepslate", () -> new DropExperienceBlock(MalumBlockProperties.BRILLIANCE_ORE(true).setCutoutRenderType(), UniformInt.of(16, 26)));
    public static final RegistryObject<Block> BLOCK_OF_BRILLIANCE = BLOCKS.register("block_of_brilliance", () -> new Block(MalumBlockProperties.BRILLIANCE_BLOCK()));

    public static final RegistryObject<Block> SOULSTONE_ORE = BLOCKS.register("soulstone_ore", () -> new DropExperienceBlock(MalumBlockProperties.SOULSTONE_ORE(false)));
    public static final RegistryObject<Block> DEEPSLATE_SOULSTONE_ORE = BLOCKS.register("deepslate_soulstone_ore", () -> new DropExperienceBlock(MalumBlockProperties.SOULSTONE_ORE(true)));
    public static final RegistryObject<Block> BLOCK_OF_RAW_SOULSTONE = BLOCKS.register("block_of_raw_soulstone", () -> new Block(MalumBlockProperties.SOULSTONE_BLOCK()));
    public static final RegistryObject<Block> BLOCK_OF_SOULSTONE = BLOCKS.register("block_of_soulstone", () -> new SoulstoneBlock(MalumBlockProperties.SOULSTONE_BLOCK()));

    public static final RegistryObject<Block> BLOCK_OF_HALLOWED_GOLD = BLOCKS.register("block_of_hallowed_gold", () -> new Block(MalumBlockProperties.HALLOWED_GOLD()));
    public static final RegistryObject<Block> BLOCK_OF_SOUL_STAINED_STEEL = BLOCKS.register("block_of_soul_stained_steel", () -> new Block(MalumBlockProperties.SOUL_STAINED_STEEL_BLOCK()));

    public static final RegistryObject<Block> BLOCK_OF_ROTTING_ESSENCE = BLOCKS.register("block_of_rotting_essence", () -> new Block(new LodestoneBlockProperties().needsPickaxe().addTags(STORAGE_BLOCKS).strength(1F, 6.0F).sound(SoundType.CORAL_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_GRIM_TALC = BLOCKS.register("block_of_grim_talc", () -> new Block(LodestoneBlockProperties.copy(Blocks.BONE_BLOCK).needsPickaxe().addTags(STORAGE_BLOCKS)));
    public static final RegistryObject<Block> BLOCK_OF_ALCHEMICAL_CALX = BLOCKS.register("block_of_alchemical_calx", () -> new Block(LodestoneBlockProperties.copy(Blocks.CALCITE).needsPickaxe().addTags(STORAGE_BLOCKS)));
    public static final RegistryObject<Block> BLOCK_OF_ASTRAL_WEAVE = BLOCKS.register("block_of_astral_weave", () -> new Block(LodestoneBlockProperties.copy(Blocks.LIGHT_BLUE_WOOL).needsPickaxe().needsHoe().addTags(STORAGE_BLOCKS)));
    public static final RegistryObject<Block> BLOCK_OF_HEX_ASH = BLOCKS.register("block_of_hex_ash", () -> new Block(LodestoneBlockProperties.copy(Blocks.PURPLE_CONCRETE_POWDER).needsPickaxe().needsHoe().addTags(STORAGE_BLOCKS)));
    public static final RegistryObject<Block> MASS_OF_BLIGHTED_GUNK = BLOCKS.register("mass_of_blighted_gunk", () -> new Block(MalumBlockProperties.BLIGHT().needsPickaxe().addTags(STORAGE_BLOCKS)));
    public static final RegistryObject<Block> BLOCK_OF_CURSED_GRIT = BLOCKS.register("block_of_cursed_grit", () -> new Block(LodestoneBlockProperties.copy(Blocks.RED_CONCRETE_POWDER).needsPickaxe().needsShovel().addTags(STORAGE_BLOCKS)));
    public static final RegistryObject<Block> BLOCK_OF_NULL_SLATE = BLOCKS.register("block_of_null_slate", () -> new Block(MalumBlockProperties.SOULSTONE_BLOCK()));
    public static final RegistryObject<Block> BLOCK_OF_VOID_SALTS = BLOCKS.register("block_of_void_salts", () -> new Block(LodestoneBlockProperties.copy(Blocks.BLACK_CONCRETE_POWDER).needsPickaxe().needsShovel().addTags(STORAGE_BLOCKS)));
    public static final RegistryObject<Block> BLOCK_OF_MNEMONIC_FRAGMENT = BLOCKS.register("block_of_mnemonic_fragment", () -> new Block(MalumBlockProperties.BRILLIANCE_BLOCK()));
    public static final RegistryObject<Block> RUNIC_SAP_BLOCK = BLOCKS.register("runic_sap_block", () -> new Block(MalumBlockProperties.RUNIC_SAP()));
    public static final RegistryObject<Block> CURSED_SAP_BLOCK = BLOCKS.register("cursed_sap_block", () -> new Block(MalumBlockProperties.CURSED_SAP()));

    public static final RegistryObject<Block> THE_DEVICE = BLOCKS.register("the_device", () -> new TheDevice(MalumBlockProperties.TAINTED_ROCK()));
    public static final RegistryObject<Block> THE_VESSEL = BLOCKS.register("the_vessel", () -> new TheVessel(MalumBlockProperties.TWISTED_ROCK()));

    @Mod.EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        @SubscribeEvent
        public static void setBlockColors(RegisterColorHandlersEvent.Block event) {
            BlockColors blockColors = event.getBlockColors();
            blockColors.register((s, l, p, c) -> {
                BlockEntity blockEntity = l.getBlockEntity(p);
                if (blockEntity instanceof EtherBlockEntity etherBlockEntity) {
                    if (etherBlockEntity.firstColor != null) {
                        return c == 0 ? etherBlockEntity.firstColor.getRGB() : -1;
                    }
                }
                return -1;
            }, ETHER.get(), IRIDESCENT_ETHER.get());
            blockColors.register((s, l, p, c) -> {
                float colorMax = MalumLeavesBlock.COLOR.getPossibleValues().size();
                float color = s.getValue(MalumLeavesBlock.COLOR);
                float pct = (colorMax - (color / colorMax));
                float value = Easing.SINE_IN_OUT.ease(pct, 0, 1, 1);
                MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) s.getBlock();
                int red = (int) Mth.lerp(value, malumLeavesBlock.minColor.getRed(), malumLeavesBlock.maxColor.getRed());
                int green = (int) Mth.lerp(value, malumLeavesBlock.minColor.getGreen(), malumLeavesBlock.maxColor.getGreen());
                int blue = (int) Mth.lerp(value, malumLeavesBlock.minColor.getBlue(), malumLeavesBlock.maxColor.getBlue());
                return red << 16 | green << 8 | blue;
            }, RUNEWOOD_LEAVES.get(), HANGING_RUNEWOOD_LEAVES.get());
            blockColors.register((s, l, p, c) -> {
                float distanceMax = MalumLeavesBlock.DISTANCE.getPossibleValues().size();
                float distance = s.getValue(MalumLeavesBlock.DISTANCE);
                float colorMax = MalumLeavesBlock.COLOR.getPossibleValues().size();
                float color = s.getValue(MalumLeavesBlock.COLOR);
                float pct = Math.max((distanceMax - distance) / distanceMax, color / colorMax);
                float value = Easing.SINE_IN_OUT.ease(pct, 0, 1, 1);
                MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) s.getBlock();
                int red = (int) Mth.lerp(value, malumLeavesBlock.minColor.getRed(), malumLeavesBlock.maxColor.getRed());
                int green = (int) Mth.lerp(value, malumLeavesBlock.minColor.getGreen(), malumLeavesBlock.maxColor.getGreen());
                int blue = (int) Mth.lerp(value, malumLeavesBlock.minColor.getBlue(), malumLeavesBlock.maxColor.getBlue());
                return red << 16 | green << 8 | blue;
            }, SOULWOOD_LEAVES.get(), MYSTIC_SOULWOOD_LEAVES.get(), HANGING_MYSTIC_SOULWOOD_LEAVES.get());
        }
    }
}