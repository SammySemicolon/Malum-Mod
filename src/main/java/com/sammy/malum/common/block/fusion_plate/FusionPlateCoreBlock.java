package com.sammy.malum.common.block.fusion_plate;

import com.sammy.malum.common.blockentity.FusionPlateBlockEntity;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import com.sammy.malum.core.systems.multiblock.IMultiBlockCore;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FusionPlateCoreBlock extends WaterLoggedBlock<FusionPlateBlockEntity> implements IMultiBlockCore {
    public static final VoxelShape SHAPE = makeShape();
    public FusionPlateCoreBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.FUSION_PLATE);
    }

    @Override
    public boolean isComplex() {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0, 0, 0, 1, 0.75, 1), BooleanOp.OR);

        return shape;
    }
}