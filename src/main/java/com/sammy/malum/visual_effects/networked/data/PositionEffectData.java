package com.sammy.malum.visual_effects.networked.data;

import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.phys.*;

public class PositionEffectData {

    public final double posX;
    public final double posY;
    public final double posZ;

    public PositionEffectData(FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public PositionEffectData(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public PositionEffectData(Entity entity) {
        this(entity.getX(), entity.getY() + entity.getBbHeight() / 2f, entity.getZ());
    }
    public PositionEffectData(Vec3 pos) {
        this(pos.x, pos.y, pos.z);
    }

    public PositionEffectData(double posX, double posY, double posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeDouble(posX);
        buf.writeDouble(posY);
        buf.writeDouble(posZ);
    }

    public BlockPos getAsBlockPos() {
        return new BlockPos(posX, posY, posZ);
    }
}