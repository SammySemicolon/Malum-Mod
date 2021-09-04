package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class PlayerHomingItemEntity extends FloatingItemEntity
{
    public UUID ownerUUID;
    public LivingEntity owner;
    public float minimumDistance = 3f;

    public float acceleration = 0.01f;
    public PlayerHomingItemEntity(EntityType<? extends PlayerHomingItemEntity> type, World worldIn)
    {
        super(type, worldIn);
    }
    public PlayerHomingItemEntity(World worldIn)
    {
        super(MalumEntities.PLAYER_HOMING_ITEM.get(), worldIn);
    }

    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        if (ownerUUID != null)
        {
            compound.putUniqueId("ownerUUID", ownerUUID);
        }
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        if (compound.contains("ownerUUID"))
        {
            setOwnerUUID(compound.getUniqueId("ownerUUID"));
        }
    }
    public float getMinimumDistance()
    {
        return world.hasNoCollisions(this) ? minimumDistance : minimumDistance*3f;
    }
    @Override
    public void move()
    {
        super.move();
        if (owner == null)
        {
            world.addEntity(new ItemEntity(world, getPosX(), getPosY(), getPosZ(), getItem()));
            remove();
            return;
        }
        float distance = getDistance(owner);
        float minimumDistance = getMinimumDistance();
        float velocity = acceleration * Math.min(moving, 40);
        if (distance < minimumDistance || moving != 0)
        {
            moving++;
            Vector3d desiredMotion = owner.getPositionVec().subtract(getPositionVec()).normalize().mul(velocity, velocity, velocity);
            float pct = 0.1f;
            if (moving > 20)
            {
                pct = MathHelper.lerp((moving - 20) / 60f, 0.1f, 1f);
                pct = Math.min(1f, pct);
            }
            float xMotion = (float) MathHelper.lerp(pct, getMotion().x, desiredMotion.x);
            float yMotion = (float) MathHelper.lerp(pct, getMotion().y, desiredMotion.y);
            float zMotion = (float) MathHelper.lerp(pct, getMotion().z, desiredMotion.z);
            Vector3d resultingMotion = new Vector3d(xMotion, yMotion, zMotion);
            setMotion(resultingMotion);
        }

        if (distance < 0.5f)
        {
            if (isAlive())
            {
                ItemStack stack = getItem();
                SpiritHelper.pickupSpirit(stack, owner);
                remove();
            }
        }
    }

    public static PlayerHomingItemEntity makeEntity(World world, UUID ownerUUID, ItemStack stack, double posX, double posY, double posZ, double velX, double velY, double velZ)
    {
        PlayerHomingItemEntity homingItemEntity = new PlayerHomingItemEntity(world);
        homingItemEntity.setOwnerUUID(ownerUUID);
        homingItemEntity.setItem(stack);
        homingItemEntity.setPosition(posX,posY,posZ);
        homingItemEntity.setMotion(velX,velY,velZ);
        return homingItemEntity;
    }
    public void setOwnerUUID(UUID ownerUUID)
    {
        this.ownerUUID = ownerUUID;
        if (MalumHelper.areWeOnServer(world))
        {
            owner = (LivingEntity) ((ServerWorld) world).getEntityByUuid(ownerUUID);
        }
        if (MalumHelper.hasCurioEquipped(owner, MalumItems.RING_OF_ARCANE_REACH))
        {
            minimumDistance *= 3;
            acceleration *= 2;
        }
    }
}
