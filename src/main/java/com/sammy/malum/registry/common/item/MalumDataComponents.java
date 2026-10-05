package com.sammy.malum.registry.common.item;

import com.mojang.serialization.Codec;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data.component.*;
import com.sammy.malum.common.data.component.banner.FancyBannerDataComponent;
import com.sammy.malum.common.data.component.gear.*;
import com.sammy.malum.common.data.component.pouch.*;
import com.sammy.malum.common.data.component.soulstone.*;
import com.sammy.malum.common.item.curiosities.TemporarilyDisabledItem.Disabled;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.component.DyedItemColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;
import java.util.function.UnaryOperator;

import static vectorwing.farmersdelight.common.registry.ModDataComponents.DATA_COMPONENTS;

public class MalumDataComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MalumMod.MALUM);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulstoneBudDataComponent>> SOULSTONE_BUD_DATA = register("soulstone_bud_data", SoulstoneBudDataComponent.CODEC, SoulstoneBudDataComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulTagDataComponent>> SOUL_TAG_DATA = register("soul_tag_data", SoulTagDataComponent.CODEC, SoulTagDataComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<GeasDataComponent>> GEAS_EFFECT = register("geas_effect", GeasDataComponent.CODEC, GeasDataComponent.STREAM_CODEC);
    
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulwovenPouchContentsComponent>> SOULWOVEN_POUCH_CONTENTS = register("soulwoven_pouch_data", SoulwovenPouchContentsComponent.CODEC, SoulwovenPouchContentsComponent.STREAM_CODEC);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<RavenousPouchContentsComponent>> RAVENOUS_POUCH_CONTENTS = register("ravenous_pouch_data", RavenousPouchContentsComponent.CODEC, RavenousPouchContentsComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<FancyBannerDataComponent>> FANCY_BANNER_PATTERN = register("fancy_banner_pattern", FancyBannerDataComponent.CODEC, FancyBannerDataComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SoulwovenBannerPatternDataComponent>> SOULWOVEN_BANNER_PATTERN = register("soulwoven_banner_pattern", SoulwovenBannerPatternDataComponent.CODEC, SoulwovenBannerPatternDataComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ArtificeAugmentDataComponent>> ARTIFICE_AUGMENT = register("artifice_augment", ArtificeAugmentDataComponent.CODEC, ArtificeAugmentDataComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<VindictiveBrandDataComponent>> VINDICTIVE_BRAND_UNLEASHED = register("vindictive_brand_state", VindictiveBrandDataComponent.CODEC, VindictiveBrandDataComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemSkinComponent>> ITEM_SKIN = register("item_skin", ItemSkinComponent.CODEC, ItemSkinComponent.STREAM_CODEC);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<ItemSkinComponent>> APPLIED_ITEM_SKIN = register("applied_item_skin", ItemSkinComponent.CODEC, ItemSkinComponent.STREAM_CODEC);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<CatalystFlingerStateComponent>> CATALYST_LOBBER_STATE = register("catalyst_flinger_state", CatalystFlingerStateComponent.CODEC, CatalystFlingerStateComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SpellweavingToolStateComponent>> SPELLWEAVING_TOOL_STATE = register("spellweaving_tool_state", SpellweavingToolStateComponent.CODEC, SpellweavingToolStateComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SpiritJarContentsComponent>> SPIRIT_JAR_CONTENTS = register("spirit_jar_contents", SpiritJarContentsComponent.CODEC, SpiritJarContentsComponent.STREAM_CODEC);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<DyedItemColor>> SECONDARY_DYED_COLOR = register("secondary_dye_color", DyedItemColor.CODEC, DyedItemColor.STREAM_CODEC);
    
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Disabled>> DISABLED = register("disabled_item_storage", Disabled.CODEC, Disabled.STREAM_CODEC);

    static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Codec<T> codec, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        return COMPONENTS.register(name, () -> DataComponentType.<T>builder().persistent(codec).networkSynchronized(streamCodec).build());
    }
}