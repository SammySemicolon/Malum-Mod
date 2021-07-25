package com.sammy.malum.common.block;

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
    private final RegistryObject<Block> doublePlantBlock;
    public MalumTallGrassBlock(Properties properties, RegistryObject<Block> doublePlantBlock)
    {
        super(properties);
        this.doublePlantBlock = doublePlantBlock;
    }

    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state)
    {
        DoublePlantBlock doubleplantblock = (DoublePlantBlock) doublePlantBlock.get();
        if (doubleplantblock.getDefaultState().isValidPosition(worldIn, pos) && worldIn.isAirBlock(pos.up()))
        {
            doubleplantblock.placeAt(worldIn, pos, 2);
        }
    }
}