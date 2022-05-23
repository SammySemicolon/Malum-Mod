package com.sammy.malum.common.entity;

import com.sammy.malum.core.setup.content.SpiritTypeRegistry;
import com.sammy.ortus.helpers.EntityHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.TheEndGatewayBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.awt.*;
import java.util.ArrayList;

public abstract class FloatingEntity extends Entity {
    protected static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(FloatingEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Integer> DATA_END_COLOR = SynchedEntityData.defineId(FloatingEntity.class, EntityDataSerializers.INT);
    public final ArrayList<Vec3> pastPositions = new ArrayList<>();
    public Color startColor = SpiritTypeRegistry.SACRED_SPIRIT.getColor();
    public Color endColor = SpiritTypeRegistry.SACRED_SPIRIT.getEndColor();
    public int maxAge;
    public int age;
    public float moveTime;
    public int range = 3;
    public float windUp;
    public final float hoverStart;

    public FloatingEntity(EntityType<? extends FloatingEntity> type, Level level) {
        super(type, level);
        noPhysics = false;
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }

    @Override
    protected void defineSynchedData() {
        this.getEntityData().define(DATA_COLOR, SpiritTypeRegistry.SACRED_SPIRIT.getColor().getRGB());
        this.getEntityData().define(DATA_END_COLOR, SpiritTypeRegistry.SACRED_SPIRIT.getEndColor().getRGB());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("age", age);
        compound.putFloat("moveTime", moveTime);
        compound.putInt("range", range);
        compound.putFloat("windUp", windUp);

        compound.putInt("start", startColor.getRGB());
        compound.putInt("end", endColor.getRGB());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        age = compound.getInt("age");
        moveTime = compound.getFloat("moveTime");
        int range = compound.getInt("range");
        if (range > 0) {
            this.range = range;
        }
        windUp = compound.getFloat("windUp");
        startColor = new Color(compound.getInt("start"));
        endColor = new Color(compound.getInt("end"));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if (DATA_COLOR.equals(pKey)) {
            startColor = new Color(entityData.get(DATA_COLOR));
        }
        if (DATA_END_COLOR.equals(pKey)) {
            endColor = new Color(entityData.get(DATA_END_COLOR));
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    public void tick() {
        super.tick();
        baseTick();
        trackPastPositions();
        age++;
        if (windUp < 1f) {
            windUp += 0.02f;
        }
        if (age > maxAge) {
            remove(RemovalReason.KILLED);
        }
        if (level.isClientSide) {
            double x = getX(), y = getY() + getYOffset(0) + 0.25f, z = getZ();
            spawnParticles(x, y, z);
        } else {
            move();
        }
    }

    public void trackPastPositions() {
        EntityHelper.trackPastPositions(pastPositions, position().add(0, getYOffset(0) + 0.25F, 0), 0.1f);
        int excess = pastPositions.size() - 1;
        ArrayList<Vec3> toRemove = new ArrayList<>();
        float ratio = 0.01f;
        for (int i = 0; i < excess; i++) {
            float progress = (float) Math.min(1, ratio * ((excess - i) * (Math.exp((Math.max(0, excess - 20))))));
            Vec3 excessPosition = pastPositions.get(i);
            Vec3 nextExcessPosition = pastPositions.get(i + 1);
            Vec3 offset = nextExcessPosition.subtract(excessPosition).normalize().multiply(progress,progress,progress);
            Vec3 newPos = excessPosition.add(offset);
            pastPositions.set(i, newPos);
            float excessDistance = (float) excessPosition.distanceTo(nextExcessPosition);
            if (excessDistance < 0.05f) {
                toRemove.add(pastPositions.get(i));
            }
        }
        pastPositions.removeAll(toRemove);
    }

    public void baseTick() {
        BlockHitResult result = level.clip(new ClipContext(position(), position().add(getDeltaMovement()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockPos blockpos = result.getBlockPos();
            BlockState blockstate = this.level.getBlockState(blockpos);
            if (blockstate.is(Blocks.NETHER_PORTAL)) {
                this.handleInsidePortal(blockpos);
            } else if (blockstate.is(Blocks.END_GATEWAY)) {
                BlockEntity blockentity = this.level.getBlockEntity(blockpos);
                if (blockentity instanceof TheEndGatewayBlockEntity && TheEndGatewayBlockEntity.canEntityTeleport(this)) {
                    TheEndGatewayBlockEntity.teleportEntity(this.level, blockpos, blockstate, this, (TheEndGatewayBlockEntity) blockentity);
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
        return Mth.sin(((float) age + partialTicks) / 20.0F + hoverStart) * 0.1F + 0.1F;
    }

    public float getRotation(float partialTicks) {
        return ((float) age + partialTicks) / 20.0F + this.hoverStart;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }
}