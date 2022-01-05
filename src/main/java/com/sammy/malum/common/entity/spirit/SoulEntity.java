package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.common.entity.FloatingEntity;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.EntityRegistry;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class SoulEntity extends FloatingEntity {
    public UUID thiefUUID;
    public LivingEntity thief;

    public SoulEntity(Level level) {
        super(EntityRegistry.NATURAL_SOUL.get(), level);
        maxAge = 60000000;
    }

    public SoulEntity(Level level, UUID ownerUUID, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(EntityRegistry.NATURAL_SOUL.get(), level);
        setThief(ownerUUID);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
        maxAge = 400;
    }

    public float getRange() {
        return level.noCollision(this) ? range : range * 5f;
    }

    public void setThief(UUID ownerUUID) {
        this.thiefUUID = ownerUUID;
        updateThief();
    }
    public void updateThief()
    {
        if (!level.isClientSide) {
            thief = (LivingEntity) ((ServerLevel) level).getEntity(thiefUUID);
            if (thief != null)
            {
                range = (int) (3+thief.getAttributeValue(AttributeRegistry.SPIRIT_REACH)*0.5f);
            }
        }
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        SpiritHelper.spawnSoulParticles(level, x, y, z, color, endColor);
    }

    @Override
    public void move() {
        setDeltaMovement(getDeltaMovement().multiply(0.95f, 0.95f, 0.95f));
        if (thief == null || !thief.isAlive()) {
            return;
        }
        float range = getRange();
        Vec3 desiredLocation = thief.position().add(0, thief.getBbHeight() / 4, 0);
        float distance = (float) distanceToSqr(desiredLocation);
        float velocity = Mth.lerp(Math.min(moveTime, 20) / 20f, 0.1f, 0.05f + (range * 0.1f));
        if (moveTime != 0 || distance < range) {
            moveTime++;
            Vec3 desiredMotion = desiredLocation.add(position()).normalize().multiply(velocity, velocity, velocity);
            float easing = 0.03f;
            float xMotion = (float) Mth.lerp(easing, getDeltaMovement().x, desiredMotion.x);
            float yMotion = (float) Mth.lerp(easing, getDeltaMovement().y, desiredMotion.y);
            float zMotion = (float) Mth.lerp(easing, getDeltaMovement().z, desiredMotion.z);
            Vec3 resultingMotion = new Vec3(xMotion, yMotion, zMotion);
            setDeltaMovement(resultingMotion);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (thiefUUID != null) {
            compound.putUUID("thiefUUID", thiefUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("thiefUUID")) {
            setThief(compound.getUUID("thiefUUID"));
        }
    }
}
