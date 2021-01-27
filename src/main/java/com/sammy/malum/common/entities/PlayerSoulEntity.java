package com.sammy.malum.common.entities;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import top.theillusivec4.curios.api.CuriosApi;

import java.awt.*;
import java.util.UUID;

public class PlayerSoulEntity extends ProjectileItemEntity
{
    public UUID oldOwnerUUID;
    public UUID ownerUUID;
    public PlayerEntity player(UUID uuid)
    {
        if (uuid != null)
        {
            if (MalumHelper.areWeOnServer(world))
            {
                return (PlayerEntity) ((ServerWorld) world).getEntityByUuid(uuid);
            }
        }
        return null;
    };
    
    public int age;
    public float rotation;
    public final float hoverStart;
    
    public PlayerSoulEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
        noClip = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }
    
    @Override
    protected Item getDefaultItem()
    {
        return MalumItems.FLUFFY_TAIL.get();
    }
    
    public void setData(UUID oldOwnerUUID, UUID ownerUUID)
    {
        this.oldOwnerUUID = oldOwnerUUID;
        this.ownerUUID = ownerUUID;
    }
    @Override
    protected void registerData()
    {
    
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
            setMotion(getMotion().mul(0.95f, 0.8f, 0.95f));
            PlayerEntity soulOwner = player(oldOwnerUUID);
            PlayerEntity newOwner = player(ownerUUID);
            if (age > 10)
            {
                if (soulOwner != null)
                {
                    float distance = getDistance(soulOwner);
                    float velocity = 0.025f;
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem().equals(MalumItems.RING_OF_ATTRACTION.get()),soulOwner).isPresent())
                    {
                        velocity = 0.1f;
                    }
                    Vector3d ownerPos = soulOwner.getPositionVec();
                    Vector3d desiredMotion = new Vector3d(ownerPos.x - getPosX(), ownerPos.y - getPosY(), ownerPos.z - getPosZ()).normalize().mul(velocity, velocity, velocity);
                    setMotion(desiredMotion);
                    if (distance < 1f)
                    {
                        if (isAlive())
                        {
                            remove();
                        }
                    }
                }
                if (newOwner != null)
                {
                    float distance = getDistance(newOwner);
                    if (distance < 1f)
                    {
                        if (isAlive())
                        {
                            newOwner.heal(newOwner.getMaxHealth());
                            remove();
                        }
                    }
                }
            }
        }
        else
        {
            double x = getPosX(), y = getPosY() + yOffset(0)+0.25f, z = getPosZ();
            Color color1 = MalumConstants.dark();
            Color color2 = MalumConstants.bright();
            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(1.0f, 0).setScale(0.075f, 0).setLifetime(20)
                    .randomOffset(0.1, 0.1).randomVelocity(0.005f, 0.005f)
                    .addVelocity(0, 0.01f, 0)
                    .setColor(color1,color1)
                    .setSpin(0.3f)
                    .repeat(world, x, y, z, 3);
            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(1.0f, 0).setScale(0.025f, 0).setLifetime(10)
                    .randomOffset(0.2, 0.2).randomVelocity(0.005f, 0.005f)
                    .addVelocity(0, 0.01f, 0)
                    .setColor(color2,color2)
                    .setSpin(0.6f)
                    .repeat(world, x, y, z, 1);
        }
    }
    public float yOffset(float partialTicks)
    {
        return MathHelper.sin(((float) age + partialTicks) / 10.0F + hoverStart) * 0.1F + 0.1F;
    }
    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        if (ownerUUID != null)
        {
            compound.putUniqueId("ownerUUID", ownerUUID);
        }
        if (oldOwnerUUID != null)
        {
            compound.putUniqueId("oldOwnerUUID", oldOwnerUUID);
        }
        compound.putInt("age",age);
        compound.putFloat("rotation",rotation);
    }
    
    @Override
    public void readAdditional(CompoundNBT compound)
    {
        if (compound.contains("ownerUUID"))
        {
            ownerUUID = compound.getUniqueId("ownerUUID");
        }
        if (compound.contains("oldOwnerUUID"))
        {
            oldOwnerUUID = compound.getUniqueId("oldOwnerUUID");
        }
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
    
}