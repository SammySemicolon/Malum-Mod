package com.sammy.malum.common.block.storage;

import com.sammy.malum.common.blockentity.storage.SpiritJarBlockEntity;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpiritJarBlock extends WaterLoggedBlock<SpiritJarBlockEntity> {
    public static final VoxelShape SHAPE = makeShape();

    public SpiritJarBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.SPIRIT_JAR);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.15625, 0.03125, 0.15625, 0.84375, 0.84375, 0.84375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.21875, 0.90625, 0.21875, 0.78125, 1.03125, 0.78125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.28125, 0.84375, 0.28125, 0.71875, 0.90625, 0.71875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.34375, -0.03125, 0.34375, 0.65625, 0.09375, 0.65625), BooleanOp.OR);

        return shape;
    }
}