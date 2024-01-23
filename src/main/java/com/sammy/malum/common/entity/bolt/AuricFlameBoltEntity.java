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
    protected void onHitEntity(EntityHitResult result) {
        if (fadingAway || spawnDelay > 0) {
            return;
        }
        if (result.getEntity() instanceof LivingEntity livingentity) {
            livingentity.setSecondsOnFire(4);
        }
        super.onHitEntity(result);
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
            Vec3 nextPosition = position().add(getDeltaMovement());
            if (nearest.distanceToSqr(nextPosition) > nearest.distanceToSqr(position())) {
                return;
            }
            Vec3 newMotion = diff.normalize().scale(speed);
            final double dot = motion.normalize().dot(diff.normalize());
            if (dot < 0.9f) {
                return;
            }
            if (newMotion.length() == 0) {
                newMotion = newMotion.add(0.01, 0, 0);
            }
            float angleScalar = (float) ((dot - 0.9f) * 10f);
            float distanceScalar = (float) (0.2f + 1f - Math.min(25f, diff.length()) / 25f);
            float horizontalFactor = 0.4f * distanceScalar * angleScalar;
            float verticalFactor = 0.6f * distanceScalar * angleScalar;
            final double x = Mth.lerp(horizontalFactor, motion.x, newMotion.x);
            final double y = Mth.lerp(verticalFactor, motion.y, newMotion.y);
            final double z = Mth.lerp(horizontalFactor, motion.z, newMotion.z);
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
    public ParticleEffectType getImpactParticleEffect() {
        return ParticleEffectTypeRegistry.AURIC_BOLT_IMPACT;
    }

    @Override
    protected Item getDefaultItem() {
        return ItemRegistry.STAFF_OF_THE_AURIC_FLAME.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void spawnParticles() {
        Level level = level();
        Vec3 position = position();
        float scalar = getVisualEffectScalar();
        Vec3 norm = getDeltaMovement().normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level, position, AuricFlameStaffItem.AURIC_COLOR_DATA, AuricFlameStaffItem.REVERSE_AURIC_COLOR_DATA);
        lightSpecs.getBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.getBloomBuilder().multiplyLifetime(1.25f).setMotion(norm);
        lightSpecs.spawnParticles();
        final Consumer<LodestoneWorldParticleActor> behavior = p -> p.setParticleMotion(p.getParticleSpeed().scale(0.98f));
        SparkParticleBuilder.create(ParticleRegistry.SLASH)
                .setTransparencyData(GenericParticleData.create(0.5f * scalar, 0.2f * scalar, 0f).setEasing(Easing.SINE_IN_OUT, Easing.SINE_IN).build())
                .setScaleData(GenericParticleData.create(0.5f * scalar).build())
                .setLengthData(GenericParticleData.create(2.4f * Math.min(1f, scalar*2), 0.2f * Math.min(1f, scalar*2)).setEasing(Easing.CUBIC_IN).build())
                .setColorData(level.getGameTime() % 6L == 0 ? AuricFlameStaffItem.REVERSE_AURIC_COLOR_DATA : AuricFlameStaffItem.AURIC_COLOR_DATA)
                .setLifetime(Math.min(6 + age * 3, 30))
                .setMotion(norm)
                .enableNoClip()
                .enableForcedSpawn()
                .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.FIRST_INDEX)
                .addTickActor(behavior)
                .spawn(level, position.x, position.y, position.z)
                .setRenderType(LodestoneWorldParticleRenderType.LUMITRANSPARENT)
                .spawn(level, position.x, position.y, position.z);
    }
}