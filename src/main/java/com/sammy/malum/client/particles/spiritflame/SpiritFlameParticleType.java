package com.sammy.malum.client.particles.spiritflame;

import com.mojang.serialization.Codec;
import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.Level.ClientLevel;
import net.minecraft.particles.ParticleType;

public class SpiritFlameParticleType extends ParticleType<MalumParticleData> {
    public SpiritFlameParticleType() {
        super(false, MalumParticleData.DESERIALIZER);
    }
    
    @Override
    public Codec<MalumParticleData> codec() {
        return MalumParticleData.codecFor(this);
    }
    
    public static class Factory implements IParticleFactory<MalumParticleData>
    {
        private final IAnimatedSprite sprite;
        
        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }
        
        @Override
        public Particle createParticle(MalumParticleData data, ClientLevel Level, double x, double y, double z, double mx, double my, double mz) {
            return new SpiritFlameParticle(Level, data, x, y, z, mx, my, mz, sprite);
        }
    }
}