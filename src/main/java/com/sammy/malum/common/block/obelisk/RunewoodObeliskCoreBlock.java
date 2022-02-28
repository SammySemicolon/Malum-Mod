package com.sammy.malum.common.block.obelisk;

import com.sammy.malum.common.blockentity.obelisk.RunewoodObeliskBlockEntity;
import com.sammy.malum.core.setup.content.block.BlockEntityRegistry;

public class RunewoodObeliskCoreBlock extends ObeliskCoreBlock<RunewoodObeliskBlockEntity> {
    public RunewoodObeliskCoreBlock(Properties properties) {
        super(properties, BlockEntityRegistry.RUNEWOOD_OBELISK);
    }
}
