package com.sammy.malum.common.block.curiosities.runic_workbench;

import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.systems.blockentity.*;

public class RunicWorkbenchBlockEntity extends LodestoneBlockEntity {

    public RunicWorkbenchBlockEntity(BlockEntityType<? extends RunicWorkbenchBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RunicWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.RUNIC_WORKBENCH.get(), pos, state);
    }
}