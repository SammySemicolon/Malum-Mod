package com.sammy.malum.client.particles.cut;

import net.minecraft.client.particle.*;
import net.minecraft.client.Level.ClientLevel;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScytheAttackParticle extends SpriteTexturedParticle {
    private final IAnimatedSprite spriteWithAge;

    private ScytheAttackParticle(ClientLevel Level, double x, double y, double z, double scale, IAnimatedSprite spriteWithAge) {
        super(Level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.spriteWithAge = spriteWithAge;
        this.lifetime = 4;
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.rCol = f;
        this.gCol = f;
        this.bCol = f;
        this.quadSize = 1.0F - (float) scale * 0.5F;
        this.setSpriteFromAge(spriteWithAge);
    }

    public int getLightColor(float partialTick) {
        return 15728880;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.setSpriteFromAge(this.spriteWithAge);
        }
    }

    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite spriteSet) {
            this.spriteSet = spriteSet;
        }

        public Particle createParticle(BasicParticleType typeIn, ClientLevel LevelIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ScytheAttackParticle(LevelIn, x, y, z, xSpeed, this.spriteSet);
        }
    }
}