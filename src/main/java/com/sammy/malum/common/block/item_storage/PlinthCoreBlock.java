package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.common.blockentity.item_storage.PlinthCoreBlockEntity;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import com.sammy.malum.core.systems.multiblock.IMultiBlockCore;

public class PlinthCoreBlock extends WaterLoggedBlock<PlinthCoreBlockEntity> implements IMultiBlockCore {
    public PlinthCoreBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.PLINTH);
    }
}