package com.sammy.malum.common.blockentity.well;

import com.sammy.malum.common.blockentity.storage.ItemStandBlockEntity;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class CorruptWellBlockEntity extends ItemStandBlockEntity {
    public CorruptWellBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.CORRUPT_WELL.get(), pos, state);
    }

    @Override
    public Vec3 itemOffset() {
        Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
        Vec3 directionVector = new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ());
        return new Vec3(0.5f - directionVector.x() * 0.25f, 0.5f - directionVector.y() * 0.05f, 0.5f - directionVector.z() * 0.25f);
    }
}
