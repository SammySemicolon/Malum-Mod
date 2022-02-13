package com.sammy.malum.common.block.spirit_altar;

import com.sammy.malum.common.blockentity.SpiritAltarTileEntity;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpiritAltarBlock extends WaterLoggedBlock<SpiritAltarTileEntity>
{
    public static final VoxelShape SHAPE = makeShape();
    public static final VoxelShape RENDER_SHAPE = makeRenderShape();
    public SpiritAltarBlock(Properties properties)
    {
        super(properties);
        setTile(BlockEntityRegistry.SPIRIT_ALTAR);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState p_60547_, BlockGetter p_60548_, BlockPos p_60549_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState p_60572_, BlockGetter p_60573_, BlockPos p_60574_, CollisionContext p_60575_) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return RENDER_SHAPE;
    }
    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.8125, 0.625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1, 0, 0.1875, 1.0625, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 1, 0.8125, 0.1875, 1.0625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 1, 0, 1, 1.0625, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 1, 0.8125, 1, 1.0625, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.3125, 1, 0.375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0, 0.6875, 0.375, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.8125, 0.6875, 0.375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.3125, 0.1875, 0.375, 0.6875), BooleanOp.OR);

        return shape;
    }
    public static VoxelShape makeRenderShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.0625, 0.9375, 0.25, 0.9375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0.25, 0.1875, 0.8125, 0.625, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0.625, 0, 1, 1, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, 0.5625, -0.125, 0.1875, 1.0625, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(-0.125, 0.5625, 0.8125, 0.1875, 1.0625, 1.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.5625, -0.125, 1.125, 1.0625, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0.5625, 0.8125, 1.125, 1.0625, 1.125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.8125, 0, 0.3125, 1, 0.375, 0.6875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0, 0.6875, 0.375, 0.1875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.3125, 0, 0.8125, 0.6875, 0.375, 1), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0.3125, 0.1875, 0.375, 0.6875), BooleanOp.OR);

        return shape;
    }
}