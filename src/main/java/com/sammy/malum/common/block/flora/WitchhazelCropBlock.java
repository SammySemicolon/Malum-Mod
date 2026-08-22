package com.sammy.malum.common.block.flora;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("NullableProblems")
public class WitchhazelCropBlock extends CropBlock {

    public static final IntegerProperty AGE_6 = IntegerProperty.create("age", 0, 6);

    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(5.0, 0.0, 5.0, 11.0, 3.0, 11.0),
            Block.box(5.0, 0.0, 5.0, 11.0, 4.0, 11.0),
            Block.box(4.0, 0.0, 4.0, 12.0, 5.0, 12.0),
            Block.box(3.0, 0.0, 3.0, 13.0, 7.0, 13.0),
            Block.box(3.0, 0.0, 3.0, 13.0, 8.0, 13.0),
            Block.box(2.0, 0.0, 2.0, 14.0, 10.0, 14.0),
            Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0)
    };

    public WitchhazelCropBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE_6);
    }

    protected @NotNull IntegerProperty getAgeProperty() {
        return AGE_6;
    }

    @Override
    public int getMaxAge() {
        return 6;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }
}
