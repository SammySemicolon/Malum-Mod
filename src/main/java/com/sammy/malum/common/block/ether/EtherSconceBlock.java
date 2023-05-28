package com.sammy.malum.common.block.ether;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EtherSconceBlock<T extends EtherBlockEntity> extends EtherTorchBlock<T> {
    protected static final VoxelShape SHAPE = box(6.0D, 0.0D, 6.0D, 10.0D, 11.0D, 10.0D);

    public EtherSconceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
