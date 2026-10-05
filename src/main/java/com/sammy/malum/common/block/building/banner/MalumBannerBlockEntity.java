package com.sammy.malum.common.block.building.banner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityType;

public class MalumBannerBlockEntity extends LodestoneBlockEntity {

    public MalumBannerBlockEntity(LodestoneBlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}