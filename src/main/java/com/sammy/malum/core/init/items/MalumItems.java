package com.sammy.malum.core.init.items;

import com.sammy.malum.common.items.ConfinedBrillianceItem;
import com.sammy.malum.common.items.EncyclopediaArcanaItem;
import com.sammy.malum.common.items.FuelItem;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.common.items.equipment.armor.SoulStainedSteelArmorItem;
import com.sammy.malum.common.items.equipment.armor.SoulStainedStrongholdArmorItem;
import com.sammy.malum.common.items.equipment.curios.*;
import com.sammy.malum.common.items.food.SolarSyrupBottleItem;
import com.sammy.malum.common.items.tools.*;
import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.items.tabs.MalumBuildingTab;
import com.sammy.malum.core.init.items.tabs.MalumCreativeTab;
import com.sammy.malum.core.init.items.tabs.MalumNatureTab;
import com.sammy.malum.core.init.items.tabs.MalumSplinterTab;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;
import static com.sammy.malum.core.init.items.MalumItemTiers.ItemTierEnum.SOUL_STAINED_STEEL_ITEM;
import static com.sammy.malum.core.init.items.MalumItemTiers.ItemTierEnum.TYRVING_ITEM;
import static net.minecraft.item.Items.GLASS_BOTTLE;

@SuppressWarnings("unused")
public class MalumItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);


    public static Item.Properties DEFAULT_PROPERTIES()
    {
        return new Item.Properties().group(MalumCreativeTab.INSTANCE);
    }

    public static Item.Properties SPLINTER_PROPERTIES()
    {
        return new Item.Properties().group(MalumSplinterTab.INSTANCE);
    }

    public static Item.Properties BUILDING_PROPERTIES()
    {
        return new Item.Properties().group(MalumBuildingTab.INSTANCE);
    }

    public static Item.Properties NATURE_PROPERTIES()
    {
        return new Item.Properties().group(MalumNatureTab.INSTANCE);
    }

    public static Item.Properties GEAR_PROPERTIES()
    {
        return new Item.Properties().group(MalumCreativeTab.INSTANCE).maxStackSize(1);
    }

    public static Item.Properties CREATIVE_PROPERTIES()
    {
        return new Item.Properties().maxStackSize(1);
    }

    public static final RegistryObject<Item> ENCYCLOPEDIA_ARCANA = ITEMS.register("encyclopedia_arcana", () -> new EncyclopediaArcanaItem(GEAR_PROPERTIES()));

    //region tainted rock
    public static final RegistryObject<Item> TAINTED_ROCK = ITEMS.register("tainted_rock", () -> new BlockItem(MalumBlocks.TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK = ITEMS.register("smooth_tainted_rock", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK = ITEMS.register("polished_tainted_rock", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS = ITEMS.register("tainted_rock_bricks", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS = ITEMS.register("cracked_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES = ITEMS.register("tainted_rock_tiles", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES = ITEMS.register("cracked_tainted_rock_tiles", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS = ITEMS.register("small_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.SMALL_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TAINTED_ROCK_BRICKS = ITEMS.register("cracked_small_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_PILLAR = ITEMS.register("tainted_rock_pillar", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_PILLAR_CAP = ITEMS.register("tainted_rock_pillar_cap", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN = ITEMS.register("tainted_rock_column", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN_CAP = ITEMS.register("tainted_rock_column_cap", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_TAINTED_ROCK = ITEMS.register("cut_tainted_rock", () -> new BlockItem(MalumBlocks.CUT_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK = ITEMS.register("chiseled_tainted_rock", () -> new BlockItem(MalumBlocks.CHISELED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = ITEMS.register("tainted_rock_pressure_plate", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_WALL = ITEMS.register("tainted_rock_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_WALL = ITEMS.register("tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_WALL = ITEMS.register("tainted_rock_tiles_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_WALL = ITEMS.register("cracked_tainted_rock_tiles_wall", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("small_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.SMALL_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_small_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_SLAB = ITEMS.register("tainted_rock_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_SLAB = ITEMS.register("smooth_tainted_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_SLAB = ITEMS.register("polished_tainted_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_SLAB = ITEMS.register("tainted_rock_tiles_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_SLAB = ITEMS.register("cracked_tainted_rock_tiles_slab", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("small_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_small_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_STAIRS = ITEMS.register("tainted_rock_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_STAIRS = ITEMS.register("smooth_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_STAIRS = ITEMS.register("polished_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_STAIRS = ITEMS.register("tainted_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_STAIRS = ITEMS.register("cracked_tainted_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("small_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_small_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_ITEM_STAND = ITEMS.register("tainted_rock_item_stand", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_ITEM_PEDESTAL = ITEMS.register("tainted_rock_item_pedestal", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));

    //endregion

    //region twisted rock
    public static final RegistryObject<Item> TWISTED_ROCK = ITEMS.register("twisted_rock", () -> new BlockItem(MalumBlocks.TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK = ITEMS.register("smooth_twisted_rock", () -> new BlockItem(MalumBlocks.SMOOTH_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK = ITEMS.register("polished_twisted_rock", () -> new BlockItem(MalumBlocks.POLISHED_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS = ITEMS.register("twisted_rock_bricks", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_BRICKS = ITEMS.register("cracked_twisted_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES = ITEMS.register("twisted_rock_tiles", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_TILES = ITEMS.register("cracked_twisted_rock_tiles", () -> new BlockItem(MalumBlocks.CRACKED_TWISTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS = ITEMS.register("small_twisted_rock_bricks", () -> new BlockItem(MalumBlocks.SMALL_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TWISTED_ROCK_BRICKS = ITEMS.register("cracked_small_twisted_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_PILLAR = ITEMS.register("twisted_rock_pillar", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_PILLAR_CAP = ITEMS.register("twisted_rock_pillar_cap", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN = ITEMS.register("twisted_rock_column", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN_CAP = ITEMS.register("twisted_rock_column_cap", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_TWISTED_ROCK = ITEMS.register("cut_twisted_rock", () -> new BlockItem(MalumBlocks.CUT_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TWISTED_ROCK = ITEMS.register("chiseled_twisted_rock", () -> new BlockItem(MalumBlocks.CHISELED_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_PRESSURE_PLATE = ITEMS.register("twisted_rock_pressure_plate", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_WALL = ITEMS.register("twisted_rock_wall", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_WALL = ITEMS.register("twisted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_twisted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_WALL = ITEMS.register("twisted_rock_tiles_wall", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("small_twisted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.SMALL_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_small_twisted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_TILES_WALL = ITEMS.register("cracked_twisted_rock_tiles_wall", () -> new BlockItem(MalumBlocks.CRACKED_TWISTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_SLAB = ITEMS.register("twisted_rock_slab", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_SLAB = ITEMS.register("smooth_twisted_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_SLAB = ITEMS.register("polished_twisted_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("twisted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_twisted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_SLAB = ITEMS.register("twisted_rock_tiles_slab", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_TILES_SLAB = ITEMS.register("cracked_twisted_rock_tiles_slab", () -> new BlockItem(MalumBlocks.CRACKED_TWISTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("small_twisted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_small_twisted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_STAIRS = ITEMS.register("twisted_rock_stairs", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_STAIRS = ITEMS.register("smooth_twisted_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_STAIRS = ITEMS.register("polished_twisted_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("twisted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_twisted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_STAIRS = ITEMS.register("twisted_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_TILES_STAIRS = ITEMS.register("cracked_twisted_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.CRACKED_TWISTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("small_twisted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_small_twisted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_STAND = ITEMS.register("twisted_rock_item_stand", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_PEDESTAL = ITEMS.register("twisted_rock_item_pedestal", () -> new BlockItem(MalumBlocks.TWISTED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));

    //endregion twisted rock

    //region purified rock
    public static final RegistryObject<Item> PURIFIED_ROCK = ITEMS.register("purified_rock", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_PURIFIED_ROCK = ITEMS.register("smooth_purified_rock", () -> new BlockItem(MalumBlocks.SMOOTH_PURIFIED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_PURIFIED_ROCK = ITEMS.register("polished_purified_rock", () -> new BlockItem(MalumBlocks.POLISHED_PURIFIED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_BRICKS = ITEMS.register("purified_rock_bricks", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_PURIFIED_ROCK_BRICKS = ITEMS.register("cracked_purified_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_PURIFIED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_TILES = ITEMS.register("purified_rock_tiles", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_PURIFIED_ROCK_TILES = ITEMS.register("cracked_purified_rock_tiles", () -> new BlockItem(MalumBlocks.CRACKED_PURIFIED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_PURIFIED_ROCK_BRICKS = ITEMS.register("small_purified_rock_bricks", () -> new BlockItem(MalumBlocks.SMALL_PURIFIED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_PURIFIED_ROCK_BRICKS = ITEMS.register("cracked_small_purified_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_PURIFIED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> PURIFIED_ROCK_PILLAR = ITEMS.register("purified_rock_pillar", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_PILLAR_CAP = ITEMS.register("purified_rock_pillar_cap", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_COLUMN = ITEMS.register("purified_rock_column", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_COLUMN_CAP = ITEMS.register("purified_rock_column_cap", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_PURIFIED_ROCK = ITEMS.register("cut_purified_rock", () -> new BlockItem(MalumBlocks.CUT_PURIFIED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_PURIFIED_ROCK = ITEMS.register("chiseled_purified_rock", () -> new BlockItem(MalumBlocks.CHISELED_PURIFIED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_PRESSURE_PLATE = ITEMS.register("purified_rock_pressure_plate", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> PURIFIED_ROCK_WALL = ITEMS.register("purified_rock_wall", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_BRICKS_WALL = ITEMS.register("purified_rock_bricks_wall", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_PURIFIED_ROCK_BRICKS_WALL = ITEMS.register("cracked_purified_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_PURIFIED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_TILES_WALL = ITEMS.register("purified_rock_tiles_wall", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_PURIFIED_ROCK_BRICKS_WALL = ITEMS.register("small_purified_rock_bricks_wall", () -> new BlockItem(MalumBlocks.SMALL_PURIFIED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_PURIFIED_ROCK_BRICKS_WALL = ITEMS.register("cracked_small_purified_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_PURIFIED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_PURIFIED_ROCK_TILES_WALL = ITEMS.register("cracked_purified_rock_tiles_wall", () -> new BlockItem(MalumBlocks.CRACKED_PURIFIED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> PURIFIED_ROCK_SLAB = ITEMS.register("purified_rock_slab", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_PURIFIED_ROCK_SLAB = ITEMS.register("smooth_purified_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_PURIFIED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_PURIFIED_ROCK_SLAB = ITEMS.register("polished_purified_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_PURIFIED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_BRICKS_SLAB = ITEMS.register("purified_rock_bricks_slab", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_PURIFIED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_purified_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_PURIFIED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_TILES_SLAB = ITEMS.register("purified_rock_tiles_slab", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_PURIFIED_ROCK_TILES_SLAB = ITEMS.register("cracked_purified_rock_tiles_slab", () -> new BlockItem(MalumBlocks.CRACKED_PURIFIED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_PURIFIED_ROCK_BRICKS_SLAB = ITEMS.register("small_purified_rock_bricks_slab", () -> new BlockItem(MalumBlocks.SMALL_PURIFIED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_PURIFIED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_small_purified_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_PURIFIED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> PURIFIED_ROCK_STAIRS = ITEMS.register("purified_rock_stairs", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_PURIFIED_ROCK_STAIRS = ITEMS.register("smooth_purified_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_PURIFIED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_PURIFIED_ROCK_STAIRS = ITEMS.register("polished_purified_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_PURIFIED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_BRICKS_STAIRS = ITEMS.register("purified_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_PURIFIED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_purified_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_PURIFIED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_TILES_STAIRS = ITEMS.register("purified_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_PURIFIED_ROCK_TILES_STAIRS = ITEMS.register("cracked_purified_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.CRACKED_PURIFIED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_PURIFIED_ROCK_BRICKS_STAIRS = ITEMS.register("small_purified_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.SMALL_PURIFIED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_PURIFIED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_small_purified_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_PURIFIED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> PURIFIED_ROCK_ITEM_STAND = ITEMS.register("purified_rock_item_stand", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURIFIED_ROCK_ITEM_PEDESTAL = ITEMS.register("purified_rock_item_pedestal", () -> new BlockItem(MalumBlocks.PURIFIED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));

    //endregion

    //region cleansed rock
    public static final RegistryObject<Item> CLEANSED_ROCK = ITEMS.register("cleansed_rock", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_CLEANSED_ROCK = ITEMS.register("smooth_cleansed_rock", () -> new BlockItem(MalumBlocks.SMOOTH_CLEANSED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CLEANSED_ROCK = ITEMS.register("polished_cleansed_rock", () -> new BlockItem(MalumBlocks.POLISHED_CLEANSED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_BRICKS = ITEMS.register("cleansed_rock_bricks", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CLEANSED_ROCK_BRICKS = ITEMS.register("cracked_cleansed_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_CLEANSED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_TILES = ITEMS.register("cleansed_rock_tiles", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CLEANSED_ROCK_TILES = ITEMS.register("cracked_cleansed_rock_tiles", () -> new BlockItem(MalumBlocks.CRACKED_CLEANSED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_CLEANSED_ROCK_BRICKS = ITEMS.register("small_cleansed_rock_bricks", () -> new BlockItem(MalumBlocks.SMALL_CLEANSED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_CLEANSED_ROCK_BRICKS = ITEMS.register("cracked_small_cleansed_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_CLEANSED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CLEANSED_ROCK_PILLAR = ITEMS.register("cleansed_rock_pillar", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_PILLAR_CAP = ITEMS.register("cleansed_rock_pillar_cap", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_COLUMN = ITEMS.register("cleansed_rock_column", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_COLUMN_CAP = ITEMS.register("cleansed_rock_column_cap", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_CLEANSED_ROCK = ITEMS.register("cut_cleansed_rock", () -> new BlockItem(MalumBlocks.CUT_CLEANSED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_CLEANSED_ROCK = ITEMS.register("chiseled_cleansed_rock", () -> new BlockItem(MalumBlocks.CHISELED_CLEANSED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_PRESSURE_PLATE = ITEMS.register("cleansed_rock_pressure_plate", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CLEANSED_ROCK_WALL = ITEMS.register("cleansed_rock_wall", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_BRICKS_WALL = ITEMS.register("cleansed_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CLEANSED_ROCK_BRICKS_WALL = ITEMS.register("cracked_cleansed_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_CLEANSED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_TILES_WALL = ITEMS.register("cleansed_rock_tiles_wall", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_CLEANSED_ROCK_BRICKS_WALL = ITEMS.register("small_cleansed_rock_bricks_wall", () -> new BlockItem(MalumBlocks.SMALL_CLEANSED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_CLEANSED_ROCK_BRICKS_WALL = ITEMS.register("cracked_small_cleansed_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_CLEANSED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CLEANSED_ROCK_TILES_WALL = ITEMS.register("cracked_cleansed_rock_tiles_wall", () -> new BlockItem(MalumBlocks.CRACKED_CLEANSED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CLEANSED_ROCK_SLAB = ITEMS.register("cleansed_rock_slab", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_CLEANSED_ROCK_SLAB = ITEMS.register("smooth_cleansed_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_CLEANSED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CLEANSED_ROCK_SLAB = ITEMS.register("polished_cleansed_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_CLEANSED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_BRICKS_SLAB = ITEMS.register("cleansed_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CLEANSED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_cleansed_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_CLEANSED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_TILES_SLAB = ITEMS.register("cleansed_rock_tiles_slab", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CLEANSED_ROCK_TILES_SLAB = ITEMS.register("cracked_cleansed_rock_tiles_slab", () -> new BlockItem(MalumBlocks.CRACKED_CLEANSED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_CLEANSED_ROCK_BRICKS_SLAB = ITEMS.register("small_cleansed_rock_bricks_slab", () -> new BlockItem(MalumBlocks.SMALL_CLEANSED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_CLEANSED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_small_cleansed_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_CLEANSED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CLEANSED_ROCK_STAIRS = ITEMS.register("cleansed_rock_stairs", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_CLEANSED_ROCK_STAIRS = ITEMS.register("smooth_cleansed_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_CLEANSED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CLEANSED_ROCK_STAIRS = ITEMS.register("polished_cleansed_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_CLEANSED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_BRICKS_STAIRS = ITEMS.register("cleansed_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CLEANSED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_cleansed_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_CLEANSED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_TILES_STAIRS = ITEMS.register("cleansed_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CLEANSED_ROCK_TILES_STAIRS = ITEMS.register("cracked_cleansed_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.CRACKED_CLEANSED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_CLEANSED_ROCK_BRICKS_STAIRS = ITEMS.register("small_cleansed_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.SMALL_CLEANSED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_CLEANSED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_small_cleansed_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_CLEANSED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CLEANSED_ROCK_ITEM_STAND = ITEMS.register("cleansed_rock_item_stand", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEANSED_ROCK_ITEM_PEDESTAL = ITEMS.register("cleansed_rock_item_pedestal", () -> new BlockItem(MalumBlocks.CLEANSED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));

    //endregion

    //region eroded rock
    public static final RegistryObject<Item> ERODED_ROCK = ITEMS.register("eroded_rock", () -> new BlockItem(MalumBlocks.ERODED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_ERODED_ROCK = ITEMS.register("smooth_eroded_rock", () -> new BlockItem(MalumBlocks.SMOOTH_ERODED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_ERODED_ROCK = ITEMS.register("polished_eroded_rock", () -> new BlockItem(MalumBlocks.POLISHED_ERODED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_BRICKS = ITEMS.register("eroded_rock_bricks", () -> new BlockItem(MalumBlocks.ERODED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_ERODED_ROCK_BRICKS = ITEMS.register("cracked_eroded_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_ERODED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_TILES = ITEMS.register("eroded_rock_tiles", () -> new BlockItem(MalumBlocks.ERODED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_ERODED_ROCK_TILES = ITEMS.register("cracked_eroded_rock_tiles", () -> new BlockItem(MalumBlocks.CRACKED_ERODED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_ERODED_ROCK_BRICKS = ITEMS.register("small_eroded_rock_bricks", () -> new BlockItem(MalumBlocks.SMALL_ERODED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_ERODED_ROCK_BRICKS = ITEMS.register("cracked_small_eroded_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_ERODED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> ERODED_ROCK_PILLAR = ITEMS.register("eroded_rock_pillar", () -> new BlockItem(MalumBlocks.ERODED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_PILLAR_CAP = ITEMS.register("eroded_rock_pillar_cap", () -> new BlockItem(MalumBlocks.ERODED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_COLUMN = ITEMS.register("eroded_rock_column", () -> new BlockItem(MalumBlocks.ERODED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_COLUMN_CAP = ITEMS.register("eroded_rock_column_cap", () -> new BlockItem(MalumBlocks.ERODED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_ERODED_ROCK = ITEMS.register("cut_eroded_rock", () -> new BlockItem(MalumBlocks.CUT_ERODED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_ERODED_ROCK = ITEMS.register("chiseled_eroded_rock", () -> new BlockItem(MalumBlocks.CHISELED_ERODED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_PRESSURE_PLATE = ITEMS.register("eroded_rock_pressure_plate", () -> new BlockItem(MalumBlocks.ERODED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> ERODED_ROCK_WALL = ITEMS.register("eroded_rock_wall", () -> new BlockItem(MalumBlocks.ERODED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_BRICKS_WALL = ITEMS.register("eroded_rock_bricks_wall", () -> new BlockItem(MalumBlocks.ERODED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_ERODED_ROCK_BRICKS_WALL = ITEMS.register("cracked_eroded_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_ERODED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_TILES_WALL = ITEMS.register("eroded_rock_tiles_wall", () -> new BlockItem(MalumBlocks.ERODED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_ERODED_ROCK_BRICKS_WALL = ITEMS.register("small_eroded_rock_bricks_wall", () -> new BlockItem(MalumBlocks.SMALL_ERODED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_ERODED_ROCK_BRICKS_WALL = ITEMS.register("cracked_small_eroded_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_ERODED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_ERODED_ROCK_TILES_WALL = ITEMS.register("cracked_eroded_rock_tiles_wall", () -> new BlockItem(MalumBlocks.CRACKED_ERODED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> ERODED_ROCK_SLAB = ITEMS.register("eroded_rock_slab", () -> new BlockItem(MalumBlocks.ERODED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_ERODED_ROCK_SLAB = ITEMS.register("smooth_eroded_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_ERODED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_ERODED_ROCK_SLAB = ITEMS.register("polished_eroded_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_ERODED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_BRICKS_SLAB = ITEMS.register("eroded_rock_bricks_slab", () -> new BlockItem(MalumBlocks.ERODED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_ERODED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_eroded_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_ERODED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_TILES_SLAB = ITEMS.register("eroded_rock_tiles_slab", () -> new BlockItem(MalumBlocks.ERODED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_ERODED_ROCK_TILES_SLAB = ITEMS.register("cracked_eroded_rock_tiles_slab", () -> new BlockItem(MalumBlocks.CRACKED_ERODED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_ERODED_ROCK_BRICKS_SLAB = ITEMS.register("small_eroded_rock_bricks_slab", () -> new BlockItem(MalumBlocks.SMALL_ERODED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_ERODED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_small_eroded_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_ERODED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> ERODED_ROCK_STAIRS = ITEMS.register("eroded_rock_stairs", () -> new BlockItem(MalumBlocks.ERODED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_ERODED_ROCK_STAIRS = ITEMS.register("smooth_eroded_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_ERODED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_ERODED_ROCK_STAIRS = ITEMS.register("polished_eroded_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_ERODED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_BRICKS_STAIRS = ITEMS.register("eroded_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.ERODED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_ERODED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_eroded_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_ERODED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_TILES_STAIRS = ITEMS.register("eroded_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.ERODED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_ERODED_ROCK_TILES_STAIRS = ITEMS.register("cracked_eroded_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.CRACKED_ERODED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_ERODED_ROCK_BRICKS_STAIRS = ITEMS.register("small_eroded_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.SMALL_ERODED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_ERODED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_small_eroded_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_SMALL_ERODED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> ERODED_ROCK_ITEM_STAND = ITEMS.register("eroded_rock_item_stand", () -> new BlockItem(MalumBlocks.ERODED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> ERODED_ROCK_ITEM_PEDESTAL = ITEMS.register("eroded_rock_item_pedestal", () -> new BlockItem(MalumBlocks.ERODED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));

    //endregion

    //region nature
    public static final RegistryObject<Item> SOLAR_SAP_BOTTLE = ITEMS.register("solar_sap_bottle", () -> new Item(NATURE_PROPERTIES().containerItem(GLASS_BOTTLE)));
    public static final RegistryObject<Item> SOLAR_SAPBALL = ITEMS.register("solar_sapball", () -> new Item(NATURE_PROPERTIES().containerItem(GLASS_BOTTLE)));
    public static final RegistryObject<Item> SOLAR_SYRUP_BOTTLE = ITEMS.register("solar_syrup_bottle", () -> new SolarSyrupBottleItem(NATURE_PROPERTIES().containerItem(GLASS_BOTTLE).food((new Food.Builder()).hunger(8).saturation(2F).build())));

    public static final RegistryObject<Item> SUN_KISSED_GRASS = ITEMS.register("sun_kissed_grass", () -> new BlockItem(MalumBlocks.SUN_KISSED_GRASS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> TALL_SUN_KISSED_GRASS = ITEMS.register("tall_sun_kissed_grass", () -> new BlockItem(MalumBlocks.TALL_SUN_KISSED_GRASS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> LAVENDER = ITEMS.register("lavender", () -> new BlockItem(MalumBlocks.LAVENDER.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_GRASS_BLOCK = ITEMS.register("sun_kissed_grass_block", () -> new BlockItem(MalumBlocks.SUN_KISSED_GRASS_BLOCK.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_LEAVES = ITEMS.register("sun_kissed_leaves", () -> new BlockItem(MalumBlocks.SUN_KISSED_LEAVES.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_SAPLING = ITEMS.register("runewood_sapling", () -> new BlockItem(MalumBlocks.RUNEWOOD_SAPLING.get(), NATURE_PROPERTIES()));
    //endregion

    //region runewood
    public static final RegistryObject<Item> SAP_FILLED_RUNEWOOD_LOG = ITEMS.register("sap_filled_runewood_log", () -> new BlockItem(MalumBlocks.SAP_FILLED_RUNEWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_LOG = ITEMS.register("runewood_log", () -> new BlockItem(MalumBlocks.RUNEWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD_LOG = ITEMS.register("stripped_runewood_log", () -> new BlockItem(MalumBlocks.STRIPPED_RUNEWOOD_LOG.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD = ITEMS.register("runewood", () -> new BlockItem(MalumBlocks.RUNEWOOD.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD = ITEMS.register("stripped_runewood", () -> new BlockItem(MalumBlocks.STRIPPED_RUNEWOOD.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS = ITEMS.register("runewood_planks", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS = ITEMS.register("vertical_runewood_planks", () -> new BlockItem(MalumBlocks.VERTICAL_RUNEWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BOLTED_RUNEWOOD_PLANKS = ITEMS.register("bolted_runewood_planks", () -> new BlockItem(MalumBlocks.BOLTED_RUNEWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL = ITEMS.register("runewood_panel", () -> new BlockItem(MalumBlocks.RUNEWOOD_PANEL.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES = ITEMS.register("runewood_tiles", () -> new BlockItem(MalumBlocks.RUNEWOOD_TILES.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_SLAB = ITEMS.register("runewood_planks_slab", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_SLAB = ITEMS.register("vertical_runewood_planks_slab", () -> new BlockItem(MalumBlocks.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BOLTED_RUNEWOOD_PLANKS_SLAB = ITEMS.register("bolted_runewood_planks_slab", () -> new BlockItem(MalumBlocks.BOLTED_RUNEWOOD_PLANKS_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL_SLAB = ITEMS.register("runewood_panel_slab", () -> new BlockItem(MalumBlocks.RUNEWOOD_PANEL_SLAB.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES_SLAB = ITEMS.register("runewood_tiles_slab", () -> new BlockItem(MalumBlocks.RUNEWOOD_TILES_SLAB.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_STAIRS = ITEMS.register("runewood_planks_stairs", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_STAIRS = ITEMS.register("vertical_runewood_planks_stairs", () -> new BlockItem(MalumBlocks.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BOLTED_RUNEWOOD_PLANKS_STAIRS = ITEMS.register("bolted_runewood_planks_stairs", () -> new BlockItem(MalumBlocks.BOLTED_RUNEWOOD_PLANKS_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL_STAIRS = ITEMS.register("runewood_panel_stairs", () -> new BlockItem(MalumBlocks.RUNEWOOD_PANEL_STAIRS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES_STAIRS = ITEMS.register("runewood_tiles_stairs", () -> new BlockItem(MalumBlocks.RUNEWOOD_TILES_STAIRS.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> CUT_RUNEWOOD_PLANKS = ITEMS.register("cut_runewood_planks", () -> new BlockItem(MalumBlocks.CUT_RUNEWOOD_PLANKS.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_BEAM = ITEMS.register("runewood_beam", () -> new BlockItem(MalumBlocks.RUNEWOOD_BEAM.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BOLTED_RUNEWOOD_BEAM = ITEMS.register("bolted_runewood_beam", () -> new BlockItem(MalumBlocks.BOLTED_RUNEWOOD_BEAM.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_DOOR = ITEMS.register("runewood_door", () -> new BlockItem(MalumBlocks.RUNEWOOD_DOOR.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TRAPDOOR = ITEMS.register("runewood_trapdoor", () -> new BlockItem(MalumBlocks.RUNEWOOD_TRAPDOOR.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOLID_RUNEWOOD_TRAPDOOR = ITEMS.register("solid_runewood_trapdoor", () -> new BlockItem(MalumBlocks.SOLID_RUNEWOOD_TRAPDOOR.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_BUTTON = ITEMS.register("runewood_planks_button", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_BUTTON.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_PRESSURE_PLATE = ITEMS.register("runewood_planks_pressure_plate", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_PRESSURE_PLATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE = ITEMS.register("runewood_planks_fence", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_FENCE.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE_GATE = ITEMS.register("runewood_planks_fence_gate", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_FENCE_GATE.get(), NATURE_PROPERTIES()));


    public static final RegistryObject<Item> RUNEWOOD_ITEM_STAND = ITEMS.register("runewood_item_stand", () -> new BlockItem(MalumBlocks.RUNEWOOD_ITEM_STAND.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_ITEM_PEDESTAL = ITEMS.register("runewood_item_pedestal", () -> new BlockItem(MalumBlocks.RUNEWOOD_ITEM_PEDESTAL.get(), NATURE_PROPERTIES()));

    //endregion

    //region ether
    public static final RegistryObject<Item> ORANGE_ETHER = ITEMS.register("orange_ether", () -> new BlockItem(MalumBlocks.ORANGE_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MAGENTA_ETHER = ITEMS.register("magenta_ether", () -> new BlockItem(MalumBlocks.MAGENTA_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> LIGHT_BLUE_ETHER = ITEMS.register("light_blue_ether", () -> new BlockItem(MalumBlocks.LIGHT_BLUE_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> YELLOW_ETHER = ITEMS.register("yellow_ether", () -> new BlockItem(MalumBlocks.YELLOW_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> LIME_ETHER = ITEMS.register("lime_ether", () -> new BlockItem(MalumBlocks.LIME_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PINK_ETHER = ITEMS.register("pink_ether", () -> new BlockItem(MalumBlocks.PINK_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CYAN_ETHER = ITEMS.register("cyan_ether", () -> new BlockItem(MalumBlocks.CYAN_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURPLE_ETHER = ITEMS.register("purple_ether", () -> new BlockItem(MalumBlocks.PURPLE_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BLUE_ETHER = ITEMS.register("blue_ether", () -> new BlockItem(MalumBlocks.BLUE_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BROWN_ETHER = ITEMS.register("brown_ether", () -> new BlockItem(MalumBlocks.BROWN_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> GREEN_ETHER = ITEMS.register("green_ether", () -> new BlockItem(MalumBlocks.GREEN_ETHER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RED_ETHER = ITEMS.register("red_ether", () -> new BlockItem(MalumBlocks.RED_ETHER.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> ORANGE_ETHER_TORCH = ITEMS.register("orange_ether_torch", () -> new WallOrFloorItem(MalumBlocks.ORANGE_ETHER_TORCH.get(), MalumBlocks.ORANGE_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MAGENTA_ETHER_TORCH = ITEMS.register("magenta_ether_torch", () -> new WallOrFloorItem(MalumBlocks.MAGENTA_ETHER_TORCH.get(), MalumBlocks.MAGENTA_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> LIGHT_BLUE_ETHER_TORCH = ITEMS.register("light_blue_ether_torch", () -> new WallOrFloorItem(MalumBlocks.LIGHT_BLUE_ETHER_TORCH.get(), MalumBlocks.LIGHT_BLUE_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> YELLOW_ETHER_TORCH = ITEMS.register("yellow_ether_torch", () -> new WallOrFloorItem(MalumBlocks.YELLOW_ETHER_TORCH.get(), MalumBlocks.YELLOW_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> LIME_ETHER_TORCH = ITEMS.register("lime_ether_torch", () -> new WallOrFloorItem(MalumBlocks.LIME_ETHER_TORCH.get(), MalumBlocks.LIME_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PINK_ETHER_TORCH = ITEMS.register("pink_ether_torch", () -> new WallOrFloorItem(MalumBlocks.PINK_ETHER_TORCH.get(), MalumBlocks.PINK_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CYAN_ETHER_TORCH = ITEMS.register("cyan_ether_torch", () -> new WallOrFloorItem(MalumBlocks.CYAN_ETHER_TORCH.get(), MalumBlocks.CYAN_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURPLE_ETHER_TORCH = ITEMS.register("purple_ether_torch", () -> new WallOrFloorItem(MalumBlocks.PURPLE_ETHER_TORCH.get(), MalumBlocks.PURPLE_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BLUE_ETHER_TORCH = ITEMS.register("blue_ether_torch", () -> new WallOrFloorItem(MalumBlocks.BLUE_ETHER_TORCH.get(), MalumBlocks.BLUE_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BROWN_ETHER_TORCH = ITEMS.register("brown_ether_torch", () -> new WallOrFloorItem(MalumBlocks.BROWN_ETHER_TORCH.get(), MalumBlocks.BROWN_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> GREEN_ETHER_TORCH = ITEMS.register("green_ether_torch", () -> new WallOrFloorItem(MalumBlocks.GREEN_ETHER_TORCH.get(), MalumBlocks.GREEN_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RED_ETHER_TORCH = ITEMS.register("red_ether_torch", () -> new WallOrFloorItem(MalumBlocks.RED_ETHER_TORCH.get(), MalumBlocks.RED_WALL_ETHER_TORCH.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> ORANGE_ETHER_BRAZIER = ITEMS.register("orange_ether_brazier", () -> new BlockItem(MalumBlocks.ORANGE_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MAGENTA_ETHER_BRAZIER = ITEMS.register("magenta_ether_brazier", () -> new BlockItem(MalumBlocks.MAGENTA_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> LIGHT_BLUE_ETHER_BRAZIER = ITEMS.register("light_blue_ether_brazier", () -> new BlockItem(MalumBlocks.LIGHT_BLUE_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> YELLOW_ETHER_BRAZIER = ITEMS.register("yellow_ether_brazier", () -> new BlockItem(MalumBlocks.YELLOW_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> LIME_ETHER_BRAZIER = ITEMS.register("lime_ether_brazier", () -> new BlockItem(MalumBlocks.LIME_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PINK_ETHER_BRAZIER = ITEMS.register("pink_ether_brazier", () -> new BlockItem(MalumBlocks.PINK_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CYAN_ETHER_BRAZIER = ITEMS.register("cyan_ether_brazier", () -> new BlockItem(MalumBlocks.CYAN_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> PURPLE_ETHER_BRAZIER = ITEMS.register("purple_ether_brazier", () -> new BlockItem(MalumBlocks.PURPLE_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BLUE_ETHER_BRAZIER = ITEMS.register("blue_ether_brazier", () -> new BlockItem(MalumBlocks.BLUE_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BROWN_ETHER_BRAZIER = ITEMS.register("brown_ether_brazier", () -> new BlockItem(MalumBlocks.BROWN_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> GREEN_ETHER_BRAZIER = ITEMS.register("green_ether_brazier", () -> new BlockItem(MalumBlocks.GREEN_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RED_ETHER_BRAZIER = ITEMS.register("red_ether_brazier", () -> new BlockItem(MalumBlocks.RED_ETHER_BRAZIER.get(), BUILDING_PROPERTIES()));
    //endregion

    //region spirits
    public static final RegistryObject<Item> SACRED_SPIRIT = ITEMS.register("sacred_spirit", () -> new SpiritItem(SPLINTER_PROPERTIES(), MalumSpiritTypes.SACRED_SPIRIT));
    public static final RegistryObject<Item> WICKED_SPIRIT = ITEMS.register("wicked_spirit", () -> new SpiritItem(SPLINTER_PROPERTIES(), MalumSpiritTypes.WICKED_SPIRIT));
    public static final RegistryObject<Item> ARCANE_SPIRIT = ITEMS.register("arcane_spirit", () -> new SpiritItem(SPLINTER_PROPERTIES(), MalumSpiritTypes.ARCANE_SPIRIT));
    public static final RegistryObject<Item> ELDRITCH_SPIRIT = ITEMS.register("eldritch_spirit", () -> new SpiritItem(SPLINTER_PROPERTIES(), MalumSpiritTypes.ELDRITCH_SPIRIT));
    public static final RegistryObject<Item> EARTHEN_SPIRIT = ITEMS.register("earthen_spirit", () -> new SpiritItem(SPLINTER_PROPERTIES(), MalumSpiritTypes.EARTHEN_SPIRIT));
    public static final RegistryObject<Item> INFERNAL_SPIRIT = ITEMS.register("infernal_spirit", () -> new SpiritItem(SPLINTER_PROPERTIES(), MalumSpiritTypes.INFERNAL_SPIRIT));
    public static final RegistryObject<Item> AERIAL_SPIRIT = ITEMS.register("aerial_spirit", () -> new SpiritItem(SPLINTER_PROPERTIES(), MalumSpiritTypes.AERIAL_SPIRIT));
    public static final RegistryObject<Item> AQUATIC_SPIRIT = ITEMS.register("aquatic_spirit", () -> new SpiritItem(SPLINTER_PROPERTIES(), MalumSpiritTypes.AQUATIC_SPIRIT));
    //endregion

    //region ores
    public static final RegistryObject<Item> BLAZING_QUARTZ_ORE = ITEMS.register("blazing_quartz_ore", () -> new BlockItem(MalumBlocks.BLAZING_QUARTZ_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLAZING_QUARTZ = ITEMS.register("blazing_quartz", () -> new FuelItem(DEFAULT_PROPERTIES(), 3200));
    public static final RegistryObject<Item> BLAZING_QUARTZ_BLOCK = ITEMS.register("blazing_quartz_block", () -> new BlockItem(MalumBlocks.BLAZING_QUARTZ_BLOCK.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> SOULSTONE_ORE = ITEMS.register("soulstone_ore", () -> new BlockItem(MalumBlocks.SOULSTONE_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOULSTONE = ITEMS.register("soulstone", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOULSTONE_BLOCK = ITEMS.register("soulstone_block", () -> new BlockItem(MalumBlocks.SOULSTONE_BLOCK.get(), DEFAULT_PROPERTIES()));
    //endregion

    //region crafting blocks
    public static final RegistryObject<Item> SPIRIT_ALTAR = ITEMS.register("spirit_altar", () -> new BlockItem(MalumBlocks.SPIRIT_ALTAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPIRIT_JAR = ITEMS.register("spirit_jar", () -> new BlockItem(MalumBlocks.SPIRIT_JAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TOTEM_BASE = ITEMS.register("totem_base", () -> new BlockItem(MalumBlocks.TOTEM_BASE.get(), DEFAULT_PROPERTIES()));
    //endregion

    //region materials
    public static final RegistryObject<Item> ARCANE_CHARCOAL = ITEMS.register("arcane_charcoal", () -> new FuelItem(DEFAULT_PROPERTIES(), 6400));
    public static final RegistryObject<Item> HEX_ASH = ITEMS.register("hex_ash", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> RADIANT_SOULSTONE = ITEMS.register("radiant_soulstone", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_SPIRIT_RESONATOR = ITEMS.register("hallowed_spirit_resonator", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STAINED_SPIRIT_RESONATOR = ITEMS.register("stained_spirit_resonator", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CONFINED_BRILLIANCE = ITEMS.register("confined_brilliance", () -> new ConfinedBrillianceItem(DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> COAL_FRAGMENT = ITEMS.register("coal_fragment", () -> new FuelItem(DEFAULT_PROPERTIES(), 200));
    public static final RegistryObject<Item> BLAZING_QUARTZ_FRAGMENT = ITEMS.register("blazing_quartz_fragment", () -> new FuelItem(DEFAULT_PROPERTIES(), 400));
    public static final RegistryObject<Item> ARCANE_CHARCOAL_FRAGMENT = ITEMS.register("arcane_charcoal_fragment", () -> new FuelItem(DEFAULT_PROPERTIES(), 800));

    public static final RegistryObject<Item> HALLOWED_GOLD_INGOT = ITEMS.register("hallowed_gold_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_GOLD_NUGGET = ITEMS.register("hallowed_gold_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_GOLD_BLOCK = ITEMS.register("hallowed_gold_block", () -> new BlockItem(MalumBlocks.HALLOWED_GOLD_BLOCK.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_INGOT = ITEMS.register("soul_stained_steel_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_NUGGET = ITEMS.register("soul_stained_steel_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_BLOCK = ITEMS.register("soul_stained_steel_block", () -> new BlockItem(MalumBlocks.SOUL_STAINED_STEEL_BLOCK.get(), DEFAULT_PROPERTIES()));

    //endregion

    //region contents
    public static final RegistryObject<Item> CRUDE_SCYTHE = ITEMS.register("crude_scythe", () -> new ScytheItem(ItemTier.IRON, 0, 0.1f, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SWORD = ITEMS.register("soul_stained_steel_sword", () -> new ModSwordItem(SOUL_STAINED_STEEL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_PICKAXE = ITEMS.register("soul_stained_steel_pickaxe", () -> new ModPickaxeItem(SOUL_STAINED_STEEL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_AXE = ITEMS.register("soul_stained_steel_axe", () -> new ModAxeItem(SOUL_STAINED_STEEL_ITEM, 0.5f, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SHOVEL = ITEMS.register("soul_stained_steel_shovel", () -> new ModShovelItem(SOUL_STAINED_STEEL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_HOE = ITEMS.register("soul_stained_steel_hoe", () -> new ModHoeItem(SOUL_STAINED_STEEL_ITEM, 0, 0, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_HELMET = ITEMS.register("soul_stained_steel_helmet", () -> new SoulStainedSteelArmorItem(EquipmentSlotType.HEAD, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_CHESTPLATE = ITEMS.register("soul_stained_steel_chestplate", () -> new SoulStainedSteelArmorItem(EquipmentSlotType.CHEST, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_LEGGINGS = ITEMS.register("soul_stained_steel_leggings", () -> new SoulStainedSteelArmorItem(EquipmentSlotType.LEGS, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_BOOTS = ITEMS.register("soul_stained_steel_boots", () -> new SoulStainedSteelArmorItem(EquipmentSlotType.FEET, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STRONGHOLD_HELMET = ITEMS.register("soul_stained_stronghold_helmet", () -> new SoulStainedStrongholdArmorItem(EquipmentSlotType.HEAD, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STRONGHOLD_CHESTPLATE = ITEMS.register("soul_stained_stronghold_chestplate", () -> new SoulStainedStrongholdArmorItem(EquipmentSlotType.CHEST, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STRONGHOLD_LEGGINGS = ITEMS.register("soul_stained_stronghold_leggings", () -> new SoulStainedStrongholdArmorItem(EquipmentSlotType.LEGS, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STRONGHOLD_BOOTS = ITEMS.register("soul_stained_stronghold_boots", () -> new SoulStainedStrongholdArmorItem(EquipmentSlotType.FEET, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> TYRVING = ITEMS.register("tyrving", () -> new ModSwordItem(TYRVING_ITEM, 0, -0.1f, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> GILDED_RING = ITEMS.register("gilded_ring", () -> new CurioGildedRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ORNATE_RING = ITEMS.register("ornate_ring", () -> new CurioOrnateRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ORNATE_NECKLACE = ITEMS.register("ornate_necklace", () -> new CurioOrnateNecklace(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> GILDED_BELT = ITEMS.register("gilded_belt", () -> new CurioGildedBelt(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> RING_OF_ARCANE_REACH = ITEMS.register("ring_of_arcane_reach", () -> new CurioArcaneReachRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_ARCANE_SPOIL = ITEMS.register("ring_of_arcane_spoil", () -> new CurioArcaneSpoilRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_PROWESS = ITEMS.register("ring_of_prowess", () -> new CurioRingOfProwess(GEAR_PROPERTIES()));

    //endregion

    //region hidden items
    public static final RegistryObject<Item> COMICALLY_LARGE_TOPHAT = ITEMS.register("comically_large_tophat", () -> new CurioComicallyLargeTophat(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> CREATIVE_SCYTHE = ITEMS.register("creative_scythe", () -> new ScytheItem(ItemTier.IRON, 9993, 9.19f, CREATIVE_PROPERTIES().defaultMaxDamage(0)));
    public static final RegistryObject<Item> TOKEN_OF_GRATITUDE = ITEMS.register("token_of_gratitude", () -> new CurioTokenOfGratitude(CREATIVE_PROPERTIES()));
    //endregion
}