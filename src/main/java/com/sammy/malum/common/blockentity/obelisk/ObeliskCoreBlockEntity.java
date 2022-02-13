package com.sammy.malum.common.blockentity.obelisk;

import com.sammy.malum.core.systems.multiblock.MultiBlockCoreEntity;
import com.sammy.malum.core.systems.multiblock.MultiBlockStructure;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ObeliskCoreBlockEntity extends MultiBlockCoreEntity {
    public ObeliskCoreBlockEntity(BlockEntityType<?> type, MultiBlockStructure structure, BlockPos pos, BlockState state) {
        super(type, structure, pos, state);
    }
}
