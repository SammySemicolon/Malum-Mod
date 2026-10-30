package com.sammy.malum.common.data.listener.reaping;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.rite.SpiritRiteType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import team.lodestar.lodestone.modules.toolkit.reload_listener.CodecBasedReloadListener;

import java.util.*;

public class ReapingDataReloadListener extends CodecBasedReloadListener<ResourceLocation, ReapingDropsData> {

    public static final String DIRECTORY = "malum_data/reaping_drops";

    public static final ReapingDataReloadListener DATA = new ReapingDataReloadListener();

    public ReapingDataReloadListener() {
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
    public Codec<ReapingDropsData> getCodec() {
        return ReapingDropsData.DIRECT_CODEC;
    }

    @Override
    public ResourceLocation getID(ReapingDropsData instance) {
        return instance.id();
    }
}