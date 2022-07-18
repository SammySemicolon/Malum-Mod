package com.sammy.malum.core.setup.content.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.client.model.*;
import com.sammy.malum.common.block.MalumLeavesBlock;
import com.sammy.malum.common.blockentity.FusionPlateBlockEntity;
import com.sammy.malum.common.blockentity.crucible.SpiritCatalyzerCoreBlockEntity;
import com.sammy.malum.common.blockentity.crucible.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.common.blockentity.obelisk.BrilliantObeliskBlockEntity;
import com.sammy.malum.common.blockentity.obelisk.RunewoodObeliskBlockEntity;
import com.sammy.malum.common.blockentity.storage.PlinthCoreBlockEntity;
import com.sammy.malum.common.entity.nitrate.EthericNitrateEntity;
import com.sammy.malum.common.entity.nitrate.VividNitrateEntity;
import com.sammy.malum.common.item.BrillianceChunkItem;
import com.sammy.malum.common.item.EncyclopediaArcanaItem;
import com.sammy.malum.common.item.EthericNitrateItem;
import com.sammy.malum.common.item.NodeItem;
import com.sammy.malum.common.item.equipment.CeaselessImpetusItem;
import com.sammy.malum.common.item.equipment.armor.SoulStainedSteelArmorItem;
import com.sammy.malum.common.item.equipment.armor.SoulHunterArmorItem;
import com.sammy.malum.common.item.equipment.armor.vanity.DripArmorItem;
import com.sammy.malum.common.item.equipment.curios.*;
import com.sammy.malum.common.item.ether.*;
import com.sammy.malum.common.item.food.HolyCaramelItem;
import com.sammy.malum.common.item.food.HolySyrupItem;
import com.sammy.malum.common.item.food.UnholySyrupItem;
import com.sammy.malum.common.item.impetus.CrackedImpetusItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.spirit.*;
import com.sammy.malum.common.item.tools.MalumScytheItem;
import com.sammy.malum.common.item.tools.magic.MagicScytheItem;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.compability.supplementaries.SupplementariesCompat;
import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.malum.core.setup.content.entity.EntityRegistry;
import com.sammy.malum.core.setup.content.item.tabs.*;
import com.sammy.ortus.helpers.ColorHelper;
import com.sammy.ortus.helpers.DataHelper;
import com.sammy.ortus.systems.item.OrtusBoatItem;
import com.sammy.ortus.systems.item.OrtusFuelBlockItem;
import com.sammy.ortus.systems.item.OrtusFuelItem;
import com.sammy.ortus.systems.item.tools.magic.*;
import com.sammy.ortus.systems.multiblock.MultiBlockItem;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumMod.MALUM;
import static com.sammy.malum.core.setup.content.item.ItemTiers.ItemTierEnum.SOUL_STAINED_STEEL;
import static com.sammy.ortus.helpers.ColorHelper.brighter;
import static com.sammy.ortus.helpers.ColorHelper.darker;
import static net.minecraft.world.item.Items.GLASS_BOTTLE;

@SuppressWarnings("unused")
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MALUM);

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new Item.Properties().tab(MalumCreativeTab.INSTANCE);
    }

    public static Item.Properties SPLINTER_PROPERTIES() {
        return new Item.Properties().tab(MalumSpiritTab.INSTANCE);
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
    public static Item.Properties IMPETUS_PROPERTIES() {
        return new Item.Properties().tab(MalumImpetusTab.INSTANCE).stacksTo(1);
    }

    public static Item.Properties NODE_PROPERTIES() {
        return new Item.Properties().tab(MalumImpetusTab.INSTANCE);
    }

    public static Item.Properties HIDDEN_PROPERTIES() {
        return new Item.Properties().stacksTo(1);
    }

    public static final RegistryObject<Item> ENCYCLOPEDIA_ARCANA = ITEMS.register("encyclopedia_arcana", () -> new EncyclopediaArcanaItem(GEAR_PROPERTIES().rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> BLAZING_TORCH = ITEMS.register("blazing_torch", () -> new StandingAndWallBlockItem(BlockRegistry.BLAZING_TORCH.get(), BlockRegistry.WALL_BLAZING_TORCH.get(), new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> BLAZING_SCONCE = ITEMS.register("blazing_sconce", () -> new StandingAndWallBlockItem(BlockRegistry.BLAZING_SCONCE.get(), BlockRegistry.WALL_BLAZING_SCONCE.get(), SupplementariesCompat.LOADED ? new Item.Properties().tab(CreativeModeTab.TAB_MISC) : HIDDEN_PROPERTIES()));

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

    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN = ITEMS.register("tainted_rock_column", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN_CAP = ITEMS.register("tainted_rock_column_cap", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_TAINTED_ROCK = ITEMS.register("cut_tainted_rock", () -> new BlockItem(BlockRegistry.CUT_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK = ITEMS.register("chiseled_tainted_rock", () -> new BlockItem(BlockRegistry.CHISELED_TAINTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = ITEMS.register("tainted_rock_pressure_plate", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BUTTON = ITEMS.register("tainted_rock_button", () -> new BlockItem(BlockRegistry.TAINTED_ROCK_BUTTON.get(), BUILDING_PROPERTIES()));

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

    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN = ITEMS.register("twisted_rock_column", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN_CAP = ITEMS.register("twisted_rock_column_cap", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN_CAP.get(), BUILDING_PROPERTIES()));

    public static final RegistryObject<Item> CUT_TWISTED_ROCK = ITEMS.register("cut_twisted_rock", () -> new BlockItem(BlockRegistry.CUT_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TWISTED_ROCK = ITEMS.register("chiseled_twisted_rock", () -> new BlockItem(BlockRegistry.CHISELED_TWISTED_ROCK.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_PRESSURE_PLATE = ITEMS.register("twisted_rock_pressure_plate", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), BUILDING_PROPERTIES()));
    public static final RegistryObject<Item> TWISTED_ROCK_BUTTON = ITEMS.register("twisted_rock_button", () -> new BlockItem(BlockRegistry.TWISTED_ROCK_BUTTON.get(), BUILDING_PROPERTIES()));

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
    public static final RegistryObject<Item> HOLY_SYRUP = ITEMS.register("holy_syrup", () -> new HolySyrupItem(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.1F).effect(()-> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1).build())));
    public static final RegistryObject<Item> HOLY_CARAMEL = ITEMS.register("holy_caramel", () -> new HolyCaramelItem(FarmersDelightCompat.LOADED ? NATURE_PROPERTIES().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.15F).effect(()-> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1).build()) : HIDDEN_PROPERTIES()));

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
    public static final RegistryObject<Item> RUNEWOOD_BOAT = ITEMS.register("runewood_boat", () -> new OrtusBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.RUNEWOOD_BOAT));
    //endregion

    //endregion
    public static final RegistryObject<Item> BLIGHTED_EARTH = ITEMS.register("blighted_earth", () -> new BlockItem(BlockRegistry.BLIGHTED_EARTH.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_SOIL = ITEMS.register("blighted_soil", () -> new BlockItem(BlockRegistry.BLIGHTED_SOIL.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_WEED = ITEMS.register("blighted_weed", () -> new BlockItem(BlockRegistry.BLIGHTED_WEED.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_COVERAGE = ITEMS.register("blighted_coverage", () -> new BlockItem(BlockRegistry.BLIGHTED_COVERAGE.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_SPIRE = ITEMS.register("blighted_spire", () -> new BlockItem(BlockRegistry.BLIGHTED_SPIRE.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> BLIGHTED_SOULWOOD = ITEMS.register("blighted_soulwood", () -> new BlockItem(BlockRegistry.BLIGHTED_SOULWOOD.get(), NATURE_PROPERTIES()));
    //region blight

    //region soulwood
    public static final RegistryObject<Item> UNHOLY_SAP = ITEMS.register("unholy_sap", () -> new Item(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE)));
    public static final RegistryObject<Item> UNHOLY_SAPBALL = ITEMS.register("unholy_sapball", () -> new Item(NATURE_PROPERTIES()));
    public static final RegistryObject<Item> UNHOLY_SYRUP = ITEMS.register("unholy_syrup", () -> new UnholySyrupItem(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.1F).effect(()-> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0), 1).build())));
    public static final RegistryObject<Item> UNHOLY_CARAMEL = ITEMS.register("unholy_caramel", () -> new HolyCaramelItem(FarmersDelightCompat.LOADED ? NATURE_PROPERTIES().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.15F).effect(()-> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0), 1).build()) : HIDDEN_PROPERTIES()));

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

    public static final RegistryObject<Item> SOULWOOD_PLANKS_BUTTON = ITEMS.register("soulwood_planks_button", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_BUTTON.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_PRESSURE_PLATE = ITEMS.register("soulwood_planks_pressure_plate", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_PRESSURE_PLATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_FENCE = ITEMS.register("soulwood_planks_fence", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_FENCE.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_FENCE_GATE = ITEMS.register("soulwood_planks_fence_gate", () -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_FENCE_GATE.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_ITEM_STAND = ITEMS.register("soulwood_item_stand", () -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_STAND.get(), NATURE_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_ITEM_PEDESTAL = ITEMS.register("soulwood_item_pedestal", () -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_PEDESTAL.get(), NATURE_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_SIGN = ITEMS.register("soulwood_sign", () -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.SOULWOOD_SIGN.get(), BlockRegistry.SOULWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> SOULWOOD_BOAT = ITEMS.register("soulwood_boat", () -> new OrtusBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.SOULWOOD_BOAT));
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
    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register("copper_nugget", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> COAL_FRAGMENT = ITEMS.register("coal_fragment", () -> new OrtusFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));
    public static final RegistryObject<Item> CHARCOAL_FRAGMENT = ITEMS.register("charcoal_fragment", () -> new OrtusFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 200));

    public static final RegistryObject<Item> ARCANE_CHARCOAL = ITEMS.register("arcane_charcoal", () -> new OrtusFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 3200));
    public static final RegistryObject<Item> ARCANE_CHARCOAL_FRAGMENT = ITEMS.register("arcane_charcoal_fragment", () -> new OrtusFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 400));
    public static final RegistryObject<Item> BLOCK_OF_ARCANE_CHARCOAL = ITEMS.register("block_of_arcane_charcoal", () -> new OrtusFuelBlockItem(BlockRegistry.BLOCK_OF_ARCANE_CHARCOAL.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS), 32000));

    public static final RegistryObject<Item> BLAZING_QUARTZ_ORE = ITEMS.register("blazing_quartz_ore", () -> new BlockItem(BlockRegistry.BLAZING_QUARTZ_ORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> BLAZING_QUARTZ = ITEMS.register("blazing_quartz", () -> new OrtusFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS), 1600));
    public static final RegistryObject<Item> BLAZING_QUARTZ_FRAGMENT = ITEMS.register("blazing_quartz_fragment", () -> new OrtusFuelItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC), 200));
    public static final RegistryObject<Item> BLOCK_OF_BLAZING_QUARTZ = ITEMS.register("block_of_blazing_quartz", () -> new OrtusFuelBlockItem(BlockRegistry.BLOCK_OF_BLAZING_QUARTZ.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS), 16000));

    public static final RegistryObject<Item> NATURAL_QUARTZ_ORE = ITEMS.register("natural_quartz_ore", () -> new BlockItem(BlockRegistry.NATURAL_QUARTZ_ORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> DEEPSLATE_QUARTZ_ORE = ITEMS.register("deepslate_quartz_ore", () -> new BlockItem(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> NATURAL_QUARTZ = ITEMS.register("natural_quartz", () -> new ItemNameBlockItem(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get(), new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));

    public static final RegistryObject<Item> BRILLIANT_STONE = ITEMS.register("brilliant_stone", () -> new BlockItem(BlockRegistry.BRILLIANT_STONE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> BRILLIANT_DEEPSLATE = ITEMS.register("brilliant_deepslate", () -> new BlockItem(BlockRegistry.BRILLIANT_DEEPSLATE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> CLUSTER_OF_BRILLIANCE = ITEMS.register("cluster_of_brilliance", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MATERIALS)));
    public static final RegistryObject<Item> CRUSHED_BRILLIANCE = ITEMS.register("crushed_brilliance", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CHUNK_OF_BRILLIANCE = ITEMS.register("chunk_of_brilliance", () -> new BrillianceChunkItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).food((new FoodProperties.Builder()).fast().alwaysEat().build())));
    public static final RegistryObject<Item> BLOCK_OF_BRILLIANCE = ITEMS.register("block_of_brilliance", () -> new BlockItem(BlockRegistry.BLOCK_OF_BRILLIANCE.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));

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
    public static final RegistryObject<Item> SPIRIT_JAR = ITEMS.register("spirit_jar", () -> new SpiritJarItem(BlockRegistry.SPIRIT_JAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_VIAL = ITEMS.register("soul_vial", () -> new SoulVialItem(BlockRegistry.SOUL_VIAL.get(), HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_OBELISK = ITEMS.register("runewood_obelisk", () -> new MultiBlockItem(BlockRegistry.RUNEWOOD_OBELISK.get(), DEFAULT_PROPERTIES(), RunewoodObeliskBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> BRILLIANT_OBELISK = ITEMS.register("brilliant_obelisk", () -> new MultiBlockItem(BlockRegistry.BRILLIANT_OBELISK.get(), DEFAULT_PROPERTIES(), BrilliantObeliskBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> SPIRIT_CRUCIBLE = ITEMS.register("spirit_crucible", () -> new MultiBlockItem(BlockRegistry.SPIRIT_CRUCIBLE.get(), DEFAULT_PROPERTIES(), SpiritCrucibleCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> TWISTED_TABLET = ITEMS.register("twisted_tablet", () -> new BlockItem(BlockRegistry.TWISTED_TABLET.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPIRIT_CATALYZER = ITEMS.register("spirit_catalyzer", () -> new MultiBlockItem(BlockRegistry.SPIRIT_CATALYZER.get(), DEFAULT_PROPERTIES(), SpiritCatalyzerCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> EMITTER_MIRROR = ITEMS.register("emitter_mirror", () -> new BlockItem(BlockRegistry.EMITTER_MIRROR.get(), HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_TOTEM_BASE = ITEMS.register("runewood_totem_base", () -> new BlockItem(BlockRegistry.RUNEWOOD_TOTEM_BASE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_TOTEM_BASE = ITEMS.register("soulwood_totem_base", () -> new BlockItem(BlockRegistry.SOULWOOD_TOTEM_BASE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOULWOOD_PLINTH = ITEMS.register("soulwood_plinth", () -> new MultiBlockItem(BlockRegistry.SOULWOOD_PLINTH.get(), HIDDEN_PROPERTIES(), PlinthCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> SOULWOOD_FUSION_PLATE = ITEMS.register("soulwood_fusion_plate", () -> new MultiBlockItem(BlockRegistry.SOULWOOD_FUSION_PLATE.get(), HIDDEN_PROPERTIES(), FusionPlateBlockEntity.STRUCTURE));
    //endregion

    //region materials
    public static final RegistryObject<Item> ROTTING_ESSENCE = ITEMS.register("rotting_essence", () -> new Item(DEFAULT_PROPERTIES().food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.2F).effect(()-> new MobEffectInstance(MobEffects.HUNGER, 600, 1), 0.95f).build())));
    public static final RegistryObject<Item> GRIM_TALC = ITEMS.register("grim_talc", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ALCHEMICAL_CALX = ITEMS.register("alchemical_calx", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ASTRAL_WEAVE = ITEMS.register("astral_weave", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> RARE_EARTHS = ITEMS.register("rare_earths", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HEX_ASH = ITEMS.register("hex_ash", () -> new Item(DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> BLOCK_OF_ROTTING_ESSENCE = ITEMS.register("block_of_rotting_essence", () -> new BlockItem(BlockRegistry.BLOCK_OF_ROTTING_ESSENCE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_GRIM_TALC = ITEMS.register("block_of_grim_talc", () -> new BlockItem(BlockRegistry.BLOCK_OF_GRIM_TALC.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_ALCHEMICAL_CALX = ITEMS.register("block_of_alchemical_calx", () -> new BlockItem(BlockRegistry.BLOCK_OF_ALCHEMICAL_CALX.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_ASTRAL_WEAVE = ITEMS.register("block_of_astral_weave", () -> new BlockItem(BlockRegistry.BLOCK_OF_ASTRAL_WEAVE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_RARE_EARTHS = ITEMS.register("block_of_rare_earths", () -> new ItemNameBlockItem(BlockRegistry.BLOCK_OF_RARE_EARTHS.get(), new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)));
    public static final RegistryObject<Item> BLOCK_OF_HEX_ASH = ITEMS.register("block_of_hex_ash", () -> new BlockItem(BlockRegistry.BLOCK_OF_HEX_ASH.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_CURSED_GRIT = ITEMS.register("block_of_cursed_grit", () -> new BlockItem(BlockRegistry.BLOCK_OF_CURSED_GRIT.get(), DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> SPIRIT_FABRIC = ITEMS.register("spirit_fabric", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SPECTRAL_LENS = ITEMS.register("spectral_lens", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POPPET = ITEMS.register("poppet", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CURSED_GRIT = ITEMS.register("cursed_grit", () -> new Item(DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> HALLOWED_GOLD_INGOT = ITEMS.register("hallowed_gold_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_GOLD_NUGGET = ITEMS.register("hallowed_gold_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_HALLOWED_GOLD = ITEMS.register("block_of_hallowed_gold", () -> new BlockItem(BlockRegistry.BLOCK_OF_HALLOWED_GOLD.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> HALLOWED_SPIRIT_RESONATOR = ITEMS.register("hallowed_spirit_resonator", () -> new Item(DEFAULT_PROPERTIES()));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_INGOT = ITEMS.register("soul_stained_steel_ingot", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_NUGGET = ITEMS.register("soul_stained_steel_nugget", () -> new Item(DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> BLOCK_OF_SOUL_STAINED_STEEL = ITEMS.register("block_of_soul_stained_steel", () -> new BlockItem(BlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STAINED_SPIRIT_RESONATOR = ITEMS.register("stained_spirit_resonator", () -> new Item(DEFAULT_PROPERTIES()));

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

    public static final RegistryObject<Item> ETHER_SCONCE = ITEMS.register("ether_sconce", () -> new EtherSconceItem(BlockRegistry.ETHER_SCONCE.get(), BlockRegistry.WALL_ETHER_SCONCE.get(), SupplementariesCompat.LOADED ? DEFAULT_PROPERTIES() : HIDDEN_PROPERTIES(), false));

    public static final RegistryObject<Item> IRIDESCENT_ETHER = ITEMS.register("iridescent_ether", () -> new EtherItem(BlockRegistry.IRIDESCENT_ETHER.get(), DEFAULT_PROPERTIES(), true));
    public static final RegistryObject<Item> IRIDESCENT_ETHER_TORCH = ITEMS.register("iridescent_ether_torch", () -> new EtherTorchItem(BlockRegistry.IRIDESCENT_ETHER_TORCH.get(), BlockRegistry.IRIDESCENT_WALL_ETHER_TORCH.get(), DEFAULT_PROPERTIES(), true));
    public static final RegistryObject<Item> TAINTED_IRIDESCENT_ETHER_BRAZIER = ITEMS.register("tainted_iridescent_ether_brazier", () -> new EtherBrazierItem(BlockRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), DEFAULT_PROPERTIES(), true));
    public static final RegistryObject<Item> TWISTED_IRIDESCENT_ETHER_BRAZIER = ITEMS.register("twisted_iridescent_ether_brazier", () -> new EtherBrazierItem(BlockRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), DEFAULT_PROPERTIES(), true));

    public static final RegistryObject<Item> IRIDESCENT_ETHER_SCONCE = ITEMS.register("iridescent_ether_sconce", () -> new EtherSconceItem(BlockRegistry.IRIDESCENT_ETHER_SCONCE.get(), BlockRegistry.IRIDESCENT_WALL_ETHER_SCONCE.get(), SupplementariesCompat.LOADED ? DEFAULT_PROPERTIES() : HIDDEN_PROPERTIES(), true));

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

    public static final RegistryObject<Item> ETHERIC_NITRATE = ITEMS.register("etheric_nitrate", () -> new EthericNitrateItem(DEFAULT_PROPERTIES(), p -> new EthericNitrateEntity(p, p.level)));
    public static final RegistryObject<Item> VIVID_NITRATE = ITEMS.register("vivid_nitrate", () -> new EthericNitrateItem(DEFAULT_PROPERTIES(), p -> new VividNitrateEntity(p, p.level)));

    public static final RegistryObject<Item> TYRVING = ITEMS.register("tyrving", () -> new TyrvingItem(ItemTiers.ItemTierEnum.TYRVING, 0, -0.3f, GEAR_PROPERTIES()));

    public static final RegistryObject<Item> GILDED_RING = ITEMS.register("gilded_ring", () -> new CurioGildedRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ORNATE_RING = ITEMS.register("ornate_ring", () -> new CurioOrnateRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> GILDED_BELT = ITEMS.register("gilded_belt", () -> new CurioGildedBelt(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> ORNATE_NECKLACE = ITEMS.register("ornate_necklace", () -> new CurioOrnateNecklace(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> RING_OF_THE_GUARDIAN = ITEMS.register("ring_of_the_guardian", () -> new CurioGuardianRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_ESOTERIC_SPOILS = ITEMS.register("ring_of_esoteric_spoils", () -> new CurioArcaneSpoilRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_CURATIVE_TALENT = ITEMS.register("ring_of_curative_talent", () -> new CurioCurativeRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_ARCANE_PROWESS = ITEMS.register("ring_of_arcane_prowess", () -> new CurioRingOfProwess(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_ALCHEMICAL_MASTERY = ITEMS.register("ring_of_alchemical_mastery", () -> new CurioAlchemicalRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_DESPERATE_VORACITY = ITEMS.register("ring_of_desperate_voracity", () -> new CurioVeraciousRing(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RING_OF_THE_BULWARK = ITEMS.register("ring_of_the_bulwark", () -> new CurioBulwarkRing(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> NECKLACE_OF_THE_MYSTIC_MIRROR = ITEMS.register("necklace_of_the_mystic_mirror", () -> new CurioMirrorNecklace(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> NECKLACE_OF_THE_NARROW_EDGE = ITEMS.register("necklace_of_the_narrow_edge", () -> new CurioNarrowNecklace(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> NECKLACE_OF_THE_HIDDEN_BLADE = ITEMS.register("necklace_of_the_hidden_blade", () -> new CurioHiddenBladeNecklace(GEAR_PROPERTIES()));

    public static final RegistryObject<Item> BELT_OF_THE_KEEPER = ITEMS.register("belt_of_the_keeper", () -> new CurioKeeperBelt(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> BELT_OF_THE_STARVED = ITEMS.register("belt_of_the_starved", () -> new CurioStarvedBelt(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> BELT_OF_THE_DELVER = ITEMS.register("belt_of_the_delver", () -> new CurioFortuneBelt(GEAR_PROPERTIES()));
    public static final RegistryObject<Item> BELT_OF_THE_MAGEBANE = ITEMS.register("belt_of_the_magebane", () -> new CurioMagebaneBelt(GEAR_PROPERTIES()));

    public static final RegistryObject<CrackedImpetusItem> CRACKED_CEASELESS_IMPETUS = ITEMS.register("cracked_ceaseless_impetus", () -> new CrackedImpetusItem(GEAR_PROPERTIES().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CEASELESS_IMPETUS = ITEMS.register("ceaseless_impetus", () -> new CeaselessImpetusItem(GEAR_PROPERTIES().durability(2).rarity(Rarity.UNCOMMON)).setCrackedVariant(CRACKED_CEASELESS_IMPETUS::get));

    //endregion

    //region hidden items
    public static final RegistryObject<Item> THE_DEVICE = ITEMS.register("the_device", () -> new BlockItem(BlockRegistry.THE_DEVICE.get(), HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> CREATIVE_SCYTHE = ITEMS.register("creative_scythe", () -> new MagicScytheItem(Tiers.IRON, 9993, 9.1f, 999f, HIDDEN_PROPERTIES().durability(-1)));
    public static final RegistryObject<Item> TOKEN_OF_GRATITUDE = ITEMS.register("token_of_gratitude", () -> new CurioTokenOfGratitude(HIDDEN_PROPERTIES()));
    //endregion

    //region vanity
    public static final RegistryObject<Item> FANCY_TOPHAT = ITEMS.register("fancy_tophat", () -> new DripArmorItem(EquipmentSlot.HEAD, HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> FANCY_JACKET = ITEMS.register("fancy_jacket", () -> new DripArmorItem(EquipmentSlot.CHEST, HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> FANCY_LEGGINGS = ITEMS.register("fancy_leggings", () -> new DripArmorItem(EquipmentSlot.LEGS, HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> FANCY_BOOTS = ITEMS.register("fancy_boots", () -> new DripArmorItem(EquipmentSlot.FEET, HIDDEN_PROPERTIES()));
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

        public static DripArmorModel DRIP_ARMOR;
        public static SpiritHunterArmorModel SPIRIT_HUNTER_ARMOR;
        public static SoulStainedSteelArmorModel SOUL_STAINED_ARMOR;
        public static TailModel TAIL_MODEL;
        public static HeadOverlayModel HEAD_OVERLAY_MODEL;
        public static ScarfModel SCARF;

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(DripArmorModel.LAYER, DripArmorModel::createBodyLayer);
            event.registerLayerDefinition(SpiritHunterArmorModel.LAYER, SpiritHunterArmorModel::createBodyLayer);
            event.registerLayerDefinition(SoulStainedSteelArmorModel.LAYER, SoulStainedSteelArmorModel::createBodyLayer);
            event.registerLayerDefinition(TailModel.LAYER, TailModel::createBodyLayer);
            event.registerLayerDefinition(HeadOverlayModel.LAYER, HeadOverlayModel::createBodyLayer);
            event.registerLayerDefinition(ScarfModel.LAYER, ScarfModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.AddLayers event) {
            DRIP_ARMOR = new DripArmorModel(event.getEntityModels().bakeLayer(DripArmorModel.LAYER));
            SPIRIT_HUNTER_ARMOR = new SpiritHunterArmorModel(event.getEntityModels().bakeLayer(SpiritHunterArmorModel.LAYER));
            SOUL_STAINED_ARMOR = new SoulStainedSteelArmorModel(event.getEntityModels().bakeLayer(SoulStainedSteelArmorModel.LAYER));
            TAIL_MODEL = new TailModel(event.getEntityModels().bakeLayer(TailModel.LAYER));
            HEAD_OVERLAY_MODEL = new HeadOverlayModel(event.getEntityModels().bakeLayer(HeadOverlayModel.LAYER));
            SCARF = new ScarfModel(event.getEntityModels().bakeLayer(ScarfModel.LAYER));
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
                if (c == 2) {
                    return etherItem.getSecondColor(s);
                }
                return c == 0 ? etherItem.getFirstColor(s) : -1;
            }, i.get()));
            DataHelper.takeAll(items, i -> i.get() instanceof EtherItem).forEach(i -> itemColors.register((s, c) -> {
                AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
                if (c == 1) {
                    return etherItem.getSecondColor(s);
                }
                return c == 0 ? etherItem.getFirstColor(s) : -1;
            }, i.get()));
            registerItemColor(itemColors, ItemRegistry.SACRED_SPIRIT, SpiritTypeRegistry.SACRED_SPIRIT.getColor());
            registerItemColor(itemColors, ItemRegistry.WICKED_SPIRIT, SpiritTypeRegistry.WICKED_SPIRIT.getColor());
            registerItemColor(itemColors, ItemRegistry.ARCANE_SPIRIT, brighter(SpiritTypeRegistry.ARCANE_SPIRIT.getColor(), 1));
            registerItemColor(itemColors, ItemRegistry.ELDRITCH_SPIRIT, darker(SpiritTypeRegistry.ELDRITCH_SPIRIT.getColor(), 1));
            registerItemColor(itemColors, ItemRegistry.AERIAL_SPIRIT, brighter(SpiritTypeRegistry.AERIAL_SPIRIT.getColor(), 1));
            registerItemColor(itemColors, ItemRegistry.AQUEOUS_SPIRIT, brighter(SpiritTypeRegistry.AQUEOUS_SPIRIT.getColor(), 1));
            registerItemColor(itemColors, ItemRegistry.INFERNAL_SPIRIT, brighter(SpiritTypeRegistry.INFERNAL_SPIRIT.getColor(), 1));
            registerItemColor(itemColors, ItemRegistry.EARTHEN_SPIRIT, brighter(SpiritTypeRegistry.EARTHEN_SPIRIT.getColor(), 1));
        }

        public static void registerItemColor(ItemColors itemColors, RegistryObject<Item> item, Color color) {
            int r = color.getRed();
            int g = color.getGreen();
            int b = color.getBlue();
            itemColors.register((stack, i) -> r << 16 | g << 8 | b, item.get());
        }
    }
}