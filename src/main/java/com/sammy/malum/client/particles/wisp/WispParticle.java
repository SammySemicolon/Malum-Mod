package com.sammy.malum.client.particles.wisp;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.core.systems.particles.ParticleRendering;
import com.sammy.malum.RenderUtil;
import com.sammy.malum.core.systems.particles.GenericMalumParticle;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
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
        super.renderParticle(ParticleRendering.getDelayedRender().getBuffer(RenderUtil.GLOWING_PARTICLE), info, pticks);
    }
}
