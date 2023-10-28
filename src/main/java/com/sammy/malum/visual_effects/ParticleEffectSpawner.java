package com.sammy.malum.visual_effects;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.builder.AbstractWorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ParticleEffectSpawner<T extends AbstractWorldParticleBuilder<T, ?>> {

    private final T builder;
    private final Consumer<T> particleSpawner;

    private final WorldParticleBuilder bloomBuilder;
    private final Consumer<WorldParticleBuilder> bloomSpawner;

    public ParticleEffectSpawner(T builder, Consumer<T> particleSpawner, @Nullable WorldParticleBuilder bloomBuilder, @Nullable Consumer<WorldParticleBuilder> bloomSpawner) {
        this.builder = builder;
        this.particleSpawner = particleSpawner;
        this.bloomBuilder = bloomBuilder;
        this.bloomSpawner = bloomSpawner;
    }

    public ParticleEffectSpawner(Level level, Vec3 pos, T builder, WorldParticleBuilder bloomBuilder) {
        this(builder, b -> b.spawn(level, pos.x, pos.y, pos.z), bloomBuilder, b -> b.spawn(level, pos.x, pos.y, pos.z));
    }

    public ParticleEffectSpawner(Level level, Vec3 pos, T builder) {
        this(builder, b -> b.spawn(level, pos.x, pos.y, pos.z), null, null);
    }

    public ParticleEffectSpawner(T builder, Consumer<T> particleSpawner) {
        this(builder, particleSpawner, null, null);
    }

    public T getBuilder() {
        return builder;
    }

    public WorldParticleBuilder getBloomBuilder() {
        return bloomBuilder;
    }

    public void spawnParticles() {
        particleSpawner.accept(builder);
        if (bloomSpawner != null) {
            bloomSpawner.accept(bloomBuilder);
        }
    }
}