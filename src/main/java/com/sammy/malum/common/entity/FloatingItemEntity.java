package com.sammy.malum.common.entity;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_systems.spirit.ISpiritEntityGlow;
import com.sammy.malum.core.mod_systems.spirit.SpiritHelper;
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
    public int age;
    public int moving;
    public float rotation;
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
        compound.putInt("moving", moving);
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        age = compound.getInt("age");
        moving = compound.getInt("moving");
    }

    @Override
    public void tick()
    {
        super.tick();
        age++;
        if (MalumHelper.areWeOnServer(world))
        {
            move();
            if (age > 2400)
            {
                remove();
            }
        }
        else
        {
            float rotationSpeed = 1.75f;
            float extraSpeed = 5.25f;
            float rotationTime = 160;
            rotation += age > rotationTime ? rotationSpeed : rotationSpeed + ((rotationTime - age) / rotationTime) * extraSpeed;

            double x = getPosX(), y = getPosY() + yOffset(0) + 0.25f, z = getPosZ();
            ItemStack stack = getItem();
            if (stack.getItem() instanceof ISpiritEntityGlow)
            {
                ISpiritEntityGlow entityGlow = (ISpiritEntityGlow) stack.getItem();
                Color color = entityGlow.getColor();
                SpiritHelper.spiritParticles(world, x,y,z, color);
            }
        }
    }
    public void move()
    {
        setMotion(getMotion().mul(0.95f, 0.75f, 0.95f));
    }
    public float yOffset(float partialTicks)
    {
        return MathHelper.sin(((float) age + partialTicks) / 10.0F + hoverStart) * 0.1F + 0.1F;
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
        return MalumItems.SACRED_SPIRIT.get();
    }
}