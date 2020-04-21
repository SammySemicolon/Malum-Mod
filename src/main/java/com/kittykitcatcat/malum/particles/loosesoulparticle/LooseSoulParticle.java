package com.kittykitcatcat.malum.particles.loosesoulparticle;

import com.kittykitcatcat.malum.MalumMod;
import net.minecraft.client.particle.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static com.kittykitcatcat.malum.MalumMod.*;

public class LooseSoulParticle extends SimpleAnimatedParticle
{

    private final IAnimatedSprite spriteSet;
    public float scale;
    public Vec3d targetPos;
    protected LooseSoulParticle(World world, double startingX, double startingZ, double targetX, double targetY, double targetZ,double unused, IAnimatedSprite spriteSet)
    {
        super(world, startingX, targetY-0.1f, startingZ, spriteSet, 0);
        motionX = MathHelper.nextFloat(random, -0.4f, 0.4f);
        motionZ = MathHelper.nextFloat(random, -0.1f, 0.2f);
        motionY = MathHelper.nextFloat(random, -0.4f, 0.4f);
        this.spriteSet = spriteSet;
        setMaxAge(80);
        canCollide = false;
        targetPos = new Vec3d(targetX,targetY,targetZ);
        setPosition(startingX,targetY-0.1f,startingZ);
    }

    @Override
    public void tick()
    {
        super.tick();
        selectSpriteWithAge(spriteSet);
        if (age < 10)
        {
            if (scale < 0.25f)
            {
                scale += 0.025f;
            }
        }
        if (age > maxAge - 15)
        {
            if (scale > 0)
            {
                scale -= 0.05f;
            }
            if (scale < 0)
            {
                scale =0f;
            }
        }

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

    @Override
    public float getScale(float p_217561_1_)
    {
        return scale;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<LooseSoulParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(LooseSoulParticleData data, World worldIn, double startingX, double startingZ, double targetPosX, double targetPosY, double targetPosZ,double age)
        {
            LooseSoulParticle particle = new LooseSoulParticle(worldIn,startingX, startingZ, targetPosX,targetPosY,targetPosZ,age,spriteSet);
            particle.selectSpriteRandomly(this.spriteSet);
            return particle;
        }
    }
}