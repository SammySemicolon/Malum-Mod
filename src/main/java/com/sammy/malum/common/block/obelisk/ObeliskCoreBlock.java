package com.sammy.malum.common.block.obelisk;

import com.sammy.malum.common.blockentity.obelisk.ObeliskCoreBlockEntity;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import com.sammy.malum.core.systems.multiblock.IMultiBlockCore;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class ObeliskCoreBlock<T extends ObeliskCoreBlockEntity> extends WaterLoggedBlock<T> implements IMultiBlockCore {
    public static final VoxelShape SHAPE = makeShape();

    public ObeliskCoreBlock(Properties properties, Supplier<BlockEntityType<T>> type) {
        super(properties);
        setTile(type);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.1875, 0.1875, 0.8125, 1, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0, 1, 0.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.625, 0.375, 0.3125, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 0.375, 0.3125, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.1875, 0.9375), BooleanOp.OR);

        return shape;
    }
}