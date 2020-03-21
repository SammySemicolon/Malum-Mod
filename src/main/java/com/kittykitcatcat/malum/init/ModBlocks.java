package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.blocks.*;
import com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceBottomBlock;
import com.kittykitcatcat.malum.blocks.spiritfurnace.SpiritFurnaceTopBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.block.RedstoneLampBlock;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks
{

    //FUNCTIONAL BLOCKS
    public static Block spirit_furnace_bottom;
    public static Block spirit_furnace_top;
    //BUILDING
    public static Block spirit_sapling;
    public static Block spirit_log;
    public static Block spirit_leaves;

    public static Block evil_pumpkin;
    public static Block lit_evil_pumpkin;

    public static Block refined_glowstone_block;
    public static Block refined_glowstone_lamp;

    public static Block smooth_stone_stairs;

    public static Block refined_bricks;
    public static Block refined_bricks_stairs;
    public static Block refined_bricks_slab;

    public static Block refined_pathway;
    public static Block refined_pathway_stairs;
    public static Block refined_pathway_slab;

    public static Block refined_smooth_stone;
    public static Block refined_smooth_stone_stairs;
    public static Block refined_smooth_stone_slab;

    public static Block spirit_planks;
    public static Block spirit_planks_stairs;
    public static Block spirit_planks_slab;

    public static Block wooden_planks;
    public static Block wooden_planks_stairs;
    public static Block wooden_planks_slab;

    public static Block wooden_beam;
    public static Block wooden_casing;

    public static Block block_of_flesh;
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        spirit_furnace_top = registerBlock(registry, new SpiritFurnaceTopBlock(Block.Properties.from(Blocks.SMOOTH_STONE)), "spirit_furnace_top");
        spirit_furnace_bottom = registerBlock(registry, new SpiritFurnaceBottomBlock(Block.Properties.from(Blocks.SMOOTH_STONE)), "spirit_furnace_bottom");

        smooth_stone_stairs = registerBlock(registry, new ModStairsBlock(Block.Properties.from(Blocks.SMOOTH_STONE)), "smooth_stone_stairs");

        evil_pumpkin = registerBlock(registry, new ModHorizontalBlock(Block.Properties.from(Blocks.CARVED_PUMPKIN)), "evil_pumpkin");
        lit_evil_pumpkin = registerBlock(registry, new ModHorizontalBlock(Block.Properties.from(Blocks.JACK_O_LANTERN)), "lit_evil_pumpkin");

        refined_glowstone_block = registerBlock(registry, new Block(Block.Properties.from(Blocks.GLOWSTONE)), "refined_glowstone_block");
        refined_glowstone_lamp = registerBlock(registry, new RedstoneLampBlock(Block.Properties.from(Blocks.REDSTONE_LAMP)), "refined_glowstone_lamp");

        refined_smooth_stone = registerBlock(registry, new Block(Block.Properties.from(Blocks.SMOOTH_STONE)), "refined_smooth_stone");
        refined_smooth_stone_slab = registerBlock(registry, new ModSlabBlock(Block.Properties.from(Blocks.SMOOTH_STONE)), "refined_smooth_stone_slab");
        refined_smooth_stone_stairs = registerBlock(registry, new ModStairsBlock(Block.Properties.from(Blocks.SMOOTH_STONE)), "refined_smooth_stone_stairs");

        spirit_planks = registerBlock(registry, new Block(Block.Properties.from(Blocks.OAK_PLANKS)), "spirit_planks");
        spirit_planks_slab = registerBlock(registry, new ModSlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)), "spirit_planks_slab");
        spirit_planks_stairs = registerBlock(registry, new ModStairsBlock(Block.Properties.from(Blocks.OAK_PLANKS)), "spirit_planks_stairs");

        refined_pathway = registerBlock(registry, new Block(Block.Properties.from(Blocks.STONE)), "refined_pathway");
        refined_pathway_slab = registerBlock(registry, new ModSlabBlock(Block.Properties.from(Blocks.STONE)), "refined_pathway_slab");
        refined_pathway_stairs = registerBlock(registry, new ModStairsBlock(Block.Properties.from(Blocks.STONE)), "refined_pathway_stairs");

        wooden_planks = registerBlock(registry, new Block(Block.Properties.from(Blocks.OAK_PLANKS)), "wooden_planks");
        wooden_planks_slab = registerBlock(registry, new ModSlabBlock(Block.Properties.from(Blocks.OAK_PLANKS)), "wooden_planks_slab");
        wooden_planks_stairs = registerBlock(registry, new ModStairsBlock(Block.Properties.from(Blocks.OAK_PLANKS)), "wooden_planks_stairs");

        refined_bricks = registerBlock(registry, new Block(Block.Properties.from(Blocks.STONE_BRICKS)), "refined_bricks");
        refined_bricks_slab = registerBlock(registry, new ModSlabBlock(Block.Properties.from(Blocks.STONE_BRICKS)), "refined_bricks_slab");
        refined_bricks_stairs = registerBlock(registry, new ModStairsBlock(Block.Properties.from(Blocks.STONE_BRICKS)), "refined_bricks_stairs");

        wooden_beam = registerBlock(registry, new LogBlock(MaterialColor.BROWN, Block.Properties.from(Blocks.OAK_LOG)), "wooden_beam");
        wooden_casing = registerBlock(registry, new LogBlock(MaterialColor.BROWN, Block.Properties.from(Blocks.OAK_LOG)), "wooden_casing");

        spirit_sapling = registerBlock(registry, new SpiritSaplingBlock(Block.Properties.from(Blocks.JUNGLE_SAPLING)), "spirit_sapling");
        spirit_leaves = registerBlock(registry, new SpiritLeafBlock(Block.Properties.from(Blocks.OAK_LEAVES)), "spirit_leaves");
        spirit_log = registerBlock(registry, new LogBlock(MaterialColor.BROWN, Block.Properties.from(Blocks.OAK_LOG)), "spirit_log");

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
