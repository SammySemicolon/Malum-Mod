package com.sammy.malum.common.entities;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.enchantments.MalumEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.UUID;

public class ScytheBoomerangEntity extends ProjectileItemEntity
{
    public static final DataParameter<ItemStack> SCYTHE = EntityDataManager.createKey(ScytheBoomerangEntity.class, DataSerializers.ITEMSTACK);
    
    public ItemStack scythe;
    public UUID ownerUUID;
    public PlayerEntity owner;
    
    public int slot;
    public float damage;
    public int age;
    public boolean comingBack;
    
    public ScytheBoomerangEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn)
    {
        super(type, worldIn);
        noClip = false;
    }
    public PlayerEntity owner()
    {
        if (owner == null)
        {
            if (MalumHelper.areWeOnServer(world))
            {
                owner = (PlayerEntity) ((ServerWorld) world).getEntityByUuid(ownerUUID);
            }
        }
        return owner;
    }
    
    public void setData(float damage, UUID ownerUUID, int slot, ItemStack scythe)
    {
        this.damage = damage;
        this.ownerUUID = ownerUUID;
        this.slot = slot;
        this.scythe = scythe;
    }
    
    public void shoot(Entity playerEntity, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
    {
        float f = -MathHelper.sin(rotationYawIn * ((float) Math.PI / 180F)) * MathHelper.cos(rotationPitchIn * ((float) Math.PI / 180F));
        float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = MathHelper.cos(rotationYawIn * ((float) Math.PI / 180F)) * MathHelper.cos(rotationPitchIn * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, inaccuracy);
        Vector3d Vector3d = playerEntity.getMotion();
        this.setMotion(this.getMotion().add(Vector3d.x, playerEntity.isOnGround() ? 0.0D : Vector3d.y, Vector3d.z));
    }
    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        Vector3d motion = (new Vector3d(x, y, z)).normalize().add(this.rand.nextGaussian() * 0.0075F * inaccuracy, this.rand.nextGaussian() * 0.0075F * inaccuracy, this.rand.nextGaussian() * 0.0075F * inaccuracy).scale(velocity).mul(1,0.25f,1);
        this.setMotion(motion);
        float f = MathHelper.sqrt(horizontalMag(motion));
        this.rotationYaw = (float) (MathHelper.atan2(motion.x, motion.z) * (180F / (float) Math.PI));
        this.rotationPitch = (float) (MathHelper.atan2(motion.y, f) * (180F / (float) Math.PI));
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }
    
    @Override
    protected void func_230299_a_(BlockRayTraceResult result)
    {
        super.func_230299_a_(result);
        comingBack = true;
    }
    
    @Override
    protected void onEntityHit(EntityRayTraceResult p_213868_1_)
    {
        DamageSource source = DamageSource.causeIndirectDamage(this, owner);
        Entity entity = p_213868_1_.getEntity();
        
        if (MalumHelper.areWeOnServer(world))
        {
            if (entity instanceof LivingEntity)
            {
                LivingEntity livingentity = (LivingEntity) entity;
                EnchantmentHelper.applyThornEnchantments(livingentity, owner());
                EnchantmentHelper.applyArthropodEnchantments(owner, livingentity);
                if (EnchantmentHelper.getFireAspectModifier(owner) > 0)
                {
                    entity.setFire(EnchantmentHelper.getFireAspectModifier(owner));
                }
            }
        }
        entity.world.playSound(null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), MalumSounds.SCYTHE_STRIKE, entity.getSoundCategory(), 1.0F, 0.9f + entity.world.rand.nextFloat() * 0.2f);
        entity.attackEntityFrom(source, damage);
        super.onEntityHit(p_213868_1_);
    }
    @Override
    public void tick()
    {
        super.tick();
        age++;
        if (MalumHelper.areWeOnServer(world))
        {
            if (age % 3 == 0)
            {
                world.playSound(null, getPosition(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1.25f);
            }
            if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
            {
                Vector3d vector3d = getMotion();
                float f = MathHelper.sqrt(horizontalMag(vector3d));
                rotationYaw = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
                rotationPitch = (float) (MathHelper.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI));
                prevRotationYaw = rotationYaw;
                prevRotationPitch = rotationPitch;
            }
            PlayerEntity playerEntity = owner();
            if (playerEntity == null)
            {
                return;
            }
            if (age > 8)
            {
                comingBack = true;
            }
            if (comingBack)
            {
                noClip = true;
                Vector3d ownerPos = playerEntity.getPositionVec().add(0, 1, 0);
                Vector3d motion = ownerPos.subtract(getPositionVec());
                setMotion(motion.normalize().scale(0.75f));
            }
            float distance = getDistance(playerEntity);
            if (age > 10)
            {
                if (distance < 3f)
                {
                    if (isAlive())
                    {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, scythe, slot);
                        if (!playerEntity.abilities.isCreativeMode)
                        {
                            int cooldown = 200 - 75 * (EnchantmentHelper.getEnchantmentLevel(MalumEnchantments.REBOUND.get(),scythe) - 1);
                            playerEntity.getCooldownTracker().setCooldown(scythe.getItem(), cooldown);
                        }
                        remove();
                    }
                }
            }
        }
    }
    
    @Override
    public void writeAdditional(CompoundNBT compound)
    {
        super.writeAdditional(compound);
        if (ownerUUID != null)
        {
            compound.putUniqueId("ownerUUID", ownerUUID);
        }
        compound.putInt("age", age);
        compound.putInt("slot", slot);
        scythe.write(compound);
        compound.putBoolean("comingBack", comingBack);
        compound.putFloat("damage", damage);
    }
    
    @Override
    public void readAdditional(CompoundNBT compound)
    {
        super.readAdditional(compound);
        if (compound.contains("ownerUUID"))
        {
            ownerUUID = compound.getUniqueId("ownerUUID");
            owner = owner();
        }
        scythe = ItemStack.read(compound);
        dataManager.set(SCYTHE, scythe);
        age = compound.getInt("age");
        slot = compound.getInt("slot");
        comingBack = compound.getBoolean("comingBack");
        damage = compound.getFloat("damage");
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
    protected void registerData()
    {
        super.registerData();
        dataManager.register(SCYTHE, ItemStack.EMPTY);
    }
    
    @Override
    protected Item getDefaultItem()
    {
        if (scythe == null)
        {
            scythe = dataManager.get(SCYTHE);
        }
        return scythe.getItem();
    }
    
    @Override
    public ItemStack getItem()
    {
        if (scythe == null)
        {
            scythe = dataManager.get(SCYTHE);
        }
        return scythe;
    }
    
    @Override
    public boolean isImmuneToFire()
    {
        return true;
    }
    
    @Override
    public boolean isImmuneToExplosions()
    {
        return true;
    }
}