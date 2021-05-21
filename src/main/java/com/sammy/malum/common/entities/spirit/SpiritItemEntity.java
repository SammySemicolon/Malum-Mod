package com.sammy.malum.common.entities.spirit;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.entities.boomerang.ScytheBoomerangEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.items.ItemHandlerHelper;

import java.awt.*;
import java.util.UUID;

public class SpiritItemEntity extends ProjectileItemEntity
{
    public static final DataParameter<ItemStack> STACK = EntityDataManager.createKey(ScytheBoomerangEntity.class, DataSerializers.ITEMSTACK);

    public ItemStack stack;
    public UUID ownerUUID;

    public int age;
    public float rotation;
    public final float hoverStart;

    public SpiritItemEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
        noClip = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }

    @Override
    protected void registerData()
    {
        super.registerData();
        dataManager.register(STACK, ItemStack.EMPTY);
    }

    public void setData(ItemStack stack, UUID ownerUUID)
    {
        this.stack = stack;
        this.ownerUUID = ownerUUID;
        getDataManager().set(STACK, stack);
    }

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
            PlayerEntity owner = owner();
            if (owner != null)
            {
                float distance = getDistance(owner);
                if (age > 10)
                {
                    float minimumDistance = 2f;
                    float velocity = 0.25f;
                    if (MalumHelper.hasCurioEquipped(owner, MalumItems.RING_OF_ARCANE_REACH))
                    {
                        minimumDistance = 8f;
                        velocity = 0.4f;
                    }
                    if (distance < minimumDistance)
                    {
                        Vector3d ownerPos = owner.getPositionVec().add(0, 0, 0);
                        Vector3d desiredMotion = new Vector3d(ownerPos.x - getPosX(), ownerPos.y - getPosY(), ownerPos.z - getPosZ()).normalize().mul(velocity, velocity, velocity);
                        setMotion(desiredMotion);
                    }
                    if (distance < 0.5f)
                    {
                        if (isAlive())
                        {
                            if (stack.getItem() instanceof SpiritItem)
                            {
                                SpiritItem spiritItem = (SpiritItem) stack.getItem();
                                SpiritHelper.harvestSpirit(spiritItem.type.identifier, owner);
                            }
                            else
                            {
                                ItemHandlerHelper.giveItemToPlayer(owner, stack);
                            }
                            remove();
                        }
                    }
                }
            }
        }
        else
        {
            double x = getPosX(), y = getPosY() + yOffset(0) + 0.25f, z = getPosZ();
            if (stack == null)
            {
                stack = dataManager.get(STACK);
            }
            if (stack.getItem() instanceof SpiritItem)
            {
                SpiritItem spiritItem = (SpiritItem) stack.getItem();
                Color color = spiritItem.type.color;

                ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                        .setAlpha(0.1f, 0f)
                        .setLifetime(4)
                        .setScale(0.4f, 0)
                        .setColor(color.brighter(), color.darker())
                        .enableNoClip()
                        .repeat(world, x, y, z, 2);

                ParticleManager.create(MalumParticles.WISP_PARTICLE)
                        .setAlpha(0.1f, 0f)
                        .setLifetime(20)
                        .setSpin(0.1f)
                        .setScale(0.2f, 0)
                        .setColor(color, color.darker())
                        .randomOffset(0.1f)
                        .enableNoClip()
                        .randomVelocity(0.01f, 0.01f)
                        .repeat(world, x, y, z, 1);
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
        super.writeAdditional(compound);
        compound.put("stack", stack.serializeNBT());
        if (ownerUUID != null)
        {
            compound.putUniqueId("ownerUUID", ownerUUID);
        }
        compound.putInt("age", age);
        compound.putFloat("rotation", rotation);
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        if (compound.contains("ownerUUID"))
        {
            ownerUUID = compound.getUniqueId("ownerUUID");
        }
        if (compound.contains("stack"))
        {
            stack = ItemStack.read(compound.getCompound("stack"));
        }
        dataManager.set(STACK, stack);
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
        if (stack == null)
        {
            stack = dataManager.get(STACK);
        }
        return stack.getItem();
    }
}