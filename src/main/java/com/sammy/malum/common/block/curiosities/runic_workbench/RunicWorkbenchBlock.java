package com.sammy.malum.common.block.curiosities.runic_workbench;

import net.minecraft.core.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.systems.block.*;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class RunicWorkbenchBlock<T extends RunicWorkbenchBlockEntity> extends LodestoneEntityBlock<T> {

    public RunicWorkbenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HORIZONTAL_FACING, Direction.NORTH));
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(HORIZONTAL_FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING);
        super.createBlockStateDefinition(builder);
    }
}
