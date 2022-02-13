package com.sammy.malum.common.block.obelisk;

import com.sammy.malum.common.blockentity.obelisk.BrilliantObeliskBlockEntity;
import com.sammy.malum.core.setup.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;

public class BrillianceObeliskCoreBlock extends ObeliskCoreBlock<BrilliantObeliskBlockEntity> {
    public BrillianceObeliskCoreBlock(Properties properties) {
        super(properties, BlockEntityRegistry.BRILLIANT_OBELISK);
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader world, BlockPos pos) {
        return 5;
    }
}
