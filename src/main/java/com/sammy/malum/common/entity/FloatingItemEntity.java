package com.sammy.malum.common.entity;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.items.ItemRegistry;
import com.sammy.malum.core.systems.spirit.ISpiritEntityGlow;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.awt.*;

public abstract class FloatingItemEntity extends ProjectileItemEntity
{
    public int maxAge = 2400;
    public int age;
    public int moveTime;
    public int range = 3;
    public float windUp;
    public final float hoverStart;

    public FloatingItemEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
        noClip = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }
    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        compound.putInt("age", age);
        compound.putInt("moveTime", moveTime);
        compound.putInt("range", range);
        compound.putFloat("windUp", windUp);
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
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
        if (MalumHelper.areWeOnServer(world))
        {
            move();
            if (age > maxAge)
            {
                remove();
            }
        }
        else
        {
            double x = getPosX(), y = getPosY() + getYOffset(0) + 0.25f, z = getPosZ();
            ItemStack stack = getItem();
            if (stack.getItem() instanceof ISpiritEntityGlow)
            {
                ISpiritEntityGlow entityGlow = (ISpiritEntityGlow) stack.getItem();
                Color color = entityGlow.getColor();
                SpiritHelper.spawnSpiritParticles(world, x,y,z, color);
            }
        }
    }
    public void move()
    {
    }
    public float getYOffset(float partialTicks)
    {
        return MathHelper.sin(((float) age + partialTicks) / 10.0F + hoverStart) * 0.1F + 0.1F;
    }

    public float getRotation(float partialTicks) {
        return ((float)age + partialTicks) / 10.0F + this.hoverStart;
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
        return ItemRegistry.SACRED_SPIRIT.get();
    }
}