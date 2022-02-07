package com.sammy.malum.core.systems.multiblock;

import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.SimpleBlock;

public class ComponentBlock extends SimpleBlock<MultiBlockComponentEntity> {
    public ComponentBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.MULTIBLOCK_COMPONENT);
    }
}
