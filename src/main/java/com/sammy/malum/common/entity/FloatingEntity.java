package com.sammy.malum.common.entity;

import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.registry.common.SpiritTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;

public abstract class FloatingEntity extends Entity {

    protected static final EntityDataAccessor<String> DATA_SPIRIT = SynchedEntityData.defineId(FloatingEntity.class, EntityDataSerializers.STRING);
    public final TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(10);
    protected MalumSpiritType spiritType = SpiritTypeRegistry.ARCANE_SPIRIT;
    public int maxAge;
    public int age;
    public float moveTime;
    public float windUp;
    public float hoverStart;

    public FloatingEntity(EntityType<? extends FloatingEntity> type, Level level) {
        super(type, level);
        noPhysics = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }

    public MalumSpiritType getSpiritType() {
        return spiritType;
    }

    public void setSpirit(MalumSpiritType spiritType) {
        setSpirit(spiritType.identifier);
    }

    public void setSpirit(String spiritIdentifier) {
        this.getEntityData().set(DATA_SPIRIT, spiritIdentifier);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_SPIRIT, SpiritTypeRegistry.ARCANE_SPIRIT.identifier);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("age", age);
        compound.putInt("maxAge", maxAge);
        compound.putFloat("moveTime", moveTime);
        compound.putFloat("windUp", windUp);
        compound.putString("spiritType", spiritType.identifier);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        age = compound.getInt("age");
        maxAge = compound.getInt("maxAge");
        moveTime = compound.getFloat("moveTime");
        windUp = compound.getFloat("windUp");
        getEntityData().set(DATA_SPIRIT, compound.getString("spiritType"));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_SPIRIT.equals(pKey)) {
            spiritType = SpiritTypeRegistry.SPIRITS.get(entityData.get(DATA_SPIRIT));
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void tick() {
        super.tick();
        hoverStart = getHoverStart(0);
        trailPointBuilder.addTrailPoint(position().add(0, getYOffset(0) + 0.25f, 0f));
        trailPointBuilder.tickTrailPoints();
        baseTick();
        age++;
        if (windUp < 1f) {
            windUp += 0.02f;
        }
        if (age > maxAge) {
            discard();
        }
        if (level().isClientSide) {
            double x = xOld, y = yOld + getYOffset(0) + 0.25f, z = zOld;
            spawnParticles(x, y, z);
        }
        move();
    }

    public void baseTick() {
        BlockHitResult result = level().clip(new ClipContext(position(), position().add(getDeltaMovement()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = result.getBlockPos();
            BlockState blockstate = this.level().getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level().getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level(), blockpos, blockstate, this, (TheEndGatewayBlockEntity) blockentity);
                }
            }
        }
        this.checkInsideBlocks();
        Vec3 movement = this.getDeltaMovement();
        double nextX = this.getX() + movement.x;
        double nextY = this.getY() + movement.y;
        double nextZ = this.getZ() + movement.z;
        double distance = movement.horizontalDistance();
        this.setXRot(lerpRotation(this.xRotO, (float) (Mth.atan2(movement.y, distance) * (double) (180F / (float) Math.PI))));
        this.setYRot(lerpRotation(this.yRotO, (float) (Mth.atan2(movement.x, movement.z) * (double) (180F / (float) Math.PI))));
        this.setPos(nextX, nextY, nextZ);
        ProjectileUtil.rotateTowardsMovement(this, 0.2F);
    }

    protected static float lerpRotation(float p_37274_, float p_37275_) {
        while (p_37275_ - p_37274_ < -180.0F) {
            p_37274_ -= 360.0F;
        }

        while (p_37275_ - p_37274_ >= 180.0F) {
            p_37274_ += 360.0F;
        }

        return Mth.lerp(0.2F, p_37274_, p_37275_);
    }

    public void spawnParticles(double x, double y, double z) {

    }

    public void move() {
    }

    public float getYOffset(float partialTicks) {
        return Mth.sin(((float) age + partialTicks) / 10.0F + getHoverStart(partialTicks)) * 0.1F + 0.1F;
    }

    public float getRotation(float partialTicks) {
        return ((float) age + partialTicks) / 20.0F + getHoverStart(partialTicks) / 2f;
    }

    public float getHoverStart(float partialTicks) {
        return hoverStart + (1 - Easing.SINE_OUT.ease(Math.min(1, (age + partialTicks) / 60f), 0, 1, 1)) * 0.35f;
    }
}
