package com.sammy.malum.core.init.blocks;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.common.blocks.*;
import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlock;
import com.sammy.malum.common.blocks.extractionfocus.ExtractionFocusBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandBlock;
import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.sammy.malum.common.blocks.lighting.EtherTorchBlock;
import com.sammy.malum.common.blocks.lighting.WallEtherTorchBlock;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarBlock;
import com.sammy.malum.common.blocks.spiritkiln.DamagedSpiritKilnBoundingBlock;
import com.sammy.malum.common.blocks.spiritkiln.DamagedSpiritKilnCoreBlock;
import com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnBoundingBlock;
import com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreBlock;
import com.sammy.malum.common.blocks.spiritstorage.jar.SpiritJarBlock;
import com.sammy.malum.common.blocks.spiritstorage.pipe.SpiritPipeBlock;
import com.sammy.malum.common.blocks.totems.TotemCoreBlock;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.common.blocks.wildfarmland.WildFarmlandBlock;
import com.sammy.malum.common.world.features.tree.RunewoodTree;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

import static com.sammy.malum.MalumMod.MODID;
import static net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING;
import static net.minecraft.block.PressurePlateBlock.Sensitivity.MOBS;


@SuppressWarnings("unused")
public class MalumBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    
    //region properties
    
    public static AbstractBlock.Properties TAINTED_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).sound(MalumSounds.TAINTED_ROCK).setRequiresTool().hardnessAndResistance(1.25F, 9.0F);
    }
    
    public static AbstractBlock.Properties DARKENED_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().sound(MalumSounds.DARKENED_ROCK).hardnessAndResistance(2.25F, 3600.0F);
    }
    
    public static AbstractBlock.Properties RUNEWOOD_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F);
    }
    public static AbstractBlock.Properties SUN_KISSED_GRASS_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.45f);
    }
    public static AbstractBlock.Properties SUN_KISSED_PLANTS_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.YELLOW).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).harvestTool(ToolType.HOE).zeroHardnessAndResistance();
    }
    
    
    public static AbstractBlock.Properties TAINTED_GRASS_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.PURPLE).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.45f).tickRandomly();
    }
    public static AbstractBlock.Properties TAINTED_PLANTS_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.PURPLE).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).harvestTool(ToolType.HOE).zeroHardnessAndResistance();
    }
    
    public static AbstractBlock.Properties LEAVES_PROPERTIES()
    {
        return AbstractBlock.Properties.from(Blocks.OAK_LEAVES);
    }
    
    public static AbstractBlock.Properties ETHER_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.GLASS, MaterialColor.BLUE).sound(SoundType.CLOTH).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14));
    }
    public static AbstractBlock.Properties ABSTRUSE_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.GLASS, MaterialColor.BLUE).sound(SoundType.CLOTH).zeroHardnessAndResistance().noDrops().notSolid();
    }
    
    public static AbstractBlock.Properties HALLOWED_GOLD_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).sound(MalumSounds.HALLOWED_GOLD).notSolid().hardnessAndResistance(2F, 16.0F);
    }
    
    public static AbstractBlock.Properties SPIRITED_METAL_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE).sound(MalumSounds.SPIRITED_METAL_BLOCK).hardnessAndResistance(5f,3600f);
    }
    
    public static AbstractBlock.Properties SPIRIT_JAR_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.GLASS, MaterialColor.BLUE).sound(MalumSounds.HALLOWED_GOLD).notSolid();
    }
    //endregion
    
    //region tainted rock
    public static final RegistryObject<Block> TAINTED_ROCK = BLOCKS.register("tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK = BLOCKS.register("smooth_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK = BLOCKS.register("polished_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS = BLOCKS.register("tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS = BLOCKS.register("cracked_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS = BLOCKS.register("mossy_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_TILES = BLOCKS.register("tainted_rock_tiles", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES = BLOCKS.register("cracked_tainted_rock_tiles", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_TILES = BLOCKS.register("mossy_tainted_rock_tiles", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_PILLAR = BLOCKS.register("tainted_rock_pillar", () -> new RotatedPillarBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_PILLAR_CAP = BLOCKS.register("tainted_rock_pillar_cap", () -> new MalumDirectionalBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_COLUMN = BLOCKS.register("tainted_rock_column", () -> new RotatedPillarBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_COLUMN_CAP = BLOCKS.register("tainted_rock_column_cap", () -> new MalumDirectionalBlock(TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CUT_TAINTED_ROCK = BLOCKS.register("cut_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_TAINTED_ROCK = BLOCKS.register("chiseled_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_SLAB = BLOCKS.register("tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_SLAB = BLOCKS.register("smooth_tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_SLAB = BLOCKS.register("polished_tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("mossy_tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_SLAB = BLOCKS.register("tainted_rock_tiles_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES_SLAB = BLOCKS.register("cracked_tainted_rock_tiles_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_TILES_SLAB = BLOCKS.register("mossy_tainted_rock_tiles_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_STAIRS = BLOCKS.register("tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_STAIRS = BLOCKS.register("smooth_tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_STAIRS = BLOCKS.register("polished_tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("mossy_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("tainted_rock_tiles_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_tainted_rock_tiles_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("mossy_tainted_rock_tiles_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_PRESSURE_PLATE = BLOCKS.register("tainted_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_WALL = BLOCKS.register("tainted_rock_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("mossy_tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_WALL = BLOCKS.register("tainted_rock_tiles_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_TILES_WALL = BLOCKS.register("mossy_tainted_rock_tiles_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES_WALL = BLOCKS.register("cracked_tainted_rock_tiles_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    //endregion
    
    //region darkened rock
    public static final RegistryObject<Block> DARKENED_ROCK = BLOCKS.register("darkened_rock", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_DARKENED_ROCK = BLOCKS.register("smooth_darkened_rock", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_DARKENED_ROCK = BLOCKS.register("polished_darkened_rock", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_BRICKS = BLOCKS.register("darkened_rock_bricks", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_BRICKS = BLOCKS.register("cracked_darkened_rock_bricks", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_BRICKS = BLOCKS.register("mossy_darkened_rock_bricks", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_TILES = BLOCKS.register("darkened_rock_tiles", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_TILES = BLOCKS.register("cracked_darkened_rock_tiles", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_TILES = BLOCKS.register("mossy_darkened_rock_tiles", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_PILLAR = BLOCKS.register("darkened_rock_pillar", () -> new RotatedPillarBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_PILLAR_CAP = BLOCKS.register("darkened_rock_pillar_cap", () -> new MalumDirectionalBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_COLUMN = BLOCKS.register("darkened_rock_column", () -> new RotatedPillarBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_COLUMN_CAP = BLOCKS.register("darkened_rock_column_cap", () -> new MalumDirectionalBlock(DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CUT_DARKENED_ROCK = BLOCKS.register("cut_darkened_rock", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_DARKENED_ROCK = BLOCKS.register("chiseled_darkened_rock", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_SLAB = BLOCKS.register("darkened_rock_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_DARKENED_ROCK_SLAB = BLOCKS.register("smooth_darkened_rock_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_DARKENED_ROCK_SLAB = BLOCKS.register("polished_darkened_rock_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_BRICKS_SLAB = BLOCKS.register("darkened_rock_bricks_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_darkened_rock_bricks_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_BRICKS_SLAB = BLOCKS.register("mossy_darkened_rock_bricks_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_TILES_SLAB = BLOCKS.register("darkened_rock_tiles_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_TILES_SLAB = BLOCKS.register("cracked_darkened_rock_tiles_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_TILES_SLAB = BLOCKS.register("mossy_darkened_rock_tiles_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_STAIRS = BLOCKS.register("darkened_rock_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_DARKENED_ROCK_STAIRS = BLOCKS.register("smooth_darkened_rock_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_DARKENED_ROCK_STAIRS = BLOCKS.register("polished_darkened_rock_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_BRICKS_STAIRS = BLOCKS.register("darkened_rock_bricks_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_darkened_rock_bricks_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_BRICKS_STAIRS = BLOCKS.register("mossy_darkened_rock_bricks_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_TILES_STAIRS = BLOCKS.register("darkened_rock_tiles_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_darkened_rock_tiles_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_TILES_STAIRS = BLOCKS.register("mossy_darkened_rock_tiles_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_PRESSURE_PLATE = BLOCKS.register("darkened_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_WALL = BLOCKS.register("darkened_rock_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_BRICKS_WALL = BLOCKS.register("darkened_rock_bricks_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_BRICKS_WALL = BLOCKS.register("mossy_darkened_rock_bricks_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_darkened_rock_bricks_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_TILES_WALL = BLOCKS.register("darkened_rock_tiles_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_TILES_WALL = BLOCKS.register("mossy_darkened_rock_tiles_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_TILES_WALL = BLOCKS.register("cracked_darkened_rock_tiles_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    //endregion
    
    //region sun kissed wood
    public static final RegistryObject<Block> STRIPPED_RUNEWOOD_LOG = BLOCKS.register("stripped_runewood_log", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_LOG = BLOCKS.register("runewood_log", () -> new MalumLogBlock(RUNEWOOD_PROPERTIES(), STRIPPED_RUNEWOOD_LOG.get()));
    public static final RegistryObject<Block> STRIPPED_RUNEWOOD = BLOCKS.register("stripped_runewood", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD = BLOCKS.register("runewood", () -> new MalumLogBlock(RUNEWOOD_PROPERTIES(), STRIPPED_RUNEWOOD.get()));
    
    public static final RegistryObject<Block> RUNEWOOD_PLANKS = BLOCKS.register("runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_SLAB = BLOCKS.register("runewood_planks_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("runewood_planks_stairs", () -> new StairsBlock(RUNEWOOD_PLANKS.get().getDefaultState(), RUNEWOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS = BLOCKS.register("vertical_runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS_SLAB = BLOCKS.register("vertical_runewood_planks_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_runewood_planks_stairs", () -> new StairsBlock(VERTICAL_RUNEWOOD_PLANKS.get().getDefaultState(), RUNEWOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> BOLTED_RUNEWOOD_PLANKS = BLOCKS.register("bolted_runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> BOLTED_RUNEWOOD_PLANKS_SLAB = BLOCKS.register("bolted_runewood_planks_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> BOLTED_RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("bolted_runewood_planks_stairs", () -> new StairsBlock(RUNEWOOD_PLANKS.get().getDefaultState(), RUNEWOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> RUNEWOOD_PANEL = BLOCKS.register("runewood_panel", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PANEL_SLAB = BLOCKS.register("runewood_panel_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PANEL_STAIRS = BLOCKS.register("runewood_panel_stairs", () -> new StairsBlock(RUNEWOOD_PANEL.get().getDefaultState(), RUNEWOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> RUNEWOOD_TILES = BLOCKS.register("runewood_tiles", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_TILES_SLAB = BLOCKS.register("runewood_tiles_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_TILES_STAIRS = BLOCKS.register("runewood_tiles_stairs", () -> new StairsBlock(RUNEWOOD_TILES.get().getDefaultState(), RUNEWOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> CUT_RUNEWOOD_PLANKS = BLOCKS.register("cut_runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_BEAM = BLOCKS.register("runewood_beam", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> BOLTED_RUNEWOOD_BEAM = BLOCKS.register("bolted_runewood_beam", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> RUNEWOOD_DOOR = BLOCKS.register("runewood_door", () -> new DoorBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> RUNEWOOD_TRAPDOOR = BLOCKS.register("runewood_trapdoor", () -> new TrapDoorBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SOLID_RUNEWOOD_TRAPDOOR = BLOCKS.register("solid_runewood_trapdoor", () -> new TrapDoorBlock(RUNEWOOD_PROPERTIES().notSolid()));
    
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_BUTTON = BLOCKS.register("runewood_planks_button", () -> new WoodButtonBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_PRESSURE_PLATE = BLOCKS.register("runewood_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, RUNEWOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_FENCE = BLOCKS.register("runewood_planks_fence", () -> new FenceBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_FENCE_GATE = BLOCKS.register("runewood_planks_fence_gate", () -> new FenceGateBlock(RUNEWOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> ORANGE_ETHER_TORCH = BLOCKS.register("orange_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.ORANGE));
    public static final RegistryObject<Block> MAGENTA_ETHER_TORCH = BLOCKS.register("magenta_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.MAGENTA));
    public static final RegistryObject<Block> LIGHT_BLUE_ETHER_TORCH = BLOCKS.register("light_blue_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.LIGHT_BLUE));
    public static final RegistryObject<Block> YELLOW_ETHER_TORCH = BLOCKS.register("yellow_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.YELLOW));
    public static final RegistryObject<Block> PINK_ETHER_TORCH = BLOCKS.register("pink_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.PINK));
    public static final RegistryObject<Block> LIME_ETHER_TORCH = BLOCKS.register("lime_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.LIME));
    public static final RegistryObject<Block> CYAN_ETHER_TORCH = BLOCKS.register("cyan_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.CYAN));
    public static final RegistryObject<Block> PURPLE_ETHER_TORCH = BLOCKS.register("purple_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.PURPLE));
    public static final RegistryObject<Block> BLUE_ETHER_TORCH = BLOCKS.register("blue_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.BLUE));
    public static final RegistryObject<Block> BROWN_ETHER_TORCH = BLOCKS.register("brown_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.BROWN));
    public static final RegistryObject<Block> GREEN_ETHER_TORCH = BLOCKS.register("green_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.GREEN));
    public static final RegistryObject<Block> RED_ETHER_TORCH = BLOCKS.register("red_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.RED));
    
    public static final RegistryObject<Block> ORANGE_WALL_ETHER_TORCH = BLOCKS.register("orange_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(ORANGE_ETHER_TORCH.get()), MalumConstants.ORANGE));
    public static final RegistryObject<Block> MAGENTA_WALL_ETHER_TORCH = BLOCKS.register("magenta_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(MAGENTA_ETHER_TORCH.get()), MalumConstants.MAGENTA));
    public static final RegistryObject<Block> LIGHT_BLUE_WALL_ETHER_TORCH = BLOCKS.register("light_blue_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(LIGHT_BLUE_ETHER_TORCH.get()), MalumConstants.LIGHT_BLUE));
    public static final RegistryObject<Block> YELLOW_WALL_ETHER_TORCH = BLOCKS.register("yellow_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(YELLOW_ETHER_TORCH.get()), MalumConstants.YELLOW));
    public static final RegistryObject<Block> PINK_WALL_ETHER_TORCH = BLOCKS.register("pink_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(PINK_ETHER_TORCH.get()), MalumConstants.PINK));
    public static final RegistryObject<Block> LIME_WALL_ETHER_TORCH = BLOCKS.register("lime_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(LIME_ETHER_TORCH.get()), MalumConstants.LIME));
    public static final RegistryObject<Block> CYAN_WALL_ETHER_TORCH = BLOCKS.register("cyan_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(CYAN_ETHER_TORCH.get()), MalumConstants.CYAN));
    public static final RegistryObject<Block> PURPLE_WALL_ETHER_TORCH = BLOCKS.register("purple_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(PURPLE_ETHER_TORCH.get()), MalumConstants.darkest()));
    public static final RegistryObject<Block> BLUE_WALL_ETHER_TORCH = BLOCKS.register("blue_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(BLUE_ETHER_TORCH.get()), MalumConstants.BLUE));
    public static final RegistryObject<Block> BROWN_WALL_ETHER_TORCH = BLOCKS.register("brown_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(BROWN_ETHER_TORCH.get()), MalumConstants.BROWN));
    public static final RegistryObject<Block> GREEN_WALL_ETHER_TORCH = BLOCKS.register("green_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(GREEN_ETHER_TORCH.get()), MalumConstants.GREEN));
    public static final RegistryObject<Block> RED_WALL_ETHER_TORCH = BLOCKS.register("red_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(RED_ETHER_TORCH.get()), MalumConstants.RED));
    
    public static final RegistryObject<Block> ORANGE_ETHER = BLOCKS.register("orange_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.ORANGE));
    public static final RegistryObject<Block> MAGENTA_ETHER = BLOCKS.register("magenta_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.MAGENTA));
    public static final RegistryObject<Block> LIGHT_BLUE_ETHER = BLOCKS.register("light_blue_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.LIGHT_BLUE));
    public static final RegistryObject<Block> YELLOW_ETHER = BLOCKS.register("yellow_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.YELLOW));
    public static final RegistryObject<Block> PINK_ETHER = BLOCKS.register("pink_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.PINK));
    public static final RegistryObject<Block> LIME_ETHER = BLOCKS.register("lime_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.LIME));
    public static final RegistryObject<Block> CYAN_ETHER = BLOCKS.register("cyan_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.CYAN));
    public static final RegistryObject<Block> PURPLE_ETHER = BLOCKS.register("purple_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.PURPLE));
    public static final RegistryObject<Block> BLUE_ETHER = BLOCKS.register("blue_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.BLUE));
    public static final RegistryObject<Block> BROWN_ETHER = BLOCKS.register("brown_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.BROWN));
    public static final RegistryObject<Block> GREEN_ETHER = BLOCKS.register("green_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.GREEN));
    public static final RegistryObject<Block> RED_ETHER = BLOCKS.register("red_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.RED));
    
    public static final RegistryObject<Block> ORANGE_ETHER_BRAZIER = BLOCKS.register("orange_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.ORANGE));
    public static final RegistryObject<Block> MAGENTA_ETHER_BRAZIER = BLOCKS.register("magenta_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.MAGENTA));
    public static final RegistryObject<Block> LIGHT_BLUE_ETHER_BRAZIER = BLOCKS.register("light_blue_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.LIGHT_BLUE));
    public static final RegistryObject<Block> YELLOW_ETHER_BRAZIER = BLOCKS.register("yellow_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.YELLOW));
    public static final RegistryObject<Block> PINK_ETHER_BRAZIER = BLOCKS.register("pink_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.PINK));
    public static final RegistryObject<Block> LIME_ETHER_BRAZIER = BLOCKS.register("lime_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.LIME));
    public static final RegistryObject<Block> CYAN_ETHER_BRAZIER = BLOCKS.register("cyan_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.CYAN));
    public static final RegistryObject<Block> PURPLE_ETHER_BRAZIER = BLOCKS.register("purple_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.PURPLE));
    public static final RegistryObject<Block> BLUE_ETHER_BRAZIER = BLOCKS.register("blue_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.BLUE));
    public static final RegistryObject<Block> BROWN_ETHER_BRAZIER = BLOCKS.register("brown_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.BROWN));
    public static final RegistryObject<Block> GREEN_ETHER_BRAZIER = BLOCKS.register("green_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.GREEN));
    public static final RegistryObject<Block> RED_ETHER_BRAZIER = BLOCKS.register("red_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.RED));
    
    //endregion
    
    public static final RegistryObject<Block> MARIGOLD = BLOCKS.register("marigold", () -> new BushBlock(SUN_KISSED_PLANTS_PROPERTIES()));
    
    //region sun kissed biome blocks
    public static final RegistryObject<Block> SUN_KISSED_GRASS_BLOCK = BLOCKS.register("sun_kissed_grass_block", () -> new GrassBlock(SUN_KISSED_GRASS_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TALL_SUN_KISSED_GRASS = BLOCKS.register("tall_sun_kissed_grass", () -> new DoublePlantBlock(SUN_KISSED_PLANTS_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_GRASS = BLOCKS.register("sun_kissed_grass", () -> new MalumTallGrassBlock(SUN_KISSED_PLANTS_PROPERTIES(), TALL_SUN_KISSED_GRASS));
    public static final RegistryObject<Block> SHORT_SUN_KISSED_GRASS = BLOCKS.register("short_sun_kissed_grass", () -> new MalumTallGrassBlock(SUN_KISSED_PLANTS_PROPERTIES(), TALL_SUN_KISSED_GRASS));
    public static final RegistryObject<Block> RUNEWOOD_SAPLING = BLOCKS.register("runewood_sapling", () -> new SaplingBlock(new RunewoodTree(), SUN_KISSED_PLANTS_PROPERTIES()));
    public static final RegistryObject<Block> RUNE_LEAVES = BLOCKS.register("rune_leaves", () -> new MalumLeavesBlock(LEAVES_PROPERTIES(), new Color(163, 72, 11), new Color(255, 198, 82)));
    
    public static final RegistryObject<Block> LAVENDER = BLOCKS.register("lavender", () -> new DoublePlantBlock(SUN_KISSED_PLANTS_PROPERTIES()));
    //endregion
    
    public static final RegistryObject<Block> CLEAN_GRAVEL = BLOCKS.register("clean_gravel", () -> new GravelBlock(AbstractBlock.Properties.from(Blocks.GRAVEL)));
    public static final RegistryObject<Block> CLEAN_SAND = BLOCKS.register("clean_sand", () -> new SandBlock(14406560,AbstractBlock.Properties.from(Blocks.SAND)));
    
    public static final RegistryObject<Block> POLISHED_BONE_BLOCK = BLOCKS.register("polished_bone_block", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_BEAM = BLOCKS.register("bone_beam", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_TILES = BLOCKS.register("bone_tiles", () -> new Block(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_TILES = BLOCKS.register("cracked_bone_tiles", () -> new Block(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_BRICKS = BLOCKS.register("bone_bricks", () -> new Block(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_BRICKS = BLOCKS.register("cracked_bone_bricks", () -> new Block(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_TILE = BLOCKS.register("bone_tile", () -> new Block(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CARVED_BONE_TILE = BLOCKS.register("carved_bone_tile", () -> new Block(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    
    public static final RegistryObject<Block> BONE_TILES_SLAB = BLOCKS.register("bone_tiles_slab", () -> new SlabBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_TILES_STAIRS = BLOCKS.register("bone_tiles_stairs", () -> new StairsBlock(BONE_TILES.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_TILES_SLAB = BLOCKS.register("cracked_bone_tiles_slab", () -> new SlabBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_TILES_STAIRS = BLOCKS.register("cracked_bone_tiles_stairs", () -> new StairsBlock(BONE_TILES.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_BRICKS_SLAB = BLOCKS.register("bone_bricks_slab", () -> new SlabBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_BRICKS_STAIRS = BLOCKS.register("bone_bricks_stairs", () -> new StairsBlock(BONE_TILES.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_BRICKS_SLAB = BLOCKS.register("cracked_bone_bricks_slab", () -> new SlabBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_BRICKS_STAIRS = BLOCKS.register("cracked_bone_bricks_stairs", () -> new StairsBlock(BONE_TILES.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_TILE_SLAB = BLOCKS.register("bone_tile_slab", () -> new SlabBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_TILE_STAIRS = BLOCKS.register("bone_tile_stairs", () -> new StairsBlock(BONE_TILES.get().getDefaultState(), AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    
    public static final RegistryObject<Block> BONE_TILES_WALL = BLOCKS.register("bone_tiles_wall", () -> new WallBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_TILES_WALL = BLOCKS.register("cracked_bone_tiles_wall", () -> new WallBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_BRICKS_WALL = BLOCKS.register("bone_bricks_wall", () -> new WallBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> CRACKED_BONE_BRICKS_WALL = BLOCKS.register("cracked_bone_bricks_wall", () -> new WallBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    public static final RegistryObject<Block> BONE_TILE_WALL = BLOCKS.register("bone_tile_wall", () -> new WallBlock(AbstractBlock.Properties.from(Blocks.BONE_BLOCK)));
    
    //region contents
    public static final RegistryObject<Block> BLAZE_QUARTZ_ORE = BLOCKS.register("blaze_quartz_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.NETHER_QUARTZ_ORE)));
    public static final RegistryObject<Block> ABSTRUSE_BLOCK = BLOCKS.register("abstruse_block", () -> new AbstruseBlock(ABSTRUSE_BLOCK_PROPERTIES()));
    
    public static final RegistryObject<Block> HALLOWED_GOLD_BLOCK = BLOCKS.register("hallowed_gold_block", () -> new Block(HALLOWED_GOLD_PROPERTIES()));
    public static final RegistryObject<Block> SPIRITED_METAL_BLOCK = BLOCKS.register("spirited_metal_block", () -> new Block(SPIRITED_METAL_BLOCK_PROPERTIES()));
    
    public static final RegistryObject<Block> WITHER_SAND = BLOCKS.register("wither_sand", () -> new WitherSandBlock(AbstractBlock.Properties.from(Blocks.SOUL_SAND).harvestTool(ToolType.SHOVEL)));
    //endregion
    
    //region crafting blocks
    //    public static final RegistryObject<Block> ARCANE_CRAFTING_TABLE = BLOCKS.register("arcane_crafting_table", () -> new ArcaneCraftingTableBlock(TAINTED_WOOD_PROPERTIES().notSolid()));
    //
    public static final RegistryObject<Block> SPIRIT_ALTAR = BLOCKS.register("spirit_altar", () -> new SpiritAltarBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SPIRIT_PIPE = BLOCKS.register("spirit_pipe", () -> new SpiritPipeBlock(HALLOWED_GOLD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SPIRIT_JAR = BLOCKS.register("spirit_jar", () -> new SpiritJarBlock(HALLOWED_GOLD_PROPERTIES().notSolid()));
    
    public static final RegistryObject<Block> TOTEM_CORE = BLOCKS.register("totem_core", () -> new TotemCoreBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> TOTEM_POLE = BLOCKS.register("totem_pole", () -> new TotemPoleBlock(RUNEWOOD_PROPERTIES().notSolid().lootFrom(RUNEWOOD_LOG.get())));
    
    public static final RegistryObject<Block> SPIRIT_KILN = BLOCKS.register("spirit_kiln", () -> new SpiritKilnCoreBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SPIRIT_KILN_TOP = BLOCKS.register("spirit_kiln_top", () -> new SpiritKilnBoundingBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    
    public static final RegistryObject<Block> DAMAGED_SPIRIT_KILN = BLOCKS.register("damaged_spirit_kiln", () -> new DamagedSpiritKilnCoreBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> DAMAGED_SPIRIT_KILN_TOP = BLOCKS.register("damaged_spirit_kiln_top", () -> new DamagedSpiritKilnBoundingBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    
    
    public static final RegistryObject<Block> ITEM_STAND = BLOCKS.register("item_stand", () -> new ItemStandBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> EXTRACTION_FOCUS = BLOCKS.register("extraction_focus", () -> new ExtractionFocusBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> IMPERVIOUS_ROCK = BLOCKS.register("impervious_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> WILD_FARMLAND = BLOCKS.register("wild_farmland", () -> new WildFarmlandBlock(SUN_KISSED_GRASS_BLOCK_PROPERTIES().notSolid().tickRandomly()));
    //endregion
    
    public static int light(BlockState state, int strength)
    {
        if (ModList.get().isLoaded("hypcore"))
        {
            if (FMLEnvironment.dist == Dist.CLIENT)
            {
                return 0;
            }
        }
        return strength;
    }
    public static WoodType register(WoodType woodTypeIn) {
        WoodType.VALUES.add(woodTypeIn);
        return woodTypeIn;
    }
}