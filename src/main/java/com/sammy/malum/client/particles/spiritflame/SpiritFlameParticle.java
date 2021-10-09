package com.sammy.malum.client.particles.spiritflame;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.core.mod_systems.particle.RenderUtilities;
import com.sammy.malum.core.mod_systems.particle.ParticlePhaseMalumParticle;
import com.sammy.malum.core.mod_systems.particle.ParticleRendering;
import com.sammy.malum.core.mod_systems.particle.data.MalumParticleData;
import com.sammy.malum.core.mod_systems.particle.phases.ParticlePhase;
import com.sammy.malum.core.mod_systems.particle.rendertypes.SpriteParticleRenderType;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;

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
    @Override
    protected int getBrightnessForRender(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return SpriteParticleRenderType.INSTANCE;
    }
}