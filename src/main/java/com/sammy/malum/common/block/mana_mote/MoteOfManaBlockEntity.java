package com.sammy.malum.common.block.mana_mote;

import com.sammy.malum.registry.common.block.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class MoteOfManaBlockEntity extends LodestoneBlockEntity {
    public MoteOfManaBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MOTE_OF_MANA.get(), pos, state);
    }
}
