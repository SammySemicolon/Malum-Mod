package com.sammy.malum.common.block.curiosities.weeping_well;

import net.minecraft.world.level.material.PushReaction;
import team.lodestar.lodestone.systems.block.LodestoneDirectionalBlock;

public class WeepingWellPillarBlock extends LodestoneDirectionalBlock {
    public WeepingWellPillarBlock(Properties pProperties) {
        super(pProperties.pushReaction(PushReaction.IGNORE));
    }
}
