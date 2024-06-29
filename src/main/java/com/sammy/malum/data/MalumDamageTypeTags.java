package com.sammy.malum.data;

import com.sammy.malum.registry.common.DamageTypeRegistry;
import com.sammy.malum.registry.common.DamageTypeTagRegistry;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageType;
import team.lodestar.lodestone.registry.common.tag.LodestoneDamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class MalumDamageTypeTags extends FabricTagProvider<DamageType> {

    public MalumDamageTypeTags(FabricDataOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.DAMAGE_TYPE, pProvider);
    }

    @Override
    protected void addTags(Provider pProvider) {
        getOrCreateTagBuilder(LodestoneDamageTypeTags.IS_MAGIC).add(DamageTypeRegistry.VOODOO);
        getOrCreateTagBuilder(DamageTypeTagRegistry.SOUL_SHATTER_DAMAGE).add(DamageTypeRegistry.VOODOO, DamageTypeRegistry.SCYTHE_SWEEP);

    }
}
