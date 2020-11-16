package com.sammy.malum.common.world.features.tree;

import com.sammy.malum.common.blocks.MalumLeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;

import java.util.Random;

public class HugeSunKissedBlockStateProvider extends SimpleBlockStateProvider
{
    public HugeSunKissedBlockStateProvider(BlockState state)
    {
        super(state);
    }
    public BlockState getBlockState(Random randomIn, BlockPos blockPosIn) {
        return super.getBlockState(randomIn,blockPosIn).with(MalumLeavesBlock.COLOR, blockPosIn.getY() % 9);
    }
}
