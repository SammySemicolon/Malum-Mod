package com.sammy.malum.client.particles.skull;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.systems.particles.MalumParticle;
import com.sammy.malum.core.systems.particles.data.EidolonParticleData;
import com.sammy.malum.core.systems.particles.phases.ParticlePhase;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkullParticle extends MalumParticle
{
    public float startingScale;
    public float currentScale;
    protected SkullParticle(ClientWorld world, EidolonParticleData data, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet)
    {
        super(world, data, xSpeed, ySpeed, zSpeed, x, y, z, spriteSet, new ParticlePhase(0, 19, 1, 0), new ParticlePhase(20, 39, 2,20));
        startingScale = scale;
        currentScale = scale;
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
    public int getBrightnessForRender(float partialTick)
    {
        return 0xF000F0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<EidolonParticleData>
    {
        private final IAnimatedSprite spriteSet;
        
        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }
        
        @Override
        public Particle makeParticle(EidolonParticleData data, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new SkullParticle(world,data, xSpeed, ySpeed, zSpeed, x, y, z, spriteSet);
        }
    }
}