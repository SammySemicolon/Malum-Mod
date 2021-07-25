package com.sammy.malum.client.particles.spiritflame;

import com.mojang.serialization.Codec;
import com.sammy.malum.core.mod_systems.particle.data.MalumParticleData;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.ParticleType;

public class SpiritFlameParticleType extends ParticleType<MalumParticleData> {
    public SpiritFlameParticleType() {
        super(false, MalumParticleData.DESERIALIZER);
    }
    
    @Override
    public Codec<MalumParticleData> func_230522_e_() {
        return MalumParticleData.codecFor(this);
    }
    
    public static class Factory implements IParticleFactory<MalumParticleData>
    {
        private final IAnimatedSprite sprite;
        
        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }
        
        @Override
        public Particle makeParticle(MalumParticleData data, ClientWorld world, double x, double y, double z, double mx, double my, double mz) {
            return new SpiritFlameParticle(world, data, x, y, z, mx, my, mz, sprite);
        }
    }
}