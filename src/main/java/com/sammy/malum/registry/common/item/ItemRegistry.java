package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.curiosities.obelisk.brilliant.BrilliantObeliskBlockEntity;
import com.sammy.malum.common.block.curiosities.obelisk.runewood.RunewoodObeliskBlockEntity;
import com.sammy.malum.common.block.curiosities.repair_pylon.RepairPylonCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.spirit_crucible.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.common.block.curiosities.spirit_crucible.catalyzer.SpiritCatalyzerCoreBlockEntity;
import com.sammy.malum.common.block.nature.MalumLeavesBlock;
import com.sammy.malum.common.block.nature.iGradientedLeavesBlock;
import com.sammy.malum.common.item.augment.*;
import com.sammy.malum.common.item.augment.core.StellarMechanismItem;
import com.sammy.malum.common.item.codex.EncyclopediaArcanaItem;
import com.sammy.malum.common.item.codex.EncyclopediaEsotericaItem;
import com.sammy.malum.common.item.cosmetic.curios.CurioTokenOfGratitude;
import com.sammy.malum.common.item.cosmetic.curios.CurioTopHat;
import com.sammy.malum.common.item.cosmetic.skins.ArmorSkin;
import com.sammy.malum.common.item.cosmetic.weaves.GenericWeaveItem;
import com.sammy.malum.common.item.cosmetic.weaves.PrideweaveItem;
import com.sammy.malum.common.item.curiosities.SpiritPouchItem;
import com.sammy.malum.common.item.curiosities.armor.MalignantStrongholdArmorItem;
import com.sammy.malum.common.item.curiosities.armor.SoulHunterArmorItem;
import com.sammy.malum.common.item.curiosities.armor.SoulStainedSteelArmorItem;
import com.sammy.malum.common.item.curiosities.nitrate.EthericNitrateItem;
import com.sammy.malum.common.item.curiosities.nitrate.VividNitrateItem;
import com.sammy.malum.common.item.curiosities.tools.TotemicStaffItem;
import com.sammy.malum.common.item.curiosities.trinkets.TrinketsGildedBelt;
import com.sammy.malum.common.item.curiosities.trinkets.TrinketsGildedRing;
import com.sammy.malum.common.item.curiosities.trinkets.TrinketsOrnateNecklace;
import com.sammy.malum.common.item.curiosities.trinkets.TrinketsOrnateRing;
import com.sammy.malum.common.item.curiosities.trinkets.brooches.CurioElaborateBrooch;
import com.sammy.malum.common.item.curiosities.trinkets.brooches.CurioGlassBrooch;
import com.sammy.malum.common.item.curiosities.trinkets.brooches.CurioGluttonousBrooch;
import com.sammy.malum.common.item.curiosities.trinkets.brooches.CurioRunicBrooch;
import com.sammy.malum.common.item.curiosities.trinkets.runes.AbstractRuneTrinketsItem;
import com.sammy.malum.common.item.curiosities.trinkets.runes.TotemicRuneTrinketsItem;
import com.sammy.malum.common.item.curiosities.trinkets.runes.madness.*;
import com.sammy.malum.common.item.curiosities.trinkets.runes.miracle.*;
import com.sammy.malum.common.item.curiosities.trinkets.sets.alchemical.TrinketsAlchemicalRing;
import com.sammy.malum.common.item.curiosities.trinkets.sets.alchemical.TrinketsCurativeRing;
import com.sammy.malum.common.item.curiosities.trinkets.sets.alchemical.TrinketsRingOfProwess;
import com.sammy.malum.common.item.curiosities.trinkets.sets.misc.TrinketsHarmonyNecklace;
import com.sammy.malum.common.item.curiosities.trinkets.sets.misc.TrinketsNarrowNecklace;
import com.sammy.malum.common.item.curiosities.trinkets.sets.misc.TrinketsWaterNecklace;
import com.sammy.malum.common.item.curiosities.trinkets.sets.prospector.TrinketsDemolitionistRing;
import com.sammy.malum.common.item.curiosities.trinkets.sets.prospector.TrinketsHoarderRing;
import com.sammy.malum.common.item.curiosities.trinkets.sets.prospector.TrinketsProspectorBelt;
import com.sammy.malum.common.item.curiosities.trinkets.sets.rotten.TrinketsStarvedBelt;
import com.sammy.malum.common.item.curiosities.trinkets.sets.rotten.TrinketsVoraciousRing;
import com.sammy.malum.common.item.curiosities.trinkets.sets.soulward.TrinketsMagebaneBelt;
import com.sammy.malum.common.item.curiosities.trinkets.sets.spirit.TrinketsArcaneSpoilRing;
import com.sammy.malum.common.item.curiosities.trinkets.sets.spirit.TrinketsMirrorNecklace;
import com.sammy.malum.common.item.curiosities.trinkets.sets.weeping.*;
import com.sammy.malum.common.item.curiosities.weapons.TyrvingItem;
import com.sammy.malum.common.item.curiosities.weapons.WeightOfWorldsItem;
import com.sammy.malum.common.item.curiosities.weapons.scythe.MagicScytheItem;
import com.sammy.malum.common.item.curiosities.weapons.scythe.MalumScytheItem;
import com.sammy.malum.common.item.curiosities.weapons.staff.AuricFlameStaffItem;
import com.sammy.malum.common.item.curiosities.weapons.staff.ErosionScepterItem;
import com.sammy.malum.common.item.curiosities.weapons.staff.HexStaffItem;
import com.sammy.malum.common.item.ether.AbstractEtherItem;
import com.sammy.malum.common.item.ether.EtherBrazierItem;
import com.sammy.malum.common.item.ether.EtherItem;
import com.sammy.malum.common.item.ether.EtherTorchItem;
import com.sammy.malum.common.item.food.DrinkableSapItem;
import com.sammy.malum.common.item.impetus.CrackedImpetusItem;
import com.sammy.malum.common.item.impetus.ImpetusItem;
import com.sammy.malum.common.item.impetus.NodeItem;
import com.sammy.malum.common.item.misc.*;
import com.sammy.malum.common.item.spirit.RitualShardItem;
import com.sammy.malum.common.item.spirit.SpiritJarItem;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.common.item.spirit.UmbralSpiritShardItem;
import com.sammy.malum.compability.farmersdelight.FarmersDelightCompat;
import com.sammy.malum.core.systems.ritual.MalumRitualTier;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.SpiritRiteRegistry;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.registry.common.item.tabs.CreativeTabRegistry;
import io.github.fabricators_of_create.porting_lib.util.LazyRegistrar;
import io.github.fabricators_of_create.porting_lib.util.RegistryObject;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.ComposterBlock;
import team.lodestar.lodestone.helpers.ColorHelper;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;
import team.lodestar.lodestone.systems.item.LodestoneBoatItem;
import team.lodestar.lodestone.systems.item.LodestoneFuelBlockItem;
import team.lodestar.lodestone.systems.item.LodestoneFuelItem;
import team.lodestar.lodestone.systems.item.tools.magic.*;
import team.lodestar.lodestone.systems.multiblock.MultiBlockItem;

import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.sammy.malum.MalumMod.MALUM;
import static com.sammy.malum.registry.common.item.ItemTiers.ItemTierEnum.*;
import static net.minecraft.world.item.Rarity.*;

@SuppressWarnings("unused")
public class ItemRegistry {
    public static final LazyRegistrar<Item> ITEMS = LazyRegistrar.create(BuiltInRegistries.ITEM, MALUM);

    public static final Map<ResourceKey<CreativeModeTab>, List<ResourceLocation>> TAB_SORTING = new HashMap<>();

    public static class LodestoneItemProperties extends Item.Properties {
        public final ResourceKey<CreativeModeTab> tab;

        public LodestoneItemProperties(RegistryObject<CreativeModeTab> registryObject) {
            this(registryObject.getKey());
        }

        public LodestoneItemProperties(ResourceKey<CreativeModeTab> tab) {
            this.tab = tab;
        }
    }

    public static final List<Item> CONTENT = new ArrayList<>();

    public static Item.Properties DEFAULT_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.CONTENT);
    }

    public static Item.Properties GEAR_PROPERTIES() {
        return DEFAULT_PROPERTIES().stacksTo(1);
    }

    public static Item.Properties BUILDING_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.BUILDING);
    }

    public static Item.Properties NATURE_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.NATURE);
    }

    public static Item.Properties METALLURGIC_NODE_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.METALLURGY);
    }

    public static Item.Properties METALLURGIC_PROPERTIES() {
        return METALLURGIC_NODE_PROPERTIES().stacksTo(1);
    }

    public static Item.Properties COSMETIC_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.COSMETIC);
    }

    public static Item.Properties HIDDEN_PROPERTIES() {
        return new Item.Properties().stacksTo(1);
    }

    public static <T extends Item> RegistryObject<T> register(String name, Item.Properties properties, Function<Item.Properties, T> function) {
        if (properties instanceof LodestoneItemProperties lodestoneItemProperties) {
            TAB_SORTING.computeIfAbsent(lodestoneItemProperties.tab, (key) -> new ArrayList<>()).add(MalumMod.malumPath(name));
        }
        return ITEMS.register(name, () -> function.apply(properties));
    }

    public static final RegistryObject<Item> ENCYCLOPEDIA_ARCANA = register("encyclopedia_arcana", GEAR_PROPERTIES().rarity(UNCOMMON), EncyclopediaArcanaItem::new);

    public static final RegistryObject<RitualShardItem> RITUAL_SHARD = register("ritual_shard", HIDDEN_PROPERTIES(), RitualShardItem::new);

    //region spirits
    public static final RegistryObject<SpiritShardItem> SACRED_SPIRIT = register("sacred_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.SACRED_SPIRIT));
    public static final RegistryObject<SpiritShardItem> WICKED_SPIRIT = register("wicked_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.WICKED_SPIRIT));
    public static final RegistryObject<SpiritShardItem> ARCANE_SPIRIT = register("arcane_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.ARCANE_SPIRIT));
    public static final RegistryObject<SpiritShardItem> ELDRITCH_SPIRIT = register("eldritch_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.ELDRITCH_SPIRIT));
    public static final RegistryObject<SpiritShardItem> AERIAL_SPIRIT = register("aerial_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final RegistryObject<SpiritShardItem> AQUEOUS_SPIRIT = register("aqueous_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.AQUEOUS_SPIRIT));
    public static final RegistryObject<SpiritShardItem> EARTHEN_SPIRIT = register("earthen_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.EARTHEN_SPIRIT));
    public static final RegistryObject<SpiritShardItem> INFERNAL_SPIRIT = register("infernal_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final RegistryObject<SpiritShardItem> UMBRAL_SPIRIT = register("umbral_spirit", HIDDEN_PROPERTIES(), (p) -> new UmbralSpiritShardItem(p, SpiritTypeRegistry.UMBRAL_SPIRIT));
    //endregion

    public static final RegistryObject<Item> ENCYCLOPEDIA_ESOTERICA = register("encyclopedia_esoterica", GEAR_PROPERTIES().rarity(EPIC), EncyclopediaEsotericaItem::new);

    //region random stuff
    public static final RegistryObject<Item> COPPER_NUGGET = register("copper_nugget", new LodestoneItemProperties(CreativeModeTabs.INGREDIENTS), Item::new);
    public static final RegistryObject<Item> COAL_FRAGMENT = register("coal_fragment", new LodestoneItemProperties(CreativeModeTabs.INGREDIENTS), (p) -> new LodestoneFuelItem(p, 200));
    public static final RegistryObject<Item> CHARCOAL_FRAGMENT = register("charcoal_fragment", new LodestoneItemProperties(CreativeModeTabs.INGREDIENTS), (p) -> new LodestoneFuelItem(p, 200));
    //endregion

    //region tainted rock
    public static final RegistryObject<Item> TAINTED_ROCK = register("tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK.get(), p));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK = register("smooth_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK.get(), p));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK = register("polished_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS = register("tainted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES = register("tainted_rock_tiles", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES.get(), p));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS = register("small_tainted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS.get(), p));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_BRICKS = register("runic_tainted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS.get(), p));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_TILES = register("runic_tainted_rock_tiles", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES.get(), p));
    public static final RegistryObject<Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS = register("runic_small_tainted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS.get(), p));

    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN = register("tainted_rock_column", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_COLUMN_CAP = register("tainted_rock_column_cap", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_COLUMN_CAP.get(), p));

    public static final RegistryObject<Item> CUT_TAINTED_ROCK = register("cut_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_TAINTED_ROCK.get(), p));
    public static final RegistryObject<Item> CHECKERED_TAINTED_ROCK = register("checkered_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHECKERED_TAINTED_ROCK.get(), p));
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK = register("chiseled_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHISELED_TAINTED_ROCK.get(), p));

    public static final RegistryObject<Item> TAINTED_ROCK_WALL = register("tainted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_WALL.get(), p));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_WALL = register("smooth_tainted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_WALL.get(), p));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_WALL = register("polished_tainted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_WALL.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_WALL = register("tainted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_WALL.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_WALL = register("tainted_rock_tiles_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_WALL.get(), p));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_WALL = register("small_tainted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(), p));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_BRICKS_WALL = register("runic_tainted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_WALL.get(), p));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_TILES_WALL = register("runic_tainted_rock_tiles_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_WALL.get(), p));
    public static final RegistryObject<Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL = register("runic_small_tainted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_WALL.get(), p));

    public static final RegistryObject<Item> TAINTED_ROCK_SLAB = register("tainted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_SLAB.get(), p));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_SLAB = register("smooth_tainted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), p));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_SLAB = register("polished_tainted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_SLAB = register("tainted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_SLAB = register("tainted_rock_tiles_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_SLAB.get(), p));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_SLAB = register("small_tainted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), p));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_BRICKS_SLAB = register("runic_tainted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_SLAB.get(), p));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_TILES_SLAB = register("runic_tainted_rock_tiles_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_SLAB.get(), p));
    public static final RegistryObject<Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB = register("runic_small_tainted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), p));

    public static final RegistryObject<Item> TAINTED_ROCK_STAIRS = register("tainted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_STAIRS.get(), p));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_STAIRS = register("smooth_tainted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), p));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_STAIRS = register("polished_tainted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_STAIRS = register("tainted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_TILES_STAIRS = register("tainted_rock_tiles_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_TILES_STAIRS.get(), p));
    public static final RegistryObject<Item> SMALL_TAINTED_ROCK_BRICKS_STAIRS = register("small_tainted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_BRICKS_STAIRS = register("runic_tainted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNIC_TAINTED_ROCK_TILES_STAIRS = register("runic_tainted_rock_tiles_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TAINTED_ROCK_TILES_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS = register("runic_small_tainted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), p));

    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = register("tainted_rock_pressure_plate", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_BUTTON = register("tainted_rock_button", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BUTTON.get(), p));

    public static final RegistryObject<Item> TAINTED_ROCK_ITEM_STAND = register("tainted_rock_item_stand", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_ITEM_STAND.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_ITEM_PEDESTAL = register("tainted_rock_item_pedestal", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get(), p));
    //endregion

    //region twisted rock
    public static final RegistryObject<Item> TWISTED_ROCK = register("twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK.get(), p));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK = register("smooth_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK.get(), p));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK = register("polished_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS = register("twisted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES = register("twisted_rock_tiles", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES.get(), p));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS = register("small_twisted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS.get(), p));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_BRICKS = register("runic_twisted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS.get(), p));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_TILES = register("runic_twisted_rock_tiles", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES.get(), p));
    public static final RegistryObject<Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS = register("runic_small_twisted_rock_bricks", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS.get(), p));

    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN = register("twisted_rock_column", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_COLUMN_CAP = register("twisted_rock_column_cap", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_COLUMN_CAP.get(), p));

    public static final RegistryObject<Item> CUT_TWISTED_ROCK = register("cut_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_TWISTED_ROCK.get(), p));
    public static final RegistryObject<Item> CHECKERED_TWISTED_ROCK = register("checkered_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHECKERED_TWISTED_ROCK.get(), p));
    public static final RegistryObject<Item> CHISELED_TWISTED_ROCK = register("chiseled_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHISELED_TWISTED_ROCK.get(), p));

    public static final RegistryObject<Item> TWISTED_ROCK_WALL = register("twisted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_WALL.get(), p));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_WALL = register("smooth_twisted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_WALL.get(), p));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_WALL = register("polished_twisted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_WALL.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_WALL = register("twisted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_WALL.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_WALL = register("twisted_rock_tiles_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_WALL.get(), p));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_WALL = register("small_twisted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(), p));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_BRICKS_WALL = register("runic_twisted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_WALL.get(), p));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_TILES_WALL = register("runic_twisted_rock_tiles_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_WALL.get(), p));
    public static final RegistryObject<Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL = register("runic_small_twisted_rock_bricks_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_WALL.get(), p));

    public static final RegistryObject<Item> TWISTED_ROCK_SLAB = register("twisted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_SLAB.get(), p));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_SLAB = register("smooth_twisted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), p));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_SLAB = register("polished_twisted_rock_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_SLAB = register("twisted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_SLAB = register("twisted_rock_tiles_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_SLAB.get(), p));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_SLAB = register("small_twisted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), p));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_BRICKS_SLAB = register("runic_twisted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_SLAB.get(), p));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_TILES_SLAB = register("runic_twisted_rock_tiles_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_SLAB.get(), p));
    public static final RegistryObject<Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB = register("runic_small_twisted_rock_bricks_slab", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), p));

    public static final RegistryObject<Item> TWISTED_ROCK_STAIRS = register("twisted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_STAIRS.get(), p));
    public static final RegistryObject<Item> SMOOTH_TWISTED_ROCK_STAIRS = register("smooth_twisted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), p));
    public static final RegistryObject<Item> POLISHED_TWISTED_ROCK_STAIRS = register("polished_twisted_rock_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_BRICKS_STAIRS = register("twisted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_TILES_STAIRS = register("twisted_rock_tiles_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_TILES_STAIRS.get(), p));
    public static final RegistryObject<Item> SMALL_TWISTED_ROCK_BRICKS_STAIRS = register("small_twisted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_BRICKS_STAIRS = register("runic_twisted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_BRICKS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNIC_TWISTED_ROCK_TILES_STAIRS = register("runic_twisted_rock_tiles_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_TWISTED_ROCK_TILES_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS = register("runic_small_twisted_rock_bricks_stairs", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), p));

    public static final RegistryObject<Item> TWISTED_ROCK_PRESSURE_PLATE = register("twisted_rock_pressure_plate", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_BUTTON = register("twisted_rock_button", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BUTTON.get(), p));

    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_STAND = register("twisted_rock_item_stand", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_STAND.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_PEDESTAL = register("twisted_rock_item_pedestal", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), p));
    //endregion twisted rock

    //region runewood
    public static final RegistryObject<Item> RUNIC_SAP = register("runic_sap", NATURE_PROPERTIES(), (p) -> new DrinkableSapItem(NATURE_PROPERTIES().food(FoodPropertyRegistry.RUNIC_SAP)));
    public static final RegistryObject<Item> RUNIC_SAPBALL = register("runic_sapball", NATURE_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> RUNIC_SAP_BLOCK = register("runic_sap_block", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_SAP_BLOCK.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_LEAVES = register("runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> HANGING_RUNEWOOD_LEAVES = register("hanging_runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.HANGING_RUNEWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_SAPLING = register("runewood_sapling", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_SAPLING.get(), p));

    public static final RegistryObject<Item> AZURE_RUNEWOOD_LEAVES = register("azure_runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.AZURE_RUNEWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> HANGING_AZURE_RUNEWOOD_LEAVES = register("hanging_azure_runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.HANGING_AZURE_RUNEWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> AZURE_RUNEWOOD_SAPLING = register("azure_runewood_sapling", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.AZURE_RUNEWOOD_SAPLING.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_LOG = register("runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_LOG.get(), p));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD_LOG = register("stripped_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_RUNEWOOD_LOG.get(), p));
    public static final RegistryObject<Item> RUNEWOOD = register("runewood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD.get(), p));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD = register("stripped_runewood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_RUNEWOOD.get(), p));

    public static final RegistryObject<Item> EXPOSED_RUNEWOOD_LOG = register("exposed_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.EXPOSED_RUNEWOOD_LOG.get(), p));
    public static final RegistryObject<Item> REVEALED_RUNEWOOD_LOG = register("revealed_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.REVEALED_RUNEWOOD_LOG.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_BOARDS = register("runewood_boards", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BOARDS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_BOARDS = register("vertical_runewood_boards", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_BOARDS.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS = register("runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS = register("vertical_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_TILES = register("runewood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES.get(), p));
    public static final RegistryObject<Item> RUSTIC_RUNEWOOD_PLANKS = register("rustic_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUSTIC_RUNEWOOD_PLANKS = register("vertical_rustic_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> RUSTIC_RUNEWOOD_TILES = register("rustic_runewood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_TILES.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_PANEL = register("runewood_panel", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PANEL.get(), p));
    public static final RegistryObject<Item> CUT_RUNEWOOD_PLANKS = register("cut_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_RUNEWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_BEAM = register("runewood_beam", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BEAM.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_BOARDS_SLAB = register("runewood_boards_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BOARDS_SLAB.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_BOARDS_SLAB = register("vertical_runewood_boards_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_BOARDS_SLAB.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_SLAB = register("runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_SLAB = register("vertical_runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_TILES_SLAB = register("runewood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES_SLAB.get(), p));
    public static final RegistryObject<Item> RUSTIC_RUNEWOOD_PLANKS_SLAB = register("rustic_runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB = register("vertical_rustic_runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> RUSTIC_RUNEWOOD_TILES_SLAB = register("rustic_runewood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_TILES_SLAB.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_BOARDS_STAIRS = register("runewood_boards_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BOARDS_STAIRS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_BOARDS_STAIRS = register("vertical_runewood_boards_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_BOARDS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_STAIRS = register("runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_STAIRS = register("vertical_runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_TILES_STAIRS = register("runewood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES_STAIRS.get(), p));
    public static final RegistryObject<Item> RUSTIC_RUNEWOOD_PLANKS_STAIRS = register("rustic_runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS = register("vertical_rustic_runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUSTIC_RUNEWOOD_TILES_STAIRS = register("rustic_runewood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_RUNEWOOD_TILES_STAIRS.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_DOOR = register("runewood_door", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_DOOR.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_TRAPDOOR = register("runewood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TRAPDOOR.get(), p));
    public static final RegistryObject<Item> SOLID_RUNEWOOD_TRAPDOOR = register("solid_runewood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOLID_RUNEWOOD_TRAPDOOR.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_BUTTON = register("runewood_planks_button", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BUTTON.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_PRESSURE_PLATE = register("runewood_planks_pressure_plate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PRESSURE_PLATE.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_FENCE = register("runewood_planks_fence", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_FENCE.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_FENCE_GATE = register("runewood_planks_fence_gate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_FENCE_GATE.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_ITEM_STAND = register("runewood_item_stand", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_STAND.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_ITEM_PEDESTAL = register("runewood_item_pedestal", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_PEDESTAL.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_SIGN = register("runewood_sign", NATURE_PROPERTIES(), (p) -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.RUNEWOOD_SIGN.get(), BlockRegistry.RUNEWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> RUNEWOOD_BOAT = register("runewood_boat", NATURE_PROPERTIES(), (p) -> new LodestoneBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.RUNEWOOD_BOAT));
    //endregion

    //region blight
    public static final RegistryObject<Item> BLIGHTED_EARTH = register("blighted_earth", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_EARTH.get(), p));
    public static final RegistryObject<Item> BLIGHTED_SOIL = register("blighted_soil", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_SOIL.get(), p));
    //endregion

    //region soulwood
    public static final RegistryObject<Item> CURSED_SAP = register("cursed_sap", NATURE_PROPERTIES(), (p) -> new DrinkableSapItem(NATURE_PROPERTIES().food(FoodPropertyRegistry.CURSED_SAP)));
    public static final RegistryObject<Item> CURSED_SAPBALL = register("cursed_sapball", NATURE_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CURSED_SAP_BLOCK = register("cursed_sap_block", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CURSED_SAP_BLOCK.get(), p));

    public static final RegistryObject<Item> SOULWOOD_LEAVES = register("soulwood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> BUDDING_SOULWOOD_LEAVES = register("budding_soulwood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BUDDING_SOULWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> HANGING_SOULWOOD_LEAVES = register("hanging_soulwood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.HANGING_SOULWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> SOULWOOD_GROWTH = register("soulwood_growth", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_GROWTH.get(), p));

    public static final RegistryObject<Item> BLIGHTED_SOULWOOD = register("blighted_soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_SOULWOOD.get(), p));
    public static final RegistryObject<Item> SOULWOOD_LOG = register("soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_LOG.get(), p));
    public static final RegistryObject<Item> STRIPPED_SOULWOOD_LOG = register("stripped_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_SOULWOOD_LOG.get(), p));
    public static final RegistryObject<Item> SOULWOOD = register("soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD.get(), p));
    public static final RegistryObject<Item> STRIPPED_SOULWOOD = register("stripped_soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_SOULWOOD.get(), p));

    public static final RegistryObject<Item> EXPOSED_SOULWOOD_LOG = register("exposed_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.EXPOSED_SOULWOOD_LOG.get(), p));
    public static final RegistryObject<Item> REVEALED_SOULWOOD_LOG = register("revealed_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.REVEALED_SOULWOOD_LOG.get(), p));

    public static final RegistryObject<Item> SOULWOOD_BOARDS = register("soulwood_boards", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BOARDS.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_BOARDS = register("vertical_soulwood_boards", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_BOARDS.get(), p));

    public static final RegistryObject<Item> SOULWOOD_PLANKS = register("soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS = register("vertical_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TILES = register("soulwood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES.get(), p));
    public static final RegistryObject<Item> RUSTIC_SOULWOOD_PLANKS = register("rustic_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUSTIC_SOULWOOD_PLANKS = register("vertical_rustic_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> RUSTIC_SOULWOOD_TILES = register("rustic_soulwood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_TILES.get(), p));

    public static final RegistryObject<Item> SOULWOOD_PANEL = register("soulwood_panel", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PANEL.get(), p));
    public static final RegistryObject<Item> CUT_SOULWOOD_PLANKS = register("cut_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_SOULWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> SOULWOOD_BEAM = register("soulwood_beam", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BEAM.get(), p));

    public static final RegistryObject<Item> SOULWOOD_BOARDS_SLAB = register("soulwood_boards_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BOARDS_SLAB.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_BOARDS_SLAB = register("vertical_soulwood_boards_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_BOARDS_SLAB.get(), p));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_SLAB = register("soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS_SLAB = register("vertical_soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TILES_SLAB = register("soulwood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES_SLAB.get(), p));
    public static final RegistryObject<Item> RUSTIC_SOULWOOD_PLANKS_SLAB = register("rustic_soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB = register("vertical_rustic_soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> RUSTIC_SOULWOOD_TILES_SLAB = register("rustic_soulwood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_TILES_SLAB.get(), p));

    public static final RegistryObject<Item> SOULWOOD_BOARDS_STAIRS = register("soulwood_boards_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BOARDS_STAIRS.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_BOARDS_STAIRS = register("vertical_soulwood_boards_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_BOARDS_STAIRS.get(), p));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_STAIRS = register("soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS_STAIRS = register("vertical_soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TILES_STAIRS = register("soulwood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES_STAIRS.get(), p));
    public static final RegistryObject<Item> RUSTIC_SOULWOOD_PLANKS_STAIRS = register("rustic_soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS = register("vertical_rustic_soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUSTIC_SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUSTIC_SOULWOOD_TILES_STAIRS = register("rustic_soulwood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUSTIC_SOULWOOD_TILES_STAIRS.get(), p));

    public static final RegistryObject<Item> SOULWOOD_DOOR = register("soulwood_door", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_DOOR.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TRAPDOOR = register("soulwood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TRAPDOOR.get(), p));
    public static final RegistryObject<Item> SOLID_SOULWOOD_TRAPDOOR = register("solid_soulwood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOLID_SOULWOOD_TRAPDOOR.get(), p));

    public static final RegistryObject<Item> SOULWOOD_BUTTON = register("soulwood_planks_button", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BUTTON.get(), p));
    public static final RegistryObject<Item> SOULWOOD_PRESSURE_PLATE = register("soulwood_planks_pressure_plate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PRESSURE_PLATE.get(), p));

    public static final RegistryObject<Item> SOULWOOD_FENCE = register("soulwood_planks_fence", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_FENCE.get(), p));
    public static final RegistryObject<Item> SOULWOOD_FENCE_GATE = register("soulwood_planks_fence_gate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_FENCE_GATE.get(), p));

    public static final RegistryObject<Item> SOULWOOD_ITEM_STAND = register("soulwood_item_stand", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_STAND.get(), p));
    public static final RegistryObject<Item> SOULWOOD_ITEM_PEDESTAL = register("soulwood_item_pedestal", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_PEDESTAL.get(), p));

    public static final RegistryObject<Item> SOULWOOD_SIGN = register("soulwood_sign", NATURE_PROPERTIES(), (p) -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.SOULWOOD_SIGN.get(), BlockRegistry.SOULWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> SOULWOOD_BOAT = register("soulwood_boat", NATURE_PROPERTIES(), (p) -> new LodestoneBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.SOULWOOD_BOAT));
    //endregion

    //region ores
    public static final RegistryObject<Item> BLOCK_OF_SOULSTONE = register("block_of_soulstone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_SOULSTONE.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_RAW_SOULSTONE = register("block_of_raw_soulstone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_RAW_SOULSTONE.get(), p));
    public static final RegistryObject<Item> DEEPSLATE_SOULSTONE_ORE = register("deepslate_soulstone_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get(), p));
    public static final RegistryObject<Item> SOULSTONE_ORE = register("soulstone_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULSTONE_ORE.get(), p));
    public static final RegistryObject<Item> RAW_SOULSTONE = register("raw_soulstone", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CRUSHED_SOULSTONE = register("crushed_soulstone", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> PROCESSED_SOULSTONE = register("processed_soulstone", DEFAULT_PROPERTIES(), Item::new);

    public static final RegistryObject<Item> BLOCK_OF_BRILLIANCE = register("block_of_brilliance", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_BRILLIANCE.get(), p));
    public static final RegistryObject<Item> BRILLIANT_DEEPSLATE = register("brilliant_deepslate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BRILLIANT_DEEPSLATE.get(), p));
    public static final RegistryObject<Item> BRILLIANT_STONE = register("brilliant_stone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BRILLIANT_STONE.get(), p));
    public static final RegistryObject<Item> CLUSTER_OF_BRILLIANCE = register("cluster_of_brilliance", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CRUSHED_BRILLIANCE = register("crushed_brilliance", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CHUNK_OF_BRILLIANCE = register("chunk_of_brilliance", DEFAULT_PROPERTIES(), (p) -> new BrillianceChunkItem(p.food((new FoodProperties.Builder()).fast().alwaysEat().build())));

    public static final RegistryObject<Item> BLOCK_OF_ARCANE_CHARCOAL = register("block_of_arcane_charcoal", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelBlockItem(BlockRegistry.BLOCK_OF_ARCANE_CHARCOAL.get(), p, 32000));
    public static final RegistryObject<Item> ARCANE_CHARCOAL = register("arcane_charcoal", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 3200));
    public static final RegistryObject<Item> ARCANE_CHARCOAL_FRAGMENT = register("arcane_charcoal_fragment", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 400));

    public static final RegistryObject<Item> BLOCK_OF_BLAZING_QUARTZ = register("block_of_blazing_quartz", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelBlockItem(BlockRegistry.BLOCK_OF_BLAZING_QUARTZ.get(), p, 16000));
    public static final RegistryObject<Item> BLAZING_QUARTZ_ORE = register("blazing_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLAZING_QUARTZ_ORE.get(), p));
    public static final RegistryObject<Item> BLAZING_QUARTZ = register("blazing_quartz", DEFAULT_PROPERTIES(), (p) -> new BlazingQuartzItem(BlockRegistry.BLAZING_QUARTZ_CLUSTER.get(), 1600, p));
    public static final RegistryObject<Item> BLAZING_QUARTZ_FRAGMENT = register("blazing_quartz_fragment", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 200));

    public static final RegistryObject<Item> DEEPSLATE_QUARTZ_ORE = register("deepslate_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get(), p));
    public static final RegistryObject<Item> NATURAL_QUARTZ_ORE = register("natural_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.NATURAL_QUARTZ_ORE.get(), p));
    public static final RegistryObject<Item> NATURAL_QUARTZ = register("natural_quartz", DEFAULT_PROPERTIES(), (p) -> new ItemNameBlockItem(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get(), p));

    public static final RegistryObject<Item> BLOCK_OF_CTHONIC_GOLD = register("block_of_cthonic_gold", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_CTHONIC_GOLD.get(), p));
    public static final RegistryObject<Item> CTHONIC_GOLD_ORE = register("cthonic_gold_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CTHONIC_GOLD_ORE.get(), p));
    public static final RegistryObject<Item> CTHONIC_GOLD = register("cthonic_gold", DEFAULT_PROPERTIES().rarity(UNCOMMON), Item::new);
    public static final RegistryObject<Item> CTHONIC_GOLD_FRAGMENT = register("cthonic_gold_fragment", DEFAULT_PROPERTIES(), (p) -> new ItemNameBlockItem(BlockRegistry.CTHONIC_GOLD_CLUSTER.get(), p));
    //endregion

    //region crafting blocks
    public static final RegistryObject<Item> SPIRIT_ALTAR = register("spirit_altar", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SPIRIT_ALTAR.get(), p));
    public static final RegistryObject<Item> RUNIC_WORKBENCH = register("runic_workbench", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNIC_WORKBENCH.get(), p));
    public static final RegistryObject<Item> SPIRIT_JAR = register("spirit_jar", DEFAULT_PROPERTIES(), (p) -> new SpiritJarItem(BlockRegistry.SPIRIT_JAR.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_OBELISK = register("runewood_obelisk", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.RUNEWOOD_OBELISK.get(), p, RunewoodObeliskBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> BRILLIANT_OBELISK = register("brilliant_obelisk", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.BRILLIANT_OBELISK.get(), p, BrilliantObeliskBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> SPIRIT_CRUCIBLE = register("spirit_crucible", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.SPIRIT_CRUCIBLE.get(), p, SpiritCrucibleCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> SPIRIT_CATALYZER = register("spirit_catalyzer", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.SPIRIT_CATALYZER.get(), p, SpiritCatalyzerCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> REPAIR_PYLON = register("repair_pylon", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.REPAIR_PYLON.get(), p, RepairPylonCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> RUNEWOOD_TOTEM_BASE = register("runewood_totem_base", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TOTEM_BASE.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TOTEM_BASE = register("soulwood_totem_base", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TOTEM_BASE.get(), p));
    public static final RegistryObject<Item> RITUAL_PLINTH = register("ritual_plinth", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RITUAL_PLINTH.get(), p));

    public static final RegistryObject<Item> WEAVERS_WORKBENCH = register("weavers_workbench", COSMETIC_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEAVERS_WORKBENCH.get(), p));
    //endregion

    //region materials
    public static final RegistryObject<Item> BLOCK_OF_ROTTING_ESSENCE = register("block_of_rotting_essence", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ROTTING_ESSENCE.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_GRIM_TALC = register("block_of_grim_talc", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_GRIM_TALC.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_ASTRAL_WEAVE = register("block_of_astral_weave", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ASTRAL_WEAVE.get(), p));
    //    public static final RegistryObject<Item> BLOCK_OF_WARP_FLUX = register("block_of_warp_flux", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_WARP_FLUX.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_HEX_ASH = register("block_of_hex_ash", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_HEX_ASH.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_LIVING_FLESH = register("block_of_living_flesh", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_LIVING_FLESH.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_ALCHEMICAL_CALX = register("block_of_alchemical_calx", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ALCHEMICAL_CALX.get(), p));
    //    public static final RegistryObject<Item> BLOCK_OF_CALCIFIED_BLIGHT = register("block_of_calcified_blight", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_CALCIFIED_BLIGHT.get(), p));
    public static final RegistryObject<Item> MASS_OF_BLIGHTED_GUNK = register("mass_of_blighted_gunk", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.MASS_OF_BLIGHTED_GUNK.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_NULL_SLATE = register("block_of_null_slate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_NULL_SLATE.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_VOID_SALTS = register("block_of_void_salts", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_VOID_SALTS.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_MNEMONIC_FRAGMENT = register("block_of_mnemonic_fragment", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_MNEMONIC_FRAGMENT.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_AURIC_EMBERS = register("block_of_auric_embers", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_AURIC_EMBERS.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_MALIGNANT_LEAD = register("block_of_malignant_lead", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_MALIGNANT_LEAD.get(), p));

    public static final RegistryObject<Item> ROTTING_ESSENCE = register("rotting_essence", DEFAULT_PROPERTIES(), (p) -> new Item(p.food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.HUNGER, 600, 1), 0.95f).build())));
    public static final RegistryObject<Item> GRIM_TALC = register("grim_talc", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> ASTRAL_WEAVE = register("astral_weave", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> WARP_FLUX = register("warp_flux", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> HEX_ASH = register("hex_ash", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> LIVING_FLESH = register("living_flesh", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> ALCHEMICAL_CALX = register("alchemical_calx", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CALCIFIED_BLIGHT = register("calcified_blight", HIDDEN_PROPERTIES(), (p) -> new CalcifiedBlightItem(BlockRegistry.CALCIFIED_BLIGHT.get(), p));
    public static final RegistryObject<Item> BLIGHTED_GUNK = register("blighted_gunk", DEFAULT_PROPERTIES(), BlightedGunkItem::new);
    public static final RegistryObject<Item> NULL_SLATE = register("null_slate", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> VOID_SALTS = register("void_salts", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> MNEMONIC_FRAGMENT = register("mnemonic_fragment", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> AURIC_EMBERS = register("auric_embers", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> MALIGNANT_LEAD = register("malignant_lead", DEFAULT_PROPERTIES().rarity(RARE), Item::new);

    public static final RegistryObject<Item> SPIRIT_FABRIC = register("spirit_fabric", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> SPECTRAL_LENS = register("spectral_lens", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> SPECTRAL_OPTIC = register("spectral_optic", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> POPPET = register("poppet", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> RUNEWOOD_TABLET = register("runewood_tablet", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> SOULWOOD_TABLET = register("soulwood_tablet", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> TAINTED_ROCK_TABLET = register("tainted_rock_tablet", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> VOID_TABLET = register("void_tablet", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> ANOMALOUS_DESIGN = register("anomalous_design", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> COMPLETE_DESIGN = register("complete_design", DEFAULT_PROPERTIES(), SimpleFoiledItem::new);
    public static final RegistryObject<Item> FUSED_CONSCIOUSNESS = register("fused_consciousness", DEFAULT_PROPERTIES(), (p) -> new FusedConsciousnessItem(p.rarity(RARE)));

    public static final RegistryObject<Item> BLOCK_OF_SOUL_STAINED_STEEL = register("block_of_soul_stained_steel", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get(), p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_INGOT = register("soul_stained_steel_ingot", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_PLATING = register("soul_stained_steel_plating", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_NUGGET = register("soul_stained_steel_nugget", DEFAULT_PROPERTIES(), Item::new);

    public static final RegistryObject<Item> BLOCK_OF_HALLOWED_GOLD = register("block_of_hallowed_gold", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_HALLOWED_GOLD.get(), p));
    public static final RegistryObject<Item> HALLOWED_GOLD_INGOT = register("hallowed_gold_ingot", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> HALLOWED_GOLD_NUGGET = register("hallowed_gold_nugget", DEFAULT_PROPERTIES(), Item::new);

    public static final RegistryObject<Item> BLOCK_OF_MALIGNANT_PEWTER = register("block_of_malignant_pewter", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_MALIGNANT_PEWTER.get(), p));
    public static final RegistryObject<Item> MALIGNANT_PEWTER_INGOT = register("malignant_pewter_ingot", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> MALIGNANT_PEWTER_PLATING = register("malignant_pewter_plating", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> MALIGNANT_PEWTER_NUGGET = register("malignant_pewter_nugget", DEFAULT_PROPERTIES(), Item::new);

    //region ether
    public static final RegistryObject<Item> ETHER = register("ether", DEFAULT_PROPERTIES(), (p) -> new EtherItem(BlockRegistry.ETHER.get(), p, false));
    public static final RegistryObject<Item> ETHER_TORCH = register("ether_torch", DEFAULT_PROPERTIES(), (p) -> new EtherTorchItem(BlockRegistry.ETHER_TORCH.get(), BlockRegistry.WALL_ETHER_TORCH.get(), p, false));
    public static final RegistryObject<Item> TAINTED_ETHER_BRAZIER = register("tainted_ether_brazier", DEFAULT_PROPERTIES(), (p) -> new EtherBrazierItem(BlockRegistry.TAINTED_ETHER_BRAZIER.get(), p, false));
    public static final RegistryObject<Item> TWISTED_ETHER_BRAZIER = register("twisted_ether_brazier", DEFAULT_PROPERTIES(), (p) -> new EtherBrazierItem(BlockRegistry.TWISTED_ETHER_BRAZIER.get(), p, false));

    public static final RegistryObject<Item> IRIDESCENT_ETHER = register("iridescent_ether", DEFAULT_PROPERTIES(), (p) -> new EtherItem(BlockRegistry.IRIDESCENT_ETHER.get(), p, true));
    public static final RegistryObject<Item> IRIDESCENT_ETHER_TORCH = register("iridescent_ether_torch", DEFAULT_PROPERTIES(), (p) -> new EtherTorchItem(BlockRegistry.IRIDESCENT_ETHER_TORCH.get(), BlockRegistry.IRIDESCENT_WALL_ETHER_TORCH.get(), p, true));
    public static final RegistryObject<Item> TAINTED_IRIDESCENT_ETHER_BRAZIER = register("tainted_iridescent_ether_brazier", DEFAULT_PROPERTIES(), (p) -> new EtherBrazierItem(BlockRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), p, true));
    public static final RegistryObject<Item> TWISTED_IRIDESCENT_ETHER_BRAZIER = register("twisted_iridescent_ether_brazier", DEFAULT_PROPERTIES(), (p) -> new EtherBrazierItem(BlockRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), p, true));
    //endregion

    public static final RegistryObject<Item> MENDING_DIFFUSER = register("mending_diffuser", DEFAULT_PROPERTIES(), MendingDiffuserItem::new);
    public static final RegistryObject<Item> IMPURITY_STABILIZER = register("impurity_stabilizer", DEFAULT_PROPERTIES(), ImpurityStabilizer::new);
    public static final RegistryObject<Item> SHIELDING_APPARATUS = register("shielding_apparatus", DEFAULT_PROPERTIES(), ShieldingApparatusItem::new);
    public static final RegistryObject<Item> WARPING_ENGINE = register("warping_engine", DEFAULT_PROPERTIES(), WarpingEngineItem::new);
    public static final RegistryObject<Item> ACCELERATING_INLAY = register("accelerating_inlay", DEFAULT_PROPERTIES(), AcceleratingInlayItem::new);
    public static final RegistryObject<Item> PRISMATIC_FOCUS_LENS = register("prismatic_focus_lens", DEFAULT_PROPERTIES(), PrismaticFocusLensItem::new);
    public static final RegistryObject<Item> BLAZING_DIODE = register("blazing_diode", DEFAULT_PROPERTIES(), BlazingDiodeItem::new);
    public static final RegistryObject<Item> INTRICATE_ASSEMBLY = register("intricate_assembly", DEFAULT_PROPERTIES(), IntricateAssemblyItem::new);
    public static final RegistryObject<Item> STELLAR_MECHANISM = register("stellar_mechanism", DEFAULT_PROPERTIES(), StellarMechanismItem::new);

    public static final RegistryObject<Item> TUNING_FORK = register("tuning_fork", GEAR_PROPERTIES(), Item::new);

    public static final RegistryObject<CrackedImpetusItem> CRACKED_IRON_IMPETUS = register("cracked_iron_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> IRON_IMPETUS = register("iron_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_IRON_IMPETUS));
    public static final RegistryObject<Item> IRON_NODE = register("iron_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_COPPER_IMPETUS = register("cracked_copper_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> COPPER_IMPETUS = register("copper_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_COPPER_IMPETUS));
    public static final RegistryObject<Item> COPPER_NODE = register("copper_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_GOLD_IMPETUS = register("cracked_gold_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> GOLD_IMPETUS = register("gold_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_GOLD_IMPETUS));
    public static final RegistryObject<Item> GOLD_NODE = register("gold_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_LEAD_IMPETUS = register("cracked_lead_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> LEAD_IMPETUS = register("lead_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_LEAD_IMPETUS));
    public static final RegistryObject<Item> LEAD_NODE = register("lead_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_SILVER_IMPETUS = register("cracked_silver_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> SILVER_IMPETUS = register("silver_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_SILVER_IMPETUS));
    public static final RegistryObject<Item> SILVER_NODE = register("silver_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_ALUMINUM_IMPETUS = register("cracked_aluminum_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> ALUMINUM_IMPETUS = register("aluminum_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ALUMINUM_IMPETUS));
    public static final RegistryObject<Item> ALUMINUM_NODE = register("aluminum_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_NICKEL_IMPETUS = register("cracked_nickel_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> NICKEL_IMPETUS = register("nickel_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_NICKEL_IMPETUS));
    public static final RegistryObject<Item> NICKEL_NODE = register("nickel_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_URANIUM_IMPETUS = register("cracked_uranium_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> URANIUM_IMPETUS = register("uranium_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_URANIUM_IMPETUS));
    public static final RegistryObject<Item> URANIUM_NODE = register("uranium_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_OSMIUM_IMPETUS = register("cracked_osmium_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> OSMIUM_IMPETUS = register("osmium_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_OSMIUM_IMPETUS));
    public static final RegistryObject<Item> OSMIUM_NODE = register("osmium_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_ZINC_IMPETUS = register("cracked_zinc_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> ZINC_IMPETUS = register("zinc_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ZINC_IMPETUS));
    public static final RegistryObject<Item> ZINC_NODE = register("zinc_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_TIN_IMPETUS = register("cracked_tin_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> TIN_IMPETUS = register("tin_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_TIN_IMPETUS));
    public static final RegistryObject<Item> TIN_NODE = register("tin_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);

    public static final RegistryObject<CrackedImpetusItem> CRACKED_ALCHEMICAL_IMPETUS = register("cracked_alchemical_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> ALCHEMICAL_IMPETUS = register("alchemical_impetus", METALLURGIC_PROPERTIES().durability(800), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ALCHEMICAL_IMPETUS));
    //endregion

    //region contents
    public static final RegistryObject<Item> TOTEMIC_STAFF = register("totemic_staff", GEAR_PROPERTIES(), TotemicStaffItem::new);

    public static final RegistryObject<Item> SPIRIT_POUCH = register("spirit_pouch", GEAR_PROPERTIES(), SpiritPouchItem::new);
    public static final RegistryObject<Item> ETHERIC_NITRATE = register("etheric_nitrate", DEFAULT_PROPERTIES(), EthericNitrateItem::new);
    public static final RegistryObject<Item> VIVID_NITRATE = register("vivid_nitrate", HIDDEN_PROPERTIES(), VividNitrateItem::new);

    public static final RegistryObject<Item> CRUDE_SCYTHE = register("crude_scythe", GEAR_PROPERTIES(), (p) -> new MalumScytheItem(Tiers.IRON, 0, 0.1f, p.durability(500)));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SCYTHE = register("soul_stained_steel_scythe", GEAR_PROPERTIES(), (p) -> new MagicScytheItem(SOUL_STAINED_STEEL, -2.5f, 0.1f, 4, p));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SWORD = register("soul_stained_steel_sword", GEAR_PROPERTIES(), (p) -> new MagicSwordItem(SOUL_STAINED_STEEL, -3, 0, 3, p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_PICKAXE = register("soul_stained_steel_pickaxe", GEAR_PROPERTIES(), (p) -> new MagicPickaxeItem(SOUL_STAINED_STEEL, -2, 0, 2, p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_AXE = register("soul_stained_steel_axe", GEAR_PROPERTIES(), (p) -> new MagicAxeItem(SOUL_STAINED_STEEL, -3f, 0, 4, p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SHOVEL = register("soul_stained_steel_shovel", GEAR_PROPERTIES(), (p) -> new MagicShovelItem(SOUL_STAINED_STEEL, -2, 0, 2, p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_HOE = register("soul_stained_steel_hoe", GEAR_PROPERTIES(), (p) -> new MagicHoeItem(SOUL_STAINED_STEEL, 0, -1.5f, 1, p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_KNIFE = register("soul_stained_steel_knife", FarmersDelightCompat.LOADED ? GEAR_PROPERTIES() : HIDDEN_PROPERTIES(), (p) -> FarmersDelightCompat.LOADED ? FarmersDelightCompat.LoadedOnly.makeMagicKnife(p) : new Item(p));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_HELMET = register("soul_stained_steel_helmet", GEAR_PROPERTIES(), (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.HELMET, p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_CHESTPLATE = register("soul_stained_steel_chestplate", GEAR_PROPERTIES(), (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.CHESTPLATE, p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_LEGGINGS = register("soul_stained_steel_leggings", GEAR_PROPERTIES(), (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.LEGGINGS, p));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_BOOTS = register("soul_stained_steel_boots", GEAR_PROPERTIES(), (p) -> new SoulStainedSteelArmorItem(ArmorItem.Type.BOOTS, p));

    public static final RegistryObject<Item> SOUL_HUNTER_CLOAK = register("soul_hunter_cloak", GEAR_PROPERTIES(), (p) -> new SoulHunterArmorItem(ArmorItem.Type.HELMET, p));
    public static final RegistryObject<Item> SOUL_HUNTER_ROBE = register("soul_hunter_robe", GEAR_PROPERTIES(), (p) -> new SoulHunterArmorItem(ArmorItem.Type.CHESTPLATE, p));
    public static final RegistryObject<Item> SOUL_HUNTER_LEGGINGS = register("soul_hunter_leggings", GEAR_PROPERTIES(), (p) -> new SoulHunterArmorItem(ArmorItem.Type.LEGGINGS, p));
    public static final RegistryObject<Item> SOUL_HUNTER_BOOTS = register("soul_hunter_boots", GEAR_PROPERTIES(), (p) -> new SoulHunterArmorItem(ArmorItem.Type.BOOTS, p));

    public static final RegistryObject<Item> TYRVING = register("tyrving", GEAR_PROPERTIES(), (p) -> new TyrvingItem(ItemTiers.ItemTierEnum.TYRVING, 0, -0.3f, p));

    public static final RegistryObject<Item> MALIGNANT_STRONGHOLD_HELMET = register("malignant_stronghold_helmet", GEAR_PROPERTIES(), (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.HELMET, p));
    public static final RegistryObject<Item> MALIGNANT_STRONGHOLD_CHESTPLATE = register("malignant_stronghold_chestplate", GEAR_PROPERTIES(), (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.CHESTPLATE, p));
    public static final RegistryObject<Item> MALIGNANT_STRONGHOLD_LEGGINGS = register("malignant_stronghold_leggings", GEAR_PROPERTIES(), (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.LEGGINGS, p));
    public static final RegistryObject<Item> MALIGNANT_STRONGHOLD_BOOTS = register("malignant_stronghold_boots", GEAR_PROPERTIES(), (p) -> new MalignantStrongholdArmorItem(ArmorItem.Type.BOOTS, p));

    public static final RegistryObject<Item> WEIGHT_OF_WORLDS = register("weight_of_worlds", GEAR_PROPERTIES(), (p) -> new WeightOfWorldsItem(ItemTiers.ItemTierEnum.MALIGNANT_ALLOY, -1, -0.1f, p));
    public static final RegistryObject<Item> EROSION_SCEPTER = register("erosion_scepter", GEAR_PROPERTIES(), (p) -> new ErosionScepterItem(MALIGNANT_ALLOY, 5, p));

    public static final RegistryObject<Item> MNEMONIC_HEX_STAFF = register("mnemonic_hex_staff", GEAR_PROPERTIES(), (p) -> new HexStaffItem(HEX_STAFF, 5, p));
    public static final RegistryObject<Item> STAFF_OF_THE_AURIC_FLAME = register("staff_of_the_auric_flame", GEAR_PROPERTIES(), (p) -> new AuricFlameStaffItem(AURIC_STAFF, 7, p));

    public static final RegistryObject<Item> RUNE_OF_IDLE_RESTORATION = register("rune_of_idle_restoration", GEAR_PROPERTIES(), RuneIdleRestorationItem::new);
    public static final RegistryObject<Item> RUNE_OF_CULLING = register("rune_of_culling", GEAR_PROPERTIES(), RuneCullingItem::new);
    public static final RegistryObject<Item> RUNE_OF_REINFORCEMENT = register("rune_of_reinforcement", GEAR_PROPERTIES(), RuneReinforcementItem::new);
    public static final RegistryObject<Item> RUNE_OF_VOLATILE_DISTORTION = register("rune_of_volatile_distortion", GEAR_PROPERTIES(), RuneVolatileDistortionItem::new);
    public static final RegistryObject<Item> RUNE_OF_DEXTERITY = register("rune_of_dexterity", GEAR_PROPERTIES(), RuneDexterityItem::new);
    public static final RegistryObject<Item> RUNE_OF_ALIMENT_CLEANSING = register("rune_of_aliment_cleansing", GEAR_PROPERTIES(), RuneAlimentCleansingItem::new);
    public static final RegistryObject<Item> RUNE_OF_REACTIVE_SHIELDING = register("rune_of_reactive_shielding", GEAR_PROPERTIES(), RuneReactiveShieldingItem::new);
    public static final RegistryObject<Item> RUNE_OF_FERVOR = register("rune_of_fervor", GEAR_PROPERTIES(), RuneFervorItem::new);

    public static final RegistryObject<Item> RUNE_OF_MOTION = register("rune_of_motion", GEAR_PROPERTIES(), p -> new TotemicRuneTrinketsItem(p, SpiritRiteRegistry.AERIAL_RITE, false));
    public static final RegistryObject<Item> RUNE_OF_LOYALTY = register("rune_of_loyalty", GEAR_PROPERTIES(), p -> new TotemicRuneTrinketsItem(p, SpiritRiteRegistry.AQUEOUS_RITE, false));
    public static final RegistryObject<Item> RUNE_OF_WARDING = register("rune_of_warding", GEAR_PROPERTIES(), p -> new TotemicRuneTrinketsItem(p, SpiritRiteRegistry.EARTHEN_RITE, false));
    public static final RegistryObject<Item> RUNE_OF_HASTE = register("rune_of_haste", GEAR_PROPERTIES(), p -> new TotemicRuneTrinketsItem(p, SpiritRiteRegistry.INFERNAL_RITE, false));
    public static final RegistryObject<Item> RUNE_OF_THE_AETHER = register("rune_of_the_aether", GEAR_PROPERTIES(), p -> new TotemicRuneTrinketsItem(p, SpiritRiteRegistry.AERIAL_RITE, true));
    public static final RegistryObject<Item> RUNE_OF_THE_SEAS = register("rune_of_the_seas", GEAR_PROPERTIES(), p -> new TotemicRuneTrinketsItem(p, SpiritRiteRegistry.AQUEOUS_RITE, true));
    public static final RegistryObject<Item> RUNE_OF_THE_ARENA = register("rune_of_the_arena", GEAR_PROPERTIES(), p -> new TotemicRuneTrinketsItem(p, SpiritRiteRegistry.EARTHEN_RITE, true));
    public static final RegistryObject<Item> RUNE_OF_THE_HELLS = register("rune_of_the_hells", GEAR_PROPERTIES(), p -> new TotemicRuneTrinketsItem(p, SpiritRiteRegistry.INFERNAL_RITE, true, 10));

    public static final RegistryObject<Item> RUNE_OF_BOLSTERING = register("rune_of_bolstering", GEAR_PROPERTIES(), RuneBolsteringItem::new);
    public static final RegistryObject<Item> RUNE_OF_SACRIFICIAL_EMPOWERMENT = register("rune_of_sacrificial_empowerment", GEAR_PROPERTIES(), RuneSacrificialEmpowermentItem::new);
    public static final RegistryObject<Item> RUNE_OF_SPELL_MASTERY = register("rune_of_spell_mastery", GEAR_PROPERTIES(), RuneSpellMasteryItem::new);
    public static final RegistryObject<Item> RUNE_OF_THE_HERETIC = register("rune_of_the_heretic", GEAR_PROPERTIES(), RuneHereticItem::new);
    public static final RegistryObject<Item> RUNE_OF_UNNATURAL_STAMINA = register("rune_of_unnatural_stamina", GEAR_PROPERTIES(), RuneUnnaturalStaminaItem::new);
    public static final RegistryObject<Item> RUNE_OF_TWINNED_DURATION = register("rune_of_twinned_duration", GEAR_PROPERTIES(), RuneTwinnedDurationItem::new);
    public static final RegistryObject<Item> RUNE_OF_TOUGHNESS = register("rune_of_toughness", GEAR_PROPERTIES(), RuneToughnessItem::new);
    public static final RegistryObject<Item> RUNE_OF_IGNEOUS_SOLACE = register("rune_of_igneous_solace", GEAR_PROPERTIES(), RuneIgneousSolaceItem::new);

    public static final RegistryObject<Item> GILDED_RING = register("gilded_ring", GEAR_PROPERTIES(), TrinketsGildedRing::new);
    public static final RegistryObject<Item> GILDED_BELT = register("gilded_belt", GEAR_PROPERTIES(), TrinketsGildedBelt::new);
    public static final RegistryObject<Item> ORNATE_RING = register("ornate_ring", GEAR_PROPERTIES(), TrinketsOrnateRing::new);
    public static final RegistryObject<Item> ORNATE_NECKLACE = register("ornate_necklace", GEAR_PROPERTIES(), TrinketsOrnateNecklace::new);

    public static final RegistryObject<Item> RUNIC_BROOCH = register("runic_brooch", GEAR_PROPERTIES(), CurioRunicBrooch::new);
    public static final RegistryObject<Item> ELABORATE_BROOCH = register("elaborate_brooch", GEAR_PROPERTIES(), CurioElaborateBrooch::new);
    public static final RegistryObject<Item> GLASS_BROOCH = register("glass_brooch", GEAR_PROPERTIES(), CurioGlassBrooch::new);
    public static final RegistryObject<Item> GLUTTONOUS_BROOCH = register("gluttonous_brooch", GEAR_PROPERTIES(), CurioGluttonousBrooch::new);

    public static final RegistryObject<Item> RING_OF_ESOTERIC_SPOILS = register("ring_of_esoteric_spoils", GEAR_PROPERTIES(), TrinketsArcaneSpoilRing::new);
    public static final RegistryObject<Item> RING_OF_CURATIVE_TALENT = register("ring_of_curative_talent", GEAR_PROPERTIES(), TrinketsCurativeRing::new);
    public static final RegistryObject<Item> RING_OF_ARCANE_PROWESS = register("ring_of_arcane_prowess", GEAR_PROPERTIES(), TrinketsRingOfProwess::new);
    public static final RegistryObject<Item> RING_OF_ALCHEMICAL_MASTERY = register("ring_of_alchemical_mastery", GEAR_PROPERTIES(), TrinketsAlchemicalRing::new);
    public static final RegistryObject<Item> RING_OF_DESPERATE_VORACITY = register("ring_of_desperate_voracity", GEAR_PROPERTIES(), TrinketsVoraciousRing::new);
    public static final RegistryObject<Item> RING_OF_THE_HOARDER = register("ring_of_the_hoarder", GEAR_PROPERTIES(), TrinketsHoarderRing::new);
    public static final RegistryObject<Item> RING_OF_THE_DEMOLITIONIST = register("ring_of_the_demolitionist", GEAR_PROPERTIES(), TrinketsDemolitionistRing::new);

    public static final RegistryObject<Item> NECKLACE_OF_THE_MYSTIC_MIRROR = register("necklace_of_the_mystic_mirror", GEAR_PROPERTIES(), TrinketsMirrorNecklace::new);
    public static final RegistryObject<Item> NECKLACE_OF_TIDAL_AFFINITY = register("necklace_of_tidal_affinity", GEAR_PROPERTIES(), TrinketsWaterNecklace::new);
    public static final RegistryObject<Item> NECKLACE_OF_THE_NARROW_EDGE = register("necklace_of_the_narrow_edge", GEAR_PROPERTIES(), TrinketsNarrowNecklace::new);
    public static final RegistryObject<Item> NECKLACE_OF_BLISSFUL_HARMONY = register("necklace_of_blissful_harmony", GEAR_PROPERTIES(), TrinketsHarmonyNecklace::new);

    public static final RegistryObject<Item> BELT_OF_THE_STARVED = register("belt_of_the_starved", GEAR_PROPERTIES(), TrinketsStarvedBelt::new);
    public static final RegistryObject<Item> BELT_OF_THE_PROSPECTOR = register("belt_of_the_prospector", GEAR_PROPERTIES(), TrinketsProspectorBelt::new);
    public static final RegistryObject<Item> BELT_OF_THE_MAGEBANE = register("belt_of_the_magebane", GEAR_PROPERTIES(), TrinketsMagebaneBelt::new);

    public static final RegistryObject<Item> RING_OF_THE_ENDLESS_WELL = register("ring_of_the_endless_well", GEAR_PROPERTIES(), TrinketsEndlessRing::new);
    public static final RegistryObject<Item> RING_OF_GROWING_FLESH = register("ring_of_growing_flesh", GEAR_PROPERTIES(), TrinketsGrowingFleshRing::new);
    public static final RegistryObject<Item> RING_OF_GRUESOME_CONCENTRATION = register("ring_of_gruesome_concentration", GEAR_PROPERTIES(), TrinketsGruesomeConcentrationRing::new);
    public static final RegistryObject<Item> NECKLACE_OF_THE_HIDDEN_BLADE = register("necklace_of_the_hidden_blade", GEAR_PROPERTIES(), TrinketsHiddenBladeNecklace::new);
    public static final RegistryObject<Item> NECKLACE_OF_THE_WATCHER = register("necklace_of_the_watcher", GEAR_PROPERTIES(), TrinketsWatcherNecklace::new);
    public static final RegistryObject<Item> BELT_OF_THE_LIMITLESS = register("belt_of_the_limitless", GEAR_PROPERTIES(), TrinketsLimitlessBelt::new);

    public static final RegistryObject<Item> AESTHETICA = register("music_disc_aesthetica", HIDDEN_PROPERTIES().rarity(RARE), AestheticaMusicDiscItem::new);
    //endregion

    //region cosmetics
    public static final RegistryObject<Item> ESOTERIC_SPOOL = register("esoteric_spool", COSMETIC_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> ANCIENT_WEAVE = register("ancient_weave", COSMETIC_PROPERTIES(), GenericWeaveItem::new);
    public static final RegistryObject<Item> CORNERED_WEAVE = register("cornered_weave", COSMETIC_PROPERTIES(), GenericWeaveItem::new);
    public static final RegistryObject<Item> DREADED_WEAVE = register("dreaded_weave", COSMETIC_PROPERTIES(), GenericWeaveItem::new);
    public static final RegistryObject<Item> MECHANICAL_WEAVE_V1 = register("mechanical_weave_v1", COSMETIC_PROPERTIES(), GenericWeaveItem::new);
    public static final RegistryObject<Item> MECHANICAL_WEAVE_V2 = register("mechanical_weave_v2", COSMETIC_PROPERTIES(), GenericWeaveItem::new);

    public static final RegistryObject<PrideweaveItem> ACE_PRIDEWEAVE = register("ace_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> AGENDER_PRIDEWEAVE = register("agender_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> ARO_PRIDEWEAVE = register("aro_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> AROACE_PRIDEWEAVE = register("aroace_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> BI_PRIDEWEAVE = register("bi_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> DEMIBOY_PRIDEWEAVE = register("demiboy_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> DEMIGIRL_PRIDEWEAVE = register("demigirl_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> ENBY_PRIDEWEAVE = register("enby_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> GAY_PRIDEWEAVE = register("gay_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> GENDERFLUID_PRIDEWEAVE = register("genderfluid_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> GENDERQUEER_PRIDEWEAVE = register("genderqueer_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> INTERSEX_PRIDEWEAVE = register("intersex_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> LESBIAN_PRIDEWEAVE = register("lesbian_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> PAN_PRIDEWEAVE = register("pan_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> PLURAL_PRIDEWEAVE = register("plural_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> POLY_PRIDEWEAVE = register("poly_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> PRIDE_PRIDEWEAVE = register("pride_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);
    public static final RegistryObject<PrideweaveItem> TRANS_PRIDEWEAVE = register("trans_prideweave", COSMETIC_PROPERTIES(), PrideweaveItem::new);

    public static final RegistryObject<Item> TOPHAT = register("tophat", COSMETIC_PROPERTIES().stacksTo(1), CurioTopHat::new);

    //endregion

    //region hidden items
    public static final RegistryObject<Item> THE_DEVICE = register("the_device", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.THE_DEVICE.get(), p));
    public static final RegistryObject<Item> THE_VESSEL = register("the_vessel", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.THE_VESSEL.get(), p));
    public static final RegistryObject<Item> CREATIVE_SCYTHE = register("creative_scythe", HIDDEN_PROPERTIES().durability(-1), (p) -> new MagicScytheItem(Tiers.IRON, 9993, 9.1f, 999f, p));
    public static final RegistryObject<Item> TOKEN_OF_GRATITUDE = register("token_of_gratitude", HIDDEN_PROPERTIES(), CurioTokenOfGratitude::new);

    public static final RegistryObject<Item> PRIMORDIAL_SOUP = register("primordial_soup", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.PRIMORDIAL_SOUP.get(), p));
    public static final RegistryObject<Item> VOID_CONDUIT = register("void_conduit", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VOID_CONDUIT.get(), p));
    public static final RegistryObject<Item> VOID_DEPOT = register("void_depot", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VOID_DEPOT.get(), p));

    public static final RegistryObject<Item> WEEPING_WELL_BRICKS = register("weeping_well_bricks", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_BRICKS.get(), p));
    public static final RegistryObject<Item> WEEPING_WELL_ENCASEMENT = register("weeping_well_encasement", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_ENCASEMENT.get(), p));
    public static final RegistryObject<Item> WEEPING_WELL_ENCASEMENT_MIRRORED = register("weeping_well_encasement_mirrored", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_ENCASEMENT_MIRRORED.get(), p));
    public static final RegistryObject<Item> WEEPING_WELL_ENCASEMENT_CORNER = register("weeping_well_encasement_corner", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_ENCASEMENT_CORNER.get(), p));
    public static final RegistryObject<Item> WEEPING_WELL_CENTRAL_ENCASEMENT = register("weeping_well_central_encasement", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_CENTRAL_ENCASEMENT.get(), p));
    public static final RegistryObject<Item> WEEPING_WELL_CENTRAL_ENCASEMENT_SUPPORT = register("weeping_well_central_encasement_support", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_CENTRAL_ENCASEMENT_SUPPORT.get(), p));
    public static final RegistryObject<Item> WEEPING_WELL_CENTRAL_PILLAR = register("weeping_well_central_pillar", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_CENTRAL_PILLAR.get(), p));
    public static final RegistryObject<Item> WEEPING_WELL_SIDE_PILLAR = register("weeping_well_side_pillar", HIDDEN_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEEPING_WELL_SIDE_PILLAR.get(), p));
    //endregion

    public static class Common {
        public static void registerCompost() {
            CompostingChanceRegistry.INSTANCE.add(RUNEWOOD_LEAVES.get(), 0.3f);
            CompostingChanceRegistry.INSTANCE.add(HANGING_RUNEWOOD_LEAVES.get(), 0.3f);
            CompostingChanceRegistry.INSTANCE.add(SOULWOOD_LEAVES.get(), 0.3f);
            CompostingChanceRegistry.INSTANCE.add(BUDDING_SOULWOOD_LEAVES.get(), 0.3f);
            CompostingChanceRegistry.INSTANCE.add(HANGING_SOULWOOD_LEAVES.get(), 0.3f);
            CompostingChanceRegistry.INSTANCE.add(RUNEWOOD_SAPLING.get(), 0.3f);
            CompostingChanceRegistry.INSTANCE.add(SOULWOOD_GROWTH.get(), 0.3f);
        }

        public static void registerCompostable(RegistryObject<Item> item, float chance) {
            ComposterBlock.COMPOSTABLES.put(item.get(), chance);
        }
    }


    public static class ClientOnly {

        public static void addItemProperties() {
            Set<LodestoneArmorItem> armors = ItemRegistry.ITEMS.getEntries().stream().filter(r -> r.get() instanceof LodestoneArmorItem).map(r -> (LodestoneArmorItem) r.get()).collect(Collectors.toSet());
            ClampedItemPropertyFunction armorPropertyFunction = (stack, level, holder, holderID) -> {
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
                ItemProperties.register(armor, new ResourceLocation(ArmorSkin.MALUM_SKIN_TAG), armorPropertyFunction);
            }
            ItemProperties.register(RITUAL_SHARD.get(), new ResourceLocation(RitualShardItem.RITUAL_TYPE), (stack, level, holder, holderID) -> {
                if (!stack.hasTag()) {
                    return -1;
                }
                CompoundTag nbt = stack.getTag();
                if (!nbt.contains(RitualShardItem.RITUAL_TYPE)) {
                    return -1;
                }
                if (!nbt.contains(RitualShardItem.STORED_SPIRITS)) {
                    return -1;
                }
                MalumRitualTier tier = RitualShardItem.getRitualTier(stack);
                return tier.potency;
            });
        }

        public static void setItemColors() {
            Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());

            DataHelper.takeAll(items, i -> i.get() instanceof BlockItem blockItem && blockItem.getBlock() instanceof iGradientedLeavesBlock).forEach(item -> {
                iGradientedLeavesBlock malumLeavesBlock = (iGradientedLeavesBlock) ((BlockItem) item.get()).getBlock();
                ColorProviderRegistry.ITEM.register((s, c) -> ColorHelper.getColor(malumLeavesBlock.getMaxColor()), item.get());
            });
            DataHelper.takeAll(items, i -> i.get() instanceof EtherTorchItem || i.get() instanceof EtherBrazierItem).forEach(i -> ColorProviderRegistry.ITEM.register((s, c) -> {
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
            DataHelper.takeAll(items, i -> i.get() instanceof EtherItem).forEach(i -> ColorProviderRegistry.ITEM.register((s, c) -> {
                AbstractEtherItem etherItem = (AbstractEtherItem) s.getItem();
                return c == 0 ? etherItem.getFirstColor(s) : etherItem.getSecondColor(s);
            }, i.get()));

            DataHelper.takeAll(items, i -> i.get() instanceof SpiritShardItem).forEach(item ->
                    ColorProviderRegistry.ITEM.register((s, c) -> ColorHelper.getColor(((SpiritShardItem) item.get()).type.getItemColor()), item.get()));

            DataHelper.takeAll(items, i -> i.get() instanceof AbstractRuneTrinketsItem).forEach(item ->
                    ColorProviderRegistry.ITEM.register((s, c) -> {
                        if (c == 0) {
                            return -1;
                        }
                        final MalumSpiritType spiritType = ((AbstractRuneTrinketsItem) item.get()).spiritType;
                        Color color = spiritType.getItemColor();
                        if (spiritType.equals(SpiritTypeRegistry.WICKED_SPIRIT) || spiritType.equals(SpiritTypeRegistry.ELDRITCH_SPIRIT)) {
                            color = color.brighter();
                        }
                        return ColorHelper.getColor(color);
                    }, item.get()));
            ColorProviderRegistry.ITEM.register((s, c) -> RitualShardItem.getRitualType(s) == null ? -1 : ColorHelper.getColor(RitualShardItem.getRitualType(s).spirit.getItemColor()), RITUAL_SHARD.get());
        }
    }
}
