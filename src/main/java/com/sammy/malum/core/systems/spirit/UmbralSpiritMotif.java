package com.sammy.malum.core.systems.spirit;

import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.awt.*;

public class UmbralSpiritMotif extends SpiritVisualMotif {

    public UmbralSpiritMotif(Color primaryColor, Color secondaryColor, float mainColorCoefficient, Easing mainColorEasing) {
        super(4f, primaryColor, secondaryColor, mainColorCoefficient, mainColorEasing);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void applyWorldParticleChanges(WorldParticleBuilder builder) {
        builder.setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT);
        builder.getScaleData().multiplyCoefficient(1.5f);
        builder.getTransparencyData().multiplyValue(4f).multiplyCoefficient(1.5f);
        builder.multiplyLifetime(2.5f);
    }
}
