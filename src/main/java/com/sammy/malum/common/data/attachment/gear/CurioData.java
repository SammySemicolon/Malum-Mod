package com.sammy.malum.common.data.attachment.gear;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public class CurioData {

    public static Codec<CurioData> CODEC = RecordCodecBuilder.create(obj -> obj.group(
            Codec.INT.optionalFieldOf("watcherNecklaceCooldown", 0).forGetter(c -> c.watcherNecklaceCooldown),
            Codec.INT.optionalFieldOf("hiddenBladeNecklaceCooldown", 0).forGetter(c -> c.hiddenBladeNecklaceCooldown)
    ).apply(obj, CurioData::new));

    public static StreamCodec<ByteBuf, CurioData> STREAM_CODEC = ByteBufCodecs.fromCodec(CurioData.CODEC);

    public int watcherNecklaceCooldown;
    public int hiddenBladeNecklaceCooldown;

    public CurioData() {
    }

    public CurioData(int watcherNecklaceCooldown, int hiddenBladeNecklaceCooldown) {
        this.watcherNecklaceCooldown = hiddenBladeNecklaceCooldown;
        this.hiddenBladeNecklaceCooldown = hiddenBladeNecklaceCooldown;
    }
}