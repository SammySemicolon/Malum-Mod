package com.sammy.malum.datagen.magic.reaping;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.sammy.malum.common.data.listener.reaping.ReapingDataReloadListener;
import com.sammy.malum.common.data.listener.reaping.ReapingDropsData;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.registries.DeferredHolder;
import team.lodestar.lodestone.modules.datagen.modular.ModularDatagenJsonBody;
import team.lodestar.lodestone.modules.datagen.modular.ModularDatagenProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ReapingDropsJsonBody extends ModularDatagenJsonBody<ReapingDropsData> {

    protected List<ReapingDropsData.ReapingLoot> drops = new ArrayList<>();
    protected MobSourceBuilder mobSource;
    protected RuleTest blockSource;

    public ReapingDropsJsonBody(ResourceLocation id) {
        super(id);
    }

    public ReapingDropsJsonBody addDrop(Ingredient drop, IntProvider count) {
        return addDrop(drop, count, 1f);
    }

    public ReapingDropsJsonBody addDrop(Ingredient drop, IntProvider count, float chance) {
        drops.add(new ReapingDropsData.ReapingLoot(drop, count, chance));
        return this;
    }

    public ReapingDropsJsonBody addMobSource(DeferredHolder<EntityType<?>, ? extends EntityType<?>> holder) {
        return addMobSource(holder.get());
    }

    public ReapingDropsJsonBody addMobSource(EntityType<?>... entities) {
        return addMobSource(Arrays.stream(entities).map(BuiltInRegistries.ENTITY_TYPE::getKey).toList());
    }

    public ReapingDropsJsonBody addMobSource(List<ResourceLocation> ids) {
        return addMobSource((l, o) -> HolderSet.direct(id -> {
            var key = ResourceKey.create(Registries.ENTITY_TYPE, id);
            var owner = o.owner(Registries.ENTITY_TYPE).orElseThrow();
            return Holder.Reference.createStandAlone(owner, key);
        }, ids));
    }

    public ReapingDropsJsonBody addMobSource(TagKey<EntityType<?>> tag) {
        return addMobSource((l, o) -> l.getOrThrow(tag));
    }

    public ReapingDropsJsonBody addMobSource(MobSourceBuilder mobSource) {
        this.mobSource = mobSource;
        return this;
    }

    public ReapingDropsJsonBody addBlockSource(RuleTest blockSource) {
        this.blockSource = blockSource;
        return this;
    }

    @Override
    public String getDataLocation() {
        return ReapingDataReloadListener.DIRECTORY;
    }

    @Override
    public ReapingDropsData build(ModularDatagenProvider datagen, RegistryOps<JsonElement> dynamicOps, HolderLookup.Provider provider) {
        var lookup = provider.lookupOrThrow(Registries.ENTITY_TYPE);
        var baked = Optional.ofNullable(mobSource).map(s -> s.buildSourceSet(lookup, dynamicOps));
        return new ReapingDropsData(id, drops, baked, Optional.ofNullable(blockSource));
    }

    @Override
    public Codec<ReapingDropsData> getCodec() {
        return ReapingDropsData.DIRECT_CODEC;
    }

    public interface MobSourceBuilder {
        HolderSet<EntityType<?>> buildSourceSet(HolderLookup.RegistryLookup<EntityType<?>> lookup, RegistryOps<JsonElement> dynamicOps);
    }
}