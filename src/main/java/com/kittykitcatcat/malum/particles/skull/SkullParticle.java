package com.kittykitcatcat.malum.particles.skull;

import net.minecraft.client.particle.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SkullParticle extends SimpleAnimatedParticle
{
    private final IAnimatedSprite spriteSet;
    float scale;
    protected SkullParticle(World world, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet)
    {
        super(world, xSpeed, ySpeed, zSpeed, spriteSet, 0);
        this.spriteSet = spriteSet;
        motionX = xSpeed;
        motionY = ySpeed;
        scale = 1f;
        motionZ = zSpeed;
        selectSpriteWithAge(spriteSet);
        setPosition(x, y, z);
        setMaxAge(84);
    }

    @Override
    public void tick()
    {
        super.tick();
        selectSpriteWithAge(spriteSet);
        motionX *= 0.9f;
        motionY *= 0.9f;
        motionZ *= 0.9f;
        if (age >= 74)
        {
            if (scale > 0f)
            {
                scale -= 0.1f;
            }
        }
    }
    @Override
    public float getScale(float p_217561_1_)
    {
        return scale;
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
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
            return new SkullParticle(worldIn, xSpeed, ySpeed, zSpeed, x,y,z, spriteSet);
        }
    }
}