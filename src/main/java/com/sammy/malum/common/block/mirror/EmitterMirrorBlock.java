package com.sammy.malum.common.block.mirror;

import com.sammy.malum.common.blockentity.mirror.EmitterMirrorBlockEntity;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;

public class EmitterMirrorBlock extends WallMirrorBlock<EmitterMirrorBlockEntity> {
    public EmitterMirrorBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.EMITTER_MIRROR);
    }
}
