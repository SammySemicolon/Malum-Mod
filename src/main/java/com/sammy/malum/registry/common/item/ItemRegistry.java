package com.sammy.malum.registry.common.item;

import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.obelisk.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.*;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.*;
import com.sammy.malum.common.block.nature.*;
import com.sammy.malum.common.item.*;
import com.sammy.malum.common.item.cosmetic.curios.*;
import com.sammy.malum.common.item.cosmetic.skins.*;
import com.sammy.malum.common.item.cosmetic.weaves.*;
import com.sammy.malum.common.item.curiosities.*;
import com.sammy.malum.common.item.curiosities.armor.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.curiosities.curios.alchemical.*;
import com.sammy.malum.common.item.curiosities.curios.rotten.*;
import com.sammy.malum.common.item.curiosities.curios.misc.*;
import com.sammy.malum.common.item.curiosities.curios.prospector.*;
import com.sammy.malum.common.item.curiosities.curios.soulward.*;
import com.sammy.malum.common.item.curiosities.curios.spirit.*;
import com.sammy.malum.common.item.curiosities.curios.weeping.*;
import com.sammy.malum.common.item.curiosities.nitrate.*;
import com.sammy.malum.common.item.curiosities.weapons.*;
import com.sammy.malum.common.item.curiosities.weapons.unique.*;
import com.sammy.malum.common.item.ether.*;
import com.sammy.malum.common.item.food.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.compability.farmersdelight.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.tabs.*;
import net.minecraft.client.color.item.*;
import net.minecraft.client.renderer.item.*;
import net.minecraft.nbt.*;
import net.minecraft.resources.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.food.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.api.distmarker.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.item.*;
import team.lodestar.lodestone.systems.item.tools.magic.*;
import team.lodestar.lodestone.systems.multiblock.*;

import java.util.*;
import java.util.stream.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemTiers.ItemTierEnum.*;
import static net.minecraft.world.item.Items.*;
import static net.minecraft.world.item.Rarity.UNCOMMON;

@SuppressWarnings("unused")
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MALUM);

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.CONTENT);
    }

    public static Item.Properties BUILDING_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.BUILDING);
    }

    public static Item.Properties NATURE_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.NATURE);
    }

    public static Item.Properties IMPETUS_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.METALLURGY).stacksTo(1);
    }

    public static Item.Properties NODE_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.METALLURGY);
    }

    public static Item.Properties GEAR_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.CONTENT).stacksTo(1);
    }

    public static Item.Properties COSMETIC_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.COSMETIC);
    }

    public static Item.Properties VOID_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.VOID);
    }

    public static Item.Properties VOID_GEAR_PROPERTIES() {
        return new Item.Properties().tab(CreativeTabRegistry.VOID).stacksTo(1);
    }


    public static Item.Properties HIDDEN_PROPERTIES() {
        return new Item.Properties().stacksTo(1);
    }

    public static final RegistryObject<Item> ENCYCLOPEDIA_ARCANA = ITEMS.register("encyclopedia_arcana", () -> new EncyclopediaArcanaItem(GEAR_PROPERTIES().rarity(UNCOMMON)));

    //region spirits
    public static final RegistryObject<SpiritShardItem> SACRED_SPIRIT = ITEMS.register("sacred_spirit", () -> new SpiritShardItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.SACRED_SPIRIT));
    public static final RegistryObject<SpiritShardItem> WICKED_SPIRIT = ITEMS.register("wicked_spirit", () -> new SpiritShardItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.WICKED_SPIRIT));
    public static final RegistryObject<SpiritShardItem> ARCANE_SPIRIT = ITEMS.register("arcane_spirit", () -> new SpiritShardItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.ARCANE_SPIRIT));
    public static final RegistryObject<SpiritShardItem> ELDRITCH_SPIRIT = ITEMS.register("eldritch_spirit", () -> new SpiritShardItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.ELDRITCH_SPIRIT));
    public static final RegistryObject<SpiritShardItem> AQUEOUS_SPIRIT = ITEMS.register("aqueous_spirit", () -> new SpiritShardItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.AQUEOUS_SPIRIT));
    public static final RegistryObject<SpiritShardItem> AERIAL_SPIRIT = ITEMS.register("aerial_spirit", () -> new SpiritShardItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final RegistryObject<SpiritShardItem> INFERNAL_SPIRIT = ITEMS.register("infernal_spirit", () -> new SpiritShardItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final RegistryObject<SpiritShardItem> EARTHEN_SPIRIT = ITEMS.register("earthen_spirit", () -> new SpiritShardItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.EARTHEN_SPIRIT));
    //endregion

    //region random stuff
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> COAL_FRAGMENT = ITEMS.register("coal_fragment", () -> new LodestoneFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final RegistryObject<Item> CHARCOAL_FRAGMENT = ITEMS.register("charcoal_fragment", () -> new LodestoneFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    //endregion

    //region tainted rock
    public static final RegistryObject<Item> TAINTED_ROCK = ITEMS.register("tainted_rock", () -> new BlockItem(BlockRegistry.TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK = ITEMS.register("smooth_tainted_rock", () -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK = ITEMS.register("polished_tainted_rock", () -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS = ITEMS.register("tainted_rock_bricks", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES = ITEMS.register("tainted_rock_tiles", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS = ITEMS.register("small_tainted_rock_bricks", () -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_BRICKS = ITEMS.register("runic_tainted_rock_bricks", () -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_TILES = ITEMS.register("runic_tainted_rock_tiles", () -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS = ITEMS.register("runic_small_tainted_rock_bricks", () -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN = ITEMS.register("tainted_rock_column", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN_CAP = ITEMS.register("tainted_rock_column_cap", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_TAINTED_ROCK = ITEMS.register("cut_tainted_rock", () -> new BlockItem(BlockRegistry.CUT_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK = ITEMS.register("chiseled_tainted_rock", () -> new BlockItem(BlockRegistry.CHISELED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = ITEMS.register("tainted_rock_pressure_plate", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BUTTON = ITEMS.register("tainted_rock_button", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BUTTON.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_WALL = ITEMS.register("tainted_rock_wall", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_WALL = ITEMS.register("tainted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_WALL = ITEMS.register("tainted_rock_tiles_wall", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("small_tainted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("runic_tainted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_TILES_WALL = ITEMS.register("runic_tainted_rock_tiles_wall", () -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("runic_small_tainted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_SLAB = ITEMS.register("tainted_rock_slab", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_SLAB = ITEMS.register("smooth_tainted_rock_slab", () -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_SLAB = ITEMS.register("polished_tainted_rock_slab", () -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("tainted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_SLAB = ITEMS.register("tainted_rock_tiles_slab", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("small_tainted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("runic_tainted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_TILES_SLAB = ITEMS.register("runic_tainted_rock_tiles_slab", () -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("runic_small_tainted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_STAIRS = ITEMS.register("tainted_rock_stairs", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_STAIRS = ITEMS.register("smooth_tainted_rock_stairs", () -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_STAIRS = ITEMS.register("polished_tainted_rock_stairs", () -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("tainted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_STAIRS = ITEMS.register("tainted_rock_tiles_stairs", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("small_tainted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("runic_tainted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_TILES_STAIRS = ITEMS.register("runic_tainted_rock_tiles_stairs", () -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("runic_small_tainted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_ITEM_STAND = ITEMS.register("tainted_rock_item_stand", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_ITEM_PEDESTAL = ITEMS.register("tainted_rock_item_pedestal", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));
    //endregion

    //region twisted rock
    public static final RegistryObject<Item> TWISTED_ROCK = ITEMS.register("twisted_rock", () -> new BlockItem(BlockRegistry.TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK = ITEMS.register("smooth_twisted_rock", () -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK = ITEMS.register("polished_twisted_rock", () -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS = ITEMS.register("twisted_rock_bricks", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES = ITEMS.register("twisted_rock_tiles", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS = ITEMS.register("small_twisted_rock_bricks", () -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_BRICKS = ITEMS.register("runic_twisted_rock_bricks", () -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_TILES = ITEMS.register("runic_twisted_rock_tiles", () -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS = ITEMS.register("runic_small_twisted_rock_bricks", () -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN = ITEMS.register("twisted_rock_column", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN_CAP = ITEMS.register("twisted_rock_column_cap", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_TWISTED_ROCK = ITEMS.register("cut_twisted_rock", () -> new BlockItem(BlockRegistry.CUT_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TWISTED_ROCK = ITEMS.register("chiseled_twisted_rock", () -> new BlockItem(BlockRegistry.CHISELED_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_PRESSURE_PLATE = ITEMS.register("twisted_rock_pressure_plate", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BUTTON = ITEMS.register("twisted_rock_button", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BUTTON.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_WALL = ITEMS.register("twisted_rock_wall", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_WALL = ITEMS.register("twisted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_WALL = ITEMS.register("twisted_rock_tiles_wall", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("small_twisted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("runic_twisted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_TILES_WALL = ITEMS.register("runic_twisted_rock_tiles_wall", () -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("runic_small_twisted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_SLAB = ITEMS.register("twisted_rock_slab", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_SLAB = ITEMS.register("smooth_twisted_rock_slab", () -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_SLAB = ITEMS.register("polished_twisted_rock_slab", () -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("twisted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_SLAB = ITEMS.register("twisted_rock_tiles_slab", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("small_twisted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("runic_twisted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_TILES_SLAB = ITEMS.register("runic_twisted_rock_tiles_slab", () -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("runic_small_twisted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_STAIRS = ITEMS.register("twisted_rock_stairs", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_STAIRS = ITEMS.register("smooth_twisted_rock_stairs", () -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_STAIRS = ITEMS.register("polished_twisted_rock_stairs", () -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("twisted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_STAIRS = ITEMS.register("twisted_rock_tiles_stairs", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("small_twisted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("runic_twisted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_TILES_STAIRS = ITEMS.register("runic_twisted_rock_tiles_stairs", () -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("runic_small_twisted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_STAND = ITEMS.register("twisted_rock_item_stand", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_PEDESTAL = ITEMS.register("twisted_rock_item_pedestal", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));
    //endregion twisted rock

    //region runewood
    public static final RegistryObject<Item> HOLY_SAP = ITEMS.register("holy_sap", () -> new Item(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE)));
    public static final RegistryObject<Item> HOLY_SAPBALL = ITEMS.register("holy_sapball", () -> new Item(NATURE_PROPERTIES()));
    public static final RegistryObject<Item> HOLY_SYRUP = ITEMS.register("holy_syrup", () -> new HolySyrupItem(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1).build())));
    public static final RegistryObject<Item> HOLY_CARAMEL = ITEMS.register("holy_caramel", () -> new HolyCaramelItem(FarmersDelightCompat.LOADED ? NATURE_PROPERTIES().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.15F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1).build()) : HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_LEAVES = ITEMS.register("runewood_leaves", () -> new BlockItem(BlockRegistry.RUNEWOOD_LEAVES.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_SAPLING = ITEMS.register("runewood_sapling", () -> new BlockItem(BlockRegistry.RUNEWOOD_SAPLING.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_LOG = ITEMS.register("runewood_log", () -> new BlockItem(BlockRegistry.RUNEWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD_LOG = ITEMS.register("stripped_runewood_log", () -> new BlockItem(BlockRegistry.STRIPPED_RUNEWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD = ITEMS.register("runewood", () -> new BlockItem(BlockRegistry.RUNEWOOD.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD = ITEMS.register("stripped_runewood", () -> new BlockItem(BlockRegistry.STRIPPED_RUNEWOOD.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> EXPOSED_RUNEWOOD_LOG = ITEMS.register("exposed_runewood_log", () -> new BlockItem(BlockRegistry.EXPOSED_RUNEWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> REVEALED_RUNEWOOD_LOG = ITEMS.register("revealed_runewood_log", () -> new BlockItem(BlockRegistry.REVEALED_RUNEWOOD_LOG.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS = ITEMS.register("runewood_planks", () -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS = ITEMS.register("vertical_runewood_planks", () -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL = ITEMS.register("runewood_panel", () -> new BlockItem(BlockRegistry.RUNEWOOD_PANEL.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES = ITEMS.register("runewood_tiles", () -> new BlockItem(BlockRegistry.RUNEWOOD_TILES.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_SLAB = ITEMS.register("runewood_planks_slab", () -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_SLAB = ITEMS.register("vertical_runewood_planks_slab", () -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL_SLAB = ITEMS.register("runewood_panel_slab", () -> new BlockItem(BlockRegistry.RUNEWOOD_PANEL_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES_SLAB = ITEMS.register("runewood_tiles_slab", () -> new BlockItem(BlockRegistry.RUNEWOOD_TILES_SLAB.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_STAIRS = ITEMS.register("runewood_planks_stairs", () -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_STAIRS = ITEMS.register("vertical_runewood_planks_stairs", () -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL_STAIRS = ITEMS.register("runewood_panel_stairs", () -> new BlockItem(BlockRegistry.RUNEWOOD_PANEL_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES_STAIRS = ITEMS.register("runewood_tiles_stairs", () -> new BlockItem(BlockRegistry.RUNEWOOD_TILES_STAIRS.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> CUT_RUNEWOOD_PLANKS = ITEMS.register("cut_runewood_planks", () -> new BlockItem(BlockRegistry.CUT_RUNEWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_BEAM = ITEMS.register("runewood_beam", () -> new BlockItem(BlockRegistry.RUNEWOOD_BEAM.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_DOOR = ITEMS.register("runewood_door", () -> new BlockItem(BlockRegistry.RUNEWOOD_DOOR.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TRAPDOOR = ITEMS.register("runewood_trapdoor", () -> new BlockItem(BlockRegistry.RUNEWOOD_TRAPDOOR.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOLID_RUNEWOOD_TRAPDOOR = ITEMS.register("solid_runewood_trapdoor", () -> new BlockItem(BlockRegistry.SOLID_RUNEWOOD_TRAPDOOR.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_BUTTON = ITEMS.register("runewood_planks_button", () -> new BlockItem(BlockRegistry.RUNEWOOD_BUTTON.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_PRESSURE_PLATE = ITEMS.register("runewood_planks_pressure_plate", () -> new BlockItem(BlockRegistry.RUNEWOOD_PRESSURE_PLATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE = ITEMS.register("runewood_planks_fence", () -> new BlockItem(BlockRegistry.RUNEWOOD_FENCE.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE_GATE = ITEMS.register("runewood_planks_fence_gate", () -> new BlockItem(BlockRegistry.RUNEWOOD_FENCE_GATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_ITEM_STAND = ITEMS.register("runewood_item_stand", () -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_STAND.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_ITEM_PEDESTAL = ITEMS.register("runewood_item_pedestal", () -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_PEDESTAL.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_SIGN = ITEMS.register("runewood_sign", () -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.RUNEWOOD_SIGN.get(), BlockRegistry.RUNEWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> RUNEWOOD_BOAT = ITEMS.register("runewood_boat", () -> new LodestoneBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.RUNEWOOD_BOAT));
    //endregion

    //region blight
    public static final RegistryObject<Item> BLIGHTED_EARTH = ITEMS.register("blighted_earth", () -> new BlockItem(BlockRegistry.BLIGHTED_EARTH.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_SOIL = ITEMS.register("blighted_soil", () -> new BlockItem(BlockRegistry.BLIGHTED_SOIL.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_WEED = ITEMS.register("blighted_weed", () -> new BlockItem(BlockRegistry.BLIGHTED_WEED.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_TUMOR = ITEMS.register("blighted_tumor", () -> new BlockItem(BlockRegistry.BLIGHTED_TUMOR.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_SOULWOOD = ITEMS.register("blighted_soulwood", () -> new BlockItem(BlockRegistry.BLIGHTED_SOULWOOD.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_GUNK = ITEMS.register("blighted_gunk", () -> new Item(NATURE_PROPERTIES()));
    //endregion

    //region soulwood
    public static final RegistryObject<Item> UNHOLY_SAP = ITEMS.register("unholy_sap", () -> new Item(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE)));
    public static final RegistryObject<Item> UNHOLY_SAPBALL = ITEMS.register("unholy_sapball", () -> new Item(NATURE_PROPERTIES()));
    public static final RegistryObject<Item> UNHOLY_SYRUP = ITEMS.register("unholy_syrup", () -> new UnholySyrupItem(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0), 1).build())));
    public static final RegistryObject<Item> UNHOLY_CARAMEL = ITEMS.register("unholy_caramel", () -> new HolyCaramelItem(FarmersDelightCompat.LOADED ? NATURE_PROPERTIES().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.15F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0), 1).build()) : HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_LEAVES = ITEMS.register("soulwood_leaves", () -> new BlockItem(BlockRegistry.SOULWOOD_LEAVES.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_GROWTH = ITEMS.register("soulwood_growth", () -> new BlockItem(BlockRegistry.SOULWOOD_GROWTH.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_LOG = ITEMS.register("soulwood_log", () -> new BlockItem(BlockRegistry.SOULWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_SOULWOOD_LOG = ITEMS.register("stripped_soulwood_log", () -> new BlockItem(BlockRegistry.STRIPPED_SOULWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD = ITEMS.register("soulwood", () -> new BlockItem(BlockRegistry.SOULWOOD.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_SOULWOOD = ITEMS.register("stripped_soulwood", () -> new BlockItem(BlockRegistry.STRIPPED_SOULWOOD.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> EXPOSED_SOULWOOD_LOG = ITEMS.register("exposed_soulwood_log", () -> new BlockItem(BlockRegistry.EXPOSED_SOULWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> REVEALED_SOULWOOD_LOG = ITEMS.register("revealed_soulwood_log", () -> new BlockItem(BlockRegistry.REVEALED_SOULWOOD_LOG.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_PLANKS = ITEMS.register("soulwood_planks", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS = ITEMS.register("vertical_soulwood_planks", () -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PANEL = ITEMS.register("soulwood_panel", () -> new BlockItem(BlockRegistry.SOULWOOD_PANEL.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_TILES = ITEMS.register("soulwood_tiles", () -> new BlockItem(BlockRegistry.SOULWOOD_TILES.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_SLAB = ITEMS.register("soulwood_planks_slab", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS_SLAB = ITEMS.register("vertical_soulwood_planks_slab", () -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PANEL_SLAB = ITEMS.register("soulwood_panel_slab", () -> new BlockItem(BlockRegistry.SOULWOOD_PANEL_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_TILES_SLAB = ITEMS.register("soulwood_tiles_slab", () -> new BlockItem(BlockRegistry.SOULWOOD_TILES_SLAB.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_STAIRS = ITEMS.register("soulwood_planks_stairs", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS_STAIRS = ITEMS.register("vertical_soulwood_planks_stairs", () -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PANEL_STAIRS = ITEMS.register("soulwood_panel_stairs", () -> new BlockItem(BlockRegistry.SOULWOOD_PANEL_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_TILES_STAIRS = ITEMS.register("soulwood_tiles_stairs", () -> new BlockItem(BlockRegistry.SOULWOOD_TILES_STAIRS.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> CUT_SOULWOOD_PLANKS = ITEMS.register("cut_soulwood_planks", () -> new BlockItem(BlockRegistry.CUT_SOULWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_BEAM = ITEMS.register("soulwood_beam", () -> new BlockItem(BlockRegistry.SOULWOOD_BEAM.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_DOOR = ITEMS.register("soulwood_door", () -> new BlockItem(BlockRegistry.SOULWOOD_DOOR.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_TRAPDOOR = ITEMS.register("soulwood_trapdoor", () -> new BlockItem(BlockRegistry.SOULWOOD_TRAPDOOR.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOLID_SOULWOOD_TRAPDOOR = ITEMS.register("solid_soulwood_trapdoor", () -> new BlockItem(BlockRegistry.SOLID_SOULWOOD_TRAPDOOR.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_BUTTON = ITEMS.register("soulwood_planks_button", () -> new BlockItem(BlockRegistry.SOULWOOD_BUTTON.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_PRESSURE_PLATE = ITEMS.register("soulwood_planks_pressure_plate", () -> new BlockItem(BlockRegistry.SOULWOOD_PRESSURE_PLATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_FENCE = ITEMS.register("soulwood_planks_fence", () -> new BlockItem(BlockRegistry.SOULWOOD_FENCE.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_FENCE_GATE = ITEMS.register("soulwood_planks_fence_gate", () -> new BlockItem(BlockRegistry.SOULWOOD_FENCE_GATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_ITEM_STAND = ITEMS.register("soulwood_item_stand", () -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_STAND.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_ITEM_PEDESTAL = ITEMS.register("soulwood_item_pedestal", () -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_PEDESTAL.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_SIGN = ITEMS.register("soulwood_sign", () -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.SOULWOOD_SIGN.get(), BlockRegistry.SOULWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> SOULWOOD_BOAT = ITEMS.register("soulwood_boat", () -> new LodestoneBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.SOULWOOD_BOAT));
    //endregion

    //region ores
    public static final RegistryObject<Item> ARCANE_CHARCOAL = ITEMS.register("arcane_charcoal", () -> new LodestoneFuelItem(DEFAULT_PROPERTIES(), 3200));
    public static final RegistryObject<Item> ARCANE_CHARCOAL_FRAGMENT = ITEMS.register("arcane_charcoal_fragment", () -> new LodestoneFuelItem(DEFAULT_PROPERTIES(), 400));
    public static final RegistryObject<Item> BLOCK_OF_ARCANE_CHARCOAL = ITEMS.register("block_of_arcane_charcoal", () -> new LodestoneFuelBlockItem(BlockRegistry.BLOCK_OF_ARCANE_CHARCOAL.get(), DEFAULT_PROPERTIES(), 32000));

    public static final RegistryObject<Item> BLAZING_QUARTZ_ORE = ITEMS.register("blazing_quartz_ore", () -> new BlockItem(BlockRegistry.BLAZING_QUARTZ_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLAZING_QUARTZ = ITEMS.register("blazing_quartz", () -> new LodestoneFuelItem(DEFAULT_PROPERTIES(), 1600));
    public static final RegistryObject<Item> BLAZING_QUARTZ_FRAGMENT = ITEMS.register("blazing_quartz_fragment", () -> new LodestoneFuelItem(DEFAULT_PROPERTIES(), 200));
    public static final RegistryObject<Item> BLOCK_OF_BLAZING_QUARTZ = ITEMS.register("block_of_blazing_quartz", () -> new LodestoneFuelBlockItem(BlockRegistry.BLOCK_OF_BLAZING_QUARTZ.get(), DEFAULT_PROPERTIES(), 16000));

    public static final RegistryObject<Item> NATURAL_QUARTZ_ORE = ITEMS.register("natural_quartz_ore", () -> new BlockItem(BlockRegistry.NATURAL_QUARTZ_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DEEPSLATE_QUARTZ_ORE = ITEMS.register("deepslate_quartz_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> NATURAL_QUARTZ = ITEMS.register("natural_quartz", () -> new ItemNameBlockItem(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> BRILLIANT_STONE = ITEMS.register("brilliant_stone", () -> new BlockItem(BlockRegistry.BRILLIANT_STONE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BRILLIANT_DEEPSLATE = ITEMS.register("brilliant_deepslate", () -> new BlockItem(BlockRegistry.BRILLIANT_DEEPSLATE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CLUSTER_OF_BRILLIANCE = ITEMS.register("cluster_of_brilliance", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRUSHED_BRILLIANCE = ITEMS.register("crushed_brilliance", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CHUNK_OF_BRILLIANCE = ITEMS.register("chunk_of_brilliance", () -> new BrillianceChunkItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).food((new FoodProperties.Builder()).fast().alwaysEat().build())));
    public static final RegistryObject<Item> BLOCK_OF_BRILLIANCE = ITEMS.register("block_of_brilliance", () -> new BlockItem(BlockRegistry.BLOCK_OF_BRILLIANCE.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> SOULSTONE_ORE = ITEMS.register("soulstone_ore", () -> new BlockItem(BlockRegistry.SOULSTONE_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DEEPSLATE_SOULSTONE_ORE = ITEMS.register("deepslate_soulstone_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> RAW_SOULSTONE = ITEMS.register("raw_soulstone", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRUSHED_SOULSTONE = ITEMS.register("crushed_soulstone", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_RAW_SOULSTONE = ITEMS.register("block_of_raw_soulstone", () -> new BlockItem(BlockRegistry.BLOCK_OF_RAW_SOULSTONE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> PROCESSED_SOULSTONE = ITEMS.register("processed_soulstone", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_SOULSTONE = ITEMS.register("block_of_soulstone", () -> new BlockItem(BlockRegistry.BLOCK_OF_SOULSTONE.get(), DEFAULT_PROPERTIES()));
    //endregion

    //region crafting blocks
    public static final RegistryObject<Item> SPIRIT_ALTAR = ITEMS.register("spirit_altar", () -> new BlockItem(BlockRegistry.SPIRIT_ALTAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> WEAVERS_WORKBENCH = ITEMS.register("weavers_workbench", () -> new BlockItem(BlockRegistry.WEAVERS_WORKBENCH.get(), COSMETIC_PROPERTIES()));
    public static final RegistryObject<Item> SPIRIT_JAR = ITEMS.register("spirit_jar", () -> new SpiritJarItem(BlockRegistry.SPIRIT_JAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_VIAL = ITEMS.register("soul_vial", () -> new SoulVialItem(BlockRegistry.SOUL_VIAL.get(), HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_OBELISK = ITEMS.register("runewood_obelisk", () -> new MultiBlockItem(BlockRegistry.RUNEWOOD_OBELISK.get(), DEFAULT_PROPERTIES(), RunewoodObeliskBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> BRILLIANT_OBELISK = ITEMS.register("brilliant_obelisk", () -> new MultiBlockItem(BlockRegistry.BRILLIANT_OBELISK.get(), DEFAULT_PROPERTIES(), BrilliantObeliskBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> SPIRIT_CRUCIBLE = ITEMS.register("spirit_crucible", () -> new MultiBlockItem(BlockRegistry.SPIRIT_CRUCIBLE.get(), DEFAULT_PROPERTIES(), SpiritCrucibleCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> TWISTED_TABLET = ITEMS.register("twisted_tablet", () -> new BlockItem(BlockRegistry.TWISTED_TABLET.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPIRIT_CATALYZER = ITEMS.register("spirit_catalyzer", () -> new MultiBlockItem(BlockRegistry.SPIRIT_CATALYZER.get(), DEFAULT_PROPERTIES(), SpiritCatalyzerCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> RUNEWOOD_TOTEM_BASE = ITEMS.register("runewood_totem_base", () -> new BlockItem(BlockRegistry.RUNEWOOD_TOTEM_BASE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_TOTEM_BASE = ITEMS.register("soulwood_totem_base", () -> new BlockItem(BlockRegistry.SOULWOOD_TOTEM_BASE.get(), DEFAULT_PROPERTIES()));
    //endregion

    //region materials
    public static final RegistryObject<Item> ROTTING_ESSENCE = ITEMS.register("rotting_essence", () -> new Item(DEFAULT_PROPERTIES().food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.2F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 1), 0.95f).build())));
    public static final RegistryObject<Item> GRIM_TALC = ITEMS.register("grim_talc", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ASTRAL_WEAVE = ITEMS.register("astral_weave", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CTHONIC_GOLD = ITEMS.register("cthonic_gold", () -> new Item(DEFAULT_PROPERTIES().rarity(UNCOMMON)));
    public static final RegistryObject<Item> HEX_ASH = ITEMS.register("hex_ash", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ALCHEMICAL_CALX = ITEMS.register("alchemical_calx", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CURSED_GRIT = ITEMS.register("cursed_grit", () -> new Item(DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> BLOCK_OF_ROTTING_ESSENCE = ITEMS.register("block_of_rotting_essence", () -> new BlockItem(BlockRegistry.BLOCK_OF_ROTTING_ESSENCE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_GRIM_TALC = ITEMS.register("block_of_grim_talc", () -> new BlockItem(BlockRegistry.BLOCK_OF_GRIM_TALC.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_ASTRAL_WEAVE = ITEMS.register("block_of_astral_weave", () -> new BlockItem(BlockRegistry.BLOCK_OF_ASTRAL_WEAVE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_CTHONIC_GOLD = ITEMS.register("block_of_cthonic_gold", () -> new ItemNameBlockItem(BlockRegistry.BLOCK_OF_CTHONIC_GOLD.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> BLOCK_OF_HEX_ASH = ITEMS.register("block_of_hex_ash", () -> new BlockItem(BlockRegistry.BLOCK_OF_HEX_ASH.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_ALCHEMICAL_CALX = ITEMS.register("block_of_alchemical_calx", () -> new BlockItem(BlockRegistry.BLOCK_OF_ALCHEMICAL_CALX.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MASS_OF_BLIGHTED_GUNK = ITEMS.register("mass_of_blighted_gunk", () -> new BlockItem(BlockRegistry.MASS_OF_BLIGHTED_GUNK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_CURSED_GRIT = ITEMS.register("block_of_cursed_grit", () -> new BlockItem(BlockRegistry.BLOCK_OF_CURSED_GRIT.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_VOID_SALTS = ITEMS.register("block_of_void_salts", () -> new BlockItem(BlockRegistry.BLOCK_OF_VOID_SALTS.get(), HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> SPIRIT_FABRIC = ITEMS.register("spirit_fabric", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPECTRAL_LENS = ITEMS.register("spectral_lens", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPECTRAL_OPTIC = ITEMS.register("spectral_optic", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ATTUNED_OPTIC_AERIAL = ITEMS.register("aerial_tuned_optic", () -> new TunedOpticItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final RegistryObject<Item> ATTUNED_OPTIC_AQUEOUS = ITEMS.register("aqueous_tuned_optic", () -> new TunedOpticItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.AQUEOUS_SPIRIT));
    public static final RegistryObject<Item> ATTUNED_OPTIC_INFERNAL = ITEMS.register("infernal_tuned_optic", () -> new TunedOpticItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final RegistryObject<Item> ATTUNED_OPTIC_EARTHEN = ITEMS.register("earthen_tuned_optic", () -> new TunedOpticItem(DEFAULT_PROPERTIES(), SpiritTypeRegistry.EARTHEN_SPIRIT));
    public static final RegistryObject<Item> POPPET = ITEMS.register("poppet", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CORRUPTED_RESONANCE = ITEMS.register("corrupted_resonance", () -> new Item(DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> HALLOWED_GOLD_INGOT = ITEMS.register("hallowed_gold_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_GOLD_NUGGET = ITEMS.register("hallowed_gold_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_HALLOWED_GOLD = ITEMS.register("block_of_hallowed_gold", () -> new BlockItem(BlockRegistry.BLOCK_OF_HALLOWED_GOLD.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_INGOT = ITEMS.register("soul_stained_steel_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_NUGGET = ITEMS.register("soul_stained_steel_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_SOUL_STAINED_STEEL = ITEMS.register("block_of_soul_stained_steel", () -> new BlockItem(BlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<CrackedImpetusItem> CRACKED_IRON_IMPETUS = ITEMS.register("cracked_iron_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> IRON_IMPETUS = ITEMS.register("iron_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_IRON_IMPETUS));
    public static final RegistryObject<Item> IRON_NODE = ITEMS.register("iron_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_COPPER_IMPETUS = ITEMS.register("cracked_copper_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> COPPER_IMPETUS = ITEMS.register("copper_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_COPPER_IMPETUS));
    public static final RegistryObject<Item> COPPER_NODE = ITEMS.register("copper_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_GOLD_IMPETUS = ITEMS.register("cracked_gold_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> GOLD_IMPETUS = ITEMS.register("gold_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_GOLD_IMPETUS));
    public static final RegistryObject<Item> GOLD_NODE = ITEMS.register("gold_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_LEAD_IMPETUS = ITEMS.register("cracked_lead_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> LEAD_IMPETUS = ITEMS.register("lead_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_LEAD_IMPETUS));
    public static final RegistryObject<Item> LEAD_NODE = ITEMS.register("lead_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_SILVER_IMPETUS = ITEMS.register("cracked_silver_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> SILVER_IMPETUS = ITEMS.register("silver_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_SILVER_IMPETUS));
    public static final RegistryObject<Item> SILVER_NODE = ITEMS.register("silver_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_ALUMINUM_IMPETUS = ITEMS.register("cracked_aluminum_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> ALUMINUM_IMPETUS = ITEMS.register("aluminum_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_ALUMINUM_IMPETUS));
    public static final RegistryObject<Item> ALUMINUM_NODE = ITEMS.register("aluminum_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_NICKEL_IMPETUS = ITEMS.register("cracked_nickel_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> NICKEL_IMPETUS = ITEMS.register("nickel_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_NICKEL_IMPETUS));
    public static final RegistryObject<Item> NICKEL_NODE = ITEMS.register("nickel_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_URANIUM_IMPETUS = ITEMS.register("cracked_uranium_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> URANIUM_IMPETUS = ITEMS.register("uranium_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_URANIUM_IMPETUS));
    public static final RegistryObject<Item> URANIUM_NODE = ITEMS.register("uranium_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_OSMIUM_IMPETUS = ITEMS.register("cracked_osmium_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> OSMIUM_IMPETUS = ITEMS.register("osmium_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_OSMIUM_IMPETUS));
    public static final RegistryObject<Item> OSMIUM_NODE = ITEMS.register("osmium_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_ZINC_IMPETUS = ITEMS.register("cracked_zinc_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> ZINC_IMPETUS = ITEMS.register("zinc_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_ZINC_IMPETUS));
    public static final RegistryObject<Item> ZINC_NODE = ITEMS.register("zinc_node", () -> new NodeItem(NODE_PROPERTIES()));
    public static final RegistryObject<CrackedImpetusItem> CRACKED_TIN_IMPETUS = ITEMS.register("cracked_tin_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> TIN_IMPETUS = ITEMS.register("tin_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_TIN_IMPETUS));
    public static final RegistryObject<Item> TIN_NODE = ITEMS.register("tin_node", () -> new NodeItem(NODE_PROPERTIES()));

    public static final RegistryObject<CrackedImpetusItem> CRACKED_ALCHEMICAL_IMPETUS = ITEMS.register("cracked_alchemical_impetus", () -> new CrackedImpetusItem(IMPETUS_PROPERTIES()));
    public static final RegistryObject<ImpetusItem> ALCHEMICAL_IMPETUS = ITEMS.register("alchemical_impetus", () -> new ImpetusItem(IMPETUS_PROPERTIES().durability(100)).setCrackedVariant(CRACKED_ALCHEMICAL_IMPETUS));
    //endregion

    //region ether
    public static final RegistryObject<Item> ETHER = ITEMS.register("ether", () -> new EtherItem(BlockRegistry.ETHER.get(), DEFAULT_PROPERTIES(), false));
    public static final RegistryObject<Item> ETHER_TORCH = ITEMS.register("ether_torch", () -> new EtherTorchItem(BlockRegistry.ETHER_TORCH.get(), BlockRegistry.WALL_ETHER_TORCH.get(), DEFAULT_PROPERTIES(), false));
    public static final RegistryObject<Item> TAINTED_ETHER_BRAZIER = ITEMS.register("tainted_ether_brazier", () -> new EtherBrazierItem(BlockRegistry.TAINTED_ETHER_BRAZIER.get(), DEFAULT_PROPERTIES(), false));
    public static final RegistryObject<Item> TWISTED_ETHER_BRAZIER = ITEMS.register("twisted_ether_brazier", () -> new EtherBrazierItem(BlockRegistry.TWISTED_ETHER_BRAZIER.get(), DEFAULT_PROPERTIES(), false));

    public static final RegistryObject<Item> IRIDESCENT_ETHER = ITEMS.register("iridescent_ether", () -> new EtherItem(BlockRegistry.IRIDESCENT_ETHER.get(), DEFAULT_PROPERTIES(), true));
    public static final RegistryObject<Item> IRIDESCENT_ETHER_TORCH = ITEMS.register("iridescent_ether_torch", () -> new EtherTorchItem(BlockRegistry.IRIDESCENT_ETHER_TORCH.get(), BlockRegistry.IRIDESCENT_WALL_ETHER_TORCH.get(), DEFAULT_PROPERTIES(), true));
    public static final RegistryObject<Item> TAINTED_IRIDESCENT_ETHER_BRAZIER = ITEMS.register("tainted_iridescent_ether_brazier", () -> new EtherBrazierItem(BlockRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), DEFAULT_PROPERTIES(), true));
    public static final RegistryObject<Item> TWISTED_IRIDESCENT_ETHER_BRAZIER = ITEMS.register("twisted_iridescent_ether_brazier", () -> new EtherBrazierItem(BlockRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), DEFAULT_PROPERTIES(), true));
    //endregion

    //region contents
    public static final RegistryObject<Item> SPIRIT_POUCH = ITEMS.register("spirit_pouch", () -> new SpiritPouchItem(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> CRUDE_SCYTHE = ITEMS.register("crude_scythe", () -> new MalumScytheItem(Tiers.IRON, 0, 0.1f, GEAR_PROPERTIES().durability(350)));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SCYTHE = ITEMS.register("soul_stained_steel_scythe", () -> new MagicScytheItem(SOUL_STAINED_STEEL, -2.5f, 0.1f, 4, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_STAVE = ITEMS.register("soulwood_stave", () -> new SoulStaveItem(HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SWORD = ITEMS.register("soul_stained_steel_sword", () -> new MagicSwordItem(SOUL_STAINED_STEEL, -3, 0, 3, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_PICKAXE = ITEMS.register("soul_stained_steel_pickaxe", () -> new MagicPickaxeItem(SOUL_STAINED_STEEL, -2, 0, 2, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_AXE = ITEMS.register("soul_stained_steel_axe", () -> new MagicAxeItem(SOUL_STAINED_STEEL, -3f, 0, 4, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SHOVEL = ITEMS.register("soul_stained_steel_shovel", () -> new MagicShovelItem(SOUL_STAINED_STEEL, -2, 0, 2, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_HOE = ITEMS.register("soul_stained_steel_hoe", () -> new MagicHoeItem(SOUL_STAINED_STEEL, 0, -1.5f, 1, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_KNIFE = ITEMS.register("soul_stained_steel_knife", () -> FarmersDelightCompat.LOADED ? FarmersDelightCompat.LoadedOnly.makeMagicKnife() : new Item(HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_HELMET = ITEMS.register("soul_stained_steel_helmet", () -> new SoulStainedSteelArmorItem(EquipmentSlot.HEAD, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_CHESTPLATE = ITEMS.register("soul_stained_steel_chestplate", () -> new SoulStainedSteelArmorItem(EquipmentSlot.CHEST, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_LEGGINGS = ITEMS.register("soul_stained_steel_leggings", () -> new SoulStainedSteelArmorItem(EquipmentSlot.LEGS, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_BOOTS = ITEMS.register("soul_stained_steel_boots", () -> new SoulStainedSteelArmorItem(EquipmentSlot.FEET, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_HUNTER_CLOAK = ITEMS.register("soul_hunter_cloak", () -> new SoulHunterArmorItem(EquipmentSlot.HEAD, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_HUNTER_ROBE = ITEMS.register("soul_hunter_robe", () -> new SoulHunterArmorItem(EquipmentSlot.CHEST, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_HUNTER_LEGGINGS = ITEMS.register("soul_hunter_leggings", () -> new SoulHunterArmorItem(EquipmentSlot.LEGS, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_HUNTER_BOOTS = ITEMS.register("soul_hunter_boots", () -> new SoulHunterArmorItem(EquipmentSlot.FEET, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> TYRVING = ITEMS.register("tyrving", () -> new TyrvingItem(ItemTiers.ItemTierEnum.TYRVING, 0, -0.3f, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> MEPHITIC_EDGE = ITEMS.register("mephitic_edge", () -> new MephiticEdgeScytheItem(VOID, -8f, 0.1f, 6, HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> ETHERIC_NITRATE = ITEMS.register("etheric_nitrate", () -> new EthericNitrateItem(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> VIVID_NITRATE = ITEMS.register("vivid_nitrate", () -> new VividNitrateItem(DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> GILDED_RING = ITEMS.register("gilded_ring", () -> new CurioGildedRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> GILDED_BELT = ITEMS.register("gilded_belt", () -> new CurioGildedBelt(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ORNATE_RING = ITEMS.register("ornate_ring", () -> new CurioOrnateRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ORNATE_NECKLACE = ITEMS.register("ornate_necklace", () -> new CurioOrnateNecklace(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> RING_OF_ESOTERIC_SPOILS = ITEMS.register("ring_of_esoteric_spoils", () -> new CurioArcaneSpoilRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_CURATIVE_TALENT = ITEMS.register("ring_of_curative_talent", () -> new CurioCurativeRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_ARCANE_PROWESS = ITEMS.register("ring_of_arcane_prowess", () -> new CurioRingOfProwess(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_ALCHEMICAL_MASTERY = ITEMS.register("ring_of_alchemical_mastery", () -> new CurioAlchemicalRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_DESPERATE_VORACITY = ITEMS.register("ring_of_desperate_voracity", () -> new CurioVoraciousRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_THE_HOARDER = ITEMS.register("ring_of_the_hoarder", () -> new CurioHoarderRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_THE_DEMOLITIONIST = ITEMS.register("ring_of_the_demolitionist", () -> new CurioDemolitionistRing(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> NECKLACE_OF_THE_MYSTIC_MIRROR = ITEMS.register("necklace_of_the_mystic_mirror", () -> new CurioMirrorNecklace(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> NECKLACE_OF_TIDAL_AFFINITY = ITEMS.register("necklace_of_tidal_affinity", () -> new CurioWaterNecklace(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> NECKLACE_OF_THE_NARROW_EDGE = ITEMS.register("necklace_of_the_narrow_edge", () -> new CurioNarrowNecklace(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> NECKLACE_OF_BLISSFUL_HARMONY = ITEMS.register("necklace_of_blissful_harmony", () -> new CurioHarmonyNecklace(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> BELT_OF_THE_STARVED = ITEMS.register("belt_of_the_starved", () -> new CurioStarvedBelt(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> BELT_OF_THE_PROSPECTOR = ITEMS.register("belt_of_the_prospector", () -> new CurioProspectorBelt(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> BELT_OF_THE_MAGEBANE = ITEMS.register("belt_of_the_magebane", () -> new CurioMagebaneBelt(GEAR_PROPERTIES()));
    //endregion

    //region chronicles of the void

    public static final RegistryObject<Item> VOID_SALTS = ITEMS.register("void_salts", () -> new Item(VOID_PROPERTIES()));
    public static final RegistryObject<Item> NULL_SLATE = ITEMS.register("null_slate", () -> new Item(VOID_PROPERTIES()));
    public static final RegistryObject<Item> STRANGE_NUCLEUS = ITEMS.register("strange_nucleus", () -> new Item(VOID_PROPERTIES()));
    public static final RegistryObject<Item> CRYSTALLIZED_NIHILITY = ITEMS.register("crystallized_nihility", () -> new Item(VOID_PROPERTIES()));
    public static final RegistryObject<Item> ANOMALOUS_DESIGN = ITEMS.register("anomalous_design", () -> new Item(VOID_PROPERTIES()));
    public static final RegistryObject<Item> COMPLETE_DESIGN = ITEMS.register("complete_design", () -> new SimpleFoiledItem(VOID_PROPERTIES()));
    public static final RegistryObject<Item> FUSED_CONSCIOUSNESS = ITEMS.register("fused_consciousness", () -> new FusedConsciousnessItem(VOID_PROPERTIES().rarity(UNCOMMON)));

    public static final RegistryObject<Item> RING_OF_GROWING_FLESH = ITEMS.register("ring_of_growing_flesh", () -> new CurioGrowingFleshRing(VOID_GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_GRUESOME_SATIATION = ITEMS.register("ring_of_gruesome_satiation", () -> new CurioGruesomeSatiationRing(VOID_GEAR_PROPERTIES()));

    public static final RegistryObject<Item> NECKLACE_OF_THE_HIDDEN_BLADE = ITEMS.register("necklace_of_the_hidden_blade", () -> new CurioHiddenBladeNecklace(VOID_GEAR_PROPERTIES()));
    public static final RegistryObject<Item> NECKLACE_OF_THE_WATCHER = ITEMS.register("necklace_of_the_watcher", () -> new CurioWatcherNecklace(VOID_GEAR_PROPERTIES()));


    //endregion

    //region cosmetics
    public static final RegistryObject<Item> ESOTERIC_SPOOL = ITEMS.register("esoteric_spool", () -> new Item(COSMETIC_PROPERTIES()));
    public static final RegistryObject<Item> ANCIENT_WEAVE = ITEMS.register("ancient_weave", () -> new GenericWeaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<Item> CORNERED_WEAVE = ITEMS.register("cornered_weave", () -> new GenericWeaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<Item> DREADED_WEAVE = ITEMS.register("dreaded_weave", () -> new GenericWeaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<Item> MECHANICAL_WEAVE_V1 = ITEMS.register("mechanical_weave_v1", () -> new GenericWeaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<Item> MECHANICAL_WEAVE_V2 = ITEMS.register("mechanical_weave_v2", () -> new GenericWeaveItem(COSMETIC_PROPERTIES()));

    public static final RegistryObject<PrideweaveItem> ACE_PRIDEWEAVE = ITEMS.register("ace_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> AGENDER_PRIDEWEAVE = ITEMS.register("agender_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> ARO_PRIDEWEAVE = ITEMS.register("aro_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> AROACE_PRIDEWEAVE = ITEMS.register("aroace_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> BI_PRIDEWEAVE = ITEMS.register("bi_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> DEMIBOY_PRIDEWEAVE = ITEMS.register("demiboy_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> DEMIGIRL_PRIDEWEAVE = ITEMS.register("demigirl_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> ENBY_PRIDEWEAVE = ITEMS.register("enby_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> GAY_PRIDEWEAVE = ITEMS.register("gay_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> GENDERFLUID_PRIDEWEAVE = ITEMS.register("genderfluid_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> GENDERQUEER_PRIDEWEAVE = ITEMS.register("genderqueer_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> INTERSEX_PRIDEWEAVE = ITEMS.register("intersex_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> LESBIAN_PRIDEWEAVE = ITEMS.register("lesbian_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> PAN_PRIDEWEAVE = ITEMS.register("pan_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> PLURAL_PRIDEWEAVE = ITEMS.register("plural_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> POLY_PRIDEWEAVE = ITEMS.register("poly_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> PRIDE_PRIDEWEAVE = ITEMS.register("pride_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));
    public static final RegistryObject<PrideweaveItem> TRANS_PRIDEWEAVE = ITEMS.register("trans_prideweave", () -> new PrideweaveItem(COSMETIC_PROPERTIES()));

    public static final RegistryObject<Item> TOPHAT = ITEMS.register("tophat", () -> new CurioTopHat(COSMETIC_PROPERTIES().stacksTo(1)));

    //endregion

    //region hidden items
    public static final RegistryObject<Item> THE_DEVICE = ITEMS.register("the_device", () -> new BlockItem(BlockRegistry.THE_DEVICE.get(), HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> THE_VESSEL = ITEMS.register("the_vessel", () -> new BlockItem(BlockRegistry.THE_VESSEL.get(), HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> CREATIVE_SCYTHE = ITEMS.register("creative_scythe", () -> new MagicScytheItem(Tiers.IRON, 9993, 9.1f, 999f, HIDDEN_PROPERTIES().durability(-1)));
    public static final RegistryObject<Item> TOKEN_OF_GRATITUDE = ITEMS.register("token_of_gratitude", () -> new CurioTokenOfGratitude(HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> PRIMORDIAL_SOUP = ITEMS.register("primordial_soup", () -> new BlockItem(BlockRegistry.PRIMORDIAL_SOUP.get(), HIDDEN_PROPERTIES()));
    //endregion

    @Mod.EventBusSubscriber(modid = MalumMod.MALUM, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Common {
        @SubscribeEvent
        public static void registerCompost(FMLCommonSetupEvent event) {
            registerCompostable(RUNEWOOD_LEAVES, 0.3f);
            registerCompostable(SOULWOOD_LEAVES, 0.3f);
            registerCompostable(RUNEWOOD_SAPLING, 0.3f);
            registerCompostable(SOULWOOD_GROWTH, 0.3f);
        }

        public static void registerCompostable(RegistryObject<Item> item, float chance) {
            ComposterBlock.COMPOSTABLES.put(item.get(), chance);
        }
    }


    @Mod.EventBusSubscriber(modid = MalumMod.MALUM, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void addItemProperties(FMLClientSetupEvent event) {
            Set<LodestoneArmorItem> armors = ItemRegistry.ITEMS.getEntries().stream().filter(r -> r.get() instanceof LodestoneArmorItem).map(r -> (LodestoneArmorItem) r.get()).collect(Collectors.toSet());
            ItemPropertyFunction itemPropertyFunction = (stack, level, holder, holderID) -> {
                if (!stack.hasTag()) {
                    return -1;
                }
                CompoundTag nbt = stack.getTag();
                if (!nbt.contains(ArmorSkin.MALUM_SKIN_TAG)) {
                    return -1;
                }
                ArmorSkin armorSkin = ArmorSkinRegistry.SKINS.get(nbt.getString(ArmorSkin.MALUM_SKIN_TAG));
                if (armorSkin == null) {
                    return -1;
                }
                return armorSkin.index;
            };
            for (LodestoneArmorItem armor : armors) {
                ItemProperties.register(armor, new ResourceLocation(ArmorSkin.MALUM_SKIN_TAG), itemPropertyFunction);
            }
        }

        @SubscribeEvent
        public static void setItemColors(ColorHandlerEvent.Item event) {
            ItemColors itemColors = event.getItemColors();
            Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());

            DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof MalumLeavesBlock).forEach(item -> {
                MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) ((BlockItem) item.get()).getBlock();
                itemColors.register((s, c) -> ColorHelper.getColor(malumLeavesBlock.minColor), item.get());
            });
            DataHelper.takeAll(items, i -> i.get() instanceof EtherTorchItem || i.get() instanceof EtherBrazierItem).forEach(i -> itemColors.register((s, c) -> {
                AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
                switch (c) {
                    case 2 -> {
                        return etherItem.getSecondColor(s);
                    }
                    case 1 -> {
                        return etherItem.getFirstColor(s);
                    }
                    default -> {
                        return -1;
                    }
                }
            }, i.get()));
            DataHelper.takeAll(items, i -> i.get() instanceof EtherItem).forEach(i -> itemColors.register((s, c) -> {
                AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
                return c == 0 ? etherItem.getFirstColor(s) : etherItem.getSecondColor(s);
            }, i.get()));

            DataHelper.takeAll(items, i -> i.get() instanceof SpiritShardItem).forEach(item ->
                    itemColors.register((s, c) -> ColorHelper.getColor(((SpiritShardItem) item.get()).type.getItemColor()), item.get()));
            DataHelper.takeAll(items, i -> i.get() instanceof TunedOpticItem).forEach(item ->
                    itemColors.register((s, c) -> c == 1 ? ColorHelper.getColor(((TunedOpticItem) item.get()).type.getItemColor()) : -1, item.get()));
        }
    }
}
