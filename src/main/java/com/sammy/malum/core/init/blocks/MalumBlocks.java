package com.sammy.malum.core.init.blocks;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.common.blocks.*;
import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlock;
import com.sammy.malum.common.blocks.arcaneworkbench.ArcaneWorkbenchBlock;
import com.sammy.malum.common.blocks.extractionfocus.ExtractionFocusBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandBlock;
import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.sammy.malum.common.blocks.lighting.EtherTorchBlock;
import com.sammy.malum.common.blocks.lighting.WallEtherTorchBlock;
import com.sammy.malum.common.blocks.spiritkiln.SpiritKilnBoundingBlock;
import com.sammy.malum.common.blocks.spiritkiln.SpiritKilnCoreBlock;
import com.sammy.malum.common.blocks.totems.TotemCoreBlock;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.common.blocks.wildfarmland.WildFarmlandBlock;
import com.sammy.malum.common.world.features.tree.SunKissedTree;
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
    
    public static AbstractBlock.Properties SUN_KISSED_WOOD_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F);
    }
    
    public static AbstractBlock.Properties TAINTED_WOOD_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.WOOD, MaterialColor.PURPLE).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(2F, 4.0F);
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
    public static final RegistryObject<Block> STRIPPED_SUN_KISSED_LOG = BLOCKS.register("stripped_sun_kissed_log", () -> new RotatedPillarBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_LOG = BLOCKS.register("sun_kissed_log", () -> new MalumLogBlock(SUN_KISSED_WOOD_PROPERTIES(), STRIPPED_SUN_KISSED_LOG.get()));
    public static final RegistryObject<Block> STRIPPED_SUN_KISSED_WOOD = BLOCKS.register("stripped_sun_kissed_wood", () -> new RotatedPillarBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_WOOD = BLOCKS.register("sun_kissed_wood", () -> new MalumLogBlock(SUN_KISSED_WOOD_PROPERTIES(), STRIPPED_SUN_KISSED_WOOD.get()));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS = BLOCKS.register("sun_kissed_planks", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_SLAB = BLOCKS.register("sun_kissed_planks_slab", () -> new SlabBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_STAIRS = BLOCKS.register("sun_kissed_planks_stairs", () -> new StairsBlock(SUN_KISSED_PLANKS.get().getDefaultState(), SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> VERTICAL_SUN_KISSED_PLANKS = BLOCKS.register("vertical_sun_kissed_planks", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_SUN_KISSED_PLANKS_SLAB = BLOCKS.register("vertical_sun_kissed_planks_slab", () -> new SlabBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_SUN_KISSED_PLANKS_STAIRS = BLOCKS.register("vertical_sun_kissed_planks_stairs", () -> new StairsBlock(VERTICAL_SUN_KISSED_PLANKS.get().getDefaultState(), SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> BOLTED_SUN_KISSED_PLANKS = BLOCKS.register("bolted_sun_kissed_planks", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> BOLTED_SUN_KISSED_PLANKS_SLAB = BLOCKS.register("bolted_sun_kissed_planks_slab", () -> new SlabBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> BOLTED_SUN_KISSED_PLANKS_STAIRS = BLOCKS.register("bolted_sun_kissed_planks_stairs", () -> new StairsBlock(SUN_KISSED_PLANKS.get().getDefaultState(), SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> SUN_KISSED_PANEL = BLOCKS.register("sun_kissed_panel", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PANEL_SLAB = BLOCKS.register("sun_kissed_panel_slab", () -> new SlabBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PANEL_STAIRS = BLOCKS.register("sun_kissed_panel_stairs", () -> new StairsBlock(SUN_KISSED_PANEL.get().getDefaultState(), SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> SUN_KISSED_TILES = BLOCKS.register("sun_kissed_tiles", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_TILES_SLAB = BLOCKS.register("sun_kissed_tiles_slab", () -> new SlabBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_TILES_STAIRS = BLOCKS.register("sun_kissed_tiles_stairs", () -> new StairsBlock(SUN_KISSED_TILES.get().getDefaultState(), SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> CUT_SUN_KISSED_PLANKS = BLOCKS.register("cut_sun_kissed_planks", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_BEAM = BLOCKS.register("sun_kissed_beam", () -> new RotatedPillarBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> BOLTED_SUN_KISSED_BEAM = BLOCKS.register("bolted_sun_kissed_beam", () -> new RotatedPillarBlock(SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> SUN_KISSED_DOOR = BLOCKS.register("sun_kissed_door", () -> new DoorBlock(SUN_KISSED_WOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SUN_KISSED_TRAPDOOR = BLOCKS.register("sun_kissed_trapdoor", () -> new TrapDoorBlock(SUN_KISSED_WOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SOLID_SUN_KISSED_TRAPDOOR = BLOCKS.register("solid_sun_kissed_trapdoor", () -> new TrapDoorBlock(SUN_KISSED_WOOD_PROPERTIES().notSolid()));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_BUTTON = BLOCKS.register("sun_kissed_planks_button", () -> new WoodButtonBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_PRESSURE_PLATE = BLOCKS.register("sun_kissed_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_FENCE = BLOCKS.register("sun_kissed_planks_fence", () -> new FenceBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_FENCE_GATE = BLOCKS.register("sun_kissed_planks_fence_gate", () -> new FenceGateBlock(SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> ORANGE_ETHER_TORCH = BLOCKS.register("orange_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.ORANGE));
    public static final RegistryObject<Block> MAGENTA_ETHER_TORCH = BLOCKS.register("magenta_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.MAGENTA));
    public static final RegistryObject<Block> LIGHT_BLUE_ETHER_TORCH = BLOCKS.register("light_blue_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.LIGHT_BLUE));
    public static final RegistryObject<Block> YELLOW_ETHER_TORCH = BLOCKS.register("yellow_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.YELLOW));
    public static final RegistryObject<Block> ETHER_TORCH = BLOCKS.register("ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.PINK));
    public static final RegistryObject<Block> LIME_ETHER_TORCH = BLOCKS.register("lime_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.LIME));
    public static final RegistryObject<Block> CYAN_ETHER_TORCH = BLOCKS.register("cyan_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.CYAN));
    public static final RegistryObject<Block> PURPLE_ETHER_TORCH = BLOCKS.register("purple_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.PURPLE));
    public static final RegistryObject<Block> BLUE_ETHER_TORCH = BLOCKS.register("blue_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.BLUE));
    public static final RegistryObject<Block> BROWN_ETHER_TORCH = BLOCKS.register("brown_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.BROWN));
    public static final RegistryObject<Block> GREEN_ETHER_TORCH = BLOCKS.register("green_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.GREEN));
    public static final RegistryObject<Block> RED_ETHER_TORCH = BLOCKS.register("red_ether_torch", () -> new EtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)), MalumConstants.RED));
    
    public static final RegistryObject<Block> ORANGE_WALL_ETHER_TORCH = BLOCKS.register("orange_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(ORANGE_ETHER_TORCH.get()), MalumConstants.ORANGE));
    public static final RegistryObject<Block> MAGENTA_WALL_ETHER_TORCH = BLOCKS.register("magenta_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(MAGENTA_ETHER_TORCH.get()), MalumConstants.MAGENTA));
    public static final RegistryObject<Block> LIGHT_BLUE_WALL_ETHER_TORCH = BLOCKS.register("light_blue_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(LIGHT_BLUE_ETHER_TORCH.get()), MalumConstants.LIGHT_BLUE));
    public static final RegistryObject<Block> YELLOW_WALL_ETHER_TORCH = BLOCKS.register("yellow_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(YELLOW_ETHER_TORCH.get()), MalumConstants.YELLOW));
    public static final RegistryObject<Block> WALL_ETHER_TORCH = BLOCKS.register("wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(ETHER_TORCH.get()), MalumConstants.PINK));
    public static final RegistryObject<Block> LIME_WALL_ETHER_TORCH = BLOCKS.register("lime_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(LIME_ETHER_TORCH.get()), MalumConstants.LIME));
    public static final RegistryObject<Block> CYAN_WALL_ETHER_TORCH = BLOCKS.register("cyan_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(CYAN_ETHER_TORCH.get()), MalumConstants.CYAN));
    public static final RegistryObject<Block> PURPLE_WALL_ETHER_TORCH = BLOCKS.register("purple_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(PURPLE_ETHER_TORCH.get()), MalumConstants.darkest()));
    public static final RegistryObject<Block> BLUE_WALL_ETHER_TORCH = BLOCKS.register("blue_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(BLUE_ETHER_TORCH.get()), MalumConstants.BLUE));
    public static final RegistryObject<Block> BROWN_WALL_ETHER_TORCH = BLOCKS.register("brown_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(BROWN_ETHER_TORCH.get()), MalumConstants.BROWN));
    public static final RegistryObject<Block> GREEN_WALL_ETHER_TORCH = BLOCKS.register("green_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(GREEN_ETHER_TORCH.get()), MalumConstants.GREEN));
    public static final RegistryObject<Block> RED_WALL_ETHER_TORCH = BLOCKS.register("red_wall_ether_torch", () -> new WallEtherTorchBlock(SUN_KISSED_WOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b,14)).lootFrom(RED_ETHER_TORCH.get()), MalumConstants.RED));
    
    public static final RegistryObject<Block> ORANGE_ETHER = BLOCKS.register("orange_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.ORANGE));
    public static final RegistryObject<Block> MAGENTA_ETHER = BLOCKS.register("magenta_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.MAGENTA));
    public static final RegistryObject<Block> LIGHT_BLUE_ETHER = BLOCKS.register("light_blue_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.LIGHT_BLUE));
    public static final RegistryObject<Block> YELLOW_ETHER = BLOCKS.register("yellow_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.YELLOW));
    public static final RegistryObject<Block> ETHER = BLOCKS.register("ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES(), MalumConstants.PINK));
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
    public static final RegistryObject<Block> ETHER_BRAZIER = BLOCKS.register("ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b,14)), MalumConstants.PINK));
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
    public static final RegistryObject<Block> SUN_KISSED_SAPLING = BLOCKS.register("sun_kissed_sapling", () -> new SaplingBlock(new SunKissedTree(), SUN_KISSED_PLANTS_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_LEAVES = BLOCKS.register("sun_kissed_leaves", () -> new MalumLeavesBlock(LEAVES_PROPERTIES(), new Color(180, 85, 13), new Color(255, 213, 78)));
    
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
    
    public static final RegistryObject<Block> TOTEM_CORE = BLOCKS.register("totem_core", () -> new TotemCoreBlock(SUN_KISSED_WOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> TOTEM_POLE = BLOCKS.register("totem_pole", () -> new TotemPoleBlock(SUN_KISSED_WOOD_PROPERTIES().notSolid().lootFrom(SUN_KISSED_LOG.get())));

    public static final RegistryObject<Block> SPIRIT_KILN = BLOCKS.register("spirit_kiln", () -> new SpiritKilnCoreBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SPIRIT_KILN_TOP = BLOCKS.register("spirit_kiln_top", () -> new SpiritKilnBoundingBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", () -> new ArcaneWorkbenchBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
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
}