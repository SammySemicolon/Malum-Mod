package com.kittykitcatcat.malum.particles;

public class ParticlePhase
{
    public int loopCount;
    public int currentLoop;
    
    public int startingFrame;
    public int frameCount;
    public int currentFrame;
    
    public int firstOffset;
    
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
                    if (firstOffset != 0)
                    {
                        ParticlePhase nextPhase = particle.phases.get(particle.currentPhase);
                        nextPhase.currentFrame = firstOffset;
                        return firstOffset + nextPhase.startingFrame;
                    }
                }
            }
        }
        return currentFrame;
    }
}