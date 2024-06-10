package com.sammy.malum.core.systems.spirit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;

import java.awt.*;

public class UmbralSpiritMotif extends SpiritVisualMotif {

    public UmbralSpiritMotif(Color primaryColor, Color secondaryColor, float mainColorCoefficient, Easing mainColorEasing) {
        super(4f, primaryColor, secondaryColor, mainColorCoefficient, mainColorEasing);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void applyWorldParticleChanges(WorldParticleBuilder builder) {
        builder.setRenderType(team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType.LUMITRANSPARENT);
        builder.getScaleData().multiplyCoefficient(1.5f);
        builder.getTransparencyData().multiplyValue(4f).multiplyCoefficient(1.5f);
        builder.multiplyLifetime(2.5f);
    }
}
