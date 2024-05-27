package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.world.damagesource.DamageType;
import team.lodestar.lodestone.*;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.concurrent.*;

public class MalumDamageTypeTags extends FabricTagProvider<DamageType> {

    public MalumDamageTypeTags(FabricDataOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, Registries.DAMAGE_TYPE, pProvider);
    }

    @Override
    protected void addTags(Provider pProvider) {
        tag(LodestoneDamageTypeTags.IS_MAGIC).add(DamageTypeRegistry.VOODOO);
    }
}
