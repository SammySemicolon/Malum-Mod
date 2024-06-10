package com.sammy.malum.core.systems.spirit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleDataBuilder;

import java.awt.*;

public class SpiritVisualMotif {

    protected final float alphaMultiplier;
    protected final Color primaryColor;
    protected final Color secondaryColor;
    protected final float colorCoefficient;
    protected final Easing colorEasing;

    public SpiritVisualMotif(Color primaryColor, Color secondaryColor, float colorCoefficient, Easing colorEasing) {
        this(1f, primaryColor, secondaryColor, colorCoefficient, colorEasing);
    }

    public SpiritVisualMotif(float alphaMultiplier, Color primaryColor, Color secondaryColor, float colorCoefficient, Easing colorEasing) {
        this.alphaMultiplier = alphaMultiplier;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.colorCoefficient = colorCoefficient;
        this.colorEasing = colorEasing;
    }

    public ColorParticleDataBuilder createColorData() {
        return createColorData(1f);
    }

    public ColorParticleDataBuilder createColorData(float coefficientMultiplier) {
        return ColorParticleData.create(primaryColor, secondaryColor).setCoefficient(colorCoefficient * coefficientMultiplier).setEasing(colorEasing);
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public float getAlphaMultiplier() {
        return alphaMultiplier;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public float getColorCoefficient() {
        return colorCoefficient;
    }

    public Easing getColorEasing() {
        return colorEasing;
    }

    @Environment(EnvType.CLIENT)
    public void applyWorldParticleChanges(WorldParticleBuilder builder) {

    }

}