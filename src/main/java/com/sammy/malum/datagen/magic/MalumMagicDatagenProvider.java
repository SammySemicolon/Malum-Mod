package com.sammy.malum.datagen.magic;

import com.sammy.malum.*;
import com.sammy.malum.datagen.lang.*;
import com.sammy.malum.datagen.magic.banner.BannerPatternDatagenData;
import com.sammy.malum.datagen.magic.rite.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import team.lodestar.lodestone.modules.datagen.modular.*;

import java.util.concurrent.*;

public class MalumMagicDatagenProvider extends ModularDatagenProvider {

    protected final MalumLangDatagen langDatagen;

    public MalumMagicDatagenProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, MalumLangDatagen langDatagen) {
        super(packOutput, lookupProvider, MalumMod.MALUM);
        this.langDatagen = langDatagen;
        BannerPatternDatagenData.init(this);
        SpiritRiteTypeDatagenData.init(this);
    }
}
