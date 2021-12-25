package com.sammy.malum.core.systems.multiblock;

import com.sammy.malum.core.helper.BlockHelper;
import com.sammy.malum.core.registry.block.BlockEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class MultiBlockComponentEntity extends SimpleBlockEntity {

    public BlockPos corePos;
    public MultiBlockComponentEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    public MultiBlockComponentEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MULTIBLOCK_COMPONENT.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        BlockHelper.saveBlockPos(tag, corePos);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        corePos = BlockHelper.loadBlockPos(tag);
        super.load(tag);
    }

    @Override
    public void onBreak() {
        if (level.getBlockEntity(corePos) instanceof MultiBlockCoreEntity core)
        {
            core.onBreak();
        }
        super.onBreak();
    }
}
