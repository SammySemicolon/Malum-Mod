package com.sammy.malum.core.init;

import com.sammy.malum.common.items.BoneNeedleItem;
import com.sammy.malum.common.items.EnderQuarksItem;
import com.sammy.malum.common.items.MalumBookItem;
import com.sammy.malum.common.items.food.VoidBerriesItem;
import com.sammy.malum.common.items.tools.TyrvingSwordItem;
import com.sammy.malum.common.items.equipment.armor.SpiritedSteelArmorItem;
import com.sammy.malum.common.items.equipment.curios.*;
import com.sammy.malum.common.items.equipment.poppets.*;
import com.sammy.malum.common.items.food.SolarSyrupBottleItem;
import com.sammy.malum.common.items.tools.*;

import com.sammy.malum.common.items.tools.elementaltools.HoeOfGrowth;
import com.sammy.malum.common.items.tools.elementaltools.PickaxeOfTheCore;
import com.sammy.malum.common.items.tools.elementaltools.ShovelOfTremors;
import com.sammy.malum.common.items.tools.elementaltools.SwordOfShiftingSkies;
import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.init.tabs.MalumBuildingTab;
import com.sammy.malum.core.init.tabs.MalumCreativeTab;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.tabs.MalumSplinterTab;
import com.sammy.malum.core.systems.multiblock.MultiblockItem;
import com.sammy.malum.core.systems.multiblock.MultiblockStructure;
import com.sammy.malum.common.items.SpiritSplinterItem;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;
import static com.sammy.malum.common.items.MalumArmorTiers.ArmorTierEnum.SPIRITED_METAL_ARMOR;
import static com.sammy.malum.common.items.MalumItemTiers.ItemTierEnum.SPIRITED_METAL_ITEM;
import static com.sammy.malum.common.items.MalumItemTiers.ItemTierEnum.TYRVING_ITEM;
import static net.minecraft.item.Items.GLASS_BOTTLE;

@SuppressWarnings("unused")
public class MalumItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    
    public static Item.Properties ORE_PROPERTIES()
    {
        return new Item.Properties().group(ItemGroup.BUILDING_BLOCKS);
    }
    public static Item.Properties MATERIAL_PROPERTIES()
    {
        return new Item.Properties().group(ItemGroup.MATERIALS);
    }
    
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
    public static Item.Properties GEAR_PROPERTIES()
    {
        return new Item.Properties().group(MalumCreativeTab.INSTANCE).maxStackSize(1);
    }
    
    public static Item.Properties CREATIVE_PROPERTIES()
    {
        return new Item.Properties().maxStackSize(1);
    }
    
    public static final RegistryObject<Item> MALUM_BOOK = ITEMS.register("malum_book", () -> new MalumBookItem(GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_ROCK = ITEMS.register("tainted_rock", () -> new BlockItem(MalumBlocks.TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK = ITEMS.register("smooth_tainted_rock", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK = ITEMS.register("polished_tainted_rock", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS = ITEMS.register("tainted_rock_bricks", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS = ITEMS.register("cracked_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS = ITEMS.register("mossy_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES = ITEMS.register("tainted_rock_tiles", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES = ITEMS.register("cracked_tainted_rock_tiles", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_TILES = ITEMS.register("mossy_tainted_rock_tiles", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_ROCK_PILLAR = ITEMS.register("tainted_rock_pillar", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_PILLAR_CAP = ITEMS.register("tainted_rock_pillar_cap", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN = ITEMS.register("tainted_rock_column", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN_CAP = ITEMS.register("tainted_rock_column_cap", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> CUT_TAINTED_ROCK = ITEMS.register("cut_tainted_rock", () -> new BlockItem(MalumBlocks.CUT_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK = ITEMS.register("chiseled_tainted_rock", () -> new BlockItem(MalumBlocks.CHISELED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = ITEMS.register("tainted_rock_pressure_plate", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_ROCK_WALL = ITEMS.register("tainted_rock_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_WALL = ITEMS.register("tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("mossy_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_WALL = ITEMS.register("tainted_rock_tiles_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_TILES_WALL = ITEMS.register("mossy_tainted_rock_tiles_wall", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_WALL = ITEMS.register("cracked_tainted_rock_tiles_wall", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_ROCK_SLAB = ITEMS.register("tainted_rock_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_SLAB = ITEMS.register("polished_tainted_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_SLAB = ITEMS.register("smooth_tainted_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("mossy_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_SLAB = ITEMS.register("tainted_rock_tiles_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_SLAB = ITEMS.register("cracked_tainted_rock_tiles_slab", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_TILES_SLAB = ITEMS.register("mossy_tainted_rock_tiles_slab", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_ROCK_STAIRS = ITEMS.register("tainted_rock_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_STAIRS = ITEMS.register("polished_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_STAIRS = ITEMS.register("smooth_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("mossy_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_STAIRS = ITEMS.register("tainted_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_STAIRS = ITEMS.register("cracked_tainted_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_TILES_STAIRS = ITEMS.register("mossy_tainted_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> DARKENED_ROCK = ITEMS.register("darkened_rock", () -> new BlockItem(MalumBlocks.DARKENED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_DARKENED_ROCK = ITEMS.register("smooth_darkened_rock", () -> new BlockItem(MalumBlocks.SMOOTH_DARKENED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_DARKENED_ROCK = ITEMS.register("polished_darkened_rock", () -> new BlockItem(MalumBlocks.POLISHED_DARKENED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_BRICKS = ITEMS.register("darkened_rock_bricks", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_ROCK_BRICKS = ITEMS.register("cracked_darkened_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_ROCK_BRICKS = ITEMS.register("mossy_darkened_rock_bricks", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_TILES = ITEMS.register("darkened_rock_tiles", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_ROCK_TILES = ITEMS.register("cracked_darkened_rock_tiles", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_ROCK_TILES = ITEMS.register("mossy_darkened_rock_tiles", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> DARKENED_ROCK_PILLAR = ITEMS.register("darkened_rock_pillar", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_PILLAR_CAP = ITEMS.register("darkened_rock_pillar_cap", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_COLUMN = ITEMS.register("darkened_rock_column", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_COLUMN_CAP = ITEMS.register("darkened_rock_column_cap", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> CUT_DARKENED_ROCK = ITEMS.register("cut_darkened_rock", () -> new BlockItem(MalumBlocks.CUT_DARKENED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_DARKENED_ROCK = ITEMS.register("chiseled_darkened_rock", () -> new BlockItem(MalumBlocks.CHISELED_DARKENED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_PRESSURE_PLATE = ITEMS.register("darkened_rock_pressure_plate", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> DARKENED_ROCK_WALL = ITEMS.register("darkened_rock_wall", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_BRICKS_WALL = ITEMS.register("darkened_rock_bricks_wall", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_ROCK_BRICKS_WALL = ITEMS.register("mossy_darkened_rock_bricks_wall", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_ROCK_BRICKS_WALL = ITEMS.register("cracked_darkened_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_TILES_WALL = ITEMS.register("darkened_rock_tiles_wall", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_ROCK_TILES_WALL = ITEMS.register("mossy_darkened_rock_tiles_wall", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_ROCK_TILES_WALL = ITEMS.register("cracked_darkened_rock_tiles_wall", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> DARKENED_ROCK_SLAB = ITEMS.register("darkened_rock_slab", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_DARKENED_ROCK_SLAB = ITEMS.register("polished_darkened_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_DARKENED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_DARKENED_ROCK_SLAB = ITEMS.register("smooth_darkened_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_DARKENED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_BRICKS_SLAB = ITEMS.register("darkened_rock_bricks_slab", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_darkened_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_ROCK_BRICKS_SLAB = ITEMS.register("mossy_darkened_rock_bricks_slab", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_TILES_SLAB = ITEMS.register("darkened_rock_tiles_slab", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_ROCK_TILES_SLAB = ITEMS.register("cracked_darkened_rock_tiles_slab", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_ROCK_TILES_SLAB = ITEMS.register("mossy_darkened_rock_tiles_slab", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> DARKENED_ROCK_STAIRS = ITEMS.register("darkened_rock_stairs", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_DARKENED_ROCK_STAIRS = ITEMS.register("polished_darkened_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_DARKENED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_DARKENED_ROCK_STAIRS = ITEMS.register("smooth_darkened_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_DARKENED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_BRICKS_STAIRS = ITEMS.register("darkened_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_darkened_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_ROCK_BRICKS_STAIRS = ITEMS.register("mossy_darkened_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ROCK_TILES_STAIRS = ITEMS.register("darkened_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.DARKENED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_ROCK_TILES_STAIRS = ITEMS.register("cracked_darkened_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_ROCK_TILES_STAIRS = ITEMS.register("mossy_darkened_rock_tiles_stairs", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> SHORT_SUN_KISSED_GRASS = ITEMS.register("short_sun_kissed_grass", () -> new BlockItem(MalumBlocks.SHORT_SUN_KISSED_GRASS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_GRASS = ITEMS.register("sun_kissed_grass", () -> new BlockItem(MalumBlocks.SUN_KISSED_GRASS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TALL_SUN_KISSED_GRASS = ITEMS.register("tall_sun_kissed_grass", () -> new BlockItem(MalumBlocks.TALL_SUN_KISSED_GRASS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> LAVENDER = ITEMS.register("lavender", () -> new BlockItem(MalumBlocks.LAVENDER.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> RUNEWOOD_LOG = ITEMS.register("runewood_log", () -> new BlockItem(MalumBlocks.RUNEWOOD_LOG.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD = ITEMS.register("runewood", () -> new BlockItem(MalumBlocks.RUNEWOOD.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD_LOG = ITEMS.register("stripped_runewood_log", () -> new BlockItem(MalumBlocks.STRIPPED_RUNEWOOD_LOG.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD = ITEMS.register("stripped_runewood", () -> new BlockItem(MalumBlocks.STRIPPED_RUNEWOOD.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> RUNEWOOD_PLANKS = ITEMS.register("runewood_planks", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS = ITEMS.register("vertical_runewood_planks", () -> new BlockItem(MalumBlocks.VERTICAL_RUNEWOOD_PLANKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BOLTED_RUNEWOOD_PLANKS = ITEMS.register("bolted_runewood_planks", () -> new BlockItem(MalumBlocks.BOLTED_RUNEWOOD_PLANKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL = ITEMS.register("runewood_panel", () -> new BlockItem(MalumBlocks.RUNEWOOD_PANEL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES = ITEMS.register("runewood_tiles", () -> new BlockItem(MalumBlocks.RUNEWOOD_TILES.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_SLAB = ITEMS.register("runewood_planks_slab", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_SLAB = ITEMS.register("vertical_runewood_planks_slab", () -> new BlockItem(MalumBlocks.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BOLTED_RUNEWOOD_PLANKS_SLAB = ITEMS.register("bolted_runewood_planks_slab", () -> new BlockItem(MalumBlocks.BOLTED_RUNEWOOD_PLANKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL_SLAB = ITEMS.register("runewood_panel_slab", () -> new BlockItem(MalumBlocks.RUNEWOOD_PANEL_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES_SLAB = ITEMS.register("runewood_tiles_slab", () -> new BlockItem(MalumBlocks.RUNEWOOD_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_STAIRS = ITEMS.register("runewood_planks_stairs", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_STAIRS = ITEMS.register("vertical_runewood_planks_stairs", () -> new BlockItem(MalumBlocks.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BOLTED_RUNEWOOD_PLANKS_STAIRS = ITEMS.register("bolted_runewood_planks_stairs", () -> new BlockItem(MalumBlocks.BOLTED_RUNEWOOD_PLANKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PANEL_STAIRS = ITEMS.register("runewood_panel_stairs", () -> new BlockItem(MalumBlocks.RUNEWOOD_PANEL_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TILES_STAIRS = ITEMS.register("runewood_tiles_stairs", () -> new BlockItem(MalumBlocks.RUNEWOOD_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> CUT_RUNEWOOD_PLANKS = ITEMS.register("cut_runewood_planks", () -> new BlockItem(MalumBlocks.CUT_RUNEWOOD_PLANKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_BEAM = ITEMS.register("runewood_beam", () -> new BlockItem(MalumBlocks.RUNEWOOD_BEAM.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BOLTED_RUNEWOOD_BEAM = ITEMS.register("bolted_runewood_beam", () -> new BlockItem(MalumBlocks.BOLTED_RUNEWOOD_BEAM.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> RUNEWOOD_DOOR = ITEMS.register("runewood_door", () -> new BlockItem(MalumBlocks.RUNEWOOD_DOOR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TRAPDOOR = ITEMS.register("runewood_trapdoor", () -> new BlockItem(MalumBlocks.RUNEWOOD_TRAPDOOR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SOLID_RUNEWOOD_TRAPDOOR = ITEMS.register("solid_runewood_trapdoor", () -> new BlockItem(MalumBlocks.SOLID_RUNEWOOD_TRAPDOOR.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_BUTTON = ITEMS.register("runewood_planks_button", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_BUTTON.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_PRESSURE_PLATE = ITEMS.register("runewood_planks_pressure_plate", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE = ITEMS.register("runewood_planks_fence", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_FENCE.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE_GATE = ITEMS.register("runewood_planks_fence_gate", () -> new BlockItem(MalumBlocks.RUNEWOOD_PLANKS_FENCE_GATE.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> RUNE_LEAVES = ITEMS.register("rune_leaves", () -> new BlockItem(MalumBlocks.RUNE_LEAVES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_SAPLING = ITEMS.register("runewood_sapling", () -> new BlockItem(MalumBlocks.RUNEWOOD_SAPLING.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_GRASS_BLOCK = ITEMS.register("sun_kissed_grass_block", () -> new BlockItem(MalumBlocks.SUN_KISSED_GRASS_BLOCK.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> MARIGOLD = ITEMS.register("marigold", () -> new BlockItem(MalumBlocks.MARIGOLD.get(), BUILDING_PROPERTIES()));
    
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
    
    public static final RegistryObject<Item> CLEAN_GRAVEL = ITEMS.register("clean_gravel", () -> new BlockItem(MalumBlocks.CLEAN_GRAVEL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CLEAN_SAND = ITEMS.register("clean_sand", () -> new BlockItem(MalumBlocks.CLEAN_SAND.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> POLISHED_BONE_BLOCK = ITEMS.register("polished_bone_block", () -> new BlockItem(MalumBlocks.POLISHED_BONE_BLOCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_BEAM = ITEMS.register("bone_beam", () -> new BlockItem(MalumBlocks.BONE_BEAM.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_TILES = ITEMS.register("bone_tiles", () -> new BlockItem(MalumBlocks.BONE_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_BONE_TILES = ITEMS.register("cracked_bone_tiles", () -> new BlockItem(MalumBlocks.CRACKED_BONE_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_BRICKS = ITEMS.register("bone_bricks", () -> new BlockItem(MalumBlocks.BONE_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_BONE_BRICKS = ITEMS.register("cracked_bone_bricks", () -> new BlockItem(MalumBlocks.CRACKED_BONE_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_TILE = ITEMS.register("bone_tile", () -> new BlockItem(MalumBlocks.BONE_TILE.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CARVED_BONE_TILE = ITEMS.register("carved_bone_tile", () -> new BlockItem(MalumBlocks.CARVED_BONE_TILE.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> BONE_TILES_SLAB = ITEMS.register("bone_tiles_slab", () -> new BlockItem(MalumBlocks.BONE_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_TILES_STAIRS = ITEMS.register("bone_tiles_stairs", () -> new BlockItem(MalumBlocks.BONE_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_BONE_TILES_SLAB = ITEMS.register("cracked_bone_tiles_slab", () -> new BlockItem(MalumBlocks.CRACKED_BONE_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_BONE_TILES_STAIRS = ITEMS.register("cracked_bone_tiles_stairs", () -> new BlockItem(MalumBlocks.CRACKED_BONE_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_BRICKS_SLAB = ITEMS.register("bone_bricks_slab", () -> new BlockItem(MalumBlocks.BONE_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_BRICKS_STAIRS = ITEMS.register("bone_bricks_stairs", () -> new BlockItem(MalumBlocks.BONE_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_BONE_BRICKS_SLAB = ITEMS.register("cracked_bone_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_BONE_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_BONE_BRICKS_STAIRS = ITEMS.register("cracked_bone_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_BONE_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_TILE_SLAB = ITEMS.register("bone_tile_slab", () -> new BlockItem(MalumBlocks.BONE_TILE_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_TILE_STAIRS = ITEMS.register("bone_tile_stairs", () -> new BlockItem(MalumBlocks.BONE_TILE_STAIRS.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> BONE_TILES_WALL = ITEMS.register("bone_tiles_wall", () -> new BlockItem(MalumBlocks.BONE_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_BONE_TILES_WALL = ITEMS.register("cracked_bone_tiles_wall", () -> new BlockItem(MalumBlocks.CRACKED_BONE_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_BRICKS_WALL = ITEMS.register("bone_bricks_wall", () -> new BlockItem(MalumBlocks.BONE_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_BONE_BRICKS_WALL = ITEMS.register("cracked_bone_bricks_wall", () -> new BlockItem(MalumBlocks.CRACKED_BONE_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> BONE_TILE_WALL = ITEMS.register("bone_tile_wall", () -> new BlockItem(MalumBlocks.BONE_TILE_WALL.get(), BUILDING_PROPERTIES()));
    
    public static final RegistryObject<Item> LIFE_SPIRIT_SPLINTER = ITEMS.register("life_spirit_splinter", () -> new SpiritSplinterItem(SPLINTER_PROPERTIES()));
    public static final RegistryObject<Item> DEATH_SPIRIT_SPLINTER = ITEMS.register("death_spirit_splinter", () -> new SpiritSplinterItem(SPLINTER_PROPERTIES()));
    public static final RegistryObject<Item> AIR_SPIRIT_SPLINTER = ITEMS.register("air_spirit_splinter", () -> new SpiritSplinterItem(SPLINTER_PROPERTIES()));
    public static final RegistryObject<Item> WATER_SPIRIT_SPLINTER = ITEMS.register("water_spirit_splinter", () -> new SpiritSplinterItem(SPLINTER_PROPERTIES()));
    public static final RegistryObject<Item> FIRE_SPIRIT_SPLINTER = ITEMS.register("fire_spirit_splinter", () -> new SpiritSplinterItem(SPLINTER_PROPERTIES()));
    public static final RegistryObject<Item> EARTH_SPIRIT_SPLINTER = ITEMS.register("earth_spirit_splinter", () -> new SpiritSplinterItem(SPLINTER_PROPERTIES()));
    public static final RegistryObject<Item> MAGIC_SPIRIT_SPLINTER = ITEMS.register("magic_spirit_splinter", () -> new SpiritSplinterItem(SPLINTER_PROPERTIES()));
    public static final RegistryObject<Item> ELDRITCH_SPIRIT_SPLINTER = ITEMS.register("eldritch_spirit_splinter", () -> new SpiritSplinterItem(SPLINTER_PROPERTIES()));
    
    public static final RegistryObject<Item> BLAZE_QUARTZ_ORE = ITEMS.register("blaze_quartz_ore", () -> new BlockItem(MalumBlocks.BLAZE_QUARTZ_ORE.get(), ORE_PROPERTIES()));
    public static final RegistryObject<Item> BLAZE_QUARTZ = ITEMS.register("blaze_quartz", () -> new Item(MATERIAL_PROPERTIES()));
    //endregion
    
    //region crafting blocks
    
    public static final RegistryObject<Item> SOLAR_SAP_BOTTLE = ITEMS.register("solar_sap_bottle", () -> new Item(DEFAULT_PROPERTIES().containerItem(GLASS_BOTTLE)));
    public static final RegistryObject<Item> SOLAR_SAPBALL = ITEMS.register("solar_sapball", () -> new Item(DEFAULT_PROPERTIES().containerItem(GLASS_BOTTLE)));
    public static final RegistryObject<Item> SOLAR_SYRUP_BOTTLE = ITEMS.register("solar_syrup_bottle", () -> new SolarSyrupBottleItem(DEFAULT_PROPERTIES().containerItem(GLASS_BOTTLE).food((new Food.Builder()).hunger(8).saturation(2F).build())));
    public static final RegistryObject<Item> VOID_BERRIES = ITEMS.register("void_berries", () -> new VoidBerriesItem(DEFAULT_PROPERTIES().food((new Food.Builder()).hunger(1).build())));
    
    public static final RegistryObject<Item> SPIRIT_ALTAR = ITEMS.register("spirit_altar", () -> new BlockItem(MalumBlocks.SPIRIT_ALTAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TOTEM_CORE = ITEMS.register("totem_core", () -> new BlockItem(MalumBlocks.TOTEM_CORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPIRIT_PIPE = ITEMS.register("spirit_pipe", () -> new BlockItem(MalumBlocks.SPIRIT_PIPE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPIRIT_JAR = ITEMS.register("spirit_jar", () -> new BlockItem(MalumBlocks.SPIRIT_JAR.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SPIRIT_KILN = ITEMS.register("spirit_kiln", () -> new MultiblockItem(MalumBlocks.SPIRIT_KILN.get(), DEFAULT_PROPERTIES(), MultiblockStructure.doubleTallBlock(MalumBlocks.SPIRIT_KILN_TOP.get())));
    public static final RegistryObject<Item> DAMAGED_SPIRIT_KILN = ITEMS.register("damaged_spirit_kiln", () -> new MultiblockItem(MalumBlocks.DAMAGED_SPIRIT_KILN.get(), DEFAULT_PROPERTIES(), MultiblockStructure.doubleTallBlock(MalumBlocks.DAMAGED_SPIRIT_KILN_TOP.get())));
    public static final RegistryObject<Item> ITEM_STAND = ITEMS.register("item_stand", () -> new BlockItem(MalumBlocks.ITEM_STAND.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> EXTRACTION_FOCUS = ITEMS.register("extraction_focus", () -> new BlockItem(MalumBlocks.EXTRACTION_FOCUS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> IMPERVIOUS_ROCK = ITEMS.register("impervious_rock", () -> new BlockItem(MalumBlocks.IMPERVIOUS_ROCK.get(), DEFAULT_PROPERTIES()));
    //endregion
    
    //region simple components
    public static final RegistryObject<Item> ARCANE_CHARCOAL = ITEMS.register("arcane_charcoal", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> UNHOLY_BLEND = ITEMS.register("unholy_blend", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ARCANE_GRIT = ITEMS.register("arcane_grit", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_GEM = ITEMS.register("soul_gem", () -> new Item(DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> HALLOWED_GOLD_INGOT = ITEMS.register("hallowed_gold_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_GOLD_NUGGET = ITEMS.register("hallowed_gold_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_GOLD_BLOCK = ITEMS.register("hallowed_gold_block", () -> new BlockItem(MalumBlocks.HALLOWED_GOLD_BLOCK.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SPIRITED_METAL_INGOT = ITEMS.register("spirited_metal_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_NUGGET = ITEMS.register("spirited_metal_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_BLOCK = ITEMS.register("spirited_metal_block", () -> new BlockItem(MalumBlocks.SPIRITED_METAL_BLOCK.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> UMBRAL_METAL_INGOT = ITEMS.register("umbral_metal_ingot", () -> new Item(DEFAULT_PROPERTIES().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> UMBRAL_METAL_SHARDS = ITEMS.register("umbral_metal_shards", () -> new Item(DEFAULT_PROPERTIES().rarity(Rarity.RARE)));
    //endregion
    
    //region combined components
    
    //    public static final RegistryObject<Item> ECTOPLASM = ITEMS.register("ectoplasm", () -> new Item(DEFAULT_PROPERTIES()));
    //    public static final RegistryObject<Item> SHARD_OF_WISDOM = ITEMS.register("shard_of_wisdom", () -> new SimpleFoiledItem(DEFAULT_PROPERTIES().rarity(Rarity.UNCOMMON)));
    
    //endregion
    
    //region contents
    public static final RegistryObject<Item> RUDIMENTARY_SNARE = ITEMS.register("rudimentary_snare", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRUDE_SCYTHE = ITEMS.register("crude_scythe", () -> new ScytheItem(ItemTier.IRON, 0, 0.1f,GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> SPIRITED_METAL_SCYTHE = ITEMS.register("spirited_metal_scythe", () -> new ScytheItem(SPIRITED_METAL_ITEM, 0, 0.2f,GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_SWORD = ITEMS.register("spirited_metal_sword", () -> new ModSwordItem(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_PICKAXE = ITEMS.register("spirited_metal_pickaxe", () -> new ModPickaxeItem(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_AXE = ITEMS.register("spirited_metal_axe", () -> new ModAxeItem(SPIRITED_METAL_ITEM, 2, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_SHOVEL = ITEMS.register("spirited_metal_shovel", () -> new ModShovelItem(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_HOE = ITEMS.register("spirited_metal_hoe", () -> new ModHoeItem(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> SPIRITED_METAL_HELMET = ITEMS.register("spirited_metal_helmet", () -> new SpiritedSteelArmorItem(SPIRITED_METAL_ARMOR, EquipmentSlotType.HEAD, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_CHESTPLATE = ITEMS.register("spirited_metal_chestplate", () -> new SpiritedSteelArmorItem(SPIRITED_METAL_ARMOR, EquipmentSlotType.CHEST, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_LEGGINGS = ITEMS.register("spirited_metal_leggings", () -> new SpiritedSteelArmorItem(SPIRITED_METAL_ARMOR, EquipmentSlotType.LEGS, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SPIRITED_METAL_BOOTS = ITEMS.register("spirited_metal_boots", () -> new SpiritedSteelArmorItem(SPIRITED_METAL_ARMOR, EquipmentSlotType.FEET, GEAR_PROPERTIES()));
    
    //    public static final RegistryObject<Item> UMBRAL_SWORD = ITEMS.register("umbral_sword", () -> new ModSwordItem(UMBRAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    //    public static final RegistryObject<Item> UMBRAL_PICKAXE = ITEMS.register("umbral_pickaxe", () -> new ModPickaxeItem(UMBRAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    //    public static final RegistryObject<Item> UMBRAL_AXE = ITEMS.register("umbral_axe", () -> new ModAxeItem(UMBRAL_ITEM, 3, 0, GEAR_PROPERTIES()));
    //    public static final RegistryObject<Item> UMBRAL_SHOVEL = ITEMS.register("umbral_shovel", () -> new ModShovelItem(UMBRAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    //    public static final RegistryObject<Item> UMBRAL_HOE = ITEMS.register("umbral_hoe", () -> new ModHoeItem(UMBRAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    //
    //    public static final RegistryObject<Item> UMBRAL_HELMET = ITEMS.register("umbral_helmet", () -> new UmbralArmor(UMBRAL_ARMOR, EquipmentSlotType.HEAD, GEAR_PROPERTIES()));
    //    public static final RegistryObject<Item> UMBRAL_CHESTPLATE = ITEMS.register("umbral_chestplate", () -> new UmbralArmor(UMBRAL_ARMOR, EquipmentSlotType.CHEST, GEAR_PROPERTIES()));
    //    public static final RegistryObject<Item> UMBRAL_LEGGINGS = ITEMS.register("umbral_leggings", () -> new UmbralArmor(UMBRAL_ARMOR, EquipmentSlotType.LEGS, GEAR_PROPERTIES()));
    //    public static final RegistryObject<Item> UMBRAL_BOOTS = ITEMS.register("umbral_boots", () -> new UmbralArmor(UMBRAL_ARMOR, EquipmentSlotType.FEET, GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> SWORD_OF_MOVING_CLOUDS = ITEMS.register("sword_of_moving_clouds", () -> new SwordOfShiftingSkies(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> PICKAXE_OF_THE_CORE = ITEMS.register("pickaxe_of_the_core", () -> new PickaxeOfTheCore(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SHOVEL_OF_TREMORS = ITEMS.register("shovel_of_tremors", () -> new ShovelOfTremors(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> HOE_OF_GROWTH = ITEMS.register("hoe_of_growth", () -> new HoeOfGrowth(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> POPPET = ITEMS.register("poppet", () -> new Item(GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> POPPET_OF_BLEEDING = ITEMS.register("poppet_of_bleeding", () -> new PoppetOfBleeding(GEAR_PROPERTIES().maxDamage(402)));
    public static final RegistryObject<Item> POPPET_OF_DEFIANCE = ITEMS.register("poppet_of_defiance", () -> new PoppetOfDefiance(GEAR_PROPERTIES().maxDamage(402)));
    public static final RegistryObject<Item> POPPET_OF_MISFORTUNE = ITEMS.register("poppet_of_misfortune", () -> new PoppetOfMisfortune(GEAR_PROPERTIES().maxDamage(402)));
    public static final RegistryObject<Item> POPPET_OF_SAPPING = ITEMS.register("poppet_of_sapping", () -> new PoppetOfSapping(GEAR_PROPERTIES().maxDamage(402)));
    public static final RegistryObject<Item> POPPET_OF_SHATTERING = ITEMS.register("poppet_of_shattering", () -> new PoppetOfShattering(GEAR_PROPERTIES().maxDamage(402)));
    public static final RegistryObject<Item> POPPET_OF_VENGEANCE = ITEMS.register("poppet_of_vengeance", () -> new PoppetOfVengeance(GEAR_PROPERTIES().maxDamage(402)));
    public static final RegistryObject<Item> POPPET_OF_UNDYING = ITEMS.register("poppet_of_undying", () -> new PoppetOfUndying(GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> BLESSED_POPPET = ITEMS.register("blessed_poppet", () -> new BlessedPoppet(GEAR_PROPERTIES().maxDamage(101)));
    public static final RegistryObject<Item> BONE_NEEDLE = ITEMS.register("bone_needle", () -> new BoneNeedleItem(GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> TYRVING = ITEMS.register("tyrving", () -> new TyrvingSwordItem(TYRVING_ITEM, 0, -0.1f,GEAR_PROPERTIES()));
    public static final RegistryObject<Item> FOOLS_BLESSING = ITEMS.register("fools_blessing", () -> new ModSwordItem(SPIRITED_METAL_ITEM, 0, 0, GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> RING_OF_SUPPRESSION = ITEMS.register("ring_of_suppression", () -> new SimpleCurio(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_ATTRACTION = ITEMS.register("ring_of_attraction", () -> new SimpleCurio(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> POPPET_BELT = ITEMS.register("poppet_belt", () -> new CurioPoppetBelt(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> BOOTS_OF_LEVITATION = ITEMS.register("boots_of_levitation", () -> new CurioBootsOFLevitation(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ARCANE_SEAL = ITEMS.register("arcane_seal", () -> new SimpleCurio(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> LIVING_CAPACITOR = ITEMS.register("living_capacitor", () -> new SimpleCurio(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> FLASK_OF_GREED = ITEMS.register("flask_of_greed", () -> new SimpleCurio(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ANCESTRAL_VEIL = ITEMS.register("ancestral_veil", () -> new CurioAncestralVeil(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> KARMIC_HOLDER = ITEMS.register("karmic_holder", () -> new SimpleCurio(GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> ABSTRUSE_BLOCK = ITEMS.register("abstruse_block", () -> new BlockItem(MalumBlocks.ABSTRUSE_BLOCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ENDER_QUARKS = ITEMS.register("ender_quarks", () -> new EnderQuarksItem(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> WITHER_SAND = ITEMS.register("wither_sand", () -> new BlockItem(MalumBlocks.WITHER_SAND.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> WILD_FARMLAND = ITEMS.register("wild_farmland", () -> new BlockItem(MalumBlocks.WILD_FARMLAND.get(), DEFAULT_PROPERTIES()));
    //endregion
    public static final RegistryObject<Item> COMICALLY_LARGE_TOPHAT = ITEMS.register("comically_large_tophat", () -> new CurioComicallyLargeTophat(GEAR_PROPERTIES()));
    
    //region hidden items
    public static final RegistryObject<Item> CREATIVE_SCYTHE = ITEMS.register("creative_scythe", () -> new ScytheItem(ItemTier.IRON, 9993, 9.19f,CREATIVE_PROPERTIES().defaultMaxDamage(0)));
    
    public static final RegistryObject<Item> FLUFFY_TAIL = ITEMS.register("fluffy_tail", () -> new CurioFluffyTail(CREATIVE_PROPERTIES()));
    //endregion
    
    //endregion
}