package com.sammy.malum.client.particles;

import com.sammy.malum.core.systems.rendering.particle.screen.GenericScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.screen.ScreenParticleType;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SpriteSet;

public class SimpleScreenParticleType extends ScreenParticleType<ScreenParticleOptions> {

    public SimpleScreenParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<ScreenParticleOptions> {
        public final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public ScreenParticle createParticle(ClientLevel pLevel, ScreenParticleOptions options, double pX, double pY, double pXSpeed, double pYSpeed) {
            return new GenericScreenParticle(pLevel, options, (ParticleEngine.MutableSpriteSet) sprite, pX, pY, pXSpeed, pYSpeed);
        }
    }
}