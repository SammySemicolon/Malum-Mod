package com.sammy.malum.common.entity.boomerang;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.misc.EntityRegistry;
import com.sammy.malum.core.registry.misc.SoundRegistry;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.Level.Level;
import net.minecraft.Level.server.ServerLevel;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.UUID;

public class ScytheBoomerangEntity extends ProjectileItemEntity
{
    public static final DataParameter<ItemStack> SCYTHE = EntityDataManager.defineId(ScytheBoomerangEntity.class, DataSerializers.ITEM_STACK);
    
    public ItemStack scythe;
    public UUID ownerUUID;
    public Player owner;
    
    public int slot;
    public float damage;
    public int age;
    public int returnAge=8;
    public boolean returning;
    
    public ScytheBoomerangEntity(Level LevelIn)
    {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), LevelIn);
        noPhysics = false;
    }
    public Player owner()
    {
        if (owner == null)
        {
            if (MalumHelper.areWeOnServer(level))
            {
                owner = (Player) ((ServerLevel) level).getEntity(ownerUUID);
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
        Vector3d Vector3d = playerEntity.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(Vector3d.x, playerEntity.isOnGround() ? 0.0D : Vector3d.y, Vector3d.z));
    }
    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy)
    {
        Vector3d motion = (new Vector3d(x, y, z)).normalize().add(this.random.nextGaussian() * 0.0075F * inaccuracy, this.random.nextGaussian() * 0.0075F * inaccuracy, this.random.nextGaussian() * 0.0075F * inaccuracy).scale(velocity).multiply(1,1,1);
        this.setDeltaMovement(motion);
        float f = MathHelper.sqrt(getHorizontalDistanceSqr(motion));
        this.yRot = (float) (MathHelper.atan2(motion.x, motion.z) * (180F / (float) Math.PI));
        this.xRot = (float) (MathHelper.atan2(motion.y, f) * (180F / (float) Math.PI));
        this.yRotO = this.yRot;
        this.xRotO = this.xRot;
    }
    
    @Override
    protected void onHitBlock(BlockRayTraceResult result)
    {
        super.onHitBlock(result);
        returning = true;
    }
    
    @Override
    protected void onHitEntity(EntityRayTraceResult p_213868_1_)
    {
        DamageSource source = DamageSource.indirectMobAttack(this, owner());
        Entity entity = p_213868_1_.getEntity();
        if (MalumHelper.areWeOnClient(level))
        {
            return;
        }
        if (entity.equals(owner))
        {
            super.onHitEntity(p_213868_1_);
            return;
        }
        boolean success = entity.hurt(source, damage);
        if (success)
        {
            if (MalumHelper.areWeOnServer(level))
            {
                if (entity instanceof LivingEntity)
                {
                    LivingEntity livingentity = (LivingEntity) entity;
                    scythe.hurtAndBreak(1, owner(), (e) -> remove());
                    MalumHelper.applyEnchantments(owner, livingentity, scythe);
                    int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, scythe);
                    if (i > 0) {
                        livingentity.setSecondsOnFire(i * 4);
                    }
                }
            }
            returnAge+=4;
            entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundRegistry.SCYTHE_CUT, entity.getSoundSource(), 1.0F, 0.9f + entity.level.random.nextFloat() * 0.2f);
        }
        super.onHitEntity(p_213868_1_);
    }

    @Override
    public void tick()
    {
        super.tick();
        age++;
        if (MalumHelper.areWeOnClient(level))
        {
            if (!isInWater())
            {
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, getItem()) > 0)
                {
                    level.addParticle(ParticleTypes.FLAME, getRandomX(1), getRandomY(), getRandomZ(1), 0, 0, 0);
                }
            }
        }

        if (MalumHelper.areWeOnServer(level))
        {
            Player playerEntity = owner();
            if (playerEntity == null)
            {
                return;
            }
            if (age % 3 == 0)
            {
                level.playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundCategory.PLAYERS, 1, 1.25f);
            }
            if (this.xRotO == 0.0F && this.yRotO == 0.0F)
            {
                Vector3d vector3d = getDeltaMovement();
                float f = MathHelper.sqrt(getHorizontalDistanceSqr(vector3d));
                yRot = (float) (MathHelper.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI));
                xRot = (float) (MathHelper.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI));
                yRotO = yRot;
                xRotO = xRot;
            }
            if (age > returnAge)
            {
                returning = true;
            }
            if (returning)
            {
                noPhysics = true;
                Vector3d ownerPos = playerEntity.position().add(0, 1, 0);
                Vector3d motion = ownerPos.subtract(position());
                setDeltaMovement(motion.normalize().scale(0.75f));
            }
            float distance = distanceTo(playerEntity);
            if (age > 8)
            {
                if (distance < 3f)
                {
                    if (isAlive())
                    {
                        ItemHandlerHelper.giveItemToPlayer(playerEntity, scythe, slot);
                        if (!playerEntity.abilities.instabuild)
                        {
                            int cooldown = 100 - 80 * (EnchantmentHelper.getItemEnchantmentLevel(MalumEnchantments.REBOUND.get(),scythe) - 1);
                            playerEntity.getCooldowns().addCooldown(scythe.getItem(), cooldown);
                        }
                        remove();
                    }
                }
            }
        }
    }
    
    @Override
    public void addAdditionalSaveData(CompoundNBT compound)
    {
        super.addAdditionalSaveData(compound);
        compound.put("scythe", scythe.serializeNBT());
        if (ownerUUID != null)
        {
            compound.putUUID("ownerUUID", ownerUUID);
        }
        compound.putInt("slot", slot);
        compound.putFloat("damage", damage);
        compound.putInt("age", age);
        compound.putBoolean("returning", returning);
        compound.putInt("returnAge", returnAge);
    }
    
    @Override
    public void readAdditionalSaveData(CompoundNBT compound)
    {
        super.readAdditionalSaveData(compound);
    
        if (compound.contains("scythe"))
        {
            scythe = ItemStack.of(compound.getCompound("scythe"));
        }
        entityData.set(SCYTHE, scythe);
        
        if (compound.contains("ownerUUID"))
        {
            ownerUUID = compound.getUUID("ownerUUID");
            owner = owner();
        }
        slot = compound.getInt("slot");
        damage = compound.getFloat("damage");
        age = compound.getInt("age");
        returning = compound.getBoolean("returning");
        returnAge = compound.getInt("returnAge");
    }
    
    @Override
    public IPacket<?> getAddEntityPacket()
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
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        entityData.define(SCYTHE, ItemStack.EMPTY);
    }
    
    @Override
    protected Item getDefaultItem()
    {
        if (scythe == null)
        {
            scythe = entityData.get(SCYTHE);
        }
        return scythe.getItem();
    }
    
    @Override
    public ItemStack getItem()
    {
        if (scythe == null)
        {
            scythe = entityData.get(SCYTHE);
        }
        return scythe;
    }
    
    @Override
    public boolean fireImmune()
    {
        return true;
    }
    
    @Override
    public boolean ignoreExplosion()
    {
        return true;
    }
}