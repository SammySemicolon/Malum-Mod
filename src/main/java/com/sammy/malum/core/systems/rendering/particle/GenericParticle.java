package com.sammy.malum.core.systems.rendering.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.core.systems.rendering.RenderManager;
import com.sammy.malum.core.systems.rendering.RenderTypes;
import com.sammy.malum.core.systems.rendering.particle.options.ParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

import java.awt.*;

public abstract class GenericParticle extends TextureSheetParticle {
    protected ParticleOptions data;
    float[] hsv1 = new float[3], hsv2 = new float[3];

    public GenericParticle(ClientLevel world, ParticleOptions data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, x, y, z);
        this.data = data;
        this.roll = data.startingSpin;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.setLifetime(data.lifetime);
        this.gravity = data.gravity ? 1 : 0;
        this.hasPhysics = !data.noClip;
        this.friction = data.activeMotionMultiplier;
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r1)), (int) (255 * Math.min(1.0f, data.g1)), (int) (255 * Math.min(1.0f, data.b1)), hsv1);
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r2)), (int) (255 * Math.min(1.0f, data.g2)), (int) (255 * Math.min(1.0f, data.b2)), hsv2);
        updateTraits();
    }

    protected float getCoeff() {
        return (float) this.age / (float) this.lifetime;
    }

    protected float getColorCoeff() {
        float increasedAge = Math.min(this.age * data.colorCurveMultiplier, this.lifetime);
        return increasedAge / (float) this.lifetime;
    }
    protected float getAlphaCoeff() {
        float increasedAge = Math.min(this.age * data.alphaCurveMultiplier, this.lifetime);
        return increasedAge / (float) this.lifetime;
    }

    protected void updateTraits() {
        float scaleCoeff = getCoeff();
        float colorCoeff = getColorCoeff();
        float alphaCoeff = getAlphaCoeff();
        quadSize = Mth.lerp(scaleCoeff, data.scale1, data.scale2);
        float h = Mth.rotLerp(colorCoeff, 360f * hsv1[0], 360f * hsv2[0]) / 360f;
        float s = Mth.lerp(colorCoeff, hsv1[1], hsv2[1]);
        float v = Mth.lerp(colorCoeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(r, g, b);
        setAlpha(Mth.lerp(alphaCoeff, data.a1, data.a2));
        oRoll = roll;
        roll += data.spin;
        data.spin*=data.activeSpinMultiplier;
    }

    @Override
    public void tick() {
        updateTraits();
        super.tick();
    }
    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(ClientConfig.DELAYED_PARTICLE_RENDERING.get() ? RenderManager.DELAYED_RENDER.getBuffer(RenderTypes.ADDITIVE_PARTICLE) : consumer, camera, partialTicks);
    }
}