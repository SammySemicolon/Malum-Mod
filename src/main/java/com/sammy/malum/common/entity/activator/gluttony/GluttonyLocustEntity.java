package com.sammy.malum.common.entity.activator.gluttony;

import com.sammy.malum.common.entity.*;
import com.sammy.malum.common.item.curiosities.curios.sets.rotten.CurioSwarmingRotRing;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.entity.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.modules.toolkit.enchanting.LodestoneEnchantmentEffectCommonsHelper;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.util.*;

public class GluttonyLocustEntity extends FloatingEntity {

    protected UUID ownerUUID;

    protected float damage;
    protected boolean aggressive;

    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);

    public GluttonyLocustEntity(Level level) {
        super(MalumEntityTypes.GLUTTONY_LOCUST.get(), level);
        maxAge = 300;

        trail = TrailPointBuilder.create(12);
        longTrail = TrailPointBuilder.create(24);
    }

    public GluttonyLocustEntity(Level level, UUID ownerUUID, float damage, boolean aggressive, Vec3 position, Vec3 velocity) {
        this(level);
        this.ownerUUID = ownerUUID;
        this.damage = damage;
        this.aggressive = aggressive;
        setPos(position);
        setDeltaMovement(velocity);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putUUID("owner", ownerUUID);
        pCompound.putFloat("damage", damage);
        pCompound.putBoolean("aggressive", aggressive);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        ownerUUID = pCompound.getUUID("owner");
        damage = pCompound.getFloat("damage");
        aggressive = pCompound.getBoolean("aggressive");
    }

    @Override
    public boolean mayInteract(Level level, BlockPos pos) {
        return false;
    }

    @Override
    public void collect(ServerLevel level) {
        getDestination().getEntityCollector(level)
                .ifPresent(target -> {
                    target.invulnerableTime = 0;
                    var source = DamageTypeHelper.create(level(), MalumDamageTypes.ROT, this, level.getEntity(ownerUUID));
                    target.hurt(source, damage);
                    target.invulnerableTime = 0;
                });
    }

    @Override
    public boolean canCollect(ServerLevel level) {
        return age >= 20;
    }

    @Override
    public void tick() {
        if (level() instanceof ServerLevel level) {
            float windUpDuration = getWindUpDuration();
            float delta = Mth.clamp(movementWindUp / windUpDuration, 0, 1);
            var length = getDeltaMovement().length();
            var disharmony = 0.05f * (1 - delta);
            if (level().getGameTime() % 40L == 0) {
                disharmony = (disharmony + 1f) * 2;
            }
            if (disharmony > 0) {
                var addedOffset = new Vec3(
                        Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony),
                        0,
                        Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony)
                );
                var newMovement = getDeltaMovement().add(addedOffset).normalize().scale(length);
                setDeltaMovement(newMovement);
            }
            float heightAdjustment = getHeightAdjustmentMotion();
            setDeltaMovement(getDeltaMovement().add(0, heightAdjustment, 0));
            followOwner(level);
        }
        super.tick();
    }

    private void followOwner(ServerLevel level) {
        if (level.getEntity(ownerUUID) instanceof LivingEntity owner) {
            double orbitOffset = 1.55f;
            double orbitAngle = ((age%120) / 60.0) * Math.PI + spinOffset;

            var playerOrbit = owner.position().add(
                    Math.sin(orbitAngle) * orbitOffset,
                    0,
                    Math.cos(orbitAngle) * orbitOffset
            );
            var offset = playerOrbit.subtract(position());
            double delta = Math.min(offset.length() / 10f, 1);
            var velocity = Easing.SINE_IN_OUT.lerp(delta, 0.025f, 0.05f);
            var direction = offset.multiply(1, 0, 1).normalize();
            var addedVelocity = direction.scale(velocity);
            setDeltaMovement(getDeltaMovement().add(addedVelocity));
        }
    }

    private float getHeightAdjustmentMotion() {
        float desiredY = (float) getY();
        var mutable = blockPosition().mutable();
        for (int i = 0; i < 6; i++) {
            mutable.move(Direction.DOWN);

            BlockState state = level().getBlockState(mutable);
            if (state.isFaceSturdy(level(), mutable, Direction.UP)) {
                desiredY = Math.max(desiredY, mutable.getY() + 1.75f);
                break;
            }
        }
        float difference = (float) (desiredY - getY());
        return difference * 0.75f;
    }

    @Override
    public Optional<Entity> correctMissingTarget(ServerLevel level) {
        if (level.getEntity(ownerUUID) instanceof LivingEntity attacker) {
            if (aggressive) {
                if (random.nextFloat() < 0.25f) {
                    var target = CurioSwarmingRotRing.findWickedTarget(attacker);
                    if (target != null) {
                        return Optional.of(target);
                    }
                }
                return Optional.empty();
            }
            var gluttony = attacker.getData(MalumAttachmentTypes.GLUTTONY);
            var target = gluttony.getAttackedTarget();
            if (target == null) {
                return Optional.empty();
            }
            if (!(level.getEntity(target) instanceof LivingEntity entity)) {
                return Optional.empty();
            }
            var predicate = LodestoneEnchantmentEffectCommonsHelper.attackPredicate(attacker).and(t -> !t.isDeadOrDying() && !(t instanceof Player));
            if (predicate.test(entity)) {
                return Optional.of(entity);
            }
        }
        return Optional.empty();
    }

    @Override
    protected long getMissingTargetUpdateFrequency() {
        return 4L;
    }

    @Override
    public int getWindUpDuration() {
        return 20;
    }

    @Override
    public float getFriction() {
        return 0.95f;
    }

    @Override
    public float getMovementSpeed(float windUp, float distance) {
        return (0.5f + Easing.EXPO_OUT.ease(windUp));
    }

    @Override
    public float getMovementEasing(float windUp, float distance) {
        return 0.15f + Easing.EXPO_IN_OUT.ease(windUp) * 0.5f;
    }

    @Override
    public void addTrailPoints() {
        Vec3 position = getPosition(0.5f);
        float offsetScale = 0.125f;
        for (int i = 0; i < 2; i++) {
            var trailPointBuilder = i == 0 ? trail : longTrail;
            float scalar = age / 8f;
            float angle = spinOffset + scalar;
            double xOffset = Math.sin(angle) * offsetScale;
            double zOffset = Math.cos(angle) * offsetScale;
            trailPointBuilder.addTrailPoint(position.add(xOffset, 0, zOffset));
        }
    }

    public UUID getOwnerUUID() {
        return ownerUUID;
    }
}