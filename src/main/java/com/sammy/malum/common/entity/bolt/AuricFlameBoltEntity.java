package com.sammy.malum.common.entity.bolt;

import com.sammy.malum.common.item.curiosities.weapons.staff.*;
import com.sammy.malum.registry.client.*;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import com.sammy.malum.registry.common.item.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.network.chat.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.util.*;
import java.util.function.*;

public class AuricFlameBoltEntity extends AbstractBoltProjectileEntity {

    public AuricFlameBoltEntity(Level level) {
        super(EntityRegistry.AURIC_FLAME_BOLT.get(), level);
        noPhysics = false;
    }

    public AuricFlameBoltEntity(Level level, double pX, double pY, double pZ) {
        this(level);
        setPos(pX, pY, pZ);
        noPhysics = false;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        final AABB boundingBox = getBoundingBox();
        setBoundingBox(boundingBox.deflate(0.5f));
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult instanceof BlockHitResult blockHitResult) {
            super.onHitBlock(blockHitResult);
        }
        setBoundingBox(boundingBox);
    }

    @Override
    public void tick() {
        Vec3 motion = getDeltaMovement();
        super.tick();
        Entity owner = getOwner();
        if (spawnDelay > 0 || owner == null || fadingAway) {
            return;
        }
        List<LivingEntity> entities = level().getEntitiesOfClass(LivingEntity.class, getBoundingBox().inflate(25), target -> target != owner && target.isAlive() && !target.isAlliedTo(owner));
        if (!entities.isEmpty()) {
            LivingEntity nearest = entities.stream().min(Comparator.comparingDouble((e) -> e.distanceToSqr(this))).get();
            Vec3 nearestPosition = nearest.position().add(0, nearest.getBbHeight() / 2, 0);
            Vec3 diff = nearestPosition.subtract(position());
            double speed = motion.length();
            final double length = diff.length();
            if (length < 2f) {
                return;
            }
            Vec3 maybeTowards = nearestPosition.add(getDeltaMovement());
            if (distanceToSqr(maybeTowards) < distanceToSqr(nearestPosition)) { //Homing should only happen if we're moving towards the target, which this checks relatively close enough
                return;
            }
            Vec3 newmotion = diff.normalize().scale(speed);
            final double dot = motion.normalize().dot(diff.normalize());
            if (dot < 0.9f) {
                return;
            }
            if (newmotion.length() == 0) {
                newmotion = newmotion.add(0.01, 0, 0);
            }
            float angleScalar = (float) ((dot - 0.9f) * 10f);
            float distanceScalar = (float) (0.2f + 1f - Math.min(25f, length) / 25f);
            float horizontalFactor = 0.4f * distanceScalar * angleScalar;
            float verticalFactor = 0.6f * distanceScalar * angleScalar;
            final double x = Mth.lerp(horizontalFactor, motion.x, newmotion.x);
            final double y = Mth.lerp(verticalFactor, motion.y, newmotion.y);
            final double z = Mth.lerp(horizontalFactor, motion.z, newmotion.z);
            setDeltaMovement(new Vec3(x, y, z));
        }
    }

    @Override
    public void playSound(SoundEvent pSound, float pVolume, float pPitch) {
        super.playSound(pSound, pVolume, pPitch);
        super.playSound(SoundRegistry.AURIC_FLAME_MOTIF.get(), pVolume+0.2f, pPitch+0.6f);
    }

    @Override
    public int getMaxAge() {
        return 40;
    }

    @Override
    public ParticleEffectType getOnHitVisualEffect() {
        return ParticleEffectTypeRegistry.AURIC_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.STAFF_OF_THE_AURIC_FLAME.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles() {
        float scalar = age > getMaxAge() ? 1f - (age - getMaxAge() + 10) / 10f : 1f;
        if (age < 5) {
            scalar = age / 5f;
        }
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), position(), AuricFlameStaffItem.AURIC_COLOR_DATA, AuricFlameStaffItem.REVERSE_AURIC_COLOR_DATA);
        lightSpecs.getBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.spawnParticles();
        final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.98f));
        SparkParticleBuilder.create(ParticleRegistry.SLASH)
                .setTransparencyData(GenericParticleData.create(0.5f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.5f * scalar).build())
                .setLengthData(GenericParticleData.create(2.4f * Math.min(1f, scalar*2)).setEasing(Easing.SINE_IN_OUT).build())
                .setColorData(level().getGameTime() % 6L == 0 ? AuricFlameStaffItem.REVERSE_AURIC_COLOR_DATA : AuricFlameStaffItem.AURIC_COLOR_DATA)
                .setLifetime(Math.min(6 + age * 3, 30))
                .setMotion(norm)
                .enableNoClip()
                .enableForcedSpawn()
                .disableCull()
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                .addTickActor(behavior)
                .spawn(level(), position().x, position().y, position().z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(level(), position().x, position().y, position().z);
    }
}