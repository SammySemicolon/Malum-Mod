package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.EntityRegistry;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class PlayerHomingItemEntity extends FloatingItemEntity {
    public UUID ownerUUID;
    public LivingEntity owner;

    public PlayerHomingItemEntity(Level level) {
        super(EntityRegistry.PLAYER_HOMING_ITEM.get(), level);
    }

    public PlayerHomingItemEntity(Level level, UUID ownerUUID, ItemStack stack, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(EntityRegistry.PLAYER_HOMING_ITEM.get(), level);
        setOwner(ownerUUID);
        setItem(stack);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);

    }

    public float getRange() {
        return level.noCollision(this) ? range : range * 5f;
    }

    public void setOwner(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        updateOwner();
    }
    public void updateOwner()
    {
        if (!level.isClientSide) {
            owner = (LivingEntity) ((ServerLevel) level).getEntity(ownerUUID);
            if (owner != null)
            {
                range = (int) owner.getAttributeValue(AttributeRegistry.SPIRIT_REACH);
            }
        }
    }
    @Override
    public void move() {
        setDeltaMovement(getDeltaMovement().multiply(0.95f, 0.95f, 0.95f));
        if (owner == null || !owner.isAlive()) {
            if (level.getGameTime() % 40L == 0)
            {
                Player playerEntity = level.getNearestPlayer(this, getRange()*5f);
                if (playerEntity != null)
                {
                    setOwner(playerEntity.getUUID());
                }
            }
            return;
        }
        Vec3 desiredLocation = owner.position().add(0, owner.getBbHeight() / 4, 0);
        float distance = (float) distanceToSqr(desiredLocation);
        float velocity = Mth.lerp(Math.min(moveTime, 20)/20f, 0.1f, 0.2f+(range*0.2f));
        if (moveTime != 0 || distance < getRange()) {
            moveTime++;
            Vec3 desiredMotion = desiredLocation.subtract(position()).normalize().multiply(velocity, velocity, velocity);
            float easing = 0.02f;
            float xMotion = (float) Mth.lerp(easing, getDeltaMovement().x, desiredMotion.x);
            float yMotion = (float) Mth.lerp(easing, getDeltaMovement().y, desiredMotion.y);
            float zMotion = (float) Mth.lerp(easing, getDeltaMovement().z, desiredMotion.z);
            Vec3 resultingMotion = new Vec3(xMotion, yMotion, zMotion);
            setDeltaMovement(resultingMotion);
        }

        if (distance < 0.4f) {
            if (isAlive()) {
                ItemStack stack = getItem();
                SpiritHelper.pickupSpirit(stack, owner);
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (ownerUUID != null) {
            compound.putUUID("ownerUUID", ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("ownerUUID")) {
            setOwner(compound.getUUID("ownerUUID"));
        }
    }
}
