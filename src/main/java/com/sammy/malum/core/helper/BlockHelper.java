package com.sammy.malum.core.helper;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Predicate;

public class BlockHelper {
    public static BlockState getBlockStateWithExistingProperties(BlockState oldState, BlockState newState) {
        BlockState finalState = newState;
        for (Property<?> property : oldState.getProperties()) {
            if (newState.hasProperty(property)) {
                finalState = newStateWithOldProperty(oldState, finalState, property);
            }
        }
        return finalState;
    }

    public static BlockState setBlockStateWithExistingProperties(Level level, BlockPos pos, BlockState newState, int flags) {
        BlockState oldState = level.getBlockState(pos);
        BlockState finalState = getBlockStateWithExistingProperties(oldState, newState);
        level.sendBlockUpdated(pos, oldState, finalState, flags);
        level.setBlock(pos, finalState, flags);
        return finalState;
    }

    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, Property<T> property) {
        return newState.setValue(property, oldState.getValue(property));
    }

    public static void saveBlockPos(CompoundTag compoundNBT, BlockPos pos) {
        compoundNBT.putInt("X", pos.getX());
        compoundNBT.putInt("Y", pos.getY());
        compoundNBT.putInt("Z", pos.getZ());
    }

    public static void saveBlockPos(CompoundTag compoundNBT, BlockPos pos, String extra) {
        compoundNBT.putInt(extra + "X", pos.getX());
        compoundNBT.putInt(extra + "Y", pos.getY());
        compoundNBT.putInt(extra + "Z", pos.getZ());
    }

    public static BlockPos loadBlockPos(CompoundTag tag) {
        return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }

    public static BlockPos loadBlockPos(CompoundTag tag, String extra) {
        return new BlockPos(tag.getInt(extra + "X"), tag.getInt(extra + "Y"), tag.getInt(extra + "Z"));
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, Level level, BlockPos pos, int range, Predicate<T> predicate) {
        return getBlockEntities(type, level, pos, range, range, range, predicate);
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, Level level, BlockPos pos, int x, int y, int z, Predicate<T> predicate) {
        ArrayList<T> blockEntities = getBlockEntities(type, level, pos, x, y, z);
        blockEntities.removeIf(b -> !predicate.test(b));
        return blockEntities;
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, Level level, BlockPos pos, int range) {
        return getBlockEntities(type, level, pos, range, range, range);
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, Level level, BlockPos pos, int x, int y, int z) {
        return getBlockEntities(type, level, pos, -x, -y, -z, x, y, z);
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, Level level, BlockPos pos, int x1, int y1, int z1, int x2, int y2, int z2) {
        return getBlockEntities(type, level, new AABB(pos.getX() + x1, pos.getY() + y1, pos.getZ() + z1, pos.getX() + x2, pos.getY() + y2, pos.getZ() + z2));
    }

    public static <T> ArrayList<T> getBlockEntities(Class<T> type, Level world, AABB bb) {
        ArrayList<T> tileList = new ArrayList<>();
        for (int i = (int) Math.floor(bb.minX); i < (int) Math.ceil(bb.maxX) + 16; i += 16) {
            for (int j = (int) Math.floor(bb.minZ); j < (int) Math.ceil(bb.maxZ) + 16; j += 16) {
                ChunkAccess c = world.getChunk(new BlockPos(i, 0, j));
                Set<BlockPos> tiles = c.getBlockEntitiesPos();
                for (BlockPos p : tiles)
                    if (bb.contains(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5)) {
                        BlockEntity t = world.getBlockEntity(p);
                        if (type.isInstance(t)) {
                            tileList.add((T) t);
                        }
                    }
            }
        }
        return tileList;
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int range, Predicate<BlockPos> predicate) {
        return getBlocks(pos, range, range, range, predicate);
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x, int y, int z, Predicate<BlockPos> predicate) {
        ArrayList<BlockPos> blocks = getBlocks(pos, x, y, z);
        blocks.removeIf(b -> !predicate.test(b));
        return blocks;
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x, int y, int z) {
        return getBlocks(pos, -x, -y, -z, x, y, z);
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x1, int y1, int z1, int x2, int y2, int z2) {
        ArrayList<BlockPos> positions = new ArrayList<>();
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    positions.add(pos.offset(x, y, z));
                }
            }
        }
        return positions;
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int range, Predicate<BlockPos> predicate) {
        return getPlaneOfBlocks(pos, range, range, predicate);
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x, int z, Predicate<BlockPos> predicate) {
        ArrayList<BlockPos> blocks = getPlaneOfBlocks(pos, x, z);
        blocks.removeIf(b -> !predicate.test(b));
        return blocks;
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x, int z) {
        return getPlaneOfBlocks(pos, -x, -z, x, z);
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x1, int z1, int x2, int z2) {
        ArrayList<BlockPos> positions = new ArrayList<>();
        for (int x = x1; x <= x2; x++) {
            for (int z = z1; z <= z2; z++) {
                positions.add(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z));
            }
        }
        return positions;
    }

    public static void updateState(Level level, BlockPos pos) {
        updateState(level.getBlockState(pos), level, pos);
    }

    public static void updateState(BlockState state, Level level, BlockPos pos) {
        level.sendBlockUpdated(pos, state, state, 2);
        level.blockEntityChanged(pos);
    }

    public static void updateAndNotifyState(Level level, BlockPos pos) {
        updateAndNotifyState(level.getBlockState(pos), level, pos);
    }

    public static void updateAndNotifyState(BlockState state, Level level, BlockPos pos) {
        updateState(state, level, pos);
        state.updateNeighbourShapes(level, pos, 2);
        level.updateNeighbourForOutputSignal(pos, state.getBlock());
    }
}