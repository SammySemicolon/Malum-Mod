package com.sammy.malum.common.block.curiosities.obelisk.runewood;

import com.sammy.malum.common.block.curiosities.obelisk.ObeliskCoreBlock;
import com.sammy.malum.registry.common.block.BlockEntityRegistry;

public class RunewoodObeliskCoreBlock extends ObeliskCoreBlock<RunewoodObeliskBlockEntity> {
    public RunewoodObeliskCoreBlock(Properties properties) {
        super(properties, BlockEntityRegistry.RUNEWOOD_OBELISK);
    }
}
