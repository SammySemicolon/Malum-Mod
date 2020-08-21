package com.kittykitcatcat.malum.particles;

import com.kittykitcatcat.malum.MalumMod;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;

public class MalumParticle extends SimpleAnimatedParticle
{
    public ArrayList<ParticlePhase> phases;
    public final IAnimatedSprite spriteSet;
    public int currentPhase;
    public float scale;
    public int animationCooldown;
    
    protected MalumParticle(World world, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet, ParticlePhase... phases)
    {
        super(world, x, y, z, spriteSet, 0);
        animationCooldown = 4;
        this.phases = new ArrayList<>();
        this.phases.addAll(Arrays.asList(phases));
        this.spriteSet = spriteSet;
        motionX = xSpeed;
        motionY = ySpeed;
        motionZ = zSpeed;
        setPosition(x, y, z);
        int age = 0;
        for (ParticlePhase phase : this.phases)
        {
            phase.init(this);
            age += phase.frameCount * phase.loopCount;
        }
        age += animationCooldown;
        setMaxAge(age);
        setSprite(0);
    }
    
    public void setSprite(int spriteIndex)
    {
        if (spriteSet instanceof ParticleManager.AnimatedSpriteImpl)
        {
            ParticleManager.AnimatedSpriteImpl animatedSprite = (ParticleManager.AnimatedSpriteImpl) spriteSet;
            setSprite(animatedSprite.sprites.get(spriteIndex));
        }
    }
    
    @Override
    public float getScale(float scaleFactor)
    {
        return scale;
    }
    
    @Override
    public void tick()
    {
        super.tick();
        ParticlePhase phase = phases.get(currentPhase);
        int currentFrame = phase.startingFrame + phase.tick(this);
        setSprite(currentFrame);
        animationCooldown--;
    }
}