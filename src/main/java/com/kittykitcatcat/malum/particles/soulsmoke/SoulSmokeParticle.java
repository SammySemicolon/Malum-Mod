package com.kittykitcatcat.malum.particles.soulsmoke;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL;

public class SoulSmokeParticle extends SimpleAnimatedParticle
{
    public float scale;
    protected SoulSmokeParticle(World world, double xSpeed, double ySpeed, double zSpeed, double x, double y, double z, IAnimatedSprite spriteSet)
    {
        super(world, xSpeed, ySpeed, zSpeed, spriteSet, 0);
        motionX = xSpeed;
        motionY = ySpeed;
        motionZ = zSpeed;
        scale = 1;
        selectSpriteRandomly(spriteSet);
        setPosition(x, y, z);
        setMaxAge(80);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (age > 70)
        {
            if (scale > 0f)
            {
                scale -= 0.1f;
            }
        }
    }
    @Override
    public IParticleRenderType getRenderType()
    {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public float getScale(float p_217561_1_)
    {
        return scale;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<SoulSmokeParticleData>
    {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet)
        {
            this.spriteSet = spriteSet;
        }

        public Particle makeParticle(SoulSmokeParticleData data, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            return new SoulSmokeParticle(worldIn, xSpeed, ySpeed, zSpeed, x,y,z, spriteSet);
        }
    }
}