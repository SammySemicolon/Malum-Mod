package com.sammy.malum.common.entity;

import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import com.sammy.malum.visual_effects.networked.altar.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.util.function.*;

public class HexProjectileEntity extends ThrowableItemProjectile {

    public static final int MAX_AGE = 60;
    protected static final EntityDataAccessor<Boolean> DATA_FADING_AWAY = SynchedEntityData.defineId(HexProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final EntityDataAccessor<Integer> DATA_SPAWN_DELAY = SynchedEntityData.defineId(HexProjectileEntity.class, EntityDataSerializers.INT);

    public final TrailPointBuilder spinningTrailPointBuilder = TrailPointBuilder.create(20);
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);
    public final TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(12);
    protected float magicDamage;
    public int age;
    public int spawnDelay;

    public boolean fadingAway;

    public HexProjectileEntity(Level level) {
        super(EntityRegistry.HEX_BOLT.get(), level);
        noPhysics = false;
    }

    public HexProjectileEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.HEX_BOLT.get(), pX, pY, pZ, level);
        noPhysics = false;
    }

    public void setData(Entity owner, float magicDamage, int spawnDelay) {
        setOwner(owner);
        this.magicDamage = magicDamage;
        getEntityData().set(DATA_SPAWN_DELAY, spawnDelay);
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
                age = MAX_AGE - 10;
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
        if (fadingAway || spawnDelay != 0) {
            return;
        }
        if (!level().isClientSide) {
            ParticleEffectTypeRegistry.HEX_BOLT_IMPACT.createPositionedEffect(level(), new PositionEffectData(position().add(getDeltaMovement().scale(0.25f))), new ColorEffectData(SpiritTypeRegistry.WICKED_SPIRIT), HexBoltHitEnemyParticleEffect.createData(getDeltaMovement().reverse().normalize()));
            level().playSound(null, blockPosition(), SoundRegistry.STAVE_STRIKES.get(), getSoundSource(), 0.5f, Mth.nextFloat(random, 0.9F, 1.5F));
        }
        getEntityData().set(DATA_FADING_AWAY, true);
        setDeltaMovement(getDeltaMovement().scale(0.05f));
        super.onHitBlock(pResult);
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return !pTarget.equals(getOwner());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (fadingAway || spawnDelay != 0) {
            return;
        }
        if (getOwner() instanceof LivingEntity staveOwner) {
            Entity target = result.getEntity();
            if (level().isClientSide) {
                return;
            }
            target.invulnerableTime = 0;
            DamageSource source = DamageTypeRegistry.create(level(), DamageTypeRegistry.VOODOO, this, staveOwner);
            boolean success = target.hurt(source, magicDamage);
            if (success && target instanceof LivingEntity livingentity) {
                ItemStack stave = getItem();
                ItemHelper.applyEnchantments(staveOwner, livingentity, stave);
                int i = stave.getEnchantmentLevel(Enchantments.FIRE_ASPECT);
                if (i > 0) {
                    livingentity.setSecondsOnFire(i * 4);
                }
                getEntityData().set(DATA_FADING_AWAY, true);
                ParticleEffectTypeRegistry.HEX_BOLT_IMPACT.createPositionedEffect(level(), new PositionEffectData(position().add(getDeltaMovement().scale(0.5f))), new ColorEffectData(SpiritTypeRegistry.WICKED_SPIRIT), HexBoltHitEnemyParticleEffect.createData(getDeltaMovement().reverse().normalize()));
                level().playSound(null, blockPosition(), SoundRegistry.STAVE_STRIKES.get(), getSoundSource(), 0.75f, Mth.nextFloat(random, 1F, 1.4F));
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
                level().playSound(null, blockPosition(), SoundRegistry.STAVE_FIRES.get(), SoundSource.PLAYERS, 0.5f, Mth.nextFloat(random, 0.9F, 1.5F));
            }
            return;
        }
        super.tick();
        Vec3 motion = getDeltaMovement();
        if (!fadingAway) {
            setDeltaMovement(motion.x * 0.96, (motion.y > 0 ? motion.y * 0.98 : motion.y) - 0.015f, motion.z * 0.96);
        }
        float offsetScale = fadingAway ? 0f : 0.3f;
        for (int i = 0; i < 2; i++) {
            float progress = i * 0.5f;
            Vec3 position = getPosition(progress);
            spinningTrailPointBuilder.addTrailPoint(position.add(Math.cos(spinOffset + (age + progress) / 2f) * offsetScale, 0, Math.sin(spinOffset + (age + progress) / 2f) * offsetScale));
            trailPointBuilder.addTrailPoint(position);
        }
        for (int i = 0; i < (fadingAway ? 2 : 1); i++) {
            spinningTrailPointBuilder.tickTrailPoints();
            trailPointBuilder.tickTrailPoints();
        }
        age++;
        if (age >= MAX_AGE) {
            remove(RemovalReason.DISCARDED);
        }
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            setYRot((float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI)));
            yRotO = getYRot();
            xRotO = getXRot();
        }
        if (level().isClientSide && !fadingAway) {
            float scalar = age > MAX_AGE ? 1f - (age - MAX_AGE + 10) / 10f : 1f;
            Vec3 norm = motion.normalize().scale(0.05f);
            var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), position(), SpiritTypeRegistry.WICKED_SPIRIT);
            lightSpecs.getBuilder().multiplyLifetime(1.25f).setMotion(norm);
            lightSpecs.getBloomBuilder().multiplyLifetime(1.25f).setMotion(norm);
            lightSpecs.spawnParticles();
            final SpinParticleData spinData = SpinParticleData.createRandomDirection(random, RandomHelper.randomBetween(random, 0.25f, 0.5f)).randomSpinOffset(random).build();
            final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.95f));
            DirectionalParticleBuilder.create(ParticleRegistry.SAW)
                    .setTransparencyData(GenericParticleData.create(0.9f * scalar, 0.4f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                    .setSpinData(spinData)
                    .setScaleData(GenericParticleData.create(0.4f * scalar, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createMainColorData().build())
                    .setLifetime(Math.min(6+age * 3, 30))
                    .setDirection(getDeltaMovement().normalize())
                    .enableNoClip()
                    .enableForcedSpawn()
                    .disableCull()
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.RANDOM_SPRITE)
                    .addTickActor(behavior)
                    .spawn(level(), position().x, position().y, position().z)
                    .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                    .spawn(level(), position().x, position().y, position().z);

        }
    }

    public void shootFromRotation(Entity shooter, float rotationPitch, float rotationYaw, float pitchOffset, float velocity, float innacuracy) {
        float f = -Mth.sin(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        float f1 = -Mth.sin((rotationPitch + pitchOffset) * ((float) Math.PI / 180F));
        float f2 = Mth.cos(rotationYaw * ((float) Math.PI / 180F)) * Mth.cos(rotationPitch * ((float) Math.PI / 180F));
        this.shoot(f, f1, f2, velocity, innacuracy);
        Vec3 vec3 = shooter.getDeltaMovement();
        this.setDeltaMovement(this.getDeltaMovement().add(vec3.x, 0, vec3.z));
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
    protected Item getDefaultItem() {
        return ItemRegistry.SOUL_STAINED_STEEL_STAVE.get();
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