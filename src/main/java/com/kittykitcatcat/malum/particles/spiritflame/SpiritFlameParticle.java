package com.kittykitcatcat.malum.particles.spiritflame;

import com.kittykitcatcat.malum.particles.MalumParticle;
import com.kittykitcatcat.malum.particles.ParticlePhase;
import com.kittykitcatcat.malum.particles.ScalePhase;
import com.kittykitcatcat.malum.particles.skull.SkullParticle;
import net.minecraft.client.particle.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpiritFlameParticle extends MalumParticle
{
    
    protected SpiritFlameParticle(World world, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, float scale, IAnimatedSprite spriteSet)
    {
        super(world, xSpeed, ySpeed, zSpeed,x,y,z, spriteSet,
                new ParticlePhase(1,20, 0,1),
                new ParticlePhase(2,20, 20),
                new ScalePhase(2,20, 20, scale,false));
        //0-19 entrance
        //20-39 animation
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
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public float getScale(float p_217561_1_)
    {
        return scale;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<SpiritFlameParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(SpiritFlameParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new SpiritFlameParticle(worldIn, xSpeed, ySpeed, zSpeed, x,y,z, data.scale, spriteSet);
        }
    }
}