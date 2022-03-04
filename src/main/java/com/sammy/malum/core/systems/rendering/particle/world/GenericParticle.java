package com.sammy.malum.core.systems.rendering.particle.world;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.core.handlers.RenderHandler;
import com.sammy.malum.core.systems.rendering.RenderTypes;
import com.sammy.malum.core.systems.rendering.particle.SimpleParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.UUID;

public class GenericParticle extends TextureSheetParticle {
    protected WorldParticleOptions data;
    private final ParticleRenderType renderType;
    protected final ParticleEngine.MutableSpriteSet spriteSet;
    float[] hsv1 = new float[3], hsv2 = new float[3];

    public GenericParticle(ClientLevel world, WorldParticleOptions data, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, x, y, z);
        this.data = data;
        this.renderType = data.renderType;
        this.spriteSet = spriteSet;
        this.roll = data.spinOffset + data.spin1;
        if (!data.forcedMotion) {
            this.xd = xd;
            this.yd = yd;
            this.zd = zd;
        }
        this.setLifetime(data.lifetime);
        this.gravity = data.gravity ? 1 : 0;
        this.hasPhysics = !data.noClip;
        this.friction = 1;
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r1)), (int) (255 * Math.min(1.0f, data.g1)), (int) (255 * Math.min(1.0f, data.b1)), hsv1);
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r2)), (int) (255 * Math.min(1.0f, data.g2)), (int) (255 * Math.min(1.0f, data.b2)), hsv2);
        if (getAnimator().equals(SimpleParticleOptions.Animator.RANDOM_SPRITE)) {
            pickSprite(spriteSet);
        }
        if (getAnimator().equals(SimpleParticleOptions.Animator.FIRST_INDEX) || getAnimator().equals(SimpleParticleOptions.Animator.WITH_AGE)) {
            pickSprite(0);
        }
        if (getAnimator().equals(SimpleParticleOptions.Animator.LAST_INDEX)) {
            pickSprite(spriteSet.sprites.size() - 1);
        }
        updateTraits();
    }

    public SimpleParticleOptions.Animator getAnimator() {
        return data.animator;
    }

    public void pickSprite(int spriteIndex) {
        if (spriteIndex < spriteSet.sprites.size() && spriteIndex >= 0) {
            setSprite(spriteSet.sprites.get(spriteIndex));
        }
    }

    public void pickColor(float colorCoeff) {
        float h = Mth.rotLerp(colorCoeff, 360f * hsv1[0], 360f * hsv2[0]) / 360f;
        float s = Mth.lerp(colorCoeff, hsv1[1], hsv2[1]);
        float v = Mth.lerp(colorCoeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(r, g, b);
    }

    public float getCurve(float multiplier) {
        return Mth.clamp((age * multiplier) / (float)lifetime, 0, 1);
    }

    protected void updateTraits() {
        pickColor(data.colorCurveEasing.ease(getCurve(data.colorCurveMultiplier), 0, 1, 1));
        if (data.isTrinaryScale()) {
            float trinaryAge = getCurve(data.scaleCurveMultiplier);
            if (trinaryAge >= 0.5f) {
                quadSize = Mth.lerp(data.scaleCurveEndEasing.ease(trinaryAge - 0.5f, 0, 1, 0.5f), data.scale2, data.scale3);
            } else {
                quadSize = Mth.lerp(data.scaleCurveStartEasing.ease(trinaryAge, 0, 1, 0.5f), data.scale1, data.scale2);
            }
        } else {
            quadSize = Mth.lerp(data.scaleCurveStartEasing.ease(getCurve(data.scaleCurveMultiplier), 0, 1, 1), data.scale1, data.scale2);
        }
        if (data.isTrinaryAlpha()) {
            float trinaryAge = getCurve(data.alphaCurveMultiplier);
            if (trinaryAge >= 0.5f) {
                alpha = Mth.lerp(data.alphaCurveStartEasing.ease(trinaryAge-0.5f, 0, 1, 0.5f), data.alpha2, data.alpha3);
            } else {
                alpha = Mth.lerp(data.alphaCurveStartEasing.ease(trinaryAge, 0, 1, 0.5f), data.alpha1, data.alpha2);
            }
        } else {
            alpha = Mth.lerp(data.alphaCurveStartEasing.ease(getCurve(data.alphaCurveMultiplier), 0, 1, 1), data.alpha1, data.alpha2);
        }
        oRoll = roll;
        roll += Mth.lerp(data.spinEasing.ease(getCurve(data.spinCurveMultiplier), 0, 1, 1), data.spin1, data.spin2);
        if (data.forcedMotion) {
            float motionAge = getCurve(data.motionCurveMultiplier);
            xd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, 1), data.startingMotion.x(), data.endingMotion.x());
            yd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, 1), data.startingMotion.y(), data.endingMotion.y());
            zd = Mth.lerp(data.motionEasing.ease(motionAge, 0, 1, 1), data.startingMotion.z(), data.endingMotion.z());
        } else {
            xd *= data.motionCurveMultiplier;
            yd *= data.motionCurveMultiplier;
            zd *= data.motionCurveMultiplier;
        }
    }

    @Override
    public void tick() {
        updateTraits();
        if (data.animator.equals(SimpleParticleOptions.Animator.WITH_AGE)) {
            setSpriteFromAge(spriteSet);
        }
        super.tick();
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(ClientConfig.DELAYED_PARTICLE_RENDERING.get() ? RenderHandler.DELAYED_RENDER.getBuffer(RenderTypes.ADDITIVE_PARTICLE) : consumer, camera, partialTicks);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return renderType;
    }
}