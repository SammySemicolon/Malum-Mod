package com.sammy.malum.common.block.spirit_altar;

import com.sammy.malum.common.blockentity.ObeliskCoreBlockEntity;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import com.sammy.malum.core.systems.multiblock.IMultiBlockCore;

public class ObeliskCoreBlock extends WaterLoggedBlock<ObeliskCoreBlockEntity> implements IMultiBlockCore {
    public ObeliskCoreBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.OBELISK);
    }
}