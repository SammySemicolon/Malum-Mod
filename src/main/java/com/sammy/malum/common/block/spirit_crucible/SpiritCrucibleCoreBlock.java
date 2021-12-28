package com.sammy.malum.common.block.spirit_crucible;

import com.sammy.malum.common.blockentity.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SpiritCrucibleCoreBlock extends WaterLoggedBlock<SpiritCrucibleCoreBlockEntity> {
    public static final VoxelShape SHAPE = makeShape();
    public SpiritCrucibleCoreBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.SPIRIT_CRUCIBLE);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.125, 0.75, 0.125, 0.875, 1, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.75, 1), BooleanOp.OR);
        return shape;
    }
}
