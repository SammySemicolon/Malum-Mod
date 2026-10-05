package com.sammy.malum.common.data.listener.banner;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sammy.malum.core.systems.rite.SpiritRiteType;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteEffect;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import net.minecraft.resources.ResourceLocation;

public record MalumBannerPatternType(ResourceLocation id, ResourceLocation texture) {

    public static final Codec<MalumBannerPatternType> DIRECT_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(MalumBannerPatternType::id),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(MalumBannerPatternType::texture)
    ).apply(instance, MalumBannerPatternType::new));

}
