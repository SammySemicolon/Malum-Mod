package com.sammy.malum.particles;

public class ScalePhase extends ParticlePhase
{
    public boolean additive;
    public float scaleChange;
    public float baseScale;
    
    public ScalePhase(int loopCount, int frameCount, int startingFrame, float baseScale, boolean additive)
    {
        super(loopCount, frameCount, startingFrame);
        this.additive = additive;
        this.baseScale = baseScale;
        this.scaleChange = baseScale / loopCount / frameCount;
    }
    
    public void init(MalumParticle particle)
    {
        if (additive)
        {
            particle.scale = 0;
            return;
        }
        particle.scale = baseScale;
    }
    
    public int tick(MalumParticle particle)
    {
        if (additive)
        {
            if (particle.scale < baseScale)
            {
                particle.scale += scaleChange;
            }
        }
        else
        {
            if (particle.scale > 0)
            {
                particle.scale -= scaleChange;
            }
            else
            {
                particle.setExpired();
            }
        }
        return super.tick(particle);
    }
}