package com.sammy.malum.client.particles.spiritflame;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.RenderUtil;
import com.sammy.malum.core.systems.particles.ParticlePhaseMalumParticle;
import com.sammy.malum.core.systems.particles.ParticleRendering;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
import com.sammy.malum.core.systems.particles.phases.ParticlePhase;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpiritFlameParticle extends ParticlePhaseMalumParticle
{
    protected SpiritFlameParticle(ClientWorld world, MalumParticleData data, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet)
    {
        super(world, data, xSpeed, ySpeed, zSpeed, x, y, z, spriteSet, new ParticlePhase(0, 37, 1, 0), new ParticlePhase(38, 77, 2,38));
        setMaxAge(118);
        canCollide = false;
    }
    @Override
    public void tick()
    {
        super.tick();
        motionX *= 0.9f;
        if (data.gravity && age < 5)
        {
            motionY += 0.005f;
        }
        else
        {
            motionY *= 0.9f;
        }
        motionZ *= 0.9f;
    }
}