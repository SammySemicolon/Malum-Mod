package com.sammy.malum.common.blockentity.obelisk;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.multiblock.MultiBlockCoreEntity;
import team.lodestar.lodestone.systems.multiblock.MultiBlockStructure;

public abstract class ObeliskCoreBlockEntity extends MultiBlockCoreEntity {
    public ObeliskCoreBlockEntity(BlockEntityType<? extends ObeliskCoreBlockEntity> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
    }
}
