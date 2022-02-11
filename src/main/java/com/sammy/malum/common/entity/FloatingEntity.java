package com.sammy.malum.common.entity;

import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;

public abstract class FloatingEntity extends ThrowableProjectile
{
    protected static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(FloatingEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_END_COLOR = SynchedEntityData.defineId(FloatingEntity.class, EntityDataSerializers.INT);
    public Color color = SpiritTypeRegistry.SACRED_SPIRIT_COLOR;
    public Color endColor = SpiritTypeRegistry.SACRED_SPIRIT.endColor;
    public int maxAge;
    public int age;
    public int moveTime;
    public int speed = 3;
    public float windUp;
    public final float hoverStart;

    public FloatingEntity(EntityType<? extends FloatingEntity> type, Level level)
    {
        super(type, level);
        noPhysics = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_COLOR, SpiritTypeRegistry.SACRED_SPIRIT_COLOR.getRGB());
        this.getEntityData().define(DATA_END_COLOR, SpiritTypeRegistry.SACRED_SPIRIT.endColor.getRGB());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putInt("age", age);
        compound.putInt("moveTime", moveTime);
        compound.putInt("range", speed);
        compound.putFloat("windUp", windUp);
        compound.putInt("red", color.getRed());
        compound.putInt("green", color.getGreen());
        compound.putInt("blue", color.getBlue());
        compound.putInt("endRed", endColor.getRed());
        compound.putInt("endGreen", endColor.getGreen());
        compound.putInt("endBlue", endColor.getBlue());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        age = compound.getInt("age");
        moveTime = compound.getInt("moveTime");
        int range = compound.getInt("range");
        if (range > 0)
        {
            this.speed = range;
        }
        windUp = compound.getFloat("windUp");
        color = new Color(compound.getInt("red"),compound.getInt("green"),compound.getInt("blue"));
        endColor = new Color(compound.getInt("endRed"),compound.getInt("endGreen"),compound.getInt("endBlue"));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_COLOR.equals(pKey))
        {
            color = new Color(entityData.get(DATA_COLOR));
        }
        if (DATA_END_COLOR.equals(pKey))
        {
            endColor = new Color(entityData.get(DATA_END_COLOR));
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void tick() {
        super.tick();
        age++;
        if (windUp < 1f) {
            windUp += 0.02f;
        }
        if (age > maxAge) {
            remove(RemovalReason.KILLED);
        }
        if (level.isClientSide) {
            double x = getX(), y = getY() + getYOffset(0) + 0.25f, z = getZ();
            spawnParticles(x, y, z);
        }
        else
        {
            move();
        }
    }
    public void spawnParticles(double x, double y, double z)
    {

    }
    public void move()
    {
    }
    public float getYOffset(float partialTicks)
    {
        return Mth.sin(((float) age + partialTicks) / 20.0F + hoverStart) * 0.1F + 0.1F;
    }

    public float getRotation(float partialTicks) {
        return ((float)age + partialTicks) / 20.0F + this.hoverStart;
    }

    @Override
    public Packet<?> getAddEntityPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isNoGravity()
    {
        return true;
    }

}