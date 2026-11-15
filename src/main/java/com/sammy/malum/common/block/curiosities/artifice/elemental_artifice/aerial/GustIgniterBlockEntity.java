package com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial;

import com.sammy.malum.common.block.curiosities.artifice.ArtificeTinkeringInfo;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.ArtificeBlockConnectionData;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.SequencedBlockConnectionArray;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.ElementalArtificeTinkeringInfo;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.PrimaryArtificeBlock;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.PrimaryArtificeBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.SecondaryArtificeBlockEntity;
import com.sammy.malum.common.item.nucleus.WindNucleusItem;
import com.sammy.malum.registry.common.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.visual_effects.block.WindTunnelParticleEffects;
import net.minecraft.core.*;
import net.minecraft.core.particles.*;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.sounds.*;
import net.minecraft.util.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.*;
import team.lodestar.lodestone.modules.toolkit.blockentity.LodestoneBlockEntityType;

import java.util.HashSet;

import static com.sammy.malum.core.handlers.WindTunnelHandler.MAX_STRENGTH;

public class GustIgniterBlockEntity extends PrimaryArtificeBlockEntity {

    public int strength = 1;
    public int limiter = -1;
    public boolean modified = false;

    public GustIgniterBlockEntity(LodestoneBlockEntityType<? extends GustIgniterBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public GustIgniterBlockEntity(BlockPos pos, BlockState state) {
        this(MalumBlockEntities.GUST_IGNITER.get(), pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        strength = tag.getInt("strength");
        limiter = tag.getInt("limiter");
        modified = tag.getBoolean("modified");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("strength", strength);
        tag.putInt("limiter", limiter);
        tag.putBoolean("modified", modified);
    }

    @Override
    public void setInfo(ArtificeTinkeringInfo info) {
        if (info instanceof ElementalArtificeTinkeringInfo tinkeringInfo) {
            this.strength = tinkeringInfo.strength();
            this.modified = tinkeringInfo.modified();
        }
    }

    @Override
    public ArtificeTinkeringInfo defaultTinkeringState() {
        return new ElementalArtificeTinkeringInfo(strength, modified);
    }

    @Override
    public void clientTick(Level level) {
        WindTunnelParticleEffects.passiveWindTunnelParticles(this);
    }

    @Override
    public void serverTick(ServerLevel level) {
        if (!GustIgniterBlock.isPowered(getBlockState())) {
            return;
        }
        if (connectionData == null) {
            return;
        }
        var affectedEntities = connectionData.findAffectedEntities(level);
        for (Entity entity : affectedEntities) {
            var data = entity.getData(MalumAttachmentTypes.WIND_TUNNEL_INFO);
            if (data.addInfluence(this)) {
                entity.syncData(MalumAttachmentTypes.WIND_TUNNEL_INFO);
            }
        }
    }

    @Override
    public void gatherConnectionData(ServerLevel level, HashSet<SecondaryArtificeBlockEntity> connectedBlocks) {
        int strength = this.strength;
        for (SecondaryArtificeBlockEntity blockEntity : connectedBlocks) {
            if (!(blockEntity instanceof WindTunnelBlockEntity windTunnel)) {
                continue;
            }
            int clamped = windTunnel.clampStrength(strength);
            if (clamped < strength) {
                strength = clamped;
            }
        }
    }

    @Override
    public ArtificeBlockConnectionData bakeConnectionData(ServerLevel level, SequencedBlockConnectionArray array) {
        float padding = 0.25f;
        int length = getTunnelLength();
        return new ArtificeBlockConnectionData(array, padding, length);
    }

    @Override
    public void clearConnectionData() {
        limiter = -1;
    }

    @Override
    public void activate(ServerLevel level, boolean powered) {
        BlockState state = getBlockState();
        if (state.getValue(PrimaryArtificeBlock.CAPTURED)) {
            return;
        }
        if (!state.getValue(WindTunnelBlock.POWERED) && powered) {
            createWindGust();
        }
    }

    public int getTunnelLength() {
        if (limiter == -1) {
            return strength;
        }
        return Math.min(strength, limiter);
    }

    public float getTunnelStrength() {
        return Mth.lerp(getTunnelLength()/MAX_STRENGTH, 0.05f, 0.2f);
    }

    public void createWindGust() {
        var state = getBlockState();
        var pos = getBlockPos();
        var facing = state.getValue(GustIgniterBlock.FACING);
        boolean isGlider = modified;
        float radius = 1.75f;
        float delta = strength / MAX_STRENGTH;
        float force = (isGlider ? 0.2f : 0.4f) + delta * 0.4f;
        var appliedEffect = isGlider ? MalumMobEffects.LIFTED : MalumMobEffects.ASCENSION;
        int effectDuration = isGlider ? 100 : 40;
        int effectAmplifier = isGlider ? 2 : 4;
        var explosionPos = pos.relative(facing).getCenter();
        var affectedEntities = WindNucleusItem.getExplosionAffectedEntities(level, null, explosionPos, radius);
        level.explode(
                null,
                null,
                WindNucleusItem.EXPLOSION_DAMAGE_CALCULATOR,
                explosionPos.x(),
                explosionPos.y(),
                explosionPos.z(),
                radius,
                false,
                Level.ExplosionInteraction.TRIGGER,
                ParticleTypes.GUST_EMITTER_SMALL,
                ParticleTypes.GUST_EMITTER_LARGE,
                SoundEvents.WIND_CHARGE_BURST
        );

        for (Entity entity : affectedEntities) {
            var velocity = entity.getDeltaMovement();
            double appliedForce = force;
            if (velocity.length() < 0.1f) {
                appliedForce *= 1.5f;
            }
            if (velocity.y < 0) {
                appliedForce += Math.abs(velocity.y);
            }

            var direction = new Vec3(facing.getStepX(), facing.getStepY(), facing.getStepZ());
            var multiplier = new Vec3(0.25f, 0.25f, 0.25f).multiply(Math.abs(direction.x*4),Math.abs(direction.y*4),Math.abs(direction.z*4));

            var addedVelocity = direction.scale(appliedForce);
            var result = velocity.add(addedVelocity).multiply(multiplier);
            double limit = Math.min(result.length(), appliedForce) * 2;
            entity.setDeltaMovement(result.normalize().scale(limit));
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(appliedEffect, effectDuration, effectAmplifier));
            }

            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(serverPlayer));
            }
        }
    }
}