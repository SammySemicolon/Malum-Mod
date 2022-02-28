package com.sammy.malum.client.particles.wisp;

import com.sammy.malum.core.systems.rendering.particle.options.ScreenParticleOptions;
import com.sammy.malum.core.systems.rendering.screenparticle.ScreenParticle;
import com.sammy.malum.core.systems.rendering.screenparticle.ScreenParticleType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SpriteSet;

public class WispScreenParticleType extends ScreenParticleType<ScreenParticleOptions> {

    public WispScreenParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<ScreenParticleOptions> {
        public final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public ScreenParticle createParticle(ClientLevel pLevel, ScreenParticleOptions options, double pX, double pY, double pXSpeed, double pYSpeed) {
            WispScreenParticle particle = new WispScreenParticle(pLevel, options, pX, pY, pXSpeed, pYSpeed);
            particle.pickSprite(sprite);
            return particle;
        }
    }
}