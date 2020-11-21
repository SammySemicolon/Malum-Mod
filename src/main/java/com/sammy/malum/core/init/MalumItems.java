package com.sammy.malum.core.init;

import com.sammy.malum.common.items.tools.scythes.CreativeScythe;
import com.sammy.malum.common.items.equipment.armor.RunicGoldArmor;
import com.sammy.malum.common.items.tools.scythes.ScytheItem;
import com.sammy.malum.core.MalumCreativeTab;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.essences.EssenceHolderBlockitem;
import com.sammy.malum.core.systems.essences.EssenceHolderItem;
import com.sammy.malum.core.systems.multiblock.MultiblockItem;
import com.sammy.malum.core.systems.multiblock.MultiblockStructure;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

@SuppressWarnings("unused")
public class MalumItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static Item.Properties DEFAULT_PROPERTIES()
    {
        return new Item.Properties().group(MalumCreativeTab.INSTANCE);
    }
    public static Item.Properties GEAR_PROPERTIES()
    {
        return new Item.Properties().group(MalumCreativeTab.INSTANCE).maxStackSize(1);
    }
    public static Item.Properties CREATIVE_PROPERTIES()
    {
        return new Item.Properties().maxStackSize(1);
    }
    
    public static final RegistryObject<Item> SOLAR_ORE = ITEMS.register("solar_ore", () -> new BlockItem(MalumBlocks.SOLAR_ORE.get(), DEFAULT_PROPERTIES()));
    
    //region tainted rock
    public static final RegistryObject<Item> TAINTED_ROCK = ITEMS.register("tainted_rock", () -> new BlockItem(MalumBlocks.TAINTED_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_SLAB = ITEMS.register("tainted_rock_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_STAIRS = ITEMS.register("tainted_rock_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK = ITEMS.register("polished_tainted_rock", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_SLAB = ITEMS.register("polished_tainted_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_STAIRS = ITEMS.register("polished_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK = ITEMS.register("smooth_tainted_rock", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_SLAB = ITEMS.register("smooth_tainted_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_STAIRS = ITEMS.register("smooth_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS = ITEMS.register("tainted_rock_bricks", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS = ITEMS.register("cracked_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS = ITEMS.register("mossy_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("mossy_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("mossy_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_ROCK_PILLAR = ITEMS.register("tainted_rock_pillar", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PILLAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK_BRICKS = ITEMS.register("chiseled_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CHISELED_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = ITEMS.register("tainted_rock_pressure_plate", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PRESSURE_PLATE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_WALL = ITEMS.register("tainted_rock_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_WALL = ITEMS.register("tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("mossy_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_LANTERN = ITEMS.register("tainted_lantern", () -> new BlockItem(MalumBlocks.TAINTED_LANTERN.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> ZOOM_ROCK = ITEMS.register("zoom_rock", () -> new BlockItem(MalumBlocks.ZOOM_ROCK.get(), DEFAULT_PROPERTIES()));
    //endregion
    
    //region darkened tainted rock
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK = ITEMS.register("darkened_tainted_rock", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_SLAB = ITEMS.register("darkened_tainted_rock_slab", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_STAIRS = ITEMS.register("darkened_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> POLISHED_DARKENED_TAINTED_ROCK = ITEMS.register("polished_darkened_tainted_rock", () -> new BlockItem(MalumBlocks.POLISHED_DARKENED_TAINTED_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_DARKENED_TAINTED_ROCK_SLAB = ITEMS.register("polished_darkened_tainted_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_DARKENED_TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_DARKENED_TAINTED_ROCK_STAIRS = ITEMS.register("polished_darkened_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_DARKENED_TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SMOOTH_DARKENED_TAINTED_ROCK = ITEMS.register("smooth_darkened_tainted_rock", () -> new BlockItem(MalumBlocks.SMOOTH_DARKENED_TAINTED_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_DARKENED_TAINTED_ROCK_SLAB = ITEMS.register("smooth_darkened_tainted_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_DARKENED_TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_DARKENED_TAINTED_ROCK_STAIRS = ITEMS.register("smooth_darkened_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_DARKENED_TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_BRICKS = ITEMS.register("darkened_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("darkened_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("darkened_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> CRACKED_DARKENED_TAINTED_ROCK_BRICKS = ITEMS.register("cracked_darkened_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_darkened_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_DARKENED_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_darkened_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_DARKENED_TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> MOSSY_DARKENED_TAINTED_ROCK_BRICKS = ITEMS.register("mossy_darkened_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("mossy_darkened_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("mossy_darkened_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_PILLAR = ITEMS.register("darkened_tainted_rock_pillar", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_PILLAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_DARKENED_TAINTED_ROCK_BRICKS = ITEMS.register("chiseled_darkened_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CHISELED_DARKENED_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_PRESSURE_PLATE = ITEMS.register("darkened_tainted_rock_pressure_plate", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_PRESSURE_PLATE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_WALL = ITEMS.register("darkened_tainted_rock_wall", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("darkened_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_ROCK_BRICKS_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_DARKENED_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("mossy_darkened_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.MOSSY_DARKENED_TAINTED_ROCK_BRICKS_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_TAINTED_LANTERN = ITEMS.register("darkened_tainted_lantern", () -> new BlockItem(MalumBlocks.DARKENED_TAINTED_LANTERN.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> DARKENED_ZOOM_ROCK = ITEMS.register("darkened_zoom_rock", () -> new BlockItem(MalumBlocks.DARKENED_ZOOM_ROCK.get(), DEFAULT_PROPERTIES()));
    //endregion
    
    //region crimson rock
    public static final RegistryObject<Item> CRIMSON_ROCK = ITEMS.register("crimson_rock", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRIMSON_ROCK_SLAB = ITEMS.register("crimson_rock_slab", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRIMSON_ROCK_STAIRS = ITEMS.register("crimson_rock_stairs", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> POLISHED_CRIMSON_ROCK = ITEMS.register("polished_crimson_rock", () -> new BlockItem(MalumBlocks.POLISHED_CRIMSON_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CRIMSON_ROCK_SLAB = ITEMS.register("polished_crimson_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_CRIMSON_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> POLISHED_CRIMSON_ROCK_STAIRS = ITEMS.register("polished_crimson_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_CRIMSON_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SMOOTH_CRIMSON_ROCK = ITEMS.register("smooth_crimson_rock", () -> new BlockItem(MalumBlocks.SMOOTH_CRIMSON_ROCK.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_CRIMSON_ROCK_SLAB = ITEMS.register("smooth_crimson_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_CRIMSON_ROCK_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SMOOTH_CRIMSON_ROCK_STAIRS = ITEMS.register("smooth_crimson_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_CRIMSON_ROCK_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> CRIMSON_ROCK_BRICKS = ITEMS.register("crimson_rock_bricks", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRIMSON_ROCK_BRICKS_SLAB = ITEMS.register("crimson_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRIMSON_ROCK_BRICKS_STAIRS = ITEMS.register("crimson_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> CRACKED_CRIMSON_ROCK_BRICKS = ITEMS.register("cracked_crimson_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_CRIMSON_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CRIMSON_ROCK_BRICKS_SLAB = ITEMS.register("cracked_crimson_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_CRIMSON_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRACKED_CRIMSON_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_crimson_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_CRIMSON_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> MOSSY_CRIMSON_ROCK_BRICKS = ITEMS.register("mossy_crimson_rock_bricks", () -> new BlockItem(MalumBlocks.MOSSY_CRIMSON_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_CRIMSON_ROCK_BRICKS_SLAB = ITEMS.register("mossy_crimson_rock_bricks_slab", () -> new BlockItem(MalumBlocks.MOSSY_CRIMSON_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_CRIMSON_ROCK_BRICKS_STAIRS = ITEMS.register("mossy_crimson_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.MOSSY_CRIMSON_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> CRIMSON_ROCK_PILLAR = ITEMS.register("crimson_rock_pillar", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_PILLAR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CHISELED_CRIMSON_ROCK_BRICKS = ITEMS.register("chiseled_crimson_rock_bricks", () -> new BlockItem(MalumBlocks.CHISELED_CRIMSON_ROCK_BRICKS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> CRIMSON_ROCK_PRESSURE_PLATE = ITEMS.register("crimson_rock_pressure_plate", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_PRESSURE_PLATE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRIMSON_ROCK_WALL = ITEMS.register("crimson_rock_wall", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRIMSON_ROCK_BRICKS_WALL = ITEMS.register("crimson_rock_bricks_wall", () -> new BlockItem(MalumBlocks.CRIMSON_ROCK_BRICKS_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> MOSSY_CRIMSON_ROCK_BRICKS_WALL = ITEMS.register("mossy_crimson_rock_bricks_wall", () -> new BlockItem(MalumBlocks.MOSSY_CRIMSON_ROCK_BRICKS_WALL.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRIMSON_LANTERN = ITEMS.register("crimson_lantern", () -> new BlockItem(MalumBlocks.CRIMSON_LANTERN.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CRIMSON_ZOOM_ROCK = ITEMS.register("crimson_zoom_rock", () -> new BlockItem(MalumBlocks.CRIMSON_ZOOM_ROCK.get(), DEFAULT_PROPERTIES()));
    
    //endregion
    
    //region sun kissed wood
    public static final RegistryObject<Item> SUN_KISSED_LOG = ITEMS.register("sun_kissed_log", () -> new BlockItem(MalumBlocks.SUN_KISSED_LOG.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_SUN_KISSED_LOG = ITEMS.register("stripped_sun_kissed_log", () -> new BlockItem(MalumBlocks.STRIPPED_SUN_KISSED_LOG.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_WOOD = ITEMS.register("sun_kissed_wood", () -> new BlockItem(MalumBlocks.SUN_KISSED_WOOD.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_SUN_KISSED_WOOD = ITEMS.register("stripped_sun_kissed_wood", () -> new BlockItem(MalumBlocks.STRIPPED_SUN_KISSED_WOOD.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SUN_KISSED_PLANKS = ITEMS.register("sun_kissed_planks", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_SLAB = ITEMS.register("sun_kissed_planks_slab", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_STAIRS = ITEMS.register("sun_kissed_planks_stairs", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SUN_KISSED_DOOR = ITEMS.register("sun_kissed_door", () -> new BlockItem(MalumBlocks.SUN_KISSED_DOOR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_TRAPDOOR = ITEMS.register("sun_kissed_trapdoor", () -> new BlockItem(MalumBlocks.SUN_KISSED_TRAPDOOR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOLID_SUN_KISSED_TRAPDOOR = ITEMS.register("solid_sun_kissed_trapdoor", () -> new BlockItem(MalumBlocks.SOLID_SUN_KISSED_TRAPDOOR.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_BUTTON = ITEMS.register("sun_kissed_planks_button", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_BUTTON.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_FENCE = ITEMS.register("sun_kissed_planks_fence", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_FENCE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_FENCE_GATE = ITEMS.register("sun_kissed_planks_fence_gate", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_FENCE_GATE.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> SUN_KISSED_LEAVES = ITEMS.register("sun_kissed_leaves", () -> new BlockItem(MalumBlocks.SUN_KISSED_LEAVES.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_SAPLING = ITEMS.register("sun_kissed_sapling", () -> new BlockItem(MalumBlocks.SUN_KISSED_SAPLING.get(), DEFAULT_PROPERTIES()));
    //endregion
    
    //region tainted wood
    public static final RegistryObject<Item> TAINTED_LOG = ITEMS.register("tainted_log", () -> new BlockItem(MalumBlocks.TAINTED_LOG.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_TAINTED_LOG = ITEMS.register("stripped_tainted_log", () -> new BlockItem(MalumBlocks.STRIPPED_TAINTED_LOG.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_WOOD = ITEMS.register("tainted_wood", () -> new BlockItem(MalumBlocks.TAINTED_WOOD.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> STRIPPED_TAINTED_WOOD = ITEMS.register("stripped_tainted_wood", () -> new BlockItem(MalumBlocks.STRIPPED_TAINTED_WOOD.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_PLANKS = ITEMS.register("tainted_planks", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_PLANKS_SLAB = ITEMS.register("tainted_planks_slab", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_SLAB.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_PLANKS_STAIRS = ITEMS.register("tainted_planks_stairs", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_STAIRS.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_DOOR = ITEMS.register("tainted_door", () -> new BlockItem(MalumBlocks.TAINTED_DOOR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_TRAPDOOR = ITEMS.register("tainted_trapdoor", () -> new BlockItem(MalumBlocks.TAINTED_TRAPDOOR.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SOLID_TAINTED_TRAPDOOR = ITEMS.register("solid_tainted_trapdoor", () -> new BlockItem(MalumBlocks.SOLID_TAINTED_TRAPDOOR.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_PLANKS_BUTTON = ITEMS.register("tainted_planks_button", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_BUTTON.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_PLANKS_FENCE = ITEMS.register("tainted_planks_fence", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_FENCE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_PLANKS_FENCE_GATE = ITEMS.register("tainted_planks_fence_gate", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_FENCE_GATE.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> TAINTED_LEAVES = ITEMS.register("tainted_leaves", () -> new BlockItem(MalumBlocks.TAINTED_LEAVES.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_SAPLING = ITEMS.register("tainted_sapling", () -> new BlockItem(MalumBlocks.TAINTED_SAPLING.get(), DEFAULT_PROPERTIES()));
    //endregion
    
    //region sun kissed biome plants
    public static final RegistryObject<Item> SHORT_SUN_KISSED_GRASS = ITEMS.register("short_sun_kissed_grass", () -> new BlockItem(MalumBlocks.SHORT_SUN_KISSED_GRASS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_GRASS = ITEMS.register("sun_kissed_grass", () -> new BlockItem(MalumBlocks.SUN_KISSED_GRASS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TALL_SUN_KISSED_GRASS = ITEMS.register("tall_sun_kissed_grass", () -> new BlockItem(MalumBlocks.TALL_SUN_KISSED_GRASS.get(), DEFAULT_PROPERTIES()));
    //endregion
    
    //region tainted biome plants
    public static final RegistryObject<Item> SHORT_TAINTED_GRASS = ITEMS.register("short_tainted_grass", () -> new BlockItem(MalumBlocks.SHORT_TAINTED_GRASS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TAINTED_GRASS = ITEMS.register("tainted_grass", () -> new BlockItem(MalumBlocks.TAINTED_GRASS.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> TALL_TAINTED_GRASS = ITEMS.register("tall_tainted_grass", () -> new BlockItem(MalumBlocks.TALL_TAINTED_GRASS.get(), DEFAULT_PROPERTIES()));
    //endregion
    
    //region biome blocks
    public static final RegistryObject<Item> LAVENDER = ITEMS.register("lavender", () -> new BlockItem(MalumBlocks.LAVENDER.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> CORNFLOWER = ITEMS.register("cornflower", () -> new BlockItem(MalumBlocks.CORNFLOWER.get(), DEFAULT_PROPERTIES()));
    
    //endregion
    
    //region crafting blocks
    public static final RegistryObject<Item> TAINTED_ARCANE_CRAFTING_TABLE = ITEMS.register("sun_kissed_arcane_crafting_table", () -> new BlockItem(MalumBlocks.TAINTED_ARCANE_CRAFTING_TABLE.get(), DEFAULT_PROPERTIES()));
    public static final RegistryObject<Item> SUN_KISSED_ARCANE_CRAFTING_TABLE = ITEMS.register("tainted_arcane_crafting_table", () -> new BlockItem(MalumBlocks.SUN_KISSED_ARCANE_CRAFTING_TABLE.get(), DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> BLIGHTING_FURNACE = ITEMS.register("blighting_furnace", () -> new MultiblockItem(MalumBlocks.BLIGHTING_FURNACE.get(), DEFAULT_PROPERTIES(), MultiblockStructure.doubleTallBlock(MalumBlocks.BLIGHTING_FURNACE_TOP.get())));
    
    //endregion
    
    //region combined components
    public static final RegistryObject<Item> ILLUSTRIOUS_FABRIC = ITEMS.register("illustrious_fabric", () -> new Item(DEFAULT_PROPERTIES()));
    
    public static final RegistryObject<Item> DARK_FLARES = ITEMS.register("dark_flares", () -> new Item(DEFAULT_PROPERTIES()));
    //endregion
    
    //region contents
    public static final RegistryObject<Item> RUNIC_GOLD_HELMET = ITEMS.register("runic_gold_helmet", () -> new RunicGoldArmor(ArmorMaterial.GOLD, EquipmentSlotType.HEAD, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_GOLD_CHESTPLATE = ITEMS.register("runic_gold_chestplate", () -> new RunicGoldArmor(ArmorMaterial.GOLD, EquipmentSlotType.CHEST, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_GOLD_LEGGINGS = ITEMS.register("runic_gold_leggings", () -> new RunicGoldArmor(ArmorMaterial.GOLD, EquipmentSlotType.LEGS, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_GOLD_BOOTS = ITEMS.register("runic_gold_boots", () -> new RunicGoldArmor(ArmorMaterial.GOLD, EquipmentSlotType.FEET, GEAR_PROPERTIES()));
    
    public static final RegistryObject<Item> ABSTRUSE_BLOCK = ITEMS.register("abstruse_block", () -> new BlockItem(MalumBlocks.ABSTRUSE_BLOCK.get(), DEFAULT_PROPERTIES()));
    
    //endregion
    
    //region essence items
    public static final RegistryObject<Item> ESSENCE_CAPACITOR = ITEMS.register("essence_capacitor", () -> new EssenceHolderItem(GEAR_PROPERTIES(), 1, 100));
    public static final RegistryObject<Item> ESSENCE_VAULT = ITEMS.register("essence_vault", () -> new EssenceHolderItem(GEAR_PROPERTIES(), 4, 25));
    public static final RegistryObject<Item> ESSENCE_JAR = ITEMS.register("essence_jar", () -> new EssenceHolderBlockitem(MalumBlocks.ESSENCE_JAR.get(),GEAR_PROPERTIES()));
    //endregion
    public static final RegistryObject<Item> SCYTHE_OF_SOL = ITEMS.register("scythe_of_sol", () -> new ScytheItem(ItemTier.STONE, 1,0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> SCYTHE_OF_LUNA = ITEMS.register("scythe_of_luna", () -> new ScytheItem(ItemTier.STONE, 1,0, GEAR_PROPERTIES()));
    public static final RegistryObject<Item> RUNIC_GOLD_SCYTHE = ITEMS.register("runic_gold_scythe", () -> new ScytheItem(ItemTier.STONE, 4,-0.2f, GEAR_PROPERTIES()));
    
    //region hidden items
    public static final RegistryObject<Item> CREATIVE_ESSENCE_VAULT = ITEMS.register("creative_essence_vault", () -> new EssenceHolderItem(CREATIVE_PROPERTIES(), 100, 9999));
    public static final RegistryObject<Item> CREATIVE_SCYTHE = ITEMS.register("creative_scythe", CreativeScythe::new);
    public static final RegistryObject<Item> SPIRIT_ESSENCE_ITEM = ITEMS.register("spirit_essence_item", ()-> new Item(CREATIVE_PROPERTIES()));
    //endregion
}