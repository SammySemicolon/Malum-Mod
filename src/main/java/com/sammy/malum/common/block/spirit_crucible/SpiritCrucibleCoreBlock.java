package com.sammy.malum.common.block.spirit_crucible;

import com.sammy.malum.common.blockentity.spirit_crucible.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.SimpleBlock;

public class SpiritCrucibleCoreBlock extends SimpleBlock<SpiritCrucibleCoreBlockEntity> {
    public SpiritCrucibleCoreBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.SPIRIT_CRUCIBLE);
    }
}
