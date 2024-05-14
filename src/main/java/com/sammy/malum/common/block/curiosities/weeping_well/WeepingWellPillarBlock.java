package com.sammy.malum.common.block.curiosities.weeping_well;

import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.systems.block.*;

public class WeepingWellPillarBlock extends LodestoneDirectionalBlock {
    public WeepingWellPillarBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState pState) {
        return PushReaction.IGNORE;
    }
}
