package com.sammy.malum.common.entity.spirit;

import com.sammy.malum.common.entity.FloatingItemEntity;
import com.sammy.malum.common.item.spirit.SpiritShardItem;
import com.sammy.malum.core.handlers.SpiritHarvestHandler;
import com.sammy.malum.registry.common.SoundRegistry;
import com.sammy.malum.registry.common.entity.EntityRegistry;
import com.sammy.malum.visual_effects.SpiritLightSpecs;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.helpers.ItemHelper;

import java.util.UUID;

public class SpiritItemEntity extends FloatingItemEntity {
    public UUID ownerUUID;
    public LivingEntity owner;
    public int soundCooldown = 20 + random.nextInt(100);


    public SpiritItemEntity(Level level) {
        super(EntityRegistry.NATURAL_SPIRIT.get(), level);
        maxAge = 4000;
    }

    public SpiritItemEntity(Level level, UUID ownerUUID, ItemStack stack, double posX, double posY, double posZ, double velX, double velY, double velZ) {
        this(level);
        setOwner(ownerUUID);
        setItem(stack);
        setPos(posX, posY, posZ);
        setDeltaMovement(velX, velY, velZ);
        maxAge = 800;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (ownerUUID != null) {
            compound.putUUID("ownerUUID", ownerUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("ownerUUID")) {
            setOwner(compound.getUUID("ownerUUID"));
        }
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public void spawnParticles(double x, double y, double z) {
        Vec3 motion = getDeltaMovement();
        Vec3 norm = motion.normalize().scale(0.05f);
        var lightSpecs = SpiritLightSpecs.spiritLightSpecs(level(), new Vec3(x, y, z), spiritType);
        lightSpecs.getBuilder().setMotion(norm);
        lightSpecs.getBloomBuilder().setMotion(norm);
        lightSpecs.spawnParticles();
    }

    @Override
    public void move() {
        if (soundCooldown-- == 0) {
            if (random.nextFloat() < 0.4f) {
                level().playSound(null, blockPosition(), SoundRegistry.ARCANE_WHISPERS.get(), SoundSource.NEUTRAL, 0.3f, Mth.nextFloat(random, 1.1f, 2f));
            }
            soundCooldown = random.nextInt(40) + 40;
        }
        float friction = 0.94f;
        setDeltaMovement(getDeltaMovement().multiply(friction, friction, friction));
        if (owner == null || !owner.isAlive()) {
            if (level().getGameTime() % 40L == 0) {
                Player playerEntity = level().getNearestPlayer(this, 50);
                if (playerEntity != null) {
                    setOwner(playerEntity.getUUID());
                }
            }
            return;
        }
        Vec3 desiredLocation = owner.position().add(0, owner.getBbHeight() / 3, 0);
        float distance = (float) distanceToSqr(desiredLocation);
        float velocity = windUp < 0.25f ? 0 : Math.min(windUp - 0.25f, 0.8f) * 5f;
        moveTime++;
        Vec3 desiredMotion = desiredLocation.subtract(position()).normalize().multiply(velocity, velocity, velocity);
        float easing = 0.01f;
        float xMotion = (float) Mth.lerp(easing, getDeltaMovement().x, desiredMotion.x);
        float yMotion = (float) Mth.lerp(easing, getDeltaMovement().y, desiredMotion.y);
        float zMotion = (float) Mth.lerp(easing, getDeltaMovement().z, desiredMotion.z);
        Vec3 resultingMotion = new Vec3(xMotion, yMotion, zMotion);
        setDeltaMovement(resultingMotion);
        if (distance < 0.4f) {
            if (isAlive()) {
                ItemStack stack = getItem();
                if (stack.getItem() instanceof SpiritShardItem) {
                    SpiritHarvestHandler.pickupSpirit(owner, stack);
                } else {
                    ItemHelper.giveItemToEntity(owner, stack);
                }
                if (random.nextFloat() < 0.6f) {
                    level().playSound(null, blockPosition(), SoundRegistry.SPIRIT_PICKUP.get(), SoundSource.NEUTRAL, 0.3f, Mth.nextFloat(random, 1.1f, 2f));
                }
                remove(RemovalReason.DISCARDED);
            }
        }
    }

    public void setOwner(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        updateOwner();
    }

    public void updateOwner() {
        if (!level().isClientSide) {
            owner = (LivingEntity) ((ServerLevel) level()).getEntity(ownerUUID);
        }
    }

    @Override
    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }
}