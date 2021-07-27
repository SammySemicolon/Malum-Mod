package com.sammy.malum.client.particles.wisp;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.core.mod_systems.particle.ParticleRendering;
import com.sammy.malum.client.RenderUtilities;
import com.sammy.malum.core.mod_systems.particle.GenericMalumParticle;
import com.sammy.malum.core.mod_systems.particle.data.MalumParticleData;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;

public class WispParticle extends GenericMalumParticle
{
    public WispParticle(ClientWorld world, MalumParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getBrightnessForRender(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public void renderParticle(IVertexBuilder b, ActiveRenderInfo info, float pticks) {
        super.renderParticle(ParticleRendering.getDelayedRender().getBuffer(RenderUtilities.GLOWING_PARTICLE), info, pticks);
    }
}
