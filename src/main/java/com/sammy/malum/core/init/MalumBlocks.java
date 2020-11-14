package com.sammy.malum.core.init;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;

import static com.sammy.malum.MalumMod.BLOCKS;

public class MalumBlocks
{
    public static final RegistryObject<Block> SEWING_STATION_BLOCK = BLOCKS.register("sewing_station",
            () -> new Block(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(2.5F))
    );
}
