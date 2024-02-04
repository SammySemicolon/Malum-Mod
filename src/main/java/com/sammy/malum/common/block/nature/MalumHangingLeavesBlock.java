package com.sammy.malum.common.block.nature;

import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.context.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.common.extensions.*;

import java.awt.*;

import static com.sammy.malum.MalumMod.*;

public class MalumHangingLeavesBlock extends MalumLeavesBlock {
    protected static final VoxelShape SHAPE = Block.box(3.0D, 3.0D, 3.0D, 13.0D, 16.0D, 13.0D);

    public MalumHangingLeavesBlock(Properties properties, Color maxColor, Color minColor) {
        super(properties, maxColor, minColor);
    }

    @Override
    protected boolean decaying(BlockState pState) {
        return false;
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pFacing.equals(Direction.UP)) {
            if (pFacingState.hasProperty(DISTANCE) && pFacingState.hasProperty(COLOR)) {
                final int distance = Math.min(pFacingState.getValue(DISTANCE)+1, 7);
                return super.updateShape(pState.setValue(DISTANCE, distance).setValue(COLOR, pFacingState.getValue(COLOR)), pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            }
        }
        return !pState.canSurvive(pLevel, pCurrentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }
    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        final Block block = pLevel.getBlockState(pPos.above()).getBlock();
        return block instanceof MalumLeavesBlock && !block.equals(pState.getBlock());
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}