package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.common.block.mana_mote.SpiritMoteBlock;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.TextColor;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;

import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class UmbralSpiritType extends MalumSpiritType {

    public static final int INVERT_COLOR = 0x4D616C6D; // M = chr 4D, a = chr 61, l = chr 6C, m = chr 6D

    public UmbralSpiritType(String identifier, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote, SpiritVisualMotif visualMotif, Color itemColor) {
        super(identifier, visualMotif, spiritShard, spiritMote, itemColor);
    }

    @Override
    public TextColor getTextColor(boolean isTooltip) {
        return TextColor.fromRgb(INVERT_COLOR);
    }
}
