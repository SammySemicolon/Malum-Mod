package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.EntityRegistry;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.Player;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.Level.Level;
import net.minecraft.Level.server.ServerLevel;

import java.util.UUID;

public class PlayerHomingItemEntity extends FloatingItemEntity {
    public UUID ownerUUID;
    public LivingEntity owner;

    public PlayerHomingItemEntity(Level LevelIn) {
        super(EntityRegistry.PLAYER_HOMING_ITEM.get(), LevelIn);
    }

    public PlayerHomingItemEntity(Level LevelIn, UUID ownerUUID, ItemStack stack, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(EntityRegistry.PLAYER_HOMING_ITEM.get(), LevelIn);
        setOwner(ownerUUID);
        setItem(stack);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);

    }

    public float getRange() {
        return level.noCollision(this) ? range : range * 3f;
    }

    public void setOwner(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        if (MalumHelper.areWeOnServer(level)) {
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
        if (owner == null) {
            if (level.getGameTime() % 20L == 0)
            {
                Player playerEntity = level.getNearestPlayer(this, getRange()*3f);
                if (playerEntity != null)
                {
                    setOwner(playerEntity.getUUID());
                }
            }
            age++;
            return;
        }
        Vector3d desiredLocation = owner.position().add(0, owner.getBbHeight() / 4, 0);
        float distance = (float) distanceToSqr(desiredLocation);
        float range = getRange();
        float velocity = MathHelper.lerp(Math.min(moveTime, 40)/40f, 0.1f, 0.3f+(range*0.1f));
        if (moveTime != 0 || distance < range) {
            moveTime++;
            Vector3d desiredMotion = desiredLocation.subtract(position()).normalize().multiply(velocity, velocity, velocity);
            float easing = 0.02f;
            float xMotion = (float) MathHelper.lerp(easing, getDeltaMovement().x, desiredMotion.x);
            float yMotion = (float) MathHelper.lerp(easing, getDeltaMovement().y, desiredMotion.y);
            float zMotion = (float) MathHelper.lerp(easing, getDeltaMovement().z, desiredMotion.z);
            Vector3d resultingMotion = new Vector3d(xMotion, yMotion, zMotion);
            setDeltaMovement(resultingMotion);
        }

        if (distance < 0.25f) {
            if (isAlive()) {
                ItemStack stack = getItem();
                SpiritHelper.pickupSpirit(stack, owner);
                remove();
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        if (ownerUUID != null) {
            compound.putUUID("ownerUUID", ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("ownerUUID")) {
            setOwner(compound.getUUID("ownerUUID"));
        }
    }
}
