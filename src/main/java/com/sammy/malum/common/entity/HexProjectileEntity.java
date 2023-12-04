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

    protected static final EntityDataAccessor<Boolean> DATA_FADING_AWAY = SynchedEntityData.defineId(HexProjectileEntity.class, EntityDataSerializers.BOOLEAN);

    public final TrailPointBuilder spinningTrailPointBuilder = TrailPointBuilder.create(20);
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);
    public final TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(12);
    protected float damage;
    protected float magicDamage;
    public int age;
    public int soundCooldown = 20 + random.nextInt(100);

    public boolean fadingAway;

    public HexProjectileEntity(Level level) {
        super(EntityRegistry.HEX_BOLT.get(), level);
        noPhysics = false;
    }

    public HexProjectileEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.HEX_BOLT.get(), pX, pY, pZ, level);
        noPhysics = false;
    }

    public void setData(Entity owner, float magicDamage) {
        setOwner(owner);
        this.magicDamage = magicDamage;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_FADING_AWAY, false);
        super.defineSynchedData();
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_FADING_AWAY.equals(pKey)) {
            fadingAway = entityData.get(DATA_FADING_AWAY);
            age = 70;
        }
        super.onSyncedDataUpdated(pKey);
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (damage != 0) {
            compound.putFloat("damage", damage);
        }
        if (magicDamage != 0) {
            compound.putFloat("magicDamage", magicDamage);
        }
        if (age != 0) {
            compound.putInt("age", age);
        }
        if (fadingAway) {
            compound.putBoolean("fadingAway", true);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        damage = compound.getFloat("damage");
        magicDamage = compound.getFloat("magicDamage");
        age = compound.getInt("age");
        getEntityData().set(DATA_FADING_AWAY, compound.getBoolean("fadingAway"));
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
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
        if (fadingAway) {
            return;
        }
        if (getOwner() instanceof LivingEntity staveOwner) {
            Entity target = result.getEntity();
            if (level().isClientSide) {
                return;
            }
            DamageSource source = target.damageSources().mobProjectile(this, staveOwner);
            boolean success = target.hurt(source, damage);
            if (success && target instanceof LivingEntity livingentity) {
                ItemStack stave = getItem();
                ItemHelper.applyEnchantments(staveOwner, livingentity, stave);
                int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FIRE_ASPECT, stave);
                if (i > 0) {
                    livingentity.setSecondsOnFire(i * 4);
                }
                if (magicDamage > 0) {
                    if (livingentity.isAlive()) {
                        livingentity.invulnerableTime = 0;
                        livingentity.hurt(DamageTypeRegistry.create(level(), DamageTypeRegistry.VOODOO, this, staveOwner), magicDamage);
                    }
                }
                getEntityData().set(DATA_FADING_AWAY, true);
                ParticleEffectTypeRegistry.HEX_BOLT_IMPACT.createPositionedEffect(level(), new PositionEffectData(position().add(getDeltaMovement().scale(0.5f))), new ColorEffectData(SpiritTypeRegistry.WICKED_SPIRIT), HexBoltHitEnemyParticleEffect.createData(getDeltaMovement().reverse().normalize()));
                target.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.SCYTHE_CUT.get(), target.getSoundSource(), 1.0F, 0.9f + target.level().random.nextFloat() * 0.2f);
                setDeltaMovement(getDeltaMovement().scale(0.05f));
            }
        }
        super.onHitEntity(result);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 motion = getDeltaMovement();
        if (!fadingAway) {
            setDeltaMovement(motion.x * 0.96, (motion.y > 0 ? motion.y * 0.98 : motion.y) - 0.015f, motion.z * 0.96);
        }
        if (soundCooldown-- == 0) {
            if (random.nextFloat() < 0.6f) {
                level().playSound(null, blockPosition(), SoundRegistry.ARCANE_WHISPERS.get(), SoundSource.NEUTRAL, 0.3f, Mth.nextFloat(random, 1.1f, 2f));
            }
            soundCooldown = random.nextInt(40) + 40;
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
        if (age >= 80) {
            remove(RemovalReason.DISCARDED);
        }
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            setYRot((float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI)));
            yRotO = getYRot();
            xRotO = getXRot();
        }
        if (level().isClientSide && !fadingAway) {
            float scalar = age > 70 ? 1f - (age - 70) / 10f : 1f;
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
                    .setScaleData(GenericParticleData.create(0.5f * scalar, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setColorData(SpiritTypeRegistry.WICKED_SPIRIT.createMainColorData().build())
                    .setLifetime(Math.min(age * 3, 30))
                    .setDirection(getDeltaMovement().normalize())
                    .enableNoClip()
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