package com.kittykitcatcat.malum.particles.soulflameparticle;

import net.minecraft.client.particle.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SoulFlameParticle extends SimpleAnimatedParticle
{

    protected SoulFlameParticle(World world, double xSpeed, double ySpeed, double zSpeed, IAnimatedSprite animatedSprite, float yAcceleration, double x, double y, double z)
    {
        super(world, xSpeed, ySpeed, zSpeed, animatedSprite, yAcceleration);
        motionX = xSpeed;
        motionY = ySpeed;
        motionZ = zSpeed;
        setPosition(x, y, z);
        setMaxAge(120);
    }

    @Override
    public void tick()
    {
        super.tick();
        motionX *= 0.9f;
        motionX *= 0.9f;
        motionZ *= 0.9f;
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<SoulFlameParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(SoulFlameParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            SoulFlameParticle particle = new SoulFlameParticle(worldIn, xSpeed, ySpeed, zSpeed, spriteSet, 0, x,y,z);
            particle.selectSpriteRandomly(this.spriteSet);
            return particle;
        }
    }
}