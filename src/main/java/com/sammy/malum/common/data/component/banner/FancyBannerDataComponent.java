package com.sammy.malum.common.data.component.banner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data.listener.banner.MalumBannerPatternReloadListener;
import com.sammy.malum.common.data.listener.banner.MalumBannerPatternType;
import com.sammy.malum.registry.common.MalumContent;
import com.sammy.malum.registry.common.item.MalumDataComponents;
import io.netty.buffer.ByteBuf;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public record FancyBannerDataComponent(MalumBannerPatternType type) {

    public static Codec<FancyBannerDataComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MalumBannerPatternReloadListener.DATA.getLookupCodec().fieldOf("pattern").forGetter(FancyBannerDataComponent::type)
    ).apply(instance, FancyBannerDataComponent::new));

    public static StreamCodec<ByteBuf, FancyBannerDataComponent> STREAM_CODEC = ByteBufCodecs.fromCodec(FancyBannerDataComponent.CODEC);
}