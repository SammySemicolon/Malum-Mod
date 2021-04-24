package com.sammy.malum.common.entities.soul;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.souls.SpiritHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.awt.*;
import java.util.UUID;

public class SoulItemEntity extends ProjectileItemEntity
{
    public static final DataParameter<String> SOUL_NAME = EntityDataManager.createKey(SoulItemEntity.class, DataSerializers.STRING);
    
    public SpiritItem soulItem;
    public UUID ownerUUID;
    public PlayerEntity owner()
    {
        if (ownerUUID != null)
        {
            if (MalumHelper.areWeOnServer(world))
            {
                return (PlayerEntity) ((ServerWorld) world).getEntityByUuid(ownerUUID);
            }
        }
        return null;
    };
    
    public int age;
    public float rotation;
    public final float hoverStart;
    
    public SoulItemEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
        noClip = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }
    
    @Override
    protected void registerData()
    {
        super.registerData();
        dataManager.register(SOUL_NAME, "holy");
    }
    public void setData(SpiritItem soulItem, UUID ownerUUID)
    {
        this.soulItem = soulItem;
        this.ownerUUID = ownerUUID;
        getDataManager().set(SoulItemEntity.SOUL_NAME, soulItem.type.identifier);
    
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
            PlayerEntity entity = owner();
            if (entity != null)
            {
                float distance = getDistance(entity);
                if (age > 10)
                {
                    float minimumDistance = 2f;
                    if (distance < minimumDistance)
                    {
                        float velocity = 0.25f;
                        Vector3d ownerPos = entity.getPositionVec().add(0, 0, 0);
                        Vector3d desiredMotion = new Vector3d(ownerPos.x - getPosX(), ownerPos.y - getPosY(), ownerPos.z - getPosZ()).normalize().mul(velocity, velocity, velocity);
                        setMotion(desiredMotion);
                    }
                    if (distance < 0.5f)
                    {
                        if (isAlive())
                        {
                            SpiritHelper.harvestSpirit(soulItem.type.identifier, entity);
                            remove();
                        }
                    }
                }
            }
        }
        else
        {
            updateSplinter();
            double x = getPosX(), y = getPosY() + yOffset(0)+0.25f, z = getPosZ();
            Color color = soulItem.type.color;

            ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                    .setAlpha(0.2f, 0f)
                    .setLifetime(10)
                    .setScale(0.4f, 0)
                    .setColor(color.brighter(), color.darker())
                    .enableNoClip()
                    .repeat(world, x,y,z, 2);

            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(0.1f, 0f)
                    .setLifetime(20)
                    .setSpin(0.1f)
                    .setScale(0.2f, 0)
                    .setColor(color, color.darker())
                    .randomOffset(0.1f)
                    .enableNoClip()
                    .randomVelocity(0.01f, 0.01f)
                    .repeat(world, x,y,z, 1);
        }
    }
    public float yOffset(float partialTicks)
    {
        return MathHelper.sin(((float) age + partialTicks) / 10.0F + hoverStart) * 0.1F + 0.1F;
    }
    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        if (ownerUUID != null)
        {
            compound.putUniqueId("ownerUUID", ownerUUID);
        }
        compound.putString("splinterType", soulItem.type.identifier);
        compound.putInt("age",age);
        compound.putFloat("rotation",rotation);
    }
    
    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        if (compound.contains("ownerUUID"))
        {
            ownerUUID = compound.getUniqueId("ownerUUID");
        }
        soulItem = SpiritHelper.figureOutType(compound.getString("splinterType")).splinterItem;
        dataManager.set(SOUL_NAME, soulItem.type.identifier);
        age = compound.getInt("age");
        rotation = compound.getFloat("rotation");
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
    
    @Override
    public float getCollisionBorderSize()
    {
        return 4f;
    }
    
    @Override
    protected Item getDefaultItem()
    {
        updateSplinter();
        return soulItem;
    }
    public void updateSplinter()
    {
        if (soulItem == null)
        {
            soulItem = SpiritHelper.figureOutType(dataManager.get(SOUL_NAME)).splinterItem;
        }
    }
}