package com.sammy.malum.core.init.blocks;

import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.common.blocks.MalumTallGrassBlock;
import com.sammy.malum.common.blocks.WitherSandBlock;
import com.sammy.malum.common.blocks.abstruceblock.AbstruseBlock;
import com.sammy.malum.common.blocks.arcanecraftingtable.ArcaneCraftingTableBlock;
import com.sammy.malum.common.blocks.blightingfurnace.BlightedFurnaceBoundingBlock;
import com.sammy.malum.common.blocks.blightingfurnace.BlightingFurnaceBlock;
import com.sammy.malum.common.blocks.essencejar.SpiritJarBlock;
import com.sammy.malum.common.blocks.essencepipe.OpenTransmissiveMetalBlock;
import com.sammy.malum.common.blocks.essencepipe.SpiritPipeBlock;
import com.sammy.malum.common.blocks.taint.*;
import com.sammy.malum.common.world.features.tree.SunKissedTree;
import com.sammy.malum.common.world.features.tree.TaintedTree;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

import static com.sammy.malum.MalumMod.MODID;
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
    
    public static AbstractBlock.Properties CRIMSON_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().sound(MalumSounds.CRIMSON_ROCK).hardnessAndResistance(1.25F, 9.0F);
    }
    
    public static AbstractBlock.Properties ARCHAIC_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().sound(MalumSounds.ARCHAIC_ROCK).hardnessAndResistance(2.25F, 3600.0F);
    }
    
    public static AbstractBlock.Properties SUN_KISSED_WOOD_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.YELLOW).sound(SoundType.WOOD).hardnessAndResistance(1.75F, 4.0F);
    }
    
    public static AbstractBlock.Properties TAINTED_WOOD_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.WOOD, MaterialColor.PURPLE).sound(SoundType.WOOD).hardnessAndResistance(2F, 4.0F);
    }
    public static AbstractBlock.Properties SUN_KISSED_GRASS_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).sound(SoundType.GROUND).hardnessAndResistance(0.45f);
    }
    public static AbstractBlock.Properties SUN_KISSED_PLANTS_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.YELLOW).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).zeroHardnessAndResistance();
    }
    
    
    public static AbstractBlock.Properties TAINTED_GRASS_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.PURPLE).sound(SoundType.GROUND).hardnessAndResistance(0.45f).tickRandomly();
    }
    public static AbstractBlock.Properties TAINTED_PLANTS_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.PURPLE).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).zeroHardnessAndResistance();
    }
    
    public static AbstractBlock.Properties LEAVES_PROPERTIES()
    {
        return AbstractBlock.Properties.from(Blocks.OAK_LEAVES);
    }
    
    public static AbstractBlock.Properties ABSTRUSE_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.GLASS, MaterialColor.BLUE).sound(SoundType.CLOTH).zeroHardnessAndResistance().noDrops().notSolid();
    }
    
    public static AbstractBlock.Properties TRANSMISSIVE_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).sound(MalumSounds.TRANSMISSIVE_ALLOY).notSolid().hardnessAndResistance(2F, 16.0F);
    }
    
    public static AbstractBlock.Properties RUIN_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE).sound(MalumSounds.RUIN_PLATING).hardnessAndResistance(5f,3600f);
    }
    
    public static AbstractBlock.Properties ESSENCE_JAR_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.GLASS, MaterialColor.BLUE).sound(SoundType.GLASS).noDrops().notSolid();
    }
    //endregion
    
    //region tainted rock
    public static final RegistryObject<Block> TAINTED_ROCK = BLOCKS.register("tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_SLAB = BLOCKS.register("tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_STAIRS = BLOCKS.register("tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK = BLOCKS.register("polished_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_SLAB = BLOCKS.register("polished_tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_STAIRS = BLOCKS.register("polished_tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK = BLOCKS.register("smooth_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_SLAB = BLOCKS.register("smooth_tainted_rock_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_STAIRS = BLOCKS.register("smooth_tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS = BLOCKS.register("tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS = BLOCKS.register("cracked_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS = BLOCKS.register("mossy_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("mossy_tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("mossy_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_PILLAR = BLOCKS.register("tainted_rock_pillar", () -> new RotatedPillarBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_TAINTED_ROCK_BRICKS = BLOCKS.register("chiseled_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_ROCK_PRESSURE_PLATE = BLOCKS.register("tainted_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_WALL = BLOCKS.register("tainted_rock_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("mossy_tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_LANTERN = BLOCKS.register("tainted_lantern", () -> new LanternBlock(TAINTED_ROCK_PROPERTIES().setLightLevel(MalumBlocks::lanternLight)));
    //endregion
    
    //region darkened rock
    public static final RegistryObject<Block> DARKENED_ROCK = BLOCKS.register("darkened_rock", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_SLAB = BLOCKS.register("darkened_rock_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_STAIRS = BLOCKS.register("darkened_rock_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> POLISHED_DARKENED_ROCK = BLOCKS.register("polished_darkened_rock", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_DARKENED_ROCK_SLAB = BLOCKS.register("polished_darkened_rock_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_DARKENED_ROCK_STAIRS = BLOCKS.register("polished_darkened_rock_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> SMOOTH_DARKENED_ROCK = BLOCKS.register("smooth_darkened_rock", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_DARKENED_ROCK_SLAB = BLOCKS.register("smooth_darkened_rock_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_DARKENED_ROCK_STAIRS = BLOCKS.register("smooth_darkened_rock_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_BRICKS = BLOCKS.register("darkened_rock_bricks", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_BRICKS_SLAB = BLOCKS.register("darkened_rock_bricks_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_BRICKS_STAIRS = BLOCKS.register("darkened_rock_bricks_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_BRICKS = BLOCKS.register("cracked_darkened_rock_bricks", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_darkened_rock_bricks_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_DARKENED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_darkened_rock_bricks_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_BRICKS = BLOCKS.register("mossy_darkened_rock_bricks", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_BRICKS_SLAB = BLOCKS.register("mossy_darkened_rock_bricks_slab", () -> new SlabBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_BRICKS_STAIRS = BLOCKS.register("mossy_darkened_rock_bricks_stairs", () -> new StairsBlock(DARKENED_ROCK.get().getDefaultState(), DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_PILLAR = BLOCKS.register("darkened_rock_pillar", () -> new RotatedPillarBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_DARKENED_ROCK_BRICKS = BLOCKS.register("chiseled_darkened_rock_bricks", () -> new Block(DARKENED_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> DARKENED_ROCK_PRESSURE_PLATE = BLOCKS.register("darkened_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_WALL = BLOCKS.register("darkened_rock_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_ROCK_BRICKS_WALL = BLOCKS.register("darkened_rock_bricks_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> MOSSY_DARKENED_ROCK_BRICKS_WALL = BLOCKS.register("mossy_darkened_rock_bricks_wall", () -> new WallBlock(DARKENED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> DARKENED_LANTERN = BLOCKS.register("darkened_lantern", () -> new LanternBlock(DARKENED_ROCK_PROPERTIES().setLightLevel(MalumBlocks::lanternLight)));
    //endregion
    
    //region crimson rock
    public static final RegistryObject<Block> CRIMSON_ROCK = BLOCKS.register("crimson_rock", () -> new Block(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRIMSON_ROCK_SLAB = BLOCKS.register("crimson_rock_slab", () -> new SlabBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRIMSON_ROCK_STAIRS = BLOCKS.register("crimson_rock_stairs", () -> new StairsBlock(CRIMSON_ROCK.get().getDefaultState(), CRIMSON_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> POLISHED_CRIMSON_ROCK = BLOCKS.register("polished_crimson_rock", () -> new Block(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CRIMSON_ROCK_SLAB = BLOCKS.register("polished_crimson_rock_slab", () -> new SlabBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CRIMSON_ROCK_STAIRS = BLOCKS.register("polished_crimson_rock_stairs", () -> new StairsBlock(CRIMSON_ROCK.get().getDefaultState(), CRIMSON_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> SMOOTH_CRIMSON_ROCK = BLOCKS.register("smooth_crimson_rock", () -> new Block(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_CRIMSON_ROCK_SLAB = BLOCKS.register("smooth_crimson_rock_slab", () -> new SlabBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_CRIMSON_ROCK_STAIRS = BLOCKS.register("smooth_crimson_rock_stairs", () -> new StairsBlock(CRIMSON_ROCK.get().getDefaultState(), CRIMSON_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CRIMSON_ROCK_BRICKS = BLOCKS.register("crimson_rock_bricks", () -> new Block(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRIMSON_ROCK_BRICKS_SLAB = BLOCKS.register("crimson_rock_bricks_slab", () -> new SlabBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRIMSON_ROCK_BRICKS_STAIRS = BLOCKS.register("crimson_rock_bricks_stairs", () -> new StairsBlock(CRIMSON_ROCK.get().getDefaultState(), CRIMSON_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CRACKED_CRIMSON_ROCK_BRICKS = BLOCKS.register("cracked_crimson_rock_bricks", () -> new Block(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CRIMSON_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_crimson_rock_bricks_slab", () -> new SlabBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CRIMSON_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_crimson_rock_bricks_stairs", () -> new StairsBlock(CRIMSON_ROCK.get().getDefaultState(), CRIMSON_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> BLAZING_CRIMSON_ROCK_BRICKS = BLOCKS.register("blazing_crimson_rock_bricks", () -> new Block(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> BLAZING_CRIMSON_ROCK_BRICKS_SLAB = BLOCKS.register("blazing_crimson_rock_bricks_slab", () -> new SlabBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> BLAZING_CRIMSON_ROCK_BRICKS_STAIRS = BLOCKS.register("blazing_crimson_rock_bricks_stairs", () -> new StairsBlock(CRIMSON_ROCK.get().getDefaultState(), CRIMSON_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CRIMSON_ROCK_PILLAR = BLOCKS.register("crimson_rock_pillar", () -> new RotatedPillarBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_CRIMSON_ROCK_BRICKS = BLOCKS.register("chiseled_crimson_rock_bricks", () -> new Block(CRIMSON_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CRIMSON_ROCK_PRESSURE_PLATE = BLOCKS.register("crimson_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRIMSON_ROCK_WALL = BLOCKS.register("crimson_rock_wall", () -> new WallBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRIMSON_ROCK_BRICKS_WALL = BLOCKS.register("crimson_rock_bricks_wall", () -> new WallBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> BLAZING_CRIMSON_ROCK_BRICKS_WALL = BLOCKS.register("blazing_crimson_rock_bricks_wall", () -> new WallBlock(CRIMSON_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRIMSON_LANTERN = BLOCKS.register("crimson_lantern", () -> new LanternBlock(CRIMSON_ROCK_PROPERTIES().setLightLevel(MalumBlocks::lanternLight)));
    //endregion
    
    //region archaic blocks
    public static final RegistryObject<Block> ARCHAIC_ROCK = BLOCKS.register("archaic_rock", () -> new Block(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ARCHAIC_ROCK_SLAB = BLOCKS.register("archaic_rock_slab", () -> new SlabBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ARCHAIC_ROCK_STAIRS = BLOCKS.register("archaic_rock_stairs", () -> new StairsBlock(ARCHAIC_ROCK.get().getDefaultState(), ARCHAIC_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> POLISHED_ARCHAIC_ROCK = BLOCKS.register("polished_archaic_rock", () -> new Block(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_ARCHAIC_ROCK_SLAB = BLOCKS.register("polished_archaic_rock_slab", () -> new SlabBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_ARCHAIC_ROCK_STAIRS = BLOCKS.register("polished_archaic_rock_stairs", () -> new StairsBlock(ARCHAIC_ROCK.get().getDefaultState(), ARCHAIC_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> SMOOTH_ARCHAIC_ROCK = BLOCKS.register("smooth_archaic_rock", () -> new Block(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_ARCHAIC_ROCK_SLAB = BLOCKS.register("smooth_archaic_rock_slab", () -> new SlabBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_ARCHAIC_ROCK_STAIRS = BLOCKS.register("smooth_archaic_rock_stairs", () -> new StairsBlock(ARCHAIC_ROCK.get().getDefaultState(), ARCHAIC_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> ARCHAIC_ROCK_BRICKS = BLOCKS.register("archaic_rock_bricks", () -> new Block(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ARCHAIC_ROCK_BRICKS_SLAB = BLOCKS.register("archaic_rock_bricks_slab", () -> new SlabBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ARCHAIC_ROCK_BRICKS_STAIRS = BLOCKS.register("archaic_rock_bricks_stairs", () -> new StairsBlock(ARCHAIC_ROCK.get().getDefaultState(), ARCHAIC_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> CRACKED_ARCHAIC_ROCK_BRICKS = BLOCKS.register("cracked_archaic_rock_bricks", () -> new Block(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ARCHAIC_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_archaic_rock_bricks_slab", () -> new SlabBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ARCHAIC_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_archaic_rock_bricks_stairs", () -> new StairsBlock(ARCHAIC_ROCK.get().getDefaultState(), ARCHAIC_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> BLAZING_ARCHAIC_ROCK_BRICKS = BLOCKS.register("blazing_archaic_rock_bricks", () -> new Block(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> BLAZING_ARCHAIC_ROCK_BRICKS_SLAB = BLOCKS.register("blazing_archaic_rock_bricks_slab", () -> new SlabBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> BLAZING_ARCHAIC_ROCK_BRICKS_STAIRS = BLOCKS.register("blazing_archaic_rock_bricks_stairs", () -> new StairsBlock(ARCHAIC_ROCK.get().getDefaultState(), ARCHAIC_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> ARCHAIC_ROCK_PILLAR = BLOCKS.register("archaic_rock_pillar", () -> new RotatedPillarBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_ARCHAIC_ROCK_BRICKS = BLOCKS.register("chiseled_archaic_rock_bricks", () -> new Block(ARCHAIC_ROCK_PROPERTIES()));
    
    public static final RegistryObject<Block> ARCHAIC_ROCK_PRESSURE_PLATE = BLOCKS.register("archaic_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ARCHAIC_ROCK_WALL = BLOCKS.register("archaic_rock_wall", () -> new WallBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ARCHAIC_ROCK_BRICKS_WALL = BLOCKS.register("archaic_rock_bricks_wall", () -> new WallBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> BLAZING_ARCHAIC_ROCK_BRICKS_WALL = BLOCKS.register("blazing_archaic_rock_bricks_wall", () -> new WallBlock(ARCHAIC_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ARCHAIC_LANTERN = BLOCKS.register("archaic_lantern", () -> new LanternBlock(ARCHAIC_ROCK_PROPERTIES().setLightLevel(MalumBlocks::lanternLight)));
    //endregion
    
    //region sun kissed wood
    public static final RegistryObject<Block> SUN_KISSED_LOG = BLOCKS.register("sun_kissed_log", () -> new RotatedPillarBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> STRIPPED_SUN_KISSED_LOG = BLOCKS.register("stripped_sun_kissed_log", () -> new RotatedPillarBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_WOOD = BLOCKS.register("sun_kissed_wood", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> STRIPPED_SUN_KISSED_WOOD = BLOCKS.register("stripped_sun_kissed_wood", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS = BLOCKS.register("sun_kissed_planks", () -> new Block(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_SLAB = BLOCKS.register("sun_kissed_planks_slab", () -> new SlabBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_STAIRS = BLOCKS.register("sun_kissed_planks_stairs", () -> new StairsBlock(SUN_KISSED_PLANKS.get().getDefaultState(), SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> SUN_KISSED_DOOR = BLOCKS.register("sun_kissed_door", () -> new DoorBlock(SUN_KISSED_WOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SUN_KISSED_TRAPDOOR = BLOCKS.register("sun_kissed_trapdoor", () -> new TrapDoorBlock(SUN_KISSED_WOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SOLID_SUN_KISSED_TRAPDOOR = BLOCKS.register("solid_sun_kissed_trapdoor", () -> new TrapDoorBlock(SUN_KISSED_WOOD_PROPERTIES().notSolid()));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_BUTTON = BLOCKS.register("sun_kissed_planks_button", () -> new WoodButtonBlock(SUN_KISSED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_FENCE = BLOCKS.register("sun_kissed_planks_fence", () -> new FenceBlock(SUN_KISSED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_PLANKS_FENCE_GATE = BLOCKS.register("sun_kissed_planks_fence_gate", () -> new FenceGateBlock(SUN_KISSED_WOOD_PROPERTIES()));
    //endregion
    
    //region tainted wood
    public static final RegistryObject<Block> TAINTED_LOG = BLOCKS.register("tainted_log", () -> new TaintedLogBlock(TAINTED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> STRIPPED_TAINTED_LOG = BLOCKS.register("stripped_tainted_log", () -> new TaintedLogBlock(TAINTED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_WOOD = BLOCKS.register("tainted_wood", () -> new TaintedBlock(TAINTED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> STRIPPED_TAINTED_WOOD = BLOCKS.register("stripped_tainted_wood", () -> new TaintedBlock(TAINTED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_PLANKS = BLOCKS.register("tainted_planks", () -> new TaintedBlock(TAINTED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_PLANKS_SLAB = BLOCKS.register("tainted_planks_slab", () -> new TaintedSlabBlock(TAINTED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_PLANKS_STAIRS = BLOCKS.register("tainted_planks_stairs", () -> new TaintedStairsBlock(TAINTED_PLANKS.get().getDefaultState(), TAINTED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_DOOR = BLOCKS.register("tainted_door", () -> new TaintedDoorBlock(TAINTED_WOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> TAINTED_TRAPDOOR = BLOCKS.register("tainted_trapdoor", () -> new TaintedTrapdoorBlock(TAINTED_WOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SOLID_TAINTED_TRAPDOOR = BLOCKS.register("solid_tainted_trapdoor", () -> new TaintedTrapdoorBlock(TAINTED_WOOD_PROPERTIES().notSolid()));
    
    public static final RegistryObject<Block> TAINTED_PLANKS_BUTTON = BLOCKS.register("tainted_planks_button", () -> new TaintedButtonBlock(TAINTED_WOOD_PROPERTIES()));
    
    public static final RegistryObject<Block> TAINTED_PLANKS_FENCE = BLOCKS.register("tainted_planks_fence", () -> new TaintedFenceBlock(TAINTED_WOOD_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_PLANKS_FENCE_GATE = BLOCKS.register("tainted_planks_fence_gate", () -> new TaintedFenceGateBlock(TAINTED_WOOD_PROPERTIES()));
    //endregion
    public static final RegistryObject<Block> MARIGOLD = BLOCKS.register("marigold", () -> new BushBlock(SUN_KISSED_PLANTS_PROPERTIES()));
    
    //region sun kissed biome blocks
    public static final RegistryObject<Block> SUN_KISSED_GRASS_BLOCK = BLOCKS.register("sun_kissed_grass_block", () -> new GrassBlock(SUN_KISSED_GRASS_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TALL_SUN_KISSED_GRASS = BLOCKS.register("tall_sun_kissed_grass", () -> new DoublePlantBlock(SUN_KISSED_PLANTS_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_GRASS = BLOCKS.register("sun_kissed_grass", () -> new MalumTallGrassBlock(SUN_KISSED_PLANTS_PROPERTIES(), TALL_SUN_KISSED_GRASS));
    public static final RegistryObject<Block> SHORT_SUN_KISSED_GRASS = BLOCKS.register("short_sun_kissed_grass", () -> new MalumTallGrassBlock(SUN_KISSED_PLANTS_PROPERTIES(), TALL_SUN_KISSED_GRASS));
    public static final RegistryObject<Block> SUN_KISSED_SAPLING = BLOCKS.register("sun_kissed_sapling", () -> new SaplingBlock(new SunKissedTree(), SUN_KISSED_PLANTS_PROPERTIES()));
    public static final RegistryObject<Block> SUN_KISSED_LEAVES = BLOCKS.register("sun_kissed_leaves", () -> new MalumLeavesBlock(LEAVES_PROPERTIES(), new Color(234, 129, 56), new Color(255, 230, 93)));
    
    public static final RegistryObject<Block> LAVENDER = BLOCKS.register("lavender", () -> new DoublePlantBlock(SUN_KISSED_PLANTS_PROPERTIES()));
    //endregion
    
    //region tainted biome blocks
    public static final RegistryObject<Block> TAINTED_GRASS_BLOCK = BLOCKS.register("tainted_grass_block", () -> new TaintedGrassBlock(TAINTED_GRASS_BLOCK_PROPERTIES()));
    
    public static final RegistryObject<Block> TALL_TAINTED_GRASS = BLOCKS.register("tall_tainted_grass", () -> new TaintedDoublePlantBlock(TAINTED_PLANTS_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_GRASS = BLOCKS.register("tainted_grass", () -> new TaintedTallGrassBlock(TAINTED_PLANTS_PROPERTIES(), TALL_TAINTED_GRASS));
    public static final RegistryObject<Block> SHORT_TAINTED_GRASS = BLOCKS.register("short_tainted_grass", () -> new TaintedTallGrassBlock(TAINTED_PLANTS_PROPERTIES(), TALL_TAINTED_GRASS));
    public static final RegistryObject<Block> TAINTED_SAPLING = BLOCKS.register("tainted_sapling", () -> new TaintedSaplingBlock(new TaintedTree(), TAINTED_PLANTS_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_LEAVES = BLOCKS.register("tainted_leaves", () -> new TaintedLeavesBlock(LEAVES_PROPERTIES(), new Color(182, 11, 79), new Color(250, 84, 188)));
    //endregion
    public static final RegistryObject<Block> TAINTED_LAVENDER = BLOCKS.register("tainted_lavender", () -> new TaintedDoublePlantBlock(TAINTED_PLANTS_PROPERTIES()));
    //endregion
    
    //region contents
    public static final RegistryObject<Block> BLAZE_QUARTZ_ORE = BLOCKS.register("blaze_quartz_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.NETHER_QUARTZ_ORE)));
    public static final RegistryObject<Block> SOLAR_ORE = BLOCKS.register("solar_ore", () -> new Block(AbstractBlock.Properties.create(Material.ROCK).sound(MalumSounds.SOLAR_ORE).setRequiresTool().hardnessAndResistance(6.0F, 3600000.0F)));
    public static final RegistryObject<Block> ABSTRUSE_BLOCK = BLOCKS.register("abstruse_block", () -> new AbstruseBlock(ABSTRUSE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TRANSMISSIVE_METAL_BLOCK = BLOCKS.register("transmissive_metal_block", () -> new Block(TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TRANSMISSIVE_METAL_BLOCK_SLAB = BLOCKS.register("transmissive_metal_block_slab", () -> new SlabBlock(TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TRANSMISSIVE_METAL_BLOCK_STAIRS = BLOCKS.register("transmissive_metal_block_stairs", () -> new StairsBlock(TRANSMISSIVE_METAL_BLOCK.get().getDefaultState(), TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TRANSMISSIVE_METAL_TILES = BLOCKS.register("transmissive_metal_tiles", () -> new Block(TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TRANSMISSIVE_METAL_TILES_SLAB = BLOCKS.register("transmissive_metal_tiles_slab", () -> new SlabBlock(TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TRANSMISSIVE_METAL_TILES_STAIRS = BLOCKS.register("transmissive_metal_tiles_stairs", () -> new StairsBlock(TRANSMISSIVE_METAL_TILES.get().getDefaultState(), TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> STACKED_TRANSMISSIVE_METAL = BLOCKS.register("stacked_transmissive_metal", () -> new Block(TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> STACKED_TRANSMISSIVE_METAL_SLAB = BLOCKS.register("stacked_transmissive_metal_slab", () -> new SlabBlock(TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> STACKED_TRANSMISSIVE_METAL_STAIRS = BLOCKS.register("stacked_transmissive_metal_stairs", () -> new StairsBlock(STACKED_TRANSMISSIVE_METAL.get().getDefaultState(), TRANSMISSIVE_BLOCK_PROPERTIES()));
    
    public static final RegistryObject<Block> RUIN_PLATING_BLOCK = BLOCKS.register("ruin_plating_block", () -> new Block(RUIN_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> RUIN_PLATING_BLOCK_SLAB = BLOCKS.register("ruin_plating_block_slab", () -> new SlabBlock(RUIN_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> RUIN_PLATING_BLOCK_STAIRS = BLOCKS.register("ruin_plating_block_stairs", () -> new StairsBlock(RUIN_PLATING_BLOCK.get().getDefaultState(), RUIN_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> RUIN_PLATING_TILES = BLOCKS.register("ruin_plating_tiles", () -> new Block(RUIN_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> RUIN_PLATING_TILES_SLAB = BLOCKS.register("ruin_plating_tiles_slab", () -> new SlabBlock(RUIN_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> RUIN_PLATING_TILES_STAIRS = BLOCKS.register("ruin_plating_tiles_stairs", () -> new StairsBlock(RUIN_PLATING_TILES.get().getDefaultState(), RUIN_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> STACKED_RUIN_PLATING = BLOCKS.register("stacked_ruin_plating", () -> new Block(RUIN_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> STACKED_RUIN_PLATING_SLAB = BLOCKS.register("stacked_ruin_plating_slab", () -> new SlabBlock(RUIN_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> STACKED_RUIN_PLATING_STAIRS = BLOCKS.register("stacked_ruin_plating_stairs", () -> new StairsBlock(STACKED_RUIN_PLATING.get().getDefaultState(), RUIN_BLOCK_PROPERTIES()));
    
    public static final RegistryObject<Block> WITHER_SAND = BLOCKS.register("wither_sand", () -> new WitherSandBlock(AbstractBlock.Properties.from(Blocks.SOUL_SAND)));
    //endregion
    
    //region crafting blocks
    public static final RegistryObject<Block> ARCANE_CRAFTING_TABLE = BLOCKS.register("arcane_crafting_table", () -> new ArcaneCraftingTableBlock(TAINTED_WOOD_PROPERTIES().notSolid()));
    
    public static final RegistryObject<Block> BLIGHTING_FURNACE = BLOCKS.register("blighting_furnace", () -> new BlightingFurnaceBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> BLIGHTING_FURNACE_TOP = BLOCKS.register("blighting_furnace_top", () -> new BlightedFurnaceBoundingBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    //endregion
    
    //region essence handling
    public static final RegistryObject<Block> ESSENCE_JAR = BLOCKS.register("essence_jar", () -> new SpiritJarBlock(ESSENCE_JAR_PROPERTIES()));
    public static final RegistryObject<Block> ESSENCE_PIPE = BLOCKS.register("essence_pipe", () -> new SpiritPipeBlock(TRANSMISSIVE_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> OPEN_TRANSMISSIVE_METAL_BLOCK = BLOCKS.register("open_transmissive_metal_block", () -> new OpenTransmissiveMetalBlock(TRANSMISSIVE_BLOCK_PROPERTIES()));
    //endregion
    
    public static int lanternLight(BlockState state)
    {
        if (ModList.get().isLoaded("hypcore"))
        {
            if (FMLEnvironment.dist == Dist.CLIENT)
            {
                return 0;
            }
        }
        return 12;
    }
}