package com.sammy.malum.core.systems.particles;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.particles.data.EidolonParticleData;
import com.sammy.malum.core.systems.particles.phases.ParticlePhase;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.world.ClientWorld;

import java.awt.*;
import java.util.ArrayList;

public class MalumParticle extends SimpleAnimatedParticle
{
    public final IAnimatedSprite spriteSet;
    public ArrayList<ParticlePhase> phases;
    public float scale;
    public float startingScale;
    protected MalumParticle(ClientWorld world, EidolonParticleData data, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet, ParticlePhase... phases)
    {
        super(world, x, y, z, spriteSet, 0);
        this.spriteSet = spriteSet;
        motionX = xSpeed;
        motionY = ySpeed;
        motionZ = zSpeed;
        setPosition(x, y, z);
        this.scale = data.scale;
        this.startingScale = this.scale;
        this.particleGravity = data.gravity ? 1 : 0;
        this.phases = MalumHelper.toArrayList(phases);
        this.phases.get(0).tick(this);
    }
    
    public void setSprite(int spriteIndex)
    {
        if (spriteSet instanceof ParticleManager.AnimatedSpriteImpl)
        {
            ParticleManager.AnimatedSpriteImpl animatedSprite = (ParticleManager.AnimatedSpriteImpl) spriteSet;
            if (spriteIndex < animatedSprite.sprites.size() && spriteIndex >= 0) //idiot-proof if statement. The idiot is me
            {
                setSprite(animatedSprite.sprites.get(spriteIndex));
            }
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
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        move(motionX, motionY, motionZ);
    
        if (phases.isEmpty())
        {
            setExpired();
            return;
        }
        if (!phases.get(0).isComplete)
        {
            phases.get(0).tick(this);
        }
        else
        {
            phases.remove(0);
        }
    }
}