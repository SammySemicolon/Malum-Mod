package com.kittykitcatcat.malum.particles.skull;

import com.kittykitcatcat.malum.particles.MalumParticle;
import com.kittykitcatcat.malum.particles.ParticlePhase;
import com.kittykitcatcat.malum.particles.ScalePhase;
import net.minecraft.client.particle.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkullParticle extends MalumParticle
{
    protected SkullParticle(World world, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, float scale, IAnimatedSprite spriteSet)
    {
        super(world, xSpeed, ySpeed, zSpeed,x,y,z, spriteSet,
                new ParticlePhase(1,12, 0,11),
                new ParticlePhase(2,20, 12),
                new ScalePhase(2,20, 12, scale,false));
        //0-11 entrance
        //12-32 animation
    }
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<SkullParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(SkullParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new SkullParticle(worldIn, xSpeed, ySpeed, zSpeed, x,y,z, data.scale, spriteSet);
        }
    }
    
    @Override
    public void tick()
    {
        super.tick();
        motionX *= 0.9f;
        motionY *= 0.9f;
        motionZ *= 0.9f;
    }
}