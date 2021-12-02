package com.sammy.malum.core.systems.particle.phases;

import com.sammy.malum.core.systems.particle.ParticlePhaseMalumParticle;

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
    
    public void tick(ParticlePhaseMalumParticle particle)
    {
        if (isComplete)
        {
            return;
        }
        particle.setSprite(currentFrame);
        
        if (currentFrame++ == loopEnd)
        {
            currentLoop++;
            if (currentLoop == totalLoops)
            {
                isComplete = true;
                return;
            }
            currentFrame = loopStart;
        }
    }
}
