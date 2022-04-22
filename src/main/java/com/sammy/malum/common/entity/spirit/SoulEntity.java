package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.common.entity.FloatingEntity;
import com.sammy.malum.core.helper.SpiritHelper;
import com.sammy.malum.core.setup.content.AttributeRegistry;
import com.sammy.malum.core.setup.content.entity.EntityRegistry;
import com.sammy.malum.core.systems.spirit.MalumEntitySpiritData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

import static com.sammy.malum.core.systems.spirit.MalumEntitySpiritData.EMPTY;

public class SoulEntity extends FloatingEntity {
    public UUID thiefUUID;
    public MalumEntitySpiritData spiritData = EMPTY;
    public LivingEntity thief;

    public SoulEntity(Level level) {
        super(EntityRegistry.NATURAL_SOUL.get(), level);
        maxAge = 2000;
        range = 8;
    }

    public SoulEntity(Level level, MalumEntitySpiritData spiritData, UUID ownerUUID, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(EntityRegistry.NATURAL_SOUL.get(), level);
        this.spiritData = spiritData;
        if (!spiritData.equals(EMPTY)) {
            this.color = spiritData.primaryType.color;
            getEntityData().set(DATA_COLOR, color.getRGB());
            this.endColor = spiritData.primaryType.endColor;
            getEntityData().set(DATA_END_COLOR, endColor.getRGB());
        }
        range = 8;
        setThief(ownerUUID);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
        maxAge = 600;
    }


    public void setThief(UUID ownerUUID) {
        this.thiefUUID = ownerUUID;
        updateThief();
    }

    public void updateThief() {
        if (!level.isClientSide) {
            thief = (LivingEntity) ((ServerLevel) level).getEntity(thiefUUID);
            if (thief != null) {
                range = (int) thief.getAttributeValue(AttributeRegistry.SPIRIT_REACH.get());
            }
        }
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.025f);
        for (int i = 0; i < 4; i++) {
            double lerpX = Mth.lerp(i / 4.0f, x - motion.x, x);
            double lerpY = Mth.lerp(i / 4.0f, y - motion.y, y);
            double lerpZ = Mth.lerp(i / 4.0f, z - motion.z, z);
            SpiritHelper.spawnSoulParticles(level, lerpX, lerpY, lerpZ, 0.25f, 1, norm, color, endColor);
        }
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (pReason.equals(RemovalReason.KILLED)) {
            SpiritHelper.createSpiritsFromSoul(spiritData, level, position(), thief);
        }
        super.remove(pReason);
    }

    @Override
    public void move() {
        setDeltaMovement(getDeltaMovement().multiply(0.95f, 0.97f, 0.95f));
        if (thief == null || !thief.isAlive()) {
            if (level.getGameTime() % 40L == 0) {
                Player playerEntity = level.getNearestPlayer(this, range * 5f);
                if (playerEntity != null) {
                    setThief(playerEntity.getUUID());
                }
            }
            return;
        }
        float sine = Mth.sin(level.getGameTime()*0.05f)*0.2f;
        Vec3 desiredLocation = thief.position().add(0, thief.getBbHeight() / 4, 0).add(-sine, sine, -sine);
        float distance = (float) distanceToSqr(desiredLocation);
        float velocity = Mth.lerp(Math.min(moveTime, 20) / 20f, 0.1f, 0.05f + (range * 0.025f));
        if (distance > 2) {
            moveTime++;
            Vec3 desiredMotion = desiredLocation.subtract(position()).normalize().multiply(velocity, velocity, velocity).add(0, 0.075f, 0);
            float easing = 0.01f;
            float xMotion = (float) Mth.lerp(easing, getDeltaMovement().x, desiredMotion.x);
            float yMotion = (float) Mth.lerp(easing, getDeltaMovement().y, desiredMotion.y);
            float zMotion = (float) Mth.lerp(easing, getDeltaMovement().z, desiredMotion.z);
            Vec3 resultingMotion = new Vec3(xMotion, yMotion, zMotion);
            setDeltaMovement(resultingMotion);
            return;
        }

        boolean above = !level.noCollision(getBoundingBox().move(0, 1.5f, 0));
        boolean below = !level.noCollision(getBoundingBox().move(0, -2f, 0));
        if (above && below) {
            setDeltaMovement(getDeltaMovement().add(0, 0.002f, 0));
            return;
        }
        if (below) {
            setDeltaMovement(getDeltaMovement().add(0, 0.003f, 0));
        }
        if (above || !below) {
            setDeltaMovement(getDeltaMovement().add(0, -0.0015f, 0));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (!spiritData.equals(EMPTY)) {
            spiritData.saveTo(compound);
        }
        if (thiefUUID != null) {
            compound.putUUID("thiefUUID", thiefUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        spiritData = MalumEntitySpiritData.load(compound);
        if (compound.contains("thiefUUID")) {
            setThief(compound.getUUID("thiefUUID"));
        }
    }
}