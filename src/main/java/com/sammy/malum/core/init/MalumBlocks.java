package com.sammy.malum.core.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.sammy.malum.MalumMod.MODID;


@SuppressWarnings("unused")
public class MalumBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    
    public static AbstractBlock.Properties SPIRIT_STONE_PROPERTIES = AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).setRequiresTool().hardnessAndResistance(1.25F, 9.0F);
    
    public static final RegistryObject<Block> SPIRIT_STONE = BLOCKS.register("spirit_stone",
            () -> new Block(SPIRIT_STONE_PROPERTIES)
    );
    public static final RegistryObject<Block> SPIRIT_STONE_SLAB = BLOCKS.register("spirit_stone_slab",
            () -> new SlabBlock(SPIRIT_STONE_PROPERTIES)
    );
    public static final RegistryObject<Block> SPIRIT_STONE_STAIRS = BLOCKS.register("spirit_stone_stairs",
            () -> new StairsBlock(SPIRIT_STONE.get().getDefaultState(), SPIRIT_STONE_PROPERTIES)
    );
}
