package com.sammy.malum.common.entities;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.item.SpiritSplinterItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.awt.*;
import java.util.UUID;

public class SpiritSplinterItemEntity extends ProjectileItemEntity
{
    public SpiritSplinterItem splinter;
    public UUID ownerUUID;
    public PlayerEntity owner;
    
    public int age;
    public float rotation;
    public final float hoverStart;
    
    public SpiritSplinterItemEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
        noClip = true;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }
    
    public static final DataParameter<String> SPLINTER_NAME = EntityDataManager.createKey(SpiritSplinterItemEntity.class, DataSerializers.STRING);
    
    public float getItemHover(float partialTicks)
    {
        return (rotation + partialTicks) / 20.0F + hoverStart;
    }
    
    @Override
    public float getCollisionBorderSize()
    {
        return 4f;
    }
    
    public PlayerEntity owner()
    {
        if (owner == null)
        {
            if (MalumHelper.areWeOnServer(world))
            {
                owner = (PlayerEntity) ((ServerWorld) world).getEntityByUuid(ownerUUID);
            }
        }
        return owner;
    }
    
    @Override
    protected void registerData()
    {
        super.registerData();
        dataManager.register(SPLINTER_NAME, "wild");
    }
    
    @Override
    public void tick()
    {
        super.tick();
        age++;
        float rotationSpeed = 1.75f;
        float extraSpeed = 5.25f;
        float rotationTime = 160;
        rotation += age > rotationTime ? rotationSpeed : rotationSpeed + ((rotationTime - age) / rotationTime) * extraSpeed;
        if (MalumHelper.areWeOnServer(world))
        {
            setMotion(getMotion().mul(0.9f, 0.8f, 0.9f));
            PlayerEntity playerEntity = owner();
            if (playerEntity == null)
            {
                return;
            }
            float distance = getDistance(playerEntity);
            if (age > 10)
            {
                if (distance < 2f)
                {
                    float velocity = 0.25f;
                    Vector3d ownerPos = owner.getPositionVec().add(0, 0, 0);
                    Vector3d desiredMotion = new Vector3d(ownerPos.x - getPosX(), ownerPos.y - getPosY(), ownerPos.z - getPosZ()).normalize().mul(velocity, velocity, velocity);
                    setMotion(desiredMotion);
                }
                if (distance < 0.5f)
                {
                    if (isAlive())
                    {
                        SpiritHelper.harvestSpirit(splinter.type.identifier, owner);
                        remove();
                    }
                }
            }
        }
    }
    
    @Override
    protected Item getDefaultItem()
    {
        if (splinter == null)
        {
            splinter = SpiritHelper.figureOutType(dataManager.get(SPLINTER_NAME)).splinterItem;
        }
        return splinter;
    }
    
    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        if (ownerUUID != null)
        {
            compound.putUniqueId("ownerUUID", ownerUUID);
        }
        compound.putString("splinterType", splinter.type.identifier);
    }
    
    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        if (compound.contains("ownerUUID"))
        {
            ownerUUID = compound.getUniqueId("ownerUUID");
            owner = owner();
        }
        splinter = SpiritHelper.figureOutType(compound.getString("splinterType")).splinterItem;
        dataManager.set(SPLINTER_NAME, splinter.type.identifier);
    }
    
    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
    @Override
    public boolean hasNoGravity()
    {
        return true;
    }
}