package com.sammy.malum.datagen.magic.rite;

import com.google.common.collect.*;
import com.google.gson.*;
import com.mojang.serialization.*;
import com.sammy.malum.common.data.listener.rite.*;
import com.sammy.malum.core.systems.rite.*;
import com.sammy.malum.core.systems.rite.effect.*;
import com.sammy.malum.core.systems.spirit.*;
import com.sammy.malum.datagen.lang.*;
import com.sammy.malum.datagen.magic.*;
import net.minecraft.core.*;
import net.minecraft.resources.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.modules.datagen.modular.*;

import java.util.*;

public class SpiritRiteJsonBody extends ModularDatagenJsonBody<SpiritRiteType> implements ILangJsonBody {

    protected List<Holder<SpiritArcanaType>> pattern;
    protected Holder<SpiritRiteEffect> effect;
    protected boolean isSoulwood;

    public SpiritRiteJsonBody(ResourceLocation id) {
        super(id);
    }

    public final SpiritRiteJsonBody setPattern(List<Holder<SpiritArcanaType>> pattern) {
        this.pattern = ImmutableList.copyOf(pattern);
        return this;
    }

    public SpiritRiteJsonBody setEffect(Holder<SpiritRiteEffect> effect) {
        this.effect = effect;
        return this;
    }

    public SpiritRiteJsonBody setSoulwood() {
        isSoulwood = true;
        return this;
    }

    @Override
    public String getDataLocation() {
        return SpiritRiteTypeReloadListener.DIRECTORY;
    }

    @Override
    public SpiritRiteType build(ModularDatagenProvider datagen, RegistryOps<JsonElement> dynamicOps, HolderLookup.Provider provider) {
        return new SpiritRiteType(id, pattern, effect, isSoulwood);
    }

    @Override
    public Codec<SpiritRiteType> getCodec() {
        return SpiritRiteType.DIRECT_CODEC;
    }

    @Override
    public void addLang(MalumLangDatagen datagen) {
        var riteName = id.getPath();
        var translation = DataHelper.toTitleCase(riteName, "_");
        datagen.add(id.getNamespace() + ".gui.rite." + riteName, translation);
    }
}