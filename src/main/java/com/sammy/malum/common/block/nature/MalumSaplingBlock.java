package com.sammy.malum.common.block.nature;

import com.sammy.malum.registry.common.block.BlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.RegistryObject;

import java.util.Random;
import java.util.function.Supplier;

public class MalumSaplingBlock extends SaplingBlock {
    public final Supplier<? extends Feature<NoneFeatureConfiguration>> tree;

    public MalumSaplingBlock(Properties properties, RegistryObject<? extends Feature<NoneFeatureConfiguration>> tree) {
        super(null, properties);
        this.tree = tree;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pLevel.getBlockState(pPos.below()).is(BlockTagRegistry.BLIGHTED_BLOCKS)) {
            return true;
        }
        return super.canSurvive(pState, pLevel, pPos);
    }

    @Override
    public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, Random rand) {
        if (state.getValue(STAGE) == 0) {
            level.setBlock(pos, state.cycle(STAGE), 4);
        } else {
            if (!net.minecraftforge.event.ForgeEventFactory.saplingGrowTree(level, rand, pos)) {
                return;
            }
            tree.get().place(NoneFeatureConfiguration.INSTANCE, level, level.getChunkSource().getGenerator(), rand, pos);
        }
    }
}