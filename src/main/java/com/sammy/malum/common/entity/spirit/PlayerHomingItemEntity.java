package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.malum.core.registry.misc.AttributeRegistry;
import com.sammy.malum.core.registry.misc.EntityRegistry;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class PlayerHomingItemEntity extends FloatingItemEntity {
    public UUID ownerUUID;
    public LivingEntity owner;

    public PlayerHomingItemEntity(World worldIn) {
        super(EntityRegistry.PLAYER_HOMING_ITEM.get(), worldIn);
    }

    public PlayerHomingItemEntity(World worldIn, UUID ownerUUID, ItemStack stack, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        super(EntityRegistry.PLAYER_HOMING_ITEM.get(), worldIn);
        setOwner(ownerUUID);
        setItem(stack);
        setPosition(posX, posY, posZ);
        setMotion(velX, velY, velZ);

    }

    public float getRange() {
        return world.hasNoCollisions(this) ? range : range * 3f;
    }

    public void setOwner(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        if (MalumHelper.areWeOnServer(world)) {
            owner = (LivingEntity) ((ServerWorld) world).getEntityByUuid(ownerUUID);
            if (owner != null)
            {
                range = (int) owner.getAttributeValue(AttributeRegistry.SPIRIT_REACH);
            }
        }
    }

    @Override
    public void move() {
        setMotion(getMotion().mul(0.95f, 0.95f, 0.95f));
        if (owner == null) {
            if (world.getGameTime() % 20L == 0)
            {
                PlayerEntity playerEntity = world.getClosestPlayer(this, getRange()*3f);
                if (playerEntity != null)
                {
                    setOwner(playerEntity.getUniqueID());
                }
            }
            age++;
            return;
        }
        Vector3d desiredLocation = owner.getPositionVec().add(0, owner.getHeight() / 4, 0);
        float distance = (float) getDistanceSq(desiredLocation);
        float range = getRange();
        float velocity = MathHelper.lerp(Math.min(moveTime, 40)/40f, 0.1f, 0.3f+(range*0.1f));
        if (moveTime != 0 || distance < range) {
            moveTime++;
            Vector3d desiredMotion = desiredLocation.subtract(getPositionVec()).normalize().mul(velocity, velocity, velocity);
            float easing = 0.02f;
            float xMotion = (float) MathHelper.lerp(easing, getMotion().x, desiredMotion.x);
            float yMotion = (float) MathHelper.lerp(easing, getMotion().y, desiredMotion.y);
            float zMotion = (float) MathHelper.lerp(easing, getMotion().z, desiredMotion.z);
            Vector3d resultingMotion = new Vector3d(xMotion, yMotion, zMotion);
            setMotion(resultingMotion);
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
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (ownerUUID != null) {
            compound.putUniqueId("ownerUUID", ownerUUID);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("ownerUUID")) {
            setOwner(compound.getUniqueId("ownerUUID"));
        }
    }
}
