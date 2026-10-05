package com.sammy.malum.common.entity.activator.vindictive_brand;

import com.sammy.malum.common.entity.*;
import com.sammy.malum.common.item.curiosities.weapons.greatsword.*;
import com.sammy.malum.registry.common.entity.*;
import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.network.syncher.*;
import net.minecraft.server.level.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.modules.core.easing.*;
import team.lodestar.lodestone.systems.rendering.trail.*;

import java.util.*;

public class ResentmentRitualActivator extends FloatingEntity {

    protected UUID owner;

    protected int storedResentment;
    public float spinOffset = (float) (random.nextFloat() * Math.PI * 2);

    public ResentmentRitualActivator(Level level) {
        super(MalumEntityTypes.RESENTMENT_RITUAL.get(), level);
        maxAge = 200;

        trail = TrailPointBuilder.create(12);
        longTrail = TrailPointBuilder.create(24);
    }

    public ResentmentRitualActivator(Level level, UUID owner, int storedResentment, Vec3 position, Vec3 velocity) {
        this(level);
        this.owner = owner;
        this.storedResentment = storedResentment;
        setDestination(new FloatingItemDestinationData(owner));
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
        pCompound.putInt("stored_resentment", storedResentment);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);

        owner = pCompound.getUUID("ownerUUID");
        storedResentment = pCompound.getInt("stored_resentment");
    }

    @Override
    public boolean mayInteract(Level level, BlockPos pos) {
        return false;
    }

    @Override
    public void collect(ServerLevel level) {
        getDestination().getEntityCollector(level)
                .ifPresent(target -> VindictiveBrandSwordItem.progressRitual(target, storedResentment));
    }

    @Override
    public boolean canCollect(ServerLevel level) {
        return age >= 10;
    }

    @Override
    public void tick() {
        if (level() instanceof ServerLevel) {
            float windUpDuration = getWindUpDuration();
            float delta = Mth.clamp(movementWindUp / windUpDuration, 0, 1);
            var length = getDeltaMovement().length();
            var disharmony = 0.5f * (1 - delta);
            var addedOffset = new Vec3(
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony),
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony) * 0.25f,
                    Easing.SINE_IN_OUT.asWeighedRandom(random, -disharmony, disharmony)
            );
            var newMovement = getDeltaMovement().add(addedOffset).normalize().scale(length);
            setDeltaMovement(newMovement);
        }
        super.tick();
    }

    @Override
    public int getWindUpDuration() {
        return 40;
    }

    @Override
    public float getFriction() {
        return 0.98f;
    }

    @Override
    public float getMovementSpeed(float windUp, float distance) {
        return (0.2f + Easing.EXPO_OUT.ease(windUp));
    }

    @Override
    public float getMovementEasing(float windUp, float distance) {
        return 0.05f + Easing.EXPO_IN_OUT.ease(windUp) * 0.2f;
    }

    @Override
    public void addTrailPoints() {
        var position = getPosition(0.5f);
        for (int i = 0; i < 2; i++) {
            var trailPointBuilder = i == 0 ? trail : longTrail;
            float offsetScale = (i+1) * 0.1f;
            float scalar = age / 2f;
            float offset = i * 0.5f;
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