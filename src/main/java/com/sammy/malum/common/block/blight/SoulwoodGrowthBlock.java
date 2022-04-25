package com.sammy.malum.common.block.blight;

import com.sammy.malum.common.block.MalumSaplingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.core.setup.content.block.BlockTagRegistry.BLIGHTED_BLOCKS;

public class SoulwoodGrowthBlock extends MalumSaplingBlock {
    public SoulwoodGrowthBlock(Properties properties, RegistryObject<? extends Feature<NoneFeatureConfiguration>> tree) {
        super(properties, tree);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        if (pState.is(BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.mayPlaceOn(pState, pLevel, pPos);
    }
}