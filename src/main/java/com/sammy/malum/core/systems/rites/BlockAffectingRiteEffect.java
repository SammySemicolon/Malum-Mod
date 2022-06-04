package com.sammy.malum.core.systems.rites;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.setup.content.block.BlockTagRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

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