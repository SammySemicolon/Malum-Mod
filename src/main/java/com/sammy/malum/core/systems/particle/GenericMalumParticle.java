package com.sammy.malum.core.systems.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sammy.malum.config.ClientConfig;
import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import com.sammy.malum.core.systems.particle.rendertypes.AdditiveRenderType;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.Level.ClientLevel;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class GenericMalumParticle extends SpriteTexturedParticle {
    MalumParticleData data;
    float[] hsv1 = new float[3], hsv2 = new float[3];

    public GenericMalumParticle(ClientLevel Level, MalumParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(Level, x, y, z, vx, vy, vz);
        this.setPos(x, y, z);
        this.data = data;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.setLifetime(data.lifetime);
        this.gravity = data.gravity ? 1 : 0;
        this.hasPhysics = !data.noClip;
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

    protected void updateTraits() {
        float coeff = getCoeff();
        quadSize = MathHelper.lerp(coeff, data.scale1, data.scale2);
        coeff = getColorCoeff();
        float h = MathHelper.rotLerp(coeff, 360 * hsv1[0], 360 * hsv2[0]) / 360;
        float s = MathHelper.lerp(coeff, hsv1[1], hsv2[1]);
        float v = MathHelper.lerp(coeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = ColorHelper.PackedColor.red(packed) / 255.0f;
        float g = ColorHelper.PackedColor.green(packed) / 255.0f;
        float b = ColorHelper.PackedColor.blue(packed) / 255.0f;
        setColor(r, g, b);
        setAlpha(MathHelper.lerp(coeff, data.a1, data.a2));
        oRoll = roll;
        roll += data.spin;
    }

    @Override
    public void tick() {
        updateTraits();
        super.tick();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return AdditiveRenderType.INSTANCE;
    }

    @Override
    public void render(IVertexBuilder b, ActiveRenderInfo info, float pticks) {
        super.render(ClientConfig.BETTER_LAYERING.get() ? ParticleRendering.getDelayedRender().getBuffer(RenderUtilities.GLOWING_PARTICLE) : b, info, pticks);
    }
}