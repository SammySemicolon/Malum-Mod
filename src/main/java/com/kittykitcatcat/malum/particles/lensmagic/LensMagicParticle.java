package com.kittykitcatcat.malum.particles.lensmagic;

import net.minecraft.client.particle.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LensMagicParticle extends SimpleAnimatedParticle
{

    private final IAnimatedSprite spriteSet;
    public float scale;
    protected LensMagicParticle(World world, float scale, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet)
    {
        super(world, xSpeed, ySpeed, zSpeed, spriteSet, 0);
        this.spriteSet = spriteSet;
        motionX = xSpeed;
        motionY = ySpeed;
        this.scale = scale;
        motionZ = zSpeed;
        selectSpriteWithAge(spriteSet);
        setPosition(x, y, z);
        setMaxAge(75);
    }

    @Override
    public void tick()
    {
        super.tick();
        selectSpriteWithAge(spriteSet);
        motionX *= 0.9f;
        motionY *= 0.9f;
        motionZ *= 0.9f;
        if (age >= 65)
        {
            if (scale > 0f)
            {
                scale -= 0.025f;
            }
        }
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
    public static class Factory implements IParticleFactory<LensMagicParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(LensMagicParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new LensMagicParticle(worldIn, data.scale, xSpeed, ySpeed, zSpeed, x,y,z, spriteSet);
        }
    }
}