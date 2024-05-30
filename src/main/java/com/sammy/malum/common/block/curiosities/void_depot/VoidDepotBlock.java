package com.sammy.malum.common.block.curiosities.void_depot;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

public class VoidDepotBlock<T extends VoidDepotBlockEntity> extends LodestoneEntityBlock<T> {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public VoidDepotBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(POWERED);
    }

    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(POWERED)) {
            pLevel.setBlock(pPos, pState.setValue(POWERED, false), 2);
        } else {
            pLevel.setBlock(pPos, pState.setValue(POWERED, true), 2);
            pLevel.scheduleTick(pPos, this, 2);
            if (pLevel.getBlockEntity(pPos) instanceof VoidDepotBlockEntity voidDepotBlockEntity) {
                voidDepotBlockEntity.onCompletion();
            }
        }
        pLevel.updateNeighborsAt(pPos, pState.getBlock());
    }

    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide) {
        return pBlockState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    public boolean isSignalSource(BlockState pState) {
        return true;
    }
}