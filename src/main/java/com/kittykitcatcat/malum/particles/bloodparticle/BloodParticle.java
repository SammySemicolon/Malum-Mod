package com.kittykitcatcat.malum.particles.bloodparticle;

import net.minecraft.client.particle.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BloodParticle extends SpriteTexturedParticle
{
    protected BloodParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        super(world, x, y, z,xSpeed,ySpeed,zSpeed);
        motionX = xSpeed;
        motionY = ySpeed;
        motionZ = zSpeed;
        setPosition(x,y,z);
        setMaxAge(80);
        setSize(MathHelper.nextFloat(world.rand, 0.6f, 1f), MathHelper.nextFloat(world.rand, 0.6f, 1f));
    }

    @Override
    public void tick()
    {
        super.tick();
        motionY -= 0.01f;
        motionX *= 0.9f;
        motionZ *= 0.9f;
        if (this.onGround)
        {
            this.motionY = 0;
        }
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BloodParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(BloodParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            BloodParticle particle = new BloodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.selectSpriteRandomly(this.spriteSet);
            return particle;
        }
    }
}
