package com.sammy.malum.core.systems.particle;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import com.sammy.malum.core.systems.particle.phases.ParticlePhase;
import net.minecraft.client.particle.*;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.Level.ClientLevel;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.ArrayList;

public class ParticlePhaseMalumParticle extends SpriteTexturedParticle
{
    public final IAnimatedSprite spriteSet;
    public ArrayList<ParticlePhase> phases;
    public MalumParticleData data;
    float[] hsv1 = new float[3], hsv2 = new float[3];
    
    public ParticlePhaseMalumParticle(ClientLevel Level, MalumParticleData data, double x, double y, double z, double vx, double vy, double vz,IAnimatedSprite spriteSet, ParticlePhase... phases)
    {
        super(Level, x, y, z, vx, vy, vz);
        this.phases = MalumHelper.toArrayList(phases);
        this.spriteSet = spriteSet;
        this.setSprite(phases[0].currentFrame);
        this.setPos(x, y, z);
        this.data = data;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.gravity = data.gravity ? 1 : 0;
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r1)), (int) (255 * Math.min(1.0f, data.g1)), (int) (255 * Math.min(1.0f, data.b1)), hsv1);
        Color.RGBtoHSB((int) (255 * Math.min(1.0f, data.r2)), (int) (255 * Math.min(1.0f, data.g2)), (int) (255 * Math.min(1.0f, data.b2)), hsv2);
        updateTraits();
    }
    public void setSprite(int spriteIndex)
    {
        if (spriteSet instanceof ParticleManager.AnimatedSpriteImpl)
        {
            ParticleManager.AnimatedSpriteImpl animatedSprite = (ParticleManager.AnimatedSpriteImpl) spriteSet;
            if (spriteIndex < animatedSprite.sprites.size() && spriteIndex >= 0) //idiot-proof if statement. The idiot is me
            {
                setSprite(animatedSprite.sprites.get(spriteIndex));
            }
        }
    }
    
    protected float getCoeff()
    {
        return (float) this.age / this.lifetime;
    }
    
    protected void updateTraits()
    {
        float coeff = getCoeff();
        quadSize = MathHelper.lerp(coeff, data.scale1, data.scale2);
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
    public void tick()
    {
        age++;
        xo = x;
        yo = y;
        zo = z;
        move(xd, yd, zd);
    
        if (phases.isEmpty())
        {
            remove();
            return;
        }
        if (!phases.get(0).isComplete)
        {
            phases.get(0).tick(this);
        }
        else
        {
            phases.remove(0);
        }
        updateTraits();
    }
    
    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    
}

