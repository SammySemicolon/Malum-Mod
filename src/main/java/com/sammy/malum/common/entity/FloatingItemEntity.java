package com.sammy.malum.common.entity;

import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.systems.spirit.ISpiritEntityGlow;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import java.awt.*;

public abstract class FloatingItemEntity extends ThrowableItemProjectile
{
    public int maxAge = 2400;
    public int age;
    public int moveTime;
    public int range = 3;
    public float windUp;
    public final float hoverStart;

    public FloatingItemEntity(EntityType<? extends ThrowableItemProjectile> type, Level level)
    {
        super(type, level);
        noPhysics = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putInt("age", age);
        compound.putInt("moveTime", moveTime);
        compound.putInt("range", range);
        compound.putFloat("windUp", windUp);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        age = compound.getInt("age");
        moveTime = compound.getInt("moveTime");
        range = compound.getInt("range");
        windUp = compound.getFloat("windUp");
    }

    @Override
    public void tick()
    {
        super.tick();
        age++;
        if (windUp < 1f)
        {
            windUp += 0.02f;
        }
        if (!level.isClientSide)
        {
            move();
            if (age > maxAge)
            {
                remove(RemovalReason.DISCARDED);
            }
        }
        else
        {
            double x = getX(), y = getY() + getYOffset(0) + 0.25f, z = getZ();
            ItemStack stack = getItem();
            if (stack.getItem() instanceof ISpiritEntityGlow)
            {
                ISpiritEntityGlow entityGlow = (ISpiritEntityGlow) stack.getItem();
                Color color = entityGlow.getColor();
                SpiritHelper.spawnSpiritParticles(level, x,y,z, color);
            }
        }
    }
    public void move()
    {
    }
    public float getYOffset(float partialTicks)
    {
        return Mth.sin(((float) age + partialTicks) / 10.0F + hoverStart) * 0.1F + 0.1F;
    }

    public float getRotation(float partialTicks) {
        return ((float)age + partialTicks) / 10.0F + this.hoverStart;
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

    @Override
    public float getPickRadius()
    {
        return 4f;
    }

    @Override
    protected Item getDefaultItem()
    {
        return ItemRegistry.SACRED_SPIRIT.get();
    }
}