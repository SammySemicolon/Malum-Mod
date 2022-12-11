package com.sammy.malum.common.block.weeping_well;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class WeepingWellComponentBlock extends WeepingWellBlock {
    public static final BooleanProperty TALL = BooleanProperty.create("tall");
    public WeepingWellComponentBlock(Properties pProperties) {
        super(pProperties);
    }


    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(TALL, pContext.getPlayer() != null && pContext.getPlayer().isCrouching()).setValue(FACING, pContext.getHorizontalDirection());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
        pBuilder.add(TALL);
    }
}
