package com.sammy.malum.core.systems.particle;

import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import com.sammy.malum.core.systems.particle.rendertypes.SpriteParticleRenderType;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.MathHelper;

import java.awt.*;

public class GenericMalumParticle extends SpriteTexturedParticle {
    MalumParticleData data;
    float[] hsv1 = new float[3], hsv2 = new float[3];
    public GenericMalumParticle(ClientWorld world, MalumParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, x, y, z, vx, vy, vz);
        this.setPosition(x, y, z);
        this.data = data;
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;
        this.setMaxAge(data.lifetime);
        this.particleGravity = data.gravity ? 1 : 0;
        this.canCollide = !data.noClip;
        Color.RGBtoHSB((int)(255 * Math.min(1.0f, data.r1)), (int)(255 * Math.min(1.0f, data.g1)), (int)(255 * Math.min(1.0f, data.b1)), hsv1);
        Color.RGBtoHSB((int)(255 * Math.min(1.0f, data.r2)), (int)(255 * Math.min(1.0f, data.g2)), (int)(255 * Math.min(1.0f, data.b2)), hsv2);
        updateTraits();
    }

    protected float getCoeff() {
        return (float)this.age / (float)this.maxAge;
    }

    protected void updateTraits() {
        float coeff = getCoeff();
        particleScale = MathHelper.lerp(coeff, data.scale1, data.scale2);
        float h = MathHelper.interpolateAngle(coeff, 360 * hsv1[0], 360 * hsv2[0]) / 360;
        float s = MathHelper.lerp(coeff, hsv1[1], hsv2[1]);
        float v = MathHelper.lerp(coeff, hsv1[2], hsv2[2]);
        int packed = Color.HSBtoRGB(h, s, v);
        float r = ColorHelper.PackedColor.getRed(packed) / 255.0f;
        float g = ColorHelper.PackedColor.getGreen(packed) / 255.0f;
        float b = ColorHelper.PackedColor.getBlue(packed) / 255.0f;
        setColor(r, g, b);
        setAlphaF(MathHelper.lerp(coeff, data.a1, data.a2));
        prevParticleAngle = particleAngle;
        particleAngle += data.spin;
    }

    @Override
    public void tick() {
        updateTraits();
        super.tick();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return SpriteParticleRenderType.INSTANCE;
    }
}
