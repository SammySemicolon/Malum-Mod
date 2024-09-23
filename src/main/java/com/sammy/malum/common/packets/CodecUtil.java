package com.sammy.malum.common.packets;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

public interface CodecUtil {
    static <T> T decodeNBT(Codec<T> codec, Tag input) {
        return codec.decode(NbtOps.INSTANCE, input).getOrThrow().getFirst();
    }

    static <T> Tag encodeNBT(Codec<T> codec, T input) {
        return codec.encodeStart(NbtOps.INSTANCE, input).getOrThrow();
    }
}
