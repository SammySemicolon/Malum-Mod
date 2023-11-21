package com.sammy.malum.core.systems.rites;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import net.minecraft.core.BlockPos;

public abstract class BlockAffectingRiteEffect extends MalumRiteEffect {

    public BlockAffectingRiteEffect() {
        super(MalumRiteEffectCategory.DIRECTIONAL_BLOCK_EFFECT);
    }

    @Override
    public BlockPos getRiteEffectCenter(TotemBaseBlockEntity totemBase) {
        return totemBase.getBlockPos().relative(totemBase.direction, getRiteEffectHorizontalRadius() + 1);
    }

    @Override
    public int getRiteEffectVerticalRadius() {
        return 1;
    }
}