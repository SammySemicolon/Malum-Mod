package com.sammy.malum.common.entities.spirit;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
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
            }
        }
        else
        {
            double x = getPosX(), y = getPosY() + yOffset(0)+0.25f, z = getPosZ();
            int lifeTime = 14 + world.rand.nextInt(4);
            float scale = 0.17f + world.rand.nextFloat() * 0.03f;
            float velocity = 0.04f + world.rand.nextFloat() * 0.02f;

            Color color = new Color(240, 36, 235);
            ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                    .setScale(scale * 2, 0)
                    .setLifetime(lifeTime)
                    .setAlpha(0.2f)
                    .setColor(color, color)
                    .spawn(world,x,y,z);
            if (world.rand.nextFloat() < 0.9f)
            {
                ParticleManager.create(MalumParticles.WISP_PARTICLE)
                        .setScale(scale, 0)
                        .setLifetime(lifeTime)
                        .setAlpha(0.9f, 0.75f)
                        .setColor(color, MalumHelper.darker(color,2))
                        .addVelocity(0,velocity,0)
                        .setSpin(world.rand.nextFloat() * 0.5f)
                        .spawn(world,x,y,z);
            }
        }
    }
    public float yOffset(float partialTicks)
    {
        return MathHelper.sin(((float) age + partialTicks) / 10.0F + hoverStart) * 0.1F + 0.1F;
    }
    @Override
    public void writeAdditional(CompoundNBT compound)
    {
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