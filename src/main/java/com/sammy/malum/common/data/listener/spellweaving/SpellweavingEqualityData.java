package com.sammy.malum.common.data.listener.spellweaving;

import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record SpellweavingEqualityData(List<Holder<Block>> equalBlocks) {

}
