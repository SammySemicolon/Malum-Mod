package com.sammy.malum.common.block.curiosities.tablet;

import com.sammy.malum.common.block.storage.ItemStandBlockEntity;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class TwistedTabletBlockEntity extends ItemStandBlockEntity {
    public TwistedTabletBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.TWISTED_TABLET.get(), pos, state);
    }

    @Override
    public Vec3 itemOffset() {
        Direction direction = getBlockState().getValue(BlockStateProperties.FACING);
        Vec3 directionVector = new Vec3(direction.getStepX(), direction.getStepY(), direction.getStepZ());
        return new Vec3(0.5f + directionVector.x() * 0.25f, 0.5f + directionVector.y() * 0.4f, 0.5f + directionVector.z() * 0.25f);
    }
}
