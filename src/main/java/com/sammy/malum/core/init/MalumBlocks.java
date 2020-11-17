package com.sammy.malum.core.init;

import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.common.blocks.arcanecraftingtable.ArcaneCraftingTableBlock;
import com.sammy.malum.common.blocks.blightingfurnace.BlightedFurnaceBoundingBlock;
import com.sammy.malum.common.blocks.blightingfurnace.BlightingFurnaceBlock;
import com.sammy.malum.common.world.features.tree.SunKissedTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

import static com.sammy.malum.MalumMod.MODID;
import static net.minecraft.block.PressurePlateBlock.Sensitivity.MOBS;


@SuppressWarnings("unused")
public class MalumBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    
    //region tainted rock
    public static AbstractBlock.Properties TAINTED_ROCK_PROPERTIES = AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(1.25F, 9.0F);
    public static final RegistryObject<Block> TAINTED_ROCK = BLOCKS.register("tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_ROCK_SLAB = BLOCKS.register("tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_ROCK_STAIRS = BLOCKS.register("tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK = BLOCKS.register("polished_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_SLAB = BLOCKS.register("polished_tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_STAIRS = BLOCKS.register("polished_tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK = BLOCKS.register("smooth_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_SLAB = BLOCKS.register("smooth_tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_STAIRS = BLOCKS.register("smooth_tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS = BLOCKS.register("tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS = BLOCKS.register("cracked_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS = BLOCKS.register("mossy_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("mossy_tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("mossy_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> TAINTED_ROCK_PILLAR = BLOCKS.register("tainted_rock_pillar", () -> new RotatedPillarBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> CHISELED_TAINTED_ROCK_BRICKS = BLOCKS.register("chiseled_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES));

    public static final RegistryObject<Block> TAINTED_ROCK_PRESSURE_PLATE = BLOCKS.register("tainted_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_ROCK_WALL = BLOCKS.register("tainted_rock_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("mossy_tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_LANTERN = BLOCKS.register("tainted_lantern", () -> new LanternBlock(AbstractBlock.Properties.from(Blocks.LANTERN)));
    //endregion
    
    public static AbstractBlock.Properties DARKENED_TAINTED_ROCK_PROPERTIES = AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(2.25F, 3600.0F);
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK = BLOCKS.register("darkened_tainted_rock", () -> new Block(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_SLAB = BLOCKS.register("darkened_tainted_rock_slab", () -> new SlabBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_STAIRS = BLOCKS.register("darkened_tainted_rock_stairs", () -> new StairsBlock(DARKENED_TAINTED_ROCK.get().getDefaultState(), DARKENED_TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> POLISHED_DARKENED_TAINTED_ROCK = BLOCKS.register("polished_darkened_tainted_rock", () -> new Block(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DARKENED_TAINTED_ROCK_SLAB = BLOCKS.register("polished_darkened_tainted_rock_slab", () -> new SlabBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> POLISHED_DARKENED_TAINTED_ROCK_STAIRS = BLOCKS.register("polished_darkened_tainted_rock_stairs", () -> new StairsBlock(DARKENED_TAINTED_ROCK.get().getDefaultState(), DARKENED_TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> SMOOTH_DARKENED_TAINTED_ROCK = BLOCKS.register("smooth_darkened_tainted_rock", () -> new Block(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> SMOOTH_DARKENED_TAINTED_ROCK_SLAB = BLOCKS.register("smooth_darkened_tainted_rock_slab", () -> new SlabBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> SMOOTH_DARKENED_TAINTED_ROCK_STAIRS = BLOCKS.register("smooth_darkened_tainted_rock_stairs", () -> new StairsBlock(DARKENED_TAINTED_ROCK.get().getDefaultState(), DARKENED_TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_BRICKS = BLOCKS.register("darkened_tainted_rock_bricks", () -> new Block(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("darkened_tainted_rock_bricks_slab", () -> new SlabBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("darkened_tainted_rock_bricks_stairs", () -> new StairsBlock(DARKENED_TAINTED_ROCK.get().getDefaultState(), DARKENED_TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> CRACKED_DARKENED_TAINTED_ROCK_BRICKS = BLOCKS.register("cracked_darkened_tainted_rock_bricks", () -> new Block(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> CRACKED_DARKENED_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_darkened_tainted_rock_bricks_slab", () -> new SlabBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> CRACKED_DARKENED_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_darkened_tainted_rock_bricks_stairs", () -> new StairsBlock(DARKENED_TAINTED_ROCK.get().getDefaultState(), DARKENED_TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> MOSSY_DARKENED_TAINTED_ROCK_BRICKS = BLOCKS.register("mossy_darkened_tainted_rock_bricks", () -> new Block(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> MOSSY_DARKENED_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("mossy_darkened_tainted_rock_bricks_slab", () -> new SlabBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> MOSSY_DARKENED_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("mossy_darkened_tainted_rock_bricks_stairs", () -> new StairsBlock(DARKENED_TAINTED_ROCK.get().getDefaultState(), DARKENED_TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_PILLAR = BLOCKS.register("darkened_tainted_rock_pillar", () -> new RotatedPillarBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> CHISELED_DARKENED_TAINTED_ROCK_BRICKS = BLOCKS.register("chiseled_darkened_tainted_rock_bricks", () -> new Block(DARKENED_TAINTED_ROCK_PROPERTIES));
    
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_PRESSURE_PLATE = BLOCKS.register("darkened_tainted_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_WALL = BLOCKS.register("darkened_tainted_rock_wall", () -> new WallBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> DARKENED_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("darkened_tainted_rock_bricks_wall", () -> new WallBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> MOSSY_DARKENED_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("mossy_darkened_tainted_rock_bricks_wall", () -> new WallBlock(DARKENED_TAINTED_ROCK_PROPERTIES));
    public static final RegistryObject<Block> DARKENED_TAINTED_LANTERN = BLOCKS.register("darkened_tainted_lantern", () -> new LanternBlock(AbstractBlock.Properties.from(Blocks.LANTERN)));
    
    //region sun kissed wood
    public static AbstractBlock.Properties SUN_KISSED_WOOD_PROPERTIES = AbstractBlock.Properties.create(Material.ROCK, MaterialColor.YELLOW).sound(SoundType.WOOD).hardnessAndResistance(1.75F, 4.0F);
    
    public static final RegistryObject<Block> SUN_KISSED_LOG = BLOCKS.register("sun_kissed_log", () -> new RotatedPillarBlock(SUN_KISSED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_SUN_KISSED_LOG = BLOCKS.register("stripped_sun_kissed_log", () -> new RotatedPillarBlock(SUN_KISSED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> SUN_KISSED_WOOD = BLOCKS.register("sun_kissed_wood", () -> new Block(SUN_KISSED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_SUN_KISSED_WOOD = BLOCKS.register("stripped_sun_kissed_wood", () -> new Block(SUN_KISSED_WOOD_PROPERTIES));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS = BLOCKS.register("sun_kissed_planks", () -> new Block(SUN_KISSED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_SLAB = BLOCKS.register("sun_kissed_planks_slab", () -> new SlabBlock(SUN_KISSED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_STAIRS = BLOCKS.register("sun_kissed_planks_stairs", () -> new StairsBlock(SUN_KISSED_PLANKS.get().getDefaultState(), SUN_KISSED_WOOD_PROPERTIES));
    
    public static final RegistryObject<Block> SUN_KISSED_DOOR = BLOCKS.register("sun_kissed_door", () -> new DoorBlock(SUN_KISSED_WOOD_PROPERTIES.notSolid()));
    public static final RegistryObject<Block> SUN_KISSED_TRAPDOOR = BLOCKS.register("sun_kissed_trapdoor", () -> new TrapDoorBlock(SUN_KISSED_WOOD_PROPERTIES.notSolid()));
    public static final RegistryObject<Block> SOLID_SUN_KISSED_TRAPDOOR = BLOCKS.register("solid_sun_kissed_trapdoor", () -> new TrapDoorBlock(SUN_KISSED_WOOD_PROPERTIES.notSolid()));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_BUTTON = BLOCKS.register("sun_kissed_planks_button", () -> new WoodButtonBlock(SUN_KISSED_WOOD_PROPERTIES));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_FENCE = BLOCKS.register("sun_kissed_planks_fence", () -> new FenceBlock(SUN_KISSED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_FENCE_GATE = BLOCKS.register("sun_kissed_planks_fence_gate", () -> new FenceGateBlock(SUN_KISSED_WOOD_PROPERTIES));
    
    //endregion
    
    //region tainted wood
    public static AbstractBlock.Properties TAINTED_WOOD_PROPERTIES = AbstractBlock.Properties.create(Material.WOOD, MaterialColor.PURPLE).sound(SoundType.WOOD).hardnessAndResistance(2F, 4.0F);
    
    public static final RegistryObject<Block> TAINTED_LOG = BLOCKS.register("tainted_log", () -> new RotatedPillarBlock(TAINTED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_TAINTED_LOG = BLOCKS.register("stripped_tainted_log", () -> new RotatedPillarBlock(TAINTED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_WOOD = BLOCKS.register("tainted_wood", () -> new Block(TAINTED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> STRIPPED_TAINTED_WOOD = BLOCKS.register("stripped_tainted_wood", () -> new Block(TAINTED_WOOD_PROPERTIES));
    
    public static final RegistryObject<Block> TAINTED_PLANKS = BLOCKS.register("tainted_planks", () -> new Block(TAINTED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_PLANKS_SLAB = BLOCKS.register("tainted_planks_slab", () -> new SlabBlock(TAINTED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_PLANKS_STAIRS = BLOCKS.register("tainted_planks_stairs", () -> new StairsBlock(TAINTED_PLANKS.get().getDefaultState(), TAINTED_WOOD_PROPERTIES));
    
    public static final RegistryObject<Block> TAINTED_DOOR = BLOCKS.register("tainted_door", () -> new DoorBlock(TAINTED_WOOD_PROPERTIES.notSolid()));
    public static final RegistryObject<Block> TAINTED_TRAPDOOR = BLOCKS.register("tainted_trapdoor", () -> new TrapDoorBlock(TAINTED_WOOD_PROPERTIES.notSolid()));
    public static final RegistryObject<Block> SOLID_TAINTED_TRAPDOOR = BLOCKS.register("solid_tainted_trapdoor", () -> new TrapDoorBlock(TAINTED_WOOD_PROPERTIES.notSolid()));
    
    public static final RegistryObject<Block> TAINTED_PLANKS_BUTTON = BLOCKS.register("tainted_planks_button", () -> new WoodButtonBlock(TAINTED_WOOD_PROPERTIES));
    
    public static final RegistryObject<Block> TAINTED_PLANKS_FENCE = BLOCKS.register("tainted_planks_fence", () -> new FenceBlock(TAINTED_WOOD_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_PLANKS_FENCE_GATE = BLOCKS.register("tainted_planks_fence_gate", () -> new FenceGateBlock(TAINTED_WOOD_PROPERTIES));
    //endregion
    
    //region sun kissed biome blocks
    public static AbstractBlock.Properties SUN_KISSED_PLANTS_PROPERTIES = AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.YELLOW).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).zeroHardnessAndResistance();
    public static AbstractBlock.Properties SUN_KISSED_LEAVES_PROPERTIES = AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.YELLOW).notSolid().sound(SoundType.PLANT).hardnessAndResistance(0.15f);

    public static final RegistryObject<Block> SHORT_SUN_KISSED_GRASS = BLOCKS.register("short_sun_kissed_grass", () -> new TallGrassBlock(SUN_KISSED_PLANTS_PROPERTIES));
    public static final RegistryObject<Block> SUN_KISSED_GRASS = BLOCKS.register("sun_kissed_grass", () -> new TallGrassBlock(SUN_KISSED_PLANTS_PROPERTIES));
    public static final RegistryObject<Block> TALL_SUN_KISSED_GRASS = BLOCKS.register("tall_sun_kissed_grass", () -> new DoublePlantBlock(SUN_KISSED_PLANTS_PROPERTIES));
    public static final RegistryObject<Block> SUN_KISSED_SAPLING = BLOCKS.register("sun_kissed_sapling", () -> new SaplingBlock(new SunKissedTree(), SUN_KISSED_PLANTS_PROPERTIES));
    public static final RegistryObject<Block> SUN_KISSED_LEAVES = BLOCKS.register("sun_kissed_leaves", () -> new MalumLeavesBlock(SUN_KISSED_LEAVES_PROPERTIES, new Color(0,0,0), new Color(255,255,255)));
    
    //endregion
    
    //region tainted biome blocks
    public static AbstractBlock.Properties TAINTED_PLANTS_PROPERTIES = AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.PURPLE).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).zeroHardnessAndResistance();
    public static AbstractBlock.Properties TAINTED_LEAVES_PROPERTIES = AbstractBlock.Properties.create(Material.LEAVES, MaterialColor.PURPLE).notSolid().sound(SoundType.PLANT).hardnessAndResistance(0.15f);
    
    public static final RegistryObject<Block> SHORT_TAINTED_GRASS = BLOCKS.register("short_tainted_grass", () -> new TallGrassBlock(TAINTED_PLANTS_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_GRASS = BLOCKS.register("tainted_grass", () -> new TallGrassBlock(TAINTED_PLANTS_PROPERTIES));
    public static final RegistryObject<Block> TALL_TAINTED_GRASS = BLOCKS.register("tall_tainted_grass", () -> new DoublePlantBlock(TAINTED_PLANTS_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_SAPLING = BLOCKS.register("tainted_sapling", () -> new SaplingBlock(new OakTree(), TAINTED_PLANTS_PROPERTIES));
    public static final RegistryObject<Block> TAINTED_LEAVES = BLOCKS.register("tainted_leaves", () -> new MalumLeavesBlock(TAINTED_LEAVES_PROPERTIES, new Color(0,0,0), new Color(255,255,255)));
    
    //endregion
    
    //region crafting blocks
    public static final RegistryObject<Block> SUN_KISSED_ARCANE_CRAFTING_TABLE = BLOCKS.register("sun_kissed_arcane_crafting_table", () -> new ArcaneCraftingTableBlock(SUN_KISSED_WOOD_PROPERTIES.notSolid()));
    public static final RegistryObject<Block> TAINTED_ARCANE_CRAFTING_TABLE = BLOCKS.register("tainted_arcane_crafting_table", () -> new ArcaneCraftingTableBlock(TAINTED_WOOD_PROPERTIES.notSolid()));
    
    public static final RegistryObject<Block> BLIGHTING_FURNACE = BLOCKS.register("blighting_furnace", () -> new BlightingFurnaceBlock(TAINTED_ROCK_PROPERTIES.notSolid()));
    public static final RegistryObject<Block> BLIGHTING_FURNACE_TOP = BLOCKS.register("blighting_furnace_top", () -> new BlightedFurnaceBoundingBlock(TAINTED_ROCK_PROPERTIES.notSolid()));
    
    //endregion
}