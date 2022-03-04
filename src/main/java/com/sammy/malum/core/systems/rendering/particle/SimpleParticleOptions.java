package com.sammy.malum.core.systems.rendering.particle;

import com.mojang.math.Vector3f;
import com.sammy.malum.core.systems.easing.Easing;
import net.minecraft.client.particle.ParticleRenderType;

public class SimpleParticleOptions {
    public enum Animator {
        FIRST_INDEX, LAST_INDEX, WITH_AGE, RANDOM_SPRITE
    }

    public ParticleRenderType renderType = ParticleRenderTypes.ADDITIVE;
    public Animator animator = Animator.FIRST_INDEX;
    public float r1 = 1, g1 = 1, b1 = 1, r2 = 1, g2 = 1, b2 = 1;
    public float colorCurveMultiplier = 1f;
    public Easing colorCurveEasing = Easing.LINEAR;

    public float scale1 = 1, scale2 = 0, scale3 = 0;
    public float scaleCurveMultiplier = 1f;
    public Easing scaleCurveStartEasing = Easing.LINEAR, scaleCurveEndEasing = Easing.LINEAR;

    public float alpha1 = 1, alpha2 = 0, alpha3 = 0;
    public float alphaCurveMultiplier = 1f;
    public Easing alphaCurveStartEasing = Easing.LINEAR, alphaCurveEndEasing = Easing.LINEAR;

    public boolean forcedMotion = false;
    public Vector3f startingMotion = Vector3f.ZERO, endingMotion = Vector3f.ZERO;
    public float motionCurveMultiplier = 1f;
    public Easing motionEasing = Easing.LINEAR;

    public float spin1 = 0, spin2 = 0;
    public float spinCurveMultiplier = 1f, spinOffset = 0;
    public Easing spinEasing = Easing.LINEAR;

    public int lifetime = 20;
    public boolean gravity = false;
    public boolean noClip = false;

    public SimpleParticleOptions() {
    }
    public boolean isTrinaryScale()
    {
        return scale2 != scale3;
    }
    public boolean isTrinaryAlpha()
    {
        return alpha2 != alpha3;
    }
}