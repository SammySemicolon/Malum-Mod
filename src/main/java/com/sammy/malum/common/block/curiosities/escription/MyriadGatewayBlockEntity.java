package com.sammy.malum.common.block.curiosities.escription;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.storage.MalumItemHolderBlockEntity;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

import static com.sammy.malum.registry.common.MalumContent.Materials.CTHONIC_GOLD;
import static net.minecraft.core.registries.Registries.DIMENSION;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;


public class MyriadGatewayBlockEntity extends MalumItemHolderBlockEntity {
    private static final double TUNNEL_WIDTH = 3.0;
    private static final double TUNNEL_HEIGHT = 3.0;
    private static final double TUNNEL_DEPTH = 12.0;
    private static final double TELEPORT_DISTANCE = TUNNEL_DEPTH * 0.75;
    private static final int MAX_ACTIVE_TICKS = 20 * 30;
    private static final double FORWARD_PULL_STRENGTH = 0.08;
    private static final double CENTER_PULL_STRENGTH = 0.08;

    private int activeTicks = 0;

    private Direction tunnelFacing = null;
    private Vec3 tunnelOrigin = null;

    private static final ResourceKey<Level> DESTINATION_DIMENSION =
            ResourceKey.create(DIMENSION, MalumMod.malumPath("canvas"));

    public MyriadGatewayBlockEntity(BlockPos pos, BlockState state) {
        super(MalumBlockEntities.MYRIAD_GATEWAY.get(), pos, state);
        inventory.attachDisplayData(MyriadGatewayDisplayData::new);
    }

    public boolean isActive() {
        var stack = getSuppliedInventory().getStackInSlot(0);
        return !stack.isEmpty() && stack.getItem().equals(CTHONIC_GOLD.asItem());
    }

    @Override
    public void serverTick(ServerLevel serverLevel) {
        BlockPos pos = worldPosition;
        BlockState state = getBlockState();

        // Reset everything when the gateway is deactivated.
        if (!isActive()) {
            activeTicks = 0;
            destroyTunnel();
            return;
        }

        activeTicks++;

        if (activeTicks >= MAX_ACTIVE_TICKS) {
            getSuppliedInventory().extractItem(serverLevel, 1);
            activeTicks = 0;
            destroyTunnel();
            setChanged();
            return;
        }

        Vec3 gatewayCenter = Vec3.atCenterOf(pos);

        // Create tunnel once, when the first player is found
        if (!hasTunnel()) {
            for (ServerPlayer player : serverLevel.players()) {
                if (player.distanceToSqr(gatewayCenter) > 12 * 12) {
                    continue;
                }
                createTunnel(gatewayCenter, player.position(), state);
                // the first player determines the tunnel direction
                break;
            }
        }

        // No tunnel until a player has been found
        if (!hasTunnel()) {
            return;
        }

        // Process every entity inside - replace with wind tunnel stuff
        AABB tunnel = getTunnelArea(tunnelOrigin, tunnelFacing);

        for (Entity entity : serverLevel.getEntities(null, tunnel)) {
            Vec3 relative = entity.position().subtract(tunnelOrigin);
            double progress = relative.x * tunnelFacing.getStepX() + relative.z * tunnelFacing.getStepZ();
            double lateral = Math.abs(relative.x * -tunnelFacing.getStepZ() + relative.z * tunnelFacing.getStepX());

            if (lateral > TUNNEL_WIDTH / 2.0) {
                continue;
            }

            if (entity.getBoundingBox().maxY < tunnelOrigin.y
                    || entity.getBoundingBox().minY > tunnelOrigin.y + TUNNEL_HEIGHT) {
                continue;
            }

            if (progress < 0 || progress > TUNNEL_DEPTH) {
                continue;
            }

            if (progress >= TELEPORT_DISTANCE) {
                teleportEntity(entity, serverLevel);
                continue;
            }

            applyGatewayInfluence(entity, tunnelOrigin, tunnelFacing, progress);
        }
    }

    private boolean hasTunnel() {
        return tunnelFacing != null && tunnelOrigin != null;
    }

    private void createTunnel(Vec3 gatewayCenter, Vec3 playerPosition, BlockState state) {
        Vec3 direction = gatewayCenter.subtract(playerPosition).multiply(1, 0, 1);

        if (direction.lengthSqr() < 0.001) {
            tunnelFacing = state.getValue(HORIZONTAL_FACING).getOpposite();
        } else {
            direction = direction.normalize();
            tunnelFacing = Direction.getNearest(direction.x, 0, direction.z);
        }

        Vec3 forward = new Vec3(tunnelFacing.getStepX(), 0, tunnelFacing.getStepZ());

        tunnelOrigin = gatewayCenter.subtract(forward.scale(TUNNEL_DEPTH)).add(0, -TUNNEL_HEIGHT / 2.0, 0);
        setChanged();
    }

    private void destroyTunnel() {
        tunnelFacing = null;
        tunnelOrigin = null;
    }

    private static void applyGatewayInfluence(Entity entity, Vec3 tunnelOrigin, Direction facing, double progress) {
        Vec3 forward = new Vec3(facing.getStepX(), 0, facing.getStepZ());
        Vec3 movement = entity.getDeltaMovement();

        double progressRatio = Math.clamp(progress / TUNNEL_DEPTH, 0.0, 1.0);
        double slowdown = 1.0 - (progressRatio * 0.85);
        double forwardSpeed = movement.dot(forward);

        if (forwardSpeed > 0) {
            movement = movement.subtract(forward.scale(forwardSpeed * (1.0 - slowdown)));
        }
        movement = movement.add(forward.scale(FORWARD_PULL_STRENGTH));

        Vec3 center = tunnelOrigin.add(forward.scale(progress)).add(0, TUNNEL_HEIGHT / 2.0 - entity.getBbHeight() / 2.0, 0);
        Vec3 toCenter = center.subtract(entity.position());
        Vec3 centerVelocity = toCenter.scale(CENTER_PULL_STRENGTH);

        movement = movement.add(centerVelocity);

        entity.setDeltaMovement(movement);
        entity.hurtMarked = true;
    }

    private static AABB getTunnelArea(Vec3 origin, Direction facing) {
        double halfWidth = TUNNEL_WIDTH / 2.0;
        double endX = origin.x + facing.getStepX() * TUNNEL_DEPTH;
        double endZ = origin.z + facing.getStepZ() * TUNNEL_DEPTH;

        return new AABB(
                Math.min(origin.x, endX) - (facing.getAxis() == Direction.Axis.Z ? halfWidth : 0),
                origin.y + 3,
                Math.min(origin.z, endZ) - (facing.getAxis() == Direction.Axis.X ? halfWidth : 0),
                Math.max(origin.x, endX) + (facing.getAxis() == Direction.Axis.Z ? halfWidth : 0),
                origin.y + TUNNEL_HEIGHT + 3,
                Math.max(origin.z, endZ) + (facing.getAxis() == Direction.Axis.X ? halfWidth : 0)
        );
    }

    private static void teleportEntity(Entity entity, ServerLevel source) {
        ServerLevel destination = source.getServer().getLevel(DESTINATION_DIMENSION);

        if (destination == null) {
            return;
        }

        entity.teleportTo(destination, 0, 108, 0, Set.of(), 0, 0);
    }
}
