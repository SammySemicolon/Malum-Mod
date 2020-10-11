package com.sammy.malum.particles;

public class ParticlePhase
{
    public int loopCount;
    public int currentLoop;
    
    public int startingFrame;
    public int frameCount;
    public int currentFrame;
    
    public int firstOffset;
    
    public boolean finalPhase;
    public ParticlePhase(int loopCount, int frameCount, int startingFrame)
    {
        this.loopCount = loopCount;
        this.frameCount = frameCount;
        this.startingFrame = startingFrame;
    }
    public ParticlePhase(int loopCount, int frameCount, int startingFrame, int firstOffset)
    {
        this.loopCount = loopCount;
        this.frameCount = frameCount;
        this.startingFrame = startingFrame;
        this.firstOffset = firstOffset;
    }
    
    public ParticlePhase(int loopCount, int frameCount, int startingFrame,boolean finalPhase)
    {
        this.loopCount = loopCount;
        this.frameCount = frameCount;
        this.startingFrame = startingFrame;
        this.finalPhase = finalPhase;
    }
    public ParticlePhase(int loopCount, int frameCount, int startingFrame, int firstOffset,boolean finalPhase)
    {
        this.loopCount = loopCount;
        this.frameCount = frameCount;
        this.startingFrame = startingFrame;
        this.firstOffset = firstOffset;
        this.finalPhase = finalPhase;
    }
    public void init(MalumParticle particle)
    {
    
    }
    public int tick(MalumParticle particle)
    {
        if (particle.animationCooldown <= 0)
        {
            currentFrame += 1;
        }
        if (currentFrame >= frameCount)
        {
            currentFrame = 0;
            currentLoop++;
            if (currentLoop >= loopCount)
            {
                if (particle.currentPhase+1 < particle.phases.size())
                {
                    particle.currentPhase++;
                    ParticlePhase nextPhase = particle.phases.get(particle.currentPhase);
                    nextPhase.currentFrame = firstOffset;
                    if (firstOffset != 0)
                    {
                        return firstOffset + nextPhase.startingFrame;
                    }
                }
                if (finalPhase)
                {
                    particle.animationCooldown = 621;
                    particle.setExpired();
                }
            }
        }
        return currentFrame;
    }
}