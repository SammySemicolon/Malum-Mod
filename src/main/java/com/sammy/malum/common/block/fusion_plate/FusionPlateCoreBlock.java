package com.sammy.malum.common.block.fusion_plate;

import com.sammy.malum.common.blockentity.FusionPlateBlockEntity;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.block.WaterLoggedBlock;
import com.sammy.malum.core.systems.multiblock.IMultiBlockCore;

public class FusionPlateCoreBlock extends WaterLoggedBlock<FusionPlateBlockEntity> implements IMultiBlockCore {
    public FusionPlateCoreBlock(Properties properties) {
        super(properties);
        setTile(BlockEntityRegistry.FUSION_PLATE);
    }
}