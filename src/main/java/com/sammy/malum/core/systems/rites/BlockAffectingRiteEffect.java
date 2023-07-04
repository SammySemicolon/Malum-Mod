package com.sammy.malum.core.systems.rites;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import net.minecraft.core.BlockPos;

public abstract class BlockAffectingRiteEffect extends MalumRiteEffect {

    public BlockAffectingRiteEffect() {
        super();
    }

    @Override
    public BlockPos getRiteEffectCenter(TotemBaseBlockEntity totemBase) {
        return totemBase.getBlockPos().relative(totemBase.direction, getRiteEffectRadius() + 1);
    }

    @Override
    public int getRiteEffectTickRate() {
        return BASE_TICK_RATE*5;
    }
}