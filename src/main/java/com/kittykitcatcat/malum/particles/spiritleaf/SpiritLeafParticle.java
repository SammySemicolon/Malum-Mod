package com.kittykitcatcat.malum.particles.spiritleaf;

import net.minecraft.client.particle.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpiritLeafParticle extends SpriteTexturedParticle
{
    protected SpiritLeafParticle(World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        super(world, x, y, z,xSpeed,ySpeed,zSpeed);
        motionX = xSpeed;
        motionY = ySpeed;
        motionZ = zSpeed;
        setPosition(x,y,z);
        setMaxAge(200);
    }

    @Override
    public void tick()
    {
        super.tick();
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
    public static class Factory implements IParticleFactory<SpiritLeafParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(SpiritLeafParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            SpiritLeafParticle particle = new SpiritLeafParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.selectSpriteRandomly(this.spriteSet);
            return particle;
        }
    }
}
