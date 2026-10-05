package com.sammy.malum.common.data.listener.banner;

import com.mojang.serialization.Codec;
import com.sammy.malum.core.systems.rite.SpiritRiteType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import team.lodestar.lodestone.modules.toolkit.reload_listener.CodecBasedReloadListener;

public class MalumBannerPatternReloadListener extends CodecBasedReloadListener<ResourceLocation, MalumBannerPatternType> {

    public static final String DIRECTORY = "malum_data/banner";

    public static final MalumBannerPatternReloadListener DATA = new MalumBannerPatternReloadListener();

    public MalumBannerPatternReloadListener() {
        super(DIRECTORY);
    }

    public static void register(AddReloadListenerEvent event) {
        event.addListener(DATA);
    }

    @Override
    public Codec<ResourceLocation> getKeyCodec() {
        return ResourceLocation.CODEC;
    }

    @Override
    public Codec<MalumBannerPatternType> getCodec() {
        return MalumBannerPatternType.DIRECT_CODEC;
    }

    @Override
    public ResourceLocation getID(MalumBannerPatternType instance) {
        return instance.id();
    }
}