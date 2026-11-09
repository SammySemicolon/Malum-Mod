package com.sammy.malum.common.data.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.common.block.curiosities.artifice.elemental_artifice.ArtificeBlockConnectionData;
import com.sammy.malum.common.block.curiosities.escription.MyriadGatewayBlockEntity;
import com.sammy.malum.common.item.curiosities.weapons.greatsword.VindictiveBrandSwordItem;
import com.sammy.malum.registry.common.MalumAttachmentTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.Set;

public class MyriadGatewayData {

    public static Codec<MyriadGatewayData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            ArtificeBlockConnectionData.AABB_CODEC.fieldOf("area").forGetter(c -> c.area),
            Direction.CODEC.fieldOf("direction").forGetter(c -> c.direction),
            Codec.INT.optionalFieldOf("duration", 0).forGetter(c -> c.duration)
    ).apply(obj, MyriadGatewayData::new));

    public static StreamCodec<ByteBuf, MyriadGatewayData> STREAM_CODEC = ByteBufCodecs.fromCodec(MyriadGatewayData.CODEC);

    public AABB area;
    public Direction direction;
    public int duration;

    public MyriadGatewayData() {
    }

    public MyriadGatewayData(AABB area, Direction direction, int duration) {
        this.area = area;
        this.direction = direction;
        this.duration = duration;
    }

    public static void entityTick(EntityTickEvent.Pre event) {
        if (event.getEntity() instanceof Player player) {
            player.getExistingData(MalumAttachmentTypes.MYRIAD_GATEWAY).ifPresent(d -> d.tickData(player));
        }
    }

    public void tickData(Entity entity) {
        var boundingBox = entity.getBoundingBox();
        var level = entity.level();
        var position = entity.position();

        if (level instanceof ServerLevel serverLevel) {
            if (!boundingBox.intersects(area)) {
                entity.removeData(MalumAttachmentTypes.MYRIAD_GATEWAY);
                return;
            }
            double progress = getProgress(position);
            if (progress >= 0.9f) {
                teleportEntity(serverLevel, entity);
            }
            return;
        }
        if (entity instanceof Player player) {
            double progress = getProgress(position);
            player.displayClientMessage(Component.literal("" + progress), true);
        }
    }


    private void teleportEntity(ServerLevel level, Entity entity) {
        var destination = level.getServer().getLevel(MyriadGatewayBlockEntity.DESTINATION_DIMENSION);

        if (destination == null) {
            return;
        }

        entity.teleportTo(destination, 0, 108, 0, Set.of(), 0, 0);
    }

    private double getProgress(Vec3 position) {
        double xDelta = (position.x - area.minX) / (area.maxX - area.minX);
        double zDelta = (position.z - area.minZ) / (area.maxZ - area.minZ);
        xDelta = Mth.clamp(xDelta, 0, 1);
        zDelta = Mth.clamp(zDelta, 0, 1);

        double delta;
        var axis = direction.getAxis();
        if (axis.equals(Direction.Axis.X)) {
            delta = direction.getStepX() * xDelta;
        }
        else {
            delta = direction.getStepZ() * zDelta;
        }
        return delta;
    }
}