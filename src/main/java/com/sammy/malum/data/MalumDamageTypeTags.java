package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.*;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.minecraft.core.HolderLookup.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import team.lodestar.lodestone.*;
import team.lodestar.lodestone.registry.common.tag.*;

import java.util.concurrent.*;

public class MalumDamageTypeTags extends DamageTypeTagsProvider {

    public MalumDamageTypeTags(PackOutput pOutput, CompletableFuture<Provider> pProvider, ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider);
    }

    @Override
    protected void addTags(Provider pProvider) {
        tag(LodestoneDamageTypeTags.IS_MAGIC).add(DamageTypeRegistry.VOODOO);
    }
}
