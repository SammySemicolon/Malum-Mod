package com.sammy.malum.common.blockentity;

import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class WeaversWorkbenchBlockEntity extends LodestoneBlockEntity {

    public WeaversWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.WEAVERS_WORKBENCH.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
    }

    @Override
    public void tick() {
        super.tick();
    }
}