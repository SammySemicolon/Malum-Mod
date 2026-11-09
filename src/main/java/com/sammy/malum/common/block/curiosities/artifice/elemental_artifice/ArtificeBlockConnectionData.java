package com.sammy.malum.common.block.curiosities.artifice.elemental_artifice;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.aerial.WindTunnelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArtificeBlockConnectionData {

    public static final Codec<AABB> AABB_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.DOUBLE.fieldOf("min_x").forGetter(aabb -> aabb.minX),
            Codec.DOUBLE.fieldOf("min_y").forGetter(aabb -> aabb.minY),
            Codec.DOUBLE.fieldOf("min_z").forGetter(aabb -> aabb.minZ),
            Codec.DOUBLE.fieldOf("max_x").forGetter(aabb -> aabb.maxX),
            Codec.DOUBLE.fieldOf("max_y").forGetter(aabb -> aabb.maxY),
            Codec.DOUBLE.fieldOf("max_z").forGetter(aabb -> aabb.maxZ)
    ).apply(instance, AABB::new));

    public static final Codec<ArtificeBlockConnectionData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            SequencedBlockConnectionArray.CODEC.fieldOf("array").forGetter(data -> data.array),
            AABB_CODEC.fieldOf("defined_area").forGetter(data -> data.definedArea)
    ).apply(instance, ArtificeBlockConnectionData::new));

    protected final SequencedBlockConnectionArray array;
    protected final AABB definedArea;

    public ArtificeBlockConnectionData(SequencedBlockConnectionArray array, float padding, int length) {
        this.array = array;
        this.definedArea = array.getTotalArea(length, padding);
    }

    public ArtificeBlockConnectionData(SequencedBlockConnectionArray array, AABB definedArea) {
        this.array = array;
        this.definedArea = definedArea;
    }

    public SequencedBlockConnectionArray getArray() {
        return array;
    }

    public AABB getDefinedArea() {
        return definedArea;
    }

    public List<Entity> findAffectedEntities(ServerLevel level) {
        var entities = level.getEntities(null, definedArea);
        var matching = new ArrayList<Entity>();
        for (Entity entity : entities) {
            if (array.isOutOfBounds(entity)) {
                continue;
            }
            matching.add(entity);
        }
        return matching;
    }

    public void unbind(ServerLevel level, Consumer<WindTunnelBlockEntity> acceptor) {
        var blocks = getArray().getConnectedBlocks();
        for (BlockPos block : blocks) {
            if (level.getBlockEntity(block) instanceof WindTunnelBlockEntity boundTunnel) {
                acceptor.accept(boundTunnel);
            }
        }
    }
}