package com.sammy.malum.common.entity.nitrate;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import team.lodestar.lodestone.helpers.EntityHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNitrateEntity extends ThrowableProjectile {
    public static final Color SECOND_SMOKE_COLOR = new Color(24, 24, 24);

    public final ArrayList<EntityHelper.PastPosition> pastPositions = new ArrayList<>(); // *screaming*
    public int maxAge = 1000;
    public int age;
    public float windUp;
    public int pierce = getPierce();

    public AbstractNitrateEntity(EntityType<? extends AbstractNitrateEntity> type, Level level) {
        super(type, level);
    }

    public AbstractNitrateEntity(EntityType<? extends AbstractNitrateEntity> type, double x, double y, double z, Level level) {
        super(type, x, y, z, level);
    }

    public AbstractNitrateEntity(EntityType<? extends AbstractNitrateEntity> type, LivingEntity owner, Level level) {
        super(type, owner, level);
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("maxAge", maxAge);
        compound.putInt("age", age);
        compound.putFloat("windUp", windUp);
        compound.putInt("pierce", pierce);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        maxAge = compound.getInt("maxAge");
        age = compound.getInt("age");
        windUp = compound.getFloat("windUp");
        pierce = compound.getInt("pierce");
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        EthericExplosion.explode(level, this, getX(), getY(0.0625D), getZ(), getExplosionRadius(), Explosion.BlockInteraction.BREAK);
        onExplode();
        if (pierce <= 0) {
            discard();
        } else {
            pierce--;
        }
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        trackPastPositions();
        age++;
        if (age > maxAge) {
            discard();
        }
        if (windUp < 1f) {
            windUp += 0.1f;
        }
        if (level.isClientSide) {
            spawnParticles();
        }
    }

    public void trackPastPositions() {
        EntityHelper.trackPastPositions(pastPositions, position().add(0, getYOffset(0) + 0.25F, 0), 0.01f);
        removeOldPositions(pastPositions);
    }

    public void removeOldPositions(List<EntityHelper.PastPosition> pastPositions) {
        int amount = pastPositions.size() - 1;
        List<EntityHelper.PastPosition> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            EntityHelper.PastPosition excess = pastPositions.get(i);
            if (excess.time > 25) {
                toRemove.add(excess);
            }
        }
        pastPositions.removeAll(toRemove);
    }

    public void onExplode() {

    }

    public abstract void spawnParticles();

    public float getYOffset(float partialTicks) {
        return Mth.sin(((float) age + partialTicks) / 10.0F) * 0.2F + 0.1F;
    }

    public abstract int getPierce();

    public abstract float getExplosionRadius();
}
