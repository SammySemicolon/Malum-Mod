package com.sammy.malum.init;

import com.sammy.malum.MalumMod;
import com.sammy.malum.blocks.machines.funkengine.FunkEngineBlock;
import com.sammy.malum.blocks.machines.spiritfurnace.SpiritFurnaceBlock;
import com.sammy.malum.blocks.machines.spiritfurnace.SpiritFurnaceBoundingBlock;
import com.sammy.malum.blocks.machines.mirror.BasicMirrorBlock;
import com.sammy.malum.blocks.machines.redstoneclock.RedstoneClockBlock;
import com.sammy.malum.blocks.machines.spiritjar.SpiritJarBlock;
import com.sammy.malum.blocks.machines.spiritsmeltery.SpiritSmelteryBlock;
import com.sammy.malum.blocks.machines.spiritsmeltery.SpiritSmelteryBoundingBlock;
import com.sammy.malum.blocks.special.FleshBlock;
import com.sammy.malum.blocks.utility.ModSlabBlock;
import com.sammy.malum.blocks.utility.ModStairsBlock;
import com.sammy.malum.world.SpiritwoodTree;
import net.minecraft.block.*;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks
{

    //FUNCTIONAL BLOCKS
    public static Block spirit_jar;
    public static Block spirit_furnace;
    public static Block basic_mirror;
    public static Block input_mirror;
    public static Block output_mirror;
    public static Block funk_engine;
    public static Block redstone_clock;
    public static Block spirit_smeltery;
    
    public static Block spirit_furnace_bounding_block;
    public static Block spirit_smeltery_bounding_block;
    //ORES

    public static Block arcane_stone;
    public static Block archaic_crystal_ore;
    //BUILDING

    public static Block spirit_stone;
    public static Block dark_spirit_stone;

    public static Block spirit_stone_brick;
    public static Block dark_spirit_stone_brick;
    public static Block smooth_spirit_stone;
    public static Block smooth_dark_spirit_stone;
    public static Block patterned_spirit_stone;
    public static Block patterned_dark_spirit_stone;
    public static Block spirit_stone_pillar;
    public static Block dark_spirit_stone_pillar;

    public static Block spirit_stone_slab;
    public static Block dark_spirit_stone_slab;

    public static Block spirit_stone_brick_slab;
    public static Block dark_spirit_stone_brick_slab;
    public static Block smooth_spirit_stone_slab;
    public static Block smooth_dark_spirit_stone_slab;
    public static Block patterned_spirit_stone_slab;
    public static Block patterned_dark_spirit_stone_slab;

    public static Block spirit_stone_stairs;
    public static Block dark_spirit_stone_stairs;

    public static Block spirit_stone_brick_stairs;
    public static Block dark_spirit_stone_brick_stairs;
    public static Block smooth_spirit_stone_stairs;
    public static Block smooth_dark_spirit_stone_stairs;
    public static Block patterned_spirit_stone_stairs;
    public static Block patterned_dark_spirit_stone_stairs;

    public static Block spirit_sapling;
    public static Block spirit_log;
    public static Block stripped_spirit_log;
    public static Block spirit_leaves;

    public static Block spirit_planks;
    public static Block spirit_stairs;
    public static Block spirit_slab;

    public static Block block_of_flesh;
    
    public static Block.Properties spirit_stone_properties = Block.Properties.from(Blocks.SMOOTH_STONE);
    public static Block.Properties dark_spirit_stone_properties = Block.Properties.from(Blocks.OBSIDIAN);
    public static Block.Properties mirror_properties = Block.Properties.from(Blocks.GLASS).notSolid();
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        spirit_jar = registerBlock(registry, new SpiritJarBlock(Block.Properties.from(Blocks.GLASS).notSolid().noDrops().setLightLevel(s -> 15)), "spirit_jar");
    
        spirit_furnace = registerBlock(registry, new SpiritFurnaceBlock(spirit_stone_properties), "spirit_furnace");
        basic_mirror = registerBlock(registry, new BasicMirrorBlock(mirror_properties, BasicMirrorBlock.mirrorTypeEnum.basic), "basic_mirror");
        input_mirror = registerBlock(registry, new BasicMirrorBlock(mirror_properties, BasicMirrorBlock.mirrorTypeEnum.input), "input_mirror");
        output_mirror = registerBlock(registry, new BasicMirrorBlock(mirror_properties, BasicMirrorBlock.mirrorTypeEnum.output), "output_mirror");
        funk_engine = registerBlock(registry, new FunkEngineBlock(spirit_stone_properties), "funk_engine");
        redstone_clock = registerBlock(registry, new RedstoneClockBlock(spirit_stone_properties), "redstone_clock");
        spirit_smeltery = registerBlock(registry, new SpiritSmelteryBlock(spirit_stone_properties), "spirit_smeltery");
    
        spirit_furnace_bounding_block = registerBlock(registry, new SpiritFurnaceBoundingBlock(spirit_stone_properties.notSolid().noDrops()), "spirit_furnace_bounding_block");
        spirit_smeltery_bounding_block = registerBlock(registry, new SpiritSmelteryBoundingBlock(spirit_stone_properties.notSolid().noDrops()), "spirit_smeltery_bounding_block");
    
        arcane_stone = registerBlock(registry, new Block(spirit_stone_properties), "arcane_stone");
        archaic_crystal_ore = registerBlock(registry, new Block(Block.Properties.from(Blocks.DIAMOND_ORE)), "archaic_crystal_ore");
    
        dark_spirit_stone = registerBlock(registry, new Block(dark_spirit_stone_properties), "dark_spirit_stone");
        spirit_stone = registerBlock(registry, new Block(spirit_stone_properties), "spirit_stone");
        dark_spirit_stone_brick = registerBlock(registry, new Block(dark_spirit_stone_properties), "dark_spirit_stone_brick");
        spirit_stone_brick = registerBlock(registry, new Block(spirit_stone_properties), "spirit_stone_brick");
        patterned_dark_spirit_stone = registerBlock(registry, new Block(dark_spirit_stone_properties), "patterned_dark_spirit_stone");
        patterned_spirit_stone = registerBlock(registry, new Block(spirit_stone_properties), "patterned_spirit_stone");
        smooth_dark_spirit_stone = registerBlock(registry, new Block(dark_spirit_stone_properties), "smooth_dark_spirit_stone");
        smooth_spirit_stone = registerBlock(registry, new Block(spirit_stone_properties), "smooth_spirit_stone");
    
        dark_spirit_stone_slab = registerBlock(registry, new SlabBlock(dark_spirit_stone_properties), "dark_spirit_stone_slab");
        spirit_stone_slab = registerBlock(registry, new SlabBlock(spirit_stone_properties), "spirit_stone_slab");
    
        dark_spirit_stone_brick_slab = registerBlock(registry, new SlabBlock(dark_spirit_stone_properties), "dark_spirit_stone_brick_slab");
        spirit_stone_brick_slab = registerBlock(registry, new SlabBlock(spirit_stone_properties), "spirit_stone_brick_slab");
        patterned_dark_spirit_stone_slab = registerBlock(registry, new SlabBlock(dark_spirit_stone_properties), "patterned_dark_spirit_stone_slab");
        patterned_spirit_stone_slab = registerBlock(registry, new SlabBlock(spirit_stone_properties), "patterned_spirit_stone_slab");
        smooth_dark_spirit_stone_slab = registerBlock(registry, new SlabBlock(dark_spirit_stone_properties), "smooth_dark_spirit_stone_slab");
        smooth_spirit_stone_slab = registerBlock(registry, new SlabBlock(spirit_stone_properties), "smooth_spirit_stone_slab");
    
        dark_spirit_stone_stairs = registerBlock(registry, new ModStairsBlock(dark_spirit_stone_properties), "dark_spirit_stone_stairs");
        spirit_stone_stairs = registerBlock(registry, new ModStairsBlock(spirit_stone_properties), "spirit_stone_stairs");
    
        dark_spirit_stone_brick_stairs = registerBlock(registry, new ModStairsBlock(dark_spirit_stone_properties), "dark_spirit_stone_brick_stairs");
        spirit_stone_brick_stairs = registerBlock(registry, new ModStairsBlock(spirit_stone_properties), "spirit_stone_brick_stairs");
        patterned_dark_spirit_stone_stairs = registerBlock(registry, new ModStairsBlock(dark_spirit_stone_properties), "patterned_dark_spirit_stone_stairs");
        patterned_spirit_stone_stairs = registerBlock(registry, new ModStairsBlock(spirit_stone_properties), "patterned_spirit_stone_stairs");
        smooth_dark_spirit_stone_stairs = registerBlock(registry, new ModStairsBlock(dark_spirit_stone_properties), "smooth_dark_spirit_stone_stairs");
        smooth_spirit_stone_stairs = registerBlock(registry, new ModStairsBlock(spirit_stone_properties), "smooth_spirit_stone_stairs");
    
        dark_spirit_stone_pillar = registerBlock(registry, new RotatedPillarBlock(dark_spirit_stone_properties), "dark_spirit_stone_pillar");
        spirit_stone_pillar = registerBlock(registry, new RotatedPillarBlock(spirit_stone_properties), "spirit_stone_pillar");
    
        spirit_planks = registerBlock(registry, new Block(Block.Properties.from(Blocks.OAK_PLANKS)), "spirit_planks");
        spirit_slab = registerBlock(registry, new ModSlabBlock(Block.Properties.from(Blocks.OAK_SLAB)), "spirit_slab");
        spirit_stairs = registerBlock(registry, new ModStairsBlock(Block.Properties.from(Blocks.OAK_STAIRS)), "spirit_stairs");
    
        spirit_sapling = registerBlock(registry, new SaplingBlock(new SpiritwoodTree(), Block.Properties.from(Blocks.JUNGLE_SAPLING)), "spirit_sapling");
        spirit_leaves = registerBlock(registry, new LeavesBlock(Block.Properties.from(Blocks.OAK_LEAVES)), "spirit_leaves");
        spirit_log = registerBlock(registry, new RotatedPillarBlock(Block.Properties.from(Blocks.OAK_LOG)), "spirit_log");
        stripped_spirit_log = registerBlock(registry, new RotatedPillarBlock(Block.Properties.from(Blocks.STRIPPED_OAK_LOG)), "stripped_spirit_log");
    
        block_of_flesh = registerBlock(registry, new FleshBlock(Block.Properties.from(Blocks.FIRE_CORAL_BLOCK).hardnessAndResistance(2)), "block_of_flesh");
    
    }

    private static <T extends Block> T registerBlock(IForgeRegistry<Block> registry, T newBlock, String name)
    {
        String prefixedName = MalumMod.MODID + ":" + name;
        newBlock.setRegistryName(prefixedName);
        registry.register(newBlock);
        return newBlock;
    }
}
