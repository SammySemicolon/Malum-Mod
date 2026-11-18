package com.sammy.malum.common.entity.nitrate;

import com.sammy.malum.visual_effects.networked.MalumNetworkedParticleEffectColorData;
import com.sammy.malum.visual_effects.networked.nitrate.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.awt.*;
import java.util.*;

public abstract class AbstractNitrateEntity extends ThrowableProjectile {
    protected static final EntityDataAccessor<Boolean> DATA_FADING_AWAY = SynchedEntityData.defineId(AbstractNitrateEntity.class, EntityDataSerializers.BOOLEAN);
    public static final int MAX_AGE = 1200;
    public static final Color SECOND_SMOKE_COLOR = new Color(30, 30, 30);

    public final TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(20);
    public final TrailPointBuilder spinningTrailPointBuilder = TrailPointBuilder.create(6);
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);

    public int age;
    public int timesExploded;

    public boolean fadingAway;

    public AbstractNitrateEntity(EntityType<? extends AbstractNitrateEntity> type, Level level) {
        super(type, level);
    }

    public AbstractNitrateEntity(EntityType<? extends AbstractNitrateEntity> type, LivingEntity owner, Level level) {
        super(type, owner, level);
    }

    public static Optional<LivingEntity> getOwnerFromExplosion(Entity explosionSource) {
        if (explosionSource instanceof AbstractNitrateEntity nitrateEntity) {
            if (nitrateEntity.getOwner() instanceof LivingEntity livingOwner) {
                return Optional.of(livingOwner);
            }
        }
        return Optional.empty();
    }

    public void onExplode() {
    }

    @OnlyIn(Dist.CLIENT)
    public abstract void spawnParticles();

    public abstract int getMaxPierce();

    public abstract float getExplosionRadius();

    public abstract NitrateImpactParticleEffectType getImpactParticleEffect();

    public abstract MalumNetworkedParticleEffectColorData getImpactParticleEffectColor();

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(DATA_FADING_AWAY, false);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FADING_AWAY.equals(pKey)) {
            fadingAway = entityData.get(DATA_FADING_AWAY);
            if (fadingAway) {
                age = MAX_AGE - 20;
            }
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (timesExploded != 0) {
            compound.putInt("timesExploded", timesExploded);
        }
        if (fadingAway) {
            compound.putBoolean("fadingAway", true);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        age = compound.getInt("age");
        timesExploded = compound.getInt("pierce");
        getEntityData().set(DATA_FADING_AWAY, compound.getBoolean("fadingAway"));
    }

    @Override
    protected void onHit(HitResult pResult) {
        if (fadingAway) {
            return;
        }

        if (level() instanceof ServerLevel serverLevel) {
            NitrateExplosion.explode(serverLevel, this, getX(), getY(0.0625D), getZ(), getExplosionRadius());
            onExplode();
            getImpactParticleEffect()
                    .createEffect(position())
                    .color(getImpactParticleEffectColor())
                    .spawn(serverLevel);
            if (timesExploded++ >= getMaxPierce()) {
                getEntityData().set(DATA_FADING_AWAY, true);
                setDeltaMovement(getDeltaMovement().scale(0.05f));
            }
        }
        super.onHit(pResult);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        if (!fadingAway) {
            setDeltaMovement(motion.x * 0.99f, (motion.y-0.015f)*0.99f, motion.z * 0.99f);
        }
        float randomOffsetScale = age > 5 ? Math.min((age-5) * 0.04f, 0.8f) : 0;
        float radialOffsetScale = fadingAway ? 0f : 0.15f;
        for (int i = 0; i < 2; i++) {
            float progress = i * 0.5f;
            Vec3 position = getPosition(progress);
            Vec3 randomizedPosition = position.add(
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -randomOffsetScale, randomOffsetScale),
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -randomOffsetScale, randomOffsetScale),
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -randomOffsetScale, randomOffsetScale));
            Vec3 rotatingPosition = position.add(Math.cos(spinOffset + (age + progress) / 2f) * radialOffsetScale, 0, Math.sin(spinOffset + (age + progress) / 2f) * radialOffsetScale);
            trailPointBuilder.addTrailPoint(new InterpolatedTrailPoint(position, i, randomizedPosition, (int) (trailPointBuilder.getTrailLength()*1.5f)));
            spinningTrailPointBuilder.addTrailPoint(new TrailPoint(rotatingPosition, i));
        }
        for (int i = 0; i < ((fadingAway || age > MAX_AGE - 20) ? 2 : 1); i++) {
            trailPointBuilder.tickTrailPoints();
            spinningTrailPointBuilder.tickTrailPoints();
        }
        age++;
        if (age > MAX_AGE) {
            discard();
        }
        if (level().isClientSide && !fadingAway && age > 1){
            spawnParticles();
        }
    }

    public float getVisualEffectScalar() {
        float effectScalar = fadingAway ? 1 - (age - MAX_AGE + 10) / 10f : 1;
        if (age < 5) {
            effectScalar = age / 5f;
        }
        return effectScalar;
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 4f;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean ignoreExplosion(Explosion explosion) {
        return true;
    }
}
