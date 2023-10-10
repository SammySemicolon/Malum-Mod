package com.sammy.malum.core.systems.spirit;

import com.sammy.malum.common.block.mana_mote.*;
import com.sammy.malum.common.item.spirit.*;
import team.lodestar.lodestone.systems.easing.*;

import java.awt.*;
import java.util.function.*;

public class SpiritTypeBuilder {

    public final String identifier;
    public final Supplier<SpiritShardItem> spiritShard;
    public final Supplier<SpiritMoteBlock> spiritMote;

    public Color primaryColor;
    public Color secondaryColor;
    public float mainColorCoefficient;
    public Easing mainColorEasing = Easing.LINEAR;

    public Color primaryBloomColor;
    public Color secondaryBloomColor;
    public float bloomColorCoefficient;
    public Easing bloomColorEasing = Easing.LINEAR;

    public SpiritTypeBuilder(String identifier, Supplier<SpiritShardItem> spiritShard, Supplier<SpiritMoteBlock> spiritMote) {
        this.identifier = identifier;
        this.spiritShard = spiritShard;
        this.spiritMote = spiritMote;
    }
    public SpiritTypeBuilder setColorData(Color primaryColor, Color secondaryColor, float colorCoefficient) {
        return setMainColorData(primaryColor, secondaryColor, colorCoefficient).setBloomColorData(primaryColor, secondaryColor, colorCoefficient);
    }
    public SpiritTypeBuilder setColorData(Color primaryColor, Color secondaryColor, float colorCoefficient, Easing easing) {
        return setMainColorData(primaryColor, secondaryColor, colorCoefficient, easing).setBloomColorData(primaryColor, secondaryColor, colorCoefficient, easing);
    }

    public SpiritTypeBuilder setMainColorData(Color primaryColor, Color secondaryColor, float mainColorCoefficient, Easing mainColorEasing) {
        this.mainColorEasing = mainColorEasing;
        return setMainColorData(primaryColor, secondaryColor, mainColorCoefficient);
    }

    public SpiritTypeBuilder setMainColorData(Color primaryColor, Color secondaryColor, float mainColorCoefficient) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.mainColorCoefficient = mainColorCoefficient;
        return this;
    }

    public SpiritTypeBuilder setBloomColorData(Color primaryBloomColor, Color secondaryBloomColor, float bloomColorCoefficient, Easing bloomColorEasing) {
        this.bloomColorEasing = bloomColorEasing;
        return setBloomColorData(primaryBloomColor, secondaryBloomColor, bloomColorCoefficient);
    }

    public SpiritTypeBuilder setBloomColorData(Color primaryBloomColor, Color secondaryBloomColor, float bloomColorCoefficient) {
        this.primaryBloomColor = primaryBloomColor;
        this.secondaryBloomColor = secondaryBloomColor;
        this.bloomColorCoefficient = bloomColorCoefficient;
        return this;
    }

    public MalumSpiritType build() {
        return new MalumSpiritType(identifier, spiritShard, spiritMote,
                primaryColor, secondaryColor, mainColorCoefficient, mainColorEasing,
                primaryBloomColor, secondaryBloomColor, bloomColorCoefficient, bloomColorEasing);
    }
}
