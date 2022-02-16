package com.sammy.malum.common.blockentity.mirror;

import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class EmitterMirrorBlockEntity extends MirrorBlockEntity{
    public EmitterMirrorBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.EMITTER_MIRROR.get(), pos, state);
    }
}
