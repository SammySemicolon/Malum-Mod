package com.sammy.malum.common.entities;

import com.mojang.datafixers.util.Pair;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.ArrayList;
import java.util.UUID;

public class SpiritEssenceEntity extends ProjectileItemEntity
{
    public ArrayList<Pair<String, Integer>> spirits;
    public UUID ownerUUID;
    public PlayerEntity owner;
    public int multiplier;
    public float velocity;
    
    public SpiritEssenceEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
        noClip = true;
        spirits = new ArrayList<>();
    }
    
    @Override
    public float getCollisionBorderSize()
    {
        return 4f;
    }
    
    @Override
    protected void onEntityHit(EntityRayTraceResult p_213868_1_)
    {
        if(isAlive())
        {
            Entity entity = p_213868_1_.getEntity();
            if (entity.equals(owner()))
            {
                SpiritHelper.harvestSpirit(spirits, owner);
                remove();
            }
        }
        super.onEntityHit(p_213868_1_);
    }
    public PlayerEntity owner()
    {
        if (owner == null)
        {
            if (world instanceof ServerWorld)
            {
                owner = (PlayerEntity) ((ServerWorld) world).getEntityByUuid(ownerUUID);
            }
        }
        return owner;
    }
    
    @Override
    public void tick()
    {
        super.tick();
        if (world instanceof ServerWorld)
        {
            velocity+= 0.002f;
            Vector3d ownerPos = owner().getPositionVec().add(0, owner.getHeight() / 2, 0);
            Vector3d desiredMotion = new Vector3d(ownerPos.x - getPosX(), ownerPos.y - getPosY(),  ownerPos.z - getPosZ()).normalize().mul(velocity,velocity,velocity);
            setMotion(desiredMotion);
        }
    }
    
    @Override
    protected Item getDefaultItem()
    {
        return MalumItems.RUIN_PLATING.get();
    }
    
    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (multiplier != 0f)
        {
            compound.putInt("multiplier", multiplier);
        }
        if (velocity != 0f)
        {
            compound.putFloat("velocity", velocity);
        }
        if (ownerUUID != null)
        {
            compound.putUniqueId("ownerUUID", ownerUUID);
        }
        if (spirits != null && !spirits.isEmpty())
        {
            compound.putInt("essences", spirits.size());
            for (int i = 0; i < spirits.size(); i++)
            {
                String identifier = spirits.get(i).getFirst();
                int count = spirits.get(i).getSecond();
                compound.putString("essenceIdentifier" + i, identifier);
                compound.putInt("essenceCount" + i, count);
            }
        }
    }
    
    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("multiplier"))
        {
            multiplier = compound.getInt("multiplier");
        }
        if (compound.contains("velocity"))
        {
            velocity = compound.getFloat("velocity");
        }
        if (compound.contains("ownerUUID"))
        {
            ownerUUID = compound.getUniqueId("ownerUUID");
            owner = owner();
        }
        if (compound.contains("essences"))
        {
            for (int i = 0; i < compound.getInt("essences"); i++)
            {
                String identifier = compound.getString("essenceIdentifier" + i);
                int essenceCount = compound.getInt("essenceCount" + i);
                spirits.add(Pair.of(identifier, essenceCount));
            }
        }
    }
    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
    
    @Override
    public boolean hasNoGravity()
    {
        return true;
    }
}
