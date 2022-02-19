package com.sammy.malum.common.block.mirror;

import com.sammy.malum.common.blockentity.mirror.EmitterMirrorBlockEntity;

public class EmitterMirrorBlock<T extends EmitterMirrorBlockEntity> extends WallMirrorBlock<T> {
    public EmitterMirrorBlock(Properties properties) {
        super(properties);
    }
}
