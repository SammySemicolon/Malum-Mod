package com.sammy.malum.common.block.ether;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class EtherWallSconceBlock<T extends EtherBlockEntity> extends EtherWallTorchBlock<EtherBlockEntity> {
    private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, box(6.0D, 2.0D, 10.0D, 10.0D, 13.0D, 16.0D), Direction.SOUTH, box(6.0D, 2.0D, 0.0D, 10.0D, 13.0D, 6.0D), Direction.WEST, box(10.0D, 2.0D, 6.0D, 16.0D, 13.0D, 10.0D), Direction.EAST, box(0.0D, 2.0D, 6.0D, 6.0D, 13.0D, 10.0D)));

    public EtherWallSconceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(HORIZONTAL_FACING));
    }
}