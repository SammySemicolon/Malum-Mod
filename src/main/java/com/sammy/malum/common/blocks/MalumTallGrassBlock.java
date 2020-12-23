package com.sammy.malum.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.TallGrassBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;

import java.util.Random;

public class MalumTallGrassBlock extends TallGrassBlock
{
    private final RegistryObject<Block> blockRegistryObject;
    public MalumTallGrassBlock(Properties properties, RegistryObject<Block> blockRegistryObject)
    {
        super(properties);
        this.blockRegistryObject = blockRegistryObject;
    }
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state)
    {
        DoublePlantBlock doubleplantblock = (DoublePlantBlock) blockRegistryObject.get();
        if (doubleplantblock.getDefaultState().isValidPosition(worldIn, pos) && worldIn.isAirBlock(pos.up()))
        {
            doubleplantblock.placeAt(worldIn, pos, 2);
        }
    }
}