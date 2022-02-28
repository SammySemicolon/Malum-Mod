package com.sammy.malum.core.systems.rendering.particle.options;

public class ParticleOptionsBase {
    public float r1 = 1, g1 = 1, b1 = 1, a1 = 1, r2 = 1, g2 = 1, b2 = 1, a2 = 0;
    public float scale1 = 1, scale2 = 0;
    public int lifetime = 20;
    public float startingSpin = 0;
    public float spin = 0;
    public float activeMotionMultiplier = 1;
    public float activeSpinMultiplier = 1;
    public boolean gravity = false;
    public boolean noClip = false;
    public float colorCurveMultiplier = 1f;
    public float alphaCurveMultiplier = 1f;

    public ParticleOptionsBase() {
    }
}