package com.sammy.malum.common.block.curiosities.artifice.elemental_artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.PrimaryArtificeBlockEntity;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.base.SecondaryArtificeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class SequencedBlockConnectionArray {

    public static final Codec<SequencedBlockConnectionArray> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Direction.CODEC.fieldOf("shared_direction").forGetter(data -> data.sharedDirection),
            BlockPos.CODEC.fieldOf("owner").forGetter(data -> data.connectionOwner),
            BlockPos.CODEC.fieldOf("root").forGetter(data -> data.connectionRoot),
            Codec.INT.fieldOf("coordinate_on_shared_axis").forGetter(data -> data.coordinateOnSharedAxis),
            Codec.INT_STREAM.xmap(IntStream::toArray, Arrays::stream).fieldOf("connection_array").forGetter(data -> data.connectionArray)
    ).apply(instance, SequencedBlockConnectionArray::new));

    protected final Direction sharedDirection;
    protected final int coordinateOnSharedAxis;

    protected final BlockPos connectionOwner;
    protected final BlockPos connectionRoot;
    protected final int[] connectionArray;

    public static SequencedBlockConnectionArray create(PrimaryArtificeBlockEntity connectionOwner, SecondaryArtificeBlockEntity connectionRoot, Direction sharedDirection, Set<SecondaryArtificeBlockEntity> capturedBlocks) {
        var ownerPos = connectionOwner.getBlockPos();
        var rootPos = connectionRoot.getBlockPos();

        Direction.Axis axis = sharedDirection.getAxis();
        int rootX = rootPos.getX();
        int rootY = rootPos.getY();
        int rootZ = rootPos.getZ();
        int sharedCoordinate = switch (axis) {
            case X -> rootX;
            case Y -> rootY;
            case Z -> rootZ;
        };
        int[] connectionArray = new int[capturedBlocks.size()];
        int index = 0;
        for (SecondaryArtificeBlockEntity capturedBlock : capturedBlocks) {
            var pos = capturedBlock.getBlockPos();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            int value = switch (axis) {
                case X -> packOffsets(y - rootY, z - rootZ);
                case Y -> packOffsets(x - rootX, z - rootZ);
                case Z -> packOffsets(x - rootX, y - rootY);
            };
            connectionArray[index] = value;
            index++;
        }

        return new SequencedBlockConnectionArray(sharedDirection, ownerPos, rootPos, sharedCoordinate, connectionArray);
    }

    public SequencedBlockConnectionArray(Direction sharedDirection, BlockPos connectionOwner, BlockPos connectionRoot, int coordinateOnSharedAxis, int[] connectionArray) {
        this.sharedDirection = sharedDirection;
        this.connectionOwner = connectionOwner;
        this.connectionRoot = connectionRoot;
        this.coordinateOnSharedAxis = coordinateOnSharedAxis;
        this.connectionArray = connectionArray;
    }

    public BlockPos getConnectionOwner() {
        return connectionOwner;
    }

    public BlockPos getConnectionRoot() {
        return connectionRoot;
    }

    public Direction getSharedDirection() {
        return sharedDirection;
    }

    public int getCoordinateOnSharedAxis() {
        return coordinateOnSharedAxis;
    }

    public Set<BlockPos> getConnectedBlocks() {
        var blocks = new HashSet<BlockPos>();

        for (int packed : connectionArray) {
            blocks.add(unpackBlockPos(packed));
        }

        return blocks;
    }

    public AABB getTotalArea(int length, float padding) {
        var axis = sharedDirection.getAxis();
        int minX = connectionRoot.getX(), minY = connectionRoot.getY(), minZ = connectionRoot.getZ();
        int maxX = minX, maxY = minY, maxZ = minZ;

        for (int packed : connectionArray) {
            int a = unpackFirst(packed);
            int b = unpackSecond(packed);

            int x = connectionRoot.getX();
            int y = connectionRoot.getY();
            int z = connectionRoot.getZ();

            switch (axis) {
                case X -> {
                    y += a;
                    z += b;
                }
                case Y -> {
                    x += a;
                    z += b;
                }
                case Z -> {
                    x += a;
                    y += b;
                }
            }
            if (x < minX) minX = x;
            if (y < minY) minY = y;
            if (z < minZ) minZ = z;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
            if (z > maxZ) maxZ = z;
        }
        AABB area = new AABB(minX, minY, minZ, maxX + 1, maxY + 1, maxZ + 1);

        int x = sharedDirection.getStepX();
        int y = sharedDirection.getStepY();
        int z = sharedDirection.getStepZ();
        var offset = new Vec3(x, y, z).scale(length);
        return area.expandTowards(offset).inflate(padding);
    }

    public boolean isOutOfBounds(Entity entity) {
        var position = entity.position();
        var center = position.add(0, entity.getBbHeight() / 2f, 0);
        return isOutOfBounds(center);
    }

    public boolean isOutOfBounds(Vec3 pos) {
        var axis = sharedDirection.getAxis();

        boolean success = false;
        for (int packed : connectionArray) {
            var offsetPos = unpackBlockPos(packed).getCenter();
            double distance = switch (axis) {
                case X -> Math.hypot(pos.y - offsetPos.y, pos.z - offsetPos.z);
                case Y -> Math.hypot(pos.x - offsetPos.x, pos.z - offsetPos.z);
                case Z -> Math.hypot(pos.x - offsetPos.x, pos.y - offsetPos.y);
            };

            if (distance < 0.75f) {
                success = true;
            }
        }

        return !success;
    }

    public BlockPos unpackBlockPos(int packed) {
        var axis = sharedDirection.getAxis();
        int a = unpackFirst(packed);
        int b = unpackSecond(packed);

        return switch (axis) {
            case X -> connectionRoot.offset(0, a, b);
            case Y -> connectionRoot.offset(a, 0, b);
            case Z -> connectionRoot.offset(a, b, 0);
        };
    }

    public static int unpackFirst(int packed) {
        return (short) (packed >> 16);
    }

    public static int unpackSecond(int packed) {
        return (short) packed;
    }

    public static int packOffsets(int first, int second) {
        return ((first & 0xFFFF) << 16) | (second & 0xFFFF);
    }
}
