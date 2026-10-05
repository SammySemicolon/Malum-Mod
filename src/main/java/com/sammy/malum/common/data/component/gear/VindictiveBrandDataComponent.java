package com.sammy.malum.common.data.component.gear;

import com.mojang.serialization.*;
import io.netty.buffer.*;
import net.minecraft.network.codec.*;

public record VindictiveBrandDataComponent() {

    public static final VindictiveBrandDataComponent UNIT = new VindictiveBrandDataComponent();
    public static final Codec<VindictiveBrandDataComponent> CODEC = Codec.unit(VindictiveBrandDataComponent::new);

    public static StreamCodec<ByteBuf, VindictiveBrandDataComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(VindictiveBrandDataComponent.CODEC);

}
