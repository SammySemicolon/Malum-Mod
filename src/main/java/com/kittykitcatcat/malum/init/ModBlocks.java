package com.kittykitcatcat.malum.init;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.blocks.machines.spiritfurnace.SpiritFurnaceBottomBlock;
import com.kittykitcatcat.malum.blocks.machines.spiritfurnace.SpiritFurnaceTopBlock;
import com.kittykitcatcat.malum.blocks.special.FleshBlock;
import com.kittykitcatcat.malum.blocks.special.SpiritLeafBlock;
import com.kittykitcatcat.malum.blocks.special.SpiritSaplingBlock;
import com.kittykitcatcat.malum.blocks.utility.ModSlabBlock;
import com.kittykitcatcat.malum.blocks.utility.ModStairsBlock;
import com.kittykitcatcat.malum.world.biomes.SpiritwoodTree;
import net.minecraft.block.*;
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
    public static Block spirit_jar;
    public static Block spirit_furnace;
    public static Block spirit_furnace_top;

    //BUILDING

    public static Block spirit_stone;
    public static Block dark_spirit_stone;

    public static Block spirit_sapling;
    public static Block spirit_log;
    public static Block stripped_spirit_log;
    public static Block spirit_leaves;

    public static Block spirit_planks;
    public static Block spirit_planks_stairs;
    public static Block spirit_planks_slab;

    public static Block block_of_flesh;
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        spirit_jar = registerBlock(registry, new Block(Block.Properties.from(Blocks.GLASS).notSolid().noDrops().lightValue(1)), "spirit_jar");

        spirit_furnace_top = registerBlock(registry, new SpiritFurnaceTopBlock(Block.Properties.from(Blocks.SMOOTH_STONE).notSolid().noDrops().lightValue(1)), "spirit_furnace_top");
        spirit_furnace = registerBlock(registry, new SpiritFurnaceBottomBlock(Block.Properties.from(Blocks.SMOOTH_STONE).notSolid().lightValue(1)), "spirit_furnace_bottom");

        dark_spirit_stone = registerBlock(registry, new Block(Block.Properties.from(Blocks.OBSIDIAN)), "dark_spirit_stone");
        spirit_stone = registerBlock(registry, new Block(Block.Properties.from(Blocks.STONE)), "spirit_stone");

        spirit_planks = registerBlock(registry, new Block(Block.Properties.from(Blocks.OAK_PLANKS)), "spirit_planks");
        spirit_planks_slab = registerBlock(registry, new ModSlabBlock(Block.Properties.from(Blocks.OAK_SLAB)), "spirit_planks_slab");
        spirit_planks_stairs = registerBlock(registry, new ModStairsBlock(Block.Properties.from(Blocks.OAK_STAIRS)), "spirit_planks_stairs");

        spirit_sapling = registerBlock(registry, new SpiritSaplingBlock(new SpiritwoodTree(), Block.Properties.from(Blocks.JUNGLE_SAPLING)), "spirit_sapling");
        spirit_leaves = registerBlock(registry, new SpiritLeafBlock(Block.Properties.from(Blocks.OAK_LEAVES)), "spirit_leaves");
        spirit_log = registerBlock(registry, new LogBlock(MaterialColor.BROWN, Block.Properties.from(Blocks.OAK_LOG)), "spirit_log");
        stripped_spirit_log = registerBlock(registry, new LogBlock(MaterialColor.BROWN, Block.Properties.from(Blocks.STRIPPED_OAK_LOG)), "stripped_spirit_log");

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
