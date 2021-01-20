package com.sammy.malum.client.particles.itemcircle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.RenderUtil;
import com.sammy.malum.core.systems.particles.ParticlePhaseMalumParticle;
import com.sammy.malum.core.systems.particles.ParticleRendering;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
import com.sammy.malum.core.systems.particles.phases.ParticlePhase;
import com.sammy.malum.core.systems.particles.rendertypes.SpriteParticleRenderType;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemCircleParticle extends ParticlePhaseMalumParticle
{
    protected ItemCircleParticle(ClientWorld world, MalumParticleData data, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet)
    {
        super(world, data, xSpeed, ySpeed, zSpeed, x, y, z, spriteSet,
                new ParticlePhase(0, 15, 1, 0));
        setMaxAge(15);
        particleAngle = world.rand.nextInt(360);
        prevParticleAngle = particleAngle;
    }
    
    @Override
    public void tick()
    {
        super.tick();
        motionX *= 0.9f;
        motionY *= 0.9f;
        motionZ *= 0.9f;
    }
    @Override
    public void renderParticle(IVertexBuilder b, ActiveRenderInfo info, float pticks) {
        super.renderParticle(ParticleRendering.getDelayedRender().getBuffer(RenderUtil.GLOWING_PARTICLE), info, pticks);
    }
    @Override
    public IParticleRenderType getRenderType()
    {
        return SpriteParticleRenderType.INSTANCE;
    }
}