package com.sammy.malum.client.particles.spiritflame;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.core.systems.rendering.RenderUtilities;
import com.sammy.malum.core.systems.particle.ParticlePhaseMalumParticle;
import com.sammy.malum.core.systems.particle.ParticleRendering;
import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import com.sammy.malum.core.systems.particle.phases.ParticlePhase;
import com.sammy.malum.core.systems.particle.rendertypes.AdditiveRenderType;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.Level.ClientLevel;

public class SpiritFlameParticle extends ParticlePhaseMalumParticle {
    protected SpiritFlameParticle(ClientLevel Level, MalumParticleData data, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet) {
        super(Level, data, xSpeed, ySpeed, zSpeed, x, y, z, spriteSet, new ParticlePhase(0, 37, 1, 0), new ParticlePhase(38, 77, 2, 38));
        setLifetime(118);
        hasPhysics = false;
    }

    @Override
    public void tick() {
        super.tick();
        xd *= 0.9f;
        if (data.gravity && age < 5) {
            yd += 0.005f;
        } else {
            yd *= 0.9f;
        }
        zd *= 0.9f;
    }

    @Override
    public void render(IVertexBuilder b, ActiveRenderInfo info, float pticks) {
        super.render(ClientConfig.BETTER_LAYERING.get() ? ParticleRendering.getDelayedRender().getBuffer(RenderUtilities.GLOWING_PARTICLE) : b, info, pticks);
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return AdditiveRenderType.INSTANCE;
    }
}