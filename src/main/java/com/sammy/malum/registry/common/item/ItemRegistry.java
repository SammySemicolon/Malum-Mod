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
import com.sammy.malum.common.item.curiosities.curios.misc.*;
import com.sammy.malum.common.item.curiosities.curios.prospector.*;
import com.sammy.malum.common.item.curiosities.curios.rotten.*;
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
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.registry.common.item.ItemTiers.ItemTierEnum.*;
import static net.minecraft.world.item.Items.*;
import static net.minecraft.world.item.Rarity.*;

@SuppressWarnings("unused")
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MALUM);

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
    public static Item.Properties VOID_PROPERTIES() {
        return new LodestoneItemProperties(CreativeTabRegistry.VOID);
    }
    public static Item.Properties VOID_GEAR_PROPERTIES() {
        return VOID_PROPERTIES().stacksTo(1);
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

    //region spirits
    public static final RegistryObject<SpiritShardItem> SACRED_SPIRIT = register("sacred_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.SACRED_SPIRIT));
    public static final RegistryObject<SpiritShardItem> WICKED_SPIRIT = register("wicked_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.WICKED_SPIRIT));
    public static final RegistryObject<SpiritShardItem> ARCANE_SPIRIT = register("arcane_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.ARCANE_SPIRIT));
    public static final RegistryObject<SpiritShardItem> ELDRITCH_SPIRIT = register("eldritch_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.ELDRITCH_SPIRIT));
    public static final RegistryObject<SpiritShardItem> AQUEOUS_SPIRIT = register("aqueous_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.AQUEOUS_SPIRIT));
    public static final RegistryObject<SpiritShardItem> AERIAL_SPIRIT = register("aerial_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final RegistryObject<SpiritShardItem> INFERNAL_SPIRIT = register("infernal_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final RegistryObject<SpiritShardItem> EARTHEN_SPIRIT = register("earthen_spirit", DEFAULT_PROPERTIES(), (p) -> new SpiritShardItem(p, SpiritTypeRegistry.EARTHEN_SPIRIT));
    //endregion

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
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK = register("chiseled_tainted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHISELED_TAINTED_ROCK.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = register("tainted_rock_pressure_plate", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), p));
    public static final RegistryObject<Item> TAINTED_ROCK_BUTTON = register("tainted_rock_button", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_BUTTON.get(), p));

    public static final RegistryObject<Item> TAINTED_ROCK_WALL = register("tainted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TAINTED_ROCK_WALL.get(), p));
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
    public static final RegistryObject<Item> CHISELED_TWISTED_ROCK = register("chiseled_twisted_rock", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CHISELED_TWISTED_ROCK.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_PRESSURE_PLATE = register("twisted_rock_pressure_plate", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_BUTTON = register("twisted_rock_button", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_BUTTON.get(), p));

    public static final RegistryObject<Item> TWISTED_ROCK_WALL = register("twisted_rock_wall", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_WALL.get(), p));
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

    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_STAND = register("twisted_rock_item_stand", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_STAND.get(), p));
    public static final RegistryObject<Item> TWISTED_ROCK_ITEM_PEDESTAL = register("twisted_rock_item_pedestal", BUILDING_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get(), p));
    //endregion twisted rock

    //region runewood
    public static final RegistryObject<Item> HOLY_SAP = register("holy_sap", NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE), Item::new);
    public static final RegistryObject<Item> HOLY_SAPBALL = register("holy_sapball", NATURE_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> HOLY_SYRUP = register("holy_syrup", NATURE_PROPERTIES(), (p) -> new HolySyrupItem(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1).build())));
    public static final RegistryObject<Item> HOLY_CARAMEL = register("holy_caramel", NATURE_PROPERTIES(), (p) -> new HolyCaramelItem(FarmersDelightCompat.LOADED ? NATURE_PROPERTIES().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.15F).effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1).build()) : HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> RUNEWOOD_LEAVES = register("runewood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_SAPLING = register("runewood_sapling", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_SAPLING.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_LOG = register("runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_LOG.get(), p));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD_LOG = register("stripped_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_RUNEWOOD_LOG.get(), p));
    public static final RegistryObject<Item> RUNEWOOD = register("runewood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD.get(), p));
    public static final RegistryObject<Item> STRIPPED_RUNEWOOD = register("stripped_runewood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_RUNEWOOD.get(), p));

    public static final RegistryObject<Item> EXPOSED_RUNEWOOD_LOG = register("exposed_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.EXPOSED_RUNEWOOD_LOG.get(), p));
    public static final RegistryObject<Item> REVEALED_RUNEWOOD_LOG = register("revealed_runewood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.REVEALED_RUNEWOOD_LOG.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS = register("runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS = register("vertical_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_PANEL = register("runewood_panel", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PANEL.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_TILES = register("runewood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_SLAB = register("runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_SLAB = register("vertical_runewood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_PANEL_SLAB = register("runewood_panel_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PANEL_SLAB.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_TILES_SLAB = register("runewood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES_SLAB.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_STAIRS = register("runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> VERTICAL_RUNEWOOD_PLANKS_STAIRS = register("vertical_runewood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_PANEL_STAIRS = register("runewood_panel_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PANEL_STAIRS.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_TILES_STAIRS = register("runewood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TILES_STAIRS.get(), p));

    public static final RegistryObject<Item> CUT_RUNEWOOD_PLANKS = register("cut_runewood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_RUNEWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_BEAM = register("runewood_beam", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BEAM.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_DOOR = register("runewood_door", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_DOOR.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_TRAPDOOR = register("runewood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TRAPDOOR.get(), p));
    public static final RegistryObject<Item> SOLID_RUNEWOOD_TRAPDOOR = register("solid_runewood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOLID_RUNEWOOD_TRAPDOOR.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_BUTTON = register("runewood_planks_button", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_BUTTON.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_PRESSURE_PLATE = register("runewood_planks_pressure_plate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_PRESSURE_PLATE.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE = register("runewood_planks_fence", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_FENCE.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_PLANKS_FENCE_GATE = register("runewood_planks_fence_gate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_FENCE_GATE.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_ITEM_STAND = register("runewood_item_stand", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_STAND.get(), p));
    public static final RegistryObject<Item> RUNEWOOD_ITEM_PEDESTAL = register("runewood_item_pedestal", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_ITEM_PEDESTAL.get(), p));

    public static final RegistryObject<Item> RUNEWOOD_SIGN = register("runewood_sign", NATURE_PROPERTIES(), (p) -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.RUNEWOOD_SIGN.get(), BlockRegistry.RUNEWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> RUNEWOOD_BOAT = register("runewood_boat", NATURE_PROPERTIES(), (p) -> new LodestoneBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.RUNEWOOD_BOAT));
    //endregion

    //region blight
    public static final RegistryObject<Item> BLIGHTED_EARTH = register("blighted_earth", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_EARTH.get(), p));
    public static final RegistryObject<Item> BLIGHTED_SOIL = register("blighted_soil", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_SOIL.get(), p));
    public static final RegistryObject<Item> BLIGHTED_WEED = register("blighted_weed", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_WEED.get(), p));
    public static final RegistryObject<Item> BLIGHTED_TUMOR = register("blighted_tumor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_TUMOR.get(), p));
    public static final RegistryObject<Item> BLIGHTED_SOULWOOD = register("blighted_soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLIGHTED_SOULWOOD.get(), p));
    public static final RegistryObject<Item> BLIGHTED_GUNK = register("blighted_gunk", NATURE_PROPERTIES(), Item::new);
    //endregion

    //region soulwood
    public static final RegistryObject<Item> UNHOLY_SAP = register("unholy_sap", NATURE_PROPERTIES(), (p) -> new Item(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE)));
    public static final RegistryObject<Item> UNHOLY_SAPBALL = register("unholy_sapball", NATURE_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> UNHOLY_SYRUP = register("unholy_syrup", NATURE_PROPERTIES(), (p) -> new UnholySyrupItem(NATURE_PROPERTIES().craftRemainder(GLASS_BOTTLE).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.1F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 200, 0), 1).build())));
    public static final RegistryObject<Item> UNHOLY_CARAMEL = register("unholy_caramel", NATURE_PROPERTIES(), (p) -> new HolyCaramelItem(FarmersDelightCompat.LOADED ? NATURE_PROPERTIES().food((new FoodProperties.Builder()).nutrition(4).saturationMod(0.15F).effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 0), 1).build()) : HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> SOULWOOD_LEAVES = register("soulwood_leaves", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_LEAVES.get(), p));
    public static final RegistryObject<Item> SOULWOOD_GROWTH = register("soulwood_growth", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_GROWTH.get(), p));

    public static final RegistryObject<Item> SOULWOOD_LOG = register("soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_LOG.get(), p));
    public static final RegistryObject<Item> STRIPPED_SOULWOOD_LOG = register("stripped_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_SOULWOOD_LOG.get(), p));
    public static final RegistryObject<Item> SOULWOOD = register("soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD.get(), p));
    public static final RegistryObject<Item> STRIPPED_SOULWOOD = register("stripped_soulwood", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.STRIPPED_SOULWOOD.get(), p));

    public static final RegistryObject<Item> EXPOSED_SOULWOOD_LOG = register("exposed_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.EXPOSED_SOULWOOD_LOG.get(), p));
    public static final RegistryObject<Item> REVEALED_SOULWOOD_LOG = register("revealed_soulwood_log", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.REVEALED_SOULWOOD_LOG.get(), p));

    public static final RegistryObject<Item> SOULWOOD_PLANKS = register("soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS = register("vertical_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> SOULWOOD_PANEL = register("soulwood_panel", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PANEL.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TILES = register("soulwood_tiles", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES.get(), p));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_SLAB = register("soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS_SLAB = register("vertical_soulwood_planks_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), p));
    public static final RegistryObject<Item> SOULWOOD_PANEL_SLAB = register("soulwood_panel_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PANEL_SLAB.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TILES_SLAB = register("soulwood_tiles_slab", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES_SLAB.get(), p));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_STAIRS = register("soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> VERTICAL_SOULWOOD_PLANKS_STAIRS = register("vertical_soulwood_planks_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), p));
    public static final RegistryObject<Item> SOULWOOD_PANEL_STAIRS = register("soulwood_panel_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PANEL_STAIRS.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TILES_STAIRS = register("soulwood_tiles_stairs", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TILES_STAIRS.get(), p));

    public static final RegistryObject<Item> CUT_SOULWOOD_PLANKS = register("cut_soulwood_planks", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.CUT_SOULWOOD_PLANKS.get(), p));
    public static final RegistryObject<Item> SOULWOOD_BEAM = register("soulwood_beam", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BEAM.get(), p));

    public static final RegistryObject<Item> SOULWOOD_DOOR = register("soulwood_door", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_DOOR.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TRAPDOOR = register("soulwood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TRAPDOOR.get(), p));
    public static final RegistryObject<Item> SOLID_SOULWOOD_TRAPDOOR = register("solid_soulwood_trapdoor", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOLID_SOULWOOD_TRAPDOOR.get(), p));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_BUTTON = register("soulwood_planks_button", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_BUTTON.get(), p));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_PRESSURE_PLATE = register("soulwood_planks_pressure_plate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_PRESSURE_PLATE.get(), p));

    public static final RegistryObject<Item> SOULWOOD_PLANKS_FENCE = register("soulwood_planks_fence", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_FENCE.get(), p));
    public static final RegistryObject<Item> SOULWOOD_PLANKS_FENCE_GATE = register("soulwood_planks_fence_gate", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_FENCE_GATE.get(), p));

    public static final RegistryObject<Item> SOULWOOD_ITEM_STAND = register("soulwood_item_stand", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_STAND.get(), p));
    public static final RegistryObject<Item> SOULWOOD_ITEM_PEDESTAL = register("soulwood_item_pedestal", NATURE_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_ITEM_PEDESTAL.get(), p));

    public static final RegistryObject<Item> SOULWOOD_SIGN = register("soulwood_sign", NATURE_PROPERTIES(), (p) -> new SignItem(NATURE_PROPERTIES().stacksTo(16), BlockRegistry.SOULWOOD_SIGN.get(), BlockRegistry.SOULWOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> SOULWOOD_BOAT = register("soulwood_boat", NATURE_PROPERTIES(), (p) -> new LodestoneBoatItem(NATURE_PROPERTIES().stacksTo(1), EntityRegistry.SOULWOOD_BOAT));
    //endregion

    //region ores
    public static final RegistryObject<Item> SOULSTONE_ORE = register("soulstone_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULSTONE_ORE.get(), p));
    public static final RegistryObject<Item> DEEPSLATE_SOULSTONE_ORE = register("deepslate_soulstone_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.DEEPSLATE_SOULSTONE_ORE.get(), p));
    public static final RegistryObject<Item> RAW_SOULSTONE = register("raw_soulstone", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CRUSHED_SOULSTONE = register("crushed_soulstone", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> BLOCK_OF_RAW_SOULSTONE = register("block_of_raw_soulstone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_RAW_SOULSTONE.get(), p));
    public static final RegistryObject<Item> PROCESSED_SOULSTONE = register("processed_soulstone", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> BLOCK_OF_SOULSTONE = register("block_of_soulstone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_SOULSTONE.get(), p));

    public static final RegistryObject<Item> BRILLIANT_STONE = register("brilliant_stone", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BRILLIANT_STONE.get(), p));
    public static final RegistryObject<Item> BRILLIANT_DEEPSLATE = register("brilliant_deepslate", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BRILLIANT_DEEPSLATE.get(), p));
    public static final RegistryObject<Item> CLUSTER_OF_BRILLIANCE = register("cluster_of_brilliance", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CRUSHED_BRILLIANCE = register("crushed_brilliance", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CHUNK_OF_BRILLIANCE = register("chunk_of_brilliance", DEFAULT_PROPERTIES(), (p) -> new BrillianceChunkItem(p.food((new FoodProperties.Builder()).fast().alwaysEat().build())));
    public static final RegistryObject<Item> BLOCK_OF_BRILLIANCE = register("block_of_brilliance", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_BRILLIANCE.get(), p));

    public static final RegistryObject<Item> ARCANE_CHARCOAL = register("arcane_charcoal", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 3200));
    public static final RegistryObject<Item> ARCANE_CHARCOAL_FRAGMENT = register("arcane_charcoal_fragment", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 400));
    public static final RegistryObject<Item> BLOCK_OF_ARCANE_CHARCOAL = register("block_of_arcane_charcoal", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelBlockItem(BlockRegistry.BLOCK_OF_ARCANE_CHARCOAL.get(), p, 32000));

    public static final RegistryObject<Item> BLAZING_QUARTZ_ORE = register("blazing_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLAZING_QUARTZ_ORE.get(), p));
    public static final RegistryObject<Item> BLAZING_QUARTZ = register("blazing_quartz", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 1600));
    public static final RegistryObject<Item> BLAZING_QUARTZ_FRAGMENT = register("blazing_quartz_fragment", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelItem(p, 200));
    public static final RegistryObject<Item> BLOCK_OF_BLAZING_QUARTZ = register("block_of_blazing_quartz", DEFAULT_PROPERTIES(), (p) -> new LodestoneFuelBlockItem(BlockRegistry.BLOCK_OF_BLAZING_QUARTZ.get(), p, 16000));

    public static final RegistryObject<Item> NATURAL_QUARTZ_ORE = register("natural_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.NATURAL_QUARTZ_ORE.get(), p));
    public static final RegistryObject<Item> DEEPSLATE_QUARTZ_ORE = register("deepslate_quartz_ore", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.DEEPSLATE_QUARTZ_ORE.get(), p));
    public static final RegistryObject<Item> NATURAL_QUARTZ = register("natural_quartz", DEFAULT_PROPERTIES(), (p) -> new ItemNameBlockItem(BlockRegistry.NATURAL_QUARTZ_CLUSTER.get(), p));
    //endregion

    //region crafting blocks
    public static final RegistryObject<Item> SPIRIT_ALTAR = register("spirit_altar", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SPIRIT_ALTAR.get(), p));
    public static final RegistryObject<Item> WEAVERS_WORKBENCH = register("weavers_workbench", COSMETIC_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.WEAVERS_WORKBENCH.get(), COSMETIC_PROPERTIES()));
    public static final RegistryObject<Item> SPIRIT_JAR = register("spirit_jar", DEFAULT_PROPERTIES(), (p) -> new SpiritJarItem(BlockRegistry.SPIRIT_JAR.get(), p));
    public static final RegistryObject<Item> SOUL_VIAL = register("soul_vial", HIDDEN_PROPERTIES(), (p) -> new SoulVialItem(BlockRegistry.SOUL_VIAL.get(), HIDDEN_PROPERTIES()));
    public static final RegistryObject<Item> RUNEWOOD_OBELISK = register("runewood_obelisk", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.RUNEWOOD_OBELISK.get(), p, RunewoodObeliskBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> BRILLIANT_OBELISK = register("brilliant_obelisk", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.BRILLIANT_OBELISK.get(), p, BrilliantObeliskBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> SPIRIT_CRUCIBLE = register("spirit_crucible", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.SPIRIT_CRUCIBLE.get(), p, SpiritCrucibleCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> TWISTED_TABLET = register("twisted_tablet", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.TWISTED_TABLET.get(), p));
    public static final RegistryObject<Item> SPIRIT_CATALYZER = register("spirit_catalyzer", DEFAULT_PROPERTIES(), (p) -> new MultiBlockItem(BlockRegistry.SPIRIT_CATALYZER.get(), p, SpiritCatalyzerCoreBlockEntity.STRUCTURE));
    public static final RegistryObject<Item> RUNEWOOD_TOTEM_BASE = register("runewood_totem_base", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.RUNEWOOD_TOTEM_BASE.get(), p));
    public static final RegistryObject<Item> SOULWOOD_TOTEM_BASE = register("soulwood_totem_base", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.SOULWOOD_TOTEM_BASE.get(), p));
    //endregion

    //region materials
    public static final RegistryObject<Item> ROTTING_ESSENCE = register("rotting_essence", DEFAULT_PROPERTIES(), (p) -> new Item(p.food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.2F).effect(() -> new MobEffectInstance(MobEffects.HUNGER, 600, 1), 0.95f).build())));
    public static final RegistryObject<Item> GRIM_TALC = register("grim_talc", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> ASTRAL_WEAVE = register("astral_weave", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CTHONIC_GOLD = register("cthonic_gold", DEFAULT_PROPERTIES().rarity(UNCOMMON), Item::new);
    public static final RegistryObject<Item> HEX_ASH = register("hex_ash", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> ALCHEMICAL_CALX = register("alchemical_calx", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CURSED_GRIT = register("cursed_grit", DEFAULT_PROPERTIES(), Item::new);

    public static final RegistryObject<Item> BLOCK_OF_ROTTING_ESSENCE = register("block_of_rotting_essence", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ROTTING_ESSENCE.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_GRIM_TALC = register("block_of_grim_talc", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_GRIM_TALC.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_ASTRAL_WEAVE = register("block_of_astral_weave", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ASTRAL_WEAVE.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_CTHONIC_GOLD = register("block_of_cthonic_gold", DEFAULT_PROPERTIES(), (p) -> new ItemNameBlockItem(BlockRegistry.BLOCK_OF_CTHONIC_GOLD.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLOCK_OF_HEX_ASH = register("block_of_hex_ash", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_HEX_ASH.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_ALCHEMICAL_CALX = register("block_of_alchemical_calx", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_ALCHEMICAL_CALX.get(), p));
    public static final RegistryObject<Item> MASS_OF_BLIGHTED_GUNK = register("mass_of_blighted_gunk", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.MASS_OF_BLIGHTED_GUNK.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_CURSED_GRIT = register("block_of_cursed_grit", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_CURSED_GRIT.get(), p));
    public static final RegistryObject<Item> BLOCK_OF_VOID_SALTS = register("block_of_void_salts", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_VOID_SALTS.get(), HIDDEN_PROPERTIES()));

    public static final RegistryObject<Item> SPIRIT_FABRIC = register("spirit_fabric", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> SPECTRAL_LENS = register("spectral_lens", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> SPECTRAL_OPTIC = register("spectral_optic", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> ATTUNED_OPTIC_AERIAL = register("aerial_tuned_optic", DEFAULT_PROPERTIES(), (p) -> new TunedOpticItem(p, SpiritTypeRegistry.AERIAL_SPIRIT));
    public static final RegistryObject<Item> ATTUNED_OPTIC_AQUEOUS = register("aqueous_tuned_optic", DEFAULT_PROPERTIES(), (p) -> new TunedOpticItem(p, SpiritTypeRegistry.AQUEOUS_SPIRIT));
    public static final RegistryObject<Item> ATTUNED_OPTIC_INFERNAL = register("infernal_tuned_optic", DEFAULT_PROPERTIES(), (p) -> new TunedOpticItem(p, SpiritTypeRegistry.INFERNAL_SPIRIT));
    public static final RegistryObject<Item> ATTUNED_OPTIC_EARTHEN = register("earthen_tuned_optic", DEFAULT_PROPERTIES(), (p) -> new TunedOpticItem(p, SpiritTypeRegistry.EARTHEN_SPIRIT));
    public static final RegistryObject<Item> POPPET = register("poppet", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CORRUPTED_RESONANCE = register("corrupted_resonance", DEFAULT_PROPERTIES(), Item::new);

    public static final RegistryObject<Item> HALLOWED_GOLD_INGOT = register("hallowed_gold_ingot", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> HALLOWED_GOLD_NUGGET = register("hallowed_gold_nugget", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> BLOCK_OF_HALLOWED_GOLD = register("block_of_hallowed_gold", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_HALLOWED_GOLD.get(), p));

    public static final RegistryObject<Item> SOUL_STAINED_STEEL_INGOT = register("soul_stained_steel_ingot", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_NUGGET = register("soul_stained_steel_nugget", DEFAULT_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> BLOCK_OF_SOUL_STAINED_STEEL = register("block_of_soul_stained_steel", DEFAULT_PROPERTIES(), (p) -> new BlockItem(BlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get(), p));

    public static final RegistryObject<CrackedImpetusItem> CRACKED_IRON_IMPETUS = register("cracked_iron_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> IRON_IMPETUS = register("iron_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_IRON_IMPETUS));
    public static final RegistryObject<Item> IRON_NODE = register("iron_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_COPPER_IMPETUS = register("cracked_copper_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> COPPER_IMPETUS = register("copper_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_COPPER_IMPETUS));
    public static final RegistryObject<Item> COPPER_NODE = register("copper_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_GOLD_IMPETUS = register("cracked_gold_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> GOLD_IMPETUS = register("gold_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_GOLD_IMPETUS));
    public static final RegistryObject<Item> GOLD_NODE = register("gold_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_LEAD_IMPETUS = register("cracked_lead_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> LEAD_IMPETUS = register("lead_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_LEAD_IMPETUS));
    public static final RegistryObject<Item> LEAD_NODE = register("lead_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_SILVER_IMPETUS = register("cracked_silver_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> SILVER_IMPETUS = register("silver_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_SILVER_IMPETUS));
    public static final RegistryObject<Item> SILVER_NODE = register("silver_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_ALUMINUM_IMPETUS = register("cracked_aluminum_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> ALUMINUM_IMPETUS = register("aluminum_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ALUMINUM_IMPETUS));
    public static final RegistryObject<Item> ALUMINUM_NODE = register("aluminum_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_NICKEL_IMPETUS = register("cracked_nickel_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> NICKEL_IMPETUS = register("nickel_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_NICKEL_IMPETUS));
    public static final RegistryObject<Item> NICKEL_NODE = register("nickel_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_URANIUM_IMPETUS = register("cracked_uranium_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> URANIUM_IMPETUS = register("uranium_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_URANIUM_IMPETUS));
    public static final RegistryObject<Item> URANIUM_NODE = register("uranium_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_OSMIUM_IMPETUS = register("cracked_osmium_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> OSMIUM_IMPETUS = register("osmium_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_OSMIUM_IMPETUS));
    public static final RegistryObject<Item> OSMIUM_NODE = register("osmium_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_ZINC_IMPETUS = register("cracked_zinc_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> ZINC_IMPETUS = register("zinc_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ZINC_IMPETUS));
    public static final RegistryObject<Item> ZINC_NODE = register("zinc_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);
    public static final RegistryObject<CrackedImpetusItem> CRACKED_TIN_IMPETUS = register("cracked_tin_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> TIN_IMPETUS = register("tin_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_TIN_IMPETUS));
    public static final RegistryObject<Item> TIN_NODE = register("tin_node", METALLURGIC_NODE_PROPERTIES(), NodeItem::new);

    public static final RegistryObject<CrackedImpetusItem> CRACKED_ALCHEMICAL_IMPETUS = register("cracked_alchemical_impetus", METALLURGIC_PROPERTIES(), CrackedImpetusItem::new);
    public static final RegistryObject<ImpetusItem> ALCHEMICAL_IMPETUS = register("alchemical_impetus", METALLURGIC_PROPERTIES().durability(100), (p) -> new ImpetusItem(p).setCrackedVariant(CRACKED_ALCHEMICAL_IMPETUS));
    //endregion

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

    //region contents
    public static final RegistryObject<Item> SPIRIT_POUCH = register("spirit_pouch", GEAR_PROPERTIES(), SpiritPouchItem::new);
    public static final RegistryObject<Item> CRUDE_SCYTHE = register("crude_scythe", GEAR_PROPERTIES(), (p) -> new MalumScytheItem(Tiers.IRON, 0, 0.1f, p.durability(500)));
    public static final RegistryObject<Item> SOUL_STAINED_STEEL_SCYTHE = register("soul_stained_steel_scythe", GEAR_PROPERTIES(), (p) -> new MagicScytheItem(SOUL_STAINED_STEEL, -2.5f, 0.1f, 4, p));
    public static final RegistryObject<Item> SOULWOOD_STAVE = register("soulwood_stave", HIDDEN_PROPERTIES(), SoulStaveItem::new);

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
    
    public static final RegistryObject<Item> ETHERIC_NITRATE = register("etheric_nitrate", DEFAULT_PROPERTIES(), EthericNitrateItem::new);
    public static final RegistryObject<Item> VIVID_NITRATE = register("vivid_nitrate", DEFAULT_PROPERTIES(), VividNitrateItem::new);

    public static final RegistryObject<Item> GILDED_RING = register("gilded_ring", GEAR_PROPERTIES(), CurioGildedRing::new);
    public static final RegistryObject<Item> GILDED_BELT = register("gilded_belt", GEAR_PROPERTIES(), CurioGildedBelt::new);
    public static final RegistryObject<Item> ORNATE_RING = register("ornate_ring", GEAR_PROPERTIES(), CurioOrnateRing::new);
    public static final RegistryObject<Item> ORNATE_NECKLACE = register("ornate_necklace", GEAR_PROPERTIES(), CurioOrnateNecklace::new);

    public static final RegistryObject<Item> RING_OF_ESOTERIC_SPOILS = register("ring_of_esoteric_spoils", GEAR_PROPERTIES(), CurioArcaneSpoilRing::new);
    public static final RegistryObject<Item> RING_OF_CURATIVE_TALENT = register("ring_of_curative_talent", GEAR_PROPERTIES(), CurioCurativeRing::new);
    public static final RegistryObject<Item> RING_OF_ARCANE_PROWESS = register("ring_of_arcane_prowess", GEAR_PROPERTIES(), CurioRingOfProwess::new);
    public static final RegistryObject<Item> RING_OF_ALCHEMICAL_MASTERY = register("ring_of_alchemical_mastery", GEAR_PROPERTIES(), CurioAlchemicalRing::new);
    public static final RegistryObject<Item> RING_OF_DESPERATE_VORACITY = register("ring_of_desperate_voracity", GEAR_PROPERTIES(), CurioVoraciousRing::new);
    public static final RegistryObject<Item> RING_OF_THE_HOARDER = register("ring_of_the_hoarder", GEAR_PROPERTIES(), CurioHoarderRing::new);
    public static final RegistryObject<Item> RING_OF_THE_DEMOLITIONIST = register("ring_of_the_demolitionist", GEAR_PROPERTIES(), CurioDemolitionistRing::new);

    public static final RegistryObject<Item> NECKLACE_OF_THE_MYSTIC_MIRROR = register("necklace_of_the_mystic_mirror", GEAR_PROPERTIES(), CurioMirrorNecklace::new);
    public static final RegistryObject<Item> NECKLACE_OF_TIDAL_AFFINITY = register("necklace_of_tidal_affinity", GEAR_PROPERTIES(), CurioWaterNecklace::new);
    public static final RegistryObject<Item> NECKLACE_OF_THE_NARROW_EDGE = register("necklace_of_the_narrow_edge", GEAR_PROPERTIES(), CurioNarrowNecklace::new);
    public static final RegistryObject<Item> NECKLACE_OF_BLISSFUL_HARMONY = register("necklace_of_blissful_harmony", GEAR_PROPERTIES(), CurioHarmonyNecklace::new);

    public static final RegistryObject<Item> BELT_OF_THE_STARVED = register("belt_of_the_starved", GEAR_PROPERTIES(), CurioStarvedBelt::new);
    public static final RegistryObject<Item> BELT_OF_THE_PROSPECTOR = register("belt_of_the_prospector", GEAR_PROPERTIES(), CurioProspectorBelt::new);
    public static final RegistryObject<Item> BELT_OF_THE_MAGEBANE = register("belt_of_the_magebane", GEAR_PROPERTIES(), CurioMagebaneBelt::new);
    //endregion

    //region chronicles of the void
    public static final RegistryObject<Item> VOID_SALTS = register("void_salts", VOID_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> NULL_SLATE = register("null_slate", VOID_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> STRANGE_NUCLEUS = register("strange_nucleus", VOID_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> CRYSTALLIZED_NIHILITY = register("crystallized_nihility", VOID_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> ANOMALOUS_DESIGN = register("anomalous_design", VOID_PROPERTIES(), Item::new);
    public static final RegistryObject<Item> COMPLETE_DESIGN = register("complete_design", VOID_PROPERTIES(), SimpleFoiledItem::new);
    public static final RegistryObject<Item> FUSED_CONSCIOUSNESS = register("fused_consciousness", VOID_PROPERTIES(), (p) -> new FusedConsciousnessItem(p.rarity(UNCOMMON)));

    public static final RegistryObject<Item> RING_OF_GROWING_FLESH = register("ring_of_growing_flesh", VOID_GEAR_PROPERTIES(), CurioGrowingFleshRing::new);
    public static final RegistryObject<Item> RING_OF_GRUESOME_SATIATION = register("ring_of_gruesome_satiation", VOID_GEAR_PROPERTIES(), CurioGruesomeSatiationRing::new);

    public static final RegistryObject<Item> NECKLACE_OF_THE_HIDDEN_BLADE = register("necklace_of_the_hidden_blade", VOID_GEAR_PROPERTIES(), CurioHiddenBladeNecklace::new);
    public static final RegistryObject<Item> NECKLACE_OF_THE_WATCHER = register("necklace_of_the_watcher", VOID_GEAR_PROPERTIES(), CurioWatcherNecklace::new);


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
        public static void setItemColors(RegisterColorHandlersEvent.Item event) {
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
