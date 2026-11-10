package com.sammy.malum.common.entity;

import net.minecraft.nbt.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.modules.core.easing.Easing;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.util.*;

public abstract class FloatingEntity extends MovingEntity {

    public TrailPointBuilder trail = TrailPointBuilder.create(5);
    public TrailPointBuilder longTrail = TrailPointBuilder.create(30);

    protected FloatingItemDestinationData destination;

    protected float hoverOffset;

    protected int age;
    protected int maxAge;
    protected int movementWindUp;
    protected int hoverWindUp;

    public FloatingEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
        noPhysics = false;
        hoverOffset = (float) (Math.random() * Math.PI * 2.0D);
    }

    public abstract void collect(ServerLevel level);

    public boolean canCollect(ServerLevel level) {
        return true;
    }

    public boolean shouldVanishAfterCollection(ServerLevel level) {
        return true;
    }

    public void setDestination(FloatingItemDestinationData destination) {
        this.destination = destination;
    }

    public FloatingItemDestinationData getDestination() {
        return destination;
    }

    public float getVisualEffectScalar() {
        return Math.min(age / 5f, 1f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        destination = FloatingItemDestinationData.load(compound);
        age = compound.getInt("age");
        maxAge = compound.getInt("maxAge");
        movementWindUp = compound.getInt("movementWindUp");
        hoverWindUp = compound.getInt("hoverWindUp");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        if (destination != null) {
            destination.save(compound);
        }
        compound.putInt("age", age);
        compound.putInt("maxAge", maxAge);
        compound.putInt("movementWindUp", movementWindUp);
        compound.putInt("hoverWindUp", hoverWindUp);
    }

    @Override
    public void tick() {
        if (age > maxAge) {
            discard();
            return;
        }
        if (level() instanceof ServerLevel level) {
            if (destination != null && destination.isValid(level)) {
                float distance = (float) destination.getDistance(level, this);
                Optional<Vec3> destination = this.destination.getDestination(level);
                if (destination.isPresent()) {
                    float windUpDuration = getWindUpDuration();
                    var targetPos = destination.get();
                    if (movementWindUp < windUpDuration) {
                        movementWindUp++;
                    }
                    float delta = Mth.clamp(movementWindUp / windUpDuration, 0, 1);
                    float velocity = Mth.clamp(delta - 0.25f, 0, 0.75f) * getMovementSpeed(delta, distance);
                    float easing = getMovementEasing(delta, distance);
                    var targetMovement = targetPos.subtract(position()).normalize().scale(velocity);
                    var newMovement = getDeltaMovement().lerp(targetMovement, easing);
                    setDeltaMovement(newMovement);
                    if (distance < 0.4f && canCollect(level)) {
                        var shouldVanish = shouldVanishAfterCollection(level);
                        collect(level);
                        if (shouldVanish) {
                            remove(RemovalReason.DISCARDED);
                        }
                    }
                }
            } else {
                if (movementWindUp > 0) {
                    movementWindUp--;
                }
                if (age >= 40) {
                    float windUpDuration = getWindUpDuration();
                    float gravity = 0.004f * (windUpDuration - movementWindUp) / windUpDuration;
                    setDeltaMovement(getDeltaMovement().subtract(0, gravity, 0).multiply(0.9f, 0.96f, 0.9f));
                }
                long frequency = getMissingTargetUpdateFrequency();
                if (level.getGameTime() % frequency == 0) {
                    var retarget = correctMissingTarget(level);
                    retarget.ifPresent(entity -> setDestination(new FloatingItemDestinationData(entity.getUUID())));
                }
            }
        } else {
            addTrailPoints();
            trail.tickTrailPoints();
            longTrail.tickTrailPoints();
        }
        super.tick();
        age++;
    }

    @Override
    public float getFriction() {
        return 0.96f;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    public int getWindUpDuration() {
        return 50;
    }

    public float getMovementSpeed(float windUp, float distance) {
        return 3f;
    }

    public float getMovementEasing(float windUp, float distance) {
        float windUpScalar = windUp * 0.01f;
        float distanceScalar = (1 / Math.max(distance, 1)) * 0.025f;
        return windUpScalar + distanceScalar;
    }

    public void addTrailPoints() {
        Vec3 position = getOffsetPosition(0.5f);
        trail.addTrailPoint(position);
        longTrail.addTrailPoint(position);
    }

    public Optional<Entity> correctMissingTarget(ServerLevel level) {
        ServerPlayer nearestPlayer = null;
        float minimumDistance = 6f;
        for (ServerPlayer player : level.players()) {
            float distance = player.distanceTo(this);
            if (distance < minimumDistance) {
                if (player.hasLineOfSight(this)) {
                    nearestPlayer = player;
                    minimumDistance = distance;
                }
            }
        }
        if (nearestPlayer != null && nearestPlayer.isAlive()) {
            return Optional.of(nearestPlayer);
        }
        return Optional.empty();
    }

    protected long getMissingTargetUpdateFrequency() {
        return 20L;
    }

    public Vec3 getOffsetPosition() {
        return getOffsetPosition(0);
    }

    public Vec3 getOffsetPosition(float partialTicks) {
        return getPosition(partialTicks).add(0, getYOffset(0), 0);
    }

    public float getYOffset(float partialTicks) {
        float windUpDuration = getWindUpDuration();
        float delta = Math.min((age + partialTicks) / windUpDuration, 1);
        float eased = Easing.CIRC_IN_OUT.ease(delta);
        return Mth.sin(((float) age + partialTicks) / 6.0F + hoverOffset) * (0.5F - (eased * 0.25F));
    }

    public float getRotation(float partialTicks) {
        return ((float) age + partialTicks) / 10.0F + hoverOffset;
    }
}