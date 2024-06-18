package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.common.block.mana_mote.*;
import com.sammy.malum.common.item.spirit.*;
import net.minecraft.network.chat.*;

import java.awt.*;
import java.util.*;
import java.util.function.*;

public class UmbralSpiritType extends MalumSpiritType {

    public static final int INVERT_COLOR = 0x4D616C6D; // M = chr 4D, a = chr 61, l = chr 6C, m = chr 6D

    public UmbralSpiritType(String identifier, Supplier<SpiritShardItem> spiritShard, Optional<SpiritMoteBlock> spiritMote, SpiritVisualMotif visualMotif, Color itemColor) {
        super(identifier, visualMotif, spiritShard, spiritMote, itemColor);
    }

    @Override
    public TextColor getTextColor(boolean isTooltip) {
        return TextColor.fromRgb(INVERT_COLOR);
    }
}
