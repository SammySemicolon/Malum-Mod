package com.sammy.malum.common.block.nature;

import com.sammy.malum.registry.common.block.BlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;

public class MalumSaplingBlock extends SaplingBlock {

    public MalumSaplingBlock(AbstractTreeGrower treeGrower, Properties properties) {
        super(treeGrower, properties);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos.below()).is(BlockTagRegistry.BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.canSurvive(pState, pLevel, pPos);
    }
}