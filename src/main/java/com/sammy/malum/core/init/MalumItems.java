package com.sammy.malum.core.init;

import com.sammy.malum.core.MalumCreativeTab;
import com.sammy.malum.core.systems.multiblock.MultiblockItem;
import com.sammy.malum.core.systems.multiblock.MultiblockStructure;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;

@SuppressWarnings("unused")
public class MalumItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final Item.Properties DEFAULT_PROPERTIES = new Item.Properties().group(MalumCreativeTab.INSTANCE);
    
    //region tainted rock
    public static final RegistryObject<Item> TAINTED_ROCK = ITEMS.register("tainted_rock", () -> new BlockItem(MalumBlocks.TAINTED_ROCK.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_ROCK_SLAB = ITEMS.register("tainted_rock_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_ROCK_STAIRS = ITEMS.register("tainted_rock_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK = ITEMS.register("polished_tainted_rock", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_SLAB = ITEMS.register("polished_tainted_rock_slab", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> POLISHED_TAINTED_ROCK_STAIRS = ITEMS.register("polished_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.POLISHED_TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK = ITEMS.register("smooth_tainted_rock", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_SLAB = ITEMS.register("smooth_tainted_rock_slab", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK_SLAB.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SMOOTH_TAINTED_ROCK_STAIRS = ITEMS.register("smooth_tainted_rock_stairs", () -> new BlockItem(MalumBlocks.SMOOTH_TAINTED_ROCK_STAIRS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS = ITEMS.register("tainted_rock_bricks", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS = ITEMS.register("cracked_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("cracked_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("cracked_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS = ITEMS.register("mossy_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_SLAB = ITEMS.register("mossy_tainted_rock_bricks_slab", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_SLAB.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_STAIRS = ITEMS.register("mossy_tainted_rock_bricks_stairs", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_STAIRS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> TAINTED_ROCK_PILLAR = ITEMS.register("tainted_rock_pillar", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PILLAR.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> CHISELED_TAINTED_ROCK_BRICKS = ITEMS.register("chiseled_tainted_rock_bricks", () -> new BlockItem(MalumBlocks.CHISELED_TAINTED_ROCK_BRICKS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> TAINTED_ROCK_PRESSURE_PLATE = ITEMS.register("tainted_rock_pressure_plate", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_PRESSURE_PLATE.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_ROCK_WALL = ITEMS.register("tainted_rock_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_WALL.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_ROCK_BRICKS_WALL = ITEMS.register("tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.TAINTED_ROCK_BRICKS_WALL.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> MOSSY_TAINTED_ROCK_BRICKS_WALL = ITEMS.register("mossy_tainted_rock_bricks_wall", () -> new BlockItem(MalumBlocks.MOSSY_TAINTED_ROCK_BRICKS_WALL.get(), DEFAULT_PROPERTIES));
    //endregion
    
    //region sun kissed wood
    public static final RegistryObject<Item> SUN_KISSED_LOG = ITEMS.register("sun_kissed_log", () -> new BlockItem(MalumBlocks.SUN_KISSED_LOG.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> STRIPPED_SUN_KISSED_LOG = ITEMS.register("stripped_sun_kissed_log", () -> new BlockItem(MalumBlocks.STRIPPED_SUN_KISSED_LOG.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SUN_KISSED_WOOD = ITEMS.register("sun_kissed_wood", () -> new BlockItem(MalumBlocks.SUN_KISSED_WOOD.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> STRIPPED_SUN_KISSED_WOOD = ITEMS.register("stripped_sun_kissed_wood", () -> new BlockItem(MalumBlocks.STRIPPED_SUN_KISSED_WOOD.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> SUN_KISSED_PLANKS = ITEMS.register("sun_kissed_planks", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_SLAB = ITEMS.register("sun_kissed_planks_slab", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_SLAB.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_STAIRS = ITEMS.register("sun_kissed_planks_stairs", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_STAIRS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> SUN_KISSED_DOOR = ITEMS.register("sun_kissed_door", () -> new BlockItem(MalumBlocks.SUN_KISSED_DOOR.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SUN_KISSED_TRAPDOOR = ITEMS.register("sun_kissed_trapdoor", () -> new BlockItem(MalumBlocks.SUN_KISSED_TRAPDOOR.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SOLID_SUN_KISSED_TRAPDOOR = ITEMS.register("solid_sun_kissed_trapdoor", () -> new BlockItem(MalumBlocks.SOLID_SUN_KISSED_TRAPDOOR.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_BUTTON = ITEMS.register("sun_kissed_planks_button", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_BUTTON.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_FENCE = ITEMS.register("sun_kissed_planks_fence", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_FENCE.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SUN_KISSED_PLANKS_FENCE_GATE = ITEMS.register("sun_kissed_planks_fence_gate", () -> new BlockItem(MalumBlocks.SUN_KISSED_PLANKS_FENCE_GATE.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> SUN_KISSED_LEAVES = ITEMS.register("sun_kissed_leaves", () -> new BlockItem(MalumBlocks.SUN_KISSED_LEAVES.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SUN_KISSED_SAPLING = ITEMS.register("sun_kissed_sapling", () -> new BlockItem(MalumBlocks.SUN_KISSED_SAPLING.get(), DEFAULT_PROPERTIES));
    //endregion
    
    //region tainted wood
    public static final RegistryObject<Item> TAINTED_LOG = ITEMS.register("tainted_log", () -> new BlockItem(MalumBlocks.TAINTED_LOG.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> STRIPPED_TAINTED_LOG = ITEMS.register("stripped_tainted_log", () -> new BlockItem(MalumBlocks.STRIPPED_TAINTED_LOG.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_WOOD = ITEMS.register("tainted_wood", () -> new BlockItem(MalumBlocks.TAINTED_WOOD.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> STRIPPED_TAINTED_WOOD = ITEMS.register("stripped_tainted_wood", () -> new BlockItem(MalumBlocks.STRIPPED_TAINTED_WOOD.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> TAINTED_PLANKS = ITEMS.register("tainted_planks", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_PLANKS_SLAB = ITEMS.register("tainted_planks_slab", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_SLAB.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_PLANKS_STAIRS = ITEMS.register("tainted_planks_stairs", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_STAIRS.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> TAINTED_DOOR = ITEMS.register("tainted_door", () -> new BlockItem(MalumBlocks.TAINTED_DOOR.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_TRAPDOOR = ITEMS.register("tainted_trapdoor", () -> new BlockItem(MalumBlocks.TAINTED_TRAPDOOR.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SOLID_TAINTED_TRAPDOOR = ITEMS.register("solid_tainted_trapdoor", () -> new BlockItem(MalumBlocks.SOLID_TAINTED_TRAPDOOR.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> TAINTED_PLANKS_BUTTON = ITEMS.register("tainted_planks_button", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_BUTTON.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> TAINTED_PLANKS_FENCE = ITEMS.register("tainted_planks_fence", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_FENCE.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_PLANKS_FENCE_GATE = ITEMS.register("tainted_planks_fence_gate", () -> new BlockItem(MalumBlocks.TAINTED_PLANKS_FENCE_GATE.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> TAINTED_LEAVES = ITEMS.register("tainted_leaves", () -> new BlockItem(MalumBlocks.TAINTED_LEAVES.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_SAPLING = ITEMS.register("tainted_sapling", () -> new BlockItem(MalumBlocks.TAINTED_SAPLING.get(), DEFAULT_PROPERTIES));
    //endregion
    
    //region sun kissed biome plants
    public static final RegistryObject<Item> SHORT_SUN_KISSED_GRASS = ITEMS.register("short_sun_kissed_grass", () -> new BlockItem(MalumBlocks.SHORT_SUN_KISSED_GRASS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SUN_KISSED_GRASS = ITEMS.register("sun_kissed_grass", () -> new BlockItem(MalumBlocks.SUN_KISSED_GRASS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TALL_SUN_KISSED_GRASS = ITEMS.register("tall_sun_kissed_grass", () -> new BlockItem(MalumBlocks.TALL_SUN_KISSED_GRASS.get(), DEFAULT_PROPERTIES));
    //endregion
    
    //region tainted biome plants
    public static final RegistryObject<Item> SHORT_TAINTED_GRASS = ITEMS.register("short_tainted_grass", () -> new BlockItem(MalumBlocks.SHORT_TAINTED_GRASS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TAINTED_GRASS = ITEMS.register("tainted_grass", () -> new BlockItem(MalumBlocks.TAINTED_GRASS.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> TALL_TAINTED_GRASS = ITEMS.register("tall_tainted_grass", () -> new BlockItem(MalumBlocks.TALL_TAINTED_GRASS.get(), DEFAULT_PROPERTIES));
    //endregion
    
    //region crafting blocks
    public static final RegistryObject<Item> TAINTED_ARCANE_CRAFTING_TABLE = ITEMS.register("sun_kissed_arcane_crafting_table", () -> new BlockItem(MalumBlocks.TAINTED_ARCANE_CRAFTING_TABLE.get(), DEFAULT_PROPERTIES));
    public static final RegistryObject<Item> SUN_KISSED_ARCANE_CRAFTING_TABLE = ITEMS.register("tainted_arcane_crafting_table", () -> new BlockItem(MalumBlocks.SUN_KISSED_ARCANE_CRAFTING_TABLE.get(), DEFAULT_PROPERTIES));
    
    public static final RegistryObject<Item> BLIGHTING_FURNACE = ITEMS.register("blighting_furnace", () -> new MultiblockItem(MalumBlocks.BLIGHTING_FURNACE.get(), DEFAULT_PROPERTIES, MultiblockStructure.doubleTallBlock(MalumBlocks.BLIGHTING_FURNACE_TOP.get())));
    
    //endregion
}