package com.sammy.malum.common.blockentity.item_storage;

import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SoulVialBlockEntity extends SimpleBlockEntity {
    public SoulVialBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.SOUL_VIAL.get(), pos, state);
    }
}