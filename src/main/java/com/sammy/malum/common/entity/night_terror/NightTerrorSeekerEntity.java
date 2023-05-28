package com.sammy.malum.common.entity.night_terror;

import com.sammy.malum.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.*;
import net.minecraft.util.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.world.*;

import java.awt.*;
import java.util.List;
import java.util.*;

import static net.minecraft.util.Mth.*;

public class NightTerrorSeekerEntity extends ThrowableProjectile {


    public static final Color NIGHT_TERROR_DARK = new Color(37, 25, 56);
    public static final Color NIGHT_TERROR_PURPLE = new Color(179, 52, 208);

    protected float magicDamage;
    public int age;
    public int fadeoutStart;
    public int fadeoutDuration;
    public final ArrayList<EntityHelper.PastPosition> pastPositions = new ArrayList<>(); // *screaming*

    public NightTerrorSeekerEntity(Level level) {
        super(EntityRegistry.NIGHT_TERROR.get(), level);
        noPhysics = false;
        fadeoutStart = Mth.nextInt(level.random, 4, 8);
        fadeoutDuration = Mth.nextInt(level.random, 18, 24);
    }

    public NightTerrorSeekerEntity(Level level, double pX, double pY, double pZ) {
        super(EntityRegistry.NIGHT_TERROR.get(), pX, pY, pZ, level);
        noPhysics = false;
        fadeoutStart = Mth.nextInt(level.random, 6, 10);
        fadeoutDuration = Mth.nextInt(level.random, 18, 24);
    }

    public void setData(Entity owner, float magicDamage) {
        setOwner(owner);
        this.magicDamage = magicDamage;
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
        if (fadeoutStart != 0) {
            compound.putInt("fadeoutStart", fadeoutStart);
        }
        if (fadeoutDuration != 0) {
            compound.putInt("fadeoutDuration", fadeoutDuration);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        magicDamage = compound.getFloat("magicDamage");
        age = compound.getInt("age");
//        fadeoutStart = compound.getInt("fadeoutStart");
//        fadeoutDuration = compound.getInt("fadeoutDuration");
        setDeltaMovement(0, 0.4f, 0);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (age < fadeoutStart) {
            age = fadeoutStart;
        }
    }

    @Override
    protected boolean canHitEntity(Entity pTarget) {
        return !pTarget.equals(getOwner());
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (getOwner() instanceof LivingEntity scytheOwner) {
            Entity target = result.getEntity();
            if (level.isClientSide) {
                return;
            }
            DamageSource source = DamageSourceRegistry.causeVoodooDamage(scytheOwner);
            target.hurt(source, magicDamage);
            if (age < fadeoutStart) {
                fadeoutStart += 4;
            }
            target.level.playSound(null, target.getX(), target.getY(), target.getZ(), SoundRegistry.SCYTHE_CUT.get(), target.getSoundSource(), 1.0F, 0.9f + target.level.random.nextFloat() * 0.2f);
        }
        super.onHitEntity(result);
    }
    @Override
    public void tick() {
        super.tick();
        trackPastPositions();
        age++;
        if (level.isClientSide) {
            ClientOnly.spawnParticles(this);
        }
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            Vec3 motion = getDeltaMovement();
            setYRot((float) (Mth.atan2(motion.x, motion.z) * (double) (180F / (float) Math.PI)));
            yRotO = getYRot();
            xRotO = getXRot();
        }
        if (!level.isClientSide) {
            if (age > fadeoutStart) {
                Vec3 motion = getDeltaMovement().scale(0.92f);
                setDeltaMovement(motion);
                if (age - fadeoutStart > fadeoutDuration) {
                    discard();
                }
            }
        }
    }

    public void trackPastPositions() {
        EntityHelper.trackPastPositions(pastPositions, position(), 0.01f);
        removeOldPositions(pastPositions);
    }

    public void removeOldPositions(List<EntityHelper.PastPosition> pastPositions) {
        int amount = pastPositions.size() - 1;
        List<EntityHelper.PastPosition> toRemove = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            EntityHelper.PastPosition excess = pastPositions.get(i);
            if (excess.time > 12) {
                toRemove.add(excess);
            }
        }
        pastPositions.removeAll(toRemove);
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
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {

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

    public static class ClientOnly {
        public static void spawnParticles(NightTerrorSeekerEntity nightTerrorSeekerEntity) {
            double ox = nightTerrorSeekerEntity.xOld, oy = nightTerrorSeekerEntity.yOld + 0.25f, oz = nightTerrorSeekerEntity.zOld;
            double x = nightTerrorSeekerEntity.getX(), y = nightTerrorSeekerEntity.getY() + 0.25f, z = nightTerrorSeekerEntity.getZ();
            Vec3 motion = nightTerrorSeekerEntity.getDeltaMovement();
            Vec3 norm = motion.normalize().scale(0.1f);
            float extraAlpha = (float) motion.length();
            float cycles = 3;
            Color firstColor = NIGHT_TERROR_PURPLE.brighter();
            Random rand = nightTerrorSeekerEntity.level.getRandom();
            for (int i = 0; i < cycles; i++) {
                float pDelta = i / cycles;
                double lerpX = Mth.lerp(pDelta, ox, x) + motion.x / 4f;
                double lerpY = Mth.lerp(pDelta, oy, y) + motion.y / 4f;
                double lerpZ = Mth.lerp(pDelta, oz, z) + motion.z / 4f;
                float alphaMultiplier = (0.35f + extraAlpha) * Math.min(1, nightTerrorSeekerEntity.age * 0.2f);
                ParticleEffects.spawnSpiritParticles(nightTerrorSeekerEntity.level, lerpX, lerpY, lerpZ, alphaMultiplier*0.8f, norm, firstColor, firstColor.darker());

                final ColorParticleData.ColorParticleDataBuilder colorDataBuilder = ColorParticleData.create(NIGHT_TERROR_DARK, NIGHT_TERROR_DARK)
                        .setEasing(Easing.QUINTIC_OUT)
                        .setCoefficient(1.25f);
                WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                        .setTransparencyData(GenericParticleData.create(Math.min(1, 0.25f * alphaMultiplier), 0f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                        .setLifetime(15 + rand.nextInt(15))
                        .setSpinData(SpinParticleData.create(nextFloat(rand, -0.1f, 0.1f)).setSpinOffset(rand.nextFloat() * 6.28f).build())
                        .setScaleData(GenericParticleData.create(0.25f + rand.nextFloat() * 0.05f, 0.1f, 0f).setEasing(Easing.SINE_IN, Easing.SINE_OUT).build())
                        .setColorData(colorDataBuilder.build())
                        .setRandomOffset(0.02f)
                        .enableNoClip()
                        .addMotion(norm.x, norm.y, norm.z)
                        .setRandomMotion(0.01f, 0.01f)
                        .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                        .spawn(nightTerrorSeekerEntity.level, lerpX, lerpY, lerpZ)
                        .setColorData(colorDataBuilder.setCoefficient(2f).build())
                        .spawn(nightTerrorSeekerEntity.level, lerpX, lerpY, lerpZ);
            }
        }
    }
}