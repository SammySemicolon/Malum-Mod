package com.sammy.malum.core.systems.spirit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.particle.builder.AbstractWorldParticleBuilder;

@Environment(EnvType.CLIENT)
public class UmbralClientHelper {
    public static <K extends AbstractWorldParticleBuilder<K, ?>> void grabRenderType(K b) {
        b.setRenderType(team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType.LUMITRANSPARENT);


    }
}
