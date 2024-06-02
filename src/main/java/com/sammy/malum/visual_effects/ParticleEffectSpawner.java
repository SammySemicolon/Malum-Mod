package com.sammy.malum.visual_effects;

import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.particle.builder.*;


import java.util.function.*;

public class ParticleEffectSpawner<T extends AbstractWorldParticleBuilder<T, ?>> {

    private final T builder;
    private final Consumer<T> particleSpawner;

    private final WorldParticleBuilder bloomBuilder;
    private final Consumer<WorldParticleBuilder> bloomSpawner;

    @Nullable
    private MalumSpiritType spiritType;

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

    public ParticleEffectSpawner<T> setSpiritType(MalumSpiritType spiritType) {
        this.spiritType = spiritType;
        return this;
    }

    public T getBuilder() {
        return builder;
    }

    public WorldParticleBuilder getBloomBuilder() {
        return bloomBuilder;
    }

    public void spawnParticles() {
        if (spiritType != null) {
            builder.act(spiritType.applyWorldParticleChanges());
        }
        particleSpawner.accept(builder);
        if (bloomSpawner != null) {
            if (spiritType != null) {
                bloomBuilder.act(spiritType.applyWorldParticleChanges());
            }
            bloomSpawner.accept(bloomBuilder);
        }
    }

    public void spawnParticlesRaw() {
        if (spiritType != null) {
            builder.act(spiritType.applyWorldParticleChanges());
        }
        particleSpawner.accept(builder);
    }
}