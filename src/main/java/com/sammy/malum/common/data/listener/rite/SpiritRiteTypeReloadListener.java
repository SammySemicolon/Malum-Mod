package com.sammy.malum.common.data.listener.rite;

import com.mojang.serialization.*;
import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.systems.rite.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.event.*;
import team.lodestar.lodestone.modules.toolkit.reload_listener.*;

public class SpiritRiteTypeReloadListener extends CodecBasedReloadListener<ResourceLocation, SpiritRiteType> {

    public static final String DIRECTORY = "malum_data/spirit_rites";

    public static final SpiritRiteTypeReloadListener DATA = new SpiritRiteTypeReloadListener();

    public SpiritRiteTypeReloadListener() {
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
    public Codec<SpiritRiteType> getCodec() {
        return SpiritRiteType.DIRECT_CODEC;
    }

    @Override
    public ResourceLocation getID(SpiritRiteType instance) {
        return instance.getId();
    }

    public SpiritRiteType findMatching(ServerLevel level, TotemBaseBlockEntity totemBase) {
        for (SpiritRiteType rite : data.values()) {
            if (rite.matches(level, totemBase)) {
                return rite;
            }
        }

        return null;
    }
}