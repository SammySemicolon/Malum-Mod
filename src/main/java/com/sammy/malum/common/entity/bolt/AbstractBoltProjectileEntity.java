package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.data.*;
import com.sammy.malum.visual_effects.networked.staff.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

public abstract class AbstractBoltProjectileEntity extends ThrowableItemProjectile {

    protected static final EntityDataAccessor<Boolean> DATA_FADING_AWAY = SynchedEntityData.defineId(AbstractBoltProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_SPAWN_DELAY = SynchedEntityData.defineId(AbstractBoltProjectileEntity.class, EntityDataSerializers.INT);

    public final TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(12);
    public final TrailPointBuilder spinningTrailPointBuilder = TrailPointBuilder.create(20);
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);
    protected float magicDamage;
    public int age;
    public int spawnDelay;

    public boolean fadingAway;

    public AbstractBoltProjectileEntity(EntityType<? extends AbstractBoltProjectileEntity> pEntityType, Level level) {
        super(pEntityType, level);
        noPhysics = false;
    }

    public void setData(Entity owner, float magicDamage, int spawnDelay) {
        setOwner(owner);
        this.magicDamage = magicDamage;
        getEntityData().set(DATA_SPAWN_DELAY, spawnDelay);
        if (!level().isClientSide) {
            if (spawnDelay == 0) {
                playSound(SoundRegistry.STAFF_FIRES.get(), 0.5f, Mth.nextFloat(random, 0.9F, 1.5F));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public abstract void spawnParticles();

    public abstract int getMaxAge();

    public abstract ParticleEffectType getImpactParticleEffect();

    public void onDealDamage(LivingEntity target) {

    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_FADING_AWAY, false);
        this.getEntityData().define(DATA_SPAWN_DELAY, 0);
        super.defineSynchedData();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FADING_AWAY.equals(pKey)) {
            fadingAway = entityData.get(DATA_FADING_AWAY);
            if (fadingAway) {
                age = getMaxAge() - 10;
                setDeltaMovement(getDeltaMovement().scale(0.05f));

            }
        }
        if (DATA_SPAWN_DELAY.equals(pKey)) {
            spawnDelay = entityData.get(DATA_SPAWN_DELAY);
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (magicDamage != 0) {
            compound.putFloat("magicDamage", magicDamage);
        }
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (spawnDelay != 0) {
            compound.putInt("spawnDelay", spawnDelay);
        }
        if (fadingAway) {
            compound.putBoolean("fadingAway", true);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        magicDamage = compound.getFloat("magicDamage");
        age = compound.getInt("age");
        getEntityData().set(DATA_SPAWN_DELAY, compound.getInt("spawnDelay"));
        getEntityData().set(DATA_FADING_AWAY, compound.getBoolean("fadingAway"));
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (fadingAway || spawnDelay > 0) {
            return;
        }

        if (!level().isClientSide) {
            getImpactParticleEffect().createPositionedEffect(level(), new PositionEffectData(position().add(getDeltaMovement().scale(0.25f))), new ColorEffectData(SpiritTypeRegistry.WICKED_SPIRIT), HexBoltImpactParticleEffect.createData(getDeltaMovement().reverse().normalize()));
            playSound(SoundRegistry.STAFF_STRIKES.get(), 0.5f, Mth.nextFloat(random, 0.9F, 1.5F));
            getEntityData().set(DATA_FADING_AWAY, true);
            Vec3 vec3 = pResult.getLocation().subtract(position());
            Vec3 vec31 = vec3.normalize().scale(0.5f);
            this.setPosRaw(getX() - vec31.x, getY() - vec31.y, getZ() - vec31.z);
        }
        super.onHitBlock(pResult);
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return super.canHitEntity(pTarget) && !pTarget.equals(getOwner()) && !(pTarget instanceof AbstractBoltProjectileEntity);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (fadingAway || spawnDelay > 0) {
            return;
        }
        if (getOwner() instanceof LivingEntity staffOwner) {
            Entity target = result.getEntity();
            if (level().isClientSide) {
                return;
            }
            target.invulnerableTime = 0;
            DamageSource source = DamageTypeRegistry.create(level(), DamageTypeRegistry.VOODOO, this, staffOwner);
            boolean success = target.hurt(source, magicDamage);
            if (success && target instanceof LivingEntity livingentity) {
                onDealDamage(livingentity);
                ItemStack staff = getItem();
                ItemHelper.applyEnchantments(staffOwner, livingentity, staff);
                int i = staff.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
                if (i > 0) {
                    livingentity.setSecondsOnFire(i * 4);
                }
                getEntityData().set(DATA_FADING_AWAY, true);
                getImpactParticleEffect().createPositionedEffect(level(), new PositionEffectData(position().add(getDeltaMovement().scale(0.5f))), new ColorEffectData(SpiritTypeRegistry.WICKED_SPIRIT), HexBoltImpactParticleEffect.createData(getDeltaMovement().reverse().normalize()));
                playSound(SoundRegistry.STAFF_STRIKES.get(), 0.75f, Mth.nextFloat(random, 1f, 1.4f));
                setDeltaMovement(getDeltaMovement().scale(0.05f));
            }
        }
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        if (spawnDelay > 0) {
            spawnDelay--;
            if (spawnDelay == 0 && !level().isClientSide) {
                spawnDelay = -1;
                playSound(SoundRegistry.STAFF_FIRES.get(), 0.5f, Mth.nextFloat(random, 0.9F, 1.5F));
            }
            return;
        }
        super.tick();
        Vec3 motion = getDeltaMovement();
        if (!fadingAway) {
            setDeltaMovement(motion.x * 0.96f, (motion.y-0.015f)*0.96f, motion.z * 0.96f);
        }
        float offsetScale = fadingAway ? 0f : 0.3f;
        for (int i = 0; i < 2; i++) {
            float progress = i * 0.5f;
            Vec3 position = getPosition(progress);
            trailPointBuilder.addTrailPoint(position);
            spinningTrailPointBuilder.addTrailPoint(position.add(Math.cos(spinOffset + (age + progress) / 2f) * offsetScale, 0, Math.sin(spinOffset + (age + progress) / 2f) * offsetScale));
        }
        for (int i = 0; i < ((fadingAway || age > getMaxAge() - 20) ? 2 : 1); i++) {
            trailPointBuilder.tickTrailPoints();
            spinningTrailPointBuilder.tickTrailPoints();
        }
        age++;
        if (age >= getMaxAge()) {
            discard();
        }
        if (level().isClientSide && !fadingAway) {
            spawnParticles();
        }
    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float innacuracy) {
        float f = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, innacuracy);
    }

    public float getVisualEffectScalar() {
        float effectScalar = fadingAway ? 1 - (age - getMaxAge() + 10) / 10f : 1;
        if (age < 5) {
            effectScalar = age / 5f;
        }
        return effectScalar;
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public float getPickRadius() {
        return 4f;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }
}