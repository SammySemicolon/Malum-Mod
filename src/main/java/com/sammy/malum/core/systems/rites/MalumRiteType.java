package com.sammy.malum.core.systems.rites;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class MalumRiteType {
    public final ArrayList<MalumSpiritType> spirits;
    public final String identifier;
    public final MalumRiteEffect effect;
    public final MalumRiteEffect corruptedEffect;

    public MalumRiteType(String identifier, MalumSpiritType... spirits) {
        this.identifier = identifier;
        this.spirits = new ArrayList<>(Arrays.asList(spirits));
        effect = getNaturalRiteEffect();
        corruptedEffect = getCorruptedEffect();
    }

    public String translationIdentifier() {
        return "malum.gui.rite." + identifier;
    }

    public ResourceLocation getIcon() {
        return MalumMod.malumPath("textures/spirit/rite/" + identifier.replace("greater_", "").replace("_rite", "") + ".png");
    }

    public MalumSpiritType getEffectSpirit() {
        return spirits.get(spirits.size() - 1);
    }

    public boolean isOneAndDone(boolean corrupted) {
        return getRiteEffect(corrupted).isOneAndDone();
    }

    public BlockPos getRiteEffectCenter(TotemBaseBlockEntity totemBase) {
        return getRiteEffect(totemBase.corrupted).getRiteEffectCenter(totemBase);
    }

    public int getRiteRadius(boolean corrupted) {
        return getRiteEffect(corrupted).getRiteEffectRadius();
    }

    public int getRiteTickRate(boolean corrupted) {
        return getRiteEffect(corrupted).getRiteEffectTickRate();
    }

    public abstract MalumRiteEffect getNaturalRiteEffect();

    public abstract MalumRiteEffect getCorruptedEffect();

    public final MalumRiteEffect getRiteEffect(boolean corrupted) {
        return corrupted ? corruptedEffect : effect;
    }

    public void executeRite(TotemBaseBlockEntity totemBase) {
        getRiteEffect(totemBase.corrupted).riteEffect(totemBase);
    }
}