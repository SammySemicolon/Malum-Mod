package com.sammy.malum.client.particles.spiritflame;

import com.sammy.malum.core.systems.particles.MalumParticle;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
import com.sammy.malum.core.systems.particles.phases.ParticlePhase;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpiritFlameParticle extends MalumParticle
{
    protected SpiritFlameParticle(ClientWorld world, MalumParticleData data, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet)
    {
        super(world, data, xSpeed, ySpeed, zSpeed, x, y, z, spriteSet, new ParticlePhase(0, 37, 1, 0), new ParticlePhase(38, 77, 2,38));
    }
    int goUpPlease = 20;
    @Override
    public void tick()
    {
        super.tick();
        motionX *= 0.9f;
        if (gravity && goUpPlease > 0)
        {
            motionY += 0.005f;
            goUpPlease--;
        }
        else
        {
            motionY *= 0.9f;
        }
        motionY *= 0.9f;
        motionZ *= 0.9f;
    }
    
    @Override
    public int getBrightnessForRender(float partialTick)
    {
        return 0xF000F0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<MalumParticleData>
    {
        private final IAnimatedSprite spriteSet;
        
        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }
        
        @Override
        public Particle makeParticle(MalumParticleData data, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new SpiritFlameParticle(world,data, xSpeed, ySpeed, zSpeed, x, y, z, spriteSet);
        }
    }
}