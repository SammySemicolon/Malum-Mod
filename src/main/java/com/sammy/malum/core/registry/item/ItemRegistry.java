package com.sammy.malum.core.registry.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.model.DripArmorModel;
import com.sammy.malum.client.model.SoulStainedSteelArmorModel;
import com.sammy.malum.client.model.SpiritHunterArmorModel;
import com.sammy.malum.client.model.TailModel;
import com.sammy.malum.common.block.misc.MalumLeavesBlock;
import com.sammy.malum.common.item.EncyclopediaArcanaItem;
import com.sammy.malum.common.item.equipment.SpiritPouchItem;
import com.sammy.malum.common.item.equipment.armor.SoulStainedSteelArmorItem;
import com.sammy.malum.common.item.equipment.armor.SpiritHunterArmorItem;
import com.sammy.malum.common.item.equipment.armor.vanity.DripArmorItem;
import com.sammy.malum.common.item.equipment.curios.*;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.item.ether.EtherBrazierItem;
import com.sammy.malum.common.item.ether.EtherItem;
import com.sammy.malum.common.item.ether.EtherTorchItem;
import com.sammy.malum.common.item.food.HolySyrupItem;
import com.sammy.malum.common.item.food.UnholySyrupItem;
import com.sammy.malum.common.item.misc.*;
import com.sammy.malum.common.item.tools.*;
import com.sammy.malum.core.helper.ClientHelper;
import com.sammy.malum.core.helper.ColorHelper;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.content.SpiritTypeRegistry;
import com.sammy.malum.core.registry.item.tabs.MalumBuildingTab;
import com.sammy.malum.core.registry.item.tabs.MalumCreativeTab;
import com.sammy.malum.core.registry.item.tabs.MalumNatureTab;
import com.sammy.malum.core.registry.item.tabs.MalumSplinterTab;
import com.sammy.malum.core.registry.misc.EntityRegistry;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumMod.MODID;
import static com.sammy.malum.core.helper.ClientHelper.brighter;
import static com.sammy.malum.core.helper.ClientHelper.darker;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;
import static com.sammy.malum.core.registry.item.ItemTiers.ItemTierEnum.SOUL_STAINED_STEEL_ITEM;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

@SuppressWarnings("unused")
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new Item.Properties().tab(MalumCreativeTab.INSTANCE);
    }

    public static Item.Properties SPLINTER_PROPERTIES() {
        return new Item.Properties().tab(MalumSplinterTab.INSTANCE);
    }

    public static Item.Properties BUILDING_PROPERTIES() {
        return new Item.Properties().tab(MalumBuildingTab.INSTANCE);
    }

    public static Item.Properties NATURE_PROPERTIES() {
        return new Item.Properties().tab(MalumNatureTab.INSTANCE);
    }

    public static Item.Properties GEAR_PROPERTIES() {
        return new Item.Properties().tab(MalumCreativeTab.INSTANCE).stacksTo(1);
    }

    public static Item.Properties HIDDEN_PROPERTIES() {
        return new Item.Properties().stacksTo(1);
    }

    public static final RegistryObject<Item> ENCYCLOPEDIA_ARCANA = ITEMS.register("encyclopedia_arcana", () -> new EncyclopediaArcanaItem(GEAR_PROPERTIES().rarity(Rarity.UNCOMMON)));

    //region tainted rock
    public static final RegistryObject<Item> TAINTED_ROCK = ITEMS.register("tainted_rock", () -> new BlockItem(BlockRegistry.TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK = ITEMS.register("smooth_tainted_rock", () -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK = ITEMS.register("polished_tainted_rock", () -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS = ITEMS.register("tainted_rock_bricks", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS = ITEMS.register("cracked_tainted_rock_bricks", () -> new BlockItem(BlockRegistry.CRACKED_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES = ITEMS.register("tainted_rock_tiles", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES = ITEMS.register("cracked_tainted_rock_tiles", () -> new BlockItem(BlockRegistry.CRACKED_TAINTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS = ITEMS.register("small_tainted_rock_bricks", () -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TAINTED_ROCK_BRICKS = ITEMS.register("cracked_small_tainted_rock_bricks", () -> new BlockItem(BlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_PILLAR = ITEMS.register("tainted_rock_pillar", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_PILLAR_CAP = ITEMS.register("tainted_rock_pillar_cap", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN = ITEMS.register("tainted_rock_column", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN_CAP = ITEMS.register("tainted_rock_column_cap", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_TAINTED_ROCK = ITEMS.register("cut_tainted_rock", () -> new BlockItem(BlockRegistry.CUT_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK = ITEMS.register("chiseled_tainted_rock", () -> new BlockItem(BlockRegistry.CHISELED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = ITEMS.register("tainted_rock_pressure_plate", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_WALL = ITEMS.register("tainted_rock_wall", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_WALL = ITEMS.register("tainted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_tainted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_WALL = ITEMS.register("tainted_rock_tiles_wall", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_WALL = ITEMS.register("cracked_tainted_rock_tiles_wall", () -> new BlockItem(BlockRegistry.CRACKED_TAINTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("small_tainted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_small_tainted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_SLAB = ITEMS.register("tainted_rock_slab", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_SLAB = ITEMS.register("smooth_tainted_rock_slab", () -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_SLAB = ITEMS.register("polished_tainted_rock_slab", () -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("tainted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_tainted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_SLAB = ITEMS.register("tainted_rock_tiles_slab", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_SLAB = ITEMS.register("cracked_tainted_rock_tiles_slab", () -> new BlockItem(BlockRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("small_tainted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_small_tainted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_STAIRS = ITEMS.register("tainted_rock_stairs", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_STAIRS = ITEMS.register("smooth_tainted_rock_stairs", () -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_STAIRS = ITEMS.register("polished_tainted_rock_stairs", () -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("tainted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_tainted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_STAIRS = ITEMS.register("tainted_rock_tiles_stairs", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_TILES_STAIRS = ITEMS.register("cracked_tainted_rock_tiles_stairs", () -> new BlockItem(BlockRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("small_tainted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_small_tainted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TAINTED_ROCK_ITEM_STAND = ITEMS.register("tainted_rock_item_stand", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_ITEM_PEDESTAL = ITEMS.register("tainted_rock_item_pedestal", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));

    //endregion

    //region twisted rock
    public static final RegistryObject<Item> TWISTED_ROCK = ITEMS.register("twisted_rock", () -> new BlockItem(BlockRegistry.TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK = ITEMS.register("smooth_twisted_rock", () -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK = ITEMS.register("polished_twisted_rock", () -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS = ITEMS.register("twisted_rock_bricks", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_BRICKS = ITEMS.register("cracked_twisted_rock_bricks", () -> new BlockItem(BlockRegistry.CRACKED_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES = ITEMS.register("twisted_rock_tiles", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_TILES = ITEMS.register("cracked_twisted_rock_tiles", () -> new BlockItem(BlockRegistry.CRACKED_TWISTED_ROCK_TILES.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS = ITEMS.register("small_twisted_rock_bricks", () -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TWISTED_ROCK_BRICKS = ITEMS.register("cracked_small_twisted_rock_bricks", () -> new BlockItem(BlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_PILLAR = ITEMS.register("twisted_rock_pillar", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_PILLAR.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_PILLAR_CAP = ITEMS.register("twisted_rock_pillar_cap", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_PILLAR_CAP.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN = ITEMS.register("twisted_rock_column", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN_CAP = ITEMS.register("twisted_rock_column_cap", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_TWISTED_ROCK = ITEMS.register("cut_twisted_rock", () -> new BlockItem(BlockRegistry.CUT_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TWISTED_ROCK = ITEMS.register("chiseled_twisted_rock", () -> new BlockItem(BlockRegistry.CHISELED_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_PRESSURE_PLATE = ITEMS.register("twisted_rock_pressure_plate", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_WALL = ITEMS.register("twisted_rock_wall", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_WALL = ITEMS.register("twisted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_twisted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_WALL = ITEMS.register("twisted_rock_tiles_wall", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("small_twisted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL = ITEMS.register("cracked_small_twisted_rock_bricks_wall", () -> new BlockItem(BlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_TILES_WALL = ITEMS.register("cracked_twisted_rock_tiles_wall", () -> new BlockItem(BlockRegistry.CRACKED_TWISTED_ROCK_TILES_WALL.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_SLAB = ITEMS.register("twisted_rock_slab", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_SLAB = ITEMS.register("smooth_twisted_rock_slab", () -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_SLAB = ITEMS.register("polished_twisted_rock_slab", () -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("twisted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_twisted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_SLAB = ITEMS.register("twisted_rock_tiles_slab", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_TILES_SLAB = ITEMS.register("cracked_twisted_rock_tiles_slab", () -> new BlockItem(BlockRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("small_twisted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_small_twisted_rock_bricks_slab", () -> new BlockItem(BlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_STAIRS = ITEMS.register("twisted_rock_stairs", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_STAIRS = ITEMS.register("smooth_twisted_rock_stairs", () -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_STAIRS = ITEMS.register("polished_twisted_rock_stairs", () -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("twisted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_twisted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_STAIRS = ITEMS.register("twisted_rock_tiles_stairs", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TWISTED_ROCK_TILES_STAIRS = ITEMS.register("cracked_twisted_rock_tiles_stairs", () -> new BlockItem(BlockRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("small_twisted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_small_twisted_rock_bricks_stairs", () -> new BlockItem(BlockRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_STAND = ITEMS.register("twisted_rock_item_stand", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_STAND.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_PEDESTAL = ITEMS.register("twisted_rock_item_pedestal", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), BUILDING_PROPERTIES()));
    //endregion twisted rock

    //region runewood
    public static final RegistryObject<Item> HOLY_SAP = ITEMS.register("holy_sap", () -> new Item(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE)));
    public static final RegistryObject<Item> HOLY_SAPBALL = ITEMS.register("holy_sapball", () -> new Item(NATURE_PROPERTIES()));
    public static final RegistryObject<Item> HOLY_SYRUP = ITEMS.register("holy_syrup", () -> new HolySyrupItem(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE).food((new FoodProperties.Builder()).nutrition(8).saturationMod(2F).build())));

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

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_BUTTON = ITEMS.register("runewood_planks_button", () -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_BUTTON.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_PRESSURE_PLATE = ITEMS.register("runewood_planks_pressure_plate", () -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_PRESSURE_PLATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE = ITEMS.register("runewood_planks_fence", () -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_FENCE.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE_GATE = ITEMS.register("runewood_planks_fence_gate", () -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_FENCE_GATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_ITEM_STAND = ITEMS.register("runewood_item_stand", () -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_STAND.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_ITEM_PEDESTAL = ITEMS.register("runewood_item_pedestal", () -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_PEDESTAL.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_SIGN = ITEMS.register("runewood_sign", () -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.RUNEWOOD_SIGN.get(), BlockRegistry.RUNEWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> RUNEWOOD_BOAT = ITEMS.register("runewood_boat", () -> new MalumBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.RUNEWOOD_BOAT));
    //endregion

    //region soulwood
    public static final RegistryObject<Item> UNHOLY_SAP = ITEMS.register("unholy_sap", () -> new Item(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE)));
    public static final RegistryObject<Item> UNHOLY_SAPBALL = ITEMS.register("unholy_sapball", () -> new Item(NATURE_PROPERTIES()));
    public static final RegistryObject<Item> UNHOLY_SYRUP = ITEMS.register("unholy_syrup", () -> new UnholySyrupItem(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE).food((new FoodProperties.Builder()).nutrition(8).saturationMod(2F).build())));

    public static final RegistryObject<Item> SOULWOOD_LEAVES = ITEMS.register("soulwood_leaves", () -> new BlockItem(BlockRegistry.SOULWOOD_LEAVES.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_SAPLING = ITEMS.register("soulwood_sapling", () -> new BlockItem(BlockRegistry.SOULWOOD_SAPLING.get(), NATURE_PROPERTIES()));

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

    public static final RegistryObject<Item> SOULWOOD_PLANKS_BUTTON = ITEMS.register("soulwood_planks_button", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_BUTTON.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_PRESSURE_PLATE = ITEMS.register("soulwood_planks_pressure_plate", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_PRESSURE_PLATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_FENCE = ITEMS.register("soulwood_planks_fence", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_FENCE.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_FENCE_GATE = ITEMS.register("soulwood_planks_fence_gate", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_FENCE_GATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_ITEM_STAND = ITEMS.register("soulwood_item_stand", () -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_STAND.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_ITEM_PEDESTAL = ITEMS.register("soulwood_item_pedestal", () -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_PEDESTAL.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_SIGN = ITEMS.register("soulwood_sign", () -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.SOULWOOD_SIGN.get(), BlockRegistry.SOULWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> SOULWOOD_BOAT = ITEMS.register("soulwood_boat", () -> new MalumBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.SOULWOOD_BOAT));

    //endregion

    //region spirits
    public static final RegistryObject<Item> SACRED_SPIRIT = ITEMS.register("sacred_spirit", () -> new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.SACRED_SPIRIT));
    public static final RegistryObject<Item> WICKED_SPIRIT = ITEMS.register("wicked_spirit", () -> new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.WICKED_SPIRIT));
    public static final RegistryObject<Item> ARCANE_SPIRIT = ITEMS.register("arcane_spirit", () -> new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.ARCANE_SPIRIT));
    public static final RegistryObject<Item> ELDRITCH_SPIRIT = ITEMS.register("eldritch_spirit", () -> new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.ELDRITCH_SPIRIT));
    public static final RegistryObject<Item> EARTHEN_SPIRIT = ITEMS.register("earthen_spirit", () -> new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.EARTHEN_SPIRIT));
    public static final RegistryObject<Item> INFERNAL_SPIRIT = ITEMS.register("infernal_spirit", () -> new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final RegistryObject<Item> AERIAL_SPIRIT = ITEMS.register("aerial_spirit", () -> new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final RegistryObject<Item> AQUEOUS_SPIRIT = ITEMS.register("aqueous_spirit", () -> new MalumSpiritItem(SPLINTER_PROPERTIES(), SpiritTypeRegistry.AQUEOUS_SPIRIT));

    //endregion

    //region ores
    public static final RegistryObject<Item> COAL_FRAGMENT = ITEMS.register("coal_fragment", () -> new MalumFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 200));
    public static final RegistryObject<Item> CHARCOAL_FRAGMENT = ITEMS.register("charcoal_fragment", () -> new MalumFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 200));

    public static final RegistryObject<Item> ARCANE_CHARCOAL = ITEMS.register("arcane_charcoal", () -> new MalumFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 3200));
    public static final RegistryObject<Item> ARCANE_CHARCOAL_FRAGMENT = ITEMS.register("arcane_charcoal_fragment", () -> new MalumFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 400));
    public static final RegistryObject<Item> BLOCK_OF_ARCANE_CHARCOAL = ITEMS.register("block_of_arcane_charcoal", () -> new MalumFuelBlockItem(BlockRegistry.BLOCK_OF_ARCANE_CHARCOAL.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS), 28800));

    public static final RegistryObject<Item> BLAZING_QUARTZ_ORE = ITEMS.register("blazing_quartz_ore", () -> new BlockItem(BlockRegistry.BLAZING_QUARTZ_ORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> BLAZING_QUARTZ = ITEMS.register("blazing_quartz", () -> new MalumFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 1600));
    public static final RegistryObject<Item> BLAZING_QUARTZ_FRAGMENT = ITEMS.register("blazing_quartz_fragment", () -> new MalumFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 200));
    public static final RegistryObject<Item> BLOCK_OF_BLAZING_QUARTZ = ITEMS.register("block_of_blazing_quartz", () -> new MalumFuelBlockItem(BlockRegistry.BLOCK_OF_BLAZING_QUARTZ.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS), 14400));

    public static final RegistryObject<Item> BRILLIANT_STONE = ITEMS.register("brilliant_stone", () -> new BlockItem(BlockRegistry.BRILLIANT_STONE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> BRILLIANT_DEEPSLATE = ITEMS.register("brilliant_deepslate", () -> new BlockItem(BlockRegistry.BRILLIANT_DEEPSLATE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> BRILLIANCE_CLUSTER = ITEMS.register("brilliance_cluster", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BRILLIANCE_CHUNK = ITEMS.register("brilliance_chunk", () -> new BrillianceChunkItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).food((new FoodProperties.Builder()).fast().alwaysEat().build())));
    public static final RegistryObject<Item> BLOCK_OF_BRILLIANCE = ITEMS.register("block_of_brilliance", () -> new BlockItem(BlockRegistry.BLOCK_OF_BRILLIANCE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

    public static final RegistryObject<Item> SOULSTONE_ORE = ITEMS.register("soulstone_ore", () -> new BlockItem(BlockRegistry.SOULSTONE_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DEEPSLATE_SOULSTONE_ORE = ITEMS.register("deepslate_soulstone_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOULSTONE_CLUSTER = ITEMS.register("soulstone_cluster", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> PROCESSED_SOULSTONE = ITEMS.register("processed_soulstone", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_SOULSTONE = ITEMS.register("block_of_soulstone", () -> new BlockItem(BlockRegistry.BLOCK_OF_SOULSTONE.get(), DEFAULT_PROPERTIES()));
    //endregion

    //region crafting blocks
    public static final RegistryObject<Item> SPIRIT_ALTAR = ITEMS.register("spirit_altar", () -> new BlockItem(BlockRegistry.SPIRIT_ALTAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TOTEM_BASE = ITEMS.register("runewood_totem_base", () -> new BlockItem(BlockRegistry.RUNEWOOD_TOTEM_BASE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_TOTEM_BASE = ITEMS.register("soulwood_totem_base", () -> new BlockItem(BlockRegistry.SOULWOOD_TOTEM_BASE.get(), DEFAULT_PROPERTIES()));
    //endregion

    //region materials

    public static final RegistryObject<Item> HEX_ASH = ITEMS.register("hex_ash", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPIRIT_FABRIC = ITEMS.register("spirit_fabric", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPECTRAL_LENS = ITEMS.register("spectral_lens", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POPPET = ITEMS.register("poppet", () -> new Item(DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> HALLOWED_GOLD_INGOT = ITEMS.register("hallowed_gold_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_GOLD_NUGGET = ITEMS.register("hallowed_gold_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_HALLOWED_GOLD = ITEMS.register("block_of_hallowed_gold", () -> new BlockItem(BlockRegistry.BLOCK_OF_HALLOWED_GOLD.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_SPIRIT_RESONATOR = ITEMS.register("hallowed_spirit_resonator", () -> new Item(DEFAULT_PROPERTIES()));

    //region hallowed contraptions
    public static final RegistryObject<Item> SPIRIT_JAR = ITEMS.register("spirit_jar", () -> new BlockItem(BlockRegistry.SPIRIT_JAR.get(), DEFAULT_PROPERTIES()));

    //endregion
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_INGOT = ITEMS.register("soul_stained_steel_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_NUGGET = ITEMS.register("soul_stained_steel_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_SOUL_STAINED_STEEL = ITEMS.register("block_of_soul_stained_steel", () -> new BlockItem(BlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STAINED_SPIRIT_RESONATOR = ITEMS.register("stained_spirit_resonator", () -> new Item(DEFAULT_PROPERTIES()));

    //region soul stained contraptions

    //endregion
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
    public static final RegistryObject<Item> CRUDE_SCYTHE = ITEMS.register("crude_scythe", () -> new ScytheItem(Tiers.IRON, 0, 0.1f, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SCYTHE = ITEMS.register("soul_stained_steel_scythe", () -> new ScytheItem(SOUL_STAINED_STEEL_ITEM, 0.5f, 0.1f, 2, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SWORD = ITEMS.register("soul_stained_steel_sword", () -> new ModSwordItem(SOUL_STAINED_STEEL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_PICKAXE = ITEMS.register("soul_stained_steel_pickaxe", () -> new ModPickaxeItem(SOUL_STAINED_STEEL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_AXE = ITEMS.register("soul_stained_steel_axe", () -> new ModAxeItem(SOUL_STAINED_STEEL_ITEM, 0.5f, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SHOVEL = ITEMS.register("soul_stained_steel_shovel", () -> new ModShovelItem(SOUL_STAINED_STEEL_ITEM, 0, 0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_HOE = ITEMS.register("soul_stained_steel_hoe", () -> new ModHoeItem(SOUL_STAINED_STEEL_ITEM, 0, 0, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_HELMET = ITEMS.register("soul_stained_steel_helmet", () -> new SoulStainedSteelArmorItem(EquipmentSlot.HEAD, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_CHESTPLATE = ITEMS.register("soul_stained_steel_chestplate", () -> new SoulStainedSteelArmorItem(EquipmentSlot.CHEST, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_LEGGINGS = ITEMS.register("soul_stained_steel_leggings", () -> new SoulStainedSteelArmorItem(EquipmentSlot.LEGS, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_BOOTS = ITEMS.register("soul_stained_steel_boots", () -> new SoulStainedSteelArmorItem(EquipmentSlot.FEET, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_HUNTER_CLOAK = ITEMS.register("soul_hunter_cloak", () -> new SpiritHunterArmorItem(EquipmentSlot.HEAD, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_HUNTER_ROBE = ITEMS.register("soul_hunter_robe", () -> new SpiritHunterArmorItem(EquipmentSlot.CHEST, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_HUNTER_LEGGINGS = ITEMS.register("soul_hunter_leggings", () -> new SpiritHunterArmorItem(EquipmentSlot.LEGS, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_HUNTER_BOOTS = ITEMS.register("soul_hunter_boots", () -> new SpiritHunterArmorItem(EquipmentSlot.FEET, GEAR_PROPERTIES()));

//    public static final RegistryObject<Item> TYRVING = ITEMS.register("tyrving", () -> new TyrvingItem(TYRVING_ITEM, 2f, 0, -0.1f, () -> SoundRegistry.TYRVING_CRUSH, GEAR_PROPERTIES().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> GILDED_RING = ITEMS.register("gilded_ring", () -> new CurioGildedRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ORNATE_RING = ITEMS.register("ornate_ring", () -> new CurioOrnateRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ORNATE_NECKLACE = ITEMS.register("ornate_necklace", () -> new CurioOrnateNecklace(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> GILDED_BELT = ITEMS.register("gilded_belt", () -> new CurioGildedBelt(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> RING_OF_ARCANE_REACH = ITEMS.register("ring_of_arcane_reach", () -> new CurioArcaneReachRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_ARCANE_SPOIL = ITEMS.register("ring_of_arcane_spoil", () -> new CurioArcaneSpoilRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_PROWESS = ITEMS.register("ring_of_prowess", () -> new CurioRingOfProwess(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_CURATIVE_TALENT = ITEMS.register("ring_of_curative_talent", () -> new CurioCurativeRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_WICKED_INTENT = ITEMS.register("ring_of_wicked_intent", () -> new CurioWickedRing(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> NECKLACE_OF_THE_MYSTIC_MIRROR = ITEMS.register("necklace_of_the_mystic_mirror", () -> new CurioMirrorNecklace(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> NECKLACE_OF_THE_NARROW_EDGE = ITEMS.register("necklace_of_the_narrow_edge", () -> new CurioNarrowNecklace(GEAR_PROPERTIES()));

    //endregion

    //region hidden items
    public static final RegistryObject<Item> CREATIVE_SCYTHE = ITEMS.register("creative_scythe", () -> new ScytheItem(Tiers.IRON, 9993, 9.2f, 1000f, HIDDEN_PROPERTIES().defaultDurability(0)));
    public static final RegistryObject<Item> TOKEN_OF_GRATITUDE = ITEMS.register("token_of_gratitude", () -> new CurioTokenOfGratitude(HIDDEN_PROPERTIES()));
    //endregion

    //region vanity
    public static final RegistryObject<Item> FANCY_TOPHAT = ITEMS.register("fancy_tophat", () -> new DripArmorItem(EquipmentSlot.HEAD, HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> FANCY_JACKET = ITEMS.register("fancy_jacket", () -> new DripArmorItem(EquipmentSlot.CHEST, HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> FANCY_LEGGINGS = ITEMS.register("fancy_leggings", () -> new DripArmorItem(EquipmentSlot.LEGS, HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> FANCY_BOOTS = ITEMS.register("fancy_boots", () -> new DripArmorItem(EquipmentSlot.FEET, HIDDEN_PROPERTIES()));
    //endregion

    @Mod.EventBusSubscriber(modid = MalumMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientOnly {

        public static DripArmorModel DRIP_ARMOR;
        public static SpiritHunterArmorModel SPIRIT_HUNTER_ARMOR;
        public static SoulStainedSteelArmorModel SOUL_STAINED_ARMOR;
        public static TailModel TAIL_MODEL;

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(DripArmorModel.LAYER, DripArmorModel::createBodyLayer);
            event.registerLayerDefinition(SpiritHunterArmorModel.LAYER, SpiritHunterArmorModel::createBodyLayer);
            event.registerLayerDefinition(SoulStainedSteelArmorModel.LAYER, SoulStainedSteelArmorModel::createBodyLayer);
            event.registerLayerDefinition(TailModel.LAYER, TailModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.AddLayers event) {
            DRIP_ARMOR = new DripArmorModel(event.getEntityModels().bakeLayer(DripArmorModel.LAYER));
            SPIRIT_HUNTER_ARMOR = new SpiritHunterArmorModel(event.getEntityModels().bakeLayer(SpiritHunterArmorModel.LAYER));
            SOUL_STAINED_ARMOR = new SoulStainedSteelArmorModel(event.getEntityModels().bakeLayer(SoulStainedSteelArmorModel.LAYER));
            TAIL_MODEL = new TailModel(event.getEntityModels().bakeLayer(TailModel.LAYER));
        }

        @SubscribeEvent
        public static void setItemColors(ColorHandlerEvent.Item event) {
            ItemColors itemColors = event.getItemColors();
            Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());

            DataHelper.takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof MalumLeavesBlock).forEach(item ->
            {
                MalumLeavesBlock malumLeavesBlock = (MalumLeavesBlock) ((BlockItem) item.get()).getBlock();
                itemColors.register((s, c) -> ColorHelper.getColor(malumLeavesBlock.minColor), item.get());
                ClientHelper.registerItemColor(itemColors, item, malumLeavesBlock.minColor);
            });
            DataHelper.takeAll(items, i -> i.get() instanceof EtherTorchItem || i.get() instanceof EtherBrazierItem).forEach(i -> {
                itemColors.register((s, c) -> {
                    AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
                    if (c == 2) {
                        return etherItem.getSecondColor(s);
                    }
                    return c == 0 ? etherItem.getFirstColor(s) : -1;
                }, i.get());
            });
            DataHelper.takeAll(items, i -> i.get() instanceof EtherItem).forEach(i -> {
                itemColors.register((s, c) -> {
                    AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
                    if (c == 1) {
                        return etherItem.getSecondColor(s);
                    }
                    return c == 0 ? etherItem.getFirstColor(s) : -1;
                }, i.get());
            });
            ClientHelper.registerItemColor(itemColors, ItemRegistry.SACRED_SPIRIT, brighter(SACRED_SPIRIT_COLOR, 1));
            ClientHelper.registerItemColor(itemColors, ItemRegistry.WICKED_SPIRIT, WICKED_SPIRIT_COLOR);
            ClientHelper.registerItemColor(itemColors, ItemRegistry.ARCANE_SPIRIT, brighter(ARCANE_SPIRIT_COLOR, 1));
            ClientHelper.registerItemColor(itemColors, ItemRegistry.ELDRITCH_SPIRIT, darker(ELDRITCH_SPIRIT_COLOR, 1));
            ClientHelper.registerItemColor(itemColors, ItemRegistry.AERIAL_SPIRIT, brighter(AERIAL_SPIRIT_COLOR, 1));
            ClientHelper.registerItemColor(itemColors, ItemRegistry.AQUEOUS_SPIRIT, brighter(AQUEOUS_SPIRIT_COLOR, 1));
            ClientHelper.registerItemColor(itemColors, ItemRegistry.INFERNAL_SPIRIT, brighter(INFERNAL_SPIRIT_COLOR, 1));
            ClientHelper.registerItemColor(itemColors, ItemRegistry.EARTHEN_SPIRIT, brighter(EARTHEN_SPIRIT_COLOR, 1));
        }
    }
}