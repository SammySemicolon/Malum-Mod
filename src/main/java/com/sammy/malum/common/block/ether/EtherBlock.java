package com.sammy.malum.common.block.ether;

import com.sammy.malum.common.blockentity.EtherBlockEntity;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EtherBlock<T extends EtherBlockEntity> extends WaterLoggedBlock<T> {
    public static final VoxelShape SHAPE = Block.box(6, 6, 6, 10, 10, 10);

    public EtherBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}