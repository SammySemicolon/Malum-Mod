package com.sammy.malum.common.block.storage.coffer;

import com.sammy.malum.registry.common.block.MalumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntity;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityType;
import team.lodestar.lodestone.modules.toolkit.inventory.LodestoneItemStackBlockHandler;

public class CofferBlockEntity extends LodestoneBlockEntity {
    private LodestoneItemStackBlockHandler handler;
    public boolean[] pins = {false, false, false, false, false};

    public CofferBlockEntity(LodestoneBlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public CofferBlockEntity(BlockPos pos, BlockState state) {
        super(MalumBlockEntities.COFFER.get(), pos, state);
    }

    public LodestoneItemStackBlockHandler getItemHandler() {
        return handler;
    }
}
