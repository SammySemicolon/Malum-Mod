package com.sammy.malum.core.init.block;

import com.sammy.malum.common.block.*;
import com.sammy.malum.common.block.item_storage.ItemPedestalBlock;
import com.sammy.malum.common.block.item_storage.ItemStandBlock;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import com.sammy.malum.common.block.ether.EtherTorchBlock;
import com.sammy.malum.common.block.ether.WallEtherTorchBlock;
import com.sammy.malum.common.block.generic.MalumDirectionalBlock;
import com.sammy.malum.common.block.generic.MalumLeavesBlock;
import com.sammy.malum.common.block.generic.MalumLogBlock;
import com.sammy.malum.common.block.generic.MalumOreBlock;
import com.sammy.malum.common.block.generic.sign.MalumStandingSignBlock;
import com.sammy.malum.common.block.generic.sign.MalumWallSignBlock;
import com.sammy.malum.common.block.item_storage.WoodItemPedestalBlock;
import com.sammy.malum.common.block.spirit_altar.SpiritAltarBlock;
import com.sammy.malum.common.block.item_storage.SpiritJarBlock;
import com.sammy.malum.common.block.TotemBaseBlock;
import com.sammy.malum.common.block.TotemPoleBlock;
import com.sammy.malum.core.init.MalumSounds;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
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

    public static AbstractBlock.Properties TAINTED_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).sound(MalumSounds.TAINTED_ROCK).setRequiresTool().hardnessAndResistance(1.25F, 9.0F);
    }

    public static AbstractBlock.Properties TWISTED_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().sound(MalumSounds.TWISTED_ROCK).hardnessAndResistance(1.25F, 9.0F);
    }

    public static AbstractBlock.Properties PURIFIED_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.WHITE_TERRACOTTA).setRequiresTool().sound(MalumSounds.TWISTED_ROCK).hardnessAndResistance(1.25F, 9.0F);
    }

    public static AbstractBlock.Properties CLEANSED_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BROWN_TERRACOTTA).setRequiresTool().sound(MalumSounds.TWISTED_ROCK).hardnessAndResistance(1.25F, 9.0F);
    }

    public static AbstractBlock.Properties ERODED_ROCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.GRAY_TERRACOTTA).setRequiresTool().sound(MalumSounds.TWISTED_ROCK).hardnessAndResistance(1.25F, 9.0F);
    }

    public static AbstractBlock.Properties SOULSTONE_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(5.0F, 3.0F).sound(MalumSounds.SOULSTONE);
    }

    public static AbstractBlock.Properties BLAZE_QUARTZ_ORE_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).sound(MalumSounds.BLAZING_QUARTZ);
    }

    public static AbstractBlock.Properties BLAZE_QUARTZ_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).sound(SoundType.NETHER_ORE);
    }

    public static AbstractBlock.Properties RUNEWOOD_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F);
    }

    public static AbstractBlock.Properties LEAVES_PROPERTIES()
    {
        return AbstractBlock.Properties.from(Blocks.OAK_LEAVES).harvestTool(ToolType.HOE);
    }

    public static AbstractBlock.Properties RUNEWOOD_PLANTS()
    {
        return AbstractBlock.Properties.create(Material.PLANTS, MaterialColor.YELLOW).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).harvestTool(ToolType.HOE).zeroHardnessAndResistance();
    }

    public static AbstractBlock.Properties ETHER_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.GLASS, MaterialColor.BLUE).sound(MalumSounds.ETHER).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14));
    }

    public static AbstractBlock.Properties ABSTRUSE_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.GLASS, MaterialColor.BLUE).sound(SoundType.CLOTH).zeroHardnessAndResistance().noDrops().notSolid();
    }

    public static AbstractBlock.Properties HALLOWED_GOLD_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.IRON, MaterialColor.YELLOW).sound(MalumSounds.HALLOWED_GOLD).notSolid().hardnessAndResistance(2F, 16.0F);
    }

    public static AbstractBlock.Properties SOUL_STAINED_STEEL_BLOCK_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.IRON, MaterialColor.BLUE).sound(MalumSounds.SOUL_STAINED_STEEL).hardnessAndResistance(5f, 3600f);
    }

    public static AbstractBlock.Properties SPIRIT_JAR_PROPERTIES()
    {
        return AbstractBlock.Properties.create(Material.GLASS, MaterialColor.BLUE).sound(MalumSounds.HALLOWED_GOLD).notSolid();
    }

    //region tainted rock
    public static final RegistryObject<Block> TAINTED_ROCK = BLOCKS.register("tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK = BLOCKS.register("smooth_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK = BLOCKS.register("polished_tainted_rock", () -> new Block(TAINTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS = BLOCKS.register("tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS = BLOCKS.register("cracked_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS = BLOCKS.register("small_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TAINTED_ROCK_TILES = BLOCKS.register("tainted_rock_tiles", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES = BLOCKS.register("cracked_tainted_rock_tiles", () -> new Block(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TAINTED_ROCK_BRICKS = BLOCKS.register("cracked_small_tainted_rock_bricks", () -> new Block(TAINTED_ROCK_PROPERTIES()));

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
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("small_tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_SLAB = BLOCKS.register("tainted_rock_tiles_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES_SLAB = BLOCKS.register("cracked_tainted_rock_tiles_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_small_tainted_rock_bricks_slab", () -> new SlabBlock(TAINTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TAINTED_ROCK_STAIRS = BLOCKS.register("tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_STAIRS = BLOCKS.register("smooth_tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_STAIRS = BLOCKS.register("polished_tainted_rock_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("tainted_rock_tiles_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_tainted_rock_tiles_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_small_tainted_rock_bricks_stairs", () -> new StairsBlock(TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TAINTED_ROCK_PRESSURE_PLATE = BLOCKS.register("tainted_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, TAINTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TAINTED_ROCK_WALL = BLOCKS.register("tainted_rock_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("small_tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_WALL = BLOCKS.register("tainted_rock_tiles_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_small_tainted_rock_bricks_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES_WALL = BLOCKS.register("cracked_tainted_rock_tiles_wall", () -> new WallBlock(TAINTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TAINTED_ROCK_ITEM_STAND = BLOCKS.register("tainted_rock_item_stand", () -> new ItemStandBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> TAINTED_ROCK_ITEM_PEDESTAL = BLOCKS.register("tainted_rock_item_pedestal", () -> new ItemPedestalBlock(TAINTED_ROCK_PROPERTIES().notSolid()));
    //endregion

    //region twisted rock
    public static final RegistryObject<Block> TWISTED_ROCK = BLOCKS.register("twisted_rock", () -> new Block(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TWISTED_ROCK = BLOCKS.register("smooth_twisted_rock", () -> new Block(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TWISTED_ROCK = BLOCKS.register("polished_twisted_rock", () -> new Block(TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS = BLOCKS.register("twisted_rock_bricks", () -> new Block(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_BRICKS = BLOCKS.register("cracked_twisted_rock_bricks", () -> new Block(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS = BLOCKS.register("small_twisted_rock_bricks", () -> new Block(TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TWISTED_ROCK_TILES = BLOCKS.register("twisted_rock_tiles", () -> new Block(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_TILES = BLOCKS.register("cracked_twisted_rock_tiles", () -> new Block(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TWISTED_ROCK_BRICKS = BLOCKS.register("cracked_small_twisted_rock_bricks", () -> new Block(TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TWISTED_ROCK_PILLAR = BLOCKS.register("twisted_rock_pillar", () -> new RotatedPillarBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_PILLAR_CAP = BLOCKS.register("twisted_rock_pillar_cap", () -> new MalumDirectionalBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_COLUMN = BLOCKS.register("twisted_rock_column", () -> new RotatedPillarBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_COLUMN_CAP = BLOCKS.register("twisted_rock_column_cap", () -> new MalumDirectionalBlock(TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CUT_TWISTED_ROCK = BLOCKS.register("cut_twisted_rock", () -> new Block(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_TWISTED_ROCK = BLOCKS.register("chiseled_twisted_rock", () -> new Block(TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TWISTED_ROCK_SLAB = BLOCKS.register("twisted_rock_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TWISTED_ROCK_SLAB = BLOCKS.register("smooth_twisted_rock_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TWISTED_ROCK_SLAB = BLOCKS.register("polished_twisted_rock_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("twisted_rock_bricks_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_twisted_rock_bricks_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("small_twisted_rock_bricks_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_TILES_SLAB = BLOCKS.register("twisted_rock_tiles_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_TILES_SLAB = BLOCKS.register("cracked_twisted_rock_tiles_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_small_twisted_rock_bricks_slab", () -> new SlabBlock(TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TWISTED_ROCK_STAIRS = BLOCKS.register("twisted_rock_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TWISTED_ROCK_STAIRS = BLOCKS.register("smooth_twisted_rock_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TWISTED_ROCK_STAIRS = BLOCKS.register("polished_twisted_rock_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("twisted_rock_bricks_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_twisted_rock_bricks_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_twisted_rock_bricks_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_TILES_STAIRS = BLOCKS.register("twisted_rock_tiles_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_twisted_rock_tiles_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_small_twisted_rock_bricks_stairs", () -> new StairsBlock(TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TWISTED_ROCK_PRESSURE_PLATE = BLOCKS.register("twisted_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TWISTED_ROCK_WALL = BLOCKS.register("twisted_rock_wall", () -> new WallBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("twisted_rock_bricks_wall", () -> new WallBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("small_twisted_rock_bricks_wall", () -> new WallBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_twisted_rock_bricks_wall", () -> new WallBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_TILES_WALL = BLOCKS.register("twisted_rock_tiles_wall", () -> new WallBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_small_twisted_rock_bricks_wall", () -> new WallBlock(TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_TILES_WALL = BLOCKS.register("cracked_twisted_rock_tiles_wall", () -> new WallBlock(TWISTED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> TWISTED_ROCK_ITEM_STAND = BLOCKS.register("twisted_rock_item_stand", () -> new ItemStandBlock(TWISTED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> TWISTED_ROCK_ITEM_PEDESTAL = BLOCKS.register("twisted_rock_item_pedestal", () -> new ItemPedestalBlock(TWISTED_ROCK_PROPERTIES().notSolid()));
    //endregion

    //region purified rock
    public static final RegistryObject<Block> PURIFIED_ROCK = BLOCKS.register("purified_rock", () -> new Block(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_PURIFIED_ROCK = BLOCKS.register("smooth_purified_rock", () -> new Block(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_PURIFIED_ROCK = BLOCKS.register("polished_purified_rock", () -> new Block(PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> PURIFIED_ROCK_BRICKS = BLOCKS.register("purified_rock_bricks", () -> new Block(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_PURIFIED_ROCK_BRICKS = BLOCKS.register("cracked_purified_rock_bricks", () -> new Block(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_PURIFIED_ROCK_BRICKS = BLOCKS.register("small_purified_rock_bricks", () -> new Block(PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> PURIFIED_ROCK_TILES = BLOCKS.register("purified_rock_tiles", () -> new Block(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_PURIFIED_ROCK_TILES = BLOCKS.register("cracked_purified_rock_tiles", () -> new Block(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_PURIFIED_ROCK_BRICKS = BLOCKS.register("cracked_small_purified_rock_bricks", () -> new Block(PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> PURIFIED_ROCK_PILLAR = BLOCKS.register("purified_rock_pillar", () -> new RotatedPillarBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_PILLAR_CAP = BLOCKS.register("purified_rock_pillar_cap", () -> new MalumDirectionalBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_COLUMN = BLOCKS.register("purified_rock_column", () -> new RotatedPillarBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_COLUMN_CAP = BLOCKS.register("purified_rock_column_cap", () -> new MalumDirectionalBlock(PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CUT_PURIFIED_ROCK = BLOCKS.register("cut_purified_rock", () -> new Block(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_PURIFIED_ROCK = BLOCKS.register("chiseled_purified_rock", () -> new Block(PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> PURIFIED_ROCK_SLAB = BLOCKS.register("purified_rock_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_PURIFIED_ROCK_SLAB = BLOCKS.register("smooth_purified_rock_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_PURIFIED_ROCK_SLAB = BLOCKS.register("polished_purified_rock_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_BRICKS_SLAB = BLOCKS.register("purified_rock_bricks_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_PURIFIED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_purified_rock_bricks_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_PURIFIED_ROCK_BRICKS_SLAB = BLOCKS.register("small_purified_rock_bricks_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_TILES_SLAB = BLOCKS.register("purified_rock_tiles_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_PURIFIED_ROCK_TILES_SLAB = BLOCKS.register("cracked_purified_rock_tiles_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_PURIFIED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_small_purified_rock_bricks_slab", () -> new SlabBlock(PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> PURIFIED_ROCK_STAIRS = BLOCKS.register("purified_rock_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_PURIFIED_ROCK_STAIRS = BLOCKS.register("smooth_purified_rock_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_PURIFIED_ROCK_STAIRS = BLOCKS.register("polished_purified_rock_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_BRICKS_STAIRS = BLOCKS.register("purified_rock_bricks_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_PURIFIED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_purified_rock_bricks_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_PURIFIED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_purified_rock_bricks_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_TILES_STAIRS = BLOCKS.register("purified_rock_tiles_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_PURIFIED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_purified_rock_tiles_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_PURIFIED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_small_purified_rock_bricks_stairs", () -> new StairsBlock(PURIFIED_ROCK.get().getDefaultState(), PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> PURIFIED_ROCK_PRESSURE_PLATE = BLOCKS.register("purified_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> PURIFIED_ROCK_WALL = BLOCKS.register("purified_rock_wall", () -> new WallBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_BRICKS_WALL = BLOCKS.register("purified_rock_bricks_wall", () -> new WallBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_PURIFIED_ROCK_BRICKS_WALL = BLOCKS.register("small_purified_rock_bricks_wall", () -> new WallBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_PURIFIED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_purified_rock_bricks_wall", () -> new WallBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> PURIFIED_ROCK_TILES_WALL = BLOCKS.register("purified_rock_tiles_wall", () -> new WallBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_PURIFIED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_small_purified_rock_bricks_wall", () -> new WallBlock(PURIFIED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_PURIFIED_ROCK_TILES_WALL = BLOCKS.register("cracked_purified_rock_tiles_wall", () -> new WallBlock(PURIFIED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> PURIFIED_ROCK_ITEM_STAND = BLOCKS.register("purified_rock_item_stand", () -> new ItemStandBlock(PURIFIED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> PURIFIED_ROCK_ITEM_PEDESTAL = BLOCKS.register("purified_rock_item_pedestal", () -> new ItemPedestalBlock(PURIFIED_ROCK_PROPERTIES().notSolid()));
    //endregion

    //region cleansed rock
    public static final RegistryObject<Block> CLEANSED_ROCK = BLOCKS.register("cleansed_rock", () -> new Block(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_CLEANSED_ROCK = BLOCKS.register("smooth_cleansed_rock", () -> new Block(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CLEANSED_ROCK = BLOCKS.register("polished_cleansed_rock", () -> new Block(CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CLEANSED_ROCK_BRICKS = BLOCKS.register("cleansed_rock_bricks", () -> new Block(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CLEANSED_ROCK_BRICKS = BLOCKS.register("cracked_cleansed_rock_bricks", () -> new Block(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_CLEANSED_ROCK_BRICKS = BLOCKS.register("small_cleansed_rock_bricks", () -> new Block(CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CLEANSED_ROCK_TILES = BLOCKS.register("cleansed_rock_tiles", () -> new Block(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CLEANSED_ROCK_TILES = BLOCKS.register("cracked_cleansed_rock_tiles", () -> new Block(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_CLEANSED_ROCK_BRICKS = BLOCKS.register("cracked_small_cleansed_rock_bricks", () -> new Block(CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CLEANSED_ROCK_PILLAR = BLOCKS.register("cleansed_rock_pillar", () -> new RotatedPillarBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_PILLAR_CAP = BLOCKS.register("cleansed_rock_pillar_cap", () -> new MalumDirectionalBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_COLUMN = BLOCKS.register("cleansed_rock_column", () -> new RotatedPillarBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_COLUMN_CAP = BLOCKS.register("cleansed_rock_column_cap", () -> new MalumDirectionalBlock(CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CUT_CLEANSED_ROCK = BLOCKS.register("cut_cleansed_rock", () -> new Block(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_CLEANSED_ROCK = BLOCKS.register("chiseled_cleansed_rock", () -> new Block(CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CLEANSED_ROCK_SLAB = BLOCKS.register("cleansed_rock_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_CLEANSED_ROCK_SLAB = BLOCKS.register("smooth_cleansed_rock_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CLEANSED_ROCK_SLAB = BLOCKS.register("polished_cleansed_rock_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_BRICKS_SLAB = BLOCKS.register("cleansed_rock_bricks_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CLEANSED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_cleansed_rock_bricks_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_CLEANSED_ROCK_BRICKS_SLAB = BLOCKS.register("small_cleansed_rock_bricks_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_TILES_SLAB = BLOCKS.register("cleansed_rock_tiles_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CLEANSED_ROCK_TILES_SLAB = BLOCKS.register("cracked_cleansed_rock_tiles_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_CLEANSED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_small_cleansed_rock_bricks_slab", () -> new SlabBlock(CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CLEANSED_ROCK_STAIRS = BLOCKS.register("cleansed_rock_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_CLEANSED_ROCK_STAIRS = BLOCKS.register("smooth_cleansed_rock_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_CLEANSED_ROCK_STAIRS = BLOCKS.register("polished_cleansed_rock_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_BRICKS_STAIRS = BLOCKS.register("cleansed_rock_bricks_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CLEANSED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_cleansed_rock_bricks_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_CLEANSED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_cleansed_rock_bricks_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_TILES_STAIRS = BLOCKS.register("cleansed_rock_tiles_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CLEANSED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_cleansed_rock_tiles_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_CLEANSED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_small_cleansed_rock_bricks_stairs", () -> new StairsBlock(CLEANSED_ROCK.get().getDefaultState(), CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CLEANSED_ROCK_PRESSURE_PLATE = BLOCKS.register("cleansed_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CLEANSED_ROCK_WALL = BLOCKS.register("cleansed_rock_wall", () -> new WallBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_BRICKS_WALL = BLOCKS.register("cleansed_rock_bricks_wall", () -> new WallBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_CLEANSED_ROCK_BRICKS_WALL = BLOCKS.register("small_cleansed_rock_bricks_wall", () -> new WallBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CLEANSED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_cleansed_rock_bricks_wall", () -> new WallBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CLEANSED_ROCK_TILES_WALL = BLOCKS.register("cleansed_rock_tiles_wall", () -> new WallBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_CLEANSED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_small_cleansed_rock_bricks_wall", () -> new WallBlock(CLEANSED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_CLEANSED_ROCK_TILES_WALL = BLOCKS.register("cracked_cleansed_rock_tiles_wall", () -> new WallBlock(CLEANSED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CLEANSED_ROCK_ITEM_STAND = BLOCKS.register("cleansed_rock_item_stand", () -> new ItemStandBlock(CLEANSED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> CLEANSED_ROCK_ITEM_PEDESTAL = BLOCKS.register("cleansed_rock_item_pedestal", () -> new ItemPedestalBlock(CLEANSED_ROCK_PROPERTIES().notSolid()));
    //endregion

    //region eroded rock
    public static final RegistryObject<Block> ERODED_ROCK = BLOCKS.register("eroded_rock", () -> new Block(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_ERODED_ROCK = BLOCKS.register("smooth_eroded_rock", () -> new Block(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_ERODED_ROCK = BLOCKS.register("polished_eroded_rock", () -> new Block(ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> ERODED_ROCK_BRICKS = BLOCKS.register("eroded_rock_bricks", () -> new Block(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ERODED_ROCK_BRICKS = BLOCKS.register("cracked_eroded_rock_bricks", () -> new Block(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_ERODED_ROCK_BRICKS = BLOCKS.register("small_eroded_rock_bricks", () -> new Block(ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> ERODED_ROCK_TILES = BLOCKS.register("eroded_rock_tiles", () -> new Block(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ERODED_ROCK_TILES = BLOCKS.register("cracked_eroded_rock_tiles", () -> new Block(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_ERODED_ROCK_BRICKS = BLOCKS.register("cracked_small_eroded_rock_bricks", () -> new Block(ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> ERODED_ROCK_PILLAR = BLOCKS.register("eroded_rock_pillar", () -> new RotatedPillarBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_PILLAR_CAP = BLOCKS.register("eroded_rock_pillar_cap", () -> new MalumDirectionalBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_COLUMN = BLOCKS.register("eroded_rock_column", () -> new RotatedPillarBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_COLUMN_CAP = BLOCKS.register("eroded_rock_column_cap", () -> new MalumDirectionalBlock(ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> CUT_ERODED_ROCK = BLOCKS.register("cut_eroded_rock", () -> new Block(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CHISELED_ERODED_ROCK = BLOCKS.register("chiseled_eroded_rock", () -> new Block(ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> ERODED_ROCK_SLAB = BLOCKS.register("eroded_rock_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_ERODED_ROCK_SLAB = BLOCKS.register("smooth_eroded_rock_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_ERODED_ROCK_SLAB = BLOCKS.register("polished_eroded_rock_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_BRICKS_SLAB = BLOCKS.register("eroded_rock_bricks_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ERODED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_eroded_rock_bricks_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_ERODED_ROCK_BRICKS_SLAB = BLOCKS.register("small_eroded_rock_bricks_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_TILES_SLAB = BLOCKS.register("eroded_rock_tiles_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ERODED_ROCK_TILES_SLAB = BLOCKS.register("cracked_eroded_rock_tiles_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_ERODED_ROCK_BRICKS_SLAB = BLOCKS.register("cracked_small_eroded_rock_bricks_slab", () -> new SlabBlock(ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> ERODED_ROCK_STAIRS = BLOCKS.register("eroded_rock_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_ERODED_ROCK_STAIRS = BLOCKS.register("smooth_eroded_rock_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_ERODED_ROCK_STAIRS = BLOCKS.register("polished_eroded_rock_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_BRICKS_STAIRS = BLOCKS.register("eroded_rock_bricks_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ERODED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_eroded_rock_bricks_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_ERODED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_eroded_rock_bricks_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_TILES_STAIRS = BLOCKS.register("eroded_rock_tiles_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ERODED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_eroded_rock_tiles_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_ERODED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_small_eroded_rock_bricks_stairs", () -> new StairsBlock(ERODED_ROCK.get().getDefaultState(), ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> ERODED_ROCK_PRESSURE_PLATE = BLOCKS.register("eroded_rock_pressure_plate", () -> new PressurePlateBlock(MOBS, ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> ERODED_ROCK_WALL = BLOCKS.register("eroded_rock_wall", () -> new WallBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_BRICKS_WALL = BLOCKS.register("eroded_rock_bricks_wall", () -> new WallBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_ERODED_ROCK_BRICKS_WALL = BLOCKS.register("small_eroded_rock_bricks_wall", () -> new WallBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ERODED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_eroded_rock_bricks_wall", () -> new WallBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> ERODED_ROCK_TILES_WALL = BLOCKS.register("eroded_rock_tiles_wall", () -> new WallBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_ERODED_ROCK_BRICKS_WALL = BLOCKS.register("cracked_small_eroded_rock_bricks_wall", () -> new WallBlock(ERODED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_ERODED_ROCK_TILES_WALL = BLOCKS.register("cracked_eroded_rock_tiles_wall", () -> new WallBlock(ERODED_ROCK_PROPERTIES()));

    public static final RegistryObject<Block> ERODED_ROCK_ITEM_STAND = BLOCKS.register("eroded_rock_item_stand", () -> new ItemStandBlock(ERODED_ROCK_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> ERODED_ROCK_ITEM_PEDESTAL = BLOCKS.register("eroded_rock_item_pedestal", () -> new ItemPedestalBlock(ERODED_ROCK_PROPERTIES().notSolid()));
    //endregion

    //region runewood
    public static final RegistryObject<Block> RUNEWOOD_SAPLING = BLOCKS.register("runewood_sapling", () -> new RunewoodSaplingBlock(RUNEWOOD_PLANTS().tickRandomly()));
    public static final RegistryObject<Block> RUNEWOOD_LEAVES = BLOCKS.register("runewood_leaves", () -> new MalumLeavesBlock(LEAVES_PROPERTIES(), new Color(175, 65, 48), new Color(251, 193, 76)));

    public static final RegistryObject<Block> STRIPPED_RUNEWOOD_LOG = BLOCKS.register("stripped_runewood_log", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_LOG = BLOCKS.register("runewood_log", () -> new RunewoodLogBlock(RUNEWOOD_PROPERTIES(), STRIPPED_RUNEWOOD_LOG));
    public static final RegistryObject<Block> STRIPPED_RUNEWOOD = BLOCKS.register("stripped_runewood", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD = BLOCKS.register("runewood", () -> new MalumLogBlock(RUNEWOOD_PROPERTIES(), STRIPPED_RUNEWOOD));

    public static final RegistryObject<Block> STRIPPED_SAP_FILLED_RUNEWOOD_LOG = BLOCKS.register("stripped_sap_filled_runewood_log", () -> new SapFilledLogBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SAP_FILLED_RUNEWOOD_LOG = BLOCKS.register("sap_filled_runewood_log", () -> new MalumLogBlock(RUNEWOOD_PROPERTIES(), STRIPPED_SAP_FILLED_RUNEWOOD_LOG));

    public static final RegistryObject<Block> RUNEWOOD_PLANKS = BLOCKS.register("runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_SLAB = BLOCKS.register("runewood_planks_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("runewood_planks_stairs", () -> new StairsBlock(RUNEWOOD_PLANKS.get().getDefaultState(), RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS = BLOCKS.register("vertical_runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS_SLAB = BLOCKS.register("vertical_runewood_planks_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_runewood_planks_stairs", () -> new StairsBlock(VERTICAL_RUNEWOOD_PLANKS.get().getDefaultState(), RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> RUNEWOOD_PANEL = BLOCKS.register("runewood_panel", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PANEL_SLAB = BLOCKS.register("runewood_panel_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PANEL_STAIRS = BLOCKS.register("runewood_panel_stairs", () -> new StairsBlock(RUNEWOOD_PANEL.get().getDefaultState(), RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> RUNEWOOD_TILES = BLOCKS.register("runewood_tiles", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_TILES_SLAB = BLOCKS.register("runewood_tiles_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_TILES_STAIRS = BLOCKS.register("runewood_tiles_stairs", () -> new StairsBlock(RUNEWOOD_TILES.get().getDefaultState(), RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> CUT_RUNEWOOD_PLANKS = BLOCKS.register("cut_runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_BEAM = BLOCKS.register("runewood_beam", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> RUNEWOOD_DOOR = BLOCKS.register("runewood_door", () -> new DoorBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> RUNEWOOD_TRAPDOOR = BLOCKS.register("runewood_trapdoor", () -> new TrapDoorBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SOLID_RUNEWOOD_TRAPDOOR = BLOCKS.register("solid_runewood_trapdoor", () -> new TrapDoorBlock(RUNEWOOD_PROPERTIES().notSolid()));

    public static final RegistryObject<Block> RUNEWOOD_PLANKS_BUTTON = BLOCKS.register("runewood_planks_button", () -> new WoodButtonBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_PRESSURE_PLATE = BLOCKS.register("runewood_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> RUNEWOOD_PLANKS_FENCE = BLOCKS.register("runewood_planks_fence", () -> new FenceBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_FENCE_GATE = BLOCKS.register("runewood_planks_fence_gate", () -> new FenceGateBlock(RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> RUNEWOOD_ITEM_STAND = BLOCKS.register("runewood_item_stand", () -> new ItemStandBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> RUNEWOOD_ITEM_PEDESTAL = BLOCKS.register("runewood_item_pedestal", () -> new WoodItemPedestalBlock(RUNEWOOD_PROPERTIES().notSolid()));

    public static final RegistryObject<Block> RUNEWOOD_SIGN = BLOCKS.register("runewood_sign", () -> new MalumStandingSignBlock(RUNEWOOD_PROPERTIES().notSolid().doesNotBlockMovement(), MalumWoodTypes.RUNEWOOD));
    public static final RegistryObject<Block> RUNEWOOD_WALL_SIGN = BLOCKS.register("runewood_wall_sign", () -> new MalumWallSignBlock(RUNEWOOD_PROPERTIES().notSolid().doesNotBlockMovement(), MalumWoodTypes.RUNEWOOD));

    //endregion

    //region ether
    public static final RegistryObject<Block> ETHER_TORCH = BLOCKS.register("ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14))));
    public static final RegistryObject<Block> WALL_ETHER_TORCH = BLOCKS.register("wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14)).lootFrom(ETHER_TORCH)));
    public static final RegistryObject<Block> ETHER = BLOCKS.register("ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> ETHER_BRAZIER = BLOCKS.register("ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b, 14))));

    public static final RegistryObject<Block> IRIDESCENT_ETHER_TORCH = BLOCKS.register("iridescent_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14))));
    public static final RegistryObject<Block> IRIDESCENT_WALL_ETHER_TORCH = BLOCKS.register("iridescent_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14)).lootFrom(IRIDESCENT_ETHER_TORCH)));
    public static final RegistryObject<Block> IRIDESCENT_ETHER = BLOCKS.register("iridescent_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> IRIDESCENT_ETHER_BRAZIER = BLOCKS.register("iridescent_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b, 14))));


    //endregion


    public static final RegistryObject<Block> BLAZING_QUARTZ_ORE = BLOCKS.register("blazing_quartz_ore", () -> new MalumOreBlock(BLAZE_QUARTZ_ORE_PROPERTIES(), 4, 12));
    public static final RegistryObject<Block> BLAZING_QUARTZ_BLOCK = BLOCKS.register("blazing_quartz_block", () -> new Block(BLAZE_QUARTZ_PROPERTIES()));

    public static final RegistryObject<Block> SOULSTONE_ORE = BLOCKS.register("soulstone_ore", () -> new Block(SOULSTONE_PROPERTIES()));
    public static final RegistryObject<Block> SOULSTONE_BLOCK = BLOCKS.register("soulstone_block", () -> new Block(SOULSTONE_PROPERTIES()));

    public static final RegistryObject<Block> HALLOWED_GOLD_BLOCK = BLOCKS.register("hallowed_gold_block", () -> new Block(HALLOWED_GOLD_PROPERTIES()));
    public static final RegistryObject<Block> SOUL_STAINED_STEEL_BLOCK = BLOCKS.register("soul_stained_steel_block", () -> new Block(SOUL_STAINED_STEEL_BLOCK_PROPERTIES()));

    //region useful blocks
    public static final RegistryObject<Block> SPIRIT_ALTAR = BLOCKS.register("spirit_altar", () -> new SpiritAltarBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SPIRIT_JAR = BLOCKS.register("spirit_jar", () -> new SpiritJarBlock(HALLOWED_GOLD_PROPERTIES().notSolid()));

    public static final RegistryObject<Block> TOTEM_BASE = BLOCKS.register("totem_base", () -> new TotemBaseBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> TOTEM_POLE = BLOCKS.register("totem_pole", () -> new TotemPoleBlock(RUNEWOOD_PROPERTIES().notSolid().lootFrom(MalumBlocks.RUNEWOOD_LOG)));

    //endregion

    public static int light(BlockState state, int strength)
    {
        return strength;
    }
}