package com.sammy.malum.core.registry.block;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import com.sammy.malum.client.ClientHelper;
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
import com.sammy.malum.common.block.totem.TotemBaseBlock;
import com.sammy.malum.common.block.totem.TotemPoleBlock;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static com.sammy.malum.MalumMod.MODID;
import static net.minecraft.block.PressurePlateBlock.Sensitivity.EVERYTHING;
import static net.minecraft.block.PressurePlateBlock.Sensitivity.MOBS;


@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid= MalumMod.MODID, value= Dist.CLIENT, bus= Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegistry
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static Properties TAINTED_ROCK_PROPERTIES()
    {
        return Properties.create(Material.ROCK, MaterialColor.STONE).sound(SoundRegistry.TAINTED_ROCK).setRequiresTool().hardnessAndResistance(1.25F, 9.0F);
    }

    public static Properties TWISTED_ROCK_PROPERTIES()
    {
        return Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().sound(SoundRegistry.TWISTED_ROCK).hardnessAndResistance(1.25F, 9.0F);
    }

    public static Properties SOULSTONE_PROPERTIES()
    {
        return Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(5.0F, 3.0F).sound(SoundRegistry.SOULSTONE);
    }

    public static Properties BLAZE_QUARTZ_ORE_PROPERTIES()
    {
        return Properties.create(Material.ROCK, MaterialColor.NETHERRACK).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).sound(SoundRegistry.BLAZING_QUARTZ);
    }

    public static Properties BLAZE_QUARTZ_PROPERTIES()
    {
        return Properties.create(Material.ROCK, MaterialColor.RED).setRequiresTool().hardnessAndResistance(3.0F, 3.0F).sound(SoundType.NETHER_ORE);
    }

    public static Properties RUNEWOOD_PROPERTIES()
    {
        return Properties.create(Material.WOOD, MaterialColor.YELLOW).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F);
    }

    public static Properties RUNEWOOD_PLANTS()
    {
        return Properties.create(Material.PLANTS, MaterialColor.YELLOW).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).harvestTool(ToolType.HOE).zeroHardnessAndResistance();
    }

    public static Properties SOULWOOD_PROPERTIES()
    {
        return Properties.create(Material.WOOD, MaterialColor.PURPLE).sound(SoundType.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(1.75F, 4.0F);
    }

    public static Properties SOULWOOD_PLANTS()
    {
        return Properties.create(Material.PLANTS, MaterialColor.PURPLE).doesNotBlockMovement().notSolid().sound(SoundType.PLANT).harvestTool(ToolType.HOE).zeroHardnessAndResistance();
    }

    public static Properties LEAVES_PROPERTIES()
    {
        return Properties.from(Blocks.OAK_LEAVES).harvestTool(ToolType.HOE);
    }

    public static Properties ETHER_BLOCK_PROPERTIES()
    {
        return Properties.create(Material.GLASS, MaterialColor.BLUE).sound(SoundRegistry.ETHER).doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14));
    }

    public static Properties ABSTRUSE_BLOCK_PROPERTIES()
    {
        return Properties.create(Material.GLASS, MaterialColor.BLUE).sound(SoundType.CLOTH).zeroHardnessAndResistance().noDrops().notSolid();
    }

    public static Properties HALLOWED_GOLD_PROPERTIES()
    {
        return Properties.create(Material.IRON, MaterialColor.YELLOW).sound(SoundRegistry.HALLOWED_GOLD).notSolid().hardnessAndResistance(2F, 16.0F);
    }

    public static Properties SOUL_STAINED_STEEL_BLOCK_PROPERTIES()
    {
        return Properties.create(Material.IRON, MaterialColor.BLUE).sound(SoundRegistry.SOUL_STAINED_STEEL).hardnessAndResistance(5f, 3600f);
    }

    public static Properties SPIRIT_JAR_PROPERTIES()
    {
        return Properties.create(Material.GLASS, MaterialColor.BLUE).sound(SoundRegistry.HALLOWED_GOLD).notSolid();
    }

    //region useful blocks
    public static final RegistryObject<Block> SPIRIT_ALTAR = BLOCKS.register("spirit_altar", () -> new SpiritAltarBlock(RUNEWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SPIRIT_JAR = BLOCKS.register("spirit_jar", () -> new SpiritJarBlock(HALLOWED_GOLD_PROPERTIES().notSolid()));

    public static final RegistryObject<Block> RUNEWOOD_TOTEM_BASE = BLOCKS.register("runewood_totem_base", () -> new TotemBaseBlock(RUNEWOOD_PROPERTIES().notSolid(), false));
    public static final RegistryObject<Block> RUNEWOOD_TOTEM_POLE = BLOCKS.register("runewood_totem_pole", () -> new TotemPoleBlock(RUNEWOOD_PROPERTIES().notSolid(), BlockRegistry.RUNEWOOD_LOG, false));

    public static final RegistryObject<Block> SOULWOOD_TOTEM_BASE = BLOCKS.register("soulwood_totem_base", () -> new TotemBaseBlock(RUNEWOOD_PROPERTIES().notSolid(), true));
    public static final RegistryObject<Block> SOULWOOD_TOTEM_POLE = BLOCKS.register("soulwood_totem_pole", () -> new TotemPoleBlock(RUNEWOOD_PROPERTIES().notSolid(), BlockRegistry.SOULWOOD_LOG, true));
    //endregion

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

    public static final RegistryObject<Block> TAINTED_ROCK_STAIRS = BLOCKS.register("tainted_rock_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TAINTED_ROCK_STAIRS = BLOCKS.register("smooth_tainted_rock_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TAINTED_ROCK_STAIRS = BLOCKS.register("polished_tainted_rock_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("tainted_rock_bricks_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_tainted_rock_bricks_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_tainted_rock_bricks_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("tainted_rock_tiles_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TAINTED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_tainted_rock_tiles_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_small_tainted_rock_bricks_stairs", () -> new StairsBlock(()->TAINTED_ROCK.get().getDefaultState(), TAINTED_ROCK_PROPERTIES()));

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

    public static final RegistryObject<Block> TWISTED_ROCK_STAIRS = BLOCKS.register("twisted_rock_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMOOTH_TWISTED_ROCK_STAIRS = BLOCKS.register("smooth_twisted_rock_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> POLISHED_TWISTED_ROCK_STAIRS = BLOCKS.register("polished_twisted_rock_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("twisted_rock_bricks_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_twisted_rock_bricks_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> SMALL_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("small_twisted_rock_bricks_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> TWISTED_ROCK_TILES_STAIRS = BLOCKS.register("twisted_rock_tiles_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_TWISTED_ROCK_TILES_STAIRS = BLOCKS.register("cracked_twisted_rock_tiles_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));
    public static final RegistryObject<Block> CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS = BLOCKS.register("cracked_small_twisted_rock_bricks_stairs", () -> new StairsBlock(()->TWISTED_ROCK.get().getDefaultState(), TWISTED_ROCK_PROPERTIES()));

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

    //region runewood
    public static final RegistryObject<Block> RUNEWOOD_SAPLING = BLOCKS.register("runewood_sapling", () -> new RunewoodSaplingBlock(RUNEWOOD_PLANTS().tickRandomly()));
    public static final RegistryObject<Block> RUNEWOOD_LEAVES = BLOCKS.register("runewood_leaves", () -> new MalumLeavesBlock(LEAVES_PROPERTIES(), new Color(175, 65, 48), new Color(251, 193, 76)));

    public static final RegistryObject<Block> STRIPPED_RUNEWOOD_LOG = BLOCKS.register("stripped_runewood_log", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_LOG = BLOCKS.register("runewood_log", () -> new RunewoodLogBlock(RUNEWOOD_PROPERTIES(), STRIPPED_RUNEWOOD_LOG, RUNEWOOD_TOTEM_POLE));
    public static final RegistryObject<Block> STRIPPED_RUNEWOOD = BLOCKS.register("stripped_runewood", () -> new RotatedPillarBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD = BLOCKS.register("runewood", () -> new MalumLogBlock(RUNEWOOD_PROPERTIES(), STRIPPED_RUNEWOOD));

    public static final RegistryObject<Block> REVEALED_RUNEWOOD_LOG = BLOCKS.register("revealed_runewood_log", () -> new SapFilledLogBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> EXPOSED_RUNEWOOD_LOG = BLOCKS.register("exposed_runewood_log", () -> new MalumLogBlock(RUNEWOOD_PROPERTIES(), REVEALED_RUNEWOOD_LOG));

    public static final RegistryObject<Block> RUNEWOOD_PLANKS = BLOCKS.register("runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_SLAB = BLOCKS.register("runewood_planks_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("runewood_planks_stairs", () -> new StairsBlock(()->RUNEWOOD_PLANKS.get().getDefaultState(), RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS = BLOCKS.register("vertical_runewood_planks", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS_SLAB = BLOCKS.register("vertical_runewood_planks_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_RUNEWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_runewood_planks_stairs", () -> new StairsBlock(()->VERTICAL_RUNEWOOD_PLANKS.get().getDefaultState(), RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> RUNEWOOD_PANEL = BLOCKS.register("runewood_panel", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PANEL_SLAB = BLOCKS.register("runewood_panel_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_PANEL_STAIRS = BLOCKS.register("runewood_panel_stairs", () -> new StairsBlock(()->RUNEWOOD_PANEL.get().getDefaultState(), RUNEWOOD_PROPERTIES()));

    public static final RegistryObject<Block> RUNEWOOD_TILES = BLOCKS.register("runewood_tiles", () -> new Block(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_TILES_SLAB = BLOCKS.register("runewood_tiles_slab", () -> new SlabBlock(RUNEWOOD_PROPERTIES()));
    public static final RegistryObject<Block> RUNEWOOD_TILES_STAIRS = BLOCKS.register("runewood_tiles_stairs", () -> new StairsBlock(()->RUNEWOOD_TILES.get().getDefaultState(), RUNEWOOD_PROPERTIES()));

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

    public static final RegistryObject<Block> RUNEWOOD_SIGN = BLOCKS.register("runewood_sign", () -> new MalumStandingSignBlock(RUNEWOOD_PROPERTIES().notSolid().doesNotBlockMovement(), WoodTypeRegistry.RUNEWOOD));
    public static final RegistryObject<Block> RUNEWOOD_WALL_SIGN = BLOCKS.register("runewood_wall_sign", () -> new MalumWallSignBlock(RUNEWOOD_PROPERTIES().notSolid().doesNotBlockMovement(), WoodTypeRegistry.RUNEWOOD));
    //endregion

    //region soulwood
    public static final RegistryObject<Block> SOULWOOD_SAPLING = BLOCKS.register("soulwood_sapling", () -> new RunewoodSaplingBlock(SOULWOOD_PLANTS().tickRandomly()));
    public static final RegistryObject<Block> SOULWOOD_LEAVES = BLOCKS.register("soulwood_leaves", () -> new MalumLeavesBlock(LEAVES_PROPERTIES(), new Color(164, 18, 79), new Color(224, 30, 214)));

    public static final RegistryObject<Block> STRIPPED_SOULWOOD_LOG = BLOCKS.register("stripped_soulwood_log", () -> new RotatedPillarBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_LOG = BLOCKS.register("soulwood_log", () -> new RunewoodLogBlock(SOULWOOD_PROPERTIES(), STRIPPED_SOULWOOD_LOG, SOULWOOD_TOTEM_POLE));
    public static final RegistryObject<Block> STRIPPED_SOULWOOD = BLOCKS.register("stripped_soulwood", () -> new RotatedPillarBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD = BLOCKS.register("soulwood", () -> new MalumLogBlock(SOULWOOD_PROPERTIES(), STRIPPED_SOULWOOD));

    public static final RegistryObject<Block> REVEALED_SOULWOOD_LOG = BLOCKS.register("revealed_soulwood_log", () -> new SapFilledLogBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> EXPOSED_SOULWOOD_LOG = BLOCKS.register("exposed_soulwood_log", () -> new MalumLogBlock(SOULWOOD_PROPERTIES(), REVEALED_SOULWOOD_LOG));

    public static final RegistryObject<Block> SOULWOOD_PLANKS = BLOCKS.register("soulwood_planks", () -> new Block(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_PLANKS_SLAB = BLOCKS.register("soulwood_planks_slab", () -> new SlabBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_PLANKS_STAIRS = BLOCKS.register("soulwood_planks_stairs", () -> new StairsBlock(()->SOULWOOD_PLANKS.get().getDefaultState(), SOULWOOD_PROPERTIES()));

    public static final RegistryObject<Block> VERTICAL_SOULWOOD_PLANKS = BLOCKS.register("vertical_soulwood_planks", () -> new Block(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_SOULWOOD_PLANKS_SLAB = BLOCKS.register("vertical_soulwood_planks_slab", () -> new SlabBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> VERTICAL_SOULWOOD_PLANKS_STAIRS = BLOCKS.register("vertical_soulwood_planks_stairs", () -> new StairsBlock(()->VERTICAL_SOULWOOD_PLANKS.get().getDefaultState(), SOULWOOD_PROPERTIES()));

    public static final RegistryObject<Block> SOULWOOD_PANEL = BLOCKS.register("soulwood_panel", () -> new Block(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_PANEL_SLAB = BLOCKS.register("soulwood_panel_slab", () -> new SlabBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_PANEL_STAIRS = BLOCKS.register("soulwood_panel_stairs", () -> new StairsBlock(()->SOULWOOD_PANEL.get().getDefaultState(), SOULWOOD_PROPERTIES()));

    public static final RegistryObject<Block> SOULWOOD_TILES = BLOCKS.register("soulwood_tiles", () -> new Block(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_TILES_SLAB = BLOCKS.register("soulwood_tiles_slab", () -> new SlabBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_TILES_STAIRS = BLOCKS.register("soulwood_tiles_stairs", () -> new StairsBlock(()->SOULWOOD_TILES.get().getDefaultState(), SOULWOOD_PROPERTIES()));

    public static final RegistryObject<Block> CUT_SOULWOOD_PLANKS = BLOCKS.register("cut_soulwood_planks", () -> new Block(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_BEAM = BLOCKS.register("soulwood_beam", () -> new RotatedPillarBlock(SOULWOOD_PROPERTIES()));

    public static final RegistryObject<Block> SOULWOOD_DOOR = BLOCKS.register("soulwood_door", () -> new DoorBlock(SOULWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SOULWOOD_TRAPDOOR = BLOCKS.register("soulwood_trapdoor", () -> new TrapDoorBlock(SOULWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SOLID_SOULWOOD_TRAPDOOR = BLOCKS.register("solid_soulwood_trapdoor", () -> new TrapDoorBlock(SOULWOOD_PROPERTIES().notSolid()));

    public static final RegistryObject<Block> SOULWOOD_PLANKS_BUTTON = BLOCKS.register("soulwood_planks_button", () -> new WoodButtonBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_PLANKS_PRESSURE_PLATE = BLOCKS.register("soulwood_planks_pressure_plate", () -> new PressurePlateBlock(EVERYTHING, SOULWOOD_PROPERTIES()));

    public static final RegistryObject<Block> SOULWOOD_PLANKS_FENCE = BLOCKS.register("soulwood_planks_fence", () -> new FenceBlock(SOULWOOD_PROPERTIES()));
    public static final RegistryObject<Block> SOULWOOD_PLANKS_FENCE_GATE = BLOCKS.register("soulwood_planks_fence_gate", () -> new FenceGateBlock(SOULWOOD_PROPERTIES()));

    public static final RegistryObject<Block> SOULWOOD_ITEM_STAND = BLOCKS.register("soulwood_item_stand", () -> new ItemStandBlock(SOULWOOD_PROPERTIES().notSolid()));
    public static final RegistryObject<Block> SOULWOOD_ITEM_PEDESTAL = BLOCKS.register("soulwood_item_pedestal", () -> new WoodItemPedestalBlock(SOULWOOD_PROPERTIES().notSolid()));

    public static final RegistryObject<Block> SOULWOOD_SIGN = BLOCKS.register("soulwood_sign", () -> new MalumStandingSignBlock(SOULWOOD_PROPERTIES().notSolid().doesNotBlockMovement(), WoodTypeRegistry.SOULWOOD));
    public static final RegistryObject<Block> SOULWOOD_WALL_SIGN = BLOCKS.register("soulwood_wall_sign", () -> new MalumWallSignBlock(SOULWOOD_PROPERTIES().notSolid().doesNotBlockMovement(), WoodTypeRegistry.SOULWOOD));
    //endregion

    //region ether
    public static final RegistryObject<Block> ETHER_TORCH = BLOCKS.register("ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14))));
    public static final RegistryObject<Block> WALL_ETHER_TORCH = BLOCKS.register("wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14)).lootFrom(ETHER_TORCH)));
    public static final RegistryObject<Block> ETHER = BLOCKS.register("ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_ETHER_BRAZIER = BLOCKS.register("tainted_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b, 14)).notSolid()));
    public static final RegistryObject<Block> TWISTED_ETHER_BRAZIER = BLOCKS.register("twisted_ether_brazier", () -> new EtherBrazierBlock(TWISTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b, 14)).notSolid()));

    public static final RegistryObject<Block> IRIDESCENT_ETHER_TORCH = BLOCKS.register("iridescent_ether_torch", () -> new EtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14))));
    public static final RegistryObject<Block> IRIDESCENT_WALL_ETHER_TORCH = BLOCKS.register("iridescent_wall_ether_torch", () -> new WallEtherTorchBlock(RUNEWOOD_PROPERTIES().doesNotBlockMovement().zeroHardnessAndResistance().setLightLevel((b) -> light(b, 14)).lootFrom(IRIDESCENT_ETHER_TORCH)));
    public static final RegistryObject<Block> IRIDESCENT_ETHER = BLOCKS.register("iridescent_ether", () -> new EtherBlock(ETHER_BLOCK_PROPERTIES()));
    public static final RegistryObject<Block> TAINTED_IRIDESCENT_ETHER_BRAZIER = BLOCKS.register("tainted_iridescent_ether_brazier", () -> new EtherBrazierBlock(TAINTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b, 14)).notSolid()));
    public static final RegistryObject<Block> TWISTED_IRIDESCENT_ETHER_BRAZIER = BLOCKS.register("twisted_iridescent_ether_brazier", () -> new EtherBrazierBlock(TWISTED_ROCK_PROPERTIES().setLightLevel((b) -> light(b, 14)).notSolid()));
    //endregion

    public static final RegistryObject<Block> BLAZING_QUARTZ_ORE = BLOCKS.register("blazing_quartz_ore", () -> new MalumOreBlock(BLAZE_QUARTZ_ORE_PROPERTIES(), 4, 12));
    public static final RegistryObject<Block> BLOCK_OF_BLAZING_QUARTZ = BLOCKS.register("block_of_blazing_quartz", () -> new Block(BLAZE_QUARTZ_PROPERTIES()));

    public static final RegistryObject<Block> BRILLIANT_STONE = BLOCKS.register("brilliant_stone", () -> new MalumOreBlock(Properties.from(Blocks.DIAMOND_ORE), 12, 24));
    public static final RegistryObject<Block> BLOCK_OF_BRILLIANCE = BLOCKS.register("block_of_brilliance", () -> new Block(Properties.from(Blocks.DIAMOND_BLOCK)));

    public static final RegistryObject<Block> SOULSTONE_ORE = BLOCKS.register("soulstone_ore", () -> new Block(SOULSTONE_PROPERTIES()));
    public static final RegistryObject<Block> BLOCK_OF_SOULSTONE = BLOCKS.register("block_of_soulstone", () -> new Block(SOULSTONE_PROPERTIES()));

    public static final RegistryObject<Block> BLOCK_OF_HALLOWED_GOLD = BLOCKS.register("block_of_hallowed_gold", () -> new Block(HALLOWED_GOLD_PROPERTIES()));
    public static final RegistryObject<Block> BLOCK_OF_SOUL_STAINED_STEEL = BLOCKS.register("block_of_soul_stained_steel", () -> new Block(SOUL_STAINED_STEEL_BLOCK_PROPERTIES()));


    public static int light(BlockState state, int strength)
    {
        return strength;
    }

    @SubscribeEvent
    public static void setRenderLayers(FMLClientSetupEvent event)
    {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());
        MalumHelper.takeAll(blocks, b -> b.get() instanceof EtherTorchBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof EtherBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BoundingBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TrapDoorBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof DoorBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SaplingBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LeavesBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof BushBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof LanternBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemStandBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof ItemPedestalBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemBaseBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof TotemPoleBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SpiritJarBlock).forEach(ClientHelper::setCutout);
        MalumHelper.takeAll(blocks, b -> b.get() instanceof SpiritAltarBlock).forEach(ClientHelper::setCutout);
        ClientHelper.setCutout(BlockRegistry.BLAZING_QUARTZ_ORE);
    }
}