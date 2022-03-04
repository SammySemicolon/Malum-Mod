package com.sammy.malum.core.systems.rendering.particle.screen;

import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.client.multiplayer.ClientLevel;

public class ScreenParticleType<T extends ScreenParticleOptions>  extends net.minecraftforge.registries.ForgeRegistryEntry<ScreenParticleType<?>> {

   public ParticleProvider<T> provider;
   public ScreenParticleType() {
   }

   public interface ParticleProvider<T extends ScreenParticleOptions> {
      ScreenParticle createParticle(ClientLevel pLevel, T options, double pX, double pY, double pXSpeed, double pYSpeed);
   }
}