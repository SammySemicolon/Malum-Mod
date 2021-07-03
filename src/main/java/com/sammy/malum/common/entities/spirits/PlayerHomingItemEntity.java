package com.sammy.malum.common.entities.spirits;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entities.FloatingItemEntity;
import com.sammy.malum.core.init.MalumEntities;
import com.sammy.malum.core.init.items.MalumItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public class PlayerHomingItemEntity extends FloatingItemEntity
{
    public UUID ownerUUID;
    public LivingEntity owner;
    public PlayerHomingItemEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
    }
    public static PlayerHomingItemEntity makeEntity(World world, UUID ownerUUID, ItemStack stack, double posX, double posY, double posZ, double velX, double velY, double velZ)
    {
        PlayerHomingItemEntity homingItemEntity = new PlayerHomingItemEntity(MalumEntities.PLAYER_HOMING_ITEM.get(), world);
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
    }

    @Override
    public void move()
    {
        super.move();
        float distance = getDistance(owner);
        if (age > 10)
        {
            float minimumDistance = 3f;
            float acceleration = 0.01f;
            if (MalumHelper.hasCurioEquipped(owner, MalumItems.RING_OF_ARCANE_REACH))
            {
                minimumDistance *= 3;
                acceleration *= 2;
            }
            float velocity = acceleration * moving;
            if (distance < minimumDistance || moving != 0)
            {
                moving++;
                Vector3d desiredMotion = owner.getPositionVec().subtract(getPositionVec()).normalize().mul(velocity, velocity, velocity);
                setMotion(desiredMotion);
            }
            if (distance < 0.5f)
            {
                if (isAlive())
                {
                    ItemStack stack = getItem();
                    MalumHelper.giveItemToEntity(stack, owner);
                    remove();
                }
            }
        }
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
}
