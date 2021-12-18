package com.sammy.malum.core.systems.rendering.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
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
        quadSize = Mth.lerp(coeff, data.scale1, data.scale2);
        coeff = getColorCoeff();
        float h = Mth.rotLerp(coeff, 360 * hsv1[0], 360 * hsv2[0]) / 360;
        float s = Mth.lerp(coeff, hsv1[1], hsv2[1]);
        float v = Mth.lerp(coeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = FastColor.ARGB32.red(packed) / 255.0f;
        float g = FastColor.ARGB32.green(packed) / 255.0f;
        float b = FastColor.ARGB32.blue(packed) / 255.0f;
        setColor(r, g, b);
        setAlpha(Mth.lerp(coeff, data.a1, data.a2));
        oRoll = roll;
        roll += data.spin;
    }

    @Override
    public void tick() {
        updateTraits();
        super.tick();
    }

//    @Override
//    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
//        super.render(ClientConfig.BETTER_LAYERING.get() ? RenderManager.DELAYED_RENDER.getBuffer(RenderTypes.ADDITIVE_BLOCK_PARTICLE) : consumer, camera, partialTicks);
//    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(consumer, camera, partialTicks);
    }
}