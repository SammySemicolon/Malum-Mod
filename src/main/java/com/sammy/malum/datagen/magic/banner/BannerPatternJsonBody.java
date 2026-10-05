package com.sammy.malum.datagen.magic.banner;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.sammy.malum.MalumMod;
import com.sammy.malum.common.data.listener.banner.MalumBannerPatternReloadListener;
import com.sammy.malum.common.data.listener.banner.MalumBannerPatternType;
import com.sammy.malum.common.data.listener.rite.SpiritRiteTypeReloadListener;
import com.sammy.malum.core.systems.rite.SpiritRiteType;
import com.sammy.malum.core.systems.rite.effect.SpiritRiteEffect;
import com.sammy.malum.core.systems.spirit.SpiritArcanaType;
import com.sammy.malum.datagen.lang.MalumLangDatagen;
import com.sammy.malum.datagen.magic.ILangJsonBody;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import team.lodestar.lodestone.helpers.DataHelper;
import team.lodestar.lodestone.modules.datagen.modular.ModularDatagenJsonBody;
import team.lodestar.lodestone.modules.datagen.modular.ModularDatagenProvider;

import java.util.List;

public class BannerPatternJsonBody extends ModularDatagenJsonBody<MalumBannerPatternType> implements ILangJsonBody {

    protected ResourceLocation texture;

    public BannerPatternJsonBody(ResourceLocation id) {
        super(id);
    }

    public BannerPatternJsonBody updateTexturePath(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }

    @Override
    public String getDataLocation() {
        return MalumBannerPatternReloadListener.DIRECTORY;
    }

    @Override
    public MalumBannerPatternType build(ModularDatagenProvider datagen, RegistryOps<JsonElement> dynamicOps, HolderLookup.Provider provider) {
        if (texture == null) {
            texture = id.withPath(p -> "textures/block/building/wool/banner_patterns/" + p + ".png");
        }
        return new MalumBannerPatternType(id, texture);
    }

    @Override
    public Codec<MalumBannerPatternType> getCodec() {
        return MalumBannerPatternType.DIRECT_CODEC;
    }

    @Override
    public void addLang(MalumLangDatagen datagen) {
//        var riteName = id.getPath();
//        var translation = DataHelper.toTitleCase(riteName, "_");
//        datagen.add(id.getNamespace() + ".gui.rite." + riteName, translation);
    }
}