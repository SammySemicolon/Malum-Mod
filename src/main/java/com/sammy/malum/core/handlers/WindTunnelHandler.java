package com.sammy.malum.core.handlers;

import com.sammy.malum.*;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.SequencedBlockConnectionArray;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.WindTunnelBlock;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.*;
import com.sammy.malum.common.data.attachment.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.event.tick.*;

import java.util.*;
import java.util.function.*;

public class WindTunnelHandler {

    public static final float MAX_STRENGTH = 32;
    public static final ResourceLocation GRAVITY_MODIFIER_ID = MalumMod.malumPath("wind_tunnel_reduced_gravity");

    public static void entityTick(EntityTickEvent.Pre event) {
        var entity = event.getEntity();
        entity.getExistingData(MalumAttachmentTypes.WIND_TUNNEL_INFO).ifPresent(d -> d.tickData(entity));
        if (entity instanceof LivingEntity livingEntity) {
            updateLivingGravity(livingEntity);
        }
    }

    public static double modifyEntityGravity(Entity entity, double original) {
        return original * entity.getExistingData(MalumAttachmentTypes.WIND_TUNNEL_INFO).map(WindTunnelData::getGravityMultiplier).orElse(1f);
    }

    public static void updateLivingGravity(LivingEntity entity) {
        var gravity = entity.getAttribute(Attributes.GRAVITY);
        if (gravity != null) {
            if (gravity.hasModifier(GRAVITY_MODIFIER_ID)) {
                gravity.removeModifier(GRAVITY_MODIFIER_ID);
            }
            getGravityAttributeModifier(entity).ifPresent(gravity::addTransientModifier);
        }
    }

    public static Optional<AttributeModifier> getGravityAttributeModifier(LivingEntity entity) {
        if (!entity.hasData(MalumAttachmentTypes.WIND_TUNNEL_INFO)) {
            return Optional.empty();
        }
        var data = entity.getData(MalumAttachmentTypes.WIND_TUNNEL_INFO);
        float multiplier = data.getGravityMultiplier();
        if (multiplier >= 1) {
            return Optional.empty();
        }
        return Optional.of(new AttributeModifier(GRAVITY_MODIFIER_ID, multiplier - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
    }

    public static boolean modifyComponents(ServerLevel level, PrimaryArtificeBlockEntity connectionOwner, boolean isOpen, boolean isPowered) {
        var unbound = new ArrayList<SecondaryArtificeBlockEntity>();

        var connectionData = connectionOwner.getConnectionData();
        if (connectionData != null) {
            connectionData.unbind(level, unbound::add);
            connectionOwner.clearConnectionData();
        }

        var locatedGizmos = findGizmos(level, connectionOwner);
        if (locatedGizmos.isEmpty()) {
            return false;
        }

        var capturedBlocks = new HashSet<SecondaryArtificeBlockEntity>();
        Direction sharedDirection = null;

        for (BlockPos pos : locatedGizmos.keySet()) {
            var blockEntity = locatedGizmos.get(pos);
            var state = level.getBlockState(pos);
            state = state.setValue(ElementalArtificeBlock.OPEN, isOpen);
            state = state.setValue(ElementalArtificeBlock.POWERED, isPowered);
            level.setBlock(pos, state, 2);
            if (ElementalArtificeBlock.isPowered(state)) {
                blockEntity.bind(connectionOwner);
                capturedBlocks.add(blockEntity);
                unbound.remove(blockEntity);
            }

            sharedDirection = state.getValue(WindTunnelBlock.FACING);
        }
        for (SecondaryArtificeBlockEntity unpowered : unbound) {
            unpowered.unbind();
        }
        if (capturedBlocks.isEmpty()) {
            return false;
        }
        connectionOwner.gatherConnectionData(level, capturedBlocks);


        var startPos = getStartPos(connectionOwner);
        var connectionRoot = locatedGizmos.get(startPos);
        var array = SequencedBlockConnectionArray.create(connectionOwner, connectionRoot, sharedDirection, capturedBlocks);
        var baked = connectionOwner.bakeConnectionData(level, array);
        connectionOwner.setConnectionData(baked);
        return true;
    }

    public static Map<BlockPos, SecondaryArtificeBlockEntity> findGizmos(Level level, PrimaryArtificeBlockEntity owner) {
        var startPos = getStartPos(owner);

        if ((level.getBlockEntity(startPos) instanceof SecondaryArtificeBlockEntity connectionRoot)) {
            if (connectionRoot.isRemoved()) {
                return Collections.emptyMap();
            }
            return findGizmos(level, connectionRoot, t -> t.canBind(owner));
        }
        return Collections.emptyMap();
    }

    public static HashMap<BlockPos, SecondaryArtificeBlockEntity> findGizmos(Level level, SecondaryArtificeBlockEntity connectionRoot, Predicate<SecondaryArtificeBlockEntity> condition) {
        var startPos = connectionRoot.getBlockPos();
        var facing = connectionRoot.getBlockState().getValue(ElementalArtificeBlock.FACING);
        var visited = new HashSet<BlockPos>();
        var result = new HashMap<BlockPos, SecondaryArtificeBlockEntity>();
        var queue = new ArrayDeque<BlockPos>();
        if (condition.test(connectionRoot)) {
            result.put(startPos, connectionRoot);
            queue.add(startPos);
            visited.add(startPos);
        }

        var directionsToCheck = new ArrayList<>(List.of(Direction.values()));
        directionsToCheck.remove(facing);
        directionsToCheck.remove(facing.getOpposite());
        var mutable = startPos.mutable();
        while (!queue.isEmpty()) {
            var pos = queue.poll();
            for (Direction direction : directionsToCheck) {
                mutable.set(pos).move(direction);
                if (!visited.contains(mutable) && level.getBlockEntity(mutable) instanceof SecondaryArtificeBlockEntity next) {
                    if (next.isRemoved()) {
                        continue;
                    }
                    var otherFacing = next.getBlockState().getValue(SecondaryArtificeBlock.FACING);
                    if (otherFacing != facing) {
                        continue;
                    }
                    if (condition.test(next)) {
                        var immutable = mutable.immutable();
                        visited.add(immutable);
                        result.put(immutable, next);
                        queue.add(immutable);
                    }
                }
            }
        }
        return result;
    }

    public static BlockPos getStartPos(PrimaryArtificeBlockEntity owner) {
        var ownerState = owner.getBlockState();
        var ownerPos = owner.getBlockPos();
        var facing = ownerState.getValue(ElementalArtificeBlock.FACING);
        return ownerPos.relative(facing);
    }
}
