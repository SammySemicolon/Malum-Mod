package com.kittykitcatcat.malum.particles.lensmagic;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.particles.MalumParticle;
import com.kittykitcatcat.malum.particles.ParticlePhase;
import com.kittykitcatcat.malum.particles.bonk.BonkParticleData;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LensMagicParticle extends MalumParticle
{
    protected LensMagicParticle(ClientWorld world, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, float scale, IAnimatedSprite spriteSet)
    {
        super(world, xSpeed, ySpeed, zSpeed,x,y,z, spriteSet,
                new ParticlePhase(1,20, 0,1),
                new ParticlePhase(MathHelper.nextInt(MalumMod.random, 1,3),20, 20),
                new ParticlePhase(1,20, 40, true));
        
        this.scale = scale;
        //0-19 entrance
        //20-39 animation
        //40-59 exit
    }
    
    @Override
    public void tick()
    {
        super.tick();
        motionX *= 0.85f;
        motionY *= 0.85f;
        motionZ *= 0.85f;
    }
    
    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<LensMagicParticleData>
    {
        private final IAnimatedSprite spriteSet;
        
        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }
        @Override
        public Particle makeParticle(LensMagicParticleData data, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new LensMagicParticle(world, xSpeed, ySpeed, zSpeed, x,y,z, data.scale, spriteSet);
        }
    }
}