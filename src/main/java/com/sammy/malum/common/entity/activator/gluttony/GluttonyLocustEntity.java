package com.sammy.malum.common.entity.activator.gluttony;

import com.sammy.malum.common.entity.*;
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

    protected UUID owner;
    protected float magicDamage;
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);

    public GluttonyLocustEntity(Level level) {
        super(MalumEntityTypes.GLUTTONY_LOCUST.get(), level);
        maxAge = 200;

        trail = TrailPointBuilder.create(6);
        longTrail = TrailPointBuilder.create(12);
    }

    public GluttonyLocustEntity(Level level, UUID owner, float magicDamage, UUID target, Vec3 position, Vec3 velocity) {
        this(level);
        this.owner = owner;
        this.magicDamage = magicDamage;
        setDestination(new FloatingItemDestinationData(target));
        setPos(position);
        setDeltaMovement(velocity);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);

        pCompound.putUUID("ownerUUID", owner);
        pCompound.putFloat("magic_damage", magicDamage);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        owner = pCompound.getUUID("ownerUUID");
        magicDamage = pCompound.getFloat("magic_damage");
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
                    var source = DamageTypeHelper.create(level(), MalumDamageTypes.ROT, this, level.getEntity(owner));
                    target.hurt(source, magicDamage);
                    target.invulnerableTime = 0;
                });
    }

    @Override
    public boolean canCollect(ServerLevel level) {
        return age >= 20;
    }

    @Override
    public void tick() {
        if (level() instanceof ServerLevel) {
            float windUpDuration = getWindUpDuration();
            float delta = Mth.clamp(movementWindUp / windUpDuration, 0, 1);
            var length = getDeltaMovement().length();
            var disharmony = 0.25f * (1 - delta);
            if (level().getGameTime() % 40L == 0) {
                disharmony = (disharmony + 0.5f) * 2;
            }
            var addedOffset = new Vec3(
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony),
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony * 0.25f, disharmony),
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony)
            );
            var newMovement = getDeltaMovement().add(addedOffset).normalize().scale(length);
            setDeltaMovement(newMovement);

            float desiredY = (float) getY();
            var mutable = blockPosition().mutable();
            for (int i = 0; i < 4; i++) {
                mutable.move(Direction.DOWN);

                BlockState state = level().getBlockState(mutable);
                if (state.isFaceSturdy(level(), mutable, Direction.UP)) {
                    desiredY = Math.max(desiredY, mutable.getY() + 1.5f);
                    break;
                }
            }
            float difference = (float) (desiredY - getY());
            float motion = difference * 0.015f;
            if (motion != 0) {
                setDeltaMovement(getDeltaMovement().add(0, motion, 0).scale(0.9f));
            }
        }
        super.tick();
    }

    @Override
    public Optional<Entity> correctMissingTarget(ServerLevel level) {
        if (level.getEntity(owner) instanceof LivingEntity attacker) {
            var area = getBoundingBox().inflate(8f, 3f, 8f);
            var predicate = LodestoneEnchantmentEffectCommonsHelper.attackPredicate(attacker).and(t -> !t.isDeadOrDying() && !(t instanceof Player));
            var targets = level.getEntitiesOfClass(LivingEntity.class, area, predicate);
            if (targets.isEmpty()) {
                return Optional.empty();
            }
            targets.sort(Comparator.comparingDouble(t -> t.distanceToSqr(this)));
            return Optional.of(targets.getFirst());
        }
        return Optional.empty();
    }

    @Override
    public int getWindUpDuration() {
        return 80;
    }

    @Override
    public float getFriction() {
        return 0.97f;
    }

    @Override
    public float getMovementSpeed(float windUp, float distance) {
        return (0.35f + Easing.EXPO_OUT.ease(windUp));
    }

    @Override
    public float getMovementEasing(float windUp, float distance) {
        return 0.1f + Easing.EXPO_IN_OUT.ease(windUp) * 0.2f;
    }

    @Override
    public void addTrailPoints() {
        Vec3 position = getPosition(0.5f);
        for (int i = 0; i < 2; i++) {
            var trailPointBuilder = i == 0 ? trail : longTrail;
            float offsetScale = (i+1) * 0.1f;
            float scalar = age / 2f;
            float offset = i * 2.35f;
            float angle = spinOffset + scalar + offset;
            double xOffset = Math.sin(angle) * offsetScale;
            double zOffset = Math.cos(angle) * offsetScale;
            trailPointBuilder.addTrailPoint(position.add(xOffset, 0, zOffset));
        }
    }

    public UUID getOwner() {
        return owner;
    }
}