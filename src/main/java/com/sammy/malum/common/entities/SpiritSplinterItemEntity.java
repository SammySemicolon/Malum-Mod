package com.sammy.malum.common.entities;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.common.items.SpiritSplinterItem;
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
import top.theillusivec4.curios.api.CuriosApi;

import java.util.UUID;

public class SpiritSplinterItemEntity extends ProjectileItemEntity
{
    public static final DataParameter<String> SPLINTER_NAME = EntityDataManager.createKey(SpiritSplinterItemEntity.class, DataSerializers.STRING);
    
    public SpiritSplinterItem splinter;
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
    
    public SpiritSplinterItemEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
        noClip = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }
    
    @Override
    protected void registerData()
    {
        super.registerData();
        dataManager.register(SPLINTER_NAME, "wild");
    }
    public void setData(SpiritSplinterItem item, UUID ownerUUID)
    {
        this.splinter = item;
        this.ownerUUID = ownerUUID;
        getDataManager().set(SpiritSplinterItemEntity.SPLINTER_NAME, item.type.identifier);
    
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
                    if (CuriosApi.getCuriosHelper().findEquippedCurio(stack -> stack.getItem().equals(MalumItems.RING_OF_ATTRACTION.get()),entity).isPresent())
                    {
                        minimumDistance = 10f;
                    }
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
                            SpiritHelper.harvestSpirit(splinter.type.identifier, entity);
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
            float r = splinter.type.color.getRed() / 255.0f, g = splinter.type.color.getGreen() / 255.0f, b = splinter.type.color.getBlue() / 255.0f;
            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(1.0f, 0).setScale(0.05f, 0).setLifetime(20)
                    .randomOffset(0.2, 0.1).randomVelocity(0.02f, 0.06f)
                    .addVelocity(0, 0.01f, 0)
                    .setColor(r, g, b, r, g * 1.5f, b * 1.5f)
                    .setSpin(0.4f)
                    .repeat(world, x, y, z, 2);
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
        compound.putString("splinterType", splinter.type.identifier);
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
        splinter = SpiritHelper.figureOutType(compound.getString("splinterType")).splinterItem;
        dataManager.set(SPLINTER_NAME, splinter.type.identifier);
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
        return splinter;
    }
    public void updateSplinter()
    {
        if (splinter == null)
        {
            splinter = SpiritHelper.figureOutType(dataManager.get(SPLINTER_NAME)).splinterItem;
        }
    }
}