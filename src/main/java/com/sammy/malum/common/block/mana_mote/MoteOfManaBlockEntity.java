package com.sammy.malum.common.block.mana_mote;

import com.sammy.malum.registry.common.block.*;
import net.minecraft.core.*;
import net.minecraft.world.level.block.state.*;
import team.lodestar.lodestone.systems.blockentity.*;

public class MoteOfManaBlockEntity extends LodestoneBlockEntity {
    public MoteOfManaBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.MOTE_OF_MANA.get(), pos, state);
    }
}
