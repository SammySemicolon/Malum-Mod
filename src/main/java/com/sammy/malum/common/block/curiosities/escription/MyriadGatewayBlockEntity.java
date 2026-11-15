package com.sammy.malum.common.block.curiosities.escription;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.MalumBlockItemStackHandler;
import com.sammy.malum.common.block.storage.MalumItemHolderBlockEntity;
import com.sammy.malum.common.data.attachment.MyriadGatewayData;
import com.sammy.malum.registry.common.MalumAttachmentTypes;
import com.sammy.malum.registry.common.block.MalumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Set;

import static com.sammy.malum.registry.common.MalumContent.Materials.CTHONIC_GOLD;
import static net.minecraft.core.registries.Registries.DIMENSION;


public class MyriadGatewayBlockEntity extends MalumItemHolderBlockEntity {

    //TODO: Move to a dimension resource key class
    public static final ResourceKey<Level> DESTINATION_DIMENSION = ResourceKey.create(DIMENSION, MalumMod.malumPath("canvas"));

    public static final float TUNNEL_OFFSET = 5f;
    public static final float TUNNEL_WIDTH = 3f;
    public static final float TUNNEL_HEIGHT = 3f;
    public static final float TUNNEL_DEPTH = 12f;

    public static final double FORWARD_PULL_STRENGTH = 0.08;
    public static final double CENTER_PULL_STRENGTH = 0.08;

    private boolean isActive = false;
    private int activeTicks = 0;
    private int activeTickDelta = 0;
    private AABB tunnelArea;

    public MyriadGatewayBlockEntity(BlockPos pos, BlockState state) {
        super(MalumBlockEntities.MYRIAD_GATEWAY.get(), pos, state);
        inventory = MalumBlockItemStackHandler.create(this, 1).onContentsChanged(this::updateActiveState).build();
        inventory.attachDisplayData(MyriadGatewayDisplayData::new);
    }

    @Override
    protected void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putBoolean("is_active", isActive);
        compound.putInt("active_ticks", activeTicks);
        compound.putInt("active_tick_delta", activeTickDelta);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider pRegistries) {
        super.loadAdditional(compound, pRegistries);
        isActive = compound.getBoolean("is_active");
        activeTicks = compound.getInt("active_ticks");
        activeTickDelta = compound.getInt("active_tick_delta");
    }

    public boolean isActive() {
        return isActive;
    }

    public AABB getTunnelArea() {
        if (tunnelArea == null) {
            tunnelArea = createTunnelArea();
        }
        return tunnelArea;
    }

    @Override
    public void serverTick(ServerLevel serverLevel) {
        if (!isActive) {
            if (activeTickDelta > 0) {
                activeTickDelta--;
            }
            return;
        }
        activeTicks++;
        if (activeTickDelta < 30) {
            activeTickDelta++;
        }

//        if (activeTicks >= MAX_ACTIVE_TICKS) {
//            getSuppliedInventory().extractItem(serverLevel, 1);
//            activeTicks = 0;
//            destroyTunnel();
//            setChanged();
//            return;
//        }
        tickEntities(serverLevel);
    }

    private void updateActiveState() {
        var stack = inventory.getStackInSlot(0);
        isActive = stack.is(CTHONIC_GOLD);
        activeTicks = 0;
        setChanged();
    }

    private void tickEntities(ServerLevel level) {
        var area = getTunnelArea();
        var affectedEntities = level.getEntities(null, area);
        for (Entity entity : affectedEntities) {
            if (entity.hasData(MalumAttachmentTypes.MYRIAD_GATEWAY)) {
                continue;
            }
            var data = new MyriadGatewayData(area, getDirection(), 0);
            entity.setData(MalumAttachmentTypes.MYRIAD_GATEWAY, data);
            entity.syncData(MalumAttachmentTypes.MYRIAD_GATEWAY);
        }
    }

//    private void createTunnel(Vec3 gatewayCenter, Vec3 playerPosition, BlockState state) {
//        Vec3 direction = gatewayCenter.subtract(playerPosition).multiply(1, 0, 1);
//
//        if (direction.lengthSqr() < 0.001) {
//            tunnelFacing = state.getValue(HORIZONTAL_FACING).getOpposite();
//        } else {
//            direction = direction.normalize();
//            tunnelFacing = Direction.getNearest(direction.x, 0, direction.z);
//        }
//
//        Vec3 forward = new Vec3(tunnelFacing.getStepX(), 0, tunnelFacing.getStepZ());
//
//        tunnelOrigin = gatewayCenter.subtract(forward.scale(TUNNEL_DEPTH)).add(0, -TUNNEL_HEIGHT / 2.0, 0);
//        setChanged();
//    }


    //TODO: This needs to be clientside logic, synchronized via data attachment.
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

    public AABB createTunnelArea() {
        var origin = getBlockPos();
        var direction = getDirection();
        var axis = direction.getAxis();
        double halfWidth = TUNNEL_WIDTH / 2.0;

        double x = origin.getX();
        double y = origin.getY();
        double z = origin.getZ();
        double endX = x + direction.getStepX() * TUNNEL_DEPTH;
        double endZ = z + direction.getStepZ() * TUNNEL_DEPTH;
        var forwardOffset = direction.step().mul(TUNNEL_OFFSET);

        double minX = Math.min(x, endX);
        double minZ = Math.min(z, endZ);

        double maxX = Math.max(x, endX) + 1;
        double maxY = y + TUNNEL_HEIGHT + 1;
        double maxZ = Math.max(z, endZ) + 1;
        if (axis.equals(Direction.Axis.X)) {
            minZ -= halfWidth;
            maxZ += halfWidth;
        } else {
            minX -= halfWidth;
            maxX += halfWidth;
        }
        AABB aabb = new AABB(minX, y, minZ, maxX, maxY, maxZ);
        aabb = aabb.move(forwardOffset);
        return aabb;
    }

    private @NotNull Direction getDirection() {
        return getBlockState().getValue(MyriadGatewayBlock.HORIZONTAL_FACING);
    }
}
