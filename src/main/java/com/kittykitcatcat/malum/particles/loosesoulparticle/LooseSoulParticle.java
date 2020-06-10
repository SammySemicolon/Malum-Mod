package com.kittykitcatcat.malum.particles.loosesoulparticle;

import com.kittykitcatcat.malum.particles.spiritleaf.SpiritLeafParticle;
import net.minecraft.client.particle.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LooseSoulParticle extends SimpleAnimatedParticle
{

    private final IAnimatedSprite spriteSet;
    public Vec3d targetPos;
    protected LooseSoulParticle(World world, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z,double targetX, double targetY, double targetZ, IAnimatedSprite spriteSet)
    {
        super(world, xSpeed, ySpeed, zSpeed, spriteSet, 0);
        this.spriteSet = spriteSet;
        motionX = xSpeed;
        motionY = ySpeed;
        motionZ = zSpeed;
        targetPos = new Vec3d(targetX,targetY,targetZ);
        setPosition(x, y, z);
        setMaxAge(100);
    }

    @Override
    public void tick()
    {
        super.tick();
        selectSpriteWithAge(spriteSet);
        float turnSpeed = 0.15f;
        float moveSpeed = 0.02f;
        Vec3d velocity = targetPos.subtract(posX, posY, posZ).normalize().mul(moveSpeed, moveSpeed, moveSpeed);
        motionX += velocity.x;
        motionY += velocity.y;
        motionZ += velocity.z;
        Vec3d newVelocity = new Vec3d(motionX, motionY, motionZ).normalize().mul(turnSpeed, turnSpeed, turnSpeed);
        motionX = newVelocity.x;
        motionY = newVelocity.y;
        motionZ = newVelocity.z;
    }

    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<LooseSoulParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }
        public Particle makeParticle(LooseSoulParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            LooseSoulParticle particle = new LooseSoulParticle(worldIn, xSpeed, ySpeed, zSpeed, x,y,z, data.X, data.Y, data.Z, spriteSet);
            particle.selectSpriteRandomly(this.spriteSet);
            return particle;
        }
    }
}