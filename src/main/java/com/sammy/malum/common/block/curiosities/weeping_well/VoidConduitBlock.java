package com.sammy.malum.common.block.curiosities.weeping_well;

import com.sammy.malum.core.handlers.TouchOfDarknessHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.lodestone.systems.block.LodestoneEntityBlock;

import static com.sammy.malum.common.block.curiosities.weeping_well.PrimordialSoupBlock.TOP_SHAPE;

public class VoidConduitBlock<T extends VoidConduitBlockEntity> extends LodestoneEntityBlock<T> {
    public VoidConduitBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TOP_SHAPE;
    }

    public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pDirection) {
        return pAdjacentBlockState.getBlock() instanceof PrimordialSoupBlock || super.skipRendering(pState, pAdjacentBlockState, pDirection);
    }

    public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return Shapes.empty();
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity livingEntity) {
            TouchOfDarknessHandler.touchedByGoop(pState, livingEntity);
        }
    }
}