package com.sammy.malum.common.data.listener.reaping;

import com.google.gson.*;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.sammy.malum.MalumMod;
import com.sammy.malum.core.systems.rite.SpiritRiteType;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import team.lodestar.lodestone.modules.toolkit.reload_listener.CodecBasedReloadListener;

import java.util.*;

public class ReapingDataReloadListener extends CodecBasedReloadListener<ResourceLocation, ReapingDropsData> {

    public static final String DIRECTORY = "malum_data/reaping_drops";

    public static final ReapingDataReloadListener DATA = new ReapingDataReloadListener();

    public static List<ReapingDropsData> getPotentialDrops(LivingEntity entity) {
        var result = new ArrayList<ReapingDropsData>();
        var values = DATA.getValues();
        for (ReapingDropsData value : values) {
            if (value.mobSource().map(entity.getType()::is).orElse(false)) {
                result.add(value);
            }
        }
        return result;
    }

    public static List<ReapingDropsData> getPotentialDrops(BlockState blockstate, RandomSource randomSource) {
        var result = new ArrayList<ReapingDropsData>();
        var values = DATA.getValues();
        for (ReapingDropsData value : values) {
            if (value.blockSource().map(c -> c.test(blockstate, randomSource)).orElse(false)) {
                result.add(value);
            }
        }
        return result;
    }

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