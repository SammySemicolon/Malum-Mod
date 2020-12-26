package com.sammy.malum.core.systems.particles.phases;

import com.sammy.malum.core.systems.particles.MalumParticle;

public class ParticlePhase
{
    public int currentLoop;
    public int currentFrame;
    
    public final int loopStart;
    public final int loopEnd;
    public final int totalLoops;
    
    public boolean isComplete;
    
    public ParticlePhase(int loopStart, int loopEnd, int totalLoops, int firstOffset) {
        this.loopStart = loopStart;
        this.loopEnd = loopEnd;
        this.totalLoops = totalLoops;
        this.currentFrame = firstOffset;
    }
    
    public void tick(MalumParticle particle)
    {
        if (isComplete)
        {
            return;
        }
        particle.setSprite(currentFrame);
        
        if (currentFrame++ == loopEnd)
        {
            currentLoop++;
            currentFrame = loopStart;
            if (currentLoop == totalLoops)
            {
                isComplete = true;
            }
        }
    }
}
