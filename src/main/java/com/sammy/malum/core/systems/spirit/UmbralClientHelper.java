package com.sammy.malum.core.systems.spirit;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;

@Environment(EnvType.CLIENT)
public class UmbralClientHelper {

    public static <K extends WorldParticleBuilder> void grabRenderType(K b) {
        b.setRenderType(team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType.LUMITRANSPARENT);
    }
}
