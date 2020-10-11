package com.sammy.malum.particles.bonk;

import com.sammy.malum.particles.MalumParticle;
import com.sammy.malum.particles.ScalePhase;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BonkParticle extends MalumParticle
{
    protected BonkParticle(ClientWorld world, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, float scale, IAnimatedSprite spriteSet)
    {
        super(world, xSpeed, ySpeed, zSpeed,x,y,z, spriteSet,
                new ScalePhase(1,25, 0, scale,false));
        //0-24 entrance
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BonkParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }
    
        public Particle makeParticle(BonkParticleData data, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new BonkParticle(world, xSpeed, ySpeed, zSpeed, x,y,z, data.scale, spriteSet);
        }
    }
}