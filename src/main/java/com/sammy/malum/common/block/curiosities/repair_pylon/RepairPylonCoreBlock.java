package com.sammy.malum.common.block.curiosities.repair_pylon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.lodestone.systems.block.WaterLoggedEntityBlock;

public class RepairPylonCoreBlock<T extends RepairPylonCoreBlockEntity> extends WaterLoggedEntityBlock<T> {

    public static final VoxelShape SHAPE = makeShape();

    public RepairPylonCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState pState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState pState, Level pLevel, BlockPos pPos) {
        if (pLevel.getBlockEntity(pPos) instanceof RepairPylonCoreBlockEntity pylon) {
            return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(pLevel.getBlockEntity(pPos));
        }
        return 0;
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0.625, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 1.125, 0.625, 1.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0, -0.125, 1.125, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, 0, 0.625, 0.375, 0.625, 1.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, 0, -0.125, 0.375, 0.625, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0.625, 0.375, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 0.375, 1, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0.5, 0.0625, 0.9375, 1, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.5, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.625, 0.625, 0, 1, 1, 0.375), BooleanOp.OR);
        return shape;
    }
}
