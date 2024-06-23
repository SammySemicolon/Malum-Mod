package com.sammy.malum.visual_effects.networked;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleRenderType;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class ClientRenderTypeHelper {

    @Environment(EnvType.CLIENT)
    public static ParticleRenderType getLumi() {
        return LodestoneWorldParticleRenderType.LUMITRANSPARENT;
    }

}
