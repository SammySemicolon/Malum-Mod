package com.sammy.malum.common.entity.boomerang;

import com.sammy.malum.core.helper.ItemHelper;
import com.sammy.malum.core.registry.enchantment.MalumEnchantments;
import com.sammy.malum.core.registry.EntityRegistry;
import com.sammy.malum.core.registry.SoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.UUID;

public class ScytheBoomerangEntity extends ThrowableItemProjectile
{
    public static final EntityDataAccessor<ItemStack> SCYTHE = SynchedEntityData.defineId(ScytheBoomerangEntity.class, EntityDataSerializers.ITEM_STACK);
    
    public ItemStack scythe;
    public UUID ownerUUID;
    public Player owner;
    
    public int slot;
    public float damage;
    public int age;
    public int returnAge=8;
    public boolean returning;
    
    public ScytheBoomerangEntity(Level level)
    {
        super(EntityRegistry.SCYTHE_BOOMERANG.get(), level);
        noPhysics = false;
    }
    public Player owner()
    {
        if (owner == null)
        {
            if (!level.isClientSide)
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
    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float innacuracy)
    {
        float f = -Mth.sin(rotationYaw * ((float)Math.PI / 180F)) * Mth.cos(rotationPitch * ((float)Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * ((float)Math.PI / 180F));
        float f2 = Mth.cos(rotationYaw * ((float)Math.PI / 180F)) * Mth.cos(rotationPitch * ((float)Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, innacuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0, vec3.z));
    }
    
    @Override
    protected void onHitBlock(BlockHitResult result)
    {
        super.onHitBlock(result);
        returning = true;
    }
    
    @Override
    protected void onHitEntity(EntityHitResult p_213868_1_)
    {
        DamageSource source = DamageSource.indirectMobAttack(this, owner());
        Entity entity = p_213868_1_.getEntity();
        if (level.isClientSide)
        {
            return;
        }
        if (entity.equals(owner))
        {
            return;
        }
        boolean success = entity.hurt(source, damage);
        if (success)
        {
            if (!level.isClientSide)
            {
                if (entity instanceof LivingEntity livingentity)
                {
                    scythe.hurtAndBreak(1, owner(), (e) -> remove(RemovalReason.KILLED));
                    ItemHelper.applyEnchantments(owner, livingentity, scythe);
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
        if (level.isClientSide)
        {
            if (!isInWater())
            {
                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, getItem()) > 0)
                {
                    level.addParticle(ParticleTypes.FLAME, getRandomX(1), getRandomY(), getRandomZ(1), 0, 0, 0);
                }
            }
        }

        if (!level.isClientSide)
        {
            Player playerEntity = owner();
            if (playerEntity == null || !playerEntity.isAlive())
            {
                ItemEntity entityitem = new ItemEntity(level, getX(), getY() + 0.5, getZ(), scythe);
                entityitem.setPickUpDelay(40);
                entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));
                level.addFreshEntity(entityitem);
                remove(RemovalReason.DISCARDED);
                return;
            }
            if (age % 3 == 0)
            {
                level.playSound(null, blockPosition(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 1, 1.25f);
            }
            if (this.xRotO == 0.0F && this.yRotO == 0.0F)
            {
                Vec3 vector3d = getDeltaMovement();
//                float f = Mth.sqrt(horizontalMag(vector3d));
                setYRot((float) (Mth.atan2(vector3d.x, vector3d.z) * (double) (180F / (float) Math.PI)));
//                setXRot((float) (Mth.atan2(vector3d.y, f) * (double) (180F / (float) Math.PI)));
                yRotO = getYRot();
                xRotO = getXRot();
            }
            if (age > returnAge)
            {
                returning = true;
            }
            if (returning)
            {
                noPhysics = true;
                Vec3 ownerPos = playerEntity.position().add(0, 1, 0);
                Vec3 motion = ownerPos.subtract(position());
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
                        if (!playerEntity.getAbilities().instabuild)
                        {
                            int cooldown = 100 - 25 * (EnchantmentHelper.getItemEnchantmentLevel(MalumEnchantments.REBOUND.get(),scythe) - 1);
                            playerEntity.getCooldowns().addCooldown(scythe.getItem(), cooldown);
                        }
                        remove(RemovalReason.DISCARDED);
                    }
                }
            }
        }
    }
    
    @Override
    public void addAdditionalSaveData(CompoundTag compound)
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
    public void readAdditionalSaveData(CompoundTag compound)
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