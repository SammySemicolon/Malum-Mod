package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.common.block.mana_mote.*;
import com.sammy.malum.common.item.spirit.*;
import net.minecraft.network.chat.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.particle.builder.*;

import java.awt.*;
import java.util.function.*;

public class UmbralSpiritType extends MalumSpiritType {

    public static final int INVERT_COLOR = 0x4D616C6D; // M = chr 4D, a = chr 61, l = chr 6C, m = chr 6D

    public UmbralSpiritType(String identifier, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote, SpiritVisualMotif visualMotif, Color itemColor) {
        super(identifier, visualMotif, spiritShard, spiritMote, itemColor);
    }

    @Override
    public TextColor getTextColor(boolean isTooltip) {
        return TextColor.fromRgb(INVERT_COLOR);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public <K extends AbstractWorldParticleBuilder<K, ?>> Consumer<K> applyWorldParticleChanges() {

        return b -> {
            //TODO server complainin' b.setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT);
            b.modifyData(b::getTransparencyData, d -> d.multiplyValue(4f));
            b.getScaleData().multiplyCoefficient(1.5f);
            b.getTransparencyData().multiplyCoefficient(1.5f);
            b.multiplyLifetime(2.5f);
        };
    }
}
